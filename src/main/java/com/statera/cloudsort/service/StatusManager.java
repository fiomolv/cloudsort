package com.statera.cloudsort.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.statera.cloudsort.dao.TurkDAO;
import com.statera.cloudsort.entity.Product;

public class StatusManager {

   
    
    private TurkDAO dao = null;

    @Autowired
    public void setTurkDAO(TurkDAO turkDAO) {
	this.dao = turkDAO;
    }


    public List<Product> getNewProducts() {
	return dao.getNewProducts();
    }
    
}
