package com.statera.cloudsort.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.OneToMany;


import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Proxy;


@Entity
public class Config implements Serializable {
    
    
    public static final String HOST="host";
    public static final String ACCESS_KEY_ID = "accessKeyId";
    public static final String SECRET_ACCESS_KEY = "secretAccessKey";
    public static final String TURK_ENVIRONMENT = "turkEnvironment";
        
    public static final String TIER_1_REWARD = "tier1Reward";
    public static final String TIER_2_REWARD = "tier2Reward";
        
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    private Integer id;
    private String name;
    private String value;

    public Config(){
	
    }
    
    public Config(String name,String value){
	this.name = name;
	this.value = value;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    

}