package com.taojin.iot.service.user.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.taojin.iot.base.comm.entity.BaseEntity;

/**
 * Entity-用户订单 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 上午9:59:11 author 王杰
 * ============================================================================
 */
@Entity
@Table(name = "iot_user_order")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_user_order_sequence")
public class UserOrder extends BaseEntity {

	private static final long serialVersionUID = -6438719339373622367L;

	public enum PayType {
		user, sms
	}

	/**
	 * 支付状态
	 */
	public enum PayStatus {

		/** 未支付 */
		unpaid,

		/** 部分支付 */
		partialPayment,

		/** 已支付 */
		paid,

		/** 部分退款 */
		partialRefunds,

		/** 已退款 */
		refunded
	}

	/** 订单编号 */
	private String sn;
	/** 购买类型 */
	private PayType payType;
	/** 购买数量 */
	private Integer payNum;
	/** 支付金额 */
	private BigDecimal money;
	/** 支付时间 */
	private String payTime;
	/** 支付状态 */
	private PayStatus payStatus;

	/**
	 * 获取订单
	 * @return
	 */
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 支付方式
	 * @return
	 */
	@Column(nullable = false)
	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	@Column(nullable = false)
	public Integer getPayNum() {
		return payNum;
	}

	public void setPayNum(Integer payNum) {
		this.payNum = payNum;
	}

	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	/**
	 * 获取支付时间
	 * @return
	 */
	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	/**
	 * 获取支付状态
	 * @return
	 */
	@Column(nullable = false)
	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}

}
