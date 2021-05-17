package com.taojin.iot.service.user.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.taojin.iot.base.comm.entity.BaseEntity;

/**
 * Entity-用户 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午2:05:28 author 王杰
 * ============================================================================
 */
@Entity
@Table(name = "iot_user")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_user_sequence")
public class User extends BaseEntity {

	private static final long serialVersionUID = 821328219290361737L;
	/** 账号 */
	private String username;
	/** 密码 */
	private String password;
	/** key */
	private String apiKey;
	/** 用户id */
	private String idNumber;
	/** 余额 */
	private BigDecimal fee;
	/** 姓名 */
	private String name;
	/** 头像 */
	private String headPic;
	/** 性别 */ 
	private String sex;
	/** 生日 */
	private String birthday;
	/** 年龄 */
	private String age;
	/** 电话号码 */	
	private String phone;
	/** 邮箱 */
	private String email;
	/** 地址 */
	private String address;
	/** 企业名称 */
	private String companyName;
	/** 所属部门 */
	private String department;
	/** 职位*/
	private String position;
	/** 角色 */
	private String roleName;
	/** 角色id */
	private Long roleId;
	/** 用户类型 0：个人用户 1：企业用户 */
	private Integer userType;
	/**短信条数*/
	private Integer smsCount;

	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[0-9a-z_A-Z\\u4e00-\\u9fa5]+$")
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取密码
	 * 
	 * @return 密码
	 */
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[^\\s&\"<>]+$")
	@Column(nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	/**
	 * 获取姓名
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置姓名
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取性别 0：男，1：女
	 * 
	 * @return
	 */
	//@Column(nullable = false)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 获取生日 yyyy-MM-dd
	 * 
	 * @return
	 */
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * 获取手机号
	 * 
	 * @return
	 */
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * 获取头像
	 * 
	 * @return
	 */
	public String getHeadPic() {
		return headPic;
	}

	/**
	 * 设置头像
	 * 
	 * @param headPic
	 */
	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	/**
	 * 获取用户类型
	 * 
	 * @return
	 */
	public Integer getUserType() {
		return userType;
	}
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	/**
	 * 设置用户类型
	 * 
	 * @param userType
	 */
	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	/**
	 * 获取用户ID
	 * @return
	 */
	public String getIdNumber() {
		return idNumber;
	}

	/**
	 * 设置用户ID 时间戳
	 * @param idNumber
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Integer getSmsCount() {
		return smsCount;
	}

	public void setSmsCount(Integer smsCount) {
		this.smsCount = smsCount;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
}
