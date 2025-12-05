package com.hengtiansoft.fastop.model.planner.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestPlanExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TestPlanExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andPlanIdIsNull() {
            addCriterion("plan_id is null");
            return (Criteria) this;
        }

        public Criteria andPlanIdIsNotNull() {
            addCriterion("plan_id is not null");
            return (Criteria) this;
        }

        public Criteria andPlanIdEqualTo(String value) {
            addCriterion("plan_id =", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdNotEqualTo(String value) {
            addCriterion("plan_id <>", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdGreaterThan(String value) {
            addCriterion("plan_id >", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdGreaterThanOrEqualTo(String value) {
            addCriterion("plan_id >=", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdLessThan(String value) {
            addCriterion("plan_id <", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdLessThanOrEqualTo(String value) {
            addCriterion("plan_id <=", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdLike(String value) {
            addCriterion("plan_id like", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdNotLike(String value) {
            addCriterion("plan_id not like", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdIn(List<String> values) {
            addCriterion("plan_id in", values, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdNotIn(List<String> values) {
            addCriterion("plan_id not in", values, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdBetween(String value1, String value2) {
            addCriterion("plan_id between", value1, value2, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdNotBetween(String value1, String value2) {
            addCriterion("plan_id not between", value1, value2, "planId");
            return (Criteria) this;
        }

        public Criteria andEntityStructIdIsNull() {
            addCriterion("entity_struct_id is null");
            return (Criteria) this;
        }

        public Criteria andEntityStructIdIsNotNull() {
            addCriterion("entity_struct_id is not null");
            return (Criteria) this;
        }

        public Criteria andEntityStructIdEqualTo(Integer value) {
            addCriterion("entity_struct_id =", value, "entityStructId");
            return (Criteria) this;
        }

        public Criteria andEntityStructIdNotEqualTo(Integer value) {
            addCriterion("entity_struct_id <>", value, "entityStructId");
            return (Criteria) this;
        }

        public Criteria andEntityStructIdGreaterThan(Integer value) {
            addCriterion("entity_struct_id >", value, "entityStructId");
            return (Criteria) this;
        }

        public Criteria andEntityStructIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("entity_struct_id >=", value, "entityStructId");
            return (Criteria) this;
        }

        public Criteria andEntityStructIdLessThan(Integer value) {
            addCriterion("entity_struct_id <", value, "entityStructId");
            return (Criteria) this;
        }

        public Criteria andEntityStructIdLessThanOrEqualTo(Integer value) {
            addCriterion("entity_struct_id <=", value, "entityStructId");
            return (Criteria) this;
        }

        public Criteria andEntityStructIdIn(List<Integer> values) {
            addCriterion("entity_struct_id in", values, "entityStructId");
            return (Criteria) this;
        }

        public Criteria andEntityStructIdNotIn(List<Integer> values) {
            addCriterion("entity_struct_id not in", values, "entityStructId");
            return (Criteria) this;
        }

        public Criteria andEntityStructIdBetween(Integer value1, Integer value2) {
            addCriterion("entity_struct_id between", value1, value2, "entityStructId");
            return (Criteria) this;
        }

        public Criteria andEntityStructIdNotBetween(Integer value1, Integer value2) {
            addCriterion("entity_struct_id not between", value1, value2, "entityStructId");
            return (Criteria) this;
        }

        public Criteria andEntityIdIsNull() {
            addCriterion("entity_id is null");
            return (Criteria) this;
        }

        public Criteria andEntityIdIsNotNull() {
            addCriterion("entity_id is not null");
            return (Criteria) this;
        }

        public Criteria andEntityIdEqualTo(Integer value) {
            addCriterion("entity_id =", value, "entityId");
            return (Criteria) this;
        }

        public Criteria andEntityIdNotEqualTo(Integer value) {
            addCriterion("entity_id <>", value, "entityId");
            return (Criteria) this;
        }

        public Criteria andEntityIdGreaterThan(Integer value) {
            addCriterion("entity_id >", value, "entityId");
            return (Criteria) this;
        }

        public Criteria andEntityIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("entity_id >=", value, "entityId");
            return (Criteria) this;
        }

        public Criteria andEntityIdLessThan(Integer value) {
            addCriterion("entity_id <", value, "entityId");
            return (Criteria) this;
        }

        public Criteria andEntityIdLessThanOrEqualTo(Integer value) {
            addCriterion("entity_id <=", value, "entityId");
            return (Criteria) this;
        }

        public Criteria andEntityIdIn(List<Integer> values) {
            addCriterion("entity_id in", values, "entityId");
            return (Criteria) this;
        }

        public Criteria andEntityIdNotIn(List<Integer> values) {
            addCriterion("entity_id not in", values, "entityId");
            return (Criteria) this;
        }

        public Criteria andEntityIdBetween(Integer value1, Integer value2) {
            addCriterion("entity_id between", value1, value2, "entityId");
            return (Criteria) this;
        }

        public Criteria andEntityIdNotBetween(Integer value1, Integer value2) {
            addCriterion("entity_id not between", value1, value2, "entityId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIsNull() {
            addCriterion("subject_id is null");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIsNotNull() {
            addCriterion("subject_id is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectIdEqualTo(Integer value) {
            addCriterion("subject_id =", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotEqualTo(Integer value) {
            addCriterion("subject_id <>", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdGreaterThan(Integer value) {
            addCriterion("subject_id >", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("subject_id >=", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdLessThan(Integer value) {
            addCriterion("subject_id <", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdLessThanOrEqualTo(Integer value) {
            addCriterion("subject_id <=", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIn(List<Integer> values) {
            addCriterion("subject_id in", values, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotIn(List<Integer> values) {
            addCriterion("subject_id not in", values, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdBetween(Integer value1, Integer value2) {
            addCriterion("subject_id between", value1, value2, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotBetween(Integer value1, Integer value2) {
            addCriterion("subject_id not between", value1, value2, "subjectId");
            return (Criteria) this;
        }

        public Criteria andFunGroupIdIsNull() {
            addCriterion("fun_group_id is null");
            return (Criteria) this;
        }

        public Criteria andFunGroupIdIsNotNull() {
            addCriterion("fun_group_id is not null");
            return (Criteria) this;
        }

        public Criteria andFunGroupIdEqualTo(Integer value) {
            addCriterion("fun_group_id =", value, "funGroupId");
            return (Criteria) this;
        }

        public Criteria andFunGroupIdNotEqualTo(Integer value) {
            addCriterion("fun_group_id <>", value, "funGroupId");
            return (Criteria) this;
        }

        public Criteria andFunGroupIdGreaterThan(Integer value) {
            addCriterion("fun_group_id >", value, "funGroupId");
            return (Criteria) this;
        }

        public Criteria andFunGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("fun_group_id >=", value, "funGroupId");
            return (Criteria) this;
        }

        public Criteria andFunGroupIdLessThan(Integer value) {
            addCriterion("fun_group_id <", value, "funGroupId");
            return (Criteria) this;
        }

        public Criteria andFunGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("fun_group_id <=", value, "funGroupId");
            return (Criteria) this;
        }

        public Criteria andFunGroupIdIn(List<Integer> values) {
            addCriterion("fun_group_id in", values, "funGroupId");
            return (Criteria) this;
        }

        public Criteria andFunGroupIdNotIn(List<Integer> values) {
            addCriterion("fun_group_id not in", values, "funGroupId");
            return (Criteria) this;
        }

        public Criteria andFunGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("fun_group_id between", value1, value2, "funGroupId");
            return (Criteria) this;
        }

        public Criteria andFunGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("fun_group_id not between", value1, value2, "funGroupId");
            return (Criteria) this;
        }

        public Criteria andSuiteIdIsNull() {
            addCriterion("suite_id is null");
            return (Criteria) this;
        }

        public Criteria andSuiteIdIsNotNull() {
            addCriterion("suite_id is not null");
            return (Criteria) this;
        }

        public Criteria andSuiteIdEqualTo(Integer value) {
            addCriterion("suite_id =", value, "suiteId");
            return (Criteria) this;
        }

        public Criteria andSuiteIdNotEqualTo(Integer value) {
            addCriterion("suite_id <>", value, "suiteId");
            return (Criteria) this;
        }

        public Criteria andSuiteIdGreaterThan(Integer value) {
            addCriterion("suite_id >", value, "suiteId");
            return (Criteria) this;
        }

        public Criteria andSuiteIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("suite_id >=", value, "suiteId");
            return (Criteria) this;
        }

        public Criteria andSuiteIdLessThan(Integer value) {
            addCriterion("suite_id <", value, "suiteId");
            return (Criteria) this;
        }

        public Criteria andSuiteIdLessThanOrEqualTo(Integer value) {
            addCriterion("suite_id <=", value, "suiteId");
            return (Criteria) this;
        }

        public Criteria andSuiteIdIn(List<Integer> values) {
            addCriterion("suite_id in", values, "suiteId");
            return (Criteria) this;
        }

        public Criteria andSuiteIdNotIn(List<Integer> values) {
            addCriterion("suite_id not in", values, "suiteId");
            return (Criteria) this;
        }

        public Criteria andSuiteIdBetween(Integer value1, Integer value2) {
            addCriterion("suite_id between", value1, value2, "suiteId");
            return (Criteria) this;
        }

        public Criteria andSuiteIdNotBetween(Integer value1, Integer value2) {
            addCriterion("suite_id not between", value1, value2, "suiteId");
            return (Criteria) this;
        }

        public Criteria andMilitaryIsNull() {
            addCriterion("military is null");
            return (Criteria) this;
        }

        public Criteria andMilitaryIsNotNull() {
            addCriterion("military is not null");
            return (Criteria) this;
        }

        public Criteria andMilitaryEqualTo(Boolean value) {
            addCriterion("military =", value, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryNotEqualTo(Boolean value) {
            addCriterion("military <>", value, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryGreaterThan(Boolean value) {
            addCriterion("military >", value, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryGreaterThanOrEqualTo(Boolean value) {
            addCriterion("military >=", value, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryLessThan(Boolean value) {
            addCriterion("military <", value, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryLessThanOrEqualTo(Boolean value) {
            addCriterion("military <=", value, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryIn(List<Boolean> values) {
            addCriterion("military in", values, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryNotIn(List<Boolean> values) {
            addCriterion("military not in", values, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryBetween(Boolean value1, Boolean value2) {
            addCriterion("military between", value1, value2, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryNotBetween(Boolean value1, Boolean value2) {
            addCriterion("military not between", value1, value2, "military");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeIsNull() {
            addCriterion("plan_start_time is null");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeIsNotNull() {
            addCriterion("plan_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeEqualTo(Date value) {
            addCriterion("plan_start_time =", value, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeNotEqualTo(Date value) {
            addCriterion("plan_start_time <>", value, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeGreaterThan(Date value) {
            addCriterion("plan_start_time >", value, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("plan_start_time >=", value, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeLessThan(Date value) {
            addCriterion("plan_start_time <", value, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("plan_start_time <=", value, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeIn(List<Date> values) {
            addCriterion("plan_start_time in", values, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeNotIn(List<Date> values) {
            addCriterion("plan_start_time not in", values, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeBetween(Date value1, Date value2) {
            addCriterion("plan_start_time between", value1, value2, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("plan_start_time not between", value1, value2, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeIsNull() {
            addCriterion("plan_end_time is null");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeIsNotNull() {
            addCriterion("plan_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeEqualTo(Date value) {
            addCriterion("plan_end_time =", value, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeNotEqualTo(Date value) {
            addCriterion("plan_end_time <>", value, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeGreaterThan(Date value) {
            addCriterion("plan_end_time >", value, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("plan_end_time >=", value, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeLessThan(Date value) {
            addCriterion("plan_end_time <", value, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("plan_end_time <=", value, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeIn(List<Date> values) {
            addCriterion("plan_end_time in", values, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeNotIn(List<Date> values) {
            addCriterion("plan_end_time not in", values, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeBetween(Date value1, Date value2) {
            addCriterion("plan_end_time between", value1, value2, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andPlanEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("plan_end_time not between", value1, value2, "planEndTime");
            return (Criteria) this;
        }

        public Criteria andActualStartTimeIsNull() {
            addCriterion("actual_start_time is null");
            return (Criteria) this;
        }

        public Criteria andActualStartTimeIsNotNull() {
            addCriterion("actual_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andActualStartTimeEqualTo(Date value) {
            addCriterion("actual_start_time =", value, "actualStartTime");
            return (Criteria) this;
        }

        public Criteria andActualStartTimeNotEqualTo(Date value) {
            addCriterion("actual_start_time <>", value, "actualStartTime");
            return (Criteria) this;
        }

        public Criteria andActualStartTimeGreaterThan(Date value) {
            addCriterion("actual_start_time >", value, "actualStartTime");
            return (Criteria) this;
        }

        public Criteria andActualStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("actual_start_time >=", value, "actualStartTime");
            return (Criteria) this;
        }

        public Criteria andActualStartTimeLessThan(Date value) {
            addCriterion("actual_start_time <", value, "actualStartTime");
            return (Criteria) this;
        }

        public Criteria andActualStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("actual_start_time <=", value, "actualStartTime");
            return (Criteria) this;
        }

        public Criteria andActualStartTimeIn(List<Date> values) {
            addCriterion("actual_start_time in", values, "actualStartTime");
            return (Criteria) this;
        }

        public Criteria andActualStartTimeNotIn(List<Date> values) {
            addCriterion("actual_start_time not in", values, "actualStartTime");
            return (Criteria) this;
        }

        public Criteria andActualStartTimeBetween(Date value1, Date value2) {
            addCriterion("actual_start_time between", value1, value2, "actualStartTime");
            return (Criteria) this;
        }

        public Criteria andActualStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("actual_start_time not between", value1, value2, "actualStartTime");
            return (Criteria) this;
        }

        public Criteria andActualEndTimeIsNull() {
            addCriterion("actual_end_time is null");
            return (Criteria) this;
        }

        public Criteria andActualEndTimeIsNotNull() {
            addCriterion("actual_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andActualEndTimeEqualTo(Date value) {
            addCriterion("actual_end_time =", value, "actualEndTime");
            return (Criteria) this;
        }

        public Criteria andActualEndTimeNotEqualTo(Date value) {
            addCriterion("actual_end_time <>", value, "actualEndTime");
            return (Criteria) this;
        }

        public Criteria andActualEndTimeGreaterThan(Date value) {
            addCriterion("actual_end_time >", value, "actualEndTime");
            return (Criteria) this;
        }

        public Criteria andActualEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("actual_end_time >=", value, "actualEndTime");
            return (Criteria) this;
        }

        public Criteria andActualEndTimeLessThan(Date value) {
            addCriterion("actual_end_time <", value, "actualEndTime");
            return (Criteria) this;
        }

        public Criteria andActualEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("actual_end_time <=", value, "actualEndTime");
            return (Criteria) this;
        }

        public Criteria andActualEndTimeIn(List<Date> values) {
            addCriterion("actual_end_time in", values, "actualEndTime");
            return (Criteria) this;
        }

        public Criteria andActualEndTimeNotIn(List<Date> values) {
            addCriterion("actual_end_time not in", values, "actualEndTime");
            return (Criteria) this;
        }

        public Criteria andActualEndTimeBetween(Date value1, Date value2) {
            addCriterion("actual_end_time between", value1, value2, "actualEndTime");
            return (Criteria) this;
        }

        public Criteria andActualEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("actual_end_time not between", value1, value2, "actualEndTime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andPlanNumberIsNull() {
            addCriterion("plan_number is null");
            return (Criteria) this;
        }

        public Criteria andPlanNumberIsNotNull() {
            addCriterion("plan_number is not null");
            return (Criteria) this;
        }

        public Criteria andPlanNumberEqualTo(String value) {
            addCriterion("plan_number =", value, "planNumber");
            return (Criteria) this;
        }

        public Criteria andPlanNumberNotEqualTo(String value) {
            addCriterion("plan_number <>", value, "planNumber");
            return (Criteria) this;
        }

        public Criteria andPlanNumberGreaterThan(String value) {
            addCriterion("plan_number >", value, "planNumber");
            return (Criteria) this;
        }

        public Criteria andPlanNumberGreaterThanOrEqualTo(String value) {
            addCriterion("plan_number >=", value, "planNumber");
            return (Criteria) this;
        }

        public Criteria andPlanNumberLessThan(String value) {
            addCriterion("plan_number <", value, "planNumber");
            return (Criteria) this;
        }

        public Criteria andPlanNumberLessThanOrEqualTo(String value) {
            addCriterion("plan_number <=", value, "planNumber");
            return (Criteria) this;
        }

        public Criteria andPlanNumberLike(String value) {
            addCriterion("plan_number like", value, "planNumber");
            return (Criteria) this;
        }

        public Criteria andPlanNumberNotLike(String value) {
            addCriterion("plan_number not like", value, "planNumber");
            return (Criteria) this;
        }

        public Criteria andPlanNumberIn(List<String> values) {
            addCriterion("plan_number in", values, "planNumber");
            return (Criteria) this;
        }

        public Criteria andPlanNumberNotIn(List<String> values) {
            addCriterion("plan_number not in", values, "planNumber");
            return (Criteria) this;
        }

        public Criteria andPlanNumberBetween(String value1, String value2) {
            addCriterion("plan_number between", value1, value2, "planNumber");
            return (Criteria) this;
        }

        public Criteria andPlanNumberNotBetween(String value1, String value2) {
            addCriterion("plan_number not between", value1, value2, "planNumber");
            return (Criteria) this;
        }

        public Criteria andPlanRoundIsNull() {
            addCriterion("plan_round is null");
            return (Criteria) this;
        }

        public Criteria andPlanRoundIsNotNull() {
            addCriterion("plan_round is not null");
            return (Criteria) this;
        }

        public Criteria andPlanRoundEqualTo(Integer value) {
            addCriterion("plan_round =", value, "planRound");
            return (Criteria) this;
        }

        public Criteria andPlanRoundNotEqualTo(Integer value) {
            addCriterion("plan_round <>", value, "planRound");
            return (Criteria) this;
        }

        public Criteria andPlanRoundGreaterThan(Integer value) {
            addCriterion("plan_round >", value, "planRound");
            return (Criteria) this;
        }

        public Criteria andPlanRoundGreaterThanOrEqualTo(Integer value) {
            addCriterion("plan_round >=", value, "planRound");
            return (Criteria) this;
        }

        public Criteria andPlanRoundLessThan(Integer value) {
            addCriterion("plan_round <", value, "planRound");
            return (Criteria) this;
        }

        public Criteria andPlanRoundLessThanOrEqualTo(Integer value) {
            addCriterion("plan_round <=", value, "planRound");
            return (Criteria) this;
        }

        public Criteria andPlanRoundIn(List<Integer> values) {
            addCriterion("plan_round in", values, "planRound");
            return (Criteria) this;
        }

        public Criteria andPlanRoundNotIn(List<Integer> values) {
            addCriterion("plan_round not in", values, "planRound");
            return (Criteria) this;
        }

        public Criteria andPlanRoundBetween(Integer value1, Integer value2) {
            addCriterion("plan_round between", value1, value2, "planRound");
            return (Criteria) this;
        }

        public Criteria andPlanRoundNotBetween(Integer value1, Integer value2) {
            addCriterion("plan_round not between", value1, value2, "planRound");
            return (Criteria) this;
        }

        public Criteria andPlanNameIsNull() {
            addCriterion("plan_name is null");
            return (Criteria) this;
        }

        public Criteria andPlanNameIsNotNull() {
            addCriterion("plan_name is not null");
            return (Criteria) this;
        }

        public Criteria andPlanNameEqualTo(String value) {
            addCriterion("plan_name =", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameNotEqualTo(String value) {
            addCriterion("plan_name <>", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameGreaterThan(String value) {
            addCriterion("plan_name >", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameGreaterThanOrEqualTo(String value) {
            addCriterion("plan_name >=", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameLessThan(String value) {
            addCriterion("plan_name <", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameLessThanOrEqualTo(String value) {
            addCriterion("plan_name <=", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameLike(String value) {
            addCriterion("plan_name like", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameNotLike(String value) {
            addCriterion("plan_name not like", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameIn(List<String> values) {
            addCriterion("plan_name in", values, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameNotIn(List<String> values) {
            addCriterion("plan_name not in", values, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameBetween(String value1, String value2) {
            addCriterion("plan_name between", value1, value2, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameNotBetween(String value1, String value2) {
            addCriterion("plan_name not between", value1, value2, "planName");
            return (Criteria) this;
        }

        public Criteria andAreaIdIsNull() {
            addCriterion("area_id is null");
            return (Criteria) this;
        }

        public Criteria andAreaIdIsNotNull() {
            addCriterion("area_id is not null");
            return (Criteria) this;
        }

        public Criteria andAreaIdEqualTo(Integer value) {
            addCriterion("area_id =", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotEqualTo(Integer value) {
            addCriterion("area_id <>", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdGreaterThan(Integer value) {
            addCriterion("area_id >", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("area_id >=", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdLessThan(Integer value) {
            addCriterion("area_id <", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdLessThanOrEqualTo(Integer value) {
            addCriterion("area_id <=", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdIn(List<Integer> values) {
            addCriterion("area_id in", values, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotIn(List<Integer> values) {
            addCriterion("area_id not in", values, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdBetween(Integer value1, Integer value2) {
            addCriterion("area_id between", value1, value2, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotBetween(Integer value1, Integer value2) {
            addCriterion("area_id not between", value1, value2, "areaId");
            return (Criteria) this;
        }

        public Criteria andDispatcherIdIsNull() {
            addCriterion("dispatcher_id is null");
            return (Criteria) this;
        }

        public Criteria andDispatcherIdIsNotNull() {
            addCriterion("dispatcher_id is not null");
            return (Criteria) this;
        }

        public Criteria andDispatcherIdEqualTo(String value) {
            addCriterion("dispatcher_id =", value, "dispatcherId");
            return (Criteria) this;
        }

        public Criteria andDispatcherIdNotEqualTo(String value) {
            addCriterion("dispatcher_id <>", value, "dispatcherId");
            return (Criteria) this;
        }

        public Criteria andDispatcherIdGreaterThan(String value) {
            addCriterion("dispatcher_id >", value, "dispatcherId");
            return (Criteria) this;
        }

        public Criteria andDispatcherIdGreaterThanOrEqualTo(String value) {
            addCriterion("dispatcher_id >=", value, "dispatcherId");
            return (Criteria) this;
        }

        public Criteria andDispatcherIdLessThan(String value) {
            addCriterion("dispatcher_id <", value, "dispatcherId");
            return (Criteria) this;
        }

        public Criteria andDispatcherIdLessThanOrEqualTo(String value) {
            addCriterion("dispatcher_id <=", value, "dispatcherId");
            return (Criteria) this;
        }

        public Criteria andDispatcherIdLike(String value) {
            addCriterion("dispatcher_id like", value, "dispatcherId");
            return (Criteria) this;
        }

        public Criteria andDispatcherIdNotLike(String value) {
            addCriterion("dispatcher_id not like", value, "dispatcherId");
            return (Criteria) this;
        }

        public Criteria andDispatcherIdIn(List<String> values) {
            addCriterion("dispatcher_id in", values, "dispatcherId");
            return (Criteria) this;
        }

        public Criteria andDispatcherIdNotIn(List<String> values) {
            addCriterion("dispatcher_id not in", values, "dispatcherId");
            return (Criteria) this;
        }

        public Criteria andDispatcherIdBetween(String value1, String value2) {
            addCriterion("dispatcher_id between", value1, value2, "dispatcherId");
            return (Criteria) this;
        }

        public Criteria andDispatcherIdNotBetween(String value1, String value2) {
            addCriterion("dispatcher_id not between", value1, value2, "dispatcherId");
            return (Criteria) this;
        }

        public Criteria andCommanderIdIsNull() {
            addCriterion("commander_id is null");
            return (Criteria) this;
        }

        public Criteria andCommanderIdIsNotNull() {
            addCriterion("commander_id is not null");
            return (Criteria) this;
        }

        public Criteria andCommanderIdEqualTo(String value) {
            addCriterion("commander_id =", value, "commanderId");
            return (Criteria) this;
        }

        public Criteria andCommanderIdNotEqualTo(String value) {
            addCriterion("commander_id <>", value, "commanderId");
            return (Criteria) this;
        }

        public Criteria andCommanderIdGreaterThan(String value) {
            addCriterion("commander_id >", value, "commanderId");
            return (Criteria) this;
        }

        public Criteria andCommanderIdGreaterThanOrEqualTo(String value) {
            addCriterion("commander_id >=", value, "commanderId");
            return (Criteria) this;
        }

        public Criteria andCommanderIdLessThan(String value) {
            addCriterion("commander_id <", value, "commanderId");
            return (Criteria) this;
        }

        public Criteria andCommanderIdLessThanOrEqualTo(String value) {
            addCriterion("commander_id <=", value, "commanderId");
            return (Criteria) this;
        }

        public Criteria andCommanderIdLike(String value) {
            addCriterion("commander_id like", value, "commanderId");
            return (Criteria) this;
        }

        public Criteria andCommanderIdNotLike(String value) {
            addCriterion("commander_id not like", value, "commanderId");
            return (Criteria) this;
        }

        public Criteria andCommanderIdIn(List<String> values) {
            addCriterion("commander_id in", values, "commanderId");
            return (Criteria) this;
        }

        public Criteria andCommanderIdNotIn(List<String> values) {
            addCriterion("commander_id not in", values, "commanderId");
            return (Criteria) this;
        }

        public Criteria andCommanderIdBetween(String value1, String value2) {
            addCriterion("commander_id between", value1, value2, "commanderId");
            return (Criteria) this;
        }

        public Criteria andCommanderIdNotBetween(String value1, String value2) {
            addCriterion("commander_id not between", value1, value2, "commanderId");
            return (Criteria) this;
        }

        public Criteria andExecutorGroupIdIsNull() {
            addCriterion("executor_group_id is null");
            return (Criteria) this;
        }

        public Criteria andExecutorGroupIdIsNotNull() {
            addCriterion("executor_group_id is not null");
            return (Criteria) this;
        }

        public Criteria andExecutorGroupIdEqualTo(String value) {
            addCriterion("executor_group_id =", value, "executorGroupId");
            return (Criteria) this;
        }

        public Criteria andExecutorGroupIdNotEqualTo(String value) {
            addCriterion("executor_group_id <>", value, "executorGroupId");
            return (Criteria) this;
        }

        public Criteria andExecutorGroupIdGreaterThan(String value) {
            addCriterion("executor_group_id >", value, "executorGroupId");
            return (Criteria) this;
        }

        public Criteria andExecutorGroupIdGreaterThanOrEqualTo(String value) {
            addCriterion("executor_group_id >=", value, "executorGroupId");
            return (Criteria) this;
        }

        public Criteria andExecutorGroupIdLessThan(String value) {
            addCriterion("executor_group_id <", value, "executorGroupId");
            return (Criteria) this;
        }

        public Criteria andExecutorGroupIdLessThanOrEqualTo(String value) {
            addCriterion("executor_group_id <=", value, "executorGroupId");
            return (Criteria) this;
        }

        public Criteria andExecutorGroupIdLike(String value) {
            addCriterion("executor_group_id like", value, "executorGroupId");
            return (Criteria) this;
        }

        public Criteria andExecutorGroupIdNotLike(String value) {
            addCriterion("executor_group_id not like", value, "executorGroupId");
            return (Criteria) this;
        }

        public Criteria andExecutorGroupIdIn(List<String> values) {
            addCriterion("executor_group_id in", values, "executorGroupId");
            return (Criteria) this;
        }

        public Criteria andExecutorGroupIdNotIn(List<String> values) {
            addCriterion("executor_group_id not in", values, "executorGroupId");
            return (Criteria) this;
        }

        public Criteria andExecutorGroupIdBetween(String value1, String value2) {
            addCriterion("executor_group_id between", value1, value2, "executorGroupId");
            return (Criteria) this;
        }

        public Criteria andExecutorGroupIdNotBetween(String value1, String value2) {
            addCriterion("executor_group_id not between", value1, value2, "executorGroupId");
            return (Criteria) this;
        }

        public Criteria andCommAssignIsNull() {
            addCriterion("comm_assign is null");
            return (Criteria) this;
        }

        public Criteria andCommAssignIsNotNull() {
            addCriterion("comm_assign is not null");
            return (Criteria) this;
        }

        public Criteria andCommAssignEqualTo(String value) {
            addCriterion("comm_assign =", value, "commAssign");
            return (Criteria) this;
        }

        public Criteria andCommAssignNotEqualTo(String value) {
            addCriterion("comm_assign <>", value, "commAssign");
            return (Criteria) this;
        }

        public Criteria andCommAssignGreaterThan(String value) {
            addCriterion("comm_assign >", value, "commAssign");
            return (Criteria) this;
        }

        public Criteria andCommAssignGreaterThanOrEqualTo(String value) {
            addCriterion("comm_assign >=", value, "commAssign");
            return (Criteria) this;
        }

        public Criteria andCommAssignLessThan(String value) {
            addCriterion("comm_assign <", value, "commAssign");
            return (Criteria) this;
        }

        public Criteria andCommAssignLessThanOrEqualTo(String value) {
            addCriterion("comm_assign <=", value, "commAssign");
            return (Criteria) this;
        }

        public Criteria andCommAssignLike(String value) {
            addCriterion("comm_assign like", value, "commAssign");
            return (Criteria) this;
        }

        public Criteria andCommAssignNotLike(String value) {
            addCriterion("comm_assign not like", value, "commAssign");
            return (Criteria) this;
        }

        public Criteria andCommAssignIn(List<String> values) {
            addCriterion("comm_assign in", values, "commAssign");
            return (Criteria) this;
        }

        public Criteria andCommAssignNotIn(List<String> values) {
            addCriterion("comm_assign not in", values, "commAssign");
            return (Criteria) this;
        }

        public Criteria andCommAssignBetween(String value1, String value2) {
            addCriterion("comm_assign between", value1, value2, "commAssign");
            return (Criteria) this;
        }

        public Criteria andCommAssignNotBetween(String value1, String value2) {
            addCriterion("comm_assign not between", value1, value2, "commAssign");
            return (Criteria) this;
        }

        public Criteria andExecutAssignIsNull() {
            addCriterion("execut_assign is null");
            return (Criteria) this;
        }

        public Criteria andExecutAssignIsNotNull() {
            addCriterion("execut_assign is not null");
            return (Criteria) this;
        }

        public Criteria andExecutAssignEqualTo(String value) {
            addCriterion("execut_assign =", value, "executAssign");
            return (Criteria) this;
        }

        public Criteria andExecutAssignNotEqualTo(String value) {
            addCriterion("execut_assign <>", value, "executAssign");
            return (Criteria) this;
        }

        public Criteria andExecutAssignGreaterThan(String value) {
            addCriterion("execut_assign >", value, "executAssign");
            return (Criteria) this;
        }

        public Criteria andExecutAssignGreaterThanOrEqualTo(String value) {
            addCriterion("execut_assign >=", value, "executAssign");
            return (Criteria) this;
        }

        public Criteria andExecutAssignLessThan(String value) {
            addCriterion("execut_assign <", value, "executAssign");
            return (Criteria) this;
        }

        public Criteria andExecutAssignLessThanOrEqualTo(String value) {
            addCriterion("execut_assign <=", value, "executAssign");
            return (Criteria) this;
        }

        public Criteria andExecutAssignLike(String value) {
            addCriterion("execut_assign like", value, "executAssign");
            return (Criteria) this;
        }

        public Criteria andExecutAssignNotLike(String value) {
            addCriterion("execut_assign not like", value, "executAssign");
            return (Criteria) this;
        }

        public Criteria andExecutAssignIn(List<String> values) {
            addCriterion("execut_assign in", values, "executAssign");
            return (Criteria) this;
        }

        public Criteria andExecutAssignNotIn(List<String> values) {
            addCriterion("execut_assign not in", values, "executAssign");
            return (Criteria) this;
        }

        public Criteria andExecutAssignBetween(String value1, String value2) {
            addCriterion("execut_assign between", value1, value2, "executAssign");
            return (Criteria) this;
        }

        public Criteria andExecutAssignNotBetween(String value1, String value2) {
            addCriterion("execut_assign not between", value1, value2, "executAssign");
            return (Criteria) this;
        }

        public Criteria andVerifyAssignIsNull() {
            addCriterion("verify_assign is null");
            return (Criteria) this;
        }

        public Criteria andVerifyAssignIsNotNull() {
            addCriterion("verify_assign is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyAssignEqualTo(String value) {
            addCriterion("verify_assign =", value, "verifyAssign");
            return (Criteria) this;
        }

        public Criteria andVerifyAssignNotEqualTo(String value) {
            addCriterion("verify_assign <>", value, "verifyAssign");
            return (Criteria) this;
        }

        public Criteria andVerifyAssignGreaterThan(String value) {
            addCriterion("verify_assign >", value, "verifyAssign");
            return (Criteria) this;
        }

        public Criteria andVerifyAssignGreaterThanOrEqualTo(String value) {
            addCriterion("verify_assign >=", value, "verifyAssign");
            return (Criteria) this;
        }

        public Criteria andVerifyAssignLessThan(String value) {
            addCriterion("verify_assign <", value, "verifyAssign");
            return (Criteria) this;
        }

        public Criteria andVerifyAssignLessThanOrEqualTo(String value) {
            addCriterion("verify_assign <=", value, "verifyAssign");
            return (Criteria) this;
        }

        public Criteria andVerifyAssignLike(String value) {
            addCriterion("verify_assign like", value, "verifyAssign");
            return (Criteria) this;
        }

        public Criteria andVerifyAssignNotLike(String value) {
            addCriterion("verify_assign not like", value, "verifyAssign");
            return (Criteria) this;
        }

        public Criteria andVerifyAssignIn(List<String> values) {
            addCriterion("verify_assign in", values, "verifyAssign");
            return (Criteria) this;
        }

        public Criteria andVerifyAssignNotIn(List<String> values) {
            addCriterion("verify_assign not in", values, "verifyAssign");
            return (Criteria) this;
        }

        public Criteria andVerifyAssignBetween(String value1, String value2) {
            addCriterion("verify_assign between", value1, value2, "verifyAssign");
            return (Criteria) this;
        }

        public Criteria andVerifyAssignNotBetween(String value1, String value2) {
            addCriterion("verify_assign not between", value1, value2, "verifyAssign");
            return (Criteria) this;
        }

        public Criteria andUpdatableIsNull() {
            addCriterion("updatable is null");
            return (Criteria) this;
        }

        public Criteria andUpdatableIsNotNull() {
            addCriterion("updatable is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatableEqualTo(Boolean value) {
            addCriterion("updatable =", value, "updatable");
            return (Criteria) this;
        }

        public Criteria andUpdatableNotEqualTo(Boolean value) {
            addCriterion("updatable <>", value, "updatable");
            return (Criteria) this;
        }

        public Criteria andUpdatableGreaterThan(Boolean value) {
            addCriterion("updatable >", value, "updatable");
            return (Criteria) this;
        }

        public Criteria andUpdatableGreaterThanOrEqualTo(Boolean value) {
            addCriterion("updatable >=", value, "updatable");
            return (Criteria) this;
        }

        public Criteria andUpdatableLessThan(Boolean value) {
            addCriterion("updatable <", value, "updatable");
            return (Criteria) this;
        }

        public Criteria andUpdatableLessThanOrEqualTo(Boolean value) {
            addCriterion("updatable <=", value, "updatable");
            return (Criteria) this;
        }

        public Criteria andUpdatableIn(List<Boolean> values) {
            addCriterion("updatable in", values, "updatable");
            return (Criteria) this;
        }

        public Criteria andUpdatableNotIn(List<Boolean> values) {
            addCriterion("updatable not in", values, "updatable");
            return (Criteria) this;
        }

        public Criteria andUpdatableBetween(Boolean value1, Boolean value2) {
            addCriterion("updatable between", value1, value2, "updatable");
            return (Criteria) this;
        }

        public Criteria andUpdatableNotBetween(Boolean value1, Boolean value2) {
            addCriterion("updatable not between", value1, value2, "updatable");
            return (Criteria) this;
        }

        public Criteria andArchivedIsNull() {
            addCriterion("archived is null");
            return (Criteria) this;
        }

        public Criteria andArchivedIsNotNull() {
            addCriterion("archived is not null");
            return (Criteria) this;
        }

        public Criteria andArchivedEqualTo(Boolean value) {
            addCriterion("archived =", value, "archived");
            return (Criteria) this;
        }

        public Criteria andArchivedNotEqualTo(Boolean value) {
            addCriterion("archived <>", value, "archived");
            return (Criteria) this;
        }

        public Criteria andArchivedGreaterThan(Boolean value) {
            addCriterion("archived >", value, "archived");
            return (Criteria) this;
        }

        public Criteria andArchivedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("archived >=", value, "archived");
            return (Criteria) this;
        }

        public Criteria andArchivedLessThan(Boolean value) {
            addCriterion("archived <", value, "archived");
            return (Criteria) this;
        }

        public Criteria andArchivedLessThanOrEqualTo(Boolean value) {
            addCriterion("archived <=", value, "archived");
            return (Criteria) this;
        }

        public Criteria andArchivedIn(List<Boolean> values) {
            addCriterion("archived in", values, "archived");
            return (Criteria) this;
        }

        public Criteria andArchivedNotIn(List<Boolean> values) {
            addCriterion("archived not in", values, "archived");
            return (Criteria) this;
        }

        public Criteria andArchivedBetween(Boolean value1, Boolean value2) {
            addCriterion("archived between", value1, value2, "archived");
            return (Criteria) this;
        }

        public Criteria andArchivedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("archived not between", value1, value2, "archived");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNull() {
            addCriterion("deleted is null");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNotNull() {
            addCriterion("deleted is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedEqualTo(Boolean value) {
            addCriterion("deleted =", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(Boolean value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(Boolean value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(Boolean value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedIn(List<Boolean> values) {
            addCriterion("deleted in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotIn(List<Boolean> values) {
            addCriterion("deleted not in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted not between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andBaseTypeIsNull() {
            addCriterion("base_type is null");
            return (Criteria) this;
        }

        public Criteria andBaseTypeIsNotNull() {
            addCriterion("base_type is not null");
            return (Criteria) this;
        }

        public Criteria andBaseTypeEqualTo(Integer value) {
            addCriterion("base_type =", value, "baseType");
            return (Criteria) this;
        }

        public Criteria andBaseTypeNotEqualTo(Integer value) {
            addCriterion("base_type <>", value, "baseType");
            return (Criteria) this;
        }

        public Criteria andBaseTypeGreaterThan(Integer value) {
            addCriterion("base_type >", value, "baseType");
            return (Criteria) this;
        }

        public Criteria andBaseTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("base_type >=", value, "baseType");
            return (Criteria) this;
        }

        public Criteria andBaseTypeLessThan(Integer value) {
            addCriterion("base_type <", value, "baseType");
            return (Criteria) this;
        }

        public Criteria andBaseTypeLessThanOrEqualTo(Integer value) {
            addCriterion("base_type <=", value, "baseType");
            return (Criteria) this;
        }

        public Criteria andBaseTypeIn(List<Integer> values) {
            addCriterion("base_type in", values, "baseType");
            return (Criteria) this;
        }

        public Criteria andBaseTypeNotIn(List<Integer> values) {
            addCriterion("base_type not in", values, "baseType");
            return (Criteria) this;
        }

        public Criteria andBaseTypeBetween(Integer value1, Integer value2) {
            addCriterion("base_type between", value1, value2, "baseType");
            return (Criteria) this;
        }

        public Criteria andBaseTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("base_type not between", value1, value2, "baseType");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNull() {
            addCriterion("created_at is null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNotNull() {
            addCriterion("created_at is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtEqualTo(Date value) {
            addCriterion("created_at =", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotEqualTo(Date value) {
            addCriterion("created_at <>", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThan(Date value) {
            addCriterion("created_at >", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("created_at >=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThan(Date value) {
            addCriterion("created_at <", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThanOrEqualTo(Date value) {
            addCriterion("created_at <=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIn(List<Date> values) {
            addCriterion("created_at in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotIn(List<Date> values) {
            addCriterion("created_at not in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtBetween(Date value1, Date value2) {
            addCriterion("created_at between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotBetween(Date value1, Date value2) {
            addCriterion("created_at not between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIsNull() {
            addCriterion("updated_at is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIsNotNull() {
            addCriterion("updated_at is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtEqualTo(Date value) {
            addCriterion("updated_at =", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotEqualTo(Date value) {
            addCriterion("updated_at <>", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThan(Date value) {
            addCriterion("updated_at >", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("updated_at >=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThan(Date value) {
            addCriterion("updated_at <", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThanOrEqualTo(Date value) {
            addCriterion("updated_at <=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIn(List<Date> values) {
            addCriterion("updated_at in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotIn(List<Date> values) {
            addCriterion("updated_at not in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtBetween(Date value1, Date value2) {
            addCriterion("updated_at between", value1, value2, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotBetween(Date value1, Date value2) {
            addCriterion("updated_at not between", value1, value2, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNull() {
            addCriterion("created_by is null");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNotNull() {
            addCriterion("created_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedByEqualTo(String value) {
            addCriterion("created_by =", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotEqualTo(String value) {
            addCriterion("created_by <>", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThan(String value) {
            addCriterion("created_by >", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThanOrEqualTo(String value) {
            addCriterion("created_by >=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThan(String value) {
            addCriterion("created_by <", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThanOrEqualTo(String value) {
            addCriterion("created_by <=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLike(String value) {
            addCriterion("created_by like", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotLike(String value) {
            addCriterion("created_by not like", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByIn(List<String> values) {
            addCriterion("created_by in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotIn(List<String> values) {
            addCriterion("created_by not in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByBetween(String value1, String value2) {
            addCriterion("created_by between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotBetween(String value1, String value2) {
            addCriterion("created_by not between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByIsNull() {
            addCriterion("updated_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedByIsNotNull() {
            addCriterion("updated_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedByEqualTo(String value) {
            addCriterion("updated_by =", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotEqualTo(String value) {
            addCriterion("updated_by <>", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByGreaterThan(String value) {
            addCriterion("updated_by >", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByGreaterThanOrEqualTo(String value) {
            addCriterion("updated_by >=", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByLessThan(String value) {
            addCriterion("updated_by <", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByLessThanOrEqualTo(String value) {
            addCriterion("updated_by <=", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByLike(String value) {
            addCriterion("updated_by like", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotLike(String value) {
            addCriterion("updated_by not like", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByIn(List<String> values) {
            addCriterion("updated_by in", values, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotIn(List<String> values) {
            addCriterion("updated_by not in", values, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByBetween(String value1, String value2) {
            addCriterion("updated_by between", value1, value2, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotBetween(String value1, String value2) {
            addCriterion("updated_by not between", value1, value2, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andSyncIsNull() {
            addCriterion("sync is null");
            return (Criteria) this;
        }

        public Criteria andSyncIsNotNull() {
            addCriterion("sync is not null");
            return (Criteria) this;
        }

        public Criteria andSyncEqualTo(Boolean value) {
            addCriterion("sync =", value, "sync");
            return (Criteria) this;
        }

        public Criteria andSyncNotEqualTo(Boolean value) {
            addCriterion("sync <>", value, "sync");
            return (Criteria) this;
        }

        public Criteria andSyncGreaterThan(Boolean value) {
            addCriterion("sync >", value, "sync");
            return (Criteria) this;
        }

        public Criteria andSyncGreaterThanOrEqualTo(Boolean value) {
            addCriterion("sync >=", value, "sync");
            return (Criteria) this;
        }

        public Criteria andSyncLessThan(Boolean value) {
            addCriterion("sync <", value, "sync");
            return (Criteria) this;
        }

        public Criteria andSyncLessThanOrEqualTo(Boolean value) {
            addCriterion("sync <=", value, "sync");
            return (Criteria) this;
        }

        public Criteria andSyncIn(List<Boolean> values) {
            addCriterion("sync in", values, "sync");
            return (Criteria) this;
        }

        public Criteria andSyncNotIn(List<Boolean> values) {
            addCriterion("sync not in", values, "sync");
            return (Criteria) this;
        }

        public Criteria andSyncBetween(Boolean value1, Boolean value2) {
            addCriterion("sync between", value1, value2, "sync");
            return (Criteria) this;
        }

        public Criteria andSyncNotBetween(Boolean value1, Boolean value2) {
            addCriterion("sync not between", value1, value2, "sync");
            return (Criteria) this;
        }

        public Criteria andManagementIsNull() {
            addCriterion("management is null");
            return (Criteria) this;
        }

        public Criteria andManagementIsNotNull() {
            addCriterion("management is not null");
            return (Criteria) this;
        }

        public Criteria andManagementEqualTo(String value) {
            addCriterion("management =", value, "management");
            return (Criteria) this;
        }

        public Criteria andManagementNotEqualTo(String value) {
            addCriterion("management <>", value, "management");
            return (Criteria) this;
        }

        public Criteria andManagementGreaterThan(String value) {
            addCriterion("management >", value, "management");
            return (Criteria) this;
        }

        public Criteria andManagementGreaterThanOrEqualTo(String value) {
            addCriterion("management >=", value, "management");
            return (Criteria) this;
        }

        public Criteria andManagementLessThan(String value) {
            addCriterion("management <", value, "management");
            return (Criteria) this;
        }

        public Criteria andManagementLessThanOrEqualTo(String value) {
            addCriterion("management <=", value, "management");
            return (Criteria) this;
        }

        public Criteria andManagementLike(String value) {
            addCriterion("management like", value, "management");
            return (Criteria) this;
        }

        public Criteria andManagementNotLike(String value) {
            addCriterion("management not like", value, "management");
            return (Criteria) this;
        }

        public Criteria andManagementIn(List<String> values) {
            addCriterion("management in", values, "management");
            return (Criteria) this;
        }

        public Criteria andManagementNotIn(List<String> values) {
            addCriterion("management not in", values, "management");
            return (Criteria) this;
        }

        public Criteria andManagementBetween(String value1, String value2) {
            addCriterion("management between", value1, value2, "management");
            return (Criteria) this;
        }

        public Criteria andManagementNotBetween(String value1, String value2) {
            addCriterion("management not between", value1, value2, "management");
            return (Criteria) this;
        }

        public Criteria andForRecordDataIsNull() {
            addCriterion("for_record_data is null");
            return (Criteria) this;
        }

        public Criteria andForRecordDataIsNotNull() {
            addCriterion("for_record_data is not null");
            return (Criteria) this;
        }

        public Criteria andForRecordDataEqualTo(Integer value) {
            addCriterion("for_record_data =", value, "forRecordData");
            return (Criteria) this;
        }

        public Criteria andForRecordDataNotEqualTo(Integer value) {
            addCriterion("for_record_data <>", value, "forRecordData");
            return (Criteria) this;
        }

        public Criteria andForRecordDataGreaterThan(Integer value) {
            addCriterion("for_record_data >", value, "forRecordData");
            return (Criteria) this;
        }

        public Criteria andForRecordDataGreaterThanOrEqualTo(Integer value) {
            addCriterion("for_record_data >=", value, "forRecordData");
            return (Criteria) this;
        }

        public Criteria andForRecordDataLessThan(Integer value) {
            addCriterion("for_record_data <", value, "forRecordData");
            return (Criteria) this;
        }

        public Criteria andForRecordDataLessThanOrEqualTo(Integer value) {
            addCriterion("for_record_data <=", value, "forRecordData");
            return (Criteria) this;
        }

        public Criteria andForRecordDataIn(List<Integer> values) {
            addCriterion("for_record_data in", values, "forRecordData");
            return (Criteria) this;
        }

        public Criteria andForRecordDataNotIn(List<Integer> values) {
            addCriterion("for_record_data not in", values, "forRecordData");
            return (Criteria) this;
        }

        public Criteria andForRecordDataBetween(Integer value1, Integer value2) {
            addCriterion("for_record_data between", value1, value2, "forRecordData");
            return (Criteria) this;
        }

        public Criteria andForRecordDataNotBetween(Integer value1, Integer value2) {
            addCriterion("for_record_data not between", value1, value2, "forRecordData");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}