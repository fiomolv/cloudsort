package com.statera.cloudsort.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Proxy;



@Entity
@Proxy(lazy=false)
public class Product implements Serializable {
    
    private Integer id;
    private String oid;
    private String productUrl;
    private String imageUrl;
    private String title;
    private Integer parentCategoryId;
    private String categoryCode;
    private Date createdDate;
    private Date modifiedDate;

    private List<Suggestion> suggestions = new ArrayList<Suggestion>();
 
    @Column(unique=true)    
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)   
    public Integer getId() {
	return id;
    }

    protected void setId(Integer id) {
	this.id = id;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic(fetch = FetchType.EAGER)
    public Integer getParentCategoryId() {
	return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
	this.parentCategoryId = parentCategoryId;
    }
    
    @Basic(fetch = FetchType.EAGER)
    public String getCategoryCode() {
	return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
	this.categoryCode = categoryCode;
    }

    public Date getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
	return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
	this.modifiedDate = modifiedDate;
    }
    
    
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "ProductSuggestions",
            joinColumns = {@JoinColumn(name = "productId")},
            inverseJoinColumns = {@JoinColumn(name = "suggestionId")})
    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> newValue) {
        this.suggestions = newValue;
    }   
  

    public boolean equals(Object obj) {
	return EqualsBuilder.reflectionEquals(this, obj);
    }

    public int hashCode() {
	return HashCodeBuilder.reflectionHashCode(this);
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this);
    }
    
    public void addSuggestion(String categoryCode,String categoryName){
	Suggestion s = new Suggestion(categoryCode,categoryName);
	suggestions.add(s);
    }
    

}
