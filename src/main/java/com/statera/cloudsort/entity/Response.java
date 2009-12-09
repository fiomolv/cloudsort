package com.statera.cloudsort.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class Response implements Serializable
{

  private Integer id;
  private String hitId;
  private String answer;
  private String workerId;
  private String result;
  private String assignmentId;
  private Integer requestId;
  public Integer getRequestId() {
    return requestId;
}

public void setRequestId(Integer requestId) {
    this.requestId = requestId;
}

@Column(unique=true)
public String getAssignmentId() {
    return assignmentId;
}

public void setAssignmentId(String assignmentId) {
    this.assignmentId = assignmentId;
}

private Date createdDate;
  
  
  
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
  public Integer getId() {
    return id;
}

public void setId(Integer id) {
    this.id = id;
}



public String getHitId() {
    return hitId;
}

public void setHitId(String hitId) {
    this.hitId = hitId;
}

@Basic(fetch = FetchType.EAGER)
public String getAnswer() {
    return answer;
}

public void setAnswer(String answer) {
    this.answer = answer;
}

public String getWorkerId() {
    return workerId;
}

public void setWorkerId(String workerId) {
    this.workerId = workerId;
}

public String getResult() {
    return result;
}

public void setResult(String result) {
    this.result = result;
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
