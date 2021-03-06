package com.taojin.iot.service.equipment.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.taojin.iot.base.comm.entity.BaseEntity;
import com.taojin.iot.base.comm.utils.UUIDTools;
import com.taojin.iot.service.charts.entity.ChartsType;
import com.taojin.iot.service.equipment.enums.EquipmentProtocolEnum;
import com.taojin.iot.service.traffic.entity.TrafficCard;

/**
 * Entity-设备管理 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午6:37:02 author 王杰
 * ============================================================================
 */
@Entity
@Table(name = "iot_equipment")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_equipment_sequence")
public class Equipment extends BaseEntity {

	private static final long serialVersionUID = -9197117667167329308L;

	private String workStationName;
	
	private String workStationNumber;
	
	/**
	 * 停机时间目标值
	 */
	private int offTime;
	
	/**
	 * 报警次数目标值
	 */
	private int alarmCount;	
	/**
	 * 目标良品率
	 */
	private int yieldTarget;
	/**
	 * 生产线编号
	 */
	private String lineNumber;
	/**
	 * 生产线名称
	 */
	private String lineName;
	/** 设备名称 */
	private String name;
	/** 设备ID　yyyyMMddHHmmss */
	private String idNumber;
	/** 序列号 */
	private String serialNumber;
	/** 链接协议 */
	private EquipmentProtocolEnum equipmentProtocol;
	/** 上报周期 */
	private Integer duration;
	/** 是否公开 0:否，1：是 */
	private Integer share;
	/** 设备位置 */
	private String devicePosition;
	/** 设置经度 */
	private String devicePositionLng;
	/** 设置纬度 */
	private String devicePositionLat;
	/**连接状态 0:正常，1：报警/故障，2：断开*/
	private Integer state;
	/**状态信息*/
	private String stateInfo;
	
	/** 设备图标 */
	private EquipmentIco equipmentIco;
	/**设备地址*/
	private EquipmentIpaddress equipmentIpaddress;
	/**图表类型*/
	private ChartsType chartsType;
	
	/** 设备传感器 */
	private List<EquipmentSensor> equipmentSensors = new ArrayList<EquipmentSensor>();
	/** 设备触发器*/
	private List<EquipmentTrigger> equipmentTriggers = new ArrayList<EquipmentTrigger>();
	/**流量 卡*/
	private List<TrafficCard> trafficCards = new ArrayList<TrafficCard>();
	/**
	 * 主设备(用来计算设备状态、故障、停机的主要工站设备)
	 * 数量不包含里面
	 */
	private Boolean mainEquipment = false;
	
	/**
	 * 规格型号
	 */
	private String specification;
	
	/**
	 * 所属部门
	 */
	private String departmentName;
	
	/**
	 * 出厂编号
	 */
	private String factoryNumber;
	
	/**
	 * 投产日期
	 */
	private Date commissioningDate;

	/**
	 * 所属设备类型
	 */
	private EquipmentType equipmentType;
	
	
	
	public Equipment(){
		this.idNumber = String.valueOf(System.currentTimeMillis());
		this.serialNumber = UUIDTools.getUUID();
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
	 * 获取设备序列号
	 * 
	 * @return
	 */
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[0-9a-z_A-Z\\u4e00-\\u9fa5]+$")
	@Length(min = 2, max = 20)
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * 设置设备序列号
	 * 
	 * @param serialNumber
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * 获取编号
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
	 * 设置编号
	 * 
	 * @param idNumber
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * 获取设备协议
	 * 
	 * @return
	 */
	@Column(nullable = false)
	public EquipmentProtocolEnum getEquipmentProtocol() {
		return equipmentProtocol;
	}

	/**
	 * 设置设备协议
	 * 
	 * @param equipmentProtocol
	 */
	public void setEquipmentProtocol(EquipmentProtocolEnum equipmentProtocol) {
		this.equipmentProtocol = equipmentProtocol;
	}

	/**
	 * 上报周期
	 * 
	 * @return
	 */
	@Column(nullable = false)
	public Integer getDuration() {
		return duration;
	}

	/**
	 * 设置周期
	 * 
	 * @param duration
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * 是否公开
	 * 
	 * @return
	 */
	public Integer getShare() {
		return share;
	}

	/**
	 * 设置是否公开
	 * 
	 * @param share
	 *            0：否，1：是
	 */
	public void setShare(Integer share) {
		this.share = share;
	}

	/**
	 * 获取设备位置
	 * 
	 * @return
	 */
	public String getDevicePosition() {
		return devicePosition;
	}

	/**
	 * 设置设备位置
	 * 
	 * @param devicePosition
	 */
	public void setDevicePosition(String devicePosition) {
		this.devicePosition = devicePosition;
	}

	/**
	 * 获取设置经度
	 * 
	 * @return
	 */
	public String getDevicePositionLng() {
		return devicePositionLng;
	}

	/**
	 * 设置经度
	 * 
	 * @param devicePositionLng
	 */
	public void setDevicePositionLng(String devicePositionLng) {
		this.devicePositionLng = devicePositionLng;
	}

	/**
	 * 获取纬度
	 * 
	 * @return
	 */
	public String getDevicePositionLat() {
		return devicePositionLat;
	}

	/**
	 * 设置纬度
	 * 
	 * @param devicePositionLat
	 */
	public void setDevicePositionLat(String devicePositionLat) {
		this.devicePositionLat = devicePositionLat;
	}
	
	/**
	 * 获取状
	 * @return  0:正常，1：报警/故障，2：断开
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * 设置状态 
	 * @param state  0:正常，1：报警/故障，2：断开
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * 获取设备图标
	 * 
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipmentIco_id")
	public EquipmentIco getEquipmentIco() {
		return equipmentIco;
	}

	/**
	 * 设置设备图标
	 * 
	 * @param equipmentIco
	 */
	public void setEquipmentIco(EquipmentIco equipmentIco) {
		this.equipmentIco = equipmentIco;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipment_type_id")
	public EquipmentType getEquipmentType() {
		return equipmentType;
	}


	public void setEquipmentType(EquipmentType equipmentType) {
		this.equipmentType = equipmentType;
	}


	/**
	 * 获取设备地址
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipment_ipaddress_id")
	public EquipmentIpaddress getEquipmentIpaddress() {
		return equipmentIpaddress;
	}

	public void setEquipmentIpaddress(EquipmentIpaddress equipmentIpaddress) {
		this.equipmentIpaddress = equipmentIpaddress;
	}

	/**
	 * 获取设备传感器
	 * 
	 * @return
	 */
	@OneToMany(mappedBy = "equipment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<EquipmentSensor> getEquipmentSensors() {
		return equipmentSensors;
	}

	/**
	 * 设置设备传感器
	 * 
	 * @param equipmentSensors
	 */
	public void setEquipmentSensors(List<EquipmentSensor> equipmentSensors) {
		this.equipmentSensors = equipmentSensors;
	}

	/**
	 * 获取触发器
	 * @return
	 */
	@OneToMany(mappedBy = "equipment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<EquipmentTrigger> getEquipmentTriggers() {
		return equipmentTriggers;
	}

	/**
	 * 设置触发器
	 * @param equipmentTriggers
	 */
	public void setEquipmentTriggers(List<EquipmentTrigger> equipmentTriggers) {
		this.equipmentTriggers = equipmentTriggers;
	}
	

	@OneToMany(mappedBy = "equipment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<TrafficCard> getTrafficCards() {
		return trafficCards;
	}

	public void setTrafficCards(List<TrafficCard> trafficCards) {
		this.trafficCards = trafficCards;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		List<EquipmentSensor> equipmentSensors = this.getEquipmentSensors();
		if (equipmentSensors != null) {
			for (EquipmentSensor sensor : equipmentSensors) {
				sensor.setEquipment(null);
			}
		}
		List<EquipmentTrigger> equipmentTriggers = this.getEquipmentTriggers();
		if (equipmentTriggers != null) {
			for (EquipmentTrigger trigger : equipmentTriggers) {
				trigger.setEquipment(null);
			}
		}
		
		List<TrafficCard> trafficCards = this.getTrafficCards();
		if (trafficCards != null) {
			for (TrafficCard trafficCard : trafficCards) {
				trafficCard.setEquipment(null);
			}
		}
	}

	/**
	 * 获取图表ID
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chartsType_id")
	public ChartsType getChartsType() {
		return chartsType;
	}

	public void setChartsType(ChartsType chartsType) {
		this.chartsType = chartsType;
	}

	/**
	 * 获取状态信息
	 * @return
	 */
	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public String getWorkStationName() {
		return workStationName;
	}

	public void setWorkStationName(String workStationName) {
		this.workStationName = workStationName;
	}


	public String getWorkStationNumber() {
		return workStationNumber;
	}

	public void setWorkStationNumber(String workStationNumber) {
		this.workStationNumber = workStationNumber;
	}

	public int getOffTime() {
		return offTime;
	}

	public void setOffTime(int offTime) {
		this.offTime = offTime;
	}

	public int getAlarmCount() {
		return alarmCount;
	}

	public void setAlarmCount(int alarmCount) {
		this.alarmCount = alarmCount;
	}

	public int getYieldTarget() {
		return yieldTarget;
	}

	public void setYieldTarget(int yieldTarget) {
		this.yieldTarget = yieldTarget;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public Boolean getMainEquipment() {
		return mainEquipment;
	}

	public void setMainEquipment(Boolean mainEquipment) {
		this.mainEquipment = mainEquipment;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getFactoryNumber() {
		return factoryNumber;
	}

	public void setFactoryNumber(String factoryNumber) {
		this.factoryNumber = factoryNumber;
	}

	public Date getCommissioningDate() {
		return commissioningDate;
	}

	public void setCommissioningDate(Date commissioningDate) {
		this.commissioningDate = commissioningDate;
	}
	
	
}
