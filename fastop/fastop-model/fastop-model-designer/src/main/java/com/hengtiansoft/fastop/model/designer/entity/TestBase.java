package com.hengtiansoft.fastop.model.designer.entity;

import java.util.Date;

public class TestBase {
    private Integer id;

    private String name;

    private Integer entityStructId;

    private Integer funGroupId;

    private Integer baseType;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getEntityStructId() {
        return entityStructId;
    }

    public void setEntityStructId(Integer entityStructId) {
        this.entityStructId = entityStructId;
    }

    public Integer getFunGroupId() {
        return funGroupId;
    }

    public void setFunGroupId(Integer funGroupId) {
        this.funGroupId = funGroupId;
    }

    public Integer getBaseType() {
        return baseType;
    }

    public void setBaseType(Integer baseType) {
        this.baseType = baseType;
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