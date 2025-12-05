package com.hengtiansoft.fastop.model.planner.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExeStepExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExeStepExample() {
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

        public Criteria andExeStepIdIsNull() {
            addCriterion("exe_step_id is null");
            return (Criteria) this;
        }

        public Criteria andExeStepIdIsNotNull() {
            addCriterion("exe_step_id is not null");
            return (Criteria) this;
        }

        public Criteria andExeStepIdEqualTo(String value) {
            addCriterion("exe_step_id =", value, "exeStepId");
            return (Criteria) this;
        }

        public Criteria andExeStepIdNotEqualTo(String value) {
            addCriterion("exe_step_id <>", value, "exeStepId");
            return (Criteria) this;
        }

        public Criteria andExeStepIdGreaterThan(String value) {
            addCriterion("exe_step_id >", value, "exeStepId");
            return (Criteria) this;
        }

        public Criteria andExeStepIdGreaterThanOrEqualTo(String value) {
            addCriterion("exe_step_id >=", value, "exeStepId");
            return (Criteria) this;
        }

        public Criteria andExeStepIdLessThan(String value) {
            addCriterion("exe_step_id <", value, "exeStepId");
            return (Criteria) this;
        }

        public Criteria andExeStepIdLessThanOrEqualTo(String value) {
            addCriterion("exe_step_id <=", value, "exeStepId");
            return (Criteria) this;
        }

        public Criteria andExeStepIdLike(String value) {
            addCriterion("exe_step_id like", value, "exeStepId");
            return (Criteria) this;
        }

        public Criteria andExeStepIdNotLike(String value) {
            addCriterion("exe_step_id not like", value, "exeStepId");
            return (Criteria) this;
        }

        public Criteria andExeStepIdIn(List<String> values) {
            addCriterion("exe_step_id in", values, "exeStepId");
            return (Criteria) this;
        }

        public Criteria andExeStepIdNotIn(List<String> values) {
            addCriterion("exe_step_id not in", values, "exeStepId");
            return (Criteria) this;
        }

        public Criteria andExeStepIdBetween(String value1, String value2) {
            addCriterion("exe_step_id between", value1, value2, "exeStepId");
            return (Criteria) this;
        }

        public Criteria andExeStepIdNotBetween(String value1, String value2) {
            addCriterion("exe_step_id not between", value1, value2, "exeStepId");
            return (Criteria) this;
        }

        public Criteria andStepIdIsNull() {
            addCriterion("step_id is null");
            return (Criteria) this;
        }

        public Criteria andStepIdIsNotNull() {
            addCriterion("step_id is not null");
            return (Criteria) this;
        }

        public Criteria andStepIdEqualTo(Integer value) {
            addCriterion("step_id =", value, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepIdNotEqualTo(Integer value) {
            addCriterion("step_id <>", value, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepIdGreaterThan(Integer value) {
            addCriterion("step_id >", value, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("step_id >=", value, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepIdLessThan(Integer value) {
            addCriterion("step_id <", value, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepIdLessThanOrEqualTo(Integer value) {
            addCriterion("step_id <=", value, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepIdIn(List<Integer> values) {
            addCriterion("step_id in", values, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepIdNotIn(List<Integer> values) {
            addCriterion("step_id not in", values, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepIdBetween(Integer value1, Integer value2) {
            addCriterion("step_id between", value1, value2, "stepId");
            return (Criteria) this;
        }

        public Criteria andStepIdNotBetween(Integer value1, Integer value2) {
            addCriterion("step_id not between", value1, value2, "stepId");
            return (Criteria) this;
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

        public Criteria andStepLevelIsNull() {
            addCriterion("step_level is null");
            return (Criteria) this;
        }

        public Criteria andStepLevelIsNotNull() {
            addCriterion("step_level is not null");
            return (Criteria) this;
        }

        public Criteria andStepLevelEqualTo(Integer value) {
            addCriterion("step_level =", value, "stepLevel");
            return (Criteria) this;
        }

        public Criteria andStepLevelNotEqualTo(Integer value) {
            addCriterion("step_level <>", value, "stepLevel");
            return (Criteria) this;
        }

        public Criteria andStepLevelGreaterThan(Integer value) {
            addCriterion("step_level >", value, "stepLevel");
            return (Criteria) this;
        }

        public Criteria andStepLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("step_level >=", value, "stepLevel");
            return (Criteria) this;
        }

        public Criteria andStepLevelLessThan(Integer value) {
            addCriterion("step_level <", value, "stepLevel");
            return (Criteria) this;
        }

        public Criteria andStepLevelLessThanOrEqualTo(Integer value) {
            addCriterion("step_level <=", value, "stepLevel");
            return (Criteria) this;
        }

        public Criteria andStepLevelIn(List<Integer> values) {
            addCriterion("step_level in", values, "stepLevel");
            return (Criteria) this;
        }

        public Criteria andStepLevelNotIn(List<Integer> values) {
            addCriterion("step_level not in", values, "stepLevel");
            return (Criteria) this;
        }

        public Criteria andStepLevelBetween(Integer value1, Integer value2) {
            addCriterion("step_level between", value1, value2, "stepLevel");
            return (Criteria) this;
        }

        public Criteria andStepLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("step_level not between", value1, value2, "stepLevel");
            return (Criteria) this;
        }

        public Criteria andStepOrderIsNull() {
            addCriterion("step_order is null");
            return (Criteria) this;
        }

        public Criteria andStepOrderIsNotNull() {
            addCriterion("step_order is not null");
            return (Criteria) this;
        }

        public Criteria andStepOrderEqualTo(Integer value) {
            addCriterion("step_order =", value, "stepOrder");
            return (Criteria) this;
        }

        public Criteria andStepOrderNotEqualTo(Integer value) {
            addCriterion("step_order <>", value, "stepOrder");
            return (Criteria) this;
        }

        public Criteria andStepOrderGreaterThan(Integer value) {
            addCriterion("step_order >", value, "stepOrder");
            return (Criteria) this;
        }

        public Criteria andStepOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("step_order >=", value, "stepOrder");
            return (Criteria) this;
        }

        public Criteria andStepOrderLessThan(Integer value) {
            addCriterion("step_order <", value, "stepOrder");
            return (Criteria) this;
        }

        public Criteria andStepOrderLessThanOrEqualTo(Integer value) {
            addCriterion("step_order <=", value, "stepOrder");
            return (Criteria) this;
        }

        public Criteria andStepOrderIn(List<Integer> values) {
            addCriterion("step_order in", values, "stepOrder");
            return (Criteria) this;
        }

        public Criteria andStepOrderNotIn(List<Integer> values) {
            addCriterion("step_order not in", values, "stepOrder");
            return (Criteria) this;
        }

        public Criteria andStepOrderBetween(Integer value1, Integer value2) {
            addCriterion("step_order between", value1, value2, "stepOrder");
            return (Criteria) this;
        }

        public Criteria andStepOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("step_order not between", value1, value2, "stepOrder");
            return (Criteria) this;
        }

        public Criteria andLevelSeqIsNull() {
            addCriterion("level_seq is null");
            return (Criteria) this;
        }

        public Criteria andLevelSeqIsNotNull() {
            addCriterion("level_seq is not null");
            return (Criteria) this;
        }

        public Criteria andLevelSeqEqualTo(String value) {
            addCriterion("level_seq =", value, "levelSeq");
            return (Criteria) this;
        }

        public Criteria andLevelSeqNotEqualTo(String value) {
            addCriterion("level_seq <>", value, "levelSeq");
            return (Criteria) this;
        }

        public Criteria andLevelSeqGreaterThan(String value) {
            addCriterion("level_seq >", value, "levelSeq");
            return (Criteria) this;
        }

        public Criteria andLevelSeqGreaterThanOrEqualTo(String value) {
            addCriterion("level_seq >=", value, "levelSeq");
            return (Criteria) this;
        }

        public Criteria andLevelSeqLessThan(String value) {
            addCriterion("level_seq <", value, "levelSeq");
            return (Criteria) this;
        }

        public Criteria andLevelSeqLessThanOrEqualTo(String value) {
            addCriterion("level_seq <=", value, "levelSeq");
            return (Criteria) this;
        }

        public Criteria andLevelSeqLike(String value) {
            addCriterion("level_seq like", value, "levelSeq");
            return (Criteria) this;
        }

        public Criteria andLevelSeqNotLike(String value) {
            addCriterion("level_seq not like", value, "levelSeq");
            return (Criteria) this;
        }

        public Criteria andLevelSeqIn(List<String> values) {
            addCriterion("level_seq in", values, "levelSeq");
            return (Criteria) this;
        }

        public Criteria andLevelSeqNotIn(List<String> values) {
            addCriterion("level_seq not in", values, "levelSeq");
            return (Criteria) this;
        }

        public Criteria andLevelSeqBetween(String value1, String value2) {
            addCriterion("level_seq between", value1, value2, "levelSeq");
            return (Criteria) this;
        }

        public Criteria andLevelSeqNotBetween(String value1, String value2) {
            addCriterion("level_seq not between", value1, value2, "levelSeq");
            return (Criteria) this;
        }

        public Criteria andStepSeqIsNull() {
            addCriterion("step_seq is null");
            return (Criteria) this;
        }

        public Criteria andStepSeqIsNotNull() {
            addCriterion("step_seq is not null");
            return (Criteria) this;
        }

        public Criteria andStepSeqEqualTo(String value) {
            addCriterion("step_seq =", value, "stepSeq");
            return (Criteria) this;
        }

        public Criteria andStepSeqNotEqualTo(String value) {
            addCriterion("step_seq <>", value, "stepSeq");
            return (Criteria) this;
        }

        public Criteria andStepSeqGreaterThan(String value) {
            addCriterion("step_seq >", value, "stepSeq");
            return (Criteria) this;
        }

        public Criteria andStepSeqGreaterThanOrEqualTo(String value) {
            addCriterion("step_seq >=", value, "stepSeq");
            return (Criteria) this;
        }

        public Criteria andStepSeqLessThan(String value) {
            addCriterion("step_seq <", value, "stepSeq");
            return (Criteria) this;
        }

        public Criteria andStepSeqLessThanOrEqualTo(String value) {
            addCriterion("step_seq <=", value, "stepSeq");
            return (Criteria) this;
        }

        public Criteria andStepSeqLike(String value) {
            addCriterion("step_seq like", value, "stepSeq");
            return (Criteria) this;
        }

        public Criteria andStepSeqNotLike(String value) {
            addCriterion("step_seq not like", value, "stepSeq");
            return (Criteria) this;
        }

        public Criteria andStepSeqIn(List<String> values) {
            addCriterion("step_seq in", values, "stepSeq");
            return (Criteria) this;
        }

        public Criteria andStepSeqNotIn(List<String> values) {
            addCriterion("step_seq not in", values, "stepSeq");
            return (Criteria) this;
        }

        public Criteria andStepSeqBetween(String value1, String value2) {
            addCriterion("step_seq between", value1, value2, "stepSeq");
            return (Criteria) this;
        }

        public Criteria andStepSeqNotBetween(String value1, String value2) {
            addCriterion("step_seq not between", value1, value2, "stepSeq");
            return (Criteria) this;
        }

        public Criteria andStepDescriptionIsNull() {
            addCriterion("step_description is null");
            return (Criteria) this;
        }

        public Criteria andStepDescriptionIsNotNull() {
            addCriterion("step_description is not null");
            return (Criteria) this;
        }

        public Criteria andStepDescriptionEqualTo(String value) {
            addCriterion("step_description =", value, "stepDescription");
            return (Criteria) this;
        }

        public Criteria andStepDescriptionNotEqualTo(String value) {
            addCriterion("step_description <>", value, "stepDescription");
            return (Criteria) this;
        }

        public Criteria andStepDescriptionGreaterThan(String value) {
            addCriterion("step_description >", value, "stepDescription");
            return (Criteria) this;
        }

        public Criteria andStepDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("step_description >=", value, "stepDescription");
            return (Criteria) this;
        }

        public Criteria andStepDescriptionLessThan(String value) {
            addCriterion("step_description <", value, "stepDescription");
            return (Criteria) this;
        }

        public Criteria andStepDescriptionLessThanOrEqualTo(String value) {
            addCriterion("step_description <=", value, "stepDescription");
            return (Criteria) this;
        }

        public Criteria andStepDescriptionLike(String value) {
            addCriterion("step_description like", value, "stepDescription");
            return (Criteria) this;
        }

        public Criteria andStepDescriptionNotLike(String value) {
            addCriterion("step_description not like", value, "stepDescription");
            return (Criteria) this;
        }

        public Criteria andStepDescriptionIn(List<String> values) {
            addCriterion("step_description in", values, "stepDescription");
            return (Criteria) this;
        }

        public Criteria andStepDescriptionNotIn(List<String> values) {
            addCriterion("step_description not in", values, "stepDescription");
            return (Criteria) this;
        }

        public Criteria andStepDescriptionBetween(String value1, String value2) {
            addCriterion("step_description between", value1, value2, "stepDescription");
            return (Criteria) this;
        }

        public Criteria andStepDescriptionNotBetween(String value1, String value2) {
            addCriterion("step_description not between", value1, value2, "stepDescription");
            return (Criteria) this;
        }

        public Criteria andContentIdIsNull() {
            addCriterion("content_id is null");
            return (Criteria) this;
        }

        public Criteria andContentIdIsNotNull() {
            addCriterion("content_id is not null");
            return (Criteria) this;
        }

        public Criteria andContentIdEqualTo(String value) {
            addCriterion("content_id =", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdNotEqualTo(String value) {
            addCriterion("content_id <>", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdGreaterThan(String value) {
            addCriterion("content_id >", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdGreaterThanOrEqualTo(String value) {
            addCriterion("content_id >=", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdLessThan(String value) {
            addCriterion("content_id <", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdLessThanOrEqualTo(String value) {
            addCriterion("content_id <=", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdLike(String value) {
            addCriterion("content_id like", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdNotLike(String value) {
            addCriterion("content_id not like", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdIn(List<String> values) {
            addCriterion("content_id in", values, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdNotIn(List<String> values) {
            addCriterion("content_id not in", values, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdBetween(String value1, String value2) {
            addCriterion("content_id between", value1, value2, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdNotBetween(String value1, String value2) {
            addCriterion("content_id not between", value1, value2, "contentId");
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

        public Criteria andMilitaryCommentIsNull() {
            addCriterion("military_comment is null");
            return (Criteria) this;
        }

        public Criteria andMilitaryCommentIsNotNull() {
            addCriterion("military_comment is not null");
            return (Criteria) this;
        }

        public Criteria andMilitaryCommentEqualTo(String value) {
            addCriterion("military_comment =", value, "militaryComment");
            return (Criteria) this;
        }

        public Criteria andMilitaryCommentNotEqualTo(String value) {
            addCriterion("military_comment <>", value, "militaryComment");
            return (Criteria) this;
        }

        public Criteria andMilitaryCommentGreaterThan(String value) {
            addCriterion("military_comment >", value, "militaryComment");
            return (Criteria) this;
        }

        public Criteria andMilitaryCommentGreaterThanOrEqualTo(String value) {
            addCriterion("military_comment >=", value, "militaryComment");
            return (Criteria) this;
        }

        public Criteria andMilitaryCommentLessThan(String value) {
            addCriterion("military_comment <", value, "militaryComment");
            return (Criteria) this;
        }

        public Criteria andMilitaryCommentLessThanOrEqualTo(String value) {
            addCriterion("military_comment <=", value, "militaryComment");
            return (Criteria) this;
        }

        public Criteria andMilitaryCommentLike(String value) {
            addCriterion("military_comment like", value, "militaryComment");
            return (Criteria) this;
        }

        public Criteria andMilitaryCommentNotLike(String value) {
            addCriterion("military_comment not like", value, "militaryComment");
            return (Criteria) this;
        }

        public Criteria andMilitaryCommentIn(List<String> values) {
            addCriterion("military_comment in", values, "militaryComment");
            return (Criteria) this;
        }

        public Criteria andMilitaryCommentNotIn(List<String> values) {
            addCriterion("military_comment not in", values, "militaryComment");
            return (Criteria) this;
        }

        public Criteria andMilitaryCommentBetween(String value1, String value2) {
            addCriterion("military_comment between", value1, value2, "militaryComment");
            return (Criteria) this;
        }

        public Criteria andMilitaryCommentNotBetween(String value1, String value2) {
            addCriterion("military_comment not between", value1, value2, "militaryComment");
            return (Criteria) this;
        }

        public Criteria andStepResultIsNull() {
            addCriterion("step_result is null");
            return (Criteria) this;
        }

        public Criteria andStepResultIsNotNull() {
            addCriterion("step_result is not null");
            return (Criteria) this;
        }

        public Criteria andStepResultEqualTo(String value) {
            addCriterion("step_result =", value, "stepResult");
            return (Criteria) this;
        }

        public Criteria andStepResultNotEqualTo(String value) {
            addCriterion("step_result <>", value, "stepResult");
            return (Criteria) this;
        }

        public Criteria andStepResultGreaterThan(String value) {
            addCriterion("step_result >", value, "stepResult");
            return (Criteria) this;
        }

        public Criteria andStepResultGreaterThanOrEqualTo(String value) {
            addCriterion("step_result >=", value, "stepResult");
            return (Criteria) this;
        }

        public Criteria andStepResultLessThan(String value) {
            addCriterion("step_result <", value, "stepResult");
            return (Criteria) this;
        }

        public Criteria andStepResultLessThanOrEqualTo(String value) {
            addCriterion("step_result <=", value, "stepResult");
            return (Criteria) this;
        }

        public Criteria andStepResultLike(String value) {
            addCriterion("step_result like", value, "stepResult");
            return (Criteria) this;
        }

        public Criteria andStepResultNotLike(String value) {
            addCriterion("step_result not like", value, "stepResult");
            return (Criteria) this;
        }

        public Criteria andStepResultIn(List<String> values) {
            addCriterion("step_result in", values, "stepResult");
            return (Criteria) this;
        }

        public Criteria andStepResultNotIn(List<String> values) {
            addCriterion("step_result not in", values, "stepResult");
            return (Criteria) this;
        }

        public Criteria andStepResultBetween(String value1, String value2) {
            addCriterion("step_result between", value1, value2, "stepResult");
            return (Criteria) this;
        }

        public Criteria andStepResultNotBetween(String value1, String value2) {
            addCriterion("step_result not between", value1, value2, "stepResult");
            return (Criteria) this;
        }

        public Criteria andDataIdIsNull() {
            addCriterion("data_id is null");
            return (Criteria) this;
        }

        public Criteria andDataIdIsNotNull() {
            addCriterion("data_id is not null");
            return (Criteria) this;
        }

        public Criteria andDataIdEqualTo(Integer value) {
            addCriterion("data_id =", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdNotEqualTo(Integer value) {
            addCriterion("data_id <>", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdGreaterThan(Integer value) {
            addCriterion("data_id >", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("data_id >=", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdLessThan(Integer value) {
            addCriterion("data_id <", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdLessThanOrEqualTo(Integer value) {
            addCriterion("data_id <=", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdIn(List<Integer> values) {
            addCriterion("data_id in", values, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdNotIn(List<Integer> values) {
            addCriterion("data_id not in", values, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdBetween(Integer value1, Integer value2) {
            addCriterion("data_id between", value1, value2, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdNotBetween(Integer value1, Integer value2) {
            addCriterion("data_id not between", value1, value2, "dataId");
            return (Criteria) this;
        }

        public Criteria andIsManualIsNull() {
            addCriterion("is_manual is null");
            return (Criteria) this;
        }

        public Criteria andIsManualIsNotNull() {
            addCriterion("is_manual is not null");
            return (Criteria) this;
        }

        public Criteria andIsManualEqualTo(Boolean value) {
            addCriterion("is_manual =", value, "isManual");
            return (Criteria) this;
        }

        public Criteria andIsManualNotEqualTo(Boolean value) {
            addCriterion("is_manual <>", value, "isManual");
            return (Criteria) this;
        }

        public Criteria andIsManualGreaterThan(Boolean value) {
            addCriterion("is_manual >", value, "isManual");
            return (Criteria) this;
        }

        public Criteria andIsManualGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_manual >=", value, "isManual");
            return (Criteria) this;
        }

        public Criteria andIsManualLessThan(Boolean value) {
            addCriterion("is_manual <", value, "isManual");
            return (Criteria) this;
        }

        public Criteria andIsManualLessThanOrEqualTo(Boolean value) {
            addCriterion("is_manual <=", value, "isManual");
            return (Criteria) this;
        }

        public Criteria andIsManualIn(List<Boolean> values) {
            addCriterion("is_manual in", values, "isManual");
            return (Criteria) this;
        }

        public Criteria andIsManualNotIn(List<Boolean> values) {
            addCriterion("is_manual not in", values, "isManual");
            return (Criteria) this;
        }

        public Criteria andIsManualBetween(Boolean value1, Boolean value2) {
            addCriterion("is_manual between", value1, value2, "isManual");
            return (Criteria) this;
        }

        public Criteria andIsManualNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_manual not between", value1, value2, "isManual");
            return (Criteria) this;
        }

        public Criteria andLevelOneIdIsNull() {
            addCriterion("level_one_id is null");
            return (Criteria) this;
        }

        public Criteria andLevelOneIdIsNotNull() {
            addCriterion("level_one_id is not null");
            return (Criteria) this;
        }

        public Criteria andLevelOneIdEqualTo(String value) {
            addCriterion("level_one_id =", value, "levelOneId");
            return (Criteria) this;
        }

        public Criteria andLevelOneIdNotEqualTo(String value) {
            addCriterion("level_one_id <>", value, "levelOneId");
            return (Criteria) this;
        }

        public Criteria andLevelOneIdGreaterThan(String value) {
            addCriterion("level_one_id >", value, "levelOneId");
            return (Criteria) this;
        }

        public Criteria andLevelOneIdGreaterThanOrEqualTo(String value) {
            addCriterion("level_one_id >=", value, "levelOneId");
            return (Criteria) this;
        }

        public Criteria andLevelOneIdLessThan(String value) {
            addCriterion("level_one_id <", value, "levelOneId");
            return (Criteria) this;
        }

        public Criteria andLevelOneIdLessThanOrEqualTo(String value) {
            addCriterion("level_one_id <=", value, "levelOneId");
            return (Criteria) this;
        }

        public Criteria andLevelOneIdLike(String value) {
            addCriterion("level_one_id like", value, "levelOneId");
            return (Criteria) this;
        }

        public Criteria andLevelOneIdNotLike(String value) {
            addCriterion("level_one_id not like", value, "levelOneId");
            return (Criteria) this;
        }

        public Criteria andLevelOneIdIn(List<String> values) {
            addCriterion("level_one_id in", values, "levelOneId");
            return (Criteria) this;
        }

        public Criteria andLevelOneIdNotIn(List<String> values) {
            addCriterion("level_one_id not in", values, "levelOneId");
            return (Criteria) this;
        }

        public Criteria andLevelOneIdBetween(String value1, String value2) {
            addCriterion("level_one_id between", value1, value2, "levelOneId");
            return (Criteria) this;
        }

        public Criteria andLevelOneIdNotBetween(String value1, String value2) {
            addCriterion("level_one_id not between", value1, value2, "levelOneId");
            return (Criteria) this;
        }

        public Criteria andJudgeResultIsNull() {
            addCriterion("judge_result is null");
            return (Criteria) this;
        }

        public Criteria andJudgeResultIsNotNull() {
            addCriterion("judge_result is not null");
            return (Criteria) this;
        }

        public Criteria andJudgeResultEqualTo(Integer value) {
            addCriterion("judge_result =", value, "judgeResult");
            return (Criteria) this;
        }

        public Criteria andJudgeResultNotEqualTo(Integer value) {
            addCriterion("judge_result <>", value, "judgeResult");
            return (Criteria) this;
        }

        public Criteria andJudgeResultGreaterThan(Integer value) {
            addCriterion("judge_result >", value, "judgeResult");
            return (Criteria) this;
        }

        public Criteria andJudgeResultGreaterThanOrEqualTo(Integer value) {
            addCriterion("judge_result >=", value, "judgeResult");
            return (Criteria) this;
        }

        public Criteria andJudgeResultLessThan(Integer value) {
            addCriterion("judge_result <", value, "judgeResult");
            return (Criteria) this;
        }

        public Criteria andJudgeResultLessThanOrEqualTo(Integer value) {
            addCriterion("judge_result <=", value, "judgeResult");
            return (Criteria) this;
        }

        public Criteria andJudgeResultIn(List<Integer> values) {
            addCriterion("judge_result in", values, "judgeResult");
            return (Criteria) this;
        }

        public Criteria andJudgeResultNotIn(List<Integer> values) {
            addCriterion("judge_result not in", values, "judgeResult");
            return (Criteria) this;
        }

        public Criteria andJudgeResultBetween(Integer value1, Integer value2) {
            addCriterion("judge_result between", value1, value2, "judgeResult");
            return (Criteria) this;
        }

        public Criteria andJudgeResultNotBetween(Integer value1, Integer value2) {
            addCriterion("judge_result not between", value1, value2, "judgeResult");
            return (Criteria) this;
        }

        public Criteria andCanNextIsNull() {
            addCriterion("can_next is null");
            return (Criteria) this;
        }

        public Criteria andCanNextIsNotNull() {
            addCriterion("can_next is not null");
            return (Criteria) this;
        }

        public Criteria andCanNextEqualTo(Boolean value) {
            addCriterion("can_next =", value, "canNext");
            return (Criteria) this;
        }

        public Criteria andCanNextNotEqualTo(Boolean value) {
            addCriterion("can_next <>", value, "canNext");
            return (Criteria) this;
        }

        public Criteria andCanNextGreaterThan(Boolean value) {
            addCriterion("can_next >", value, "canNext");
            return (Criteria) this;
        }

        public Criteria andCanNextGreaterThanOrEqualTo(Boolean value) {
            addCriterion("can_next >=", value, "canNext");
            return (Criteria) this;
        }

        public Criteria andCanNextLessThan(Boolean value) {
            addCriterion("can_next <", value, "canNext");
            return (Criteria) this;
        }

        public Criteria andCanNextLessThanOrEqualTo(Boolean value) {
            addCriterion("can_next <=", value, "canNext");
            return (Criteria) this;
        }

        public Criteria andCanNextIn(List<Boolean> values) {
            addCriterion("can_next in", values, "canNext");
            return (Criteria) this;
        }

        public Criteria andCanNextNotIn(List<Boolean> values) {
            addCriterion("can_next not in", values, "canNext");
            return (Criteria) this;
        }

        public Criteria andCanNextBetween(Boolean value1, Boolean value2) {
            addCriterion("can_next between", value1, value2, "canNext");
            return (Criteria) this;
        }

        public Criteria andCanNextNotBetween(Boolean value1, Boolean value2) {
            addCriterion("can_next not between", value1, value2, "canNext");
            return (Criteria) this;
        }

        public Criteria andOperationIsNull() {
            addCriterion("operation is null");
            return (Criteria) this;
        }

        public Criteria andOperationIsNotNull() {
            addCriterion("operation is not null");
            return (Criteria) this;
        }

        public Criteria andOperationEqualTo(String value) {
            addCriterion("operation =", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationNotEqualTo(String value) {
            addCriterion("operation <>", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationGreaterThan(String value) {
            addCriterion("operation >", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationGreaterThanOrEqualTo(String value) {
            addCriterion("operation >=", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationLessThan(String value) {
            addCriterion("operation <", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationLessThanOrEqualTo(String value) {
            addCriterion("operation <=", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationLike(String value) {
            addCriterion("operation like", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationNotLike(String value) {
            addCriterion("operation not like", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationIn(List<String> values) {
            addCriterion("operation in", values, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationNotIn(List<String> values) {
            addCriterion("operation not in", values, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationBetween(String value1, String value2) {
            addCriterion("operation between", value1, value2, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationNotBetween(String value1, String value2) {
            addCriterion("operation not between", value1, value2, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationObjectIsNull() {
            addCriterion("operation_object is null");
            return (Criteria) this;
        }

        public Criteria andOperationObjectIsNotNull() {
            addCriterion("operation_object is not null");
            return (Criteria) this;
        }

        public Criteria andOperationObjectEqualTo(String value) {
            addCriterion("operation_object =", value, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectNotEqualTo(String value) {
            addCriterion("operation_object <>", value, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectGreaterThan(String value) {
            addCriterion("operation_object >", value, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectGreaterThanOrEqualTo(String value) {
            addCriterion("operation_object >=", value, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectLessThan(String value) {
            addCriterion("operation_object <", value, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectLessThanOrEqualTo(String value) {
            addCriterion("operation_object <=", value, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectLike(String value) {
            addCriterion("operation_object like", value, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectNotLike(String value) {
            addCriterion("operation_object not like", value, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectIn(List<String> values) {
            addCriterion("operation_object in", values, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectNotIn(List<String> values) {
            addCriterion("operation_object not in", values, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectBetween(String value1, String value2) {
            addCriterion("operation_object between", value1, value2, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationObjectNotBetween(String value1, String value2) {
            addCriterion("operation_object not between", value1, value2, "operationObject");
            return (Criteria) this;
        }

        public Criteria andOperationContentIsNull() {
            addCriterion("operation_content is null");
            return (Criteria) this;
        }

        public Criteria andOperationContentIsNotNull() {
            addCriterion("operation_content is not null");
            return (Criteria) this;
        }

        public Criteria andOperationContentEqualTo(String value) {
            addCriterion("operation_content =", value, "operationContent");
            return (Criteria) this;
        }

        public Criteria andOperationContentNotEqualTo(String value) {
            addCriterion("operation_content <>", value, "operationContent");
            return (Criteria) this;
        }

        public Criteria andOperationContentGreaterThan(String value) {
            addCriterion("operation_content >", value, "operationContent");
            return (Criteria) this;
        }

        public Criteria andOperationContentGreaterThanOrEqualTo(String value) {
            addCriterion("operation_content >=", value, "operationContent");
            return (Criteria) this;
        }

        public Criteria andOperationContentLessThan(String value) {
            addCriterion("operation_content <", value, "operationContent");
            return (Criteria) this;
        }

        public Criteria andOperationContentLessThanOrEqualTo(String value) {
            addCriterion("operation_content <=", value, "operationContent");
            return (Criteria) this;
        }

        public Criteria andOperationContentLike(String value) {
            addCriterion("operation_content like", value, "operationContent");
            return (Criteria) this;
        }

        public Criteria andOperationContentNotLike(String value) {
            addCriterion("operation_content not like", value, "operationContent");
            return (Criteria) this;
        }

        public Criteria andOperationContentIn(List<String> values) {
            addCriterion("operation_content in", values, "operationContent");
            return (Criteria) this;
        }

        public Criteria andOperationContentNotIn(List<String> values) {
            addCriterion("operation_content not in", values, "operationContent");
            return (Criteria) this;
        }

        public Criteria andOperationContentBetween(String value1, String value2) {
            addCriterion("operation_content between", value1, value2, "operationContent");
            return (Criteria) this;
        }

        public Criteria andOperationContentNotBetween(String value1, String value2) {
            addCriterion("operation_content not between", value1, value2, "operationContent");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardIdIsNull() {
            addCriterion("criterion_standard_id is null");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardIdIsNotNull() {
            addCriterion("criterion_standard_id is not null");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardIdEqualTo(Integer value) {
            addCriterion("criterion_standard_id =", value, "criterionStandardId");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardIdNotEqualTo(Integer value) {
            addCriterion("criterion_standard_id <>", value, "criterionStandardId");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardIdGreaterThan(Integer value) {
            addCriterion("criterion_standard_id >", value, "criterionStandardId");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("criterion_standard_id >=", value, "criterionStandardId");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardIdLessThan(Integer value) {
            addCriterion("criterion_standard_id <", value, "criterionStandardId");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardIdLessThanOrEqualTo(Integer value) {
            addCriterion("criterion_standard_id <=", value, "criterionStandardId");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardIdIn(List<Integer> values) {
            addCriterion("criterion_standard_id in", values, "criterionStandardId");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardIdNotIn(List<Integer> values) {
            addCriterion("criterion_standard_id not in", values, "criterionStandardId");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardIdBetween(Integer value1, Integer value2) {
            addCriterion("criterion_standard_id between", value1, value2, "criterionStandardId");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardIdNotBetween(Integer value1, Integer value2) {
            addCriterion("criterion_standard_id not between", value1, value2, "criterionStandardId");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardIsNull() {
            addCriterion("criterion_standard is null");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardIsNotNull() {
            addCriterion("criterion_standard is not null");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardEqualTo(String value) {
            addCriterion("criterion_standard =", value, "criterionStandard");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardNotEqualTo(String value) {
            addCriterion("criterion_standard <>", value, "criterionStandard");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardGreaterThan(String value) {
            addCriterion("criterion_standard >", value, "criterionStandard");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardGreaterThanOrEqualTo(String value) {
            addCriterion("criterion_standard >=", value, "criterionStandard");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardLessThan(String value) {
            addCriterion("criterion_standard <", value, "criterionStandard");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardLessThanOrEqualTo(String value) {
            addCriterion("criterion_standard <=", value, "criterionStandard");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardLike(String value) {
            addCriterion("criterion_standard like", value, "criterionStandard");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardNotLike(String value) {
            addCriterion("criterion_standard not like", value, "criterionStandard");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardIn(List<String> values) {
            addCriterion("criterion_standard in", values, "criterionStandard");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardNotIn(List<String> values) {
            addCriterion("criterion_standard not in", values, "criterionStandard");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardBetween(String value1, String value2) {
            addCriterion("criterion_standard between", value1, value2, "criterionStandard");
            return (Criteria) this;
        }

        public Criteria andCriterionStandardNotBetween(String value1, String value2) {
            addCriterion("criterion_standard not between", value1, value2, "criterionStandard");
            return (Criteria) this;
        }

        public Criteria andCriterionTypeIsNull() {
            addCriterion("criterion_type is null");
            return (Criteria) this;
        }

        public Criteria andCriterionTypeIsNotNull() {
            addCriterion("criterion_type is not null");
            return (Criteria) this;
        }

        public Criteria andCriterionTypeEqualTo(Integer value) {
            addCriterion("criterion_type =", value, "criterionType");
            return (Criteria) this;
        }

        public Criteria andCriterionTypeNotEqualTo(Integer value) {
            addCriterion("criterion_type <>", value, "criterionType");
            return (Criteria) this;
        }

        public Criteria andCriterionTypeGreaterThan(Integer value) {
            addCriterion("criterion_type >", value, "criterionType");
            return (Criteria) this;
        }

        public Criteria andCriterionTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("criterion_type >=", value, "criterionType");
            return (Criteria) this;
        }

        public Criteria andCriterionTypeLessThan(Integer value) {
            addCriterion("criterion_type <", value, "criterionType");
            return (Criteria) this;
        }

        public Criteria andCriterionTypeLessThanOrEqualTo(Integer value) {
            addCriterion("criterion_type <=", value, "criterionType");
            return (Criteria) this;
        }

        public Criteria andCriterionTypeIn(List<Integer> values) {
            addCriterion("criterion_type in", values, "criterionType");
            return (Criteria) this;
        }

        public Criteria andCriterionTypeNotIn(List<Integer> values) {
            addCriterion("criterion_type not in", values, "criterionType");
            return (Criteria) this;
        }

        public Criteria andCriterionTypeBetween(Integer value1, Integer value2) {
            addCriterion("criterion_type between", value1, value2, "criterionType");
            return (Criteria) this;
        }

        public Criteria andCriterionTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("criterion_type not between", value1, value2, "criterionType");
            return (Criteria) this;
        }

        public Criteria andCriterionValueUnitIsNull() {
            addCriterion("criterion_value_unit is null");
            return (Criteria) this;
        }

        public Criteria andCriterionValueUnitIsNotNull() {
            addCriterion("criterion_value_unit is not null");
            return (Criteria) this;
        }

        public Criteria andCriterionValueUnitEqualTo(String value) {
            addCriterion("criterion_value_unit =", value, "criterionValueUnit");
            return (Criteria) this;
        }

        public Criteria andCriterionValueUnitNotEqualTo(String value) {
            addCriterion("criterion_value_unit <>", value, "criterionValueUnit");
            return (Criteria) this;
        }

        public Criteria andCriterionValueUnitGreaterThan(String value) {
            addCriterion("criterion_value_unit >", value, "criterionValueUnit");
            return (Criteria) this;
        }

        public Criteria andCriterionValueUnitGreaterThanOrEqualTo(String value) {
            addCriterion("criterion_value_unit >=", value, "criterionValueUnit");
            return (Criteria) this;
        }

        public Criteria andCriterionValueUnitLessThan(String value) {
            addCriterion("criterion_value_unit <", value, "criterionValueUnit");
            return (Criteria) this;
        }

        public Criteria andCriterionValueUnitLessThanOrEqualTo(String value) {
            addCriterion("criterion_value_unit <=", value, "criterionValueUnit");
            return (Criteria) this;
        }

        public Criteria andCriterionValueUnitLike(String value) {
            addCriterion("criterion_value_unit like", value, "criterionValueUnit");
            return (Criteria) this;
        }

        public Criteria andCriterionValueUnitNotLike(String value) {
            addCriterion("criterion_value_unit not like", value, "criterionValueUnit");
            return (Criteria) this;
        }

        public Criteria andCriterionValueUnitIn(List<String> values) {
            addCriterion("criterion_value_unit in", values, "criterionValueUnit");
            return (Criteria) this;
        }

        public Criteria andCriterionValueUnitNotIn(List<String> values) {
            addCriterion("criterion_value_unit not in", values, "criterionValueUnit");
            return (Criteria) this;
        }

        public Criteria andCriterionValueUnitBetween(String value1, String value2) {
            addCriterion("criterion_value_unit between", value1, value2, "criterionValueUnit");
            return (Criteria) this;
        }

        public Criteria andCriterionValueUnitNotBetween(String value1, String value2) {
            addCriterion("criterion_value_unit not between", value1, value2, "criterionValueUnit");
            return (Criteria) this;
        }

        public Criteria andCriterionDescIsNull() {
            addCriterion("criterion_desc is null");
            return (Criteria) this;
        }

        public Criteria andCriterionDescIsNotNull() {
            addCriterion("criterion_desc is not null");
            return (Criteria) this;
        }

        public Criteria andCriterionDescEqualTo(String value) {
            addCriterion("criterion_desc =", value, "criterionDesc");
            return (Criteria) this;
        }

        public Criteria andCriterionDescNotEqualTo(String value) {
            addCriterion("criterion_desc <>", value, "criterionDesc");
            return (Criteria) this;
        }

        public Criteria andCriterionDescGreaterThan(String value) {
            addCriterion("criterion_desc >", value, "criterionDesc");
            return (Criteria) this;
        }

        public Criteria andCriterionDescGreaterThanOrEqualTo(String value) {
            addCriterion("criterion_desc >=", value, "criterionDesc");
            return (Criteria) this;
        }

        public Criteria andCriterionDescLessThan(String value) {
            addCriterion("criterion_desc <", value, "criterionDesc");
            return (Criteria) this;
        }

        public Criteria andCriterionDescLessThanOrEqualTo(String value) {
            addCriterion("criterion_desc <=", value, "criterionDesc");
            return (Criteria) this;
        }

        public Criteria andCriterionDescLike(String value) {
            addCriterion("criterion_desc like", value, "criterionDesc");
            return (Criteria) this;
        }

        public Criteria andCriterionDescNotLike(String value) {
            addCriterion("criterion_desc not like", value, "criterionDesc");
            return (Criteria) this;
        }

        public Criteria andCriterionDescIn(List<String> values) {
            addCriterion("criterion_desc in", values, "criterionDesc");
            return (Criteria) this;
        }

        public Criteria andCriterionDescNotIn(List<String> values) {
            addCriterion("criterion_desc not in", values, "criterionDesc");
            return (Criteria) this;
        }

        public Criteria andCriterionDescBetween(String value1, String value2) {
            addCriterion("criterion_desc between", value1, value2, "criterionDesc");
            return (Criteria) this;
        }

        public Criteria andCriterionDescNotBetween(String value1, String value2) {
            addCriterion("criterion_desc not between", value1, value2, "criterionDesc");
            return (Criteria) this;
        }

        public Criteria andGuideUrlIsNull() {
            addCriterion("guide_url is null");
            return (Criteria) this;
        }

        public Criteria andGuideUrlIsNotNull() {
            addCriterion("guide_url is not null");
            return (Criteria) this;
        }

        public Criteria andGuideUrlEqualTo(String value) {
            addCriterion("guide_url =", value, "guideUrl");
            return (Criteria) this;
        }

        public Criteria andGuideUrlNotEqualTo(String value) {
            addCriterion("guide_url <>", value, "guideUrl");
            return (Criteria) this;
        }

        public Criteria andGuideUrlGreaterThan(String value) {
            addCriterion("guide_url >", value, "guideUrl");
            return (Criteria) this;
        }

        public Criteria andGuideUrlGreaterThanOrEqualTo(String value) {
            addCriterion("guide_url >=", value, "guideUrl");
            return (Criteria) this;
        }

        public Criteria andGuideUrlLessThan(String value) {
            addCriterion("guide_url <", value, "guideUrl");
            return (Criteria) this;
        }

        public Criteria andGuideUrlLessThanOrEqualTo(String value) {
            addCriterion("guide_url <=", value, "guideUrl");
            return (Criteria) this;
        }

        public Criteria andGuideUrlLike(String value) {
            addCriterion("guide_url like", value, "guideUrl");
            return (Criteria) this;
        }

        public Criteria andGuideUrlNotLike(String value) {
            addCriterion("guide_url not like", value, "guideUrl");
            return (Criteria) this;
        }

        public Criteria andGuideUrlIn(List<String> values) {
            addCriterion("guide_url in", values, "guideUrl");
            return (Criteria) this;
        }

        public Criteria andGuideUrlNotIn(List<String> values) {
            addCriterion("guide_url not in", values, "guideUrl");
            return (Criteria) this;
        }

        public Criteria andGuideUrlBetween(String value1, String value2) {
            addCriterion("guide_url between", value1, value2, "guideUrl");
            return (Criteria) this;
        }

        public Criteria andGuideUrlNotBetween(String value1, String value2) {
            addCriterion("guide_url not between", value1, value2, "guideUrl");
            return (Criteria) this;
        }

        public Criteria andKeyProcessIsNull() {
            addCriterion("key_process is null");
            return (Criteria) this;
        }

        public Criteria andKeyProcessIsNotNull() {
            addCriterion("key_process is not null");
            return (Criteria) this;
        }

        public Criteria andKeyProcessEqualTo(Boolean value) {
            addCriterion("key_process =", value, "keyProcess");
            return (Criteria) this;
        }

        public Criteria andKeyProcessNotEqualTo(Boolean value) {
            addCriterion("key_process <>", value, "keyProcess");
            return (Criteria) this;
        }

        public Criteria andKeyProcessGreaterThan(Boolean value) {
            addCriterion("key_process >", value, "keyProcess");
            return (Criteria) this;
        }

        public Criteria andKeyProcessGreaterThanOrEqualTo(Boolean value) {
            addCriterion("key_process >=", value, "keyProcess");
            return (Criteria) this;
        }

        public Criteria andKeyProcessLessThan(Boolean value) {
            addCriterion("key_process <", value, "keyProcess");
            return (Criteria) this;
        }

        public Criteria andKeyProcessLessThanOrEqualTo(Boolean value) {
            addCriterion("key_process <=", value, "keyProcess");
            return (Criteria) this;
        }

        public Criteria andKeyProcessIn(List<Boolean> values) {
            addCriterion("key_process in", values, "keyProcess");
            return (Criteria) this;
        }

        public Criteria andKeyProcessNotIn(List<Boolean> values) {
            addCriterion("key_process not in", values, "keyProcess");
            return (Criteria) this;
        }

        public Criteria andKeyProcessBetween(Boolean value1, Boolean value2) {
            addCriterion("key_process between", value1, value2, "keyProcess");
            return (Criteria) this;
        }

        public Criteria andKeyProcessNotBetween(Boolean value1, Boolean value2) {
            addCriterion("key_process not between", value1, value2, "keyProcess");
            return (Criteria) this;
        }

        public Criteria andDependOnDeviceIsNull() {
            addCriterion("depend_on_device is null");
            return (Criteria) this;
        }

        public Criteria andDependOnDeviceIsNotNull() {
            addCriterion("depend_on_device is not null");
            return (Criteria) this;
        }

        public Criteria andDependOnDeviceEqualTo(Boolean value) {
            addCriterion("depend_on_device =", value, "dependOnDevice");
            return (Criteria) this;
        }

        public Criteria andDependOnDeviceNotEqualTo(Boolean value) {
            addCriterion("depend_on_device <>", value, "dependOnDevice");
            return (Criteria) this;
        }

        public Criteria andDependOnDeviceGreaterThan(Boolean value) {
            addCriterion("depend_on_device >", value, "dependOnDevice");
            return (Criteria) this;
        }

        public Criteria andDependOnDeviceGreaterThanOrEqualTo(Boolean value) {
            addCriterion("depend_on_device >=", value, "dependOnDevice");
            return (Criteria) this;
        }

        public Criteria andDependOnDeviceLessThan(Boolean value) {
            addCriterion("depend_on_device <", value, "dependOnDevice");
            return (Criteria) this;
        }

        public Criteria andDependOnDeviceLessThanOrEqualTo(Boolean value) {
            addCriterion("depend_on_device <=", value, "dependOnDevice");
            return (Criteria) this;
        }

        public Criteria andDependOnDeviceIn(List<Boolean> values) {
            addCriterion("depend_on_device in", values, "dependOnDevice");
            return (Criteria) this;
        }

        public Criteria andDependOnDeviceNotIn(List<Boolean> values) {
            addCriterion("depend_on_device not in", values, "dependOnDevice");
            return (Criteria) this;
        }

        public Criteria andDependOnDeviceBetween(Boolean value1, Boolean value2) {
            addCriterion("depend_on_device between", value1, value2, "dependOnDevice");
            return (Criteria) this;
        }

        public Criteria andDependOnDeviceNotBetween(Boolean value1, Boolean value2) {
            addCriterion("depend_on_device not between", value1, value2, "dependOnDevice");
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

        public Criteria andCommanderIsNull() {
            addCriterion("commander is null");
            return (Criteria) this;
        }

        public Criteria andCommanderIsNotNull() {
            addCriterion("commander is not null");
            return (Criteria) this;
        }

        public Criteria andCommanderEqualTo(String value) {
            addCriterion("commander =", value, "commander");
            return (Criteria) this;
        }

        public Criteria andCommanderNotEqualTo(String value) {
            addCriterion("commander <>", value, "commander");
            return (Criteria) this;
        }

        public Criteria andCommanderGreaterThan(String value) {
            addCriterion("commander >", value, "commander");
            return (Criteria) this;
        }

        public Criteria andCommanderGreaterThanOrEqualTo(String value) {
            addCriterion("commander >=", value, "commander");
            return (Criteria) this;
        }

        public Criteria andCommanderLessThan(String value) {
            addCriterion("commander <", value, "commander");
            return (Criteria) this;
        }

        public Criteria andCommanderLessThanOrEqualTo(String value) {
            addCriterion("commander <=", value, "commander");
            return (Criteria) this;
        }

        public Criteria andCommanderLike(String value) {
            addCriterion("commander like", value, "commander");
            return (Criteria) this;
        }

        public Criteria andCommanderNotLike(String value) {
            addCriterion("commander not like", value, "commander");
            return (Criteria) this;
        }

        public Criteria andCommanderIn(List<String> values) {
            addCriterion("commander in", values, "commander");
            return (Criteria) this;
        }

        public Criteria andCommanderNotIn(List<String> values) {
            addCriterion("commander not in", values, "commander");
            return (Criteria) this;
        }

        public Criteria andCommanderBetween(String value1, String value2) {
            addCriterion("commander between", value1, value2, "commander");
            return (Criteria) this;
        }

        public Criteria andCommanderNotBetween(String value1, String value2) {
            addCriterion("commander not between", value1, value2, "commander");
            return (Criteria) this;
        }

        public Criteria andVerfierIsNull() {
            addCriterion("verfier is null");
            return (Criteria) this;
        }

        public Criteria andVerfierIsNotNull() {
            addCriterion("verfier is not null");
            return (Criteria) this;
        }

        public Criteria andVerfierEqualTo(String value) {
            addCriterion("verfier =", value, "verfier");
            return (Criteria) this;
        }

        public Criteria andVerfierNotEqualTo(String value) {
            addCriterion("verfier <>", value, "verfier");
            return (Criteria) this;
        }

        public Criteria andVerfierGreaterThan(String value) {
            addCriterion("verfier >", value, "verfier");
            return (Criteria) this;
        }

        public Criteria andVerfierGreaterThanOrEqualTo(String value) {
            addCriterion("verfier >=", value, "verfier");
            return (Criteria) this;
        }

        public Criteria andVerfierLessThan(String value) {
            addCriterion("verfier <", value, "verfier");
            return (Criteria) this;
        }

        public Criteria andVerfierLessThanOrEqualTo(String value) {
            addCriterion("verfier <=", value, "verfier");
            return (Criteria) this;
        }

        public Criteria andVerfierLike(String value) {
            addCriterion("verfier like", value, "verfier");
            return (Criteria) this;
        }

        public Criteria andVerfierNotLike(String value) {
            addCriterion("verfier not like", value, "verfier");
            return (Criteria) this;
        }

        public Criteria andVerfierIn(List<String> values) {
            addCriterion("verfier in", values, "verfier");
            return (Criteria) this;
        }

        public Criteria andVerfierNotIn(List<String> values) {
            addCriterion("verfier not in", values, "verfier");
            return (Criteria) this;
        }

        public Criteria andVerfierBetween(String value1, String value2) {
            addCriterion("verfier between", value1, value2, "verfier");
            return (Criteria) this;
        }

        public Criteria andVerfierNotBetween(String value1, String value2) {
            addCriterion("verfier not between", value1, value2, "verfier");
            return (Criteria) this;
        }

        public Criteria andSoldierIsNull() {
            addCriterion("soldier is null");
            return (Criteria) this;
        }

        public Criteria andSoldierIsNotNull() {
            addCriterion("soldier is not null");
            return (Criteria) this;
        }

        public Criteria andSoldierEqualTo(String value) {
            addCriterion("soldier =", value, "soldier");
            return (Criteria) this;
        }

        public Criteria andSoldierNotEqualTo(String value) {
            addCriterion("soldier <>", value, "soldier");
            return (Criteria) this;
        }

        public Criteria andSoldierGreaterThan(String value) {
            addCriterion("soldier >", value, "soldier");
            return (Criteria) this;
        }

        public Criteria andSoldierGreaterThanOrEqualTo(String value) {
            addCriterion("soldier >=", value, "soldier");
            return (Criteria) this;
        }

        public Criteria andSoldierLessThan(String value) {
            addCriterion("soldier <", value, "soldier");
            return (Criteria) this;
        }

        public Criteria andSoldierLessThanOrEqualTo(String value) {
            addCriterion("soldier <=", value, "soldier");
            return (Criteria) this;
        }

        public Criteria andSoldierLike(String value) {
            addCriterion("soldier like", value, "soldier");
            return (Criteria) this;
        }

        public Criteria andSoldierNotLike(String value) {
            addCriterion("soldier not like", value, "soldier");
            return (Criteria) this;
        }

        public Criteria andSoldierIn(List<String> values) {
            addCriterion("soldier in", values, "soldier");
            return (Criteria) this;
        }

        public Criteria andSoldierNotIn(List<String> values) {
            addCriterion("soldier not in", values, "soldier");
            return (Criteria) this;
        }

        public Criteria andSoldierBetween(String value1, String value2) {
            addCriterion("soldier between", value1, value2, "soldier");
            return (Criteria) this;
        }

        public Criteria andSoldierNotBetween(String value1, String value2) {
            addCriterion("soldier not between", value1, value2, "soldier");
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

        public Criteria andCalculateIsNull() {
            addCriterion("calculate is null");
            return (Criteria) this;
        }

        public Criteria andCalculateIsNotNull() {
            addCriterion("calculate is not null");
            return (Criteria) this;
        }

        public Criteria andCalculateEqualTo(String value) {
            addCriterion("calculate =", value, "calculate");
            return (Criteria) this;
        }

        public Criteria andCalculateNotEqualTo(String value) {
            addCriterion("calculate <>", value, "calculate");
            return (Criteria) this;
        }

        public Criteria andCalculateGreaterThan(String value) {
            addCriterion("calculate >", value, "calculate");
            return (Criteria) this;
        }

        public Criteria andCalculateGreaterThanOrEqualTo(String value) {
            addCriterion("calculate >=", value, "calculate");
            return (Criteria) this;
        }

        public Criteria andCalculateLessThan(String value) {
            addCriterion("calculate <", value, "calculate");
            return (Criteria) this;
        }

        public Criteria andCalculateLessThanOrEqualTo(String value) {
            addCriterion("calculate <=", value, "calculate");
            return (Criteria) this;
        }

        public Criteria andCalculateLike(String value) {
            addCriterion("calculate like", value, "calculate");
            return (Criteria) this;
        }

        public Criteria andCalculateNotLike(String value) {
            addCriterion("calculate not like", value, "calculate");
            return (Criteria) this;
        }

        public Criteria andCalculateIn(List<String> values) {
            addCriterion("calculate in", values, "calculate");
            return (Criteria) this;
        }

        public Criteria andCalculateNotIn(List<String> values) {
            addCriterion("calculate not in", values, "calculate");
            return (Criteria) this;
        }

        public Criteria andCalculateBetween(String value1, String value2) {
            addCriterion("calculate between", value1, value2, "calculate");
            return (Criteria) this;
        }

        public Criteria andCalculateNotBetween(String value1, String value2) {
            addCriterion("calculate not between", value1, value2, "calculate");
            return (Criteria) this;
        }

        public Criteria andParallelExecuteIsNull() {
            addCriterion("parallel_execute is null");
            return (Criteria) this;
        }

        public Criteria andParallelExecuteIsNotNull() {
            addCriterion("parallel_execute is not null");
            return (Criteria) this;
        }

        public Criteria andParallelExecuteEqualTo(Integer value) {
            addCriterion("parallel_execute =", value, "parallelExecute");
            return (Criteria) this;
        }

        public Criteria andParallelExecuteNotEqualTo(Integer value) {
            addCriterion("parallel_execute <>", value, "parallelExecute");
            return (Criteria) this;
        }

        public Criteria andParallelExecuteGreaterThan(Integer value) {
            addCriterion("parallel_execute >", value, "parallelExecute");
            return (Criteria) this;
        }

        public Criteria andParallelExecuteGreaterThanOrEqualTo(Integer value) {
            addCriterion("parallel_execute >=", value, "parallelExecute");
            return (Criteria) this;
        }

        public Criteria andParallelExecuteLessThan(Integer value) {
            addCriterion("parallel_execute <", value, "parallelExecute");
            return (Criteria) this;
        }

        public Criteria andParallelExecuteLessThanOrEqualTo(Integer value) {
            addCriterion("parallel_execute <=", value, "parallelExecute");
            return (Criteria) this;
        }

        public Criteria andParallelExecuteIn(List<Integer> values) {
            addCriterion("parallel_execute in", values, "parallelExecute");
            return (Criteria) this;
        }

        public Criteria andParallelExecuteNotIn(List<Integer> values) {
            addCriterion("parallel_execute not in", values, "parallelExecute");
            return (Criteria) this;
        }

        public Criteria andParallelExecuteBetween(Integer value1, Integer value2) {
            addCriterion("parallel_execute between", value1, value2, "parallelExecute");
            return (Criteria) this;
        }

        public Criteria andParallelExecuteNotBetween(Integer value1, Integer value2) {
            addCriterion("parallel_execute not between", value1, value2, "parallelExecute");
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