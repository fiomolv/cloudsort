package com.satera.cloudsort;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import com.satera.cloudsort.dao.TurkDAO;
import com.satera.cloudsort.entity.Category;
import com.satera.cloudsort.service.HITManager;

public class Ingestor {

    
    public static final String inRoot = "/opt/cloudsort/in";
    public static final String processedRoot = "/opt/cloudsort/processed";
    private TurkDAO dao = null;
    private HITManager hitManager = null;    
    
    @Autowired
    public void setTurkDAO(TurkDAO turkDAO){
	this.dao = turkDAO;
    }
    
    @Autowired
    public void setHITManager(HITManager hitManager){
	this.hitManager = hitManager;
    }    
    public void init(){
	
	List<Category> categories = dao.getTopLevelCategories();
	
	for(Category category:categories){
	    String inDir = inRoot + "/" + category.getName();
	    String processedDir = processedRoot + "/" + category.getName();
	    	    
	    new File(inDir).mkdirs();
	    	    
	    new File(processedDir).mkdirs();
	    
	    DirWatcher dirWatcher = new DirWatcher(inDir,processedDir,category.getId(),dao,hitManager);
	    
	    dirWatcher.start();	    
	}
		
	
    }
    
  
}
