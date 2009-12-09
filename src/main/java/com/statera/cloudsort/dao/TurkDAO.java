package com.statera.cloudsort.dao;

import java.util.List;
import java.util.Properties;

import com.statera.cloudsort.entity.Category;
import com.statera.cloudsort.entity.Config;
import com.statera.cloudsort.entity.Product;
import com.statera.cloudsort.entity.Qualification;
import com.statera.cloudsort.entity.Request;
import com.statera.cloudsort.entity.Response;


public interface TurkDAO {

    public Product getProductById(Integer id);

    public Product saveProduct(Product product);

    public Request saveRequest(Request request);

    public Response saveResponse(Response response);

    public Request getRequestById(Integer id);

    public Request getRequestByHitId(String hitId);

    public Category saveCategory(Category category);

    public Category getCategoryById(Integer id);

    public Category getCategoryByCategoryCode(String categoryCode);

    public Category getCategoryByName(String name);

    public List<Category> getTopLevelCategories();

    public List<Category> getDetailedCategories();
        
    public List<Category> getCategories(Integer parentId);

    public Response getResponseByAssignmentId(String assignmentId);

    //public List<Response> getTierOneResponsesForTierTwoResponseId(Integer id);

    public Request getRequestByProductIdAndTier(int productId, int i);

    public List<Response> getResponsesByRequestId(Integer id);

    public Properties getConfig();
    
    public Config getConfigByName(String name);
    
    public Config getConfig(Integer id);
    
    public Config saveConfig(Config config);
    
    public String getConfigValue(String name);
           
    public Qualification saveQualification(Qualification qualification);
    
    public Qualification getQualificationForCategoryId(Integer parentId);
    
    public List<Qualification> getQualifications();

    public List<Config> getConfigList();
      
    public List<Product> getNewProducts();
    
    public List<String> getTierOneAnswersCategories(Integer producrId);

    public List<Request> getAllRequests();

    public List<Request> getOrphanedRequests();

    public List<Response> getOrphanedResponses();

    public List<Response> getResponsesWithNoResult();
        
    public String getParentCategoryNameForProduct(Integer productId);

}
