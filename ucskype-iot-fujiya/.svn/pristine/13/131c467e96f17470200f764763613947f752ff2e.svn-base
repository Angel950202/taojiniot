package com.taojin.iot.service.user.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.taojin.iot.base.comm.entity.BaseEntity;

@Entity
@Table(name = "iot_user_role")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_user_role_sequence")
public class UserRole extends BaseEntity {
	/**
	 * 角色名
	 */
	private String roleName;
	
	/**
	 * 角色编号
	 */
	private String roleNumber;
	
	/**
	 * 权限列表
	 */
	private String roleList;
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleNumber() {
		return roleNumber;
	}
	public void setRoleNumber(String roleNumber) {
		this.roleNumber = roleNumber;
	}
	public String getRoleList() {
		return roleList;
	}
	public void setRoleList(String roleList) {
		this.roleList = roleList;
	}
	

}
