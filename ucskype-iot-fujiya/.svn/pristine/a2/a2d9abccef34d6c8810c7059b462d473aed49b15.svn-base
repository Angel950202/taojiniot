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
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.taojin.iot.base.comm.entity.BaseEntity;

/**
 * Entity-传感器自定义字段，与协议格式对应
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午4:25:41
 * author 王杰
 * ============================================================================
 */
@Entity
@Table(name = "iot_equipment_sensor_params")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_equipment_sensor_params_sequence")
public class EquipmentSensorParams extends BaseEntity{

	private static final long serialVersionUID = 8290828726467245926L;

	/**自定义名称*/
	private String name;
	/**自定义参数*/
	private String parameter;
	/**自定义描述*/
	private String description;
	/**触发器*/
	private Set<EquipmentSensor> equipmentSensorSet = new HashSet<EquipmentSensor>(); 
	
	/**
	 * 获取自定义名称
	 * @return
	 */
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[0-9a-z_A-Z\\u4e00-\\u9fa5]+$")
	@Column(nullable = false, unique = true, length = 100)
	public String getName() {
		return name;
	}
	/**
	 * 设置自定义名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取自定义参数对象
	 * {"key1":"value1","key2":"value2"}
	 * @return
	 */
	public String getParameter() {
		return parameter;
	}
	/**
	 * 设置自定义参数对象
	 * @param parameter {"key1":"value1","key2":"value2"}
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	/**
	 * 获取自定义描述
	 * @return
	 */
	@Column(length = 100)
	public String getDescription() {
		return description;
	}
	/**
	 * 设置自定义描述
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(mappedBy = "equipmentSensorParams", fetch = FetchType.LAZY)
	public Set<EquipmentSensor> getEquipmentSensorSet() {
		return equipmentSensorSet;
	}
	public void setEquipmentSensorSet(Set<EquipmentSensor> equipmentSensorSet) {
		this.equipmentSensorSet = equipmentSensorSet;
	}
	
	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<EquipmentSensor> equipmentSensors = getEquipmentSensorSet();
		if (equipmentSensors != null) {
			for (EquipmentSensor sensor : equipmentSensors) {
				sensor.setEquipmentSensorParams(null);
			}
		}
	}
}
