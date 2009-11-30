package com.statera.cloudsort.validation;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import com.statera.cloudsort.entity.Qualification;


public class QualificationValidator {

	public void validate(Qualification qualification, Errors errors) {
	    
		String name = qualification.getName();
		if (!StringUtils.hasLength(name)) {
			errors.rejectValue("name", "required", "required");
		}	
		
		
		
		String qualTypeIdGeneral = qualification.getQualTypeIdGeneral();
		if (!StringUtils.hasLength(qualTypeIdGeneral)) {
			errors.rejectValue("qualTypeIdGeneral", "required", "required");
		}		
		
			
	}

}
