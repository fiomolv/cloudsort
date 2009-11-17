package com.satera.cloudsort;

import java.util.Collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.zabada.springrecipes.hibernatejpa.dao.MusicDAO;
import com.zabada.springrecipes.hibernatejpa.entity.Artist;
import com.zabada.springrecipes.hibernatejpa.entity.ImageFile;

public class CloudSort {

    public static void main(String[] args){
	
	
	System.out.println("CloudSort Version 0.9.01");
	
	
	    ApplicationContext context = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/spring.xml");

	    MusicDAO musicDAO = (MusicDAO)context.getBean("musicDAO");
	    
	    musicDAO = (MusicDAO)context.getBean("musicDAO");
	   ImageFile imageFile = new ImageFile();
	   imageFile.setFilename("DUDER.gif");
	   musicDAO.saveImageFile(imageFile);
	DirWatcher dirWatcher = new DirWatcher();
	dirWatcher.run();
    }
}
