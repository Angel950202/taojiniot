package com.taojin.iot.service.equipment.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.Page;
import com.taojin.iot.base.comm.Pageable;
import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.service.charts.entity.ChartsType;
import com.taojin.iot.service.equipment.dao.EquipmentDao;
import com.taojin.iot.service.equipment.entity.Equipment;
import com.taojin.iot.service.equipment.entity.EquipmentIco;
import com.taojin.iot.service.equipment.entity.EquipmentSensor;
import com.taojin.iot.service.equipment.enums.EquipmentProtocolEnum;
import com.taojin.iot.service.equipment.service.EquipmentSensorService;
import com.taojin.iot.service.equipment.service.EquipmentService;
import com.taojin.iot.service.task.service.ProductionLineService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Service实现-设备
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午8:45:58
 * author 王杰
 * ============================================================================
 */
@Service("equipmentServiceImpl")
public class EquipmentServiceImpl extends BaseServiceImpl<Equipment,Long> implements EquipmentService{
	
	@Resource(name = "productionLineServiceImpl")
	private ProductionLineService productionLineService;
	
	@Resource(name = "equipmentSensorServiceImpl")
	private EquipmentSensorService equipmentSensorService;
	
	@Resource(name = "equipmentDaoImpl")
	private EquipmentDao equipmentDao;

	@Resource(name = "equipmentDaoImpl")
	public void setBaseDao(EquipmentDao equipmentDao) {
		super.setBaseDao(equipmentDao);
	}

	@Override
	public Equipment querySensorLineByAddrssANDLine(String address,
			String lineNumber) {

		return equipmentDao.querySensorLineByAddrssANDLine(address, lineNumber);
	}

	@Override
	public JSONObject findEquipmentList(JSONObject param) {

		List<Filter> filters = new ArrayList<Filter>();
		//设备名称
		if (param.has("name")&&!"".equals(param.get("name"))) {
			filters.add(Filter.like("name", "%" + param.getString("name") + "%"));
		}
		//生产线
		if(param.has("lineName")&&!"null".equals(param.getString("lineName"))&&!"".equals(param.getString("lineName"))){
			filters.add(Filter.eq("lineName", param.getString("lineName")));
		}
		//设备状态
		if (param.has("state")&&!"".equals(param.get("state"))){
			filters.add(Filter.eq("state", param.getInt("state")));
		}
		//删除标记
		filters.add(Filter.eq("isDel", false));

		// 分页检索
		Pageable pageable = new Pageable(param.getInt("pageNumber"),
				param.getInt("pageSize"));
		pageable.setFilters(filters);
		Page<Equipment> page = findPage(pageable);

		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取设备成功");
		jsonReturn.put("values", page2JsonArray(page));
		jsonReturn = getJsonPage(page, jsonReturn);
		return jsonReturn;
	}
	
	private JSONArray page2JsonArray(Page<Equipment> page) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < page.getContent().size(); i++) {
			JSONObject json = new JSONObject();
			Equipment equipment = page.getContent().get(i);
			json.put("id", equipment.getId());
			json.put("name", equipment.getName());
			json.put("createDate", DatesUtils.dateToString(
					equipment.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
			json.put("idNumber", equipment.getIdNumber());
			json.put("workStationName", equipment.getWorkStationName());
			json.put("workStationNumber", equipment.getWorkStationNumber());
			json.put("offTime", equipment.getOffTime());
			json.put("alarmCount", equipment.getAlarmCount());
			json.put("yieldTarget", equipment.getYieldTarget());
			json.put("lineNumber", equipment.getLineNumber());
			json.put("lineName", equipment.getLineName());
			json.put("serialNumber", equipment.getSerialNumber());
			json.put("equipmentProtocol", equipment.getEquipmentProtocol()
					.name());
			json.put("duration", equipment.getDuration());
			json.put("share", equipment.getShare());
			json.put("devicePosition", equipment.getDevicePosition());
			json.put("devicePositionLng", equipment.getDevicePositionLng());
			json.put("devicePositionLat", equipment.getDevicePositionLat());
			if (equipment.getEquipmentIco() != null) {
				json.put("equipmentIco", equipment.getEquipmentIco()
						.getIcoPath());
				json.put("equipmentOnlineIco", equipment.getEquipmentIco()
						.getOnlineIco());
			}
			if (equipment.getEquipmentIpaddress() != null) {
				json.put("equipmentIp", equipment.getEquipmentIpaddress()
						.getIpAddress());
				json.put("equipmentPort", equipment.getEquipmentIpaddress()
						.getIpPort());
			}
			if (equipment.getChartsType() != null) {
				json.put("chartsId", equipment.getChartsType().getId());
				json.put("chartsTypes", equipment.getChartsType()
						.getEchartTypeParams());
				json.put("chartsName", equipment.getChartsType().getName());
			}
			json.put("equipmentStatus", equipment.getState());
			json.put("equipmentStatusWarn", equipment.getStateInfo());// 报警信息
			//specification-规格型号
			json.put("specification", equipment.getSpecification());
			//departmentName所属部门
			json.put("departmentName", equipment.getDepartmentName());
			//factoryNumber-出厂编号
			json.put("factoryNumber", equipment.getFactoryNumber());
			//commissioningDate-投产日期
			json.put("commissioningDate", equipment.getCommissioningDate());
			jsonArray.add(json);
		}
		return jsonArray;
	}
	
	private JSONObject getJsonPage(Page<?> page,JSONObject jsonReturn){
		JSONObject jsonPage = new JSONObject();
		jsonPage.put("totalPage", page.getTotalPages());
		jsonPage.put("total", page.getTotal());
		jsonPage.put("pageNumber", page.getPageNumber());
		jsonPage.put("pageSize", page.getPageSize());
		jsonReturn.put("page", jsonPage);
		return jsonReturn;
	}

	/**
	 * 添加传感器
	 */
	@Override
	public void addEquipment(JSONObject param) {
		Equipment equipment = new Equipment();
		equipment.setName(param.getString("name"));
		if (!param.has("idNumber"))
			equipment.setIdNumber(param.getString("idNumber"));
		equipment.setOffTime(param.getInt("offTime"));
		equipment.setAlarmCount(1);
		if(param.has("alarmCount"))
		equipment.setAlarmCount(param.getInt("alarmCount"));
		equipment.setYieldTarget(param.getInt("yieldTarget"));
		equipment.setLineNumber(param.getString("lineNumber"));
		equipment.setLineName(productionLineService.getByParam("lineNumber",
				param.getString("lineNumber")).getLineName());
		equipment.setEquipmentProtocol(EquipmentProtocolEnum.getEnum(param.getString("equipmentProtocol")));
		equipment.setDuration(param.getInt("duration"));
		equipment.setShare(param.getInt("share"));
		equipment.setDevicePosition(param.getString("devicePosition"));
		equipment.setDevicePositionLng(param.getString("devicePositionLng"));
		equipment.setDevicePositionLat(param.getString("devicePositionLat"));
		EquipmentIco equipmentIco = new EquipmentIco();
		equipmentIco.setId(param.getLong("equipmentIcoId"));
		equipment.setEquipmentIco(equipmentIco);
		ChartsType chartsType = new ChartsType();
		chartsType.setId(param.getLong("chartsTypeId"));
		equipment.setChartsType(chartsType);
		equipment.setState(2);//初始值均为断开状态
		save(equipment);
		// 添加传感器
		if (param.has("equipmentSensors")) {
			List<EquipmentSensor> equipmentSensorList = com.alibaba.fastjson.JSONObject.parseArray(
					param.getString("equipmentSensors"), EquipmentSensor.class);
			for(EquipmentSensor equipmentSensor:equipmentSensorList){
				equipmentSensor.setEquipment(equipment);
				equipmentSensorService.save(equipmentSensor);
			}
		}
	}

	@Override
	public JSONObject findEquipmentById(JSONObject param) {

		JSONObject jsonReturn = new JSONObject();
		
		
		Equipment equipment = find(param
				.getLong("equipmentId"));
		if (equipment == null) {
			jsonReturn.put("errcode", "-2");
			jsonReturn.put("errmsg", "对象不存在");
			return jsonReturn;
		}

		JSONObject json = new JSONObject();
		json.put("workStationName", equipment.getWorkStationName());
		json.put("workStationNumber", equipment.getWorkStationNumber());
		json.put("offTime", equipment.getOffTime());//停机时间目标值
		json.put("alarmCount", equipment.getAlarmCount());//停机时间目标值
		json.put("yieldTarget", equipment.getYieldTarget());//良品率目标值
		json.put("lineNumber", equipment.getLineNumber());//所属产线
		json.put("lineName", equipment.getLineName());//所属产线
		json.put("id", equipment.getId());
		json.put("name", equipment.getName());//设备名称
		json.put("createDate", DatesUtils.dateToString(
				equipment.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
		json.put("idNumber", equipment.getIdNumber());//设备ID
		json.put("serialNumber", equipment.getSerialNumber());//序列号
		json.put("equipmentProtocol", equipment.getEquipmentProtocol().name());//链接协议
		json.put("duration", equipment.getDuration());//上报周期
		json.put("specification", equipment.getSpecification());//规格型号
		json.put("departmentName", equipment.getDepartmentName());//所属部门
		json.put("factoryNumber", equipment.getFactoryNumber());//出厂编号
		json.put("commissioningDate", equipment.getCommissioningDate());//投产日期
		json.put("share", equipment.getShare());//是否公开
		json.put("devicePosition", equipment.getDevicePosition());
		json.put("devicePositionLng", equipment.getDevicePositionLng());
		json.put("devicePositionLat", equipment.getDevicePositionLat());
		if (equipment.getEquipmentType()!=null) {//所属设备类型
			json.put("equipmentTypeId", equipment.getEquipmentType().getId());
			json.put("equipmentTypeName", equipment.getEquipmentType().getName());			
		}
		if (equipment.getEquipmentIco() != null) {
			json.put("equipmentIcoId", equipment.getEquipmentIco().getId());
			json.put("equipmentIco", equipment.getEquipmentIco().getIcoPath());
			json.put("equipmentOnlineIco", equipment.getEquipmentIco()
					.getOnlineIco());
		}
		if (equipment.getEquipmentIpaddress() != null) {
			json.put("equipmentIp", equipment.getEquipmentIpaddress()
					.getIpAddress());
			json.put("equipmentPort", equipment.getEquipmentIpaddress()
					.getIpPort());
		}
		if (equipment.getChartsType() != null) {
			json.put("chartsId", equipment.getChartsType().getId());
			json.put("chartsTypes", equipment.getChartsType()
					.getEchartTypeParams());
			json.put("chartsName", equipment.getChartsType().getName());
		}

		if (equipment.getEquipmentSensors().size() > 0) {//传感器
			JSONArray jsonArraySensors = new JSONArray();
			for (int i = 0; i < equipment.getEquipmentSensors().size(); i++) {
				JSONObject jsonSensors = new JSONObject();
				EquipmentSensor equipmentSensor = equipment
						.getEquipmentSensors().get(i);
				jsonSensors.put("id", equipmentSensor.getId());
				jsonSensors.put("idNumber", equipmentSensor.getIdNumber());
				jsonSensors.put("serialNumber",
						equipmentSensor.getSerialNumber());
				jsonSensors.put("name", equipmentSensor.getName());
				jsonSensors.put("type", equipmentSensor.getType());
				jsonSensors.put("sign", equipmentSensor.getSign());
				jsonSensors.put("unit", equipmentSensor.getUnit());
				if (equipmentSensor.getEquipmentIco() != null) {
					jsonSensors.put("sensorIcoId", equipmentSensor
							.getEquipmentIco().getId());
					jsonSensors.put("sensorIcoPath", equipmentSensor
							.getEquipmentIco().getIcoPath());
					jsonSensors.put("sensorIcoOnlinePath", equipmentSensor
							.getEquipmentIco().getOnlineIco());
				}
				if (equipmentSensor.getEquipment() != null) {
					jsonSensors.put("equipmentId", equipmentSensor
							.getEquipment().getId());
					jsonSensors.put("equipment", equipmentSensor.getEquipment()
							.getName());
				}
				if (equipment.getChartsType() != null) {
					json.put("chartsId", equipment.getChartsType().getId());
					json.put("chartTypes", equipment.getChartsType()
							.getEchartTypeParams());
					json.put("chartName", equipment.getChartsType().getName());
				}

				jsonArraySensors.add(jsonSensors);
			}
			json.put("equipmentSensors", jsonArraySensors);
		}

		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取设备成功");
		jsonReturn.put("value", json);
		return jsonReturn;
	}
}
