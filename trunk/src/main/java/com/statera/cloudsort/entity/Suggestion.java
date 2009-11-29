package com.statera.cloudsort.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Suggestion {

    private Integer id;
    private String categoryCode;
    private String categoryName;
    
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Suggestion() {
    }    
    public Suggestion(String categoryCode,String categoryName) {
	this.categoryCode = categoryCode;
	this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
	return id;
    }

    protected void setId(Integer id) {
	this.id = id;
    }    
    
    
    
}
