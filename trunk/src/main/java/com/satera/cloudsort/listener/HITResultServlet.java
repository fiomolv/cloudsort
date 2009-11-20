package com.satera.cloudsort.listener;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.satera.cloudsort.service.HITManager;

public class HITResultServlet extends HttpServlet {

    static Logger log = Logger.getLogger("LoopbackServlet");
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	doPost(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {
	
	log.info(" HIT RESULT servlet called with payload: "+ request.getQueryString());
	
	
	Enumeration names = request.getParameterNames();
	while(names.hasMoreElements()){
	    String name = (String)names.nextElement();
	    log.info("PARAM NAME:"+name+", VALUE:"+request.getParameter(name));
	    
	    
	}
	
	
	String hitId = request.getParameter("Event.1.HITId");
	
	
	log.info("hitID = " + hitId);
	HITManager hitManager = new HITManager();
	
	hitManager.getHITResult(hitId);
	
	
	//response.setContentType("text/xml");
	response.getWriter().print("HIT RESULT servlet called with payload");
    }  
}
