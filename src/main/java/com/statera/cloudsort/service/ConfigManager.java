package com.statera.cloudsort.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.statera.cloudsort.dao.TurkDAO;
import com.statera.cloudsort.entity.Config;

public class ConfigManager {

  
    private TurkDAO dao = null;

    @Autowired
    public void setTurkDAO(TurkDAO turkDAO) {
	this.dao = turkDAO;
    }

    public Config getConfigForName(String name){
	
	Config config = null;
	config = dao.getConfigByName(name);
	return config;
    }
   
    public List<Config> getConfigs(){	
	return dao.getConfigList();	
    }
      
    public Config saveConfig(Config config){
	return dao.saveConfig(config);
    }

    public Config getConfigByName(String name) {
	return dao.getConfigByName(name);
    }    
}
