package com.statera.cloudsort.service;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.Ostermiller.util.CSVParser;
import com.statera.cloudsort.dao.TurkDAO;
import com.statera.cloudsort.entity.Category;
import com.statera.cloudsort.entity.Product;
import org.apache.log4j.Logger;

public class DirWatcher extends Thread {

    static Logger log = Logger.getLogger("DirWatcher");
    private String inDir;
    private String processedDir;
    private TurkDAO dao;
    private HITManager hitManager;
    private Category category;

    public DirWatcher(String inDir, String processedDir, Category category,
	    TurkDAO dao, HITManager hitManager) {
	this.inDir = inDir;
	this.processedDir = processedDir;
	this.dao = dao;
	this.hitManager = hitManager;
	this.category = category;
    }

    public void run() {

	while (true) {

	    List<String> files = getNewFiles();

	    for (String f : files) {

		log.info("moving " + category.getName() + " file " + f);
		File src = new File(inDir + "/" + f);

		load(category, src, true);

		boolean success = src.renameTo(new File(processedDir, f));

		log.info("file " + src.getName() + " moved successfully: "
			+ success);

	    }

	    try {

		Thread.sleep(5000L);

	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	}

    }

    List getNewFiles() {

	List<String> newFiles = new ArrayList();

	File dir = new File(inDir);

	FileFilter fileFilter = new FileFilter() {
	    public boolean accept(File file) {
		boolean isDir = file.isDirectory();
		long age = System.currentTimeMillis() - file.lastModified();
		return !isDir && age > 2000L;
	    }
	};
	File[] files = dir.listFiles(fileFilter);

	for (int i = 0; i < 10 && i < files.length; i++) {
	    //System.out.println("file " + files[i].getAbsolutePath());
	    newFiles.add(files[i].getName());
	}
	return newFiles;
    }

    public void load(Category category, File file, boolean createHITs) {

	try {
	    CSVParser csvParser = new CSVParser(new FileInputStream(file));

	    String[] nextLine = csvParser.getLine(); // skip first line
	    while ((nextLine = csvParser.getLine()) != null) {
		// nextLine[] is an array of values from the line

		String oid = nextLine[0];
		String title = nextLine[1];
		String imageUrl = nextLine[2];
		String productUrl = nextLine[3];

		if (title.length() > 255)
		    title = title.substring(0, 255);
		if (imageUrl.length() > 255)
		    imageUrl = imageUrl.substring(0, 255);
		if (productUrl.length() > 255)
		    productUrl = productUrl.substring(0, 255);

		Product product = new Product();

		product.setOid(oid);
		product.setParentCategoryId(category.getId());
		product.setTitle(title);
		product.setCreatedDate(new Date());
		product.setImageUrl(imageUrl);
		product.setProductUrl(productUrl);

		// for(int i=0;i<6;i++){
		for (int i = 0; i < (nextLine.length - 4) / 2; i++) {

		    String categoryCode = nextLine[4 + 2 * i];

		    if (categoryCode.length() > 255)
			categoryCode = categoryCode.substring(0, 255);

		    String categoryName = nextLine[5 + 2 * i];

		    if (categoryName.length() > 255)
			categoryName = categoryName.substring(0, 255);

		    product.addSuggestion(categoryCode, categoryName);
		}

		try {
		    dao.saveProduct(product);

		    if (createHITs)
			hitManager.createHIT(product, 1);
		} catch (DataAccessException e) {
		    log.error("problem with product entry OID " + product.getOid()+ ": " + e.getMessage());
		}
	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

}
