package com.taojin.iot.service.equipment.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.taojin.iot.base.comm.entity.BaseEntity;

/**
 * Entity-传感器状态-内存表 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午3:20:50 author 王杰
 * ============================================================================
 */
@Entity
@Table(name = "iot_equipment_sensor_state")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_equipment_sensor_state_sequence")
public class EquipmentSensorState extends BaseEntity {

	private static final long serialVersionUID = 3855180309953155208L;

	/** 传感器 */
	private String sensorIdNumber;
	/** 传感器值 [{"value":"值1"},{"value":"值2"}] */
	private String sensorValues;
	/** 原始值 */
	private String sensorTrueValue;
	/** 状态0:未连接,1:已连接 */
	private Integer state;

	/**
	 * 获取传感器ID号码
	 * @return
	 */
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getSensorIdNumber() {
		return sensorIdNumber;
	}

	public void setSensorIdNumber(String sensorIdNumber) {
		this.sensorIdNumber = sensorIdNumber;
	}

	/**
	 * 获取上报值
	 * 
	 * @return
	 */
	@Column(nullable = false)
	public String getSensorValues() {
		return sensorValues;
	}

	/**
	 * 设置上报值
	 * 
	 * @param sensorValues
	 */
	public void setSensorValues(String sensorValues) {
		this.sensorValues = sensorValues;
	}

	/**
	 * 获取真实值
	 * 
	 * @return
	 */
	public String getSensorTrueValue() {
		return sensorTrueValue;
	}

	/**
	 * 设置真实上报值
	 * 
	 * @param sensorTrueValue
	 */
	public void setSensorTrueValue(String sensorTrueValue) {
		this.sensorTrueValue = sensorTrueValue;
	}

	/**
	 * 获取状态
	 * 
	 * @return 0：未连接 1：已连接
	 */
	@Column(nullable = false)
	public Integer getState() {
		return state;
	}

	/**
	 * 设置状态 0：未连接 1：已连接
	 * 
	 * @param state
	 */
	public void setState(Integer state) {
		this.state = state;
	}

}
