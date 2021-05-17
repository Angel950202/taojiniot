package com.taojin.iot.service.task.entity;


import com.taojin.iot.agreement.fujiya.enums.AddressTypeEnum;
import com.taojin.iot.base.comm.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 2020.7.7
 * 用来记录redis每天的值
 * @author bjt
 *
 */
@Entity
@Table(name = "iot_work_finish")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_work_finish_sequence")
public class WorkFinish extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4642689644693568095L;
    
    /**
	 * 编号
	 */
	private String lineNumber;
	/**
	 * 值
	 */
	private Long value;
	/**
	 * 次数
	 */
	private Long count;
	/**
	 * 时长
	 */
	private Long timeLong;
	/**
	 * 信号位
	 */
	private String address;
	
	/**
	 * 信号类型
	 */
	private AddressTypeEnum  addressType;
	/**
	 * 日期，方便检索
	 */
	private String dateTime;

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getTimeLong() {
		return timeLong;
	}

	public void setTimeLong(Long timeLong) {
		this.timeLong = timeLong;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public AddressTypeEnum getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressTypeEnum addressType) {
		this.addressType = addressType;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
}
