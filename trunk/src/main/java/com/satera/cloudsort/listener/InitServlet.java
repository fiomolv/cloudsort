package com.satera.cloudsort.listener;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.satera.cloudsort.Ingestor;
import com.satera.cloudsort.dao.TurkDAO;
import com.satera.cloudsort.entity.Category;
import com.satera.cloudsort.service.CategoryLoader;

public class InitServlet extends HttpServlet {

    static Logger log = Logger.getLogger("InitServlet");
    
  

    public void init(ServletConfig config) throws ServletException
    {
               super.init(config);
               
       	ApplicationContext context = WebApplicationContextUtils
	.getWebApplicationContext(this.getServletConfig()
			.getServletContext());
	               
       	log.info("loading categories");
       	
       	CategoryLoader categoryLoader = (CategoryLoader) context.getBean("categoryLoader");
	categoryLoader.load();
	
	TurkDAO dao = (TurkDAO) context.getBean("turkDAO");
       	log.info("starting ingestor");
	Ingestor ingester = (Ingestor)context.getBean("ingestor");
	ingester.init();
    }
  

    

	
}
