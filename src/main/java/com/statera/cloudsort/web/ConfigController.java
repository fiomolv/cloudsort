package com.statera.cloudsort.web;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.statera.cloudsort.entity.Config;
import com.statera.cloudsort.service.ConfigManager;
import com.statera.cloudsort.validation.ConfigValidator;

/**
 * Controller to manage qualifications
 * 
 * @author David Knoernschild
 */
@Controller
@RequestMapping("/config/*")
public class ConfigController {

    private static Logger logger = Logger.getLogger("ConfigController");

    private ConfigManager manager;

    @Autowired
    public void setConfigManager(ConfigManager configManager) {
	this.manager = configManager;
    }

    /**
     * list all qualifications
     */
    @RequestMapping("/config/list")
    public ModelAndView list() {

	Collection configs = manager.getConfigs();

	ModelAndView mav = new ModelAndView("configs");
	mav.addObject("configs",configs);

	return mav;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String view(@RequestParam("configName") String configName,
	    Model model) {
	Config config = manager.getConfigForName(configName);
	model.addAttribute("config", config);

	return "configForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("config") Config config,
	    BindingResult result, SessionStatus status) {
	
	logger.info("updating config");
	new ConfigValidator().validate(config, result);
	if (result.hasErrors()) {
	    return "configForm";
	} else {
	    
	    Config stored = manager.getConfigByName(config.getName());
	    stored.setValue(config.getValue());  	    
	    manager.saveConfig(stored);
	    status.setComplete();
	    return "redirect:/main/config/list";
	}
    }
}
