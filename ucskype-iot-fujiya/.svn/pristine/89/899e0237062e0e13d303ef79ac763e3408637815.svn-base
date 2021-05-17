/*
 * 
 * 
 * 
 */
package com.taojin.iot.base.comm;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
/**
 * 筛选
 * 
 * 
 * 
 */
public class Filter implements Serializable {

	private static final long serialVersionUID = -8712382358441065075L;

	/**
	 * 运算符
	 */
	public enum Operator {
		/** 并 */
		and,
		
		/** 或  */
		or,	
		
		/** 等于 */
		eq,
		
		/** 不等于 */
		ne,

		/** 大于 */
		gt,

		/** 小于 */
		lt,

		/** 大于等于 */
		ge,

		/** 小于等于 */
		le,

		/** 相似 */
		like,

		/**值包含 */
		in,
		
		/**属性包含*/
		propertyIn,

		/** 为Null */
		isNull,

		/** 不为Null */
		isNotNull,
		
		/** 时间段 */
		between,
		
		/**
		 * 时间段查询
		 */
		between_two;

		/**
		 * 从String中获取Operator
		 * 
		 * @param value
		 *            值
		 * @return String对应的operator
		 */
		public static Operator fromString(String value) {
			return Operator.valueOf(value.toLowerCase());
		}
	}
	
	/** 默认是否忽略大小写 */
	private static final boolean DEFAULT_IGNORE_CASE = false;

	/** 属性 */
	private String property;
	
	/** 属性数组 */
	private String[] propertys;

	/** 运算符 */
	private Operator operator;
	
	/** 属性数组运算符 */
	private Operator propertyInOperator;

	/** 值 */
	private Object value;

	/**用于时间段*/
	private Object value1;
	
	private List<Filter> filters = new ArrayList<Filter>();
	/** 是否忽略大小写 */
	private Boolean ignoreCase = DEFAULT_IGNORE_CASE;

	/**
	 * 初始化一个新创建的Filter对象
	 */
	public Filter() {
	}

	/**
	 * 初始化一个新创建的Filter对象
	 * 
	 * @param property
	 *            属性
	 * @param operator
	 *            运算符
	 * @param value
	 *            值
	 */
	public Filter(String property, Operator operator, Object value) {
		this.property = property;
		this.operator = operator;
		this.value = value;
	}
	
	/**
	 * 初始化一个新创建的Filter对象
	 * @param operator   and or
	 * @param filters
	 */
	public Filter(Operator operator, List<Filter> filters) {
		this.operator = operator;
		this.filters = filters;
	}
	/**
	 * 初始化一个新创建的Filter对象
	 * @param propertys
	 * @param propertyInOperator 
	 * 		  like le ge lt gt  现仅支持like,若扩展需在BaseDaoImpl中扩展
	 * @param value
	 */
	/*后期新增*/
	public Filter(String[] propertys,Operator propertyInOperator,Object value){
		this.propertys = propertys;
		this.operator = Operator.propertyIn;
		this.propertyInOperator = propertyInOperator;
		this.value = value;
	}

	/**
	 * 重载（用于时间段查询）
	 * @param property
	 * @param operator
	 * @param value1
	 * @param value2
	 */
	public Filter(String property, Operator operator, Object value,Object value1){
		this.property = property;
		this.operator = operator;
		this.value = value;
		this.value1=value1;
	}
	
	/**
	 * 初始化一个新创建的Filter对象
	 * 
	 * @param property
	 *            属性
	 * @param operator
	 *            运算符
	 * @param value
	 *            值
	 * @param ignoreCase
	 *            忽略大小写
	 */
	public Filter(String property, Operator operator, Object value, boolean ignoreCase) {
		this.property = property;
		this.operator = operator;
		this.value = value;
		this.ignoreCase = ignoreCase;
	}

	public static Filter and(List<Filter> filters) {
		return new Filter(Operator.and, filters);
	}
	
	public static Filter or(List<Filter> filters) {
		return new Filter(Operator.or, filters);
	}
	
	/**
	 * 返回等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 等于筛选
	 */
	public static Filter eq(String property, Object value) {
		return new Filter(property, Operator.eq, value);
	}

	/**
	 * 返回等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @param ignoreCase
	 *            忽略大小写
	 * @return 等于筛选
	 */
	public static Filter eq(String property, Object value, boolean ignoreCase) {
		return new Filter(property, Operator.eq, value, ignoreCase);
	}

	/**
	 * 返回不等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 不等于筛选
	 */
	public static Filter ne(String property, Object value) {
		return new Filter(property, Operator.ne, value);
	}

	/**
	 * 返回不等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @param ignoreCase
	 *            忽略大小写
	 * @return 不等于筛选
	 */
	public static Filter ne(String property, Object value, boolean ignoreCase) {
		return new Filter(property, Operator.ne, value, ignoreCase);
	}

	/**
	 * 返回大于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 大于筛选
	 */
	public static Filter gt(String property, Object value) {
		return new Filter(property, Operator.gt, value);
	}

	/**
	 * 返回小于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 小于筛选
	 */
	public static Filter lt(String property, Object value) {
		return new Filter(property, Operator.lt, value);
	}

	/**
	 * 返回大于等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 大于等于筛选
	 */
	public static Filter ge(String property, Object value) {
		return new Filter(property, Operator.ge, value);
	}

	/**
	 * 返回小于等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 小于等于筛选
	 */
	public static Filter le(String property, Object value) {
		return new Filter(property, Operator.le, value);
	}

	/**
	 * 返回相似筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 相似筛选
	 */
	public static Filter like(String property, Object value) {
		return new Filter(property, Operator.like, value);
	}

	/**
	 * 返回包含筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 包含筛选
	 */
	public static Filter in(String property, Object value) {
		return new Filter(property, Operator.in, value);
	}
	
	/**
	 * 返回符合属性值的结果
	 * @param propertys
	 * @param propertyInOperator 	
	 *   	  like le ge lt gt 现仅支持like,若扩展需在BaseDaoImpl中扩展
	 * @param value
	 * @return
	 */
	/*后期新增*/
	public static Filter propertyIn(String[] propertys,Operator propertyInOperator,Object value) {
		return new Filter(propertys,propertyInOperator, value);
	}
	/**
	 * 返回为Null筛选
	 * 
	 * @param property
	 *            属性
	 * @return 为Null筛选
	 */
	public static Filter isNull(String property) {
		return new Filter(property, Operator.isNull, null);
	}

	/**
	 * 返回不为Null筛选
	 * 
	 * @param property
	 *            属性
	 * @return 不为Null筛选
	 */
	public static Filter isNotNull(String property) {
		return new Filter(property, Operator.isNotNull, null);
	}

	/**
	 * 返回范围查询
	 * @param property
	 * @param value
	 * @param value1
	 * @return
	 */
	public static Filter between(String property,Object value,Object value1){
		return new Filter(property,Operator.between,value,value1);
	}
	
	/**
	 * 时间段查询
	 * @param property
	 * @param value
	 * @param value1
	 * @return
	 */
	public static Filter between_two(String property,Object value,Object value1){
		return new Filter(property,Operator.between_two,value,value1);
	}
	
	/**
	 * 返回忽略大小写筛选
	 * 
	 * @return 忽略大小写筛选
	 */
	public Filter ignoreCase() {
		this.ignoreCase = true;
		return this;
	}

	/**
	 * 获取属性
	 * 
	 * @return 属性
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * 设置属性
	 * 
	 * @param property
	 *            属性
	 */
	public void setProperty(String property) {
		this.property = property;
	}
	
	/**
	 * 获取多个属性
	 * @return
	 */
	/*后期新增*/
	public String[] getPropertys() {
		return propertys;
	}
	/**
	 * 设置多个属性
	 * @param propertys
	 */
	/*后期新增*/
	public void setPropertys(String[] propertys) {
		this.propertys = propertys;
	}
	
	/**
	 * 获取运算符
	 * 
	 * @return 运算符
	 */
	public Operator getOperator() {
		return operator;
	}

	/**
	 * 设置运算符
	 * 
	 * @param operator
	 *            运算符
	 */
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
	/*后期新增*/
	public Operator getPropertyInOperator() {
		return propertyInOperator;
	}
	/*后期新增*/
	public void setPropertyInOperator(Operator propertyInOperator) {
		this.propertyInOperator = propertyInOperator;
	}
	
	/**
	 * 获取值
	 * 
	 * @return 值
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 设置值
	 * 
	 * @param value
	 *            值
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	/**
	 * 获取值1
	 * 
	 * @return 值1
	 */
	public Object getValue1() {
		return value1;
	}
	/**
	 * 设置值1
	 * 
	 * @param value
	 *            值1
	 */
	public void setValue1(Object value1) {
		this.value1 = value1;
	}
	
	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	/**
	 * 获取是否忽略大小写
	 * 
	 * @return 是否忽略大小写
	 */
	public Boolean getIgnoreCase() {
		return ignoreCase;
	}

	/**
	 * 设置是否忽略大小写
	 * 
	 * @param ignoreCase
	 *            是否忽略大小写
	 */
	public void setIgnoreCase(Boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Filter other = (Filter) obj;
		return new EqualsBuilder().append(getProperty(), other.getProperty()).append(getOperator(), other.getOperator()).append(getValue(), other.getValue()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getProperty()).append(getOperator()).append(getValue()).toHashCode();
	}

}