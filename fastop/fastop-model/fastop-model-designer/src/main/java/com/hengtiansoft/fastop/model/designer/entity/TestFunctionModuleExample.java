package com.hengtiansoft.fastop.model.designer.entity;

import java.util.ArrayList;
import java.util.List;

public class TestFunctionModuleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TestFunctionModuleExample() {
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

        public Criteria andModuleNameIsNull() {
            addCriterion("module_name is null");
            return (Criteria) this;
        }

        public Criteria andModuleNameIsNotNull() {
            addCriterion("module_name is not null");
            return (Criteria) this;
        }

        public Criteria andModuleNameEqualTo(String value) {
            addCriterion("module_name =", value, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameNotEqualTo(String value) {
            addCriterion("module_name <>", value, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameGreaterThan(String value) {
            addCriterion("module_name >", value, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameGreaterThanOrEqualTo(String value) {
            addCriterion("module_name >=", value, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameLessThan(String value) {
            addCriterion("module_name <", value, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameLessThanOrEqualTo(String value) {
            addCriterion("module_name <=", value, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameLike(String value) {
            addCriterion("module_name like", value, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameNotLike(String value) {
            addCriterion("module_name not like", value, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameIn(List<String> values) {
            addCriterion("module_name in", values, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameNotIn(List<String> values) {
            addCriterion("module_name not in", values, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameBetween(String value1, String value2) {
            addCriterion("module_name between", value1, value2, "moduleName");
            return (Criteria) this;
        }

        public Criteria andModuleNameNotBetween(String value1, String value2) {
            addCriterion("module_name not between", value1, value2, "moduleName");
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

        public Criteria andModuleDescriptionIsNull() {
            addCriterion("module_description is null");
            return (Criteria) this;
        }

        public Criteria andModuleDescriptionIsNotNull() {
            addCriterion("module_description is not null");
            return (Criteria) this;
        }

        public Criteria andModuleDescriptionEqualTo(String value) {
            addCriterion("module_description =", value, "moduleDescription");
            return (Criteria) this;
        }

        public Criteria andModuleDescriptionNotEqualTo(String value) {
            addCriterion("module_description <>", value, "moduleDescription");
            return (Criteria) this;
        }

        public Criteria andModuleDescriptionGreaterThan(String value) {
            addCriterion("module_description >", value, "moduleDescription");
            return (Criteria) this;
        }

        public Criteria andModuleDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("module_description >=", value, "moduleDescription");
            return (Criteria) this;
        }

        public Criteria andModuleDescriptionLessThan(String value) {
            addCriterion("module_description <", value, "moduleDescription");
            return (Criteria) this;
        }

        public Criteria andModuleDescriptionLessThanOrEqualTo(String value) {
            addCriterion("module_description <=", value, "moduleDescription");
            return (Criteria) this;
        }

        public Criteria andModuleDescriptionLike(String value) {
            addCriterion("module_description like", value, "moduleDescription");
            return (Criteria) this;
        }

        public Criteria andModuleDescriptionNotLike(String value) {
            addCriterion("module_description not like", value, "moduleDescription");
            return (Criteria) this;
        }

        public Criteria andModuleDescriptionIn(List<String> values) {
            addCriterion("module_description in", values, "moduleDescription");
            return (Criteria) this;
        }

        public Criteria andModuleDescriptionNotIn(List<String> values) {
            addCriterion("module_description not in", values, "moduleDescription");
            return (Criteria) this;
        }

        public Criteria andModuleDescriptionBetween(String value1, String value2) {
            addCriterion("module_description between", value1, value2, "moduleDescription");
            return (Criteria) this;
        }

        public Criteria andModuleDescriptionNotBetween(String value1, String value2) {
            addCriterion("module_description not between", value1, value2, "moduleDescription");
            return (Criteria) this;
        }

        public Criteria andModuleNoteIsNull() {
            addCriterion("module_note is null");
            return (Criteria) this;
        }

        public Criteria andModuleNoteIsNotNull() {
            addCriterion("module_note is not null");
            return (Criteria) this;
        }

        public Criteria andModuleNoteEqualTo(String value) {
            addCriterion("module_note =", value, "moduleNote");
            return (Criteria) this;
        }

        public Criteria andModuleNoteNotEqualTo(String value) {
            addCriterion("module_note <>", value, "moduleNote");
            return (Criteria) this;
        }

        public Criteria andModuleNoteGreaterThan(String value) {
            addCriterion("module_note >", value, "moduleNote");
            return (Criteria) this;
        }

        public Criteria andModuleNoteGreaterThanOrEqualTo(String value) {
            addCriterion("module_note >=", value, "moduleNote");
            return (Criteria) this;
        }

        public Criteria andModuleNoteLessThan(String value) {
            addCriterion("module_note <", value, "moduleNote");
            return (Criteria) this;
        }

        public Criteria andModuleNoteLessThanOrEqualTo(String value) {
            addCriterion("module_note <=", value, "moduleNote");
            return (Criteria) this;
        }

        public Criteria andModuleNoteLike(String value) {
            addCriterion("module_note like", value, "moduleNote");
            return (Criteria) this;
        }

        public Criteria andModuleNoteNotLike(String value) {
            addCriterion("module_note not like", value, "moduleNote");
            return (Criteria) this;
        }

        public Criteria andModuleNoteIn(List<String> values) {
            addCriterion("module_note in", values, "moduleNote");
            return (Criteria) this;
        }

        public Criteria andModuleNoteNotIn(List<String> values) {
            addCriterion("module_note not in", values, "moduleNote");
            return (Criteria) this;
        }

        public Criteria andModuleNoteBetween(String value1, String value2) {
            addCriterion("module_note between", value1, value2, "moduleNote");
            return (Criteria) this;
        }

        public Criteria andModuleNoteNotBetween(String value1, String value2) {
            addCriterion("module_note not between", value1, value2, "moduleNote");
            return (Criteria) this;
        }

        public Criteria andModuleDateIsNull() {
            addCriterion("module_date is null");
            return (Criteria) this;
        }

        public Criteria andModuleDateIsNotNull() {
            addCriterion("module_date is not null");
            return (Criteria) this;
        }

        public Criteria andModuleDateEqualTo(String value) {
            addCriterion("module_date =", value, "moduleDate");
            return (Criteria) this;
        }

        public Criteria andModuleDateNotEqualTo(String value) {
            addCriterion("module_date <>", value, "moduleDate");
            return (Criteria) this;
        }

        public Criteria andModuleDateGreaterThan(String value) {
            addCriterion("module_date >", value, "moduleDate");
            return (Criteria) this;
        }

        public Criteria andModuleDateGreaterThanOrEqualTo(String value) {
            addCriterion("module_date >=", value, "moduleDate");
            return (Criteria) this;
        }

        public Criteria andModuleDateLessThan(String value) {
            addCriterion("module_date <", value, "moduleDate");
            return (Criteria) this;
        }

        public Criteria andModuleDateLessThanOrEqualTo(String value) {
            addCriterion("module_date <=", value, "moduleDate");
            return (Criteria) this;
        }

        public Criteria andModuleDateLike(String value) {
            addCriterion("module_date like", value, "moduleDate");
            return (Criteria) this;
        }

        public Criteria andModuleDateNotLike(String value) {
            addCriterion("module_date not like", value, "moduleDate");
            return (Criteria) this;
        }

        public Criteria andModuleDateIn(List<String> values) {
            addCriterion("module_date in", values, "moduleDate");
            return (Criteria) this;
        }

        public Criteria andModuleDateNotIn(List<String> values) {
            addCriterion("module_date not in", values, "moduleDate");
            return (Criteria) this;
        }

        public Criteria andModuleDateBetween(String value1, String value2) {
            addCriterion("module_date between", value1, value2, "moduleDate");
            return (Criteria) this;
        }

        public Criteria andModuleDateNotBetween(String value1, String value2) {
            addCriterion("module_date not between", value1, value2, "moduleDate");
            return (Criteria) this;
        }

        public Criteria andFunIdIsNull() {
            addCriterion("fun_id is null");
            return (Criteria) this;
        }

        public Criteria andFunIdIsNotNull() {
            addCriterion("fun_id is not null");
            return (Criteria) this;
        }

        public Criteria andFunIdEqualTo(Integer value) {
            addCriterion("fun_id =", value, "funId");
            return (Criteria) this;
        }

        public Criteria andFunIdNotEqualTo(Integer value) {
            addCriterion("fun_id <>", value, "funId");
            return (Criteria) this;
        }

        public Criteria andFunIdGreaterThan(Integer value) {
            addCriterion("fun_id >", value, "funId");
            return (Criteria) this;
        }

        public Criteria andFunIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("fun_id >=", value, "funId");
            return (Criteria) this;
        }

        public Criteria andFunIdLessThan(Integer value) {
            addCriterion("fun_id <", value, "funId");
            return (Criteria) this;
        }

        public Criteria andFunIdLessThanOrEqualTo(Integer value) {
            addCriterion("fun_id <=", value, "funId");
            return (Criteria) this;
        }

        public Criteria andFunIdIn(List<Integer> values) {
            addCriterion("fun_id in", values, "funId");
            return (Criteria) this;
        }

        public Criteria andFunIdNotIn(List<Integer> values) {
            addCriterion("fun_id not in", values, "funId");
            return (Criteria) this;
        }

        public Criteria andFunIdBetween(Integer value1, Integer value2) {
            addCriterion("fun_id between", value1, value2, "funId");
            return (Criteria) this;
        }

        public Criteria andFunIdNotBetween(Integer value1, Integer value2) {
            addCriterion("fun_id not between", value1, value2, "funId");
            return (Criteria) this;
        }

        public Criteria andModuleStatusIsNull() {
            addCriterion("module_status is null");
            return (Criteria) this;
        }

        public Criteria andModuleStatusIsNotNull() {
            addCriterion("module_status is not null");
            return (Criteria) this;
        }

        public Criteria andModuleStatusEqualTo(Integer value) {
            addCriterion("module_status =", value, "moduleStatus");
            return (Criteria) this;
        }

        public Criteria andModuleStatusNotEqualTo(Integer value) {
            addCriterion("module_status <>", value, "moduleStatus");
            return (Criteria) this;
        }

        public Criteria andModuleStatusGreaterThan(Integer value) {
            addCriterion("module_status >", value, "moduleStatus");
            return (Criteria) this;
        }

        public Criteria andModuleStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("module_status >=", value, "moduleStatus");
            return (Criteria) this;
        }

        public Criteria andModuleStatusLessThan(Integer value) {
            addCriterion("module_status <", value, "moduleStatus");
            return (Criteria) this;
        }

        public Criteria andModuleStatusLessThanOrEqualTo(Integer value) {
            addCriterion("module_status <=", value, "moduleStatus");
            return (Criteria) this;
        }

        public Criteria andModuleStatusIn(List<Integer> values) {
            addCriterion("module_status in", values, "moduleStatus");
            return (Criteria) this;
        }

        public Criteria andModuleStatusNotIn(List<Integer> values) {
            addCriterion("module_status not in", values, "moduleStatus");
            return (Criteria) this;
        }

        public Criteria andModuleStatusBetween(Integer value1, Integer value2) {
            addCriterion("module_status between", value1, value2, "moduleStatus");
            return (Criteria) this;
        }

        public Criteria andModuleStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("module_status not between", value1, value2, "moduleStatus");
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