package com.satera.cloudsort.dao;

import java.util.Collection;
import java.util.List;

import com.amazonaws.mturk.requester.Assignment;
import com.satera.cloudsort.entity.Response;
import com.satera.cloudsort.entity.Category;
import com.satera.cloudsort.entity.Request;
import com.satera.cloudsort.entity.Product;

public interface TurkDAO {

    /**
     * Get a Product given the id
     * 
     * @param id
     */
    public Product getProductById(Integer id);

    /**
     * Save a record
     * 
     * @param record
     */
    public Product saveProduct(Product product);

    /**
     * Save a request
     * 
     * @param request
     */
    public Request saveRequest(Request request);

    /**
     * Save a response
     * 
     * @param response
     */
    public Response saveResponse(Response response);

    /**
     * Get a Hit given the id
     * 
     * @param id
     * @return
     */
    public Request getRequestById(Integer id);

    /**
     * Get a Hit given the id
     * 
     * @param id
     * @return
     */
    public Request getRequestByHitId(String hitId);

    /**
     * Save a Aategory Object
     */
    public Category saveCategory(Category category);

    /**
     * Get a Category given the id
     * 
     * @param id
     */
    public Category getCategoryById(Integer id);

    /**
     * Get a Category given the categoryCode
     * 
     * @param id
     */
    public Category getCategoryByCategoryCode(String categoryCode);

    /**
     * Get a Category given the categoryCode
     * 
     * @param id
     */
    public Category getCategoryByName(String name);

    /**
     * Get a top level categories
     */
    public List<Category> getTopLevelCategories();

    /**
     * Get categories
     */
    public List<Category> getCategories(String topLevelCategoryName);

    public Response getResponseByAssignmentId(String assignmentId);

    public List<Response> getTierOneResponsesForTierTwoResponseId(Integer id);

    public Request getRequestByProductIdAndTier(int productId, int i);

    public List<Response> getResponsesByRequestId(
	    Integer id);

}
