package com.taojin.iot.service.management.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.taojin.iot.base.comm.entity.BaseEntity;

@Entity
@Table(name = "iot_Equipment_Update")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_Equipment_Update_sequence")
public class EquipmentUpdate extends BaseEntity implements java.io.Serializable{
	
	private static final long serialVersionUID = 821328219290361737L;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getFileAddress() {
		return fileAddress;
	}

	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}

	private String name;
	
	private String idNumber;
	
	private String version;
	
	private String fileAddress;
}
