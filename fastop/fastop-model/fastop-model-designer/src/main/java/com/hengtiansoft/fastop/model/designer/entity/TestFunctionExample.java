package com.hengtiansoft.fastop.model.designer.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestFunctionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TestFunctionExample() {
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

        public Criteria andFunNameIsNull() {
            addCriterion("fun_name is null");
            return (Criteria) this;
        }

        public Criteria andFunNameIsNotNull() {
            addCriterion("fun_name is not null");
            return (Criteria) this;
        }

        public Criteria andFunNameEqualTo(String value) {
            addCriterion("fun_name =", value, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameNotEqualTo(String value) {
            addCriterion("fun_name <>", value, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameGreaterThan(String value) {
            addCriterion("fun_name >", value, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameGreaterThanOrEqualTo(String value) {
            addCriterion("fun_name >=", value, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameLessThan(String value) {
            addCriterion("fun_name <", value, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameLessThanOrEqualTo(String value) {
            addCriterion("fun_name <=", value, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameLike(String value) {
            addCriterion("fun_name like", value, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameNotLike(String value) {
            addCriterion("fun_name not like", value, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameIn(List<String> values) {
            addCriterion("fun_name in", values, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameNotIn(List<String> values) {
            addCriterion("fun_name not in", values, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameBetween(String value1, String value2) {
            addCriterion("fun_name between", value1, value2, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameNotBetween(String value1, String value2) {
            addCriterion("fun_name not between", value1, value2, "funName");
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

        public Criteria andSecurityLevelIsNull() {
            addCriterion("security_level is null");
            return (Criteria) this;
        }

        public Criteria andSecurityLevelIsNotNull() {
            addCriterion("security_level is not null");
            return (Criteria) this;
        }

        public Criteria andSecurityLevelEqualTo(Integer value) {
            addCriterion("security_level =", value, "securityLevel");
            return (Criteria) this;
        }

        public Criteria andSecurityLevelNotEqualTo(Integer value) {
            addCriterion("security_level <>", value, "securityLevel");
            return (Criteria) this;
        }

        public Criteria andSecurityLevelGreaterThan(Integer value) {
            addCriterion("security_level >", value, "securityLevel");
            return (Criteria) this;
        }

        public Criteria andSecurityLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("security_level >=", value, "securityLevel");
            return (Criteria) this;
        }

        public Criteria andSecurityLevelLessThan(Integer value) {
            addCriterion("security_level <", value, "securityLevel");
            return (Criteria) this;
        }

        public Criteria andSecurityLevelLessThanOrEqualTo(Integer value) {
            addCriterion("security_level <=", value, "securityLevel");
            return (Criteria) this;
        }

        public Criteria andSecurityLevelIn(List<Integer> values) {
            addCriterion("security_level in", values, "securityLevel");
            return (Criteria) this;
        }

        public Criteria andSecurityLevelNotIn(List<Integer> values) {
            addCriterion("security_level not in", values, "securityLevel");
            return (Criteria) this;
        }

        public Criteria andSecurityLevelBetween(Integer value1, Integer value2) {
            addCriterion("security_level between", value1, value2, "securityLevel");
            return (Criteria) this;
        }

        public Criteria andSecurityLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("security_level not between", value1, value2, "securityLevel");
            return (Criteria) this;
        }

        public Criteria andCommentIsNull() {
            addCriterion("comment is null");
            return (Criteria) this;
        }

        public Criteria andCommentIsNotNull() {
            addCriterion("comment is not null");
            return (Criteria) this;
        }

        public Criteria andCommentEqualTo(String value) {
            addCriterion("comment =", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotEqualTo(String value) {
            addCriterion("comment <>", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentGreaterThan(String value) {
            addCriterion("comment >", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentGreaterThanOrEqualTo(String value) {
            addCriterion("comment >=", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLessThan(String value) {
            addCriterion("comment <", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLessThanOrEqualTo(String value) {
            addCriterion("comment <=", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLike(String value) {
            addCriterion("comment like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotLike(String value) {
            addCriterion("comment not like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentIn(List<String> values) {
            addCriterion("comment in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotIn(List<String> values) {
            addCriterion("comment not in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentBetween(String value1, String value2) {
            addCriterion("comment between", value1, value2, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotBetween(String value1, String value2) {
            addCriterion("comment not between", value1, value2, "comment");
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

        public Criteria andApproveCommentIsNull() {
            addCriterion("approve_comment is null");
            return (Criteria) this;
        }

        public Criteria andApproveCommentIsNotNull() {
            addCriterion("approve_comment is not null");
            return (Criteria) this;
        }

        public Criteria andApproveCommentEqualTo(String value) {
            addCriterion("approve_comment =", value, "approveComment");
            return (Criteria) this;
        }

        public Criteria andApproveCommentNotEqualTo(String value) {
            addCriterion("approve_comment <>", value, "approveComment");
            return (Criteria) this;
        }

        public Criteria andApproveCommentGreaterThan(String value) {
            addCriterion("approve_comment >", value, "approveComment");
            return (Criteria) this;
        }

        public Criteria andApproveCommentGreaterThanOrEqualTo(String value) {
            addCriterion("approve_comment >=", value, "approveComment");
            return (Criteria) this;
        }

        public Criteria andApproveCommentLessThan(String value) {
            addCriterion("approve_comment <", value, "approveComment");
            return (Criteria) this;
        }

        public Criteria andApproveCommentLessThanOrEqualTo(String value) {
            addCriterion("approve_comment <=", value, "approveComment");
            return (Criteria) this;
        }

        public Criteria andApproveCommentLike(String value) {
            addCriterion("approve_comment like", value, "approveComment");
            return (Criteria) this;
        }

        public Criteria andApproveCommentNotLike(String value) {
            addCriterion("approve_comment not like", value, "approveComment");
            return (Criteria) this;
        }

        public Criteria andApproveCommentIn(List<String> values) {
            addCriterion("approve_comment in", values, "approveComment");
            return (Criteria) this;
        }

        public Criteria andApproveCommentNotIn(List<String> values) {
            addCriterion("approve_comment not in", values, "approveComment");
            return (Criteria) this;
        }

        public Criteria andApproveCommentBetween(String value1, String value2) {
            addCriterion("approve_comment between", value1, value2, "approveComment");
            return (Criteria) this;
        }

        public Criteria andApproveCommentNotBetween(String value1, String value2) {
            addCriterion("approve_comment not between", value1, value2, "approveComment");
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

        public Criteria andOtherTechFilesIsNull() {
            addCriterion("other_tech_files is null");
            return (Criteria) this;
        }

        public Criteria andOtherTechFilesIsNotNull() {
            addCriterion("other_tech_files is not null");
            return (Criteria) this;
        }

        public Criteria andOtherTechFilesEqualTo(String value) {
            addCriterion("other_tech_files =", value, "otherTechFiles");
            return (Criteria) this;
        }

        public Criteria andOtherTechFilesNotEqualTo(String value) {
            addCriterion("other_tech_files <>", value, "otherTechFiles");
            return (Criteria) this;
        }

        public Criteria andOtherTechFilesGreaterThan(String value) {
            addCriterion("other_tech_files >", value, "otherTechFiles");
            return (Criteria) this;
        }

        public Criteria andOtherTechFilesGreaterThanOrEqualTo(String value) {
            addCriterion("other_tech_files >=", value, "otherTechFiles");
            return (Criteria) this;
        }

        public Criteria andOtherTechFilesLessThan(String value) {
            addCriterion("other_tech_files <", value, "otherTechFiles");
            return (Criteria) this;
        }

        public Criteria andOtherTechFilesLessThanOrEqualTo(String value) {
            addCriterion("other_tech_files <=", value, "otherTechFiles");
            return (Criteria) this;
        }

        public Criteria andOtherTechFilesLike(String value) {
            addCriterion("other_tech_files like", value, "otherTechFiles");
            return (Criteria) this;
        }

        public Criteria andOtherTechFilesNotLike(String value) {
            addCriterion("other_tech_files not like", value, "otherTechFiles");
            return (Criteria) this;
        }

        public Criteria andOtherTechFilesIn(List<String> values) {
            addCriterion("other_tech_files in", values, "otherTechFiles");
            return (Criteria) this;
        }

        public Criteria andOtherTechFilesNotIn(List<String> values) {
            addCriterion("other_tech_files not in", values, "otherTechFiles");
            return (Criteria) this;
        }

        public Criteria andOtherTechFilesBetween(String value1, String value2) {
            addCriterion("other_tech_files between", value1, value2, "otherTechFiles");
            return (Criteria) this;
        }

        public Criteria andOtherTechFilesNotBetween(String value1, String value2) {
            addCriterion("other_tech_files not between", value1, value2, "otherTechFiles");
            return (Criteria) this;
        }

        public Criteria andDevicePoolIsNull() {
            addCriterion("device_pool is null");
            return (Criteria) this;
        }

        public Criteria andDevicePoolIsNotNull() {
            addCriterion("device_pool is not null");
            return (Criteria) this;
        }

        public Criteria andDevicePoolEqualTo(String value) {
            addCriterion("device_pool =", value, "devicePool");
            return (Criteria) this;
        }

        public Criteria andDevicePoolNotEqualTo(String value) {
            addCriterion("device_pool <>", value, "devicePool");
            return (Criteria) this;
        }

        public Criteria andDevicePoolGreaterThan(String value) {
            addCriterion("device_pool >", value, "devicePool");
            return (Criteria) this;
        }

        public Criteria andDevicePoolGreaterThanOrEqualTo(String value) {
            addCriterion("device_pool >=", value, "devicePool");
            return (Criteria) this;
        }

        public Criteria andDevicePoolLessThan(String value) {
            addCriterion("device_pool <", value, "devicePool");
            return (Criteria) this;
        }

        public Criteria andDevicePoolLessThanOrEqualTo(String value) {
            addCriterion("device_pool <=", value, "devicePool");
            return (Criteria) this;
        }

        public Criteria andDevicePoolLike(String value) {
            addCriterion("device_pool like", value, "devicePool");
            return (Criteria) this;
        }

        public Criteria andDevicePoolNotLike(String value) {
            addCriterion("device_pool not like", value, "devicePool");
            return (Criteria) this;
        }

        public Criteria andDevicePoolIn(List<String> values) {
            addCriterion("device_pool in", values, "devicePool");
            return (Criteria) this;
        }

        public Criteria andDevicePoolNotIn(List<String> values) {
            addCriterion("device_pool not in", values, "devicePool");
            return (Criteria) this;
        }

        public Criteria andDevicePoolBetween(String value1, String value2) {
            addCriterion("device_pool between", value1, value2, "devicePool");
            return (Criteria) this;
        }

        public Criteria andDevicePoolNotBetween(String value1, String value2) {
            addCriterion("device_pool not between", value1, value2, "devicePool");
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

        public Criteria andApproveStatusIsNull() {
            addCriterion("approve_status is null");
            return (Criteria) this;
        }

        public Criteria andApproveStatusIsNotNull() {
            addCriterion("approve_status is not null");
            return (Criteria) this;
        }

        public Criteria andApproveStatusEqualTo(Integer value) {
            addCriterion("approve_status =", value, "approveStatus");
            return (Criteria) this;
        }

        public Criteria andApproveStatusNotEqualTo(Integer value) {
            addCriterion("approve_status <>", value, "approveStatus");
            return (Criteria) this;
        }

        public Criteria andApproveStatusGreaterThan(Integer value) {
            addCriterion("approve_status >", value, "approveStatus");
            return (Criteria) this;
        }

        public Criteria andApproveStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("approve_status >=", value, "approveStatus");
            return (Criteria) this;
        }

        public Criteria andApproveStatusLessThan(Integer value) {
            addCriterion("approve_status <", value, "approveStatus");
            return (Criteria) this;
        }

        public Criteria andApproveStatusLessThanOrEqualTo(Integer value) {
            addCriterion("approve_status <=", value, "approveStatus");
            return (Criteria) this;
        }

        public Criteria andApproveStatusIn(List<Integer> values) {
            addCriterion("approve_status in", values, "approveStatus");
            return (Criteria) this;
        }

        public Criteria andApproveStatusNotIn(List<Integer> values) {
            addCriterion("approve_status not in", values, "approveStatus");
            return (Criteria) this;
        }

        public Criteria andApproveStatusBetween(Integer value1, Integer value2) {
            addCriterion("approve_status between", value1, value2, "approveStatus");
            return (Criteria) this;
        }

        public Criteria andApproveStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("approve_status not between", value1, value2, "approveStatus");
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

        public Criteria andUsingByIsNull() {
            addCriterion("using_by is null");
            return (Criteria) this;
        }

        public Criteria andUsingByIsNotNull() {
            addCriterion("using_by is not null");
            return (Criteria) this;
        }

        public Criteria andUsingByEqualTo(String value) {
            addCriterion("using_by =", value, "usingBy");
            return (Criteria) this;
        }

        public Criteria andUsingByNotEqualTo(String value) {
            addCriterion("using_by <>", value, "usingBy");
            return (Criteria) this;
        }

        public Criteria andUsingByGreaterThan(String value) {
            addCriterion("using_by >", value, "usingBy");
            return (Criteria) this;
        }

        public Criteria andUsingByGreaterThanOrEqualTo(String value) {
            addCriterion("using_by >=", value, "usingBy");
            return (Criteria) this;
        }

        public Criteria andUsingByLessThan(String value) {
            addCriterion("using_by <", value, "usingBy");
            return (Criteria) this;
        }

        public Criteria andUsingByLessThanOrEqualTo(String value) {
            addCriterion("using_by <=", value, "usingBy");
            return (Criteria) this;
        }

        public Criteria andUsingByLike(String value) {
            addCriterion("using_by like", value, "usingBy");
            return (Criteria) this;
        }

        public Criteria andUsingByNotLike(String value) {
            addCriterion("using_by not like", value, "usingBy");
            return (Criteria) this;
        }

        public Criteria andUsingByIn(List<String> values) {
            addCriterion("using_by in", values, "usingBy");
            return (Criteria) this;
        }

        public Criteria andUsingByNotIn(List<String> values) {
            addCriterion("using_by not in", values, "usingBy");
            return (Criteria) this;
        }

        public Criteria andUsingByBetween(String value1, String value2) {
            addCriterion("using_by between", value1, value2, "usingBy");
            return (Criteria) this;
        }

        public Criteria andUsingByNotBetween(String value1, String value2) {
            addCriterion("using_by not between", value1, value2, "usingBy");
            return (Criteria) this;
        }

        public Criteria andDesignerIsNull() {
            addCriterion("designer is null");
            return (Criteria) this;
        }

        public Criteria andDesignerIsNotNull() {
            addCriterion("designer is not null");
            return (Criteria) this;
        }

        public Criteria andDesignerEqualTo(String value) {
            addCriterion("designer =", value, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerNotEqualTo(String value) {
            addCriterion("designer <>", value, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerGreaterThan(String value) {
            addCriterion("designer >", value, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerGreaterThanOrEqualTo(String value) {
            addCriterion("designer >=", value, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerLessThan(String value) {
            addCriterion("designer <", value, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerLessThanOrEqualTo(String value) {
            addCriterion("designer <=", value, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerLike(String value) {
            addCriterion("designer like", value, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerNotLike(String value) {
            addCriterion("designer not like", value, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerIn(List<String> values) {
            addCriterion("designer in", values, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerNotIn(List<String> values) {
            addCriterion("designer not in", values, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerBetween(String value1, String value2) {
            addCriterion("designer between", value1, value2, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerNotBetween(String value1, String value2) {
            addCriterion("designer not between", value1, value2, "designer");
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

        public Criteria andVerifierIsNull() {
            addCriterion("verifier is null");
            return (Criteria) this;
        }

        public Criteria andVerifierIsNotNull() {
            addCriterion("verifier is not null");
            return (Criteria) this;
        }

        public Criteria andVerifierEqualTo(String value) {
            addCriterion("verifier =", value, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierNotEqualTo(String value) {
            addCriterion("verifier <>", value, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierGreaterThan(String value) {
            addCriterion("verifier >", value, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierGreaterThanOrEqualTo(String value) {
            addCriterion("verifier >=", value, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierLessThan(String value) {
            addCriterion("verifier <", value, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierLessThanOrEqualTo(String value) {
            addCriterion("verifier <=", value, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierLike(String value) {
            addCriterion("verifier like", value, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierNotLike(String value) {
            addCriterion("verifier not like", value, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierIn(List<String> values) {
            addCriterion("verifier in", values, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierNotIn(List<String> values) {
            addCriterion("verifier not in", values, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierBetween(String value1, String value2) {
            addCriterion("verifier between", value1, value2, "verifier");
            return (Criteria) this;
        }

        public Criteria andVerifierNotBetween(String value1, String value2) {
            addCriterion("verifier not between", value1, value2, "verifier");
            return (Criteria) this;
        }

        public Criteria andCheckerIsNull() {
            addCriterion("checker is null");
            return (Criteria) this;
        }

        public Criteria andCheckerIsNotNull() {
            addCriterion("checker is not null");
            return (Criteria) this;
        }

        public Criteria andCheckerEqualTo(String value) {
            addCriterion("checker =", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotEqualTo(String value) {
            addCriterion("checker <>", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerGreaterThan(String value) {
            addCriterion("checker >", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerGreaterThanOrEqualTo(String value) {
            addCriterion("checker >=", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerLessThan(String value) {
            addCriterion("checker <", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerLessThanOrEqualTo(String value) {
            addCriterion("checker <=", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerLike(String value) {
            addCriterion("checker like", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotLike(String value) {
            addCriterion("checker not like", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerIn(List<String> values) {
            addCriterion("checker in", values, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotIn(List<String> values) {
            addCriterion("checker not in", values, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerBetween(String value1, String value2) {
            addCriterion("checker between", value1, value2, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotBetween(String value1, String value2) {
            addCriterion("checker not between", value1, value2, "checker");
            return (Criteria) this;
        }

        public Criteria andQualityerIsNull() {
            addCriterion("qualityer is null");
            return (Criteria) this;
        }

        public Criteria andQualityerIsNotNull() {
            addCriterion("qualityer is not null");
            return (Criteria) this;
        }

        public Criteria andQualityerEqualTo(String value) {
            addCriterion("qualityer =", value, "qualityer");
            return (Criteria) this;
        }

        public Criteria andQualityerNotEqualTo(String value) {
            addCriterion("qualityer <>", value, "qualityer");
            return (Criteria) this;
        }

        public Criteria andQualityerGreaterThan(String value) {
            addCriterion("qualityer >", value, "qualityer");
            return (Criteria) this;
        }

        public Criteria andQualityerGreaterThanOrEqualTo(String value) {
            addCriterion("qualityer >=", value, "qualityer");
            return (Criteria) this;
        }

        public Criteria andQualityerLessThan(String value) {
            addCriterion("qualityer <", value, "qualityer");
            return (Criteria) this;
        }

        public Criteria andQualityerLessThanOrEqualTo(String value) {
            addCriterion("qualityer <=", value, "qualityer");
            return (Criteria) this;
        }

        public Criteria andQualityerLike(String value) {
            addCriterion("qualityer like", value, "qualityer");
            return (Criteria) this;
        }

        public Criteria andQualityerNotLike(String value) {
            addCriterion("qualityer not like", value, "qualityer");
            return (Criteria) this;
        }

        public Criteria andQualityerIn(List<String> values) {
            addCriterion("qualityer in", values, "qualityer");
            return (Criteria) this;
        }

        public Criteria andQualityerNotIn(List<String> values) {
            addCriterion("qualityer not in", values, "qualityer");
            return (Criteria) this;
        }

        public Criteria andQualityerBetween(String value1, String value2) {
            addCriterion("qualityer between", value1, value2, "qualityer");
            return (Criteria) this;
        }

        public Criteria andQualityerNotBetween(String value1, String value2) {
            addCriterion("qualityer not between", value1, value2, "qualityer");
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

        public Criteria andHistoryVersionLineIsNull() {
            addCriterion("history_version_line is null");
            return (Criteria) this;
        }

        public Criteria andHistoryVersionLineIsNotNull() {
            addCriterion("history_version_line is not null");
            return (Criteria) this;
        }

        public Criteria andHistoryVersionLineEqualTo(String value) {
            addCriterion("history_version_line =", value, "historyVersionLine");
            return (Criteria) this;
        }

        public Criteria andHistoryVersionLineNotEqualTo(String value) {
            addCriterion("history_version_line <>", value, "historyVersionLine");
            return (Criteria) this;
        }

        public Criteria andHistoryVersionLineGreaterThan(String value) {
            addCriterion("history_version_line >", value, "historyVersionLine");
            return (Criteria) this;
        }

        public Criteria andHistoryVersionLineGreaterThanOrEqualTo(String value) {
            addCriterion("history_version_line >=", value, "historyVersionLine");
            return (Criteria) this;
        }

        public Criteria andHistoryVersionLineLessThan(String value) {
            addCriterion("history_version_line <", value, "historyVersionLine");
            return (Criteria) this;
        }

        public Criteria andHistoryVersionLineLessThanOrEqualTo(String value) {
            addCriterion("history_version_line <=", value, "historyVersionLine");
            return (Criteria) this;
        }

        public Criteria andHistoryVersionLineLike(String value) {
            addCriterion("history_version_line like", value, "historyVersionLine");
            return (Criteria) this;
        }

        public Criteria andHistoryVersionLineNotLike(String value) {
            addCriterion("history_version_line not like", value, "historyVersionLine");
            return (Criteria) this;
        }

        public Criteria andHistoryVersionLineIn(List<String> values) {
            addCriterion("history_version_line in", values, "historyVersionLine");
            return (Criteria) this;
        }

        public Criteria andHistoryVersionLineNotIn(List<String> values) {
            addCriterion("history_version_line not in", values, "historyVersionLine");
            return (Criteria) this;
        }

        public Criteria andHistoryVersionLineBetween(String value1, String value2) {
            addCriterion("history_version_line between", value1, value2, "historyVersionLine");
            return (Criteria) this;
        }

        public Criteria andHistoryVersionLineNotBetween(String value1, String value2) {
            addCriterion("history_version_line not between", value1, value2, "historyVersionLine");
            return (Criteria) this;
        }

        public Criteria andSyncPlanIsNull() {
            addCriterion("sync_plan is null");
            return (Criteria) this;
        }

        public Criteria andSyncPlanIsNotNull() {
            addCriterion("sync_plan is not null");
            return (Criteria) this;
        }

        public Criteria andSyncPlanEqualTo(String value) {
            addCriterion("sync_plan =", value, "syncPlan");
            return (Criteria) this;
        }

        public Criteria andSyncPlanNotEqualTo(String value) {
            addCriterion("sync_plan <>", value, "syncPlan");
            return (Criteria) this;
        }

        public Criteria andSyncPlanGreaterThan(String value) {
            addCriterion("sync_plan >", value, "syncPlan");
            return (Criteria) this;
        }

        public Criteria andSyncPlanGreaterThanOrEqualTo(String value) {
            addCriterion("sync_plan >=", value, "syncPlan");
            return (Criteria) this;
        }

        public Criteria andSyncPlanLessThan(String value) {
            addCriterion("sync_plan <", value, "syncPlan");
            return (Criteria) this;
        }

        public Criteria andSyncPlanLessThanOrEqualTo(String value) {
            addCriterion("sync_plan <=", value, "syncPlan");
            return (Criteria) this;
        }

        public Criteria andSyncPlanLike(String value) {
            addCriterion("sync_plan like", value, "syncPlan");
            return (Criteria) this;
        }

        public Criteria andSyncPlanNotLike(String value) {
            addCriterion("sync_plan not like", value, "syncPlan");
            return (Criteria) this;
        }

        public Criteria andSyncPlanIn(List<String> values) {
            addCriterion("sync_plan in", values, "syncPlan");
            return (Criteria) this;
        }

        public Criteria andSyncPlanNotIn(List<String> values) {
            addCriterion("sync_plan not in", values, "syncPlan");
            return (Criteria) this;
        }

        public Criteria andSyncPlanBetween(String value1, String value2) {
            addCriterion("sync_plan between", value1, value2, "syncPlan");
            return (Criteria) this;
        }

        public Criteria andSyncPlanNotBetween(String value1, String value2) {
            addCriterion("sync_plan not between", value1, value2, "syncPlan");
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

        public Criteria andCreateNewIsNull() {
            addCriterion("create_new is null");
            return (Criteria) this;
        }

        public Criteria andCreateNewIsNotNull() {
            addCriterion("create_new is not null");
            return (Criteria) this;
        }

        public Criteria andCreateNewEqualTo(Integer value) {
            addCriterion("create_new =", value, "createNew");
            return (Criteria) this;
        }

        public Criteria andCreateNewNotEqualTo(Integer value) {
            addCriterion("create_new <>", value, "createNew");
            return (Criteria) this;
        }

        public Criteria andCreateNewGreaterThan(Integer value) {
            addCriterion("create_new >", value, "createNew");
            return (Criteria) this;
        }

        public Criteria andCreateNewGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_new >=", value, "createNew");
            return (Criteria) this;
        }

        public Criteria andCreateNewLessThan(Integer value) {
            addCriterion("create_new <", value, "createNew");
            return (Criteria) this;
        }

        public Criteria andCreateNewLessThanOrEqualTo(Integer value) {
            addCriterion("create_new <=", value, "createNew");
            return (Criteria) this;
        }

        public Criteria andCreateNewIn(List<Integer> values) {
            addCriterion("create_new in", values, "createNew");
            return (Criteria) this;
        }

        public Criteria andCreateNewNotIn(List<Integer> values) {
            addCriterion("create_new not in", values, "createNew");
            return (Criteria) this;
        }

        public Criteria andCreateNewBetween(Integer value1, Integer value2) {
            addCriterion("create_new between", value1, value2, "createNew");
            return (Criteria) this;
        }

        public Criteria andCreateNewNotBetween(Integer value1, Integer value2) {
            addCriterion("create_new not between", value1, value2, "createNew");
            return (Criteria) this;
        }

        public Criteria andMilitaryFuncIsNull() {
            addCriterion("military_func is null");
            return (Criteria) this;
        }

        public Criteria andMilitaryFuncIsNotNull() {
            addCriterion("military_func is not null");
            return (Criteria) this;
        }

        public Criteria andMilitaryFuncEqualTo(Integer value) {
            addCriterion("military_func =", value, "militaryFunc");
            return (Criteria) this;
        }

        public Criteria andMilitaryFuncNotEqualTo(Integer value) {
            addCriterion("military_func <>", value, "militaryFunc");
            return (Criteria) this;
        }

        public Criteria andMilitaryFuncGreaterThan(Integer value) {
            addCriterion("military_func >", value, "militaryFunc");
            return (Criteria) this;
        }

        public Criteria andMilitaryFuncGreaterThanOrEqualTo(Integer value) {
            addCriterion("military_func >=", value, "militaryFunc");
            return (Criteria) this;
        }

        public Criteria andMilitaryFuncLessThan(Integer value) {
            addCriterion("military_func <", value, "militaryFunc");
            return (Criteria) this;
        }

        public Criteria andMilitaryFuncLessThanOrEqualTo(Integer value) {
            addCriterion("military_func <=", value, "militaryFunc");
            return (Criteria) this;
        }

        public Criteria andMilitaryFuncIn(List<Integer> values) {
            addCriterion("military_func in", values, "militaryFunc");
            return (Criteria) this;
        }

        public Criteria andMilitaryFuncNotIn(List<Integer> values) {
            addCriterion("military_func not in", values, "militaryFunc");
            return (Criteria) this;
        }

        public Criteria andMilitaryFuncBetween(Integer value1, Integer value2) {
            addCriterion("military_func between", value1, value2, "militaryFunc");
            return (Criteria) this;
        }

        public Criteria andMilitaryFuncNotBetween(Integer value1, Integer value2) {
            addCriterion("military_func not between", value1, value2, "militaryFunc");
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