package com.statera.cloudsort;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class DirWatcher implements Runnable {

    private static final String inputDir = "/opt/cloudsort/in";
    public void run() {
	
	while(true){
	    System.out.println("looking for new images");
	    
	    
	    getNewFiles();
	    try {
		
		Thread.currentThread().sleep(5000L);
				
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	    
	}
	
	
	
    }
    
    
    List getNewFiles(){
	
	List newFiles = new ArrayList();
	
	  File dir = new File(inputDir);
	    
	    FileFilter fileFilter = new FileFilter() {
	        public boolean accept(File file) {
	            String lower = file.getName().toLowerCase();
	            System.out.println("Considering "+ lower);
	            boolean isImage = lower.endsWith(".jpg")||lower.endsWith(".gif")||lower.endsWith(".png");
	            boolean isDir = file.isDirectory();
	            long age = System.currentTimeMillis() - file.lastModified();   
	            
	            

	            
	            
	            return !isDir&&isImage&&age > 2000L;
	        }
	    };
	   File[] files = dir.listFiles(fileFilter);
	   // File[] files = dir.listFiles();
	    
	    System.out.println("file count "+files.length );
	    	    
	    for(int i=0;i<10&&i<files.length;i++){
		System.out.println("file "+files[i].getAbsolutePath());
		newFiles.add(files[i].getAbsolutePath());
	    }

	    return newFiles;	
    }
    
}
