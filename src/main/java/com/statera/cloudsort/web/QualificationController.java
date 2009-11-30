package com.statera.cloudsort.web;

import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.statera.cloudsort.entity.Qualification;
import com.statera.cloudsort.service.QualificationManager;
import com.statera.cloudsort.validation.QualificationValidator;

/**
 * Controller to manage qualifications
 * 
 * @author David Knoernschild
 */
@Controller
@RequestMapping("/qualification/*")
public class QualificationController {

    private static Logger logger = Logger.getLogger("QualificationController");

    private QualificationManager manager;

    @Autowired
    public void setQualificationManager(QualificationManager qualificationManager) {
	this.manager = qualificationManager;
    }

    /**
     * list all qualifications
     */
    @RequestMapping("/qualification/list")
    public ModelAndView list() {

	Collection qualifications = manager.getQualifications();
	
	ModelAndView mav = new ModelAndView("qualifications");
	mav.addObject("qualifications",qualifications);

	return mav;
    }


   //@RequestMapping("/qualification/edit")
    @RequestMapping(method = RequestMethod.GET)
    public String view(@RequestParam("categoryId") String categoryId,
	    Model model) {

	Qualification qualification = manager.getQualificationForCategoryId(Integer.parseInt(categoryId));
	model.addAttribute("qualification", qualification);
	return "qualificationForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("qualification") Qualification qualification,
	    BindingResult result, SessionStatus status) {
	new QualificationValidator().validate(qualification, result);
	if (result.hasErrors()) {
	    
	    logger.info("has error " +result.getAllErrors());
	    return "qualificationForm";
	} else {
	    
	    Qualification stored = manager.getQualificationForCategoryId(qualification.getCategoryId());
	    stored.setQualTypeIdGeneral(qualification.getQualTypeIdGeneral());
	    stored.setQualTypeScoreGeneral(qualification.getQualTypeScoreGeneral());
	    stored.setQualTypeIdTrusted(qualification.getQualTypeIdTrusted());
	    stored.setQualTypeScoreTrusted(qualification.getQualTypeScoreTrusted());
	    	       
	    manager.saveQualification(stored);
	    status.setComplete();
	    return "redirect:/main/qualification/list";
	}
    }


}
