package com.hengtiansoft.fastop.model.designer.entity;

import java.util.Date;

public class TestFunction {
    private Integer funId;

    private String funName;

    private Integer funOrder;

    private Integer testBaseId;

    private Integer version;

    private Integer flowVersion;

    private Integer planeEffectMin;

    private Integer planeEffectMax;

    private Integer num;

    private Integer expectTime;

    private Integer securityLevel;

    private String comment;

    private String versionDescription;

    private String approveComment;

    private String subjectSourceId;

    private String otherTechFiles;

    private String devicePool;

    private String testCautionId;

    private String caution;

    private String dependsOn;

    private Integer changeFlag;

    private Integer keyProCount;

    private Integer approveStatus;

    private String apprChain;

    private Boolean military;

    private String usingBy;

    private String designer;

    private String proofer;

    private String verifier;

    private String checker;

    private String qualityer;

    private String approver;

    private String historyVersionLine;

    private String syncPlan;

    private Boolean deleted;

    private Date createdAt;

    private Date updatedAt;

    private String createdBy;

    private String updatedBy;

    private Integer createNew;

    private Integer militaryFunc;

    private String detectId;

    public Integer getFunId() {
        return funId;
    }

    public void setFunId(Integer funId) {
        this.funId = funId;
    }

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName == null ? null : funName.trim();
    }

    public Integer getFunOrder() {
        return funOrder;
    }

    public void setFunOrder(Integer funOrder) {
        this.funOrder = funOrder;
    }

    public Integer getTestBaseId() {
        return testBaseId;
    }

    public void setTestBaseId(Integer testBaseId) {
        this.testBaseId = testBaseId;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(Integer expectTime) {
        this.expectTime = expectTime;
    }

    public Integer getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(Integer securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getVersionDescription() {
        return versionDescription;
    }

    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription == null ? null : versionDescription.trim();
    }

    public String getApproveComment() {
        return approveComment;
    }

    public void setApproveComment(String approveComment) {
        this.approveComment = approveComment == null ? null : approveComment.trim();
    }

    public String getSubjectSourceId() {
        return subjectSourceId;
    }

    public void setSubjectSourceId(String subjectSourceId) {
        this.subjectSourceId = subjectSourceId == null ? null : subjectSourceId.trim();
    }

    public String getOtherTechFiles() {
        return otherTechFiles;
    }

    public void setOtherTechFiles(String otherTechFiles) {
        this.otherTechFiles = otherTechFiles == null ? null : otherTechFiles.trim();
    }

    public String getDevicePool() {
        return devicePool;
    }

    public void setDevicePool(String devicePool) {
        this.devicePool = devicePool == null ? null : devicePool.trim();
    }

    public String getTestCautionId() {
        return testCautionId;
    }

    public void setTestCautionId(String testCautionId) {
        this.testCautionId = testCautionId == null ? null : testCautionId.trim();
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution == null ? null : caution.trim();
    }

    public String getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(String dependsOn) {
        this.dependsOn = dependsOn == null ? null : dependsOn.trim();
    }

    public Integer getChangeFlag() {
        return changeFlag;
    }

    public void setChangeFlag(Integer changeFlag) {
        this.changeFlag = changeFlag;
    }

    public Integer getKeyProCount() {
        return keyProCount;
    }

    public void setKeyProCount(Integer keyProCount) {
        this.keyProCount = keyProCount;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApprChain() {
        return apprChain;
    }

    public void setApprChain(String apprChain) {
        this.apprChain = apprChain == null ? null : apprChain.trim();
    }

    public Boolean getMilitary() {
        return military;
    }

    public void setMilitary(Boolean military) {
        this.military = military;
    }

    public String getUsingBy() {
        return usingBy;
    }

    public void setUsingBy(String usingBy) {
        this.usingBy = usingBy == null ? null : usingBy.trim();
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer == null ? null : designer.trim();
    }

    public String getProofer() {
        return proofer;
    }

    public void setProofer(String proofer) {
        this.proofer = proofer == null ? null : proofer.trim();
    }

    public String getVerifier() {
        return verifier;
    }

    public void setVerifier(String verifier) {
        this.verifier = verifier == null ? null : verifier.trim();
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker == null ? null : checker.trim();
    }

    public String getQualityer() {
        return qualityer;
    }

    public void setQualityer(String qualityer) {
        this.qualityer = qualityer == null ? null : qualityer.trim();
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver == null ? null : approver.trim();
    }

    public String getHistoryVersionLine() {
        return historyVersionLine;
    }

    public void setHistoryVersionLine(String historyVersionLine) {
        this.historyVersionLine = historyVersionLine == null ? null : historyVersionLine.trim();
    }

    public String getSyncPlan() {
        return syncPlan;
    }

    public void setSyncPlan(String syncPlan) {
        this.syncPlan = syncPlan == null ? null : syncPlan.trim();
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

    public Integer getCreateNew() {
        return createNew;
    }

    public void setCreateNew(Integer createNew) {
        this.createNew = createNew;
    }

    public Integer getMilitaryFunc() {
        return militaryFunc;
    }

    public void setMilitaryFunc(Integer militaryFunc) {
        this.militaryFunc = militaryFunc;
    }

    public String getDetectId() {
        return detectId;
    }

    public void setDetectId(String detectId) {
        this.detectId = detectId == null ? null : detectId.trim();
    }
}