package com.taojin.iot.service.equipment.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.taojin.iot.base.comm.entity.BaseEntity;
import com.taojin.iot.service.equipment.enums.EquipmentAlarmTypeEnum;

/**
 * Entity-设备触发器 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午10:56:14 author 王杰
 * ============================================================================
 */
@Entity
@Table(name = "iot_equipment_trigger")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_equipment_trigger_sequence")
public class EquipmentTrigger extends BaseEntity {

	private static final long serialVersionUID = 6065046836684185123L;

	// 报警方式
	public enum Target {
		WeiXin, Email, SMS
	}

	/** 设备 */
	private Equipment equipment;
	/** 传感器 */
	private EquipmentSensor equipmentSensor;
	/** 设备触发器条件 */
	private EquipmentAlarmTypeEnum equipmentAlarmTypeEnum;
	/** 触发器值 json */
	private String upperBoundC;
	/** 报警方式 */
	private Target target;
	/** 报警值 */
	private String targetValue;
	/** 是否转发 0：否，1：是 */
	private Integer isTransfer;
	/** 触发器状态1：已开启，0：未开启 */
	private Integer state;
	/**
	 * 报警类型
	 */
	private Integer type;
	
	
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	private String userName;
	/** 触发器值 json *//*
	private String upperBoundC;*/
	private Set<EquipmentTriggerLog> equipmentTriggerLogSet = new HashSet<EquipmentTriggerLog>();

	/**
	 * 获取设备
	 * 
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipment_id")
	public Equipment getEquipment() {
		return equipment;
	}

	/**
	 * 设置设备
	 * 
	 * @param equipment
	 */
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	/**
	 * 获取传感器
	 * 
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipmentSensor_id")
	public EquipmentSensor getEquipmentSensor() {
		return equipmentSensor;
	}

	/**
	 * 设置传感器
	 * 
	 * @param equipmentSensor
	 */
	public void setEquipmentSensor(EquipmentSensor equipmentSensor) {
		this.equipmentSensor = equipmentSensor;
	}

	/**
	 * 获取触发条件
	 * 
	 * @return
	 */
	@Column(nullable = false)
	public EquipmentAlarmTypeEnum getEquipmentAlarmTypeEnum() {
		return equipmentAlarmTypeEnum;
	}

	/**
	 * 设置触发条件
	 * 
	 * @param equipmentAlarmTypeEnum
	 */
	public void setEquipmentAlarmTypeEnum(
			EquipmentAlarmTypeEnum equipmentAlarmTypeEnum) {
		this.equipmentAlarmTypeEnum = equipmentAlarmTypeEnum;
	}

	/**
	 * 获取触发值
	 * 
	 * @return
	 */
	public String getUpperBoundC() {
		return upperBoundC;
	}

	/**
	 * 设置触发值
	 * 
	 * @param upperBoundC
	 */
	public void setUpperBoundC(String upperBoundC) {
		this.upperBoundC = upperBoundC;
	}

	/**
	 * 获取报警方式
	 * 
	 * @return
	 */
	@Column(nullable = false)
	public Target getTarget() {
		return target;
	}

	/**
	 * 设置报警方式
	 * 
	 * @param target
	 */
	public void setTarget(Target target) {
		this.target = target;
	}

	/**
	 * 获取报警值
	 * 
	 * @return
	 */
	public String getTargetValue() {
		return targetValue;
	}

	/**
	 * 设置报警值
	 * 
	 * @param targetValue
	 */
	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}

	/**
	 * 获取是否转发
	 * 
	 * @return
	 */
	@Column(nullable = false)
	public Integer getIsTransfer() {
		return isTransfer;
	}

	/**
	 * 设置是否转发
	 * 
	 * @param isTransfer
	 *            0：否，1：是
	 */
	public void setIsTransfer(Integer isTransfer) {
		this.isTransfer = isTransfer;
	}

	/**
	 * 获取触发器状态
	 * 
	 * @return
	 */
	@Column(nullable = false)
	public Integer getState() {
		return state;
	}

	/**
	 * 设置触发器状态
	 * 
	 * @param state
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	@OneToMany(mappedBy = "equipmentTrigger", fetch = FetchType.LAZY)
	public Set<EquipmentTriggerLog> getEquipmentTriggerLogSet() {
		return equipmentTriggerLogSet;
	}

	public void setEquipmentTriggerLogSet(
			Set<EquipmentTriggerLog> equipmentTriggerLogSet) {
		this.equipmentTriggerLogSet = equipmentTriggerLogSet;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<EquipmentTriggerLog> equipmentTriggerLogs = getEquipmentTriggerLogSet();
		if (equipmentTriggerLogs != null) {
			for (EquipmentTriggerLog triggerLog : equipmentTriggerLogs) {
				triggerLog.setEquipmentTrigger(null);
			}
		}
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
