package com.statera.cloudsort.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.statera.cloudsort.dao.TurkDAO;
import com.statera.cloudsort.entity.Category;

public class CategoryLoader {

    
    private TurkDAO dao = null;

    static Logger log = Logger.getLogger("CategoryLoader");
       
 
    @Autowired
    public void setTurkDAO(TurkDAO turkDAO){
	this.dao = turkDAO;
    }
    
    
    public void load(){
	
	int categoriesLoaded = 0;
	 
		try {
		    BufferedReader reader = new BufferedReader(new FileReader("/opt/cloudsort/categories.csv"));
		    String line = null;
		    while ((line = reader.readLine()) != null) {

			StringTokenizer st = new StringTokenizer(line,"|");

			String name = st.nextToken();
			String code = st.nextToken();
			String parentName = st.nextToken();

			Category category = new Category();
			category.setCategoryCode(code);
			category.setName(name);
		
			
			Category parent = dao.getCategoryByName(parentName);
			if(parent==null){
			    
			    parent = new Category();
			    parent.setName(parentName);
			    dao.saveCategory(parent);
			}
			
			category.setParentID(parent.getId());
			
		
			try{
			
			dao.saveCategory(category);
			categoriesLoaded++;
			
			}catch(Exception e){
			    log.info("failed to load category " +  name+ ", " + e.getMessage());
			}
			
		    }
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
		log.info(categoriesLoaded + " categories loaded");
		
	    }
    }
    
    
    
    

