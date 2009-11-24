package com.statera.cloudsort.listener;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.statera.cloudsort.dao.TurkDAO;
import com.statera.cloudsort.entity.Category;

public class CategoryServlet extends HttpServlet {

    static Logger log = Logger.getLogger("CategoryServlet");
   
    public void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	doPost(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	
	String type = request.getParameter("type");
	
	ApplicationContext ctx = WebApplicationContextUtils
	.getWebApplicationContext(this.getServletConfig()
			.getServletContext());
	
	TurkDAO dao = (TurkDAO) ctx.getBean("turkDAO");
	
	
	
	JSONObject jsonObject = new JSONObject();
	
	try {
	    jsonObject.put("identifier", "categoryCode");
		jsonObject.put("label", "name");
		
				
		JSONArray array = new JSONArray();
				
		Integer parentCategoryId = 0;
				
		try{
		    parentCategoryId = (Integer.parseInt(type));
		}catch(Exception ignore){}
		
		
		List<Category> categories = dao.getCategories(parentCategoryId);
		
		for(Category category:categories){
		    		
		JSONObject entry = new JSONObject();
		entry.put("name",category.getName());
		entry.put("categoryCode",category.getCategoryCode());
		array.put(entry);
		
		}
		
		jsonObject.put("items",array);
		
		
	} catch (JSONException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	
	
	//response.setContentType("text/xml");
	response.getWriter().print(jsonObject);
    }  
}
