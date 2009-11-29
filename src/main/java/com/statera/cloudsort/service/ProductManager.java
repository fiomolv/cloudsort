package com.statera.cloudsort.service;

import java.util.List;

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
	product = dao.getProductById(productId);
	return product;
    }

    public List<Category> getDetailedCategories(){
	return dao.getDetailedCategories();
    }
    
}
