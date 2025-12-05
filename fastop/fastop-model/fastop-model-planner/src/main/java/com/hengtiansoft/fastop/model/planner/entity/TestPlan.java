package com.hengtiansoft.fastop.model.planner.entity;

import java.util.Date;

public class TestPlan {
    private String planId;

    private Integer entityStructId;

    private Integer entityId;

    private Integer subjectId;

    private Integer funGroupId;

    private Integer suiteId;

    private Boolean military;

    private Date planStartTime;

    private Date planEndTime;

    private Date actualStartTime;

    private Date actualEndTime;

    private Integer status;

    private String planNumber;

    private Integer planRound;

    private String planName;

    private Integer areaId;

    private String dispatcherId;

    private String commanderId;

    private String executorGroupId;

    private String commAssign;

    private String executAssign;

    private String verifyAssign;

    private Boolean updatable;

    private Boolean archived;

    private Boolean deleted;

    private Integer baseType;

    private Date createdAt;

    private Date updatedAt;

    private String createdBy;

    private String updatedBy;

    private Boolean sync;

    private String management;

    private Integer forRecordData;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId == null ? null : planId.trim();
    }

    public Integer getEntityStructId() {
        return entityStructId;
    }

    public void setEntityStructId(Integer entityStructId) {
        this.entityStructId = entityStructId;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getFunGroupId() {
        return funGroupId;
    }

    public void setFunGroupId(Integer funGroupId) {
        this.funGroupId = funGroupId;
    }

    public Integer getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(Integer suiteId) {
        this.suiteId = suiteId;
    }

    public Boolean getMilitary() {
        return military;
    }

    public void setMilitary(Boolean military) {
        this.military = military;
    }

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Date getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Date getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(String planNumber) {
        this.planNumber = planNumber == null ? null : planNumber.trim();
    }

    public Integer getPlanRound() {
        return planRound;
    }

    public void setPlanRound(Integer planRound) {
        this.planRound = planRound;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getDispatcherId() {
        return dispatcherId;
    }

    public void setDispatcherId(String dispatcherId) {
        this.dispatcherId = dispatcherId == null ? null : dispatcherId.trim();
    }

    public String getCommanderId() {
        return commanderId;
    }

    public void setCommanderId(String commanderId) {
        this.commanderId = commanderId == null ? null : commanderId.trim();
    }

    public String getExecutorGroupId() {
        return executorGroupId;
    }

    public void setExecutorGroupId(String executorGroupId) {
        this.executorGroupId = executorGroupId == null ? null : executorGroupId.trim();
    }

    public String getCommAssign() {
        return commAssign;
    }

    public void setCommAssign(String commAssign) {
        this.commAssign = commAssign == null ? null : commAssign.trim();
    }

    public String getExecutAssign() {
        return executAssign;
    }

    public void setExecutAssign(String executAssign) {
        this.executAssign = executAssign == null ? null : executAssign.trim();
    }

    public String getVerifyAssign() {
        return verifyAssign;
    }

    public void setVerifyAssign(String verifyAssign) {
        this.verifyAssign = verifyAssign == null ? null : verifyAssign.trim();
    }

    public Boolean getUpdatable() {
        return updatable;
    }

    public void setUpdatable(Boolean updatable) {
        this.updatable = updatable;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getBaseType() {
        return baseType;
    }

    public void setBaseType(Integer baseType) {
        this.baseType = baseType;
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

    public Boolean getSync() {
        return sync;
    }

    public void setSync(Boolean sync) {
        this.sync = sync;
    }

    public String getManagement() {
        return management;
    }

    public void setManagement(String management) {
        this.management = management == null ? null : management.trim();
    }

    public Integer getForRecordData() {
        return forRecordData;
    }

    public void setForRecordData(Integer forRecordData) {
        this.forRecordData = forRecordData;
    }
}