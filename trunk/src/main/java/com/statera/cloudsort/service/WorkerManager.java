package com.statera.cloudsort.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.statera.cloudsort.dao.TurkDAO;

public class WorkerManager {
   
    private TurkDAO dao = null;

    @Autowired
    public void setTurkDAO(TurkDAO turkDAO) {
	this.dao = turkDAO;
    }


    /**
     * Returns a worker's score in a given parent category (Appliances, etc).  Calculates percentage of 
     * Response.RESULT_APPROVED / (Response.RESULT_APPROVED+Response.RESULT_REJECTED)
     * @param workerId
     * @param parentCategoryId
     * @return
     */
    public double getScore(String workerId,Integer parentCategoryId){
	// TODO: query DB, calculate current score
	return -1.0;
    }


}
