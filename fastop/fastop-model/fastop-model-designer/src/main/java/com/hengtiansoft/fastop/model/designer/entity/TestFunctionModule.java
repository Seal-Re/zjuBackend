package com.hengtiansoft.fastop.model.designer.entity;

public class TestFunctionModule {
    private Integer moduleId;

    private String moduleName;

    private String changeUser;

    private String moduleDescription;

    private String moduleNote;

    private String moduleDate;

    private Integer funId;

    private Integer moduleStatus;

    private Integer updated;

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    public String getChangeUser() {
        return changeUser;
    }

    public void setChangeUser(String changeUser) {
        this.changeUser = changeUser == null ? null : changeUser.trim();
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription == null ? null : moduleDescription.trim();
    }

    public String getModuleNote() {
        return moduleNote;
    }

    public void setModuleNote(String moduleNote) {
        this.moduleNote = moduleNote == null ? null : moduleNote.trim();
    }

    public String getModuleDate() {
        return moduleDate;
    }

    public void setModuleDate(String moduleDate) {
        this.moduleDate = moduleDate == null ? null : moduleDate.trim();
    }

    public Integer getFunId() {
        return funId;
    }

    public void setFunId(Integer funId) {
        this.funId = funId;
    }

    public Integer getModuleStatus() {
        return moduleStatus;
    }

    public void setModuleStatus(Integer moduleStatus) {
        this.moduleStatus = moduleStatus;
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }
}