package com.hengtiansoft.fastop.model.designer.entity;

import java.util.Date;

public class FunctionSuite {
    private Integer id;

    private Integer testFunId;

    private Integer funNum;

    private Integer funVersion;

    private Integer funOrder;

    private String dependsOn;

    private Integer suiteId;

    private Boolean deleted;

    private Date createdAt;

    private Date updatedAt;

    private String createdBy;

    private String updatedBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTestFunId() {
        return testFunId;
    }

    public void setTestFunId(Integer testFunId) {
        this.testFunId = testFunId;
    }

    public Integer getFunNum() {
        return funNum;
    }

    public void setFunNum(Integer funNum) {
        this.funNum = funNum;
    }

    public Integer getFunVersion() {
        return funVersion;
    }

    public void setFunVersion(Integer funVersion) {
        this.funVersion = funVersion;
    }

    public Integer getFunOrder() {
        return funOrder;
    }

    public void setFunOrder(Integer funOrder) {
        this.funOrder = funOrder;
    }

    public String getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(String dependsOn) {
        this.dependsOn = dependsOn == null ? null : dependsOn.trim();
    }

    public Integer getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(Integer suiteId) {
        this.suiteId = suiteId;
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