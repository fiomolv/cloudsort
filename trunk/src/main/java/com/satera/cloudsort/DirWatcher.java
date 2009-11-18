package com.satera.cloudsort;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.satera.cloudsort.service.HITManager;

public class DirWatcher extends Thread {

    private String inDir;
    private String processedDir;
       
    public DirWatcher(String inDir,String processedDir){
	this.inDir = inDir;
	this.processedDir = processedDir;
    }
    
    public void run() {
	
	while(true){

	    List<String> files = getNewFiles();
	    
	    for(String f:files){

		System.out.println("moving file "+ f);
		File src = new File(inDir+"/" +f); 

		boolean success = src.renameTo(new File(processedDir, f));

		System.out.println("success = "+ success);
		
		
	        HITManager app = new HITManager();

	        if (app.hasEnoughFund()) {
	          app.createSiteCategoryHITs();
	        }		
		
		
		

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
	            String lower = file.getName().toLowerCase();
	            boolean isImage = lower.endsWith(".jpg")||lower.endsWith(".gif")||lower.endsWith(".png");
	            boolean isDir = file.isDirectory();
	            long age = System.currentTimeMillis() - file.lastModified();   	                       	            
	            return !isDir&&isImage&&age > 2000L;
	        }
	    };
	   File[] files = dir.listFiles(fileFilter);
    
	    for(int i=0;i<10&&i<files.length;i++){
		System.out.println("file "+files[i].getAbsolutePath());
		newFiles.add(files[i].getName());
	    }
	    return newFiles;	
    }
  
}
