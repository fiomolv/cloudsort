package com.statera.cloudsort;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.statera.cloudsort.dao.TurkDAO;
import com.statera.cloudsort.service.CategoryLoader;

public class CloudSort {

    public static void main(String[] args) {

	System.out.println("CloudSort Version 0.9.01");

	ApplicationContext context = new FileSystemXmlApplicationContext(
		"src/main/webapp/WEB-INF/spring.xml");
	
	CategoryLoader categoryLoader = (CategoryLoader) context.getBean("categoryLoader");
	categoryLoader.load();
	
	TurkDAO dao = (TurkDAO) context.getBean("turkDAO");
	
	Ingestor ingester = (Ingestor)context.getBean("ingestor");
	ingester.init();
	
	//dao = (TurkDAO) context.getBean("turkDAO");
	//ImageFile imageFile = new ImageFile();
	//imageFile.setFilename("DUDER.gif");
	//dao.saveImageFile(imageFile);
    }
}
