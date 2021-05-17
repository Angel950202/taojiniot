package com.taojin.iot.base.comm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.base.comm.utils.UUIDTools;

/**
 * Entity-用户会话 内存表
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午2:19:37 author 王杰
 * ============================================================================
 */
@Entity
@Table(name = "iot_user_session")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_user_session_sequence")
public class UserSession extends BaseEntity {

	private static final long serialVersionUID = -3452451360327072616L;

	private static final int timeout = 15;// 超时时间15分钟

	/** 用户名 */
	private String username;
	/** 用户ID */
	private String userId;
	/** 会话ID */
	private String sessionId;
	/** 登录终端 */
	private String device;
	/** 系统版本 */
	private String systemVersion;
	/** 绑定时间 */
	private String bindTime;

	public UserSession() {
		this.sessionId = UUIDTools.getUUID();
		this.bindTime = DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取用户名
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取用户ID
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取会话ID
	 * @return
	 */
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[0-9a-z_A-Z\\u4e00-\\u9fa5]+$")
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * 获取设备
	 * @return
	 */
	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	/**
	 * 获取系统版本
	 * @return
	 */
	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	/**
	 * 获取绑定时间
	 * @return
	 */
	public String getBindTime() {
		return bindTime;
	}

	public void setBindTime(String bindTime) {
		this.bindTime = bindTime;
	}

	public static int getTimeout() {
		return timeout;
	}

}
