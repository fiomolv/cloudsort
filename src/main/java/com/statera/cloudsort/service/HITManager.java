package com.statera.cloudsort.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.amazonaws.mturk.requester.Assignment;
import com.amazonaws.mturk.requester.Comparator;
import com.amazonaws.mturk.requester.EventType;
import com.amazonaws.mturk.requester.HIT;
import com.amazonaws.mturk.requester.HITStatus;
import com.amazonaws.mturk.requester.NotificationSpecification;
import com.amazonaws.mturk.requester.NotificationTransport;
import com.amazonaws.mturk.requester.QualificationRequirement;
import com.amazonaws.mturk.service.axis.RequesterService;
import com.amazonaws.mturk.service.exception.InternalServiceException;
import com.amazonaws.mturk.util.ClientConfig;
import com.statera.cloudsort.dao.TurkDAO;
import com.statera.cloudsort.entity.Category;
import com.statera.cloudsort.entity.Config;
import com.statera.cloudsort.entity.Product;
import com.statera.cloudsort.entity.Qualification;
import com.statera.cloudsort.entity.Request;
import com.statera.cloudsort.entity.Response;

public class HITManager {

    private RequesterService service;

    
    static Logger log = Logger.getLogger("HitManager");

    private TurkDAO turkDAO;
    
    private String hostname;

    public void setTurkDAO(TurkDAO turkDAO) {
	this.turkDAO = turkDAO;
	init();
    }

    private AnswerParser answerParser;

    public void setAnswerParser(AnswerParser answerParser) {
	this.answerParser = answerParser;
    }

    public HITManager(){
	
    }
    
    public HITManager(TurkDAO turkDAO, AnswerParser answerParser){
	this.turkDAO = turkDAO;
	this.answerParser = answerParser;
    }

    public void init(){
		
	hostname = turkDAO.getConfigValue(Config.HOST);
	log.info("Config.HOST = " + hostname);
	
	ClientConfig clientConfig = new ClientConfig();
	
	String accessKeyId = turkDAO.getConfigValue(Config.ACCESS_KEY_ID);
	
	log.info("Config.ACCESS_KEY_ID = " + accessKeyId);
	clientConfig.setAccessKeyId(accessKeyId);
	
	String secretAccessKey = turkDAO.getConfigValue(Config.SECRET_ACCESS_KEY);
	
	String turkEnvironment = turkDAO.getConfigValue(Config.TURK_ENVIRONMENT);
	log.info("Config.TURK_ENVIRONMENT = " + turkEnvironment);
				
	clientConfig
		.setSecretAccessKey(secretAccessKey);
	
	if("SANDBOX".equalsIgnoreCase(turkEnvironment)){
	  clientConfig.setServiceURL(ClientConfig.SANDBOX_SERVICE_URL);
	}else{
	    clientConfig.setServiceURL(ClientConfig.PRODUCTION_SERVICE_URL);
	}
			
	clientConfig.setRetryAttempts(10);
	clientConfig.setRetryDelayMillis(1000);
	HashSet<String> retriableErrors = new HashSet<String>();
	retriableErrors.add("Server.ServiceUnavailable");

	clientConfig.setRetriableErrors(retriableErrors);

	service = new RequesterService(clientConfig);
    }
        

    /**
     * Check to see if there are sufficient funds.
     * 
     * @return true if there are sufficient funds. False otherwise.
     */
    public boolean hasEnoughFund() {
	
	
	
	double balance = service.getAccountBalance();
	// System.out.println("Got account balance: "+
	// RequesterService.formatCurrency(balance));
	return balance > 0;
    }

    public void createHIT(Product product, int tier) {

	
	String parentCategoryName = turkDAO.getParentCategoryNameForProduct(product.getId());
	log.info("creating tier " + tier + " hit for productId "
		+ product.getId());
	
	String hitTypeId = null;
	String title = "Choose the best category for this "+ parentCategoryName +" product";
	String description = "Please look at this product and select a categorization for it.";
	String keywords = "shopping,product,merchandise,shopzilla";
		
	double reward = 0.0;
	
	try{
	    if(tier==1){
		reward = Double.parseDouble(turkDAO.getConfigValue(Config.TIER_1_REWARD));
	    }else{
		reward = Double.parseDouble(turkDAO.getConfigValue(Config.TIER_2_REWARD));
	    }
	    
	}catch(Exception ignore){}
	
	int maxAssignments = 3 - tier;
	long assignmentDurationInSeconds = 60 * 60; // one hour
	long autoApprovalDelayInSeconds = 60 * 60 * 24 * 30; // one month
	long lifetimeInSeconds = 60 * 60 * 24; // one day
	String requesterAnnotation = "ShopZilla two plus one HIT";
	
	String externalURL="http://"+hostname+"/cloudsort/hit.do?id="+ product.getId();
	if(tier==2){
	    externalURL+="&amp;tier=2";
	}
	
	String question = "<?xml version=\"1.0\"?>"
		+ "<ExternalQuestion xmlns=\"http://mechanicalturk.amazonaws.com/AWSMechanicalTurkDataSchemas/2006-07-14/ExternalQuestion.xsd\">"
		+ "	<ExternalURL>"+externalURL + "</ExternalURL>"
		+ "	<FrameHeight>500</FrameHeight>" + "</ExternalQuestion>";

	QualificationRequirement[] qualificationRequirements = null;

	Qualification qualification = null;

	try {

	    qualification = turkDAO.getQualificationForCategoryId(product
		    .getParentCategoryId());

	} catch (Exception ignore) {
	    log.info("no qualification data found for parent category "
		    + product.getParentCategoryId());
	}

	if (qualification != null) {

	    if (tier == 2 && qualification.getQualTypeIdTrusted() != null
		    && qualification.getQualTypeIdTrusted().length() > 0
		    || tier == 1
		    && qualification.getQualTypeIdGeneral() != null
		    && qualification.getQualTypeIdGeneral().length() > 0) {

		qualificationRequirements = new QualificationRequirement[1];
		qualificationRequirements[0] = new QualificationRequirement();
		qualificationRequirements[0]
			.setComparator(Comparator.GreaterThanOrEqualTo);
		if (tier == 1) {
		    qualificationRequirements[0]
			    .setQualificationTypeId(qualification
				    .getQualTypeIdGeneral());
		    qualificationRequirements[0].setIntegerValue(qualification
			    .getQualTypeScoreGeneral());
		} else {
		    qualificationRequirements[0]
			    .setQualificationTypeId(qualification
				    .getQualTypeIdGeneral());
		    qualificationRequirements[0].setIntegerValue(qualification
			    .getQualTypeScoreGeneral());
		}
		qualificationRequirements[0].setRequiredToPreview(false);
				
		log.info("qualification typeID: " +qualificationRequirements[0].getQualificationTypeId() +", score: "+qualificationRequirements[0].getIntegerValue());  		
	    }
	}

	String responseGroup[] = null;

	
	HIT hit = service.createHIT(hitTypeId, title, description, keywords,
		question, reward, assignmentDurationInSeconds,
		autoApprovalDelayInSeconds, lifetimeInSeconds, maxAssignments,
		requesterAnnotation, qualificationRequirements, responseGroup);

	log.info("hit id " + hit.getHITId() + ", hitType " + hit.getHITTypeId()
		+ " created");

	Request request = new Request();
	request.setProductId(product.getId());
	request.setCreatedDate(new Date());
	request.setHitId(hit.getHITId());
	request.setParentCategoryId(product.getParentCategoryId());
	request.setTier(tier);

	turkDAO.saveRequest(request);

	NotificationSpecification notification = new NotificationSpecification();
	notification.setDestination("http://" + hostname + "/cloudsort/hitresult");
	notification.setEventType(new EventType[] {
		EventType.AssignmentSubmitted, EventType.AssignmentAccepted,
		EventType.AssignmentReturned, EventType.AssignmentAbandoned });
	notification.setTransport(NotificationTransport.REST);
	notification.setVersion("2006-05-05");

	service.setHITTypeNotification(hit.getHITTypeId(), notification, true);

	log.info("notification set up for Hit TypeID = " + hit.getHITTypeId());

    }

    public void getHITResult(String hitId) {

	
	log.info("getting assignment result for hitId " + hitId);

	Assignment[] assignments = service
		.getAllSubmittedAssignmentsForHIT(hitId);

	Response[] responses = new Response[assignments.length];
	Request request = null;
	// Product product = null;
	if (assignments != null && assignments.length > 0) {
	    request = turkDAO.getRequestByHitId(assignments[0].getHITId());
	}
	

	int i = 0;
	for (Assignment assignment : assignments) {

	    log.info("got assignmentID " + assignment.getAssignmentId()
		    + " from worker " + assignment.getWorkerId());

	    log.info("answer XML: " + assignment.getAnswer());
	    String categoryIdAnswer = answerParser.getAnswer(assignment
		    .getAnswer());
	    log.info("answer is: " + categoryIdAnswer);

	    Response response = turkDAO.getResponseByAssignmentId(assignment
		    .getAssignmentId());

	    if (response != null) {

	    } else {
		response = new Response();
		response.setAnswer(categoryIdAnswer);
		response.setCreatedDate(new Date());
		response.setHitId(assignment.getHITId());
		response.setWorkerId(assignment.getWorkerId());
		response.setAssignmentId(assignment.getAssignmentId());
		response.setRequestId(request.getId());
	    }

	    response.setAnswer(categoryIdAnswer);

	    responses[i] = response;
	    turkDAO.saveResponse(response);
	    i++;
	}

	if (assignments.length == 2 && responses[0].getAnswer()!=null && responses[1].getAnswer()!=null) {

	    if (!AnswerParser.EMPTY_ANSWER.equals(responses[0].getAnswer())&&responses[0].getAnswer().equals(responses[1].getAnswer())) {

		String unanymousAnswer = responses[0].getAnswer();

		reviewAssignment(assignments[0].getAssignmentId(),true);
		reviewAssignment(assignments[1].getAssignmentId(),true);
				
		Product product = turkDAO
			.getProductById(request.getProductId());
		product.setCategoryCode(unanymousAnswer);
		product.setModifiedDate(new Date());
		turkDAO.saveProduct(product);

		for (int j = 0; j < 2; j++) {
		    responses[j].setResult("APPROVED");
		    turkDAO.saveResponse(responses[j]);

		    log.info("approved assignmentId "
			    + responses[j].getAssignmentId());
		}

	    } else {

		// create adjudication hit

		Product product = turkDAO
			.getProductById(request.getProductId());


		log
			.info("answers did not match, creating adjudication hit for product "
				+ product.getId());

		createHIT(product, 2);
	    }

	} else if (assignments.length == 1 && responses[0].getAnswer()!=null ) {
	    // check to see if its an adjudication result

	    if (request != null && request.getTier() == 2) {
		// this was a tier 2 hit

		// provide feedback for tier one hits
		int productId = request.getProductId();

		// retrieve tier one request
		Request tierOneRequest = turkDAO.getRequestByProductIdAndTier(
			productId, 1);

		List<Response> tierOneResponses = turkDAO
			.getResponsesByRequestId(tierOneRequest.getId());

		for (Response tierOneResponse : tierOneResponses) {
		    
		    if(tierOneResponse.getAnswer()==null){
			log.warn("tier2 response received but tier one answer is null for responseId "+ tierOneResponse.getId());
		    }
		    
		    
		    else if (!AnswerParser.EMPTY_ANSWER.equals(responses[0].getAnswer())&&tierOneResponse.getAnswer().equals(
			    responses[0].getAnswer())) {

			reviewAssignment(tierOneResponse.getAssignmentId(),
				true);
			tierOneResponse.setResult("APPROVED");

			log.info("approved assigment "
				+ tierOneResponse.getAssignmentId()
				+ ", matched adjudication result");

		    } else {
			reviewAssignment(tierOneResponse.getAssignmentId(),
				false);
			tierOneResponse.setResult("REJECTED");
			log.info("rejected assigment "
				+ tierOneResponse.getAssignmentId()
				+ ", did not match adjudication result");
		    }
		    tierOneResponse.setModifiedDate(new Date());
		    turkDAO.saveResponse(tierOneResponse);
		}

		// review tier2 response
		
		
		if(!AnswerParser.EMPTY_ANSWER.equals(responses[0].getAnswer())){
		
		reviewAssignment(assignments[0].getAssignmentId(), true);
		responses[0].setResult("APPROVED");
		log.info("approved adjudication assigment "
			+ assignments[0].getAssignmentId());
		}else{
			reviewAssignment(assignments[0].getAssignmentId(), false);
			responses[0].setResult("REJECTED");	
			log.info("rejected adjudication assigment "
				+ assignments[0].getAssignmentId());
		}
		responses[0].setModifiedDate(new Date());
		turkDAO.saveResponse(responses[0]);

		

		Product product = turkDAO
			.getProductById(request.getProductId());
		product.setCategoryCode(responses[0].getAnswer());
		product.setModifiedDate(new Date());
		turkDAO.saveProduct(product);

	    }
	}
    }

    private void reviewAssignment(String assignmentId, boolean correct) {

	try{
	if (correct) {
	    service.approveAssignment(assignmentId,
		    "Product classified correctly.  Thank you.");
	} else {
	    service.approveAssignment(assignmentId,
		    "Product classified.  Thank you.");
	}
	}catch(InternalServiceException e){
	    log.error("InternalServiceException while trying to approve["+correct+"] assignment "+assignmentId+", "+e.getMessage());	    	    
	}catch(Exception e){
	    log.error("Exception while trying to approve["+correct+"] assignment "+assignmentId+", "+e.getMessage());	    	    
	}

    }

    public void deleteHITs(String[] string) {
		
	service.deleteHITs(string, true,true,null);
			
    }

    public void getHITStatus(String hitId) {
		
	HIT hit = service.getHIT(hitId);
	
	HITStatus hitStatus = hit.getHITStatus();
	
	String value = hitStatus.getValue();
	
	System.out.println("hit value  = "+ value);
			
	Assignment[] assignments = service.getAssignmentsForHIT(hitId, 1);
	if(assignments!=null){
	for(Assignment assignment:assignments){
	    System.out.println("assignment:" + assignment.getAnswer());
	    
	}
	}
	
    }
    
    
    

}
