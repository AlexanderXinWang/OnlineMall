package com.xju.onlinemall.common.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OutputOderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OutputOderExample() {
        oredCriteria = new ArrayList<>();
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
            criteria = new ArrayList<>();
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

        public Criteria andOutIdIsNull() {
            addCriterion("out_id is null");
            return (Criteria) this;
        }

        public Criteria andOutIdIsNotNull() {
            addCriterion("out_id is not null");
            return (Criteria) this;
        }

        public Criteria andOutIdEqualTo(Integer value) {
            addCriterion("out_id =", value, "outId");
            return (Criteria) this;
        }

        public Criteria andOutIdNotEqualTo(Integer value) {
            addCriterion("out_id <>", value, "outId");
            return (Criteria) this;
        }

        public Criteria andOutIdGreaterThan(Integer value) {
            addCriterion("out_id >", value, "outId");
            return (Criteria) this;
        }

        public Criteria andOutIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_id >=", value, "outId");
            return (Criteria) this;
        }

        public Criteria andOutIdLessThan(Integer value) {
            addCriterion("out_id <", value, "outId");
            return (Criteria) this;
        }

        public Criteria andOutIdLessThanOrEqualTo(Integer value) {
            addCriterion("out_id <=", value, "outId");
            return (Criteria) this;
        }

        public Criteria andOutIdIn(List<Integer> values) {
            addCriterion("out_id in", values, "outId");
            return (Criteria) this;
        }

        public Criteria andOutIdNotIn(List<Integer> values) {
            addCriterion("out_id not in", values, "outId");
            return (Criteria) this;
        }

        public Criteria andOutIdBetween(Integer value1, Integer value2) {
            addCriterion("out_id between", value1, value2, "outId");
            return (Criteria) this;
        }

        public Criteria andOutIdNotBetween(Integer value1, Integer value2) {
            addCriterion("out_id not between", value1, value2, "outId");
            return (Criteria) this;
        }

        public Criteria andPmIdIsNull() {
            addCriterion("pm_id is null");
            return (Criteria) this;
        }

        public Criteria andPmIdIsNotNull() {
            addCriterion("pm_id is not null");
            return (Criteria) this;
        }

        public Criteria andPmIdEqualTo(Integer value) {
            addCriterion("pm_id =", value, "pmId");
            return (Criteria) this;
        }

        public Criteria andPmIdNotEqualTo(Integer value) {
            addCriterion("pm_id <>", value, "pmId");
            return (Criteria) this;
        }

        public Criteria andPmIdGreaterThan(Integer value) {
            addCriterion("pm_id >", value, "pmId");
            return (Criteria) this;
        }

        public Criteria andPmIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("pm_id >=", value, "pmId");
            return (Criteria) this;
        }

        public Criteria andPmIdLessThan(Integer value) {
            addCriterion("pm_id <", value, "pmId");
            return (Criteria) this;
        }

        public Criteria andPmIdLessThanOrEqualTo(Integer value) {
            addCriterion("pm_id <=", value, "pmId");
            return (Criteria) this;
        }

        public Criteria andPmIdIn(List<Integer> values) {
            addCriterion("pm_id in", values, "pmId");
            return (Criteria) this;
        }

        public Criteria andPmIdNotIn(List<Integer> values) {
            addCriterion("pm_id not in", values, "pmId");
            return (Criteria) this;
        }

        public Criteria andPmIdBetween(Integer value1, Integer value2) {
            addCriterion("pm_id between", value1, value2, "pmId");
            return (Criteria) this;
        }

        public Criteria andPmIdNotBetween(Integer value1, Integer value2) {
            addCriterion("pm_id not between", value1, value2, "pmId");
            return (Criteria) this;
        }

        public Criteria andOutNumberIsNull() {
            addCriterion("out_number is null");
            return (Criteria) this;
        }

        public Criteria andOutNumberIsNotNull() {
            addCriterion("out_number is not null");
            return (Criteria) this;
        }

        public Criteria andOutNumberEqualTo(Integer value) {
            addCriterion("out_number =", value, "outNumber");
            return (Criteria) this;
        }

        public Criteria andOutNumberNotEqualTo(Integer value) {
            addCriterion("out_number <>", value, "outNumber");
            return (Criteria) this;
        }

        public Criteria andOutNumberGreaterThan(Integer value) {
            addCriterion("out_number >", value, "outNumber");
            return (Criteria) this;
        }

        public Criteria andOutNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_number >=", value, "outNumber");
            return (Criteria) this;
        }

        public Criteria andOutNumberLessThan(Integer value) {
            addCriterion("out_number <", value, "outNumber");
            return (Criteria) this;
        }

        public Criteria andOutNumberLessThanOrEqualTo(Integer value) {
            addCriterion("out_number <=", value, "outNumber");
            return (Criteria) this;
        }

        public Criteria andOutNumberIn(List<Integer> values) {
            addCriterion("out_number in", values, "outNumber");
            return (Criteria) this;
        }

        public Criteria andOutNumberNotIn(List<Integer> values) {
            addCriterion("out_number not in", values, "outNumber");
            return (Criteria) this;
        }

        public Criteria andOutNumberBetween(Integer value1, Integer value2) {
            addCriterion("out_number between", value1, value2, "outNumber");
            return (Criteria) this;
        }

        public Criteria andOutNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("out_number not between", value1, value2, "outNumber");
            return (Criteria) this;
        }

        public Criteria andOutDateIsNull() {
            addCriterion("out_date is null");
            return (Criteria) this;
        }

        public Criteria andOutDateIsNotNull() {
            addCriterion("out_date is not null");
            return (Criteria) this;
        }

        public Criteria andOutDateEqualTo(Date value) {
            addCriterion("out_date =", value, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateNotEqualTo(Date value) {
            addCriterion("out_date <>", value, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateGreaterThan(Date value) {
            addCriterion("out_date >", value, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateGreaterThanOrEqualTo(Date value) {
            addCriterion("out_date >=", value, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateLessThan(Date value) {
            addCriterion("out_date <", value, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateLessThanOrEqualTo(Date value) {
            addCriterion("out_date <=", value, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateIn(List<Date> values) {
            addCriterion("out_date in", values, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateNotIn(List<Date> values) {
            addCriterion("out_date not in", values, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateBetween(Date value1, Date value2) {
            addCriterion("out_date between", value1, value2, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutDateNotBetween(Date value1, Date value2) {
            addCriterion("out_date not between", value1, value2, "outDate");
            return (Criteria) this;
        }

        public Criteria andOutStatusIsNull() {
            addCriterion("out_status is null");
            return (Criteria) this;
        }

        public Criteria andOutStatusIsNotNull() {
            addCriterion("out_status is not null");
            return (Criteria) this;
        }

        public Criteria andOutStatusEqualTo(Integer value) {
            addCriterion("out_status =", value, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutStatusNotEqualTo(Integer value) {
            addCriterion("out_status <>", value, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutStatusGreaterThan(Integer value) {
            addCriterion("out_status >", value, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_status >=", value, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutStatusLessThan(Integer value) {
            addCriterion("out_status <", value, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutStatusLessThanOrEqualTo(Integer value) {
            addCriterion("out_status <=", value, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutStatusIn(List<Integer> values) {
            addCriterion("out_status in", values, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutStatusNotIn(List<Integer> values) {
            addCriterion("out_status not in", values, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutStatusBetween(Integer value1, Integer value2) {
            addCriterion("out_status between", value1, value2, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("out_status not between", value1, value2, "outStatus");
            return (Criteria) this;
        }

        public Criteria andOutIsDeleteIsNull() {
            addCriterion("out_is_delete is null");
            return (Criteria) this;
        }

        public Criteria andOutIsDeleteIsNotNull() {
            addCriterion("out_is_delete is not null");
            return (Criteria) this;
        }

        public Criteria andOutIsDeleteEqualTo(Byte value) {
            addCriterion("out_is_delete =", value, "outIsDelete");
            return (Criteria) this;
        }

        public Criteria andOutIsDeleteNotEqualTo(Byte value) {
            addCriterion("out_is_delete <>", value, "outIsDelete");
            return (Criteria) this;
        }

        public Criteria andOutIsDeleteGreaterThan(Byte value) {
            addCriterion("out_is_delete >", value, "outIsDelete");
            return (Criteria) this;
        }

        public Criteria andOutIsDeleteGreaterThanOrEqualTo(Byte value) {
            addCriterion("out_is_delete >=", value, "outIsDelete");
            return (Criteria) this;
        }

        public Criteria andOutIsDeleteLessThan(Byte value) {
            addCriterion("out_is_delete <", value, "outIsDelete");
            return (Criteria) this;
        }

        public Criteria andOutIsDeleteLessThanOrEqualTo(Byte value) {
            addCriterion("out_is_delete <=", value, "outIsDelete");
            return (Criteria) this;
        }

        public Criteria andOutIsDeleteIn(List<Byte> values) {
            addCriterion("out_is_delete in", values, "outIsDelete");
            return (Criteria) this;
        }

        public Criteria andOutIsDeleteNotIn(List<Byte> values) {
            addCriterion("out_is_delete not in", values, "outIsDelete");
            return (Criteria) this;
        }

        public Criteria andOutIsDeleteBetween(Byte value1, Byte value2) {
            addCriterion("out_is_delete between", value1, value2, "outIsDelete");
            return (Criteria) this;
        }

        public Criteria andOutIsDeleteNotBetween(Byte value1, Byte value2) {
            addCriterion("out_is_delete not between", value1, value2, "outIsDelete");
            return (Criteria) this;
        }

        public Criteria andOutKeepFieldIsNull() {
            addCriterion("out_keep_field is null");
            return (Criteria) this;
        }

        public Criteria andOutKeepFieldIsNotNull() {
            addCriterion("out_keep_field is not null");
            return (Criteria) this;
        }

        public Criteria andOutKeepFieldEqualTo(String value) {
            addCriterion("out_keep_field =", value, "outKeepField");
            return (Criteria) this;
        }

        public Criteria andOutKeepFieldNotEqualTo(String value) {
            addCriterion("out_keep_field <>", value, "outKeepField");
            return (Criteria) this;
        }

        public Criteria andOutKeepFieldGreaterThan(String value) {
            addCriterion("out_keep_field >", value, "outKeepField");
            return (Criteria) this;
        }

        public Criteria andOutKeepFieldGreaterThanOrEqualTo(String value) {
            addCriterion("out_keep_field >=", value, "outKeepField");
            return (Criteria) this;
        }

        public Criteria andOutKeepFieldLessThan(String value) {
            addCriterion("out_keep_field <", value, "outKeepField");
            return (Criteria) this;
        }

        public Criteria andOutKeepFieldLessThanOrEqualTo(String value) {
            addCriterion("out_keep_field <=", value, "outKeepField");
            return (Criteria) this;
        }

        public Criteria andOutKeepFieldLike(String value) {
            addCriterion("out_keep_field like", value, "outKeepField");
            return (Criteria) this;
        }

        public Criteria andOutKeepFieldNotLike(String value) {
            addCriterion("out_keep_field not like", value, "outKeepField");
            return (Criteria) this;
        }

        public Criteria andOutKeepFieldIn(List<String> values) {
            addCriterion("out_keep_field in", values, "outKeepField");
            return (Criteria) this;
        }

        public Criteria andOutKeepFieldNotIn(List<String> values) {
            addCriterion("out_keep_field not in", values, "outKeepField");
            return (Criteria) this;
        }

        public Criteria andOutKeepFieldBetween(String value1, String value2) {
            addCriterion("out_keep_field between", value1, value2, "outKeepField");
            return (Criteria) this;
        }

        public Criteria andOutKeepFieldNotBetween(String value1, String value2) {
            addCriterion("out_keep_field not between", value1, value2, "outKeepField");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNull() {
            addCriterion("product_id is null");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNotNull() {
            addCriterion("product_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualTo(Integer value) {
            addCriterion("product_id =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotEqualTo(Integer value) {
            addCriterion("product_id <>", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThan(Integer value) {
            addCriterion("product_id >", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("product_id >=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThan(Integer value) {
            addCriterion("product_id <", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThanOrEqualTo(Integer value) {
            addCriterion("product_id <=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdIn(List<Integer> values) {
            addCriterion("product_id in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotIn(List<Integer> values) {
            addCriterion("product_id not in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdBetween(Integer value1, Integer value2) {
            addCriterion("product_id between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotBetween(Integer value1, Integer value2) {
            addCriterion("product_id not between", value1, value2, "productId");
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