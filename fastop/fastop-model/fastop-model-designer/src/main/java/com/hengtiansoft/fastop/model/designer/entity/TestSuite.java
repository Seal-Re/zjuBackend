package com.hengtiansoft.fastop.model.designer.entity;

import java.util.Date;

public class TestSuite {
    private Integer suiteId;

    private String suiteName;

    private String suiteDesc;

    private Integer version;

    private Integer planeEffectMin;

    private Integer planeEffectMax;

    private String proofer;

    private String approver;

    private String submitter;

    private Integer testBaseId;

    private Integer listApprStatus;

    private Boolean military;

    private Boolean keyProcess;

    private String apprChain;

    private Boolean deleted;

    private Date createdAt;

    private Date updatedAt;

    private String createdBy;

    private String updatedBy;

    private String mesdceCode;

    public Integer getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(Integer suiteId) {
        this.suiteId = suiteId;
    }

    public String getSuiteName() {
        return suiteName;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName == null ? null : suiteName.trim();
    }

    public String getSuiteDesc() {
        return suiteDesc;
    }

    public void setSuiteDesc(String suiteDesc) {
        this.suiteDesc = suiteDesc == null ? null : suiteDesc.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getPlaneEffectMin() {
        return planeEffectMin;
    }

    public void setPlaneEffectMin(Integer planeEffectMin) {
        this.planeEffectMin = planeEffectMin;
    }

    public Integer getPlaneEffectMax() {
        return planeEffectMax;
    }

    public void setPlaneEffectMax(Integer planeEffectMax) {
        this.planeEffectMax = planeEffectMax;
    }

    public String getProofer() {
        return proofer;
    }

    public void setProofer(String proofer) {
        this.proofer = proofer == null ? null : proofer.trim();
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver == null ? null : approver.trim();
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter == null ? null : submitter.trim();
    }

    public Integer getTestBaseId() {
        return testBaseId;
    }

    public void setTestBaseId(Integer testBaseId) {
        this.testBaseId = testBaseId;
    }

    public Integer getListApprStatus() {
        return listApprStatus;
    }

    public void setListApprStatus(Integer listApprStatus) {
        this.listApprStatus = listApprStatus;
    }

    public Boolean getMilitary() {
        return military;
    }

    public void setMilitary(Boolean military) {
        this.military = military;
    }

    public Boolean getKeyProcess() {
        return keyProcess;
    }

    public void setKeyProcess(Boolean keyProcess) {
        this.keyProcess = keyProcess;
    }

    public String getApprChain() {
        return apprChain;
    }

    public void setApprChain(String apprChain) {
        this.apprChain = apprChain == null ? null : apprChain.trim();
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

    public String getMesdceCode() {
        return mesdceCode;
    }

    public void setMesdceCode(String mesdceCode) {
        this.mesdceCode = mesdceCode == null ? null : mesdceCode.trim();
    }
}