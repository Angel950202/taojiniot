package com.taojin.iot.service.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.taojin.iot.base.comm.entity.BaseEntity;

/**
 * Entity-用户联系人
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午2:31:33
 * author 王杰
 * ============================================================================
 */
@Entity
@Table(name = "iot_user_contact")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_user_contact_sequence")
public class UserContact extends BaseEntity{

	private static final long serialVersionUID = 5532189999459955578L;
	
	/**联系人姓名*/
	private String name;
	/**手机号码*/
	private String phone;
	/**邮箱*/
	private String email;
	/**微信号*/
	private String weixin;
	
	/**
	 * 获取姓名
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置姓名
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取手机
	 * @return
	 */
	@Column(nullable = false, length = 100)
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置手机
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取邮箱
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置邮箱
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取微信号
	 * @return
	 */
	public String getWeixin() {
		return weixin;
	}
	/**
	 * 设置微信号
	 * @param weixin
	 */
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
}
