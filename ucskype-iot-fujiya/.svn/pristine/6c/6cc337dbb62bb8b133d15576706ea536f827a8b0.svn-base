package com.taojin.iot.agreement.fujiya.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.taojin.iot.agreement.fujiya.enums.AgreementFujiyaEnum;
import com.taojin.iot.base.comm.entity.BaseEntity;

/**
 * 指令-rc701-指令对应值
 * @author wangjie
 *
 */
@Entity
@Table(name = "iot_agreement_rc701_value")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_agreement_rc701_value_sequence")
public class AgreementRc701Value extends BaseEntity{

	private static final long serialVersionUID = 8633747499593742321L;

	/**设备ID*/
	private String iccid;
	/**信号位=工位号-工站号-地址*/
	private String address;
	/**采集值*/
	private Integer commandValue;
	/**增量值*/
	private String historyValue;
	/**接收的命令*/
	private String commandStr;
	/**设备类型*/
	private AgreementFujiyaEnum equipType;
	/**日期 对应yyyy-MM-dd*/
	private String dateTime;
	/**工位名称*/
	private String addressName;
	
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getCommandValue() {
		return commandValue;
	}
	public void setCommandValue(Integer commandValue) {
		this.commandValue = commandValue;
	}
	public AgreementFujiyaEnum getEquipType() {
		return equipType;
	}
	public void setEquipType(AgreementFujiyaEnum equipType) {
		this.equipType = equipType;
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
	public String getHistoryValue() {
		return historyValue;
	}
	public void setHistoryValue(String historyValue) {
		this.historyValue = historyValue;
	}
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
}
