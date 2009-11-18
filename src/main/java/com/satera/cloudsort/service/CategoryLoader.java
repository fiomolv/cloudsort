package com.satera.cloudsort.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;

import com.satera.cloudsort.dao.TurkDAO;
import com.satera.cloudsort.entity.Category;

public class CategoryLoader {

    
    private TurkDAO dao = null;
    
    @Autowired
    public void setMusicDAO(TurkDAO musicDAO){
	this.dao = musicDAO;
    }
    
    
    public void load(){
	 
		try {
		    BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/categories.csv"));
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
			
			
			
	
			System.out.println("category: "+name);
			dao.saveCategory(category);
			
		    }
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

		
	    }


    }
    
    
    
    

