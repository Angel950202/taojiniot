package com.taojin.iot.service.equipment.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.taojin.iot.base.comm.entity.BaseEntity;


@Entity
@Table(name = "iot_equipment_report")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_equipment_report_sequence")
public class DeviceReport extends BaseEntity {

	private static final long serialVersionUID = -9197117667167329308L;
	
	/**
	 * 生产线编号
	 */
	private String lineNumber;
	/**
	 * 生产线名称
	 */
	private String lineName;
	
	/**
	 * 工站名称
	 */
	private String workStationName;
	
	/**
	 * 工站编号
	 */
	private String workStationNumber;
	
	/**
	 * 开机时间
	 */
	private String bootUpTime;
	
	/**
	 * 运行时间
	 */
	private String runningTime;
	
	/**
	 * 故障时间
	 */
	private String failureTime;
	
	/**
	 * 设备稼动率
	 */
	private String deviceTrs;

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getWorkStationName() {
		return workStationName;
	}

	public void setWorkStationName(String workStationName) {
		this.workStationName = workStationName;
	}

	public String getWorkStationNumber() {
		return workStationNumber;
	}

	public void setWorkStationNumber(String workStationNumber) {
		this.workStationNumber = workStationNumber;
	}

	public String getBootUpTime() {
		return bootUpTime;
	}

	public void setBootUpTime(String bootUpTime) {
		this.bootUpTime = bootUpTime;
	}

	public String getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(String runningTime) {
		this.runningTime = runningTime;
	}

	public String getFailureTime() {
		return failureTime;
	}

	public void setFailureTime(String failureTime) {
		this.failureTime = failureTime;
	}

	public String getDeviceTrs() {
		return deviceTrs;
	}

	public void setDeviceTrs(String deviceTrs) {
		this.deviceTrs = deviceTrs;
	}
	
}

