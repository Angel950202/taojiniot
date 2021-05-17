package com.taojin.iot.service.task.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import com.taojin.iot.base.comm.entity.BaseEntity;

@Entity
@Table(name = "iot_production_line")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_production_line_sequence")
public class ProductionLine extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -549759373087148360L;
	
	/**
	 * 编号
	 */
	private String lineNumber;
	/**
	 * 名字
	 */
	private String lineName;
	/**
	 * 进度目标
	 */
	private int progressTarget;
	/**
	 * 良品目标
	 */
	private int yieldTarget;
	/**
	 * TRS目标
	 */
	private int trsTarget;
	/**
	 * CT值
	 */
	private Double ct;
	
	/**
	 * 创建时间
	 */
	private String CreatTime;
	
	/**
	 * dtu编号
	 */
	private String dtuCode;
	
	/**
	 *创建人
	 */
	private String CreaterName;
	
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
	public int getProgressTarget() {
		return progressTarget;
	}
	public void setProgressTarget(int progressTarget) {
		this.progressTarget = progressTarget;
	}
	public int getYieldTarget() {
		return yieldTarget;
	}
	public void setYieldTarget(int yieldTarget) {
		this.yieldTarget = yieldTarget;
	}
	public int getTrsTarget() {
		return trsTarget;
	}
	public void setTrsTarget(int trsTarget) {
		this.trsTarget = trsTarget;
	}
	public Double getCt() {
		return ct;
	}
	public void setCt(Double ct) {
		this.ct = ct;
	}
    @Column(name="creat_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,updatable = false)
    @Generated(GenerationTime.INSERT)
	public String getCreatTime() {
		return CreatTime;
	}
	public void setCreatTime(String creatTime) {
		CreatTime = creatTime;
	}
	public String getCreaterName() {
		return CreaterName;
	}
	public void setCreaterName(String createrName) {
		CreaterName = createrName;
	}
	public String getDtuCode() {
		return dtuCode;
	}
	public void setDtuCode(String dtuCode) {
		this.dtuCode = dtuCode;
	}


}
