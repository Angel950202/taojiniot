package com.taojin.iot.service.equipment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.taojin.iot.base.comm.entity.BaseEntity;
import com.taojin.iot.service.equipment.entity.EquipmentTrigger.Target;

/**
 * Entity-设备触发器记录 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午11:49:19 author 王杰
 * ============================================================================
 */
@Entity
@Table(name = "iot_equipment_trigger_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_equipment_trigger_log_sequence")
public class EquipmentTriggerLog extends BaseEntity {

	private static final long serialVersionUID = -7861152338056987791L;
	/**
	 * 生产型号
	 */
	private String productionModel;
	/**
	 * 生产线
	 */
	private String productionLine;
	/**
	 * 工站
	 */
	private String workStationName;
	/**
	 * 报警类型
	 */
	private String type;
	/** 触发报警值 */
	private String triggerValue;
	/** 触发报警方式 */
	private Target target;
	/** 发送状态 0:发送失败，1：发送成功 */
	private Integer sendState;
	/** 失败原因 */
	private String faileReason;
	/** 报警内容 */
	private String triggerContent;

	/** 所属触发器 */
	private EquipmentTrigger equipmentTrigger;

	/**
	 * 获取触发报警值
	 * 
	 * @return
	 */
	public String getTriggerValue() {
		return triggerValue;
	}

	/**
	 * 设置触发报警值
	 * 
	 * @param triggerValue
	 */
	public void setTriggerValue(String triggerValue) {
		this.triggerValue = triggerValue;
	}

	/**
	 * 获取触发类型
	 * 
	 * @return
	 */
	public Target getTarget() {
		return target;
	}

	/**
	 * 设置触发类型
	 * 
	 * @param target
	 */
	public void setTarget(Target target) {
		this.target = target;
	}

	/**
	 * 获取发送状态
	 * 
	 * @return
	 */
	@Column(nullable = false)
	public Integer getSendState() {
		return sendState;
	}

	/**
	 * 设置发送状态 默认0
	 * 
	 * @param sendState
	 */
	public void setSendState(Integer sendState) {
		this.sendState = sendState;
	}

	/**
	 * 获取失败原因
	 * 
	 * @return
	 */
	public String getFaileReason() {
		return faileReason;
	}

	/**
	 * 设置失败原因
	 * 
	 * @param faileReason
	 */
	public void setFaileReason(String faileReason) {
		this.faileReason = faileReason;
	}

	/**
	 * 获取报警内容
	 * 
	 * @return
	 */
	public String getTriggerContent() {
		return triggerContent;
	}

	/**
	 * 设置报警内容
	 * 
	 * @param triggerContent
	 */
	public void setTriggerContent(String triggerContent) {
		this.triggerContent = triggerContent;
	}

	/**
	 * 获取所属触发器
	 * 
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipmentTrigger_id")
	public EquipmentTrigger getEquipmentTrigger() {
		return equipmentTrigger;
	}

	/**
	 * 设置所属触发器
	 * 
	 * @param equipmentTrigger
	 */
	public void setEquipmentTrigger(EquipmentTrigger equipmentTrigger) {
		this.equipmentTrigger = equipmentTrigger;
	}

	public String getProductionModel() {
		return productionModel;
	}

	public void setProductionModel(String productionModel) {
		this.productionModel = productionModel;
	}

	public String getProductionLine() {
		return productionLine;
	}

	public void setProductionLine(String productionLine) {
		this.productionLine = productionLine;
	}

	public String getWorkStationName() {
		return workStationName;
	}

	public void setWorkStationName(String workStationName) {
		this.workStationName = workStationName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
