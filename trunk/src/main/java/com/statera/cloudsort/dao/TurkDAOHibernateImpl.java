package com.statera.cloudsort.dao;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.statera.cloudsort.entity.Category;
import com.statera.cloudsort.entity.Config;
import com.statera.cloudsort.entity.Product;
import com.statera.cloudsort.entity.Request;
import com.statera.cloudsort.entity.Response;



public class TurkDAOHibernateImpl extends HibernateDaoSupport implements TurkDAO{
    
    static Logger log = Logger.getLogger("TurkDAOHibernateImpl");
    public Product saveProduct(Product product) {
	getHibernateTemplate().saveOrUpdate(product);
	return product;
    }

    public Category saveCategory(Category category) {
	getHibernateTemplate().saveOrUpdate(category);
	return category;
    }
       
    public Category getCategoryById(Integer id) {
	Category category = (Category) getHibernateTemplate().load(Category.class, id);
	return category;
    }   
    
    public Category getCategoryByName(String name) {
	Category category = null;
	List<Category> list = getHibernateTemplate().find("from Category where name=?", name);  
	if(list!=null&&list.size()>0){
	    category = list.get(0);
	}
	return category;
    } 
        
    public Category getCategoryByCategoryCode (String categoryCode) {
	Category category = null;
	List<Category> list = getHibernateTemplate().find("from Category where categoryCode=?", categoryCode);  
	if(list!=null&&list.size()>0){
	    category = list.get(0);
	}
	return category;
    }
    
    public List<Category> getTopLevelCategories() {
	List<Category> list = getHibernateTemplate().find("from Category where parentID is null");  
	return list;
    }

    public List<Category> getDetailedCategories() {
	List<Category> list = getHibernateTemplate().find("from Category where parentID is not null");  
	return list;
    }
    
    
    public List<Category> getCategories(Integer parentId) {
	List<Category> list = getHibernateTemplate().find("from Category where parentID =?",parentId);  
	return list;
    }

    public Response getResponseByAssignmentId(String assignmentId) {
	Response response = null;
	List<Response> list = getHibernateTemplate().find("from Response where assignmentId=?", assignmentId);  
	if(list!=null&&list.size()>0){
	    response = list.get(0);
	}
	return response;

    }   
      
    public Request getRequestById(Integer id) {
	Request request = (Request) getHibernateTemplate().load(Request.class, id);
	System.out.println("Got request: " + request);
	return request;
    }

    public Request getRequestByHitId(String hitId) {
	Request request = null;
	List<Request> list = getHibernateTemplate().find("from Request where hitId=?", hitId);  
	if(list!=null&&list.size()>0){
	    request = list.get(0);
	}
	return request;
    }

    public Request saveRequest(Request request) {
	getHibernateTemplate().saveOrUpdate(request);
	return request;

    }

    public Product getProductById(Integer id) {
	log.info("loading product for id " + id);
	Product product = (Product) getHibernateTemplate().load(Product.class, id);
	return product;
    }

    public Response saveResponse(Response response) {
	getHibernateTemplate().saveOrUpdate(response);
	return response;
    }

    public List<Response> getTierOneResponsesForTierTwoResponseId(Integer id) {
	List<Response> list = getHibernateTemplate().find("from Response where hitId in (select hitId from Request where tier=1 and selectCategory where parentID in (select id from Category where name=?) order by name",id);  
	return list;
    }

    public Request getRequestByProductIdAndTier(int productId, int tier) {
	Request request = null;
	List<Request> list = getHibernateTemplate().find("from Request where productId=? and tier=?",new Object[]{productId,tier});  
	if(list!=null&&list.size()>0){
	    request = list.get(0);
	}
	return request;
    }

    public List<Response> getResponsesByRequestId(Integer requestId) {
	List<Response> list = getHibernateTemplate().find("from Response where requestId=?",requestId);  
	return list;
    }

    public Properties getConfig() {
	List<Config> list = getHibernateTemplate().find("from Config"); 
	
	Properties props = new Properties();
	for(Config config:list){
	    props.setProperty(config.getName(), config.getName());
	}
	return props;
    }



}