package com.statera.cloudsort.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.statera.cloudsort.dao.TurkDAO;
import com.statera.cloudsort.entity.Qualification;

public class QualificationManager {
       
    private TurkDAO dao = null;

    @Autowired
    public void setTurkDAO(TurkDAO turkDAO) {
	this.dao = turkDAO;
    }

    public Qualification getQualificationForCategoryId(Integer categoryId){
	Qualification qualification = null;
	qualification = dao.getQualificationForCategoryId(categoryId);
	return qualification;
    }

    public List<Qualification> getQualifications(){
	return dao.getQualifications();
    }
    
    public Qualification saveQualification(Qualification qualification){
	return dao.saveQualification(qualification);
    }  
}
