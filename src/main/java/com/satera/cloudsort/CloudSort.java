package com.satera.cloudsort;

import java.util.Collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.satera.cloudsort.dao.TurkDAO;
import com.satera.cloudsort.entity.Artist;
import com.satera.cloudsort.entity.ImageFile;

public class CloudSort {

    public static void main(String[] args) {

	System.out.println("CloudSort Version 0.9.01");

	ApplicationContext context = new FileSystemXmlApplicationContext(
		"src/main/webapp/WEB-INF/spring.xml");

	TurkDAO dao = (TurkDAO) context.getBean("turkDAO");
	
	dao = (TurkDAO) context.getBean("turkDAO");
	ImageFile imageFile = new ImageFile();
	imageFile.setFilename("DUDER.gif");
	dao.saveImageFile(imageFile);
	DirWatcher dirWatcher = new DirWatcher();
	dirWatcher.run();
    }
}
