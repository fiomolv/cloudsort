package com.statera.cloudsort.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import com.statera.cloudsort.dao.TurkDAO;
import com.statera.cloudsort.entity.Category;
import com.statera.cloudsort.service.HITManager;

public class Ingestor {

    
    public static final String inRoot = "/opt/cloudsort/in";
    public static final String processedRoot = "/opt/cloudsort/processed";
    private TurkDAO dao = null; 
    private AnswerParser answerParser = null;
    
    @Autowired
    public void setTurkDAO(TurkDAO turkDAO){
	this.dao = turkDAO;
    }
    
    @Autowired
    public void setAnswerParser(AnswerParser answerParser){
	this.answerParser = answerParser;
    }   
   
    public void init(){
	
	List<Category> categories = dao.getTopLevelCategories();
	
	for(Category category:categories){
	    String inDir = inRoot + "/" + category.getName();
	    String processedDir = processedRoot + "/" + category.getName();
	    	    
	    new File(inDir).mkdirs();
	    	    
	    new File(processedDir).mkdirs();
	    
	    HITManager hitManager = new HITManager();
	    hitManager.setAnswerParser(answerParser);
	    hitManager.setTurkDAO(dao);
	    DirWatcher dirWatcher = new DirWatcher(inDir,processedDir,category,dao,hitManager);
	    
	    dirWatcher.start();	    
	}
		
	
    }
    
  
}
