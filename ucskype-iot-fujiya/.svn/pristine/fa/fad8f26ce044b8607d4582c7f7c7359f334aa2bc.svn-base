package com.taojin.iot.service.task.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.taojin.iot.base.comm.entity.BaseEntity;

@Entity
@Table(name = "iot_work_trace_detail")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_work_trace_detail_sequence")
public class WorkTraceDetail extends BaseEntity {


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
     * 工站名称
     */
    private String works;

    /**
     * 创建时间
     */
    private String creatTime;
    
    /**
     * 实际生产总数
     */
    private Integer totalNum;
    
    /**
     * OK数
     */
    private Integer okNum;
    
    
   /**
    * NOK数量
    */
    private Integer nokNum;
    
    /**
     * 追溯信息
     */
    private String msg;
    
    /**
     * 生产型号
     */
    private String model;

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

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
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


	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getOkNum() {
		return okNum;
	}

	public void setOkNum(Integer okNum) {
		this.okNum = okNum;
	}

	public Integer getNokNum() {
		return nokNum;
	}

	public void setNokNum(Integer nokNum) {
		this.nokNum = nokNum;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	
}
