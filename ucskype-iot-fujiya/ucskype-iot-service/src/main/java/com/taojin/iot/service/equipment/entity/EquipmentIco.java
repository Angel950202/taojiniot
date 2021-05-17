package com.taojin.iot.service.equipment.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.taojin.iot.base.comm.entity.BaseEntity;

/**
 * Entity-设备ico图标 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午6:51:56 author 王杰
 * ============================================================================
 */
@Entity
@Table(name = "iot_equipment_ico")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_equipment_ico_sequence")
public class EquipmentIco extends BaseEntity {

	private static final long serialVersionUID = -5345802407497210379L;

	// 图标类型
	public enum IcoType {
		device, // 设备
		sensor// 传感器
	}

	/** 图标名称 */
	private String name;
	/** 图片地址 */
	private String icoPath;
	/**在线显示图标*/
	private String onlineIco;
	
	/** 类型 */
	private IcoType icoType;
	/**是否为系统*/
	private Integer issystem;
	
	/**传感器*/
	private Set<EquipmentSensor> equipmentSensorset = new HashSet<EquipmentSensor>();
	/**设备*/
	private Set<Equipment> equipmentSet = new HashSet<Equipment>();
	
	/**
	 * 获取图标名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置图标名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取图标地址
	 * 
	 * @return
	 */
	public String getIcoPath() {
		return icoPath;
	}

	/**
	 * 设置图标地址
	 * 
	 * @param icoPath
	 */
	public void setIcoPath(String icoPath) {
		this.icoPath = icoPath;
	}

	/**
	 * 获取图标类型
	 * 
	 * @return
	 */
	public IcoType getIcoType() {
		return icoType;
	}

	/**
	 * 设置图标类型
	 * 
	 * @param icoType
	 */
	public void setIcoType(IcoType icoType) {
		this.icoType = icoType;
	}
	
	@OneToMany(mappedBy = "equipmentIco", fetch = FetchType.LAZY)
	public Set<EquipmentSensor> getEquipmentSensorset() {
		return equipmentSensorset;
	}

	public void setEquipmentSensorset(Set<EquipmentSensor> equipmentSensorset) {
		this.equipmentSensorset = equipmentSensorset;
	}

	@OneToMany(mappedBy = "equipmentIco", fetch = FetchType.LAZY)
	public Set<Equipment> getEquipmentSet() {
		return equipmentSet;
	}

	public void setEquipmentSet(Set<Equipment> equipmentSet) {
		this.equipmentSet = equipmentSet;
	}
	
	public String getOnlineIco() {
		return onlineIco;
	}

	public void setOnlineIco(String onlineIco) {
		this.onlineIco = onlineIco;
	}

	@Column(nullable = false)
	public Integer getIssystem() {
		return issystem;
	}

	public void setIssystem(Integer issystem) {
		this.issystem = issystem;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<EquipmentSensor> equipmentSensors = getEquipmentSensorset();
		if (equipmentSensors != null) {
			for (EquipmentSensor sensor : equipmentSensors) {
				sensor.setEquipmentIco(null);
			}
		}
		Set<Equipment> equipments = getEquipmentSet();
		if (equipments != null) {
			for (Equipment equipment : equipments) {
				equipment.setEquipmentIco(null);
			}
		}
	}

}
