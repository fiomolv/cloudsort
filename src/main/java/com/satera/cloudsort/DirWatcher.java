package com.satera.cloudsort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.satera.cloudsort.dao.TurkDAO;
import com.satera.cloudsort.entity.Product;
import com.satera.cloudsort.service.HITManager;

public class DirWatcher extends Thread {

    private String inDir;
    private String processedDir;
    private TurkDAO dao;
    private HITManager hitManager;
    private int parentCategoryId;
          
    
    public DirWatcher(String inDir,String processedDir,int parentCategoryId,TurkDAO dao,HITManager hitManager){
	this.inDir = inDir;
	this.processedDir = processedDir;
	this.parentCategoryId = parentCategoryId;
	this.dao = dao;
	this.hitManager = hitManager;
    }
    
    public void run() {
	
	while(true){

	    List<String> files = getNewFiles();
	    
	    for(String f:files){

		System.out.println("moving file "+ f);
		File src = new File(inDir+"/" +f); 

		
		load(parentCategoryId,src);
		
		boolean success = src.renameTo(new File(processedDir, f));

		System.out.println("success = "+ success);

	    }
	    
	    
	    try {
		
		Thread.currentThread().sleep(5000L);
				
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	    
	}
	
	
	
    }
    
    
    List getNewFiles(){
	
	List<String> newFiles = new ArrayList();
	
	  File dir = new File(inDir);
	    
	    FileFilter fileFilter = new FileFilter() {
	        public boolean accept(File file) {
	            boolean isDir = file.isDirectory();
	            long age = System.currentTimeMillis() - file.lastModified();   	                       	            
	            return !isDir&&age > 2000L;
	        }
	    };
	   File[] files = dir.listFiles(fileFilter);
    
	    for(int i=0;i<10&&i<files.length;i++){
		System.out.println("file "+files[i].getAbsolutePath());
		newFiles.add(files[i].getName());
	    }
	    return newFiles;	
    }
    
    
    
    
    
    public void load(int parentCategoryId,File file){
	 
	try {
	    BufferedReader reader = new BufferedReader(new FileReader(file));
	    String line = reader.readLine();
	    		    
	    while ((line = reader.readLine()) != null) {

		StringTokenizer st = new StringTokenizer(line,",");

		String oid = st.nextToken();
		String title = st.nextToken();
		String imageUrl = st.nextToken();

		
		Product product = new Product();
		
		product.setOid(oid);
		product.setParentCategoryId(parentCategoryId);
		product.setCreatedDate(new Date());
		product.setUrl(imageUrl);
		
		dao.saveProduct(product);
					
		hitManager.createHIT(product, 1);
		
		
	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	
    }

    
    
    
    
    
    
  
}
