package com.hengtiansoft.fastop.model.designer.entity;

public class TestFunctionCase {
    private Integer caseId;

    private String caseName;

    private String changeUser;

    private String caseDescription;

    private String caseNote;

    private String caseDate;

    private Integer moduleId;

    private Integer caseStatus;

    private Integer updated;

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName == null ? null : caseName.trim();
    }

    public String getChangeUser() {
        return changeUser;
    }

    public void setChangeUser(String changeUser) {
        this.changeUser = changeUser == null ? null : changeUser.trim();
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public void setCaseDescription(String caseDescription) {
        this.caseDescription = caseDescription == null ? null : caseDescription.trim();
    }

    public String getCaseNote() {
        return caseNote;
    }

    public void setCaseNote(String caseNote) {
        this.caseNote = caseNote == null ? null : caseNote.trim();
    }

    public String getCaseDate() {
        return caseDate;
    }

    public void setCaseDate(String caseDate) {
        this.caseDate = caseDate == null ? null : caseDate.trim();
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(Integer caseStatus) {
        this.caseStatus = caseStatus;
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }
}