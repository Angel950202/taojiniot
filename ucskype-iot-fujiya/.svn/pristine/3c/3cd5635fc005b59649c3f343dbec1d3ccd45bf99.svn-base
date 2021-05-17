package com.taojin.iot.agreement.fujiya.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.taojin.iot.agreement.fujiya.enums.AddressTypeEnum;
import com.taojin.iot.agreement.fujiya.enums.AgreementFujiyaEnum;
import com.taojin.iot.base.comm.entity.BaseEntity;


/**
 * 2020.7.7  只取我需要的信号位做redis处理
 * Entity-信号位
 * @author bjt
 * RD77-2 取C801 S2全部工站信号位
 * RD77-1 取C801 S2 NOK信号位,S3 OK信号位 ,生产总数S1+S2
 * RC70-2 取C801 S2全部工站信号位
 * RC70-1 取C801 S2全部工站信号位
 */
@Entity
@Table(name = "iot_address_dtu")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_address_dtu_sequence")
public class AddressDTU extends BaseEntity{

	private static final long serialVersionUID = -9137348902764358298L;
	
	/**信号地址*/
	private String address;
	/**信号类型*/
	private AddressTypeEnum  addressType;
	/**指令类型*/
	private AgreementFujiyaEnum agreementFujiya;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public AddressTypeEnum getAddressType() {
		return addressType;
	}
	public void setAddressType(AddressTypeEnum addressType) {
		this.addressType = addressType;
	}
	public AgreementFujiyaEnum getAgreementFujiya() {
		return agreementFujiya;
	}
	public void setAgreementFujiya(AgreementFujiyaEnum agreementFujiya) {
		this.agreementFujiya = agreementFujiya;
	}
}
