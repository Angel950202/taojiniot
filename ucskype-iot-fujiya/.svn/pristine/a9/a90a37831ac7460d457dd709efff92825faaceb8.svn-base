package com.taojin.iot.service.user.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.taojin.iot.base.comm.entity.BaseEntity;

@Entity
@Table(name = "iot_company_profile")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_company_profile_sequence")
public class CompanyProfile extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = -1495787682343110812L;
	
	/** 单位 */
	private String company;	
	/** 姓名 */
	private String  name;
	/** 电话*/
	private String phone;
	/** 设备数量 */	
	private int count; 

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	
}