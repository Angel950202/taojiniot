package com.taojin.iot.service.report.entity;


import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.taojin.iot.base.comm.entity.BaseEntity;

/**
 * Entity-传感器报表，接收数据 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午2:51:59 author 王杰
 * ============================================================================
 */
@Entity
@Table(name = "iot_report_equipment_sensor")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_report_equipment_sensor_sequence")
public class ReportEquipmentSensor extends BaseEntity {

	private static final long serialVersionUID = 1414147042791102212L;
	/** 传感器ID */
	private Long sensorId;
	/** 传感器编号 */
	private String sensorNumber;
	/** 传感器值 */
	private String sensorValues;
	/** 原始值 */
	private String sensorTrueValue;
	/** 日期 yyyy-MM-dd */
	private String dateTime;

	/**
	 * 获取传感器ID
	 * @return
	 */
	public Long getSensorId() {
		return sensorId;
	}

	/**
	 * 设置传感器ID
	 * @param sensorId
	 */
	public void setSensorId(Long sensorId) {
		this.sensorId = sensorId;
	}

	/**
	 * 获取传感器编号
	 * @return
	 */
	public String getSensorNumber() {
		return sensorNumber;
	}

	/**
	 * 设置传感器编号
	 * @param sensorNumber
	 */
	public void setSensorNumber(String sensorNumber) {
		this.sensorNumber = sensorNumber;
	}

	/**
	 * 获取传感器值 [{"value":"0"}]
	 * @return
	 */
	public String getSensorValues() {
		return sensorValues;
	}

	/**
	 * 设置传感器值[{"value":"0"}]
	 * @param sensorValues
	 */
	public void setSensorValues(String sensorValues) {
		this.sensorValues = sensorValues;
	}

	/**
	 * 获取真实值
	 * @return
	 */
	public String getSensorTrueValue() {
		return sensorTrueValue;
	}
	
	/**
	 * 设置真实值
	 * @param sensorTrueValue
	 */
	public void setSensorTrueValue(String sensorTrueValue) {
		this.sensorTrueValue = sensorTrueValue;
	}

	/**
	 * 获取日期
	 * @return
	 */
	public String getDateTime() {
		return dateTime;
	}

	/**
	 * 设置日期
	 * @param dateTime
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

}
