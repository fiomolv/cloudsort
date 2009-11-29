package com.statera.cloudsort.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.statera.cloudsort.dao.TurkDAO;

public class Setup {

    
    static Logger log = Logger.getLogger("Setup");
       
    private TurkDAO dao;
    private CategoryLoader categoryLoader;
    
    @Autowired
    public void setTurkDAO(TurkDAO turkDAO){
	this.dao = turkDAO;
    }
    @Autowired
    public void setCategoryLoader(CategoryLoader categoryLoader){
	this.categoryLoader = categoryLoader;
    }
    
    

    public void execute(){
	
	List topLevelCategories = dao.getTopLevelCategories();
	
	if(topLevelCategories==null||topLevelCategories.size()<1){
	    
	    log.info("no top-level categories found");
	    
	    categoryLoader.load();
	}else{
	    log.info("categories already loaded");	    
	}
	
	
    }
    
}
