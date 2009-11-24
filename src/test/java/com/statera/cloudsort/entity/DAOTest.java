package com.satera.cloudsort.entity;


import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.satera.cloudsort.dao.TurkDAO;
import com.satera.cloudsort.service.CategoryLoader;

public class DAOTest extends TestCase
{
  private TurkDAO turkDAO;
  
  public void setUp() throws Exception
  {
    super.setUp();
    ApplicationContext context = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/spring.xml");
    turkDAO = (TurkDAO)context.getBean("turkDAO");
    
    
    CategoryLoader categoryLoader = (CategoryLoader)context.getBean("categoryLoader");
    categoryLoader.load();
  }

  /**
   * Simple tests excersing the various methods of turkDAO
   */
  public void test()
  {
      
      String filename = "349587345834.jpg";
      Product product = new com.satera.cloudsort.entity.Product();
     
      
      product.setCategoryCode("10531");
      product.setParentCategoryId(1);
      
      
      turkDAO.saveProduct(product);
      assertNotNull(product.getId()); 
      
      assertEquals("10531",product.getCategoryCode());
      
      
      
      Product product2 = turkDAO.getProductById(product.getId());
      assertEquals("10531",product2.getCategoryCode());
      
      
    
      List<Category> topLevelCategories = turkDAO.getTopLevelCategories();
      
      assertNotNull(topLevelCategories);
      assertEquals(18,topLevelCategories.size());
    
    
  }
  
}