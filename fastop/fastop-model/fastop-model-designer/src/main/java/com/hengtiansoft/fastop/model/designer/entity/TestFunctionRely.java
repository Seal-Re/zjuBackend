package com.hengtiansoft.fastop.model.designer.entity;

import java.util.Date;

public class TestFunctionRely {
    private Integer testFunctionRelyId;

    private Integer suiteId;

    private Integer testFunctionId;

    private Integer relyFunctionId;

    private Boolean relyFuntionReady;

    private Boolean deleted;

    private Date createdAt;

    private Date updatedAt;

    private String createdBy;

    private String updatedBy;

    public Integer getTestFunctionRelyId() {
        return testFunctionRelyId;
    }

    public void setTestFunctionRelyId(Integer testFunctionRelyId) {
        this.testFunctionRelyId = testFunctionRelyId;
    }

    public Integer getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(Integer suiteId) {
        this.suiteId = suiteId;
    }

    public Integer getTestFunctionId() {
        return testFunctionId;
    }

    public void setTestFunctionId(Integer testFunctionId) {
        this.testFunctionId = testFunctionId;
    }

    public Integer getRelyFunctionId() {
        return relyFunctionId;
    }

    public void setRelyFunctionId(Integer relyFunctionId) {
        this.relyFunctionId = relyFunctionId;
    }

    public Boolean getRelyFuntionReady() {
        return relyFuntionReady;
    }

    public void setRelyFuntionReady(Boolean relyFuntionReady) {
        this.relyFuntionReady = relyFuntionReady;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }
}