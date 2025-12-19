package com.hengtiansoft.fastop.model.designer.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FunctionSuiteExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FunctionSuiteExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTestFunIdIsNull() {
            addCriterion("test_fun_id is null");
            return (Criteria) this;
        }

        public Criteria andTestFunIdIsNotNull() {
            addCriterion("test_fun_id is not null");
            return (Criteria) this;
        }

        public Criteria andTestFunIdEqualTo(Integer value) {
            addCriterion("test_fun_id =", value, "testFunId");
            return (Criteria) this;
        }

        public Criteria andTestFunIdNotEqualTo(Integer value) {
            addCriterion("test_fun_id <>", value, "testFunId");
            return (Criteria) this;
        }

        public Criteria andTestFunIdGreaterThan(Integer value) {
            addCriterion("test_fun_id >", value, "testFunId");
            return (Criteria) this;
        }

        public Criteria andTestFunIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("test_fun_id >=", value, "testFunId");
            return (Criteria) this;
        }

        public Criteria andTestFunIdLessThan(Integer value) {
            addCriterion("test_fun_id <", value, "testFunId");
            return (Criteria) this;
        }

        public Criteria andTestFunIdLessThanOrEqualTo(Integer value) {
            addCriterion("test_fun_id <=", value, "testFunId");
            return (Criteria) this;
        }

        public Criteria andTestFunIdIn(List<Integer> values) {
            addCriterion("test_fun_id in", values, "testFunId");
            return (Criteria) this;
        }

        public Criteria andTestFunIdNotIn(List<Integer> values) {
            addCriterion("test_fun_id not in", values, "testFunId");
            return (Criteria) this;
        }

        public Criteria andTestFunIdBetween(Integer value1, Integer value2) {
            addCriterion("test_fun_id between", value1, value2, "testFunId");
            return (Criteria) this;
        }

        public Criteria andTestFunIdNotBetween(Integer value1, Integer value2) {
            addCriterion("test_fun_id not between", value1, value2, "testFunId");
            return (Criteria) this;
        }

        public Criteria andFunNumIsNull() {
            addCriterion("fun_num is null");
            return (Criteria) this;
        }

        public Criteria andFunNumIsNotNull() {
            addCriterion("fun_num is not null");
            return (Criteria) this;
        }

        public Criteria andFunNumEqualTo(Integer value) {
            addCriterion("fun_num =", value, "funNum");
            return (Criteria) this;
        }

        public Criteria andFunNumNotEqualTo(Integer value) {
            addCriterion("fun_num <>", value, "funNum");
            return (Criteria) this;
        }

        public Criteria andFunNumGreaterThan(Integer value) {
            addCriterion("fun_num >", value, "funNum");
            return (Criteria) this;
        }

        public Criteria andFunNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("fun_num >=", value, "funNum");
            return (Criteria) this;
        }

        public Criteria andFunNumLessThan(Integer value) {
            addCriterion("fun_num <", value, "funNum");
            return (Criteria) this;
        }

        public Criteria andFunNumLessThanOrEqualTo(Integer value) {
            addCriterion("fun_num <=", value, "funNum");
            return (Criteria) this;
        }

        public Criteria andFunNumIn(List<Integer> values) {
            addCriterion("fun_num in", values, "funNum");
            return (Criteria) this;
        }

        public Criteria andFunNumNotIn(List<Integer> values) {
            addCriterion("fun_num not in", values, "funNum");
            return (Criteria) this;
        }

        public Criteria andFunNumBetween(Integer value1, Integer value2) {
            addCriterion("fun_num between", value1, value2, "funNum");
            return (Criteria) this;
        }

        public Criteria andFunNumNotBetween(Integer value1, Integer value2) {
            addCriterion("fun_num not between", value1, value2, "funNum");
            return (Criteria) this;
        }

        public Criteria andFunVersionIsNull() {
            addCriterion("fun_version is null");
            return (Criteria) this;
        }

        public Criteria andFunVersionIsNotNull() {
            addCriterion("fun_version is not null");
            return (Criteria) this;
        }

        public Criteria andFunVersionEqualTo(Integer value) {
            addCriterion("fun_version =", value, "funVersion");
            return (Criteria) this;
        }

        public Criteria andFunVersionNotEqualTo(Integer value) {
            addCriterion("fun_version <>", value, "funVersion");
            return (Criteria) this;
        }

        public Criteria andFunVersionGreaterThan(Integer value) {
            addCriterion("fun_version >", value, "funVersion");
            return (Criteria) this;
        }

        public Criteria andFunVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("fun_version >=", value, "funVersion");
            return (Criteria) this;
        }

        public Criteria andFunVersionLessThan(Integer value) {
            addCriterion("fun_version <", value, "funVersion");
            return (Criteria) this;
        }

        public Criteria andFunVersionLessThanOrEqualTo(Integer value) {
            addCriterion("fun_version <=", value, "funVersion");
            return (Criteria) this;
        }

        public Criteria andFunVersionIn(List<Integer> values) {
            addCriterion("fun_version in", values, "funVersion");
            return (Criteria) this;
        }

        public Criteria andFunVersionNotIn(List<Integer> values) {
            addCriterion("fun_version not in", values, "funVersion");
            return (Criteria) this;
        }

        public Criteria andFunVersionBetween(Integer value1, Integer value2) {
            addCriterion("fun_version between", value1, value2, "funVersion");
            return (Criteria) this;
        }

        public Criteria andFunVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("fun_version not between", value1, value2, "funVersion");
            return (Criteria) this;
        }

        public Criteria andFunOrderIsNull() {
            addCriterion("fun_order is null");
            return (Criteria) this;
        }

        public Criteria andFunOrderIsNotNull() {
            addCriterion("fun_order is not null");
            return (Criteria) this;
        }

        public Criteria andFunOrderEqualTo(Integer value) {
            addCriterion("fun_order =", value, "funOrder");
            return (Criteria) this;
        }

        public Criteria andFunOrderNotEqualTo(Integer value) {
            addCriterion("fun_order <>", value, "funOrder");
            return (Criteria) this;
        }

        public Criteria andFunOrderGreaterThan(Integer value) {
            addCriterion("fun_order >", value, "funOrder");
            return (Criteria) this;
        }

        public Criteria andFunOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("fun_order >=", value, "funOrder");
            return (Criteria) this;
        }

        public Criteria andFunOrderLessThan(Integer value) {
            addCriterion("fun_order <", value, "funOrder");
            return (Criteria) this;
        }

        public Criteria andFunOrderLessThanOrEqualTo(Integer value) {
            addCriterion("fun_order <=", value, "funOrder");
            return (Criteria) this;
        }

        public Criteria andFunOrderIn(List<Integer> values) {
            addCriterion("fun_order in", values, "funOrder");
            return (Criteria) this;
        }

        public Criteria andFunOrderNotIn(List<Integer> values) {
            addCriterion("fun_order not in", values, "funOrder");
            return (Criteria) this;
        }

        public Criteria andFunOrderBetween(Integer value1, Integer value2) {
            addCriterion("fun_order between", value1, value2, "funOrder");
            return (Criteria) this;
        }

        public Criteria andFunOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("fun_order not between", value1, value2, "funOrder");
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