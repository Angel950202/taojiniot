package com.taojin.iot.service.equipment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.taojin.iot.base.comm.entity.BaseEntity;

/**
 * Entity-设备类型
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午6:16:05
 * author 王杰
 * ============================================================================
 */
@Entity
@Table(name = "iot_equipment_fault_type")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_equipment_fault_type_sequence")
public class EquipmentFaultType extends BaseEntity{

	private static final long serialVersionUID = -4179318503893385795L;
	
	/**名称*/
	private String name;
	/**图标*/
	private String icopath;
	
	/**
	 * 获取类型
	 * @return
	 */
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[0-9a-z_A-Z\\u4e00-\\u9fa5]+$")
	@Length(min = 2, max = 20)
	@Column(nullable = false, updatable = true, unique = true, length = 100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 类型图标
	 * @return
	 */
	public String getIcopath() {
		return icopath;
	}
	public void setIcopath(String icopath) {
		this.icopath = icopath;
	}
}
