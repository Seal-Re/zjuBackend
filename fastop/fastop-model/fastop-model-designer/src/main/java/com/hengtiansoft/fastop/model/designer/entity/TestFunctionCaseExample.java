package com.hengtiansoft.fastop.model.designer.entity;

import java.util.ArrayList;
import java.util.List;

public class TestFunctionCaseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TestFunctionCaseExample() {
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

        public Criteria andCaseNameIsNull() {
            addCriterion("case_name is null");
            return (Criteria) this;
        }

        public Criteria andCaseNameIsNotNull() {
            addCriterion("case_name is not null");
            return (Criteria) this;
        }

        public Criteria andCaseNameEqualTo(String value) {
            addCriterion("case_name =", value, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameNotEqualTo(String value) {
            addCriterion("case_name <>", value, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameGreaterThan(String value) {
            addCriterion("case_name >", value, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameGreaterThanOrEqualTo(String value) {
            addCriterion("case_name >=", value, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameLessThan(String value) {
            addCriterion("case_name <", value, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameLessThanOrEqualTo(String value) {
            addCriterion("case_name <=", value, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameLike(String value) {
            addCriterion("case_name like", value, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameNotLike(String value) {
            addCriterion("case_name not like", value, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameIn(List<String> values) {
            addCriterion("case_name in", values, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameNotIn(List<String> values) {
            addCriterion("case_name not in", values, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameBetween(String value1, String value2) {
            addCriterion("case_name between", value1, value2, "caseName");
            return (Criteria) this;
        }

        public Criteria andCaseNameNotBetween(String value1, String value2) {
            addCriterion("case_name not between", value1, value2, "caseName");
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

        public Criteria andCaseDescriptionIsNull() {
            addCriterion("case_description is null");
            return (Criteria) this;
        }

        public Criteria andCaseDescriptionIsNotNull() {
            addCriterion("case_description is not null");
            return (Criteria) this;
        }

        public Criteria andCaseDescriptionEqualTo(String value) {
            addCriterion("case_description =", value, "caseDescription");
            return (Criteria) this;
        }

        public Criteria andCaseDescriptionNotEqualTo(String value) {
            addCriterion("case_description <>", value, "caseDescription");
            return (Criteria) this;
        }

        public Criteria andCaseDescriptionGreaterThan(String value) {
            addCriterion("case_description >", value, "caseDescription");
            return (Criteria) this;
        }

        public Criteria andCaseDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("case_description >=", value, "caseDescription");
            return (Criteria) this;
        }

        public Criteria andCaseDescriptionLessThan(String value) {
            addCriterion("case_description <", value, "caseDescription");
            return (Criteria) this;
        }

        public Criteria andCaseDescriptionLessThanOrEqualTo(String value) {
            addCriterion("case_description <=", value, "caseDescription");
            return (Criteria) this;
        }

        public Criteria andCaseDescriptionLike(String value) {
            addCriterion("case_description like", value, "caseDescription");
            return (Criteria) this;
        }

        public Criteria andCaseDescriptionNotLike(String value) {
            addCriterion("case_description not like", value, "caseDescription");
            return (Criteria) this;
        }

        public Criteria andCaseDescriptionIn(List<String> values) {
            addCriterion("case_description in", values, "caseDescription");
            return (Criteria) this;
        }

        public Criteria andCaseDescriptionNotIn(List<String> values) {
            addCriterion("case_description not in", values, "caseDescription");
            return (Criteria) this;
        }

        public Criteria andCaseDescriptionBetween(String value1, String value2) {
            addCriterion("case_description between", value1, value2, "caseDescription");
            return (Criteria) this;
        }

        public Criteria andCaseDescriptionNotBetween(String value1, String value2) {
            addCriterion("case_description not between", value1, value2, "caseDescription");
            return (Criteria) this;
        }

        public Criteria andCaseNoteIsNull() {
            addCriterion("case_note is null");
            return (Criteria) this;
        }

        public Criteria andCaseNoteIsNotNull() {
            addCriterion("case_note is not null");
            return (Criteria) this;
        }

        public Criteria andCaseNoteEqualTo(String value) {
            addCriterion("case_note =", value, "caseNote");
            return (Criteria) this;
        }

        public Criteria andCaseNoteNotEqualTo(String value) {
            addCriterion("case_note <>", value, "caseNote");
            return (Criteria) this;
        }

        public Criteria andCaseNoteGreaterThan(String value) {
            addCriterion("case_note >", value, "caseNote");
            return (Criteria) this;
        }

        public Criteria andCaseNoteGreaterThanOrEqualTo(String value) {
            addCriterion("case_note >=", value, "caseNote");
            return (Criteria) this;
        }

        public Criteria andCaseNoteLessThan(String value) {
            addCriterion("case_note <", value, "caseNote");
            return (Criteria) this;
        }

        public Criteria andCaseNoteLessThanOrEqualTo(String value) {
            addCriterion("case_note <=", value, "caseNote");
            return (Criteria) this;
        }

        public Criteria andCaseNoteLike(String value) {
            addCriterion("case_note like", value, "caseNote");
            return (Criteria) this;
        }

        public Criteria andCaseNoteNotLike(String value) {
            addCriterion("case_note not like", value, "caseNote");
            return (Criteria) this;
        }

        public Criteria andCaseNoteIn(List<String> values) {
            addCriterion("case_note in", values, "caseNote");
            return (Criteria) this;
        }

        public Criteria andCaseNoteNotIn(List<String> values) {
            addCriterion("case_note not in", values, "caseNote");
            return (Criteria) this;
        }

        public Criteria andCaseNoteBetween(String value1, String value2) {
            addCriterion("case_note between", value1, value2, "caseNote");
            return (Criteria) this;
        }

        public Criteria andCaseNoteNotBetween(String value1, String value2) {
            addCriterion("case_note not between", value1, value2, "caseNote");
            return (Criteria) this;
        }

        public Criteria andCaseDateIsNull() {
            addCriterion("case_date is null");
            return (Criteria) this;
        }

        public Criteria andCaseDateIsNotNull() {
            addCriterion("case_date is not null");
            return (Criteria) this;
        }

        public Criteria andCaseDateEqualTo(String value) {
            addCriterion("case_date =", value, "caseDate");
            return (Criteria) this;
        }

        public Criteria andCaseDateNotEqualTo(String value) {
            addCriterion("case_date <>", value, "caseDate");
            return (Criteria) this;
        }

        public Criteria andCaseDateGreaterThan(String value) {
            addCriterion("case_date >", value, "caseDate");
            return (Criteria) this;
        }

        public Criteria andCaseDateGreaterThanOrEqualTo(String value) {
            addCriterion("case_date >=", value, "caseDate");
            return (Criteria) this;
        }

        public Criteria andCaseDateLessThan(String value) {
            addCriterion("case_date <", value, "caseDate");
            return (Criteria) this;
        }

        public Criteria andCaseDateLessThanOrEqualTo(String value) {
            addCriterion("case_date <=", value, "caseDate");
            return (Criteria) this;
        }

        public Criteria andCaseDateLike(String value) {
            addCriterion("case_date like", value, "caseDate");
            return (Criteria) this;
        }

        public Criteria andCaseDateNotLike(String value) {
            addCriterion("case_date not like", value, "caseDate");
            return (Criteria) this;
        }

        public Criteria andCaseDateIn(List<String> values) {
            addCriterion("case_date in", values, "caseDate");
            return (Criteria) this;
        }

        public Criteria andCaseDateNotIn(List<String> values) {
            addCriterion("case_date not in", values, "caseDate");
            return (Criteria) this;
        }

        public Criteria andCaseDateBetween(String value1, String value2) {
            addCriterion("case_date between", value1, value2, "caseDate");
            return (Criteria) this;
        }

        public Criteria andCaseDateNotBetween(String value1, String value2) {
            addCriterion("case_date not between", value1, value2, "caseDate");
            return (Criteria) this;
        }

        public Criteria andModuleIdIsNull() {
            addCriterion("module_id is null");
            return (Criteria) this;
        }

        public Criteria andModuleIdIsNotNull() {
            addCriterion("module_id is not null");
            return (Criteria) this;
        }

        public Criteria andModuleIdEqualTo(Integer value) {
            addCriterion("module_id =", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdNotEqualTo(Integer value) {
            addCriterion("module_id <>", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdGreaterThan(Integer value) {
            addCriterion("module_id >", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("module_id >=", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdLessThan(Integer value) {
            addCriterion("module_id <", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdLessThanOrEqualTo(Integer value) {
            addCriterion("module_id <=", value, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdIn(List<Integer> values) {
            addCriterion("module_id in", values, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdNotIn(List<Integer> values) {
            addCriterion("module_id not in", values, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdBetween(Integer value1, Integer value2) {
            addCriterion("module_id between", value1, value2, "moduleId");
            return (Criteria) this;
        }

        public Criteria andModuleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("module_id not between", value1, value2, "moduleId");
            return (Criteria) this;
        }

        public Criteria andCaseStatusIsNull() {
            addCriterion("case_status is null");
            return (Criteria) this;
        }

        public Criteria andCaseStatusIsNotNull() {
            addCriterion("case_status is not null");
            return (Criteria) this;
        }

        public Criteria andCaseStatusEqualTo(Integer value) {
            addCriterion("case_status =", value, "caseStatus");
            return (Criteria) this;
        }

        public Criteria andCaseStatusNotEqualTo(Integer value) {
            addCriterion("case_status <>", value, "caseStatus");
            return (Criteria) this;
        }

        public Criteria andCaseStatusGreaterThan(Integer value) {
            addCriterion("case_status >", value, "caseStatus");
            return (Criteria) this;
        }

        public Criteria andCaseStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("case_status >=", value, "caseStatus");
            return (Criteria) this;
        }

        public Criteria andCaseStatusLessThan(Integer value) {
            addCriterion("case_status <", value, "caseStatus");
            return (Criteria) this;
        }

        public Criteria andCaseStatusLessThanOrEqualTo(Integer value) {
            addCriterion("case_status <=", value, "caseStatus");
            return (Criteria) this;
        }

        public Criteria andCaseStatusIn(List<Integer> values) {
            addCriterion("case_status in", values, "caseStatus");
            return (Criteria) this;
        }

        public Criteria andCaseStatusNotIn(List<Integer> values) {
            addCriterion("case_status not in", values, "caseStatus");
            return (Criteria) this;
        }

        public Criteria andCaseStatusBetween(Integer value1, Integer value2) {
            addCriterion("case_status between", value1, value2, "caseStatus");
            return (Criteria) this;
        }

        public Criteria andCaseStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("case_status not between", value1, value2, "caseStatus");
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