package com.statera.cloudsort.web;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.statera.cloudsort.service.StatusManager;

/**
 * Controller to manage qualifications
 * 
 * @author David Knoernschild
 */
@Controller
@RequestMapping("/status/*")
public class StatusController {

    private static Logger logger = Logger.getLogger("StatusController");

    private StatusManager manager;

    @Autowired
    public void setStatusManager(StatusManager statusManager) {
	this.manager = statusManager;
    }

    /**
     * list all qualifications
     */
    @RequestMapping("/status/list")
    public ModelAndView list() {

	logger.info("getting system status");
	Collection products = manager.getNewProducts();

	ModelAndView mav = new ModelAndView("statuses");
	mav.addObject("products",products);

	return mav;
    }



}
