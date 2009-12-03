package com.statera.cloudsort.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.statera.cloudsort.service.ProductManager;

/**
 * Annotation-driven <em>MultiActionController</em> that handles all non-form
 * URL's.
 */
@Controller
public class CloudsortController {

    private ProductManager productManager = null;

    static Logger log = Logger.getLogger("CloudsortController");

    @Autowired
    public void setProductManager(ProductManager productManager) {
	this.productManager = productManager;
    }

    /*
     * private TurkDAO dao = null;
     * 
     * @Autowired public void setTurkDAO(TurkDAO turkDAO) { this.dao = turkDAO;
     * }
     */

    /**
     * Custom handler for the welcome view.
     * <p>
     * Note that this handler relies on the RequestToViewNameTranslator to
     * determine the logical view name based on the request URL: "/welcome.do"
     * -&gt; "welcome".
     */
    @RequestMapping("/welcome.do")
    public void welcomeHandler(Model model) {

    }

    @RequestMapping("/login")
    public void login(Model model) {

    }

    

    
    @RequestMapping("/hit.do")

    public ModelMap handleGet(HttpServletRequest req, HttpServletResponse res) {
	
	String id = req.getParameter("id");
	String tier = req.getParameter("tier");
	
	ModelMap model = new ModelMap();

	model.addAttribute("productId", new Integer(id));

	try {
	    model.addAttribute("categories", productManager
		    .getDetailedCategories());
	    model.addAttribute("product", productManager.getProduct(Integer
		    .parseInt(id)));

	    if ("2".equals(tier)) {

		log.info("looking for tier 1 answers");
		    model.addAttribute("tierOneAnswers", productManager.getTierOneAnswersCategories(Integer
			    .parseInt(id)));
		

	    }

	} catch (org.hibernate.ObjectNotFoundException e){
		
		log.info("ObjectNotFoundException: can't find request productId: " + id);
	} catch (DataAccessException e) {
	    log.info("DataAccessException: can't find request productId: " + id);
	}

	return model;

    }    
    
    

}
