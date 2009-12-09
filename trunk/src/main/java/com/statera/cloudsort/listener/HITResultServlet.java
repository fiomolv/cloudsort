package com.statera.cloudsort.listener;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.statera.cloudsort.dao.TurkDAO;
import com.statera.cloudsort.service.AnswerParser;
import com.statera.cloudsort.service.HITManager;

public class HITResultServlet extends HttpServlet {

    static Logger log = Logger.getLogger("HITResultServlet");

    HITManager hitManager = null;
    public void init(ServletConfig config) throws ServletException {
	super.init(config);

	ApplicationContext context = WebApplicationContextUtils
		.getWebApplicationContext(this.getServletConfig()
			.getServletContext());

	TurkDAO turkDAO = (TurkDAO) context.getBean("turkDAO");

	AnswerParser answerParser = (AnswerParser) context
		.getBean("answerParser");

	hitManager = new HITManager(turkDAO, answerParser);

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	log.info(" HIT RESULT servlet " + request.getMethod() + ": "
		+ request.getQueryString() + ", method = "
		+ request.getMethod());

	for (int i = 1; i < 100; i++) {
	    String eventType = request
		    .getParameter("Event." + i + ".EventType");
	    if (eventType == null) {
		break;
	    } else if ("AssignmentSubmitted".equalsIgnoreCase(eventType)) {
		String hitId = request.getParameter("Event." + i + ".HITId");
		String hitTypeId = request.getParameter("Event." + i
			+ ".HITTypeId");
		String assignmentId = request.getParameter("Event." + i
			+ ".AssignmentId");

		log.info(eventType + " for hitID " + hitId + ", hitTypeId "
			+ hitTypeId + ", assignmentId " + assignmentId);

		hitManager.getHITResult(hitId);
	    }
	}

	response.getWriter().print("HIT RESULT servlet OK");
    }



}
