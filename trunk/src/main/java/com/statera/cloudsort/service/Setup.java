package com.statera.cloudsort.service;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.statera.cloudsort.dao.TurkDAO;
import com.statera.cloudsort.entity.Category;
import com.statera.cloudsort.entity.Config;
import com.statera.cloudsort.entity.Qualification;

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
	    
	    
	    List<Category> parents = dao.getTopLevelCategories();
	    
	    for(Category parent:parents){
		Qualification qualification = new Qualification();
		qualification.setCategoryId(parent.getId());
		qualification.setName(parent.getName());
		dao.saveQualification(qualification);
	    }    
	    
	}else{
	    log.info("categories already loaded");	    
	}
	
	
	Properties props = dao.getConfig();
	if(props!=null && props.size()>0){
	    log.info("config already set up");
	}else{
	    log.info("setting up default config values");	

	    dao.saveConfig(new Config(Config.HOST,"yourhost.com"));
	    dao.saveConfig(new Config(Config.ACCESS_KEY_ID,"AWSACCESSKEY"));
	    dao.saveConfig(new Config(Config.SECRET_ACCESS_KEY,"AWSSECRETKEY"));
	    dao.saveConfig(new Config(Config.TURK_ENVIRONMENT,"SANDBOX"));
	       	    
	    dao.saveConfig(new Config(Config.TIER_1_REWARD,"0.01"));
	    dao.saveConfig(new Config(Config.TIER_2_REWARD,"0.02"));      
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
    }
    
}
