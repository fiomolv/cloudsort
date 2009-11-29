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
public class Qualification implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    private Integer id;


    
    private Integer categoryId;
    private String qualTypeIdGeneral;
    private Double qualTypeScoreGeneral;
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getQualTypeIdGeneral() {
        return qualTypeIdGeneral;
    }

    public void setQualTypeIdGeneral(String qualTypeIdGeneral) {
        this.qualTypeIdGeneral = qualTypeIdGeneral;
    }

    public Double getQualTypeScoreGeneral() {
        return qualTypeScoreGeneral;
    }

    public void setQualTypeScoreGeneral(Double qualTypeScoreGeneral) {
        this.qualTypeScoreGeneral = qualTypeScoreGeneral;
    }

    public String getQualTypeIdTrusted() {
        return qualTypeIdTrusted;
    }

    public void setQualTypeIdTrusted(String qualTypeIdTrusted) {
        this.qualTypeIdTrusted = qualTypeIdTrusted;
    }

    public Double getQualTypeScoreTruste() {
        return qualTypeScoreTruste;
    }

    public void setQualTypeScoreTruste(Double qualTypeScoreTruste) {
        this.qualTypeScoreTruste = qualTypeScoreTruste;
    }

    private String qualTypeIdTrusted;
    private Double qualTypeScoreTruste;

 

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
