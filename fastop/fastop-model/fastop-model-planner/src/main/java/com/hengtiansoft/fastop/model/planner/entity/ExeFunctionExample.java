package com.hengtiansoft.fastop.model.planner.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExeFunctionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExeFunctionExample() {
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

        public Criteria andExeFunctionIdIsNull() {
            addCriterion("exe_function_id is null");
            return (Criteria) this;
        }

        public Criteria andExeFunctionIdIsNotNull() {
            addCriterion("exe_function_id is not null");
            return (Criteria) this;
        }

        public Criteria andExeFunctionIdEqualTo(String value) {
            addCriterion("exe_function_id =", value, "exeFunctionId");
            return (Criteria) this;
        }

        public Criteria andExeFunctionIdNotEqualTo(String value) {
            addCriterion("exe_function_id <>", value, "exeFunctionId");
            return (Criteria) this;
        }

        public Criteria andExeFunctionIdGreaterThan(String value) {
            addCriterion("exe_function_id >", value, "exeFunctionId");
            return (Criteria) this;
        }

        public Criteria andExeFunctionIdGreaterThanOrEqualTo(String value) {
            addCriterion("exe_function_id >=", value, "exeFunctionId");
            return (Criteria) this;
        }

        public Criteria andExeFunctionIdLessThan(String value) {
            addCriterion("exe_function_id <", value, "exeFunctionId");
            return (Criteria) this;
        }

        public Criteria andExeFunctionIdLessThanOrEqualTo(String value) {
            addCriterion("exe_function_id <=", value, "exeFunctionId");
            return (Criteria) this;
        }

        public Criteria andExeFunctionIdLike(String value) {
            addCriterion("exe_function_id like", value, "exeFunctionId");
            return (Criteria) this;
        }

        public Criteria andExeFunctionIdNotLike(String value) {
            addCriterion("exe_function_id not like", value, "exeFunctionId");
            return (Criteria) this;
        }

        public Criteria andExeFunctionIdIn(List<String> values) {
            addCriterion("exe_function_id in", values, "exeFunctionId");
            return (Criteria) this;
        }

        public Criteria andExeFunctionIdNotIn(List<String> values) {
            addCriterion("exe_function_id not in", values, "exeFunctionId");
            return (Criteria) this;
        }

        public Criteria andExeFunctionIdBetween(String value1, String value2) {
            addCriterion("exe_function_id between", value1, value2, "exeFunctionId");
            return (Criteria) this;
        }

        public Criteria andExeFunctionIdNotBetween(String value1, String value2) {
            addCriterion("exe_function_id not between", value1, value2, "exeFunctionId");
            return (Criteria) this;
        }

        public Criteria andFunctionNameIsNull() {
            addCriterion("function_name is null");
            return (Criteria) this;
        }

        public Criteria andFunctionNameIsNotNull() {
            addCriterion("function_name is not null");
            return (Criteria) this;
        }

        public Criteria andFunctionNameEqualTo(String value) {
            addCriterion("function_name =", value, "functionName");
            return (Criteria) this;
        }

        public Criteria andFunctionNameNotEqualTo(String value) {
            addCriterion("function_name <>", value, "functionName");
            return (Criteria) this;
        }

        public Criteria andFunctionNameGreaterThan(String value) {
            addCriterion("function_name >", value, "functionName");
            return (Criteria) this;
        }

        public Criteria andFunctionNameGreaterThanOrEqualTo(String value) {
            addCriterion("function_name >=", value, "functionName");
            return (Criteria) this;
        }

        public Criteria andFunctionNameLessThan(String value) {
            addCriterion("function_name <", value, "functionName");
            return (Criteria) this;
        }

        public Criteria andFunctionNameLessThanOrEqualTo(String value) {
            addCriterion("function_name <=", value, "functionName");
            return (Criteria) this;
        }

        public Criteria andFunctionNameLike(String value) {
            addCriterion("function_name like", value, "functionName");
            return (Criteria) this;
        }

        public Criteria andFunctionNameNotLike(String value) {
            addCriterion("function_name not like", value, "functionName");
            return (Criteria) this;
        }

        public Criteria andFunctionNameIn(List<String> values) {
            addCriterion("function_name in", values, "functionName");
            return (Criteria) this;
        }

        public Criteria andFunctionNameNotIn(List<String> values) {
            addCriterion("function_name not in", values, "functionName");
            return (Criteria) this;
        }

        public Criteria andFunctionNameBetween(String value1, String value2) {
            addCriterion("function_name between", value1, value2, "functionName");
            return (Criteria) this;
        }

        public Criteria andFunctionNameNotBetween(String value1, String value2) {
            addCriterion("function_name not between", value1, value2, "functionName");
            return (Criteria) this;
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

        public Criteria andFunctionIdIsNull() {
            addCriterion("function_id is null");
            return (Criteria) this;
        }

        public Criteria andFunctionIdIsNotNull() {
            addCriterion("function_id is not null");
            return (Criteria) this;
        }

        public Criteria andFunctionIdEqualTo(String value) {
            addCriterion("function_id =", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdNotEqualTo(String value) {
            addCriterion("function_id <>", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdGreaterThan(String value) {
            addCriterion("function_id >", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdGreaterThanOrEqualTo(String value) {
            addCriterion("function_id >=", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdLessThan(String value) {
            addCriterion("function_id <", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdLessThanOrEqualTo(String value) {
            addCriterion("function_id <=", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdLike(String value) {
            addCriterion("function_id like", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdNotLike(String value) {
            addCriterion("function_id not like", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdIn(List<String> values) {
            addCriterion("function_id in", values, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdNotIn(List<String> values) {
            addCriterion("function_id not in", values, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdBetween(String value1, String value2) {
            addCriterion("function_id between", value1, value2, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdNotBetween(String value1, String value2) {
            addCriterion("function_id not between", value1, value2, "functionId");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Integer value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Integer value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Integer value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Integer value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Integer value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Integer> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Integer> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Integer value1, Integer value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andFlowVersionIsNull() {
            addCriterion("flow_version is null");
            return (Criteria) this;
        }

        public Criteria andFlowVersionIsNotNull() {
            addCriterion("flow_version is not null");
            return (Criteria) this;
        }

        public Criteria andFlowVersionEqualTo(Integer value) {
            addCriterion("flow_version =", value, "flowVersion");
            return (Criteria) this;
        }

        public Criteria andFlowVersionNotEqualTo(Integer value) {
            addCriterion("flow_version <>", value, "flowVersion");
            return (Criteria) this;
        }

        public Criteria andFlowVersionGreaterThan(Integer value) {
            addCriterion("flow_version >", value, "flowVersion");
            return (Criteria) this;
        }

        public Criteria andFlowVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("flow_version >=", value, "flowVersion");
            return (Criteria) this;
        }

        public Criteria andFlowVersionLessThan(Integer value) {
            addCriterion("flow_version <", value, "flowVersion");
            return (Criteria) this;
        }

        public Criteria andFlowVersionLessThanOrEqualTo(Integer value) {
            addCriterion("flow_version <=", value, "flowVersion");
            return (Criteria) this;
        }

        public Criteria andFlowVersionIn(List<Integer> values) {
            addCriterion("flow_version in", values, "flowVersion");
            return (Criteria) this;
        }

        public Criteria andFlowVersionNotIn(List<Integer> values) {
            addCriterion("flow_version not in", values, "flowVersion");
            return (Criteria) this;
        }

        public Criteria andFlowVersionBetween(Integer value1, Integer value2) {
            addCriterion("flow_version between", value1, value2, "flowVersion");
            return (Criteria) this;
        }

        public Criteria andFlowVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("flow_version not between", value1, value2, "flowVersion");
            return (Criteria) this;
        }

        public Criteria andNumIsNull() {
            addCriterion("num is null");
            return (Criteria) this;
        }

        public Criteria andNumIsNotNull() {
            addCriterion("num is not null");
            return (Criteria) this;
        }

        public Criteria andNumEqualTo(Integer value) {
            addCriterion("num =", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotEqualTo(Integer value) {
            addCriterion("num <>", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThan(Integer value) {
            addCriterion("num >", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("num >=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThan(Integer value) {
            addCriterion("num <", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThanOrEqualTo(Integer value) {
            addCriterion("num <=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumIn(List<Integer> values) {
            addCriterion("num in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotIn(List<Integer> values) {
            addCriterion("num not in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumBetween(Integer value1, Integer value2) {
            addCriterion("num between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotBetween(Integer value1, Integer value2) {
            addCriterion("num not between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andSecurityIsNull() {
            addCriterion("security is null");
            return (Criteria) this;
        }

        public Criteria andSecurityIsNotNull() {
            addCriterion("security is not null");
            return (Criteria) this;
        }

        public Criteria andSecurityEqualTo(Integer value) {
            addCriterion("security =", value, "security");
            return (Criteria) this;
        }

        public Criteria andSecurityNotEqualTo(Integer value) {
            addCriterion("security <>", value, "security");
            return (Criteria) this;
        }

        public Criteria andSecurityGreaterThan(Integer value) {
            addCriterion("security >", value, "security");
            return (Criteria) this;
        }

        public Criteria andSecurityGreaterThanOrEqualTo(Integer value) {
            addCriterion("security >=", value, "security");
            return (Criteria) this;
        }

        public Criteria andSecurityLessThan(Integer value) {
            addCriterion("security <", value, "security");
            return (Criteria) this;
        }

        public Criteria andSecurityLessThanOrEqualTo(Integer value) {
            addCriterion("security <=", value, "security");
            return (Criteria) this;
        }

        public Criteria andSecurityIn(List<Integer> values) {
            addCriterion("security in", values, "security");
            return (Criteria) this;
        }

        public Criteria andSecurityNotIn(List<Integer> values) {
            addCriterion("security not in", values, "security");
            return (Criteria) this;
        }

        public Criteria andSecurityBetween(Integer value1, Integer value2) {
            addCriterion("security between", value1, value2, "security");
            return (Criteria) this;
        }

        public Criteria andSecurityNotBetween(Integer value1, Integer value2) {
            addCriterion("security not between", value1, value2, "security");
            return (Criteria) this;
        }

        public Criteria andExpectTimeIsNull() {
            addCriterion("expect_time is null");
            return (Criteria) this;
        }

        public Criteria andExpectTimeIsNotNull() {
            addCriterion("expect_time is not null");
            return (Criteria) this;
        }

        public Criteria andExpectTimeEqualTo(Integer value) {
            addCriterion("expect_time =", value, "expectTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeNotEqualTo(Integer value) {
            addCriterion("expect_time <>", value, "expectTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeGreaterThan(Integer value) {
            addCriterion("expect_time >", value, "expectTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("expect_time >=", value, "expectTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeLessThan(Integer value) {
            addCriterion("expect_time <", value, "expectTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeLessThanOrEqualTo(Integer value) {
            addCriterion("expect_time <=", value, "expectTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeIn(List<Integer> values) {
            addCriterion("expect_time in", values, "expectTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeNotIn(List<Integer> values) {
            addCriterion("expect_time not in", values, "expectTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeBetween(Integer value1, Integer value2) {
            addCriterion("expect_time between", value1, value2, "expectTime");
            return (Criteria) this;
        }

        public Criteria andExpectTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("expect_time not between", value1, value2, "expectTime");
            return (Criteria) this;
        }

        public Criteria andTestCautionIdIsNull() {
            addCriterion("test_caution_id is null");
            return (Criteria) this;
        }

        public Criteria andTestCautionIdIsNotNull() {
            addCriterion("test_caution_id is not null");
            return (Criteria) this;
        }

        public Criteria andTestCautionIdEqualTo(String value) {
            addCriterion("test_caution_id =", value, "testCautionId");
            return (Criteria) this;
        }

        public Criteria andTestCautionIdNotEqualTo(String value) {
            addCriterion("test_caution_id <>", value, "testCautionId");
            return (Criteria) this;
        }

        public Criteria andTestCautionIdGreaterThan(String value) {
            addCriterion("test_caution_id >", value, "testCautionId");
            return (Criteria) this;
        }

        public Criteria andTestCautionIdGreaterThanOrEqualTo(String value) {
            addCriterion("test_caution_id >=", value, "testCautionId");
            return (Criteria) this;
        }

        public Criteria andTestCautionIdLessThan(String value) {
            addCriterion("test_caution_id <", value, "testCautionId");
            return (Criteria) this;
        }

        public Criteria andTestCautionIdLessThanOrEqualTo(String value) {
            addCriterion("test_caution_id <=", value, "testCautionId");
            return (Criteria) this;
        }

        public Criteria andTestCautionIdLike(String value) {
            addCriterion("test_caution_id like", value, "testCautionId");
            return (Criteria) this;
        }

        public Criteria andTestCautionIdNotLike(String value) {
            addCriterion("test_caution_id not like", value, "testCautionId");
            return (Criteria) this;
        }

        public Criteria andTestCautionIdIn(List<String> values) {
            addCriterion("test_caution_id in", values, "testCautionId");
            return (Criteria) this;
        }

        public Criteria andTestCautionIdNotIn(List<String> values) {
            addCriterion("test_caution_id not in", values, "testCautionId");
            return (Criteria) this;
        }

        public Criteria andTestCautionIdBetween(String value1, String value2) {
            addCriterion("test_caution_id between", value1, value2, "testCautionId");
            return (Criteria) this;
        }

        public Criteria andTestCautionIdNotBetween(String value1, String value2) {
            addCriterion("test_caution_id not between", value1, value2, "testCautionId");
            return (Criteria) this;
        }

        public Criteria andSubjectSourceIdIsNull() {
            addCriterion("subject_source_id is null");
            return (Criteria) this;
        }

        public Criteria andSubjectSourceIdIsNotNull() {
            addCriterion("subject_source_id is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectSourceIdEqualTo(String value) {
            addCriterion("subject_source_id =", value, "subjectSourceId");
            return (Criteria) this;
        }

        public Criteria andSubjectSourceIdNotEqualTo(String value) {
            addCriterion("subject_source_id <>", value, "subjectSourceId");
            return (Criteria) this;
        }

        public Criteria andSubjectSourceIdGreaterThan(String value) {
            addCriterion("subject_source_id >", value, "subjectSourceId");
            return (Criteria) this;
        }

        public Criteria andSubjectSourceIdGreaterThanOrEqualTo(String value) {
            addCriterion("subject_source_id >=", value, "subjectSourceId");
            return (Criteria) this;
        }

        public Criteria andSubjectSourceIdLessThan(String value) {
            addCriterion("subject_source_id <", value, "subjectSourceId");
            return (Criteria) this;
        }

        public Criteria andSubjectSourceIdLessThanOrEqualTo(String value) {
            addCriterion("subject_source_id <=", value, "subjectSourceId");
            return (Criteria) this;
        }

        public Criteria andSubjectSourceIdLike(String value) {
            addCriterion("subject_source_id like", value, "subjectSourceId");
            return (Criteria) this;
        }

        public Criteria andSubjectSourceIdNotLike(String value) {
            addCriterion("subject_source_id not like", value, "subjectSourceId");
            return (Criteria) this;
        }

        public Criteria andSubjectSourceIdIn(List<String> values) {
            addCriterion("subject_source_id in", values, "subjectSourceId");
            return (Criteria) this;
        }

        public Criteria andSubjectSourceIdNotIn(List<String> values) {
            addCriterion("subject_source_id not in", values, "subjectSourceId");
            return (Criteria) this;
        }

        public Criteria andSubjectSourceIdBetween(String value1, String value2) {
            addCriterion("subject_source_id between", value1, value2, "subjectSourceId");
            return (Criteria) this;
        }

        public Criteria andSubjectSourceIdNotBetween(String value1, String value2) {
            addCriterion("subject_source_id not between", value1, value2, "subjectSourceId");
            return (Criteria) this;
        }

        public Criteria andExeFunctionOrderIsNull() {
            addCriterion("exe_function_order is null");
            return (Criteria) this;
        }

        public Criteria andExeFunctionOrderIsNotNull() {
            addCriterion("exe_function_order is not null");
            return (Criteria) this;
        }

        public Criteria andExeFunctionOrderEqualTo(Integer value) {
            addCriterion("exe_function_order =", value, "exeFunctionOrder");
            return (Criteria) this;
        }

        public Criteria andExeFunctionOrderNotEqualTo(Integer value) {
            addCriterion("exe_function_order <>", value, "exeFunctionOrder");
            return (Criteria) this;
        }

        public Criteria andExeFunctionOrderGreaterThan(Integer value) {
            addCriterion("exe_function_order >", value, "exeFunctionOrder");
            return (Criteria) this;
        }

        public Criteria andExeFunctionOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("exe_function_order >=", value, "exeFunctionOrder");
            return (Criteria) this;
        }

        public Criteria andExeFunctionOrderLessThan(Integer value) {
            addCriterion("exe_function_order <", value, "exeFunctionOrder");
            return (Criteria) this;
        }

        public Criteria andExeFunctionOrderLessThanOrEqualTo(Integer value) {
            addCriterion("exe_function_order <=", value, "exeFunctionOrder");
            return (Criteria) this;
        }

        public Criteria andExeFunctionOrderIn(List<Integer> values) {
            addCriterion("exe_function_order in", values, "exeFunctionOrder");
            return (Criteria) this;
        }

        public Criteria andExeFunctionOrderNotIn(List<Integer> values) {
            addCriterion("exe_function_order not in", values, "exeFunctionOrder");
            return (Criteria) this;
        }

        public Criteria andExeFunctionOrderBetween(Integer value1, Integer value2) {
            addCriterion("exe_function_order between", value1, value2, "exeFunctionOrder");
            return (Criteria) this;
        }

        public Criteria andExeFunctionOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("exe_function_order not between", value1, value2, "exeFunctionOrder");
            return (Criteria) this;
        }

        public Criteria andCurrentStepNumIsNull() {
            addCriterion("current_step_num is null");
            return (Criteria) this;
        }

        public Criteria andCurrentStepNumIsNotNull() {
            addCriterion("current_step_num is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentStepNumEqualTo(Integer value) {
            addCriterion("current_step_num =", value, "currentStepNum");
            return (Criteria) this;
        }

        public Criteria andCurrentStepNumNotEqualTo(Integer value) {
            addCriterion("current_step_num <>", value, "currentStepNum");
            return (Criteria) this;
        }

        public Criteria andCurrentStepNumGreaterThan(Integer value) {
            addCriterion("current_step_num >", value, "currentStepNum");
            return (Criteria) this;
        }

        public Criteria andCurrentStepNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("current_step_num >=", value, "currentStepNum");
            return (Criteria) this;
        }

        public Criteria andCurrentStepNumLessThan(Integer value) {
            addCriterion("current_step_num <", value, "currentStepNum");
            return (Criteria) this;
        }

        public Criteria andCurrentStepNumLessThanOrEqualTo(Integer value) {
            addCriterion("current_step_num <=", value, "currentStepNum");
            return (Criteria) this;
        }

        public Criteria andCurrentStepNumIn(List<Integer> values) {
            addCriterion("current_step_num in", values, "currentStepNum");
            return (Criteria) this;
        }

        public Criteria andCurrentStepNumNotIn(List<Integer> values) {
            addCriterion("current_step_num not in", values, "currentStepNum");
            return (Criteria) this;
        }

        public Criteria andCurrentStepNumBetween(Integer value1, Integer value2) {
            addCriterion("current_step_num between", value1, value2, "currentStepNum");
            return (Criteria) this;
        }

        public Criteria andCurrentStepNumNotBetween(Integer value1, Integer value2) {
            addCriterion("current_step_num not between", value1, value2, "currentStepNum");
            return (Criteria) this;
        }

        public Criteria andExeStatusIsNull() {
            addCriterion("exe_status is null");
            return (Criteria) this;
        }

        public Criteria andExeStatusIsNotNull() {
            addCriterion("exe_status is not null");
            return (Criteria) this;
        }

        public Criteria andExeStatusEqualTo(Integer value) {
            addCriterion("exe_status =", value, "exeStatus");
            return (Criteria) this;
        }

        public Criteria andExeStatusNotEqualTo(Integer value) {
            addCriterion("exe_status <>", value, "exeStatus");
            return (Criteria) this;
        }

        public Criteria andExeStatusGreaterThan(Integer value) {
            addCriterion("exe_status >", value, "exeStatus");
            return (Criteria) this;
        }

        public Criteria andExeStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("exe_status >=", value, "exeStatus");
            return (Criteria) this;
        }

        public Criteria andExeStatusLessThan(Integer value) {
            addCriterion("exe_status <", value, "exeStatus");
            return (Criteria) this;
        }

        public Criteria andExeStatusLessThanOrEqualTo(Integer value) {
            addCriterion("exe_status <=", value, "exeStatus");
            return (Criteria) this;
        }

        public Criteria andExeStatusIn(List<Integer> values) {
            addCriterion("exe_status in", values, "exeStatus");
            return (Criteria) this;
        }

        public Criteria andExeStatusNotIn(List<Integer> values) {
            addCriterion("exe_status not in", values, "exeStatus");
            return (Criteria) this;
        }

        public Criteria andExeStatusBetween(Integer value1, Integer value2) {
            addCriterion("exe_status between", value1, value2, "exeStatus");
            return (Criteria) this;
        }

        public Criteria andExeStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("exe_status not between", value1, value2, "exeStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusIsNull() {
            addCriterion("verify_status is null");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusIsNotNull() {
            addCriterion("verify_status is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusEqualTo(Integer value) {
            addCriterion("verify_status =", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusNotEqualTo(Integer value) {
            addCriterion("verify_status <>", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusGreaterThan(Integer value) {
            addCriterion("verify_status >", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("verify_status >=", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusLessThan(Integer value) {
            addCriterion("verify_status <", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusLessThanOrEqualTo(Integer value) {
            addCriterion("verify_status <=", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusIn(List<Integer> values) {
            addCriterion("verify_status in", values, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusNotIn(List<Integer> values) {
            addCriterion("verify_status not in", values, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusBetween(Integer value1, Integer value2) {
            addCriterion("verify_status between", value1, value2, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("verify_status not between", value1, value2, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyNumIsNull() {
            addCriterion("verify_num is null");
            return (Criteria) this;
        }

        public Criteria andVerifyNumIsNotNull() {
            addCriterion("verify_num is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyNumEqualTo(Integer value) {
            addCriterion("verify_num =", value, "verifyNum");
            return (Criteria) this;
        }

        public Criteria andVerifyNumNotEqualTo(Integer value) {
            addCriterion("verify_num <>", value, "verifyNum");
            return (Criteria) this;
        }

        public Criteria andVerifyNumGreaterThan(Integer value) {
            addCriterion("verify_num >", value, "verifyNum");
            return (Criteria) this;
        }

        public Criteria andVerifyNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("verify_num >=", value, "verifyNum");
            return (Criteria) this;
        }

        public Criteria andVerifyNumLessThan(Integer value) {
            addCriterion("verify_num <", value, "verifyNum");
            return (Criteria) this;
        }

        public Criteria andVerifyNumLessThanOrEqualTo(Integer value) {
            addCriterion("verify_num <=", value, "verifyNum");
            return (Criteria) this;
        }

        public Criteria andVerifyNumIn(List<Integer> values) {
            addCriterion("verify_num in", values, "verifyNum");
            return (Criteria) this;
        }

        public Criteria andVerifyNumNotIn(List<Integer> values) {
            addCriterion("verify_num not in", values, "verifyNum");
            return (Criteria) this;
        }

        public Criteria andVerifyNumBetween(Integer value1, Integer value2) {
            addCriterion("verify_num between", value1, value2, "verifyNum");
            return (Criteria) this;
        }

        public Criteria andVerifyNumNotBetween(Integer value1, Integer value2) {
            addCriterion("verify_num not between", value1, value2, "verifyNum");
            return (Criteria) this;
        }

        public Criteria andMilitaryStatusIsNull() {
            addCriterion("military_status is null");
            return (Criteria) this;
        }

        public Criteria andMilitaryStatusIsNotNull() {
            addCriterion("military_status is not null");
            return (Criteria) this;
        }

        public Criteria andMilitaryStatusEqualTo(Integer value) {
            addCriterion("military_status =", value, "militaryStatus");
            return (Criteria) this;
        }

        public Criteria andMilitaryStatusNotEqualTo(Integer value) {
            addCriterion("military_status <>", value, "militaryStatus");
            return (Criteria) this;
        }

        public Criteria andMilitaryStatusGreaterThan(Integer value) {
            addCriterion("military_status >", value, "militaryStatus");
            return (Criteria) this;
        }

        public Criteria andMilitaryStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("military_status >=", value, "militaryStatus");
            return (Criteria) this;
        }

        public Criteria andMilitaryStatusLessThan(Integer value) {
            addCriterion("military_status <", value, "militaryStatus");
            return (Criteria) this;
        }

        public Criteria andMilitaryStatusLessThanOrEqualTo(Integer value) {
            addCriterion("military_status <=", value, "militaryStatus");
            return (Criteria) this;
        }

        public Criteria andMilitaryStatusIn(List<Integer> values) {
            addCriterion("military_status in", values, "militaryStatus");
            return (Criteria) this;
        }

        public Criteria andMilitaryStatusNotIn(List<Integer> values) {
            addCriterion("military_status not in", values, "militaryStatus");
            return (Criteria) this;
        }

        public Criteria andMilitaryStatusBetween(Integer value1, Integer value2) {
            addCriterion("military_status between", value1, value2, "militaryStatus");
            return (Criteria) this;
        }

        public Criteria andMilitaryStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("military_status not between", value1, value2, "militaryStatus");
            return (Criteria) this;
        }

        public Criteria andMilitaryNumIsNull() {
            addCriterion("military_num is null");
            return (Criteria) this;
        }

        public Criteria andMilitaryNumIsNotNull() {
            addCriterion("military_num is not null");
            return (Criteria) this;
        }

        public Criteria andMilitaryNumEqualTo(Integer value) {
            addCriterion("military_num =", value, "militaryNum");
            return (Criteria) this;
        }

        public Criteria andMilitaryNumNotEqualTo(Integer value) {
            addCriterion("military_num <>", value, "militaryNum");
            return (Criteria) this;
        }

        public Criteria andMilitaryNumGreaterThan(Integer value) {
            addCriterion("military_num >", value, "militaryNum");
            return (Criteria) this;
        }

        public Criteria andMilitaryNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("military_num >=", value, "militaryNum");
            return (Criteria) this;
        }

        public Criteria andMilitaryNumLessThan(Integer value) {
            addCriterion("military_num <", value, "militaryNum");
            return (Criteria) this;
        }

        public Criteria andMilitaryNumLessThanOrEqualTo(Integer value) {
            addCriterion("military_num <=", value, "militaryNum");
            return (Criteria) this;
        }

        public Criteria andMilitaryNumIn(List<Integer> values) {
            addCriterion("military_num in", values, "militaryNum");
            return (Criteria) this;
        }

        public Criteria andMilitaryNumNotIn(List<Integer> values) {
            addCriterion("military_num not in", values, "militaryNum");
            return (Criteria) this;
        }

        public Criteria andMilitaryNumBetween(Integer value1, Integer value2) {
            addCriterion("military_num between", value1, value2, "militaryNum");
            return (Criteria) this;
        }

        public Criteria andMilitaryNumNotBetween(Integer value1, Integer value2) {
            addCriterion("military_num not between", value1, value2, "militaryNum");
            return (Criteria) this;
        }

        public Criteria andVersionDescriptionIsNull() {
            addCriterion("version_description is null");
            return (Criteria) this;
        }

        public Criteria andVersionDescriptionIsNotNull() {
            addCriterion("version_description is not null");
            return (Criteria) this;
        }

        public Criteria andVersionDescriptionEqualTo(String value) {
            addCriterion("version_description =", value, "versionDescription");
            return (Criteria) this;
        }

        public Criteria andVersionDescriptionNotEqualTo(String value) {
            addCriterion("version_description <>", value, "versionDescription");
            return (Criteria) this;
        }

        public Criteria andVersionDescriptionGreaterThan(String value) {
            addCriterion("version_description >", value, "versionDescription");
            return (Criteria) this;
        }

        public Criteria andVersionDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("version_description >=", value, "versionDescription");
            return (Criteria) this;
        }

        public Criteria andVersionDescriptionLessThan(String value) {
            addCriterion("version_description <", value, "versionDescription");
            return (Criteria) this;
        }

        public Criteria andVersionDescriptionLessThanOrEqualTo(String value) {
            addCriterion("version_description <=", value, "versionDescription");
            return (Criteria) this;
        }

        public Criteria andVersionDescriptionLike(String value) {
            addCriterion("version_description like", value, "versionDescription");
            return (Criteria) this;
        }

        public Criteria andVersionDescriptionNotLike(String value) {
            addCriterion("version_description not like", value, "versionDescription");
            return (Criteria) this;
        }

        public Criteria andVersionDescriptionIn(List<String> values) {
            addCriterion("version_description in", values, "versionDescription");
            return (Criteria) this;
        }

        public Criteria andVersionDescriptionNotIn(List<String> values) {
            addCriterion("version_description not in", values, "versionDescription");
            return (Criteria) this;
        }

        public Criteria andVersionDescriptionBetween(String value1, String value2) {
            addCriterion("version_description between", value1, value2, "versionDescription");
            return (Criteria) this;
        }

        public Criteria andVersionDescriptionNotBetween(String value1, String value2) {
            addCriterion("version_description not between", value1, value2, "versionDescription");
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

        public Criteria andKeyProCountIsNull() {
            addCriterion("key_pro_count is null");
            return (Criteria) this;
        }

        public Criteria andKeyProCountIsNotNull() {
            addCriterion("key_pro_count is not null");
            return (Criteria) this;
        }

        public Criteria andKeyProCountEqualTo(Integer value) {
            addCriterion("key_pro_count =", value, "keyProCount");
            return (Criteria) this;
        }

        public Criteria andKeyProCountNotEqualTo(Integer value) {
            addCriterion("key_pro_count <>", value, "keyProCount");
            return (Criteria) this;
        }

        public Criteria andKeyProCountGreaterThan(Integer value) {
            addCriterion("key_pro_count >", value, "keyProCount");
            return (Criteria) this;
        }

        public Criteria andKeyProCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("key_pro_count >=", value, "keyProCount");
            return (Criteria) this;
        }

        public Criteria andKeyProCountLessThan(Integer value) {
            addCriterion("key_pro_count <", value, "keyProCount");
            return (Criteria) this;
        }

        public Criteria andKeyProCountLessThanOrEqualTo(Integer value) {
            addCriterion("key_pro_count <=", value, "keyProCount");
            return (Criteria) this;
        }

        public Criteria andKeyProCountIn(List<Integer> values) {
            addCriterion("key_pro_count in", values, "keyProCount");
            return (Criteria) this;
        }

        public Criteria andKeyProCountNotIn(List<Integer> values) {
            addCriterion("key_pro_count not in", values, "keyProCount");
            return (Criteria) this;
        }

        public Criteria andKeyProCountBetween(Integer value1, Integer value2) {
            addCriterion("key_pro_count between", value1, value2, "keyProCount");
            return (Criteria) this;
        }

        public Criteria andKeyProCountNotBetween(Integer value1, Integer value2) {
            addCriterion("key_pro_count not between", value1, value2, "keyProCount");
            return (Criteria) this;
        }

        public Criteria andChangeFlagIsNull() {
            addCriterion("change_flag is null");
            return (Criteria) this;
        }

        public Criteria andChangeFlagIsNotNull() {
            addCriterion("change_flag is not null");
            return (Criteria) this;
        }

        public Criteria andChangeFlagEqualTo(Integer value) {
            addCriterion("change_flag =", value, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andChangeFlagNotEqualTo(Integer value) {
            addCriterion("change_flag <>", value, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andChangeFlagGreaterThan(Integer value) {
            addCriterion("change_flag >", value, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andChangeFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("change_flag >=", value, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andChangeFlagLessThan(Integer value) {
            addCriterion("change_flag <", value, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andChangeFlagLessThanOrEqualTo(Integer value) {
            addCriterion("change_flag <=", value, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andChangeFlagIn(List<Integer> values) {
            addCriterion("change_flag in", values, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andChangeFlagNotIn(List<Integer> values) {
            addCriterion("change_flag not in", values, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andChangeFlagBetween(Integer value1, Integer value2) {
            addCriterion("change_flag between", value1, value2, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andChangeFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("change_flag not between", value1, value2, "changeFlag");
            return (Criteria) this;
        }

        public Criteria andFlowVersionLineIsNull() {
            addCriterion("flow_version_line is null");
            return (Criteria) this;
        }

        public Criteria andFlowVersionLineIsNotNull() {
            addCriterion("flow_version_line is not null");
            return (Criteria) this;
        }

        public Criteria andFlowVersionLineEqualTo(String value) {
            addCriterion("flow_version_line =", value, "flowVersionLine");
            return (Criteria) this;
        }

        public Criteria andFlowVersionLineNotEqualTo(String value) {
            addCriterion("flow_version_line <>", value, "flowVersionLine");
            return (Criteria) this;
        }

        public Criteria andFlowVersionLineGreaterThan(String value) {
            addCriterion("flow_version_line >", value, "flowVersionLine");
            return (Criteria) this;
        }

        public Criteria andFlowVersionLineGreaterThanOrEqualTo(String value) {
            addCriterion("flow_version_line >=", value, "flowVersionLine");
            return (Criteria) this;
        }

        public Criteria andFlowVersionLineLessThan(String value) {
            addCriterion("flow_version_line <", value, "flowVersionLine");
            return (Criteria) this;
        }

        public Criteria andFlowVersionLineLessThanOrEqualTo(String value) {
            addCriterion("flow_version_line <=", value, "flowVersionLine");
            return (Criteria) this;
        }

        public Criteria andFlowVersionLineLike(String value) {
            addCriterion("flow_version_line like", value, "flowVersionLine");
            return (Criteria) this;
        }

        public Criteria andFlowVersionLineNotLike(String value) {
            addCriterion("flow_version_line not like", value, "flowVersionLine");
            return (Criteria) this;
        }

        public Criteria andFlowVersionLineIn(List<String> values) {
            addCriterion("flow_version_line in", values, "flowVersionLine");
            return (Criteria) this;
        }

        public Criteria andFlowVersionLineNotIn(List<String> values) {
            addCriterion("flow_version_line not in", values, "flowVersionLine");
            return (Criteria) this;
        }

        public Criteria andFlowVersionLineBetween(String value1, String value2) {
            addCriterion("flow_version_line between", value1, value2, "flowVersionLine");
            return (Criteria) this;
        }

        public Criteria andFlowVersionLineNotBetween(String value1, String value2) {
            addCriterion("flow_version_line not between", value1, value2, "flowVersionLine");
            return (Criteria) this;
        }

        public Criteria andDependsOnIsNull() {
            addCriterion("depends_on is null");
            return (Criteria) this;
        }

        public Criteria andDependsOnIsNotNull() {
            addCriterion("depends_on is not null");
            return (Criteria) this;
        }

        public Criteria andDependsOnEqualTo(String value) {
            addCriterion("depends_on =", value, "dependsOn");
            return (Criteria) this;
        }

        public Criteria andDependsOnNotEqualTo(String value) {
            addCriterion("depends_on <>", value, "dependsOn");
            return (Criteria) this;
        }

        public Criteria andDependsOnGreaterThan(String value) {
            addCriterion("depends_on >", value, "dependsOn");
            return (Criteria) this;
        }

        public Criteria andDependsOnGreaterThanOrEqualTo(String value) {
            addCriterion("depends_on >=", value, "dependsOn");
            return (Criteria) this;
        }

        public Criteria andDependsOnLessThan(String value) {
            addCriterion("depends_on <", value, "dependsOn");
            return (Criteria) this;
        }

        public Criteria andDependsOnLessThanOrEqualTo(String value) {
            addCriterion("depends_on <=", value, "dependsOn");
            return (Criteria) this;
        }

        public Criteria andDependsOnLike(String value) {
            addCriterion("depends_on like", value, "dependsOn");
            return (Criteria) this;
        }

        public Criteria andDependsOnNotLike(String value) {
            addCriterion("depends_on not like", value, "dependsOn");
            return (Criteria) this;
        }

        public Criteria andDependsOnIn(List<String> values) {
            addCriterion("depends_on in", values, "dependsOn");
            return (Criteria) this;
        }

        public Criteria andDependsOnNotIn(List<String> values) {
            addCriterion("depends_on not in", values, "dependsOn");
            return (Criteria) this;
        }

        public Criteria andDependsOnBetween(String value1, String value2) {
            addCriterion("depends_on between", value1, value2, "dependsOn");
            return (Criteria) this;
        }

        public Criteria andDependsOnNotBetween(String value1, String value2) {
            addCriterion("depends_on not between", value1, value2, "dependsOn");
            return (Criteria) this;
        }

        public Criteria andResultCommentsIsNull() {
            addCriterion("result_comments is null");
            return (Criteria) this;
        }

        public Criteria andResultCommentsIsNotNull() {
            addCriterion("result_comments is not null");
            return (Criteria) this;
        }

        public Criteria andResultCommentsEqualTo(String value) {
            addCriterion("result_comments =", value, "resultComments");
            return (Criteria) this;
        }

        public Criteria andResultCommentsNotEqualTo(String value) {
            addCriterion("result_comments <>", value, "resultComments");
            return (Criteria) this;
        }

        public Criteria andResultCommentsGreaterThan(String value) {
            addCriterion("result_comments >", value, "resultComments");
            return (Criteria) this;
        }

        public Criteria andResultCommentsGreaterThanOrEqualTo(String value) {
            addCriterion("result_comments >=", value, "resultComments");
            return (Criteria) this;
        }

        public Criteria andResultCommentsLessThan(String value) {
            addCriterion("result_comments <", value, "resultComments");
            return (Criteria) this;
        }

        public Criteria andResultCommentsLessThanOrEqualTo(String value) {
            addCriterion("result_comments <=", value, "resultComments");
            return (Criteria) this;
        }

        public Criteria andResultCommentsLike(String value) {
            addCriterion("result_comments like", value, "resultComments");
            return (Criteria) this;
        }

        public Criteria andResultCommentsNotLike(String value) {
            addCriterion("result_comments not like", value, "resultComments");
            return (Criteria) this;
        }

        public Criteria andResultCommentsIn(List<String> values) {
            addCriterion("result_comments in", values, "resultComments");
            return (Criteria) this;
        }

        public Criteria andResultCommentsNotIn(List<String> values) {
            addCriterion("result_comments not in", values, "resultComments");
            return (Criteria) this;
        }

        public Criteria andResultCommentsBetween(String value1, String value2) {
            addCriterion("result_comments between", value1, value2, "resultComments");
            return (Criteria) this;
        }

        public Criteria andResultCommentsNotBetween(String value1, String value2) {
            addCriterion("result_comments not between", value1, value2, "resultComments");
            return (Criteria) this;
        }

        public Criteria andCautionIsNull() {
            addCriterion("caution is null");
            return (Criteria) this;
        }

        public Criteria andCautionIsNotNull() {
            addCriterion("caution is not null");
            return (Criteria) this;
        }

        public Criteria andCautionEqualTo(String value) {
            addCriterion("caution =", value, "caution");
            return (Criteria) this;
        }

        public Criteria andCautionNotEqualTo(String value) {
            addCriterion("caution <>", value, "caution");
            return (Criteria) this;
        }

        public Criteria andCautionGreaterThan(String value) {
            addCriterion("caution >", value, "caution");
            return (Criteria) this;
        }

        public Criteria andCautionGreaterThanOrEqualTo(String value) {
            addCriterion("caution >=", value, "caution");
            return (Criteria) this;
        }

        public Criteria andCautionLessThan(String value) {
            addCriterion("caution <", value, "caution");
            return (Criteria) this;
        }

        public Criteria andCautionLessThanOrEqualTo(String value) {
            addCriterion("caution <=", value, "caution");
            return (Criteria) this;
        }

        public Criteria andCautionLike(String value) {
            addCriterion("caution like", value, "caution");
            return (Criteria) this;
        }

        public Criteria andCautionNotLike(String value) {
            addCriterion("caution not like", value, "caution");
            return (Criteria) this;
        }

        public Criteria andCautionIn(List<String> values) {
            addCriterion("caution in", values, "caution");
            return (Criteria) this;
        }

        public Criteria andCautionNotIn(List<String> values) {
            addCriterion("caution not in", values, "caution");
            return (Criteria) this;
        }

        public Criteria andCautionBetween(String value1, String value2) {
            addCriterion("caution between", value1, value2, "caution");
            return (Criteria) this;
        }

        public Criteria andCautionNotBetween(String value1, String value2) {
            addCriterion("caution not between", value1, value2, "caution");
            return (Criteria) this;
        }

        public Criteria andIsReadyIsNull() {
            addCriterion("is_ready is null");
            return (Criteria) this;
        }

        public Criteria andIsReadyIsNotNull() {
            addCriterion("is_ready is not null");
            return (Criteria) this;
        }

        public Criteria andIsReadyEqualTo(Boolean value) {
            addCriterion("is_ready =", value, "isReady");
            return (Criteria) this;
        }

        public Criteria andIsReadyNotEqualTo(Boolean value) {
            addCriterion("is_ready <>", value, "isReady");
            return (Criteria) this;
        }

        public Criteria andIsReadyGreaterThan(Boolean value) {
            addCriterion("is_ready >", value, "isReady");
            return (Criteria) this;
        }

        public Criteria andIsReadyGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_ready >=", value, "isReady");
            return (Criteria) this;
        }

        public Criteria andIsReadyLessThan(Boolean value) {
            addCriterion("is_ready <", value, "isReady");
            return (Criteria) this;
        }

        public Criteria andIsReadyLessThanOrEqualTo(Boolean value) {
            addCriterion("is_ready <=", value, "isReady");
            return (Criteria) this;
        }

        public Criteria andIsReadyIn(List<Boolean> values) {
            addCriterion("is_ready in", values, "isReady");
            return (Criteria) this;
        }

        public Criteria andIsReadyNotIn(List<Boolean> values) {
            addCriterion("is_ready not in", values, "isReady");
            return (Criteria) this;
        }

        public Criteria andIsReadyBetween(Boolean value1, Boolean value2) {
            addCriterion("is_ready between", value1, value2, "isReady");
            return (Criteria) this;
        }

        public Criteria andIsReadyNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_ready not between", value1, value2, "isReady");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
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

        public Criteria andCalBeforeTimeIsNull() {
            addCriterion("cal_before_time is null");
            return (Criteria) this;
        }

        public Criteria andCalBeforeTimeIsNotNull() {
            addCriterion("cal_before_time is not null");
            return (Criteria) this;
        }

        public Criteria andCalBeforeTimeEqualTo(Date value) {
            addCriterion("cal_before_time =", value, "calBeforeTime");
            return (Criteria) this;
        }

        public Criteria andCalBeforeTimeNotEqualTo(Date value) {
            addCriterion("cal_before_time <>", value, "calBeforeTime");
            return (Criteria) this;
        }

        public Criteria andCalBeforeTimeGreaterThan(Date value) {
            addCriterion("cal_before_time >", value, "calBeforeTime");
            return (Criteria) this;
        }

        public Criteria andCalBeforeTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("cal_before_time >=", value, "calBeforeTime");
            return (Criteria) this;
        }

        public Criteria andCalBeforeTimeLessThan(Date value) {
            addCriterion("cal_before_time <", value, "calBeforeTime");
            return (Criteria) this;
        }

        public Criteria andCalBeforeTimeLessThanOrEqualTo(Date value) {
            addCriterion("cal_before_time <=", value, "calBeforeTime");
            return (Criteria) this;
        }

        public Criteria andCalBeforeTimeIn(List<Date> values) {
            addCriterion("cal_before_time in", values, "calBeforeTime");
            return (Criteria) this;
        }

        public Criteria andCalBeforeTimeNotIn(List<Date> values) {
            addCriterion("cal_before_time not in", values, "calBeforeTime");
            return (Criteria) this;
        }

        public Criteria andCalBeforeTimeBetween(Date value1, Date value2) {
            addCriterion("cal_before_time between", value1, value2, "calBeforeTime");
            return (Criteria) this;
        }

        public Criteria andCalBeforeTimeNotBetween(Date value1, Date value2) {
            addCriterion("cal_before_time not between", value1, value2, "calBeforeTime");
            return (Criteria) this;
        }

        public Criteria andCalTimeIsNull() {
            addCriterion("cal_time is null");
            return (Criteria) this;
        }

        public Criteria andCalTimeIsNotNull() {
            addCriterion("cal_time is not null");
            return (Criteria) this;
        }

        public Criteria andCalTimeEqualTo(String value) {
            addCriterion("cal_time =", value, "calTime");
            return (Criteria) this;
        }

        public Criteria andCalTimeNotEqualTo(String value) {
            addCriterion("cal_time <>", value, "calTime");
            return (Criteria) this;
        }

        public Criteria andCalTimeGreaterThan(String value) {
            addCriterion("cal_time >", value, "calTime");
            return (Criteria) this;
        }

        public Criteria andCalTimeGreaterThanOrEqualTo(String value) {
            addCriterion("cal_time >=", value, "calTime");
            return (Criteria) this;
        }

        public Criteria andCalTimeLessThan(String value) {
            addCriterion("cal_time <", value, "calTime");
            return (Criteria) this;
        }

        public Criteria andCalTimeLessThanOrEqualTo(String value) {
            addCriterion("cal_time <=", value, "calTime");
            return (Criteria) this;
        }

        public Criteria andCalTimeLike(String value) {
            addCriterion("cal_time like", value, "calTime");
            return (Criteria) this;
        }

        public Criteria andCalTimeNotLike(String value) {
            addCriterion("cal_time not like", value, "calTime");
            return (Criteria) this;
        }

        public Criteria andCalTimeIn(List<String> values) {
            addCriterion("cal_time in", values, "calTime");
            return (Criteria) this;
        }

        public Criteria andCalTimeNotIn(List<String> values) {
            addCriterion("cal_time not in", values, "calTime");
            return (Criteria) this;
        }

        public Criteria andCalTimeBetween(String value1, String value2) {
            addCriterion("cal_time between", value1, value2, "calTime");
            return (Criteria) this;
        }

        public Criteria andCalTimeNotBetween(String value1, String value2) {
            addCriterion("cal_time not between", value1, value2, "calTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeIsNull() {
            addCriterion("execute_time is null");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeIsNotNull() {
            addCriterion("execute_time is not null");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeEqualTo(Integer value) {
            addCriterion("execute_time =", value, "executeTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeNotEqualTo(Integer value) {
            addCriterion("execute_time <>", value, "executeTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeGreaterThan(Integer value) {
            addCriterion("execute_time >", value, "executeTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("execute_time >=", value, "executeTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeLessThan(Integer value) {
            addCriterion("execute_time <", value, "executeTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeLessThanOrEqualTo(Integer value) {
            addCriterion("execute_time <=", value, "executeTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeIn(List<Integer> values) {
            addCriterion("execute_time in", values, "executeTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeNotIn(List<Integer> values) {
            addCriterion("execute_time not in", values, "executeTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeBetween(Integer value1, Integer value2) {
            addCriterion("execute_time between", value1, value2, "executeTime");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("execute_time not between", value1, value2, "executeTime");
            return (Criteria) this;
        }

        public Criteria andRedoCountIsNull() {
            addCriterion("redo_count is null");
            return (Criteria) this;
        }

        public Criteria andRedoCountIsNotNull() {
            addCriterion("redo_count is not null");
            return (Criteria) this;
        }

        public Criteria andRedoCountEqualTo(String value) {
            addCriterion("redo_count =", value, "redoCount");
            return (Criteria) this;
        }

        public Criteria andRedoCountNotEqualTo(String value) {
            addCriterion("redo_count <>", value, "redoCount");
            return (Criteria) this;
        }

        public Criteria andRedoCountGreaterThan(String value) {
            addCriterion("redo_count >", value, "redoCount");
            return (Criteria) this;
        }

        public Criteria andRedoCountGreaterThanOrEqualTo(String value) {
            addCriterion("redo_count >=", value, "redoCount");
            return (Criteria) this;
        }

        public Criteria andRedoCountLessThan(String value) {
            addCriterion("redo_count <", value, "redoCount");
            return (Criteria) this;
        }

        public Criteria andRedoCountLessThanOrEqualTo(String value) {
            addCriterion("redo_count <=", value, "redoCount");
            return (Criteria) this;
        }

        public Criteria andRedoCountLike(String value) {
            addCriterion("redo_count like", value, "redoCount");
            return (Criteria) this;
        }

        public Criteria andRedoCountNotLike(String value) {
            addCriterion("redo_count not like", value, "redoCount");
            return (Criteria) this;
        }

        public Criteria andRedoCountIn(List<String> values) {
            addCriterion("redo_count in", values, "redoCount");
            return (Criteria) this;
        }

        public Criteria andRedoCountNotIn(List<String> values) {
            addCriterion("redo_count not in", values, "redoCount");
            return (Criteria) this;
        }

        public Criteria andRedoCountBetween(String value1, String value2) {
            addCriterion("redo_count between", value1, value2, "redoCount");
            return (Criteria) this;
        }

        public Criteria andRedoCountNotBetween(String value1, String value2) {
            addCriterion("redo_count not between", value1, value2, "redoCount");
            return (Criteria) this;
        }

        public Criteria andDetectIdIsNull() {
            addCriterion("detect_id is null");
            return (Criteria) this;
        }

        public Criteria andDetectIdIsNotNull() {
            addCriterion("detect_id is not null");
            return (Criteria) this;
        }

        public Criteria andDetectIdEqualTo(String value) {
            addCriterion("detect_id =", value, "detectId");
            return (Criteria) this;
        }

        public Criteria andDetectIdNotEqualTo(String value) {
            addCriterion("detect_id <>", value, "detectId");
            return (Criteria) this;
        }

        public Criteria andDetectIdGreaterThan(String value) {
            addCriterion("detect_id >", value, "detectId");
            return (Criteria) this;
        }

        public Criteria andDetectIdGreaterThanOrEqualTo(String value) {
            addCriterion("detect_id >=", value, "detectId");
            return (Criteria) this;
        }

        public Criteria andDetectIdLessThan(String value) {
            addCriterion("detect_id <", value, "detectId");
            return (Criteria) this;
        }

        public Criteria andDetectIdLessThanOrEqualTo(String value) {
            addCriterion("detect_id <=", value, "detectId");
            return (Criteria) this;
        }

        public Criteria andDetectIdLike(String value) {
            addCriterion("detect_id like", value, "detectId");
            return (Criteria) this;
        }

        public Criteria andDetectIdNotLike(String value) {
            addCriterion("detect_id not like", value, "detectId");
            return (Criteria) this;
        }

        public Criteria andDetectIdIn(List<String> values) {
            addCriterion("detect_id in", values, "detectId");
            return (Criteria) this;
        }

        public Criteria andDetectIdNotIn(List<String> values) {
            addCriterion("detect_id not in", values, "detectId");
            return (Criteria) this;
        }

        public Criteria andDetectIdBetween(String value1, String value2) {
            addCriterion("detect_id between", value1, value2, "detectId");
            return (Criteria) this;
        }

        public Criteria andDetectIdNotBetween(String value1, String value2) {
            addCriterion("detect_id not between", value1, value2, "detectId");
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