package com.satera.cloudsort.entity;

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
public class Request implements Serializable
{
  private static final long serialVersionUID = -4567543344323545677L;
  private Integer id;

  
  private String hitId;
  private int parentCategoryId;
  private Date createdDate;
  private int productId;
  public int getProductId() {
    return productId;
}

public void setProductId(int productId) {
    this.productId = productId;
}

public String getHitId() {
    return hitId;
}

public void setHitId(String hitId) {
    this.hitId = hitId;
}



public int getParentCategoryId() {
    return parentCategoryId;
}

public void setParentCategoryId(int parentCategoryId) {
    this.parentCategoryId = parentCategoryId;
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

private Date modifiedDate;
  private int tier;

  public int getTier() {
    return tier;
}

public void setTier(int tier) {
    this.tier = tier;
}


@Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  public Integer getId()
  {
    return id;
  }
  
  protected void setId(Integer id)
  {
    this.id = id;
  }
  

  public boolean equals(Object obj)
  {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  public int hashCode()
  {
    return HashCodeBuilder.reflectionHashCode(this);
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }
}
