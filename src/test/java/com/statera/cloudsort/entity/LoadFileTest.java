package com.statera.cloudsort.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import com.Ostermiller.util.CSVParser;

public class LoadFileTest {


    
    public static void main(String[] args){
	LoadFileTest loadFileTest = new LoadFileTest();
	
	Category category = new Category();
	
	File file = new File("/home/dknoern/Desktop/Pet_Supplies_12_2_09.utf.txt");
	loadFileTest.load(category, file, false);
    }
    
    public void load(Category category,File file,boolean createHITs){
	 
	try {
	    CSVParser csvParser = new CSVParser(new FileInputStream(file));

	   


	    String[] nextLine = csvParser.getLine(); //skip first line
	    while ((nextLine = csvParser.getLine()) != null) {
	        // nextLine[] is an array of values from the line
	
		for(int i=0;i<nextLine.length;i++){
		System.out.print(nextLine[i]);
		    if(i<nextLine.length-1){
			System.out.print(",");
		    }
		}
		
		System.out.println();
	
		
		
		
		
		String oid = nextLine[0];
		String title = nextLine[1];
		String imageUrl = nextLine[2];
		String productUrl = nextLine[3];


		if(title.length()>255)title=title.substring(0,255);
		if(imageUrl.length()>255)imageUrl=imageUrl.substring(0,255);
		if(productUrl.length()>255)productUrl=productUrl.substring(0,255);
		
		
		Product product = new Product();
		
		product.setOid(oid);
		product.setParentCategoryId(category.getId());
		product.setTitle(title);
		product.setCreatedDate(new Date());
		product.setImageUrl(imageUrl);
		product.setProductUrl(productUrl);
				
		for(int i=0;i<6;i++){
		    
		    
		    
		    String categoryCode = nextLine[4+2*i];
		    
		    
		    if(categoryCode.length()>255)categoryCode=categoryCode.substring(0,255);
		    
		    String categoryName = nextLine[5+2*i];
		    
		    if(categoryName.length()>255)categoryName=categoryName.substring(0,255);
		    
		    
		    
		    product.addSuggestion(categoryCode,categoryName);
		    

		}
	
		
	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	
    }

    
    
    
    
    
    
  
}
