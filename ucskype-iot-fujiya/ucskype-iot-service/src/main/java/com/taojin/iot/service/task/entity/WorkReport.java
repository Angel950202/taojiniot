package com.taojin.iot.service.task.entity;


import com.taojin.iot.base.comm.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "iot_work_report")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_work_report_sequence")
public class WorkReport  extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -852446413970642571L;

	/**
     * 报工单编号
     */
	private String workReporNumber;
	
	/**
     * 工站
     */
	private String equipmentName;
	
	/**
     * 生产任务单号
     */
	private String orderNumber;
	
	 /**
     * 生产型号
     */
    private String productionModel;
    
    /**
     * 生产线
     */
	private String productionLine;
    /**
     * 计划生产数量
     */
    private int planCount;
    
    /**
     * 实际生产数量
     */
    private int actualCount;


	/**
     * ok数量
     */
	private int okCount;
	
	/**
     * nok数量
     */
	private int nokCount;
	
	/**
     * nok原因
     */
	private String nokCause;
	
	/**
     * 缺陷率
     */
	private String nokPercent;
	
	/**
     * 合格率
     */
	private String yieldPercent;
	/**
     * 汇报人
     */
	private String reporter;
	/**
     * 汇报日期
     */
	private String reportTime;
	
	
	public String getWorkReporNumber() {
		return workReporNumber;
	}
	public void setWorkReporNumber(String workReporNumber) {
		this.workReporNumber = workReporNumber;
	}
	public String getProductionModel() {
		return productionModel;
	}
	public void setProductionModel(String productionModel) {
		this.productionModel = productionModel;
	}
	public int getPlanCount() {
		return planCount;
	}
	public void setPlanCount(int planCount) {
		this.planCount = planCount;
	}
	public int getActualCount() {
		return actualCount;
	}
	public void setActualCount(int actualCount) {
		this.actualCount = actualCount;
	}
	public int getOkCount() {
		return okCount;
	}
	public void setOkCount(int okCount) {
		this.okCount = okCount;
	}
	public int getNokCount() {
		return nokCount;
	}
	public void setNokCount(int nokCount) {
		this.nokCount = nokCount;
	}
	public String getReporter() {
		return reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	public String getReportTime() {
		return reportTime;
	}
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}
	public String getNokCause() {
		return nokCause;
	}
	public void setNokCause(String nokCause) {
		this.nokCause = nokCause;
	}

	public String getProductionLine() {
		return productionLine;
	}
	public void setProductionLine(String productionLine) {
		this.productionLine = productionLine;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public String getYieldPercent() {
		return yieldPercent;
	}
	public void setYieldPercent(String yieldPercent) {
		this.yieldPercent = yieldPercent;
	}
	public String getNokPercent() {
		return nokPercent;
	}
	public void setNokPercent(String nokPercent) {
		this.nokPercent = nokPercent;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}


}
