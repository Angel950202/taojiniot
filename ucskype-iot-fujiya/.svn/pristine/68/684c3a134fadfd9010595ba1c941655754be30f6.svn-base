package com.taojin.iot.service.task.entity;


import com.taojin.iot.base.comm.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "iot_work_report_detail")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_work_report_detail_sequence")
public class WorkReportDetail extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7436035758092819759L;

	 /**
     * 生产任务单编号
     */
    private String orderNumber;
  
    /**
     * 生产线编号
     */
    private String lineCode;
    
    /**
     * 生产线名称
     */
    private String lineName;
    
    /**
     * 追溯信息
     */
    private String msg;
    
    /**
     * 工站名称
     */
    private String works;

    /**
     * 创建时间
     */
    private String creatTime;
    
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getLineCode() {
		return lineCode;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getWorks() {
		return works;
	}

	public void setWorks(String works) {
		this.works = works;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

}
