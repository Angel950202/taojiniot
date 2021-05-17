package com.taojin.iot.agreement.fujiya.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.taojin.iot.agreement.fujiya.enums.AgreementFujiyaEnum;
import com.taojin.iot.base.comm.entity.BaseEntity;


/**
 * 记录详情
 */
@Entity
@Table(name = "iot_address_detail")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_address_detail_sequence")
public class AddressDetail extends BaseEntity{

	private static final long serialVersionUID = -9137348902764358298L;
	
	/**信号地址*/
	private String address;
	/**信号类型*/
	private String  value;
	/**指令类型*/
	private AgreementFujiyaEnum agreementFujiya;
	/**接收的命令*/
	private String commandStr;
	/**
	 * 时间
	 */
	private String dateTime;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public AgreementFujiyaEnum getAgreementFujiya() {
		return agreementFujiya;
	}
	public void setAgreementFujiya(AgreementFujiyaEnum agreementFujiya) {
		this.agreementFujiya = agreementFujiya;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getCommandStr() {
		return commandStr;
	}
	public void setCommandStr(String commandStr) {
		this.commandStr = commandStr;
	}
}
