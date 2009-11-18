package com.satera.cloudsort.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class Category implements Serializable
{
  private static final long serialVersionUID = -4567652345676758432L;
  private Integer id;
  private String name;
  private Integer parentID;
  private String categoryCode;

  public  Integer getParentID() {
    return parentID;
}

public void setParentID(Integer parentID) {
    this.parentID = parentID;
}

public String getCategoryCode() {
    return categoryCode;
}

public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
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
  

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
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
