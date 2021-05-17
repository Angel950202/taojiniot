package com.taojin.iot.service.equipment.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.taojin.iot.base.comm.entity.BaseEntity;

/**
 * Entity-设备ip服务
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午4:07:37
 * author 王杰
 * ============================================================================
 */
@Entity
@Table(name = "iot_equipment_ipaddress")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_equipment_ipaddress_sequence")
public class EquipmentIpaddress extends BaseEntity{

	private static final long serialVersionUID = 2244900367927343632L;

	/**名称*/
	private String name;
	/**ip地址*/
	private String ipAddress;
	/**端口*/
	private String ipPort;
	/**备注*/
	private String memo;
	
	/**使用的设备*/
	List<Equipment> equipments = new ArrayList<Equipment>();
	
	/**
	 * 获取名称
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取ip地址/域名
	 * @return
	 */
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[0-9a-z_A-Z\\u4e00-\\u9fa5]+$")
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getIpAddress() {
		return ipAddress;
	}
	/**
	 * 设置IP地址/域名
	 * @param ipAddress
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	/**
	 * 获取端口
	 * @return
	 */
	@Column(nullable = false)
	public String getIpPort() {
		return ipPort;
	}
	/**
	 * 设置端口
	 * @param ipPort
	 */
	public void setIpPort(String ipPort) {
		this.ipPort = ipPort;
	}
	/**
	 * 获取备注
	 * @return
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * 设置备注
	 * @param memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	/**
	 * 获取绑定的设备
	 * @return
	 */
	@OneToMany(mappedBy = "equipmentIpaddress", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Equipment> getEquipments() {
		return equipments;
	}
	/**
	 * 设置绑定的设备
	 * @param equipments
	 */
	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
	}
	
}
