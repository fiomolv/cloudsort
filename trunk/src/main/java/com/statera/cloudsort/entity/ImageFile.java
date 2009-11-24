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
public class ImageFile implements Serializable
{
  private static final long serialVersionUID = 5736455873645418556L;
  private Integer id;
  private String filename;

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
  
  
  public String getFilename() {
    return filename;
}

public void setFilename(String filename) {
    this.filename = filename;
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
