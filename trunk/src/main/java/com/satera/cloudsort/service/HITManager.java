package com.satera.cloudsort.service;

import java.util.Date;
import java.util.List;

import com.amazonaws.mturk.requester.Assignment;
import com.amazonaws.mturk.requester.Comparator;
import com.amazonaws.mturk.requester.EventType;
import com.amazonaws.mturk.requester.HIT;
import com.amazonaws.mturk.requester.NotificationSpecification;
import com.amazonaws.mturk.requester.NotificationTransport;
import com.amazonaws.mturk.requester.QualificationRequirement;
import com.amazonaws.mturk.service.axis.RequesterService;
import com.amazonaws.mturk.util.PropertiesClientConfig;
import com.satera.cloudsort.dao.TurkDAO;
import com.satera.cloudsort.entity.Product;
import com.satera.cloudsort.entity.Request;
import com.satera.cloudsort.entity.Response;

public class HITManager {

    // Defining the locations of the input files
    private RequesterService service;
    // private RequesterServiceRaw serviceRaw;

    private static String host = "24.17.221.139";
    private String rootDir = "src/main/resources/mturk";

    private TurkDAO turkDAO;

    public void setTurkDAO(TurkDAO turkDAO) {
	this.turkDAO = turkDAO;
    }

    public HITManager() {
	service = new RequesterService(new PropertiesClientConfig(rootDir
		+ "/mturk.properties"));
    }

    /**
     * Check to see if there are sufficient funds.
     * 
     * @return true if there are sufficient funds. False otherwise.
     */
    public boolean hasEnoughFund() {
	double balance = service.getAccountBalance();
	System.out.println("Got account balance: "
		+ RequesterService.formatCurrency(balance));
	return balance > 0;
    }

    public void createHIT(Product product, int tier) {

	String hitTypeId = "20091121100200";
	String title = "Product categorization";
	String description = "Please look at this product and select a categorization for it.";
	String keywords = "shopping,product,merchandise";
	double reward = .02;
	int maxAssignments = 2;
	long assignmentDurationInSeconds = 60 * 60; // one hour
	long autoApprovalDelayInSeconds = 60 * 60 * 24 * 30; // one month
	long lifetimeInSeconds = 60 * 60 * 24; // one day
	String requesterAnnotation = "ShopZilla two plus one HIT";

	String question = "<?xml version=\"1.0\"?>"
		+ "<ExternalQuestion xmlns=\"http://mechanicalturk.amazonaws.com/AWSMechanicalTurkDataSchemas/2006-07-14/ExternalQuestion.xsd\">"
		+ "	<ExternalURL>http://24.17.221.139/cloudsort/externalpage.htm?url=dude.com</ExternalURL>"
		+ "	<FrameHeight>600</FrameHeight>" + "</ExternalQuestion>";

	QualificationRequirement[] qualificationRequirements;

	if (tier == 2) {
	    qualificationRequirements = new QualificationRequirement[2];

	} else {
	    qualificationRequirements = new QualificationRequirement[1];
	}

	qualificationRequirements[0] = new QualificationRequirement();
	qualificationRequirements[0].setComparator(Comparator.EqualTo);
	qualificationRequirements[0].setQualificationTypeId("POOL1");

	if (tier == 2) {
	    qualificationRequirements[1] = new QualificationRequirement();
	    qualificationRequirements[1].setComparator(Comparator.EqualTo);
	    qualificationRequirements[1].setQualificationTypeId("POOL2");

	}

	String responseGroup[] = null;

	HIT hit = service.createHIT(hitTypeId, title, description, keywords,
		question, reward, assignmentDurationInSeconds,
		autoApprovalDelayInSeconds, lifetimeInSeconds, maxAssignments,
		requesterAnnotation, qualificationRequirements, responseGroup);

	Request request = new Request();
	request.setCreatedDate(new Date());
	request.setHitId(hit.getHITId());
	request.setParentCategoryId(product.getParentCategoryId());
	request.setTier(tier);

	turkDAO.saveRequest(request);

	NotificationSpecification notification = new NotificationSpecification();
	notification.setDestination("http://" + host + "/cloudsort/hitresult");
	notification.setEventType(new EventType[] {
		EventType.AssignmentSubmitted, EventType.AssignmentAccepted,
		EventType.AssignmentReturned, EventType.AssignmentAbandoned });
	notification.setTransport(NotificationTransport.REST);
	notification.setVersion("2006-05-05");

	service.setHITTypeNotification(hitTypeId, notification, true);

    }

    public void getHITResult(String hitId) {

	Assignment[] assignments = service
		.getAllSubmittedAssignmentsForHIT(hitId);

	Response[] responses = new Response[assignments.length];

	int i = 0;
	for (Assignment assignment : assignments) {

	    System.out.println("THE ANSWER IS" + assignment.getAnswer());
	    System.out.println("WORKER is" + assignment.getWorkerId());

	    Response response = turkDAO.getResponseByAssignmentId(assignment
		    .getAssignmentId());

	    if (response != null) {

	    } else {
		response = new Response();
		response.setAnswer(assignment.getAnswer());
		response.setCreatedDate(new Date());
		response.setHitId(assignment.getHITId());
		response.setWorkerId(assignment.getWorkerId());
	    }

	    responses[i] = response;
	    turkDAO.saveResponse(response);

	    i++;
	}

	if (assignments.length == 2) {
	    if (responses[0].getAnswer().equals(responses[1].getAnswer())) {
		String requesterFeedback = "Product classified correctly.  Thank you.";
		service.approveAssignment(assignments[0].getAssignmentId(),
			requesterFeedback);
		service.approveAssignment(assignments[1].getAssignmentId(),
			requesterFeedback);
	    } else {

		// create adjudication hit

		assignments[0].getHITId();
		Request originalRequest = turkDAO
			.getRequestByHitId(assignments[0].getHITId());

		Product product = turkDAO.getProductById(originalRequest
			.getProductId());
		createHIT(product, 2);
	    }

	} else if (assignments.length == 1) {
	    // check to see if its an adjudication result

	    Request request = turkDAO.getRequestByHitId(hitId);
	    if (request != null && request.getTier() == 2) {
		// this was a tier 2 hit
		Response tierTwoResponse = responses[0];

		// give feedback
		String requesterFeedback = "Product classified correctly.  Thank you.";
		service.approveAssignment(assignments[0].getAssignmentId(),
			requesterFeedback);

		// provide feedback for tier one hits

		int productId = request.getProductId();

		// retrieve tier one request
		Request tierOneRequest = turkDAO.getRequestByProductIdAndTier(
			productId, 1);

		List<Response> tierOneResponses = turkDAO
			.getResponsesByRequestId(tierOneRequest.getId());

		for (Response tierOneResponse : tierOneResponses) {
		    if (tierOneResponse.getAnswer().equals(
			    responses[0].getAnswer())) {

			reviewAssignment(assignments[0].getAssignmentId(), true);

		    } else {
			reviewAssignment(assignments[0].getAssignmentId(), true);
		    }
		}

	    }
	}
    }

    private void reviewAssignment(String assignmentId, boolean correct) {

	if (correct) {
	    service.approveAssignment(assignmentId,
		    "Product classified correctly.  Thank you.");
	} else {
	    service.approveAssignment(assignmentId,
		    "Product classified incorrectly.  Thank you.");
	}

    }

}