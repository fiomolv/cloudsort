package com.satera.cloudsort.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.satera.cloudsort.entity.Category;
import com.satera.cloudsort.entity.Product;
import com.satera.cloudsort.entity.Request;
import com.satera.cloudsort.entity.Response;


public class TurkDAOHibernateImpl extends HibernateDaoSupport implements TurkDAO{

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
	System.out.println("Got category: " + category);
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

    public List<Category> getCategories(String topLevelCategoryName) {
	List<Category> list = getHibernateTemplate().find("from Category where parentID in (select id from Category where name=?) order by name",topLevelCategoryName);  
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

    public Request getRequestByProductIdAndTier(int productId, int i) {
	// TODO Auto-generated method stub
	return null;
    }

    public List<Response> getResponsesByRequestId(Integer id) {
	// TODO Auto-generated method stub
	return null;
    }

}