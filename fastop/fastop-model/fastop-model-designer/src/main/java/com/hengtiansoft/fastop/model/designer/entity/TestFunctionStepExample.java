package com.hengtiansoft.fastop.model.designer.entity;

import java.util.ArrayList;
import java.util.List;

public class TestFunctionStepExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TestFunctionStepExample() {
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

        public Criteria andStepNameIsNull() {
            addCriterion("step_name is null");
            return (Criteria) this;
        }

        public Criteria andStepNameIsNotNull() {
            addCriterion("step_name is not null");
            return (Criteria) this;
        }

        public Criteria andStepNameEqualTo(String value) {
            addCriterion("step_name =", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameNotEqualTo(String value) {
            addCriterion("step_name <>", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameGreaterThan(String value) {
            addCriterion("step_name >", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameGreaterThanOrEqualTo(String value) {
            addCriterion("step_name >=", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameLessThan(String value) {
            addCriterion("step_name <", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameLessThanOrEqualTo(String value) {
            addCriterion("step_name <=", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameLike(String value) {
            addCriterion("step_name like", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameNotLike(String value) {
            addCriterion("step_name not like", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameIn(List<String> values) {
            addCriterion("step_name in", values, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameNotIn(List<String> values) {
            addCriterion("step_name not in", values, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameBetween(String value1, String value2) {
            addCriterion("step_name between", value1, value2, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameNotBetween(String value1, String value2) {
            addCriterion("step_name not between", value1, value2, "stepName");
            return (Criteria) this;
        }

        public Criteria andChangeUserIsNull() {
            addCriterion("change_user is null");
            return (Criteria) this;
        }

        public Criteria andChangeUserIsNotNull() {
            addCriterion("change_user is not null");
            return (Criteria) this;
        }

        public Criteria andChangeUserEqualTo(String value) {
            addCriterion("change_user =", value, "changeUser");
            return (Criteria) this;
        }

        public Criteria andChangeUserNotEqualTo(String value) {
            addCriterion("change_user <>", value, "changeUser");
            return (Criteria) this;
        }

        public Criteria andChangeUserGreaterThan(String value) {
            addCriterion("change_user >", value, "changeUser");
            return (Criteria) this;
        }

        public Criteria andChangeUserGreaterThanOrEqualTo(String value) {
            addCriterion("change_user >=", value, "changeUser");
            return (Criteria) this;
        }

        public Criteria andChangeUserLessThan(String value) {
            addCriterion("change_user <", value, "changeUser");
            return (Criteria) this;
        }

        public Criteria andChangeUserLessThanOrEqualTo(String value) {
            addCriterion("change_user <=", value, "changeUser");
            return (Criteria) this;
        }

        public Criteria andChangeUserLike(String value) {
            addCriterion("change_user like", value, "changeUser");
            return (Criteria) this;
        }

        public Criteria andChangeUserNotLike(String value) {
            addCriterion("change_user not like", value, "changeUser");
            return (Criteria) this;
        }

        public Criteria andChangeUserIn(List<String> values) {
            addCriterion("change_user in", values, "changeUser");
            return (Criteria) this;
        }

        public Criteria andChangeUserNotIn(List<String> values) {
            addCriterion("change_user not in", values, "changeUser");
            return (Criteria) this;
        }

        public Criteria andChangeUserBetween(String value1, String value2) {
            addCriterion("change_user between", value1, value2, "changeUser");
            return (Criteria) this;
        }

        public Criteria andChangeUserNotBetween(String value1, String value2) {
            addCriterion("change_user not between", value1, value2, "changeUser");
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

        public Criteria andStepNoteIsNull() {
            addCriterion("step_note is null");
            return (Criteria) this;
        }

        public Criteria andStepNoteIsNotNull() {
            addCriterion("step_note is not null");
            return (Criteria) this;
        }

        public Criteria andStepNoteEqualTo(String value) {
            addCriterion("step_note =", value, "stepNote");
            return (Criteria) this;
        }

        public Criteria andStepNoteNotEqualTo(String value) {
            addCriterion("step_note <>", value, "stepNote");
            return (Criteria) this;
        }

        public Criteria andStepNoteGreaterThan(String value) {
            addCriterion("step_note >", value, "stepNote");
            return (Criteria) this;
        }

        public Criteria andStepNoteGreaterThanOrEqualTo(String value) {
            addCriterion("step_note >=", value, "stepNote");
            return (Criteria) this;
        }

        public Criteria andStepNoteLessThan(String value) {
            addCriterion("step_note <", value, "stepNote");
            return (Criteria) this;
        }

        public Criteria andStepNoteLessThanOrEqualTo(String value) {
            addCriterion("step_note <=", value, "stepNote");
            return (Criteria) this;
        }

        public Criteria andStepNoteLike(String value) {
            addCriterion("step_note like", value, "stepNote");
            return (Criteria) this;
        }

        public Criteria andStepNoteNotLike(String value) {
            addCriterion("step_note not like", value, "stepNote");
            return (Criteria) this;
        }

        public Criteria andStepNoteIn(List<String> values) {
            addCriterion("step_note in", values, "stepNote");
            return (Criteria) this;
        }

        public Criteria andStepNoteNotIn(List<String> values) {
            addCriterion("step_note not in", values, "stepNote");
            return (Criteria) this;
        }

        public Criteria andStepNoteBetween(String value1, String value2) {
            addCriterion("step_note between", value1, value2, "stepNote");
            return (Criteria) this;
        }

        public Criteria andStepNoteNotBetween(String value1, String value2) {
            addCriterion("step_note not between", value1, value2, "stepNote");
            return (Criteria) this;
        }

        public Criteria andStepDateIsNull() {
            addCriterion("step_date is null");
            return (Criteria) this;
        }

        public Criteria andStepDateIsNotNull() {
            addCriterion("step_date is not null");
            return (Criteria) this;
        }

        public Criteria andStepDateEqualTo(String value) {
            addCriterion("step_date =", value, "stepDate");
            return (Criteria) this;
        }

        public Criteria andStepDateNotEqualTo(String value) {
            addCriterion("step_date <>", value, "stepDate");
            return (Criteria) this;
        }

        public Criteria andStepDateGreaterThan(String value) {
            addCriterion("step_date >", value, "stepDate");
            return (Criteria) this;
        }

        public Criteria andStepDateGreaterThanOrEqualTo(String value) {
            addCriterion("step_date >=", value, "stepDate");
            return (Criteria) this;
        }

        public Criteria andStepDateLessThan(String value) {
            addCriterion("step_date <", value, "stepDate");
            return (Criteria) this;
        }

        public Criteria andStepDateLessThanOrEqualTo(String value) {
            addCriterion("step_date <=", value, "stepDate");
            return (Criteria) this;
        }

        public Criteria andStepDateLike(String value) {
            addCriterion("step_date like", value, "stepDate");
            return (Criteria) this;
        }

        public Criteria andStepDateNotLike(String value) {
            addCriterion("step_date not like", value, "stepDate");
            return (Criteria) this;
        }

        public Criteria andStepDateIn(List<String> values) {
            addCriterion("step_date in", values, "stepDate");
            return (Criteria) this;
        }

        public Criteria andStepDateNotIn(List<String> values) {
            addCriterion("step_date not in", values, "stepDate");
            return (Criteria) this;
        }

        public Criteria andStepDateBetween(String value1, String value2) {
            addCriterion("step_date between", value1, value2, "stepDate");
            return (Criteria) this;
        }

        public Criteria andStepDateNotBetween(String value1, String value2) {
            addCriterion("step_date not between", value1, value2, "stepDate");
            return (Criteria) this;
        }

        public Criteria andStepOperationIsNull() {
            addCriterion("step_operation is null");
            return (Criteria) this;
        }

        public Criteria andStepOperationIsNotNull() {
            addCriterion("step_operation is not null");
            return (Criteria) this;
        }

        public Criteria andStepOperationEqualTo(String value) {
            addCriterion("step_operation =", value, "stepOperation");
            return (Criteria) this;
        }

        public Criteria andStepOperationNotEqualTo(String value) {
            addCriterion("step_operation <>", value, "stepOperation");
            return (Criteria) this;
        }

        public Criteria andStepOperationGreaterThan(String value) {
            addCriterion("step_operation >", value, "stepOperation");
            return (Criteria) this;
        }

        public Criteria andStepOperationGreaterThanOrEqualTo(String value) {
            addCriterion("step_operation >=", value, "stepOperation");
            return (Criteria) this;
        }

        public Criteria andStepOperationLessThan(String value) {
            addCriterion("step_operation <", value, "stepOperation");
            return (Criteria) this;
        }

        public Criteria andStepOperationLessThanOrEqualTo(String value) {
            addCriterion("step_operation <=", value, "stepOperation");
            return (Criteria) this;
        }

        public Criteria andStepOperationLike(String value) {
            addCriterion("step_operation like", value, "stepOperation");
            return (Criteria) this;
        }

        public Criteria andStepOperationNotLike(String value) {
            addCriterion("step_operation not like", value, "stepOperation");
            return (Criteria) this;
        }

        public Criteria andStepOperationIn(List<String> values) {
            addCriterion("step_operation in", values, "stepOperation");
            return (Criteria) this;
        }

        public Criteria andStepOperationNotIn(List<String> values) {
            addCriterion("step_operation not in", values, "stepOperation");
            return (Criteria) this;
        }

        public Criteria andStepOperationBetween(String value1, String value2) {
            addCriterion("step_operation between", value1, value2, "stepOperation");
            return (Criteria) this;
        }

        public Criteria andStepOperationNotBetween(String value1, String value2) {
            addCriterion("step_operation not between", value1, value2, "stepOperation");
            return (Criteria) this;
        }

        public Criteria andStepObjIsNull() {
            addCriterion("step_obj is null");
            return (Criteria) this;
        }

        public Criteria andStepObjIsNotNull() {
            addCriterion("step_obj is not null");
            return (Criteria) this;
        }

        public Criteria andStepObjEqualTo(String value) {
            addCriterion("step_obj =", value, "stepObj");
            return (Criteria) this;
        }

        public Criteria andStepObjNotEqualTo(String value) {
            addCriterion("step_obj <>", value, "stepObj");
            return (Criteria) this;
        }

        public Criteria andStepObjGreaterThan(String value) {
            addCriterion("step_obj >", value, "stepObj");
            return (Criteria) this;
        }

        public Criteria andStepObjGreaterThanOrEqualTo(String value) {
            addCriterion("step_obj >=", value, "stepObj");
            return (Criteria) this;
        }

        public Criteria andStepObjLessThan(String value) {
            addCriterion("step_obj <", value, "stepObj");
            return (Criteria) this;
        }

        public Criteria andStepObjLessThanOrEqualTo(String value) {
            addCriterion("step_obj <=", value, "stepObj");
            return (Criteria) this;
        }

        public Criteria andStepObjLike(String value) {
            addCriterion("step_obj like", value, "stepObj");
            return (Criteria) this;
        }

        public Criteria andStepObjNotLike(String value) {
            addCriterion("step_obj not like", value, "stepObj");
            return (Criteria) this;
        }

        public Criteria andStepObjIn(List<String> values) {
            addCriterion("step_obj in", values, "stepObj");
            return (Criteria) this;
        }

        public Criteria andStepObjNotIn(List<String> values) {
            addCriterion("step_obj not in", values, "stepObj");
            return (Criteria) this;
        }

        public Criteria andStepObjBetween(String value1, String value2) {
            addCriterion("step_obj between", value1, value2, "stepObj");
            return (Criteria) this;
        }

        public Criteria andStepObjNotBetween(String value1, String value2) {
            addCriterion("step_obj not between", value1, value2, "stepObj");
            return (Criteria) this;
        }

        public Criteria andStepPurposeIsNull() {
            addCriterion("step_purpose is null");
            return (Criteria) this;
        }

        public Criteria andStepPurposeIsNotNull() {
            addCriterion("step_purpose is not null");
            return (Criteria) this;
        }

        public Criteria andStepPurposeEqualTo(String value) {
            addCriterion("step_purpose =", value, "stepPurpose");
            return (Criteria) this;
        }

        public Criteria andStepPurposeNotEqualTo(String value) {
            addCriterion("step_purpose <>", value, "stepPurpose");
            return (Criteria) this;
        }

        public Criteria andStepPurposeGreaterThan(String value) {
            addCriterion("step_purpose >", value, "stepPurpose");
            return (Criteria) this;
        }

        public Criteria andStepPurposeGreaterThanOrEqualTo(String value) {
            addCriterion("step_purpose >=", value, "stepPurpose");
            return (Criteria) this;
        }

        public Criteria andStepPurposeLessThan(String value) {
            addCriterion("step_purpose <", value, "stepPurpose");
            return (Criteria) this;
        }

        public Criteria andStepPurposeLessThanOrEqualTo(String value) {
            addCriterion("step_purpose <=", value, "stepPurpose");
            return (Criteria) this;
        }

        public Criteria andStepPurposeLike(String value) {
            addCriterion("step_purpose like", value, "stepPurpose");
            return (Criteria) this;
        }

        public Criteria andStepPurposeNotLike(String value) {
            addCriterion("step_purpose not like", value, "stepPurpose");
            return (Criteria) this;
        }

        public Criteria andStepPurposeIn(List<String> values) {
            addCriterion("step_purpose in", values, "stepPurpose");
            return (Criteria) this;
        }

        public Criteria andStepPurposeNotIn(List<String> values) {
            addCriterion("step_purpose not in", values, "stepPurpose");
            return (Criteria) this;
        }

        public Criteria andStepPurposeBetween(String value1, String value2) {
            addCriterion("step_purpose between", value1, value2, "stepPurpose");
            return (Criteria) this;
        }

        public Criteria andStepPurposeNotBetween(String value1, String value2) {
            addCriterion("step_purpose not between", value1, value2, "stepPurpose");
            return (Criteria) this;
        }

        public Criteria andTotalSendIsNull() {
            addCriterion("total_send is null");
            return (Criteria) this;
        }

        public Criteria andTotalSendIsNotNull() {
            addCriterion("total_send is not null");
            return (Criteria) this;
        }

        public Criteria andTotalSendEqualTo(Boolean value) {
            addCriterion("total_send =", value, "totalSend");
            return (Criteria) this;
        }

        public Criteria andTotalSendNotEqualTo(Boolean value) {
            addCriterion("total_send <>", value, "totalSend");
            return (Criteria) this;
        }

        public Criteria andTotalSendGreaterThan(Boolean value) {
            addCriterion("total_send >", value, "totalSend");
            return (Criteria) this;
        }

        public Criteria andTotalSendGreaterThanOrEqualTo(Boolean value) {
            addCriterion("total_send >=", value, "totalSend");
            return (Criteria) this;
        }

        public Criteria andTotalSendLessThan(Boolean value) {
            addCriterion("total_send <", value, "totalSend");
            return (Criteria) this;
        }

        public Criteria andTotalSendLessThanOrEqualTo(Boolean value) {
            addCriterion("total_send <=", value, "totalSend");
            return (Criteria) this;
        }

        public Criteria andTotalSendIn(List<Boolean> values) {
            addCriterion("total_send in", values, "totalSend");
            return (Criteria) this;
        }

        public Criteria andTotalSendNotIn(List<Boolean> values) {
            addCriterion("total_send not in", values, "totalSend");
            return (Criteria) this;
        }

        public Criteria andTotalSendBetween(Boolean value1, Boolean value2) {
            addCriterion("total_send between", value1, value2, "totalSend");
            return (Criteria) this;
        }

        public Criteria andTotalSendNotBetween(Boolean value1, Boolean value2) {
            addCriterion("total_send not between", value1, value2, "totalSend");
            return (Criteria) this;
        }

        public Criteria andConditionStatusIsNull() {
            addCriterion("condition_status is null");
            return (Criteria) this;
        }

        public Criteria andConditionStatusIsNotNull() {
            addCriterion("condition_status is not null");
            return (Criteria) this;
        }

        public Criteria andConditionStatusEqualTo(String value) {
            addCriterion("condition_status =", value, "conditionStatus");
            return (Criteria) this;
        }

        public Criteria andConditionStatusNotEqualTo(String value) {
            addCriterion("condition_status <>", value, "conditionStatus");
            return (Criteria) this;
        }

        public Criteria andConditionStatusGreaterThan(String value) {
            addCriterion("condition_status >", value, "conditionStatus");
            return (Criteria) this;
        }

        public Criteria andConditionStatusGreaterThanOrEqualTo(String value) {
            addCriterion("condition_status >=", value, "conditionStatus");
            return (Criteria) this;
        }

        public Criteria andConditionStatusLessThan(String value) {
            addCriterion("condition_status <", value, "conditionStatus");
            return (Criteria) this;
        }

        public Criteria andConditionStatusLessThanOrEqualTo(String value) {
            addCriterion("condition_status <=", value, "conditionStatus");
            return (Criteria) this;
        }

        public Criteria andConditionStatusLike(String value) {
            addCriterion("condition_status like", value, "conditionStatus");
            return (Criteria) this;
        }

        public Criteria andConditionStatusNotLike(String value) {
            addCriterion("condition_status not like", value, "conditionStatus");
            return (Criteria) this;
        }

        public Criteria andConditionStatusIn(List<String> values) {
            addCriterion("condition_status in", values, "conditionStatus");
            return (Criteria) this;
        }

        public Criteria andConditionStatusNotIn(List<String> values) {
            addCriterion("condition_status not in", values, "conditionStatus");
            return (Criteria) this;
        }

        public Criteria andConditionStatusBetween(String value1, String value2) {
            addCriterion("condition_status between", value1, value2, "conditionStatus");
            return (Criteria) this;
        }

        public Criteria andConditionStatusNotBetween(String value1, String value2) {
            addCriterion("condition_status not between", value1, value2, "conditionStatus");
            return (Criteria) this;
        }

        public Criteria andCaseIdIsNull() {
            addCriterion("case_id is null");
            return (Criteria) this;
        }

        public Criteria andCaseIdIsNotNull() {
            addCriterion("case_id is not null");
            return (Criteria) this;
        }

        public Criteria andCaseIdEqualTo(Integer value) {
            addCriterion("case_id =", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdNotEqualTo(Integer value) {
            addCriterion("case_id <>", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdGreaterThan(Integer value) {
            addCriterion("case_id >", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("case_id >=", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdLessThan(Integer value) {
            addCriterion("case_id <", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdLessThanOrEqualTo(Integer value) {
            addCriterion("case_id <=", value, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdIn(List<Integer> values) {
            addCriterion("case_id in", values, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdNotIn(List<Integer> values) {
            addCriterion("case_id not in", values, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdBetween(Integer value1, Integer value2) {
            addCriterion("case_id between", value1, value2, "caseId");
            return (Criteria) this;
        }

        public Criteria andCaseIdNotBetween(Integer value1, Integer value2) {
            addCriterion("case_id not between", value1, value2, "caseId");
            return (Criteria) this;
        }

        public Criteria andStepStatusIsNull() {
            addCriterion("step_status is null");
            return (Criteria) this;
        }

        public Criteria andStepStatusIsNotNull() {
            addCriterion("step_status is not null");
            return (Criteria) this;
        }

        public Criteria andStepStatusEqualTo(Integer value) {
            addCriterion("step_status =", value, "stepStatus");
            return (Criteria) this;
        }

        public Criteria andStepStatusNotEqualTo(Integer value) {
            addCriterion("step_status <>", value, "stepStatus");
            return (Criteria) this;
        }

        public Criteria andStepStatusGreaterThan(Integer value) {
            addCriterion("step_status >", value, "stepStatus");
            return (Criteria) this;
        }

        public Criteria andStepStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("step_status >=", value, "stepStatus");
            return (Criteria) this;
        }

        public Criteria andStepStatusLessThan(Integer value) {
            addCriterion("step_status <", value, "stepStatus");
            return (Criteria) this;
        }

        public Criteria andStepStatusLessThanOrEqualTo(Integer value) {
            addCriterion("step_status <=", value, "stepStatus");
            return (Criteria) this;
        }

        public Criteria andStepStatusIn(List<Integer> values) {
            addCriterion("step_status in", values, "stepStatus");
            return (Criteria) this;
        }

        public Criteria andStepStatusNotIn(List<Integer> values) {
            addCriterion("step_status not in", values, "stepStatus");
            return (Criteria) this;
        }

        public Criteria andStepStatusBetween(Integer value1, Integer value2) {
            addCriterion("step_status between", value1, value2, "stepStatus");
            return (Criteria) this;
        }

        public Criteria andStepStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("step_status not between", value1, value2, "stepStatus");
            return (Criteria) this;
        }

        public Criteria andUpdateIsNull() {
            addCriterion("update is null");
            return (Criteria) this;
        }

        public Criteria andUpdateIsNotNull() {
            addCriterion("update is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateEqualTo(Integer value) {
            addCriterion("update =", value, "update");
            return (Criteria) this;
        }

        public Criteria andUpdateNotEqualTo(Integer value) {
            addCriterion("update <>", value, "update");
            return (Criteria) this;
        }

        public Criteria andUpdateGreaterThan(Integer value) {
            addCriterion("update >", value, "update");
            return (Criteria) this;
        }

        public Criteria andUpdateGreaterThanOrEqualTo(Integer value) {
            addCriterion("update >=", value, "update");
            return (Criteria) this;
        }

        public Criteria andUpdateLessThan(Integer value) {
            addCriterion("update <", value, "update");
            return (Criteria) this;
        }

        public Criteria andUpdateLessThanOrEqualTo(Integer value) {
            addCriterion("update <=", value, "update");
            return (Criteria) this;
        }

        public Criteria andUpdateIn(List<Integer> values) {
            addCriterion("update in", values, "update");
            return (Criteria) this;
        }

        public Criteria andUpdateNotIn(List<Integer> values) {
            addCriterion("update not in", values, "update");
            return (Criteria) this;
        }

        public Criteria andUpdateBetween(Integer value1, Integer value2) {
            addCriterion("update between", value1, value2, "update");
            return (Criteria) this;
        }

        public Criteria andUpdateNotBetween(Integer value1, Integer value2) {
            addCriterion("update not between", value1, value2, "update");
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