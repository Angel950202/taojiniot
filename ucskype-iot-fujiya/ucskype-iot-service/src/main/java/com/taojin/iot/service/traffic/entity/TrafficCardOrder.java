package com.taojin.iot.service.traffic.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.taojin.iot.base.comm.entity.BaseEntity;

/**
 * Entity-购卡订单 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 上午11:50:38 author 王杰
 * ============================================================================
 */
@Entity
@Table(name = "iot_traffic_card_order")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_traffic_card_order_sequence")
public class TrafficCardOrder extends BaseEntity {

	private static final long serialVersionUID = 7416930458830366550L;

	/**
	 * 订单状态
	 */
	public enum OrderStatus {

		/** 未确认 */
		unconfirmed,

		/** 已确认 */
		confirmed,

		/** 已完成 */
		completed,

		/** 已取消 */
		cancelled,
		
		/** 待发货*/
		delivery
	}

	/**
	 * 支付状态
	 */
	public enum PaymentStatus {

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

	public enum FreightType {
		/** 在线支付 */
		CashonLine,
		/** 货到付款 */
		Cashondelivery
	}

	/** 订单编号 */
	private String sn;

	/** 订单状态 */
	private OrderStatus orderStatus;

	/** 支付状态 */
	private PaymentStatus paymentStatus;

	/** 公司名称 */
	private String companyName;

	/** 联系人 */
	private String contactName;

	/** 收货人 */
	private String consignee;

	/** 地区名称 */
	private String areaName;

	/** 地址 */
	private String address;

	/** 邮编 */
	private String zipCode;

	/** 电话 */
	private String phone;

	/** 订单金额 */
	private BigDecimal fee;

	/** 购买年限 */
	private Integer yearsNumber;

	/** 购买数量 */
	private Integer buycardnumber;

	/** 运费支付类型 */
	private FreightType freightType;

	/** 运费 */
	private BigDecimal freight;
	
	/**
	 * 获取订单编号
	 * 
	 * @return 订单编号
	 */
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getSn() {
		return sn;
	}

	/**
	 * 设置订单编号
	 * @param sn
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 获取订单状态
	 * 
	 * @return 订单状态
	 */
	@Column(nullable = false)
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	/**
	 * 设置订单状态
	 * @param orderStatus
	 */
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * 获取支付状态
	 * 
	 * @return 支付状态
	 */
	@Column(nullable = false)
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * 设置支付状态
	 * @param paymentStatus
	 */
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * 获取公司名称
	 * @return
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * 设置公司名称
	 * @param companyName
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * 获取联系人姓名
	 * @return
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * 设置联系人姓名
	 * @param contactName
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * 获取收货人
	 * 
	 * @return 收货人
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getConsignee() {
		return consignee;
	}

	/**
	 * 设置收货人
	 * @param consignee
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	/**
	 * 获取地区名称
	 * @return
	 */
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * 获取收货地址
	 * @return
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取邮编
	 * 
	 * @return 邮编
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * 获取电话
	 * 
	 * @return 电话
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	/**
	 * 获取购买年限
	 * @return
	 */
	@Column(nullable = false)
	public Integer getYearsNumber() {
		return yearsNumber;
	}

	/**
	 * 设置购买年限
	 * @param yearsNumber
	 */
	public void setYearsNumber(Integer yearsNumber) {
		this.yearsNumber = yearsNumber;
	}

	/**
	 * 获取购买数量
	 * @return
	 */
	@Column(nullable = false)
	public Integer getBuycardnumber() {
		return buycardnumber;
	}

	public void setBuycardnumber(Integer buycardnumber) {
		this.buycardnumber = buycardnumber;
	}

	/**
	 * 获取运费支付类型
	 * @return
	 */
	@Column(nullable = false)
	public FreightType getFreightType() {
		return freightType;
	}

	public void setFreightType(FreightType freightType) {
		this.freightType = freightType;
	}

	/**
	 * 获取运费
	 * @return
	 */
	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
}
