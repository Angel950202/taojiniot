package com.taojin.iot.service.kanban.entiy;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.taojin.iot.base.comm.entity.BaseEntity;

@Entity
@Table(name = "iot_trs")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_trs_sequence")
public class Trs extends BaseEntity {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4642689644693568095L;
	
	/**
	 * 生产线编号
	 */
	private String lineNumber;
	/**
	 * 生产线名称
	 */
	private String lineName;
	
	/**
	 * ct值
	 */
	private Integer ct;
	
	/**
	 * 当前班次trs值
	 */
	private Double trs;
	
	/**
	 * 当前班次产线生产总数
	 */
	private Double totalNum;
	
	/**
	 * 当前班次产线OK总数
	 */
	private Double OKnum;
	
	/**
	 * 当前班次产线不良总数
	 */
	private Double NOKnum;
	
	/**
	 * trs目标值
	 */
	private Integer trsTarget;
	
	/**
	 * 当前班次生产任务单实际生产时间
	 */
	private String finishTime;
	
	
	/**
	 * 班次
	 * 早班：07:00:00 - 15:00:00
	 * 中班：15:00:00 - 23:00:00
	 * 晚班：23:00:00 - 07:00:00
	 */
	private Integer Shifts;


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


	public Integer getCt() {
		return ct;
	}


	public void setCt(Integer ct) {
		this.ct = ct;
	}


	public Double getTrs() {
		return trs;
	}


	public void setTrs(Double trs) {
		this.trs = trs;
	}


	public Double getTotalNum() {
		return totalNum;
	}


	public void setTotalNum(Double totalNum) {
		this.totalNum = totalNum;
	}


	public Double getOKnum() {
		return OKnum;
	}


	public void setOKnum(Double oKnum) {
		OKnum = oKnum;
	}


	public Double getNOKnum() {
		return NOKnum;
	}


	public void setNOKnum(Double nOKnum) {
		NOKnum = nOKnum;
	}


	public Integer getTrsTarget() {
		return trsTarget;
	}


	public void setTrsTarget(Integer trsTarget) {
		this.trsTarget = trsTarget;
	}


	public String getFinishTime() {
		return finishTime;
	}


	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}


	public Integer getShifts() {
		return Shifts;
	}


	public void setShifts(Integer shifts) {
		Shifts = shifts;
	}
	
}
