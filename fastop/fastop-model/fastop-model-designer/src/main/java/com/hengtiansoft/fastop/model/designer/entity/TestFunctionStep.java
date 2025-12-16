package com.hengtiansoft.fastop.model.designer.entity;

public class TestFunctionStep {
    private Integer stepId;

    private String stepName;

    private String changeUser;

    private String stepDescription;

    private String stepNote;

    private String stepDate;

    private String stepOperation;

    private String stepObj;

    private String stepPurpose;

    private Boolean totalSend;

    private String conditionStatus;

    private Integer caseId;

    private Integer stepStatus;

    private Integer update;

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName == null ? null : stepName.trim();
    }

    public String getChangeUser() {
        return changeUser;
    }

    public void setChangeUser(String changeUser) {
        this.changeUser = changeUser == null ? null : changeUser.trim();
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription == null ? null : stepDescription.trim();
    }

    public String getStepNote() {
        return stepNote;
    }

    public void setStepNote(String stepNote) {
        this.stepNote = stepNote == null ? null : stepNote.trim();
    }

    public String getStepDate() {
        return stepDate;
    }

    public void setStepDate(String stepDate) {
        this.stepDate = stepDate == null ? null : stepDate.trim();
    }

    public String getStepOperation() {
        return stepOperation;
    }

    public void setStepOperation(String stepOperation) {
        this.stepOperation = stepOperation == null ? null : stepOperation.trim();
    }

    public String getStepObj() {
        return stepObj;
    }

    public void setStepObj(String stepObj) {
        this.stepObj = stepObj == null ? null : stepObj.trim();
    }

    public String getStepPurpose() {
        return stepPurpose;
    }

    public void setStepPurpose(String stepPurpose) {
        this.stepPurpose = stepPurpose == null ? null : stepPurpose.trim();
    }

    public Boolean getTotalSend() {
        return totalSend;
    }

    public void setTotalSend(Boolean totalSend) {
        this.totalSend = totalSend;
    }

    public String getConditionStatus() {
        return conditionStatus;
    }

    public void setConditionStatus(String conditionStatus) {
        this.conditionStatus = conditionStatus == null ? null : conditionStatus.trim();
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Integer getStepStatus() {
        return stepStatus;
    }

    public void setStepStatus(Integer stepStatus) {
        this.stepStatus = stepStatus;
    }

    public Integer getUpdate() {
        return update;
    }

    public void setUpdate(Integer update) {
        this.update = update;
    }
}