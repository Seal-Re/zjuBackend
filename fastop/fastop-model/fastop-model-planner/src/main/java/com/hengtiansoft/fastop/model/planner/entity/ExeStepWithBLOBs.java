package com.hengtiansoft.fastop.model.planner.entity;

public class ExeStepWithBLOBs extends ExeStep {
    private String commandData;

    private String failCause;

    private String criterionContent;

    public String getCommandData() {
        return commandData;
    }

    public void setCommandData(String commandData) {
        this.commandData = commandData == null ? null : commandData.trim();
    }

    public String getFailCause() {
        return failCause;
    }

    public void setFailCause(String failCause) {
        this.failCause = failCause == null ? null : failCause.trim();
    }

    public String getCriterionContent() {
        return criterionContent;
    }

    public void setCriterionContent(String criterionContent) {
        this.criterionContent = criterionContent == null ? null : criterionContent.trim();
    }
}