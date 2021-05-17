package com.taojin.iot.service.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.taojin.iot.base.comm.entity.BaseEntity;

/**
 * 设备档案
 * @author 李鹤阳
 *
 */
@Entity
@Table(name = "iot_senior_management")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_senior_management_sequence")
public class SeniorManagement extends BaseEntity  implements java.io.Serializable {
	private static final long serialVersionUID = -1495787682343110812L;
	
	/** 单位 */
	private String company; 	
	/** 所属账号 */
	private String  Belong; 
	/** 设备名 */
	private String username;
	/** 设备号 */
	private String number;
	/** 启动时间 */
	private Date startdate; 
	/** 连接状态  未激活，已激活*/
	private String status;	
	/** 剩余流量 */
	private int netflow;
	/**待充值的流量*/
	private int payNetFlow;
	
	/** 远程状态0开1关 */
	private boolean remote; 
	
	public SeniorManagement() {
		super();
	}
	public SeniorManagement(String company, String belong, String username,
			String number, Date startdate, String status,int netflow, int payNetFlow, boolean remote) {
		super();
		this.company = company;
		Belong = belong;
		this.username = username;
		this.number = number;
		this.startdate = startdate;
		this.status = status;
		this.netflow = netflow;
		this.payNetFlow = payNetFlow;
		this.remote = remote;
	}
	/**
	 * 单位 
	 * @return
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * 单位 
	 * @return
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * 所属账号
	 * @return
	 */
	public String getBelong() {
		return Belong;
	}
	/**
	 * 所属账号
	 * @return
	 */
	public void setBelong(String belong) {
		Belong = belong;
	}
	/**
	 * 设备名 
	 * @return
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设备名
	 * @return
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 设备号 
	 * @return
	 */
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[0-9a-z_A-Z\\u4e00-\\u9fa5]+$")
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getNumber() {
		return number;
	}
	/**
	 * 设备号
	 * @return
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * 启动时间
	 * @return
	 */
	public Date getStartdate() {
		return startdate;
	}
	/**
	 * 启动时间 
	 * @return
	 */
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	/**
	 * 连接状态 
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 连接状态
	 * @return
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 流量 
	 * @return
	 */
	public int getNetflow() {
		return netflow;
	}
	/**
	 * 流量 
	 * @param netflow 
	 * @return
	 */
	public void setNetflow(int netflow) {
		this.netflow = netflow;
	}
	/**
	 * 远程状态
	 * @return
	 */
	public boolean getRemote() {
		return remote;
	}
	/**
	 * 远程状态
	 * @return
	 */
	public void setRemote(boolean remote) {
		this.remote = remote;
	}
	
	/**
	 * 获取待充值的流量
	 * @return
	 */
	public int getPayNetFlow() {
		return payNetFlow;
	}
	/**
	 * 设置待充值的流量-页面设置充值流量，设备充值成功后则清0
	 * @param payNetFlow
	 */
	public void setPayNetFlow(int payNetFlow) {
		this.payNetFlow = payNetFlow;
	}
}
