package com.hengtiansoft.fastop.model.planner.entity;

import java.util.Date;

public class ExeFunction {
    private String exeFunctionId;

    private String functionName;

    private String planId;

    private String functionId;

    private Integer version;

    private Integer flowVersion;

    private Integer num;

    private Integer security;

    private Integer expectTime;

    private String testCautionId;

    private String subjectSourceId;

    private Integer exeFunctionOrder;

    private Integer currentStepNum;

    private Integer exeStatus;

    private Integer verifyStatus;

    private Integer verifyNum;

    private Integer militaryStatus;

    private Integer militaryNum;

    private String versionDescription;

    private Boolean military;

    private Integer keyProCount;

    private Integer changeFlag;

    private String flowVersionLine;

    private String dependsOn;

    private String resultComments;

    private String caution;

    private Boolean isReady;

    private Date startTime;

    private Date endTime;

    private Boolean deleted;

    private Date createdAt;

    private Date updatedAt;

    private String createdBy;

    private String updatedBy;

    private Date calBeforeTime;

    private String calTime;

    private Integer executeTime;

    private String redoCount;

    private String detectId;

    public String getExeFunctionId() {
        return exeFunctionId;
    }

    public void setExeFunctionId(String exeFunctionId) {
        this.exeFunctionId = exeFunctionId == null ? null : exeFunctionId.trim();
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName == null ? null : functionName.trim();
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId == null ? null : planId.trim();
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId == null ? null : functionId.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getFlowVersion() {
        return flowVersion;
    }

    public void setFlowVersion(Integer flowVersion) {
        this.flowVersion = flowVersion;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getSecurity() {
        return security;
    }

    public void setSecurity(Integer security) {
        this.security = security;
    }

    public Integer getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(Integer expectTime) {
        this.expectTime = expectTime;
    }

    public String getTestCautionId() {
        return testCautionId;
    }

    public void setTestCautionId(String testCautionId) {
        this.testCautionId = testCautionId == null ? null : testCautionId.trim();
    }

    public String getSubjectSourceId() {
        return subjectSourceId;
    }

    public void setSubjectSourceId(String subjectSourceId) {
        this.subjectSourceId = subjectSourceId == null ? null : subjectSourceId.trim();
    }

    public Integer getExeFunctionOrder() {
        return exeFunctionOrder;
    }

    public void setExeFunctionOrder(Integer exeFunctionOrder) {
        this.exeFunctionOrder = exeFunctionOrder;
    }

    public Integer getCurrentStepNum() {
        return currentStepNum;
    }

    public void setCurrentStepNum(Integer currentStepNum) {
        this.currentStepNum = currentStepNum;
    }

    public Integer getExeStatus() {
        return exeStatus;
    }

    public void setExeStatus(Integer exeStatus) {
        this.exeStatus = exeStatus;
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public Integer getVerifyNum() {
        return verifyNum;
    }

    public void setVerifyNum(Integer verifyNum) {
        this.verifyNum = verifyNum;
    }

    public Integer getMilitaryStatus() {
        return militaryStatus;
    }

    public void setMilitaryStatus(Integer militaryStatus) {
        this.militaryStatus = militaryStatus;
    }

    public Integer getMilitaryNum() {
        return militaryNum;
    }

    public void setMilitaryNum(Integer militaryNum) {
        this.militaryNum = militaryNum;
    }

    public String getVersionDescription() {
        return versionDescription;
    }

    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription == null ? null : versionDescription.trim();
    }

    public Boolean getMilitary() {
        return military;
    }

    public void setMilitary(Boolean military) {
        this.military = military;
    }

    public Integer getKeyProCount() {
        return keyProCount;
    }

    public void setKeyProCount(Integer keyProCount) {
        this.keyProCount = keyProCount;
    }

    public Integer getChangeFlag() {
        return changeFlag;
    }

    public void setChangeFlag(Integer changeFlag) {
        this.changeFlag = changeFlag;
    }

    public String getFlowVersionLine() {
        return flowVersionLine;
    }

    public void setFlowVersionLine(String flowVersionLine) {
        this.flowVersionLine = flowVersionLine == null ? null : flowVersionLine.trim();
    }

    public String getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(String dependsOn) {
        this.dependsOn = dependsOn == null ? null : dependsOn.trim();
    }

    public String getResultComments() {
        return resultComments;
    }

    public void setResultComments(String resultComments) {
        this.resultComments = resultComments == null ? null : resultComments.trim();
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution == null ? null : caution.trim();
    }

    public Boolean getIsReady() {
        return isReady;
    }

    public void setIsReady(Boolean isReady) {
        this.isReady = isReady;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Date getCalBeforeTime() {
        return calBeforeTime;
    }

    public void setCalBeforeTime(Date calBeforeTime) {
        this.calBeforeTime = calBeforeTime;
    }

    public String getCalTime() {
        return calTime;
    }

    public void setCalTime(String calTime) {
        this.calTime = calTime == null ? null : calTime.trim();
    }

    public Integer getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Integer executeTime) {
        this.executeTime = executeTime;
    }

    public String getRedoCount() {
        return redoCount;
    }

    public void setRedoCount(String redoCount) {
        this.redoCount = redoCount == null ? null : redoCount.trim();
    }

    public String getDetectId() {
        return detectId;
    }

    public void setDetectId(String detectId) {
        this.detectId = detectId == null ? null : detectId.trim();
    }
}