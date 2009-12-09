package com.statera.cloudsort.service;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import com.statera.cloudsort.dao.TurkDAO;
import com.statera.cloudsort.entity.Category;
import com.statera.cloudsort.entity.Product;

public class ProductManager {

    
    
    private TurkDAO dao = null;

    @Autowired
    public void setTurkDAO(TurkDAO turkDAO) {
	this.dao = turkDAO;
    }




    public Product getProduct(Integer productId){
	
	Product product = null;
	try{
	product = dao.getProductById(productId);
	}catch(ObjectNotFoundException e){}
	return product;
    }

    public List<Category> getDetailedCategories(){
	return dao.getDetailedCategories();
    }




    public Object getTierOneAnswersCategories(int productId) {
	return dao.getTierOneAnswersCategories(productId);
    }
    
}
