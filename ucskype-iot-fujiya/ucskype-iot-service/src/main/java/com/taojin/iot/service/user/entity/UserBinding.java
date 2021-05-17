package com.taojin.iot.service.user.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.taojin.iot.base.comm.entity.BaseEntity;

/**
 * Entity-第三方用户绑定 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午2:34:55 author 王杰
 * ============================================================================
 */
@Entity
@Table(name = "iot_user_binding")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_user_binding_sequence")
public class UserBinding extends BaseEntity {

	private static final long serialVersionUID = -8069737496399838989L;

	/** 绑定用户 */
	private User user;
	/** 第三方 */
	private String username;
	/** 姓名 */
	private String name;
	/** 头像 */
	private String headPic;

	/**
	 * 获取用户
	 * @return
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	/**
	 * 设置用户
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 获取绑定账号
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置绑定账号
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取绑定昵称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置绑定昵称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取绑定头像
	 * @return
	 */
	public String getHeadPic() {
		return headPic;
	}

	/**
	 * 设置绑定头像
	 * @param headPic
	 */
	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

}
