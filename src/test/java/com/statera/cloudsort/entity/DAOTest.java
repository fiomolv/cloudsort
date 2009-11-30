package com.statera.cloudsort.entity;

import java.io.File;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.statera.cloudsort.DirWatcher;
import com.statera.cloudsort.dao.TurkDAO;
import com.statera.cloudsort.service.CategoryLoader;
import com.statera.cloudsort.service.HITManager;
import com.statera.cloudsort.service.Setup;

public class DAOTest extends TestCase
{
    private TurkDAO turkDAO;
    private Setup setup;
  
  public void setUp() throws Exception
  {
    super.setUp();
    ApplicationContext context = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/spring.xml");
    turkDAO = (TurkDAO)context.getBean("turkDAO");
    setup = (Setup)context.getBean("setup");
    setup.execute();
      

  }

  /**
   * Simple tests excersing the various methods of turkDAO
   */
  public void test()
  {
      
      String filename = "src/test/data/test.csv";
      //String filename = "src/test/data/appliances-11-18-09.csv";
      
      Category category = turkDAO.getCategoryByName("Appliances");
      
      DirWatcher dirWatcher = new DirWatcher("/tmp","/tmp/out",category.getId(),turkDAO,new HITManager());

      
	
      dirWatcher.load(category.getId(), new File(filename),false);
	  
	  
      //Product product = new com.statera.cloudsort.entity.Product();
     
    
      List<Category> topLevelCategories = turkDAO.getTopLevelCategories();
      
      assertNotNull(topLevelCategories);
      assertEquals(18,topLevelCategories.size());
      
      
      Qualification qualification = new Qualification();
      
      Integer categoryId = 10023;
      qualification.setCategoryId(categoryId);
      qualification.setQualTypeIdGeneral("788357836");
      qualification.setQualTypeIdTrusted("387366379");
      
      qualification.setQualTypeScoreGeneral(50);
      qualification.setQualTypeScoreTrusted(75);
      
      turkDAO.saveQualification(qualification);
      
      Qualification qualification2 = turkDAO.getQualificationForCategoryId(categoryId);
      
      assertNotNull(qualification2);
      assertEquals(new Integer(75),qualification2.getQualTypeScoreTrusted());
   
      /*
      Config config = new Config();
      config.setName("host");
      config.setValue("localhost");
      
      turkDAO.saveConfig(config);
      
      String lookupValue = turkDAO.getConfigValue("host");
      
      assertNotNull(lookupValue);
      assertEquals(config.getValue(),lookupValue);
      
      */
      
      
      
      
  }
  
}