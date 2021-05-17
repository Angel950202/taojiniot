package com.taojin.iot.service.traffic.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.taojin.iot.base.comm.entity.BaseEntity;
import com.taojin.iot.service.equipment.entity.Equipment;

/**
 * Entity-流量卡 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 上午11:29:38 author 王杰
 * ============================================================================
 */
@Entity
@Table(name = "iot_traffic_card")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_traffic_card_sequence")
public class TrafficCard extends BaseEntity {

	private static final long serialVersionUID = 3584330722021730060L;

	/** 卡号 */
	private String cardnumber;
	/** ICCID */
	private String iccid;
	/** 总流量 */
	private BigDecimal totalTraffic;
	/** 已使用流量 */
	private BigDecimal usedTraffic;
	/** 剩余流量 */
	private BigDecimal surplusTraffic;
	/** 流量报警线 */
	private BigDecimal warningLine;
	/** 开卡日期 */
	private String startTime;
	/** 截止日期 */
	private String endTime;
	/** 卡状态 0：未开启，1：已开启 */
	private Integer cardState;

	/** 绑定设备 */
	private Equipment equipment;

	/**
	 * 获取卡号
	 * 
	 * @return
	 */
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[0-9a-z_A-Z\\u4e00-\\u9fa5]+$")
	@Length(min = 2, max = 20)
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getCardnumber() {
		return cardnumber;
	}

	/**
	 * 设置卡号
	 * 
	 * @param cardnumber
	 */
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	/**
	 * 获取ICCID
	 * 
	 * @return
	 */
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[0-9a-z_A-Z\\u4e00-\\u9fa5]+$")
	@Length(min = 2, max = 20)
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getIccid() {
		return iccid;
	}

	/**
	 * 设置ICCID
	 * 
	 * @param iccid
	 */
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	/**
	 * 获取总流量 M
	 * 
	 * @return
	 */
	@Column(nullable = false, precision = 21, scale = 2)
	public BigDecimal getTotalTraffic() {
		return totalTraffic;
	}

	/**
	 * 设置总流量 M
	 * 
	 * @param totalTraffic
	 */
	public void setTotalTraffic(BigDecimal totalTraffic) {
		this.totalTraffic = totalTraffic;
	}

	/**
	 * 获取已使用流量
	 * 
	 * @return
	 */
	@Column(nullable = false, precision = 21, scale = 2)
	public BigDecimal getUsedTraffic() {
		return usedTraffic;
	}

	/**
	 * 设置已使用流量
	 * 
	 * @param usedTraffic
	 */
	public void setUsedTraffic(BigDecimal usedTraffic) {
		this.usedTraffic = usedTraffic;
	}

	/**
	 * 获取剩余流量
	 * 
	 * @return
	 */
	@Column(nullable = false, precision = 21, scale = 2)
	public BigDecimal getSurplusTraffic() {
		return surplusTraffic;
	}

	/**
	 * 设置剩余流量
	 * 
	 * @param surplusTraffic
	 */
	public void setSurplusTraffic(BigDecimal surplusTraffic) {
		this.surplusTraffic = surplusTraffic;
	}

	/**
	 * 获取报警线
	 * 
	 * @return
	 */
	@Column(nullable = false, precision = 21, scale = 2)
	public BigDecimal getWarningLine() {
		return warningLine;
	}

	/**
	 * 获取报警线
	 * 
	 * @param warningLine
	 */
	public void setWarningLine(BigDecimal warningLine) {
		this.warningLine = warningLine;
	}

	/**
	 * 获取开卡时间 yyyy-MM-dd
	 * 
	 * @return
	 */
	@Column(nullable = false)
	public String getStartTime() {
		return startTime;
	}

	/**
	 * 设置开卡时间 yyyy-MM-dd
	 * 
	 * @param startTime
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * 获取卡有效期 yyyy-MM-dd
	 */
	@Column(nullable = false)
	public String getEndTime() {
		return endTime;
	}

	/**
	 * 设置卡有效期 yyyy-MM-dd
	 * 
	 * @param endTime
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * 获取卡状态 0：未开启，1：已开启
	 * 
	 * @return
	 */
	@Column(nullable = false)
	public Integer getCardState() {
		return cardState;
	}

	/**
	 * 设置卡状态 0：未开启，1：已开启
	 * 
	 * @param cardState
	 */
	public void setCardState(Integer cardState) {
		this.cardState = cardState;
	}

	/**
	 * 获取绑定设置
	 * 
	 * @return
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipment_id")
	public Equipment getEquipment() {
		return equipment;
	}

	/**
	 * 设置绑定设备
	 * 
	 * @param equipment
	 */
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

}
