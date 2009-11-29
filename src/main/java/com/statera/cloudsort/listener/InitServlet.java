package com.statera.cloudsort.listener;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.statera.cloudsort.DirWatcher;
import com.statera.cloudsort.Ingestor;
import com.statera.cloudsort.dao.TurkDAO;
import com.statera.cloudsort.entity.Category;
import com.statera.cloudsort.service.CategoryLoader;
import com.statera.cloudsort.service.HITManager;
import com.statera.cloudsort.service.Setup;

public class InitServlet extends HttpServlet {

    static Logger log = Logger.getLogger("InitServlet");

    public void init(ServletConfig config) throws ServletException {
	super.init(config);

	ApplicationContext context = WebApplicationContextUtils
		.getWebApplicationContext(this.getServletConfig()
			.getServletContext());

	
	
	Setup setup = (Setup)context.getBean("setup");
	
	setup.execute();
	
	TurkDAO dao = (TurkDAO) context.getBean("turkDAO");
	log.info("starting ingestor");
	Ingestor ingester = (Ingestor) context.getBean("ingestor");
	ingester.init();		

    }

}
