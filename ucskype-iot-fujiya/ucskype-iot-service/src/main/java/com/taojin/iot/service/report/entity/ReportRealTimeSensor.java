package com.taojin.iot.service.report.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.taojin.iot.base.comm.entity.BaseEntity;

@Entity
@Table(name = "iot_report_real_time_sensor")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_report_real_time_sensor_sequence")
public class ReportRealTimeSensor extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 传感器记录
	 */
	private Long sensorId;
	/** 传感器编号 */
	private String sensorNumber;
	/** 所属设备号 */
	private Long equipmentNumber;
	/**传感器值名称*/
	private String sensorName;
	/** 传感器值 */
	private Integer sensorValues;
	/** 传感器单位 */
	private String sensorUnit;
	
	public ReportRealTimeSensor() {
		super();
	}
	public ReportRealTimeSensor(Long sensorId, String sensorNumber,
			Long equipmentNumber, String sensorName, Integer sensorValues,
			String sensorUnit, String quarter) {
		super();
		this.sensorId = sensorId;
		this.sensorNumber = sensorNumber;
		this.equipmentNumber = equipmentNumber;
		this.sensorName = sensorName;
		this.sensorValues = sensorValues;
		this.sensorUnit = sensorUnit;
	}
	public Long getEquipmentNumber() {
		return equipmentNumber;
	}
	public void setEquipmentNumber(Long equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}
	public Long getSensorId() {
		return sensorId;
	}
	public void setSensorId(Long sensorId) {
		this.sensorId = sensorId;
	}
	public String getSensorNumber() {
		return sensorNumber;
	}
	public void setSensorNumber(String sensorNumber) {
		this.sensorNumber = sensorNumber;
	}
	public Integer getSensorValues() {
		return sensorValues;
	}
	public void setSensorValues(Integer sensorValues) {
		this.sensorValues = sensorValues;
	}
	public String getSensorUnit() {
		return sensorUnit;
	}
	public void setSensorUnit(String sensorUnit) {
		this.sensorUnit = sensorUnit;
	}
	public String getSensorName() {
		return sensorName;
	}
	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
	
}
