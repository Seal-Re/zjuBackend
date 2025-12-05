package com.hengtiansoft.fastop.model.designer.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestSuiteExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TestSuiteExample() {
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

        public Criteria andSuiteNameIsNull() {
            addCriterion("suite_name is null");
            return (Criteria) this;
        }

        public Criteria andSuiteNameIsNotNull() {
            addCriterion("suite_name is not null");
            return (Criteria) this;
        }

        public Criteria andSuiteNameEqualTo(String value) {
            addCriterion("suite_name =", value, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameNotEqualTo(String value) {
            addCriterion("suite_name <>", value, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameGreaterThan(String value) {
            addCriterion("suite_name >", value, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameGreaterThanOrEqualTo(String value) {
            addCriterion("suite_name >=", value, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameLessThan(String value) {
            addCriterion("suite_name <", value, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameLessThanOrEqualTo(String value) {
            addCriterion("suite_name <=", value, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameLike(String value) {
            addCriterion("suite_name like", value, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameNotLike(String value) {
            addCriterion("suite_name not like", value, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameIn(List<String> values) {
            addCriterion("suite_name in", values, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameNotIn(List<String> values) {
            addCriterion("suite_name not in", values, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameBetween(String value1, String value2) {
            addCriterion("suite_name between", value1, value2, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameNotBetween(String value1, String value2) {
            addCriterion("suite_name not between", value1, value2, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteDescIsNull() {
            addCriterion("suite_desc is null");
            return (Criteria) this;
        }

        public Criteria andSuiteDescIsNotNull() {
            addCriterion("suite_desc is not null");
            return (Criteria) this;
        }

        public Criteria andSuiteDescEqualTo(String value) {
            addCriterion("suite_desc =", value, "suiteDesc");
            return (Criteria) this;
        }

        public Criteria andSuiteDescNotEqualTo(String value) {
            addCriterion("suite_desc <>", value, "suiteDesc");
            return (Criteria) this;
        }

        public Criteria andSuiteDescGreaterThan(String value) {
            addCriterion("suite_desc >", value, "suiteDesc");
            return (Criteria) this;
        }

        public Criteria andSuiteDescGreaterThanOrEqualTo(String value) {
            addCriterion("suite_desc >=", value, "suiteDesc");
            return (Criteria) this;
        }

        public Criteria andSuiteDescLessThan(String value) {
            addCriterion("suite_desc <", value, "suiteDesc");
            return (Criteria) this;
        }

        public Criteria andSuiteDescLessThanOrEqualTo(String value) {
            addCriterion("suite_desc <=", value, "suiteDesc");
            return (Criteria) this;
        }

        public Criteria andSuiteDescLike(String value) {
            addCriterion("suite_desc like", value, "suiteDesc");
            return (Criteria) this;
        }

        public Criteria andSuiteDescNotLike(String value) {
            addCriterion("suite_desc not like", value, "suiteDesc");
            return (Criteria) this;
        }

        public Criteria andSuiteDescIn(List<String> values) {
            addCriterion("suite_desc in", values, "suiteDesc");
            return (Criteria) this;
        }

        public Criteria andSuiteDescNotIn(List<String> values) {
            addCriterion("suite_desc not in", values, "suiteDesc");
            return (Criteria) this;
        }

        public Criteria andSuiteDescBetween(String value1, String value2) {
            addCriterion("suite_desc between", value1, value2, "suiteDesc");
            return (Criteria) this;
        }

        public Criteria andSuiteDescNotBetween(String value1, String value2) {
            addCriterion("suite_desc not between", value1, value2, "suiteDesc");
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

        public Criteria andPlaneEffectMinIsNull() {
            addCriterion("plane_effect_min is null");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMinIsNotNull() {
            addCriterion("plane_effect_min is not null");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMinEqualTo(Integer value) {
            addCriterion("plane_effect_min =", value, "planeEffectMin");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMinNotEqualTo(Integer value) {
            addCriterion("plane_effect_min <>", value, "planeEffectMin");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMinGreaterThan(Integer value) {
            addCriterion("plane_effect_min >", value, "planeEffectMin");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMinGreaterThanOrEqualTo(Integer value) {
            addCriterion("plane_effect_min >=", value, "planeEffectMin");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMinLessThan(Integer value) {
            addCriterion("plane_effect_min <", value, "planeEffectMin");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMinLessThanOrEqualTo(Integer value) {
            addCriterion("plane_effect_min <=", value, "planeEffectMin");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMinIn(List<Integer> values) {
            addCriterion("plane_effect_min in", values, "planeEffectMin");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMinNotIn(List<Integer> values) {
            addCriterion("plane_effect_min not in", values, "planeEffectMin");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMinBetween(Integer value1, Integer value2) {
            addCriterion("plane_effect_min between", value1, value2, "planeEffectMin");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMinNotBetween(Integer value1, Integer value2) {
            addCriterion("plane_effect_min not between", value1, value2, "planeEffectMin");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMaxIsNull() {
            addCriterion("plane_effect_max is null");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMaxIsNotNull() {
            addCriterion("plane_effect_max is not null");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMaxEqualTo(Integer value) {
            addCriterion("plane_effect_max =", value, "planeEffectMax");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMaxNotEqualTo(Integer value) {
            addCriterion("plane_effect_max <>", value, "planeEffectMax");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMaxGreaterThan(Integer value) {
            addCriterion("plane_effect_max >", value, "planeEffectMax");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMaxGreaterThanOrEqualTo(Integer value) {
            addCriterion("plane_effect_max >=", value, "planeEffectMax");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMaxLessThan(Integer value) {
            addCriterion("plane_effect_max <", value, "planeEffectMax");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMaxLessThanOrEqualTo(Integer value) {
            addCriterion("plane_effect_max <=", value, "planeEffectMax");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMaxIn(List<Integer> values) {
            addCriterion("plane_effect_max in", values, "planeEffectMax");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMaxNotIn(List<Integer> values) {
            addCriterion("plane_effect_max not in", values, "planeEffectMax");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMaxBetween(Integer value1, Integer value2) {
            addCriterion("plane_effect_max between", value1, value2, "planeEffectMax");
            return (Criteria) this;
        }

        public Criteria andPlaneEffectMaxNotBetween(Integer value1, Integer value2) {
            addCriterion("plane_effect_max not between", value1, value2, "planeEffectMax");
            return (Criteria) this;
        }

        public Criteria andProoferIsNull() {
            addCriterion("proofer is null");
            return (Criteria) this;
        }

        public Criteria andProoferIsNotNull() {
            addCriterion("proofer is not null");
            return (Criteria) this;
        }

        public Criteria andProoferEqualTo(String value) {
            addCriterion("proofer =", value, "proofer");
            return (Criteria) this;
        }

        public Criteria andProoferNotEqualTo(String value) {
            addCriterion("proofer <>", value, "proofer");
            return (Criteria) this;
        }

        public Criteria andProoferGreaterThan(String value) {
            addCriterion("proofer >", value, "proofer");
            return (Criteria) this;
        }

        public Criteria andProoferGreaterThanOrEqualTo(String value) {
            addCriterion("proofer >=", value, "proofer");
            return (Criteria) this;
        }

        public Criteria andProoferLessThan(String value) {
            addCriterion("proofer <", value, "proofer");
            return (Criteria) this;
        }

        public Criteria andProoferLessThanOrEqualTo(String value) {
            addCriterion("proofer <=", value, "proofer");
            return (Criteria) this;
        }

        public Criteria andProoferLike(String value) {
            addCriterion("proofer like", value, "proofer");
            return (Criteria) this;
        }

        public Criteria andProoferNotLike(String value) {
            addCriterion("proofer not like", value, "proofer");
            return (Criteria) this;
        }

        public Criteria andProoferIn(List<String> values) {
            addCriterion("proofer in", values, "proofer");
            return (Criteria) this;
        }

        public Criteria andProoferNotIn(List<String> values) {
            addCriterion("proofer not in", values, "proofer");
            return (Criteria) this;
        }

        public Criteria andProoferBetween(String value1, String value2) {
            addCriterion("proofer between", value1, value2, "proofer");
            return (Criteria) this;
        }

        public Criteria andProoferNotBetween(String value1, String value2) {
            addCriterion("proofer not between", value1, value2, "proofer");
            return (Criteria) this;
        }

        public Criteria andApproverIsNull() {
            addCriterion("approver is null");
            return (Criteria) this;
        }

        public Criteria andApproverIsNotNull() {
            addCriterion("approver is not null");
            return (Criteria) this;
        }

        public Criteria andApproverEqualTo(String value) {
            addCriterion("approver =", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverNotEqualTo(String value) {
            addCriterion("approver <>", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverGreaterThan(String value) {
            addCriterion("approver >", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverGreaterThanOrEqualTo(String value) {
            addCriterion("approver >=", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverLessThan(String value) {
            addCriterion("approver <", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverLessThanOrEqualTo(String value) {
            addCriterion("approver <=", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverLike(String value) {
            addCriterion("approver like", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverNotLike(String value) {
            addCriterion("approver not like", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverIn(List<String> values) {
            addCriterion("approver in", values, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverNotIn(List<String> values) {
            addCriterion("approver not in", values, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverBetween(String value1, String value2) {
            addCriterion("approver between", value1, value2, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverNotBetween(String value1, String value2) {
            addCriterion("approver not between", value1, value2, "approver");
            return (Criteria) this;
        }

        public Criteria andSubmitterIsNull() {
            addCriterion("submitter is null");
            return (Criteria) this;
        }

        public Criteria andSubmitterIsNotNull() {
            addCriterion("submitter is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitterEqualTo(String value) {
            addCriterion("submitter =", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterNotEqualTo(String value) {
            addCriterion("submitter <>", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterGreaterThan(String value) {
            addCriterion("submitter >", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterGreaterThanOrEqualTo(String value) {
            addCriterion("submitter >=", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterLessThan(String value) {
            addCriterion("submitter <", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterLessThanOrEqualTo(String value) {
            addCriterion("submitter <=", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterLike(String value) {
            addCriterion("submitter like", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterNotLike(String value) {
            addCriterion("submitter not like", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterIn(List<String> values) {
            addCriterion("submitter in", values, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterNotIn(List<String> values) {
            addCriterion("submitter not in", values, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterBetween(String value1, String value2) {
            addCriterion("submitter between", value1, value2, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterNotBetween(String value1, String value2) {
            addCriterion("submitter not between", value1, value2, "submitter");
            return (Criteria) this;
        }

        public Criteria andTestBaseIdIsNull() {
            addCriterion("test_base_id is null");
            return (Criteria) this;
        }

        public Criteria andTestBaseIdIsNotNull() {
            addCriterion("test_base_id is not null");
            return (Criteria) this;
        }

        public Criteria andTestBaseIdEqualTo(Integer value) {
            addCriterion("test_base_id =", value, "testBaseId");
            return (Criteria) this;
        }

        public Criteria andTestBaseIdNotEqualTo(Integer value) {
            addCriterion("test_base_id <>", value, "testBaseId");
            return (Criteria) this;
        }

        public Criteria andTestBaseIdGreaterThan(Integer value) {
            addCriterion("test_base_id >", value, "testBaseId");
            return (Criteria) this;
        }

        public Criteria andTestBaseIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("test_base_id >=", value, "testBaseId");
            return (Criteria) this;
        }

        public Criteria andTestBaseIdLessThan(Integer value) {
            addCriterion("test_base_id <", value, "testBaseId");
            return (Criteria) this;
        }

        public Criteria andTestBaseIdLessThanOrEqualTo(Integer value) {
            addCriterion("test_base_id <=", value, "testBaseId");
            return (Criteria) this;
        }

        public Criteria andTestBaseIdIn(List<Integer> values) {
            addCriterion("test_base_id in", values, "testBaseId");
            return (Criteria) this;
        }

        public Criteria andTestBaseIdNotIn(List<Integer> values) {
            addCriterion("test_base_id not in", values, "testBaseId");
            return (Criteria) this;
        }

        public Criteria andTestBaseIdBetween(Integer value1, Integer value2) {
            addCriterion("test_base_id between", value1, value2, "testBaseId");
            return (Criteria) this;
        }

        public Criteria andTestBaseIdNotBetween(Integer value1, Integer value2) {
            addCriterion("test_base_id not between", value1, value2, "testBaseId");
            return (Criteria) this;
        }

        public Criteria andListApprStatusIsNull() {
            addCriterion("list_appr_status is null");
            return (Criteria) this;
        }

        public Criteria andListApprStatusIsNotNull() {
            addCriterion("list_appr_status is not null");
            return (Criteria) this;
        }

        public Criteria andListApprStatusEqualTo(Integer value) {
            addCriterion("list_appr_status =", value, "listApprStatus");
            return (Criteria) this;
        }

        public Criteria andListApprStatusNotEqualTo(Integer value) {
            addCriterion("list_appr_status <>", value, "listApprStatus");
            return (Criteria) this;
        }

        public Criteria andListApprStatusGreaterThan(Integer value) {
            addCriterion("list_appr_status >", value, "listApprStatus");
            return (Criteria) this;
        }

        public Criteria andListApprStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("list_appr_status >=", value, "listApprStatus");
            return (Criteria) this;
        }

        public Criteria andListApprStatusLessThan(Integer value) {
            addCriterion("list_appr_status <", value, "listApprStatus");
            return (Criteria) this;
        }

        public Criteria andListApprStatusLessThanOrEqualTo(Integer value) {
            addCriterion("list_appr_status <=", value, "listApprStatus");
            return (Criteria) this;
        }

        public Criteria andListApprStatusIn(List<Integer> values) {
            addCriterion("list_appr_status in", values, "listApprStatus");
            return (Criteria) this;
        }

        public Criteria andListApprStatusNotIn(List<Integer> values) {
            addCriterion("list_appr_status not in", values, "listApprStatus");
            return (Criteria) this;
        }

        public Criteria andListApprStatusBetween(Integer value1, Integer value2) {
            addCriterion("list_appr_status between", value1, value2, "listApprStatus");
            return (Criteria) this;
        }

        public Criteria andListApprStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("list_appr_status not between", value1, value2, "listApprStatus");
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

        public Criteria andApprChainIsNull() {
            addCriterion("appr_chain is null");
            return (Criteria) this;
        }

        public Criteria andApprChainIsNotNull() {
            addCriterion("appr_chain is not null");
            return (Criteria) this;
        }

        public Criteria andApprChainEqualTo(String value) {
            addCriterion("appr_chain =", value, "apprChain");
            return (Criteria) this;
        }

        public Criteria andApprChainNotEqualTo(String value) {
            addCriterion("appr_chain <>", value, "apprChain");
            return (Criteria) this;
        }

        public Criteria andApprChainGreaterThan(String value) {
            addCriterion("appr_chain >", value, "apprChain");
            return (Criteria) this;
        }

        public Criteria andApprChainGreaterThanOrEqualTo(String value) {
            addCriterion("appr_chain >=", value, "apprChain");
            return (Criteria) this;
        }

        public Criteria andApprChainLessThan(String value) {
            addCriterion("appr_chain <", value, "apprChain");
            return (Criteria) this;
        }

        public Criteria andApprChainLessThanOrEqualTo(String value) {
            addCriterion("appr_chain <=", value, "apprChain");
            return (Criteria) this;
        }

        public Criteria andApprChainLike(String value) {
            addCriterion("appr_chain like", value, "apprChain");
            return (Criteria) this;
        }

        public Criteria andApprChainNotLike(String value) {
            addCriterion("appr_chain not like", value, "apprChain");
            return (Criteria) this;
        }

        public Criteria andApprChainIn(List<String> values) {
            addCriterion("appr_chain in", values, "apprChain");
            return (Criteria) this;
        }

        public Criteria andApprChainNotIn(List<String> values) {
            addCriterion("appr_chain not in", values, "apprChain");
            return (Criteria) this;
        }

        public Criteria andApprChainBetween(String value1, String value2) {
            addCriterion("appr_chain between", value1, value2, "apprChain");
            return (Criteria) this;
        }

        public Criteria andApprChainNotBetween(String value1, String value2) {
            addCriterion("appr_chain not between", value1, value2, "apprChain");
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

        public Criteria andMesdceCodeIsNull() {
            addCriterion("mesdce_code is null");
            return (Criteria) this;
        }

        public Criteria andMesdceCodeIsNotNull() {
            addCriterion("mesdce_code is not null");
            return (Criteria) this;
        }

        public Criteria andMesdceCodeEqualTo(String value) {
            addCriterion("mesdce_code =", value, "mesdceCode");
            return (Criteria) this;
        }

        public Criteria andMesdceCodeNotEqualTo(String value) {
            addCriterion("mesdce_code <>", value, "mesdceCode");
            return (Criteria) this;
        }

        public Criteria andMesdceCodeGreaterThan(String value) {
            addCriterion("mesdce_code >", value, "mesdceCode");
            return (Criteria) this;
        }

        public Criteria andMesdceCodeGreaterThanOrEqualTo(String value) {
            addCriterion("mesdce_code >=", value, "mesdceCode");
            return (Criteria) this;
        }

        public Criteria andMesdceCodeLessThan(String value) {
            addCriterion("mesdce_code <", value, "mesdceCode");
            return (Criteria) this;
        }

        public Criteria andMesdceCodeLessThanOrEqualTo(String value) {
            addCriterion("mesdce_code <=", value, "mesdceCode");
            return (Criteria) this;
        }

        public Criteria andMesdceCodeLike(String value) {
            addCriterion("mesdce_code like", value, "mesdceCode");
            return (Criteria) this;
        }

        public Criteria andMesdceCodeNotLike(String value) {
            addCriterion("mesdce_code not like", value, "mesdceCode");
            return (Criteria) this;
        }

        public Criteria andMesdceCodeIn(List<String> values) {
            addCriterion("mesdce_code in", values, "mesdceCode");
            return (Criteria) this;
        }

        public Criteria andMesdceCodeNotIn(List<String> values) {
            addCriterion("mesdce_code not in", values, "mesdceCode");
            return (Criteria) this;
        }

        public Criteria andMesdceCodeBetween(String value1, String value2) {
            addCriterion("mesdce_code between", value1, value2, "mesdceCode");
            return (Criteria) this;
        }

        public Criteria andMesdceCodeNotBetween(String value1, String value2) {
            addCriterion("mesdce_code not between", value1, value2, "mesdceCode");
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