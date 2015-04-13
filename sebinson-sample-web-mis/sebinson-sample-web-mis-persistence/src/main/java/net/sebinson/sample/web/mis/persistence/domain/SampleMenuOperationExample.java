package net.sebinson.sample.web.mis.persistence.domain;

import java.util.ArrayList;
import java.util.List;

public class SampleMenuOperationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SampleMenuOperationExample() {
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

        public Criteria andOperIdIsNull() {
            addCriterion("oper_id is null");
            return (Criteria) this;
        }

        public Criteria andOperIdIsNotNull() {
            addCriterion("oper_id is not null");
            return (Criteria) this;
        }

        public Criteria andOperIdEqualTo(String value) {
            addCriterion("oper_id =", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdNotEqualTo(String value) {
            addCriterion("oper_id <>", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdGreaterThan(String value) {
            addCriterion("oper_id >", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdGreaterThanOrEqualTo(String value) {
            addCriterion("oper_id >=", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdLessThan(String value) {
            addCriterion("oper_id <", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdLessThanOrEqualTo(String value) {
            addCriterion("oper_id <=", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdLike(String value) {
            addCriterion("oper_id like", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdNotLike(String value) {
            addCriterion("oper_id not like", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdIn(List<String> values) {
            addCriterion("oper_id in", values, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdNotIn(List<String> values) {
            addCriterion("oper_id not in", values, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdBetween(String value1, String value2) {
            addCriterion("oper_id between", value1, value2, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdNotBetween(String value1, String value2) {
            addCriterion("oper_id not between", value1, value2, "operId");
            return (Criteria) this;
        }

        public Criteria andMenuIdIsNull() {
            addCriterion("menu_id is null");
            return (Criteria) this;
        }

        public Criteria andMenuIdIsNotNull() {
            addCriterion("menu_id is not null");
            return (Criteria) this;
        }

        public Criteria andMenuIdEqualTo(String value) {
            addCriterion("menu_id =", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdNotEqualTo(String value) {
            addCriterion("menu_id <>", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdGreaterThan(String value) {
            addCriterion("menu_id >", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdGreaterThanOrEqualTo(String value) {
            addCriterion("menu_id >=", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdLessThan(String value) {
            addCriterion("menu_id <", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdLessThanOrEqualTo(String value) {
            addCriterion("menu_id <=", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdLike(String value) {
            addCriterion("menu_id like", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdNotLike(String value) {
            addCriterion("menu_id not like", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdIn(List<String> values) {
            addCriterion("menu_id in", values, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdNotIn(List<String> values) {
            addCriterion("menu_id not in", values, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdBetween(String value1, String value2) {
            addCriterion("menu_id between", value1, value2, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdNotBetween(String value1, String value2) {
            addCriterion("menu_id not between", value1, value2, "menuId");
            return (Criteria) this;
        }

        public Criteria andOperCodeIsNull() {
            addCriterion("oper_code is null");
            return (Criteria) this;
        }

        public Criteria andOperCodeIsNotNull() {
            addCriterion("oper_code is not null");
            return (Criteria) this;
        }

        public Criteria andOperCodeEqualTo(String value) {
            addCriterion("oper_code =", value, "operCode");
            return (Criteria) this;
        }

        public Criteria andOperCodeNotEqualTo(String value) {
            addCriterion("oper_code <>", value, "operCode");
            return (Criteria) this;
        }

        public Criteria andOperCodeGreaterThan(String value) {
            addCriterion("oper_code >", value, "operCode");
            return (Criteria) this;
        }

        public Criteria andOperCodeGreaterThanOrEqualTo(String value) {
            addCriterion("oper_code >=", value, "operCode");
            return (Criteria) this;
        }

        public Criteria andOperCodeLessThan(String value) {
            addCriterion("oper_code <", value, "operCode");
            return (Criteria) this;
        }

        public Criteria andOperCodeLessThanOrEqualTo(String value) {
            addCriterion("oper_code <=", value, "operCode");
            return (Criteria) this;
        }

        public Criteria andOperCodeLike(String value) {
            addCriterion("oper_code like", value, "operCode");
            return (Criteria) this;
        }

        public Criteria andOperCodeNotLike(String value) {
            addCriterion("oper_code not like", value, "operCode");
            return (Criteria) this;
        }

        public Criteria andOperCodeIn(List<String> values) {
            addCriterion("oper_code in", values, "operCode");
            return (Criteria) this;
        }

        public Criteria andOperCodeNotIn(List<String> values) {
            addCriterion("oper_code not in", values, "operCode");
            return (Criteria) this;
        }

        public Criteria andOperCodeBetween(String value1, String value2) {
            addCriterion("oper_code between", value1, value2, "operCode");
            return (Criteria) this;
        }

        public Criteria andOperCodeNotBetween(String value1, String value2) {
            addCriterion("oper_code not between", value1, value2, "operCode");
            return (Criteria) this;
        }

        public Criteria andOperNameIsNull() {
            addCriterion("oper_name is null");
            return (Criteria) this;
        }

        public Criteria andOperNameIsNotNull() {
            addCriterion("oper_name is not null");
            return (Criteria) this;
        }

        public Criteria andOperNameEqualTo(String value) {
            addCriterion("oper_name =", value, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameNotEqualTo(String value) {
            addCriterion("oper_name <>", value, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameGreaterThan(String value) {
            addCriterion("oper_name >", value, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameGreaterThanOrEqualTo(String value) {
            addCriterion("oper_name >=", value, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameLessThan(String value) {
            addCriterion("oper_name <", value, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameLessThanOrEqualTo(String value) {
            addCriterion("oper_name <=", value, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameLike(String value) {
            addCriterion("oper_name like", value, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameNotLike(String value) {
            addCriterion("oper_name not like", value, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameIn(List<String> values) {
            addCriterion("oper_name in", values, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameNotIn(List<String> values) {
            addCriterion("oper_name not in", values, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameBetween(String value1, String value2) {
            addCriterion("oper_name between", value1, value2, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameNotBetween(String value1, String value2) {
            addCriterion("oper_name not between", value1, value2, "operName");
            return (Criteria) this;
        }

        public Criteria andOperTypeIsNull() {
            addCriterion("oper_type is null");
            return (Criteria) this;
        }

        public Criteria andOperTypeIsNotNull() {
            addCriterion("oper_type is not null");
            return (Criteria) this;
        }

        public Criteria andOperTypeEqualTo(String value) {
            addCriterion("oper_type =", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeNotEqualTo(String value) {
            addCriterion("oper_type <>", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeGreaterThan(String value) {
            addCriterion("oper_type >", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeGreaterThanOrEqualTo(String value) {
            addCriterion("oper_type >=", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeLessThan(String value) {
            addCriterion("oper_type <", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeLessThanOrEqualTo(String value) {
            addCriterion("oper_type <=", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeLike(String value) {
            addCriterion("oper_type like", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeNotLike(String value) {
            addCriterion("oper_type not like", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeIn(List<String> values) {
            addCriterion("oper_type in", values, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeNotIn(List<String> values) {
            addCriterion("oper_type not in", values, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeBetween(String value1, String value2) {
            addCriterion("oper_type between", value1, value2, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeNotBetween(String value1, String value2) {
            addCriterion("oper_type not between", value1, value2, "operType");
            return (Criteria) this;
        }

        public Criteria andOperActionsIsNull() {
            addCriterion("oper_actions is null");
            return (Criteria) this;
        }

        public Criteria andOperActionsIsNotNull() {
            addCriterion("oper_actions is not null");
            return (Criteria) this;
        }

        public Criteria andOperActionsEqualTo(String value) {
            addCriterion("oper_actions =", value, "operActions");
            return (Criteria) this;
        }

        public Criteria andOperActionsNotEqualTo(String value) {
            addCriterion("oper_actions <>", value, "operActions");
            return (Criteria) this;
        }

        public Criteria andOperActionsGreaterThan(String value) {
            addCriterion("oper_actions >", value, "operActions");
            return (Criteria) this;
        }

        public Criteria andOperActionsGreaterThanOrEqualTo(String value) {
            addCriterion("oper_actions >=", value, "operActions");
            return (Criteria) this;
        }

        public Criteria andOperActionsLessThan(String value) {
            addCriterion("oper_actions <", value, "operActions");
            return (Criteria) this;
        }

        public Criteria andOperActionsLessThanOrEqualTo(String value) {
            addCriterion("oper_actions <=", value, "operActions");
            return (Criteria) this;
        }

        public Criteria andOperActionsLike(String value) {
            addCriterion("oper_actions like", value, "operActions");
            return (Criteria) this;
        }

        public Criteria andOperActionsNotLike(String value) {
            addCriterion("oper_actions not like", value, "operActions");
            return (Criteria) this;
        }

        public Criteria andOperActionsIn(List<String> values) {
            addCriterion("oper_actions in", values, "operActions");
            return (Criteria) this;
        }

        public Criteria andOperActionsNotIn(List<String> values) {
            addCriterion("oper_actions not in", values, "operActions");
            return (Criteria) this;
        }

        public Criteria andOperActionsBetween(String value1, String value2) {
            addCriterion("oper_actions between", value1, value2, "operActions");
            return (Criteria) this;
        }

        public Criteria andOperActionsNotBetween(String value1, String value2) {
            addCriterion("oper_actions not between", value1, value2, "operActions");
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