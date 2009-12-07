package com.statera.cloudsort.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


@Entity
public class Error implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    private Integer id;
    private Date createdDate;
    private String description;
    private String message;



    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
	return id;
    }

    protected void setId(Integer id) {
	this.id = id;
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

    

}
