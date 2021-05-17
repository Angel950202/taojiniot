package com.taojin.iot.service.equipment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.taojin.iot.base.comm.entity.BaseEntity;
import com.taojin.iot.base.comm.utils.UUIDTools;

/**
 * Entity-传感器 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午6:55:16 author 王杰
 * ============================================================================
 */
@Entity
@Table(name = "iot_equipment_sensor")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_equipment_sensor_sequence")
public class EquipmentSensor extends BaseEntity {

	private static final long serialVersionUID = -8250174891650579683L;

	/** 传感器ID */
	private String idNumber;
	/** 传感器名称 */
	private String name;
	/** 类型 1：数值型，2：开关型（可操作）,3:定位型,4:开关型（不可操作） */
	private Integer type;
	/** 小数位数 0：0位小数,1:1位小数... */
	private Integer sign;
	/** 单位 */
	private String unit;

	/** 传感器图标 */
	private EquipmentIco equipmentIco;
	/** 所属设备 */
	private Equipment equipment;
	/** 自定义字段 */
	private EquipmentSensorParams equipmentSensorParams;
	
	/**通道：0:平台,1:国动,2:陶金*/
	private Integer channel; 
	/**传感器序号-界面上需要开放配置项*/
	private String serialNumber;

	public EquipmentSensor() {
		this.idNumber = UUIDTools.getUUID();
		this.serialNumber = String.valueOf(System.currentTimeMillis());
	}

	/**
	 * 获取传感器ID
	 * 
	 * @return
	 */
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[0-9a-z_A-Z\\u4e00-\\u9fa5]+$")
	@Length(min = 2, max = 20)
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getIdNumber() {
		return idNumber;
	}

	/**
	 * 设置传感器ID
	 * 
	 * @param idNumber
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * 获取名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取类型 设备、传感器
	 * 
	 * @return
	 */
	@Column(nullable = false)
	public Integer getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 获取小数位数
	 * 
	 * @return
	 */
	@Column(nullable = false)
	public Integer getSign() {
		return sign;
	}

	/**
	 * 设置小数位数
	 * 
	 * @param sign
	 */
	public void setSign(Integer sign) {
		this.sign = sign;
	}

	/**
	 * 获取单位
	 * 
	 * @return
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * 设置单位
	 * 
	 * @param unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 获取传感器图标
	 * 
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipmentIco_id")
	public EquipmentIco getEquipmentIco() {
		return equipmentIco;
	}

	/**
	 * 设置传感器
	 * 
	 * @param equipmentIco
	 */
	public void setEquipmentIco(EquipmentIco equipmentIco) {
		this.equipmentIco = equipmentIco;
	}

	/**
	 * 所属设备
	 * 
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipment_id")
	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	/**
	 * 获取自这下义参数字段
	 * 
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipmentSensorParams_id")
	public EquipmentSensorParams getEquipmentSensorParams() {
		return equipmentSensorParams;
	}

	/**
	 * 设置自定义参数字段
	 * 
	 * @param equipmentSensorParams
	 */
	public void setEquipmentSensorParams(
			EquipmentSensorParams equipmentSensorParams) {
		this.equipmentSensorParams = equipmentSensorParams;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
}
