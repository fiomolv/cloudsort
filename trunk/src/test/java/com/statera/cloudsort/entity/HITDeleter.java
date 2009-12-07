package com.statera.cloudsort.entity;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.statera.cloudsort.dao.TurkDAO;
import com.statera.cloudsort.service.AnswerParser;
import com.statera.cloudsort.service.HITManager;

public class HITDeleter extends TestCase {

    HITManager hitManager;

    public static void main(String[] args) {
	ApplicationContext context = new FileSystemXmlApplicationContext(
		"src/main/webapp/WEB-INF/spring.xml");
	TurkDAO turkDAO = (TurkDAO)context.getBean("turkDAO");

	AnswerParser answerParser = (AnswerParser)context.getBean("answerParser");
		
	HITManager hitManager = new HITManager(turkDAO,answerParser);
		
	List<Request> requests = turkDAO.getAllRequests();
	
	String[] hits = new String[requests.size()];
	int i =0;
	for(Request request:requests){
	    hits[i]=request.getHitId();
	    i++;
	}
	hitManager.deleteHITs(hits);
		
    }

}