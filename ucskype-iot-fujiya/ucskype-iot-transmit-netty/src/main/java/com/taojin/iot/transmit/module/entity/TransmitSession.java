package com.taojin.iot.transmit.module.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.taojin.iot.base.comm.entity.BaseEntity;

/**
 * 会话
 * @author wangjie
 *
 */
@Entity
@Table(name = "iot_transmit_session")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_transmit_session_sequence")
public class TransmitSession extends BaseEntity{
	private static final long serialVersionUID = 8249371247226096482L;
	
	/**会话ID*/
	private String sessionId;
	/**设备ID*/
	private String ccid;
	/**心跳时间 （秒）*/
	private Integer heartbeatTime;
	/**最后一次上报数据时间 */
	private String lastTime;
	
	public TransmitSession() {
		super();
	}
	public TransmitSession(String sessionId, String ccid,Integer heartbeat) {
		super();
		this.sessionId = sessionId;
		this.ccid = ccid;
		this.heartbeatTime = heartbeat;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[0-9a-z_A-Z\\u4e00-\\u9fa5]+$")
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getCcid() {
		return ccid;
	}
	public void setCcid(String ccid) {
		this.ccid = ccid;
	}
	public Integer getHeartbeatTime() {
		return heartbeatTime;
	}
	public void setHeartbeatTime(Integer heartbeatTime) {
		this.heartbeatTime = heartbeatTime;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
}
