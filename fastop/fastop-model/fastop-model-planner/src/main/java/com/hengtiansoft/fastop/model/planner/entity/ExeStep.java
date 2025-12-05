package com.hengtiansoft.fastop.model.planner.entity;

import java.util.Date;

public class ExeStep {
    private String exeStepId;

    private Integer stepId;

    private String exeFunctionId;

    private Integer stepLevel;

    private Integer stepOrder;

    private String levelSeq;

    private String stepSeq;

    private String stepDescription;

    private String contentId;

    private Integer exeStatus;

    private Integer verifyStatus;

    private Integer militaryStatus;

    private String resultComments;

    private String militaryComment;

    private String stepResult;

    private Integer dataId;

    private Boolean isManual;

    private String levelOneId;

    private Integer judgeResult;

    private Boolean canNext;

    private String operation;

    private String operationObject;

    private String operationContent;

    private Integer criterionStandardId;

    private String criterionStandard;

    private Integer criterionType;

    private String criterionValueUnit;

    private String criterionDesc;

    private String guideUrl;

    private Boolean keyProcess;

    private Boolean dependOnDevice;

    private String caution;

    private String commander;

    private String verfier;

    private String soldier;

    private Date startTime;

    private Date endTime;

    private Integer changeFlag;

    private Boolean deleted;

    private Date createdAt;

    private Date updatedAt;

    private String createdBy;

    private String updatedBy;

    private String calculate;

    private Integer parallelExecute;

    public String getExeStepId() {
        return exeStepId;
    }

    public void setExeStepId(String exeStepId) {
        this.exeStepId = exeStepId == null ? null : exeStepId.trim();
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public String getExeFunctionId() {
        return exeFunctionId;
    }

    public void setExeFunctionId(String exeFunctionId) {
        this.exeFunctionId = exeFunctionId == null ? null : exeFunctionId.trim();
    }

    public Integer getStepLevel() {
        return stepLevel;
    }

    public void setStepLevel(Integer stepLevel) {
        this.stepLevel = stepLevel;
    }

    public Integer getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }

    public String getLevelSeq() {
        return levelSeq;
    }

    public void setLevelSeq(String levelSeq) {
        this.levelSeq = levelSeq == null ? null : levelSeq.trim();
    }

    public String getStepSeq() {
        return stepSeq;
    }

    public void setStepSeq(String stepSeq) {
        this.stepSeq = stepSeq == null ? null : stepSeq.trim();
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription == null ? null : stepDescription.trim();
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId == null ? null : contentId.trim();
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

    public Integer getMilitaryStatus() {
        return militaryStatus;
    }

    public void setMilitaryStatus(Integer militaryStatus) {
        this.militaryStatus = militaryStatus;
    }

    public String getResultComments() {
        return resultComments;
    }

    public void setResultComments(String resultComments) {
        this.resultComments = resultComments == null ? null : resultComments.trim();
    }

    public String getMilitaryComment() {
        return militaryComment;
    }

    public void setMilitaryComment(String militaryComment) {
        this.militaryComment = militaryComment == null ? null : militaryComment.trim();
    }

    public String getStepResult() {
        return stepResult;
    }

    public void setStepResult(String stepResult) {
        this.stepResult = stepResult == null ? null : stepResult.trim();
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public Boolean getIsManual() {
        return isManual;
    }

    public void setIsManual(Boolean isManual) {
        this.isManual = isManual;
    }

    public String getLevelOneId() {
        return levelOneId;
    }

    public void setLevelOneId(String levelOneId) {
        this.levelOneId = levelOneId == null ? null : levelOneId.trim();
    }

    public Integer getJudgeResult() {
        return judgeResult;
    }

    public void setJudgeResult(Integer judgeResult) {
        this.judgeResult = judgeResult;
    }

    public Boolean getCanNext() {
        return canNext;
    }

    public void setCanNext(Boolean canNext) {
        this.canNext = canNext;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public String getOperationObject() {
        return operationObject;
    }

    public void setOperationObject(String operationObject) {
        this.operationObject = operationObject == null ? null : operationObject.trim();
    }

    public String getOperationContent() {
        return operationContent;
    }

    public void setOperationContent(String operationContent) {
        this.operationContent = operationContent == null ? null : operationContent.trim();
    }

    public Integer getCriterionStandardId() {
        return criterionStandardId;
    }

    public void setCriterionStandardId(Integer criterionStandardId) {
        this.criterionStandardId = criterionStandardId;
    }

    public String getCriterionStandard() {
        return criterionStandard;
    }

    public void setCriterionStandard(String criterionStandard) {
        this.criterionStandard = criterionStandard == null ? null : criterionStandard.trim();
    }

    public Integer getCriterionType() {
        return criterionType;
    }

    public void setCriterionType(Integer criterionType) {
        this.criterionType = criterionType;
    }

    public String getCriterionValueUnit() {
        return criterionValueUnit;
    }

    public void setCriterionValueUnit(String criterionValueUnit) {
        this.criterionValueUnit = criterionValueUnit == null ? null : criterionValueUnit.trim();
    }

    public String getCriterionDesc() {
        return criterionDesc;
    }

    public void setCriterionDesc(String criterionDesc) {
        this.criterionDesc = criterionDesc == null ? null : criterionDesc.trim();
    }

    public String getGuideUrl() {
        return guideUrl;
    }

    public void setGuideUrl(String guideUrl) {
        this.guideUrl = guideUrl == null ? null : guideUrl.trim();
    }

    public Boolean getKeyProcess() {
        return keyProcess;
    }

    public void setKeyProcess(Boolean keyProcess) {
        this.keyProcess = keyProcess;
    }

    public Boolean getDependOnDevice() {
        return dependOnDevice;
    }

    public void setDependOnDevice(Boolean dependOnDevice) {
        this.dependOnDevice = dependOnDevice;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution == null ? null : caution.trim();
    }

    public String getCommander() {
        return commander;
    }

    public void setCommander(String commander) {
        this.commander = commander == null ? null : commander.trim();
    }

    public String getVerfier() {
        return verfier;
    }

    public void setVerfier(String verfier) {
        this.verfier = verfier == null ? null : verfier.trim();
    }

    public String getSoldier() {
        return soldier;
    }

    public void setSoldier(String soldier) {
        this.soldier = soldier == null ? null : soldier.trim();
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

    public Integer getChangeFlag() {
        return changeFlag;
    }

    public void setChangeFlag(Integer changeFlag) {
        this.changeFlag = changeFlag;
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

    public String getCalculate() {
        return calculate;
    }

    public void setCalculate(String calculate) {
        this.calculate = calculate == null ? null : calculate.trim();
    }

    public Integer getParallelExecute() {
        return parallelExecute;
    }

    public void setParallelExecute(Integer parallelExecute) {
        this.parallelExecute = parallelExecute;
    }
}