package com.statera.cloudsort.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class Configuration implements Serializable
{
  private Integer id;
  private String name;
  private String value;

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
  
  public String getValue() {
    return value;
}

public void setValue(String value) {
    this.value = value;
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
