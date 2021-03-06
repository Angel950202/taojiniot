package com.taojin.iot.service.report.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import com.taojin.iot.base.comm.entity.BaseEntity;

@Entity
@Table(name = "iot_report_dtu")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_report_dtu_sequence")
public class ReportDTU extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6005674076741832762L;

	/** 设备编号 */
	private String equipmenrtNumber;
	
	/** 回复指令 */
	private String reply;
	
	/** 解析值 */
	private String valuess;
	
	/** 接收时间 */
	private String creatTime;
	
	public String getEquipmenrtNumber() {
		return equipmenrtNumber;
	}

	public void setEquipmenrtNumber(String equipmenrtNumber) {
		this.equipmenrtNumber = equipmenrtNumber;
	}

	public String getValuess() {
		return valuess;
	}

	public void setValuess(String valuess) {
		this.valuess = valuess;
	}

	@Column(name="creat_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,updatable = false)
    @Generated(GenerationTime.INSERT)
	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

}
