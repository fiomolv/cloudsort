package com.statera.cloudsort.entity;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.statera.cloudsort.dao.TurkDAO;
import com.statera.cloudsort.service.HITManager;

public class HITChecker extends TestCase {

    HITManager hitManager;

    public static void main(String[] args) {
	ApplicationContext context = new FileSystemXmlApplicationContext(
		"src/main/webapp/WEB-INF/spring.xml");
	TurkDAO turkDAO = (TurkDAO)context.getBean("turkDAO");
	HITManager hitManager = (HITManager)context.getBean("HITManager");
	
	
	//String hitId = "CZJZXXZHGX64N8Y6GXCZ";
	//hitManager.getHITStatus(hitId);
	//hitManager.getHITResult(hitId);
	
	
	
	/*
	List<Request> orphans = turkDAO.getOrphanedRequests();
	
	
	for(Request orphan:orphans){
	    hitManager.getHITStatus(orphan.getHitId());
		//hitManager.getHITResult(orphan.getHitId());
	}
	

	
	List<Response> orphans = turkDAO.getOrphanedResponses();
	
	
	for(Response orphan:orphans){
	    //hitManager.getHITStatus(orphan.getHitId());
		hitManager.getHITResult(orphan.getHitId());
	}
	
	*/
	
	
	List<Response> orphans = turkDAO.getResponsesWithNoResult();
	
	//Response orphan = orphans.get(0);
	for(Response orphan:orphans){
	    //hitManager.getHITStatus(orphan.getHitId());
		hitManager.getHITResult(orphan.getHitId());
	}
	
	
	
	

    }

}