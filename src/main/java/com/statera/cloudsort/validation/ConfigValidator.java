package com.statera.cloudsort.validation;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import com.statera.cloudsort.entity.Config;
import com.statera.cloudsort.entity.Qualification;


public class ConfigValidator {

	public void validate(Config config, Errors errors) {
	    /*
		String name = qualification.getName();
		if (!StringUtils.hasLength(name)) {
			errors.rejectValue("name", "required", "required");
		}		
		*/	
	}

}
