package com.taojin.iot.api.equipment.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taojin.iot.BaseController;
import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.Page;
import com.taojin.iot.base.comm.Pageable;
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.service.charts.entity.ChartsType;
import com.taojin.iot.service.equipment.entity.Equipment;
import com.taojin.iot.service.equipment.entity.EquipmentIco;
import com.taojin.iot.service.equipment.entity.EquipmentSensor;
import com.taojin.iot.service.equipment.entity.EquipmentSensorParams;
import com.taojin.iot.service.equipment.enums.EquipmentProtocolEnum;
import com.taojin.iot.service.equipment.service.EquipmentSensorService;
import com.taojin.iot.service.equipment.service.EquipmentService;
import com.taojin.iot.service.task.service.ProductionLineService;
import com.taojin.iot.service.user.service.UserService;

/**
 * Controller-设备 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午2:02:12 author 王杰
 * ============================================================================
 */
@Controller("internalEquipmentController")
@RequestMapping("/internal/equipment/equipment")
public class EquipmentController extends BaseController {
	
	@Resource(name = "productionLineServiceImpl")
	private ProductionLineService productionLineService;
	
	@Resource(name = "equipmentServiceImpl")
	private EquipmentService equipmentService;
	
	@Resource(name = "equipmentSensorServiceImpl")
	private EquipmentSensorService equipmentSensorService;
	
	@Resource(name = "userServiceImpl")
	private UserService userService;

	/**
	 * 获取设备列表
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipment","method":"list","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":{"name":
	 *            "设备名称", "pageNumber":"页码","pageSize":"每页记录数","state":"连接状态"}}
	 * @return
	 */
	@RequestMapping(value = "/list", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String list(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
		// 参数验证
		if (!param.has("pageNumber")) {
			param.put("pageNumber", 1);
		}

		if (!param.has("pageSize")) {
			param.put("pageSize", 200);
		}
		
		
		return equipmentService.findEquipmentList(param).toString();
	}

	/**
	 * 添加设备
	 * 
	 * @param requestParams
	 *            {"action":"equipment","method":"save","session":{"sessionId":
	 *            "会话ID","equipmentTypeId":"频道ID"},
	 *            "param":{"name":"设备名称","chartsTypeId":"图表类型ID",
	 *            "equipmentProtocol":"链接协议","duration":"上报周期","share":"是否公开",
	 *            "devicePosition":"设备位置","devicePositionLng":"经度",
	 *            "devicePositionLat":"纬度","equipmentIcoId":"设备图标",
	 *            "equipmentSensors"
	 *            :[{"name":"名称","type":"类型","sign":"小数位","unit"
	 *            :"单位","equipmentIcoId"
	 *            :"图标ID","equipmentId":"所属设备ID","sensorParamId":"参数ID"}]}}
	 * @return
	 */
	@RequestMapping(value = "/save", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String save(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}

		// 参数验证
		if (!param.has("name")) {
			return errorMsg("-2", "缺少设备名称!");
		}
//		if (!param.has("equipmentProtocol")) {
//			return errorMsg("-2", "缺少设备协议!");
//		}
//		if (!param.has("duration")) {
//			return errorMsg("-2", "缺少上报周期!");
//		}
		if (!param.has("share")) {
			param.put("share", 0);
		}
//		if (!param.has("devicePosition")) {
//			return errorMsg("-2", "缺少设备位置!");
//		}
//		if (!param.has("devicePositionLng")) {
//			return errorMsg("-2", "缺少经度!");
//		}
//		if (!param.has("devicePositionLat")) {
//			return errorMsg("-2", "缺少纬度!");
//		}
//		if (!param.has("equipmentIcoId")) {
//			return errorMsg("-2", "缺少设备图标!");
//		}

		if (!param.has("chartsTypeId")) {
			param.put("chartsTypeId", "1");
		}
		equipmentService.addEquipment(param);
		return successMsg("0", "添加设备成功!");
	}

	/**
	 * 根据设备ID获取设备编辑信息
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipment","method":
	 *            "getEquipmentById","session":
	 *            {"sessionId":"会话ID","equipmentTypeId"
	 *            :"频道ID"},"param":{"equipmentId":"键值"}}
	 * @return
	 */
	@RequestMapping(value = "/getEquipmentById", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String getEquipmentById(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
		// 参数验证
		if (!param.has("equipmentId")) {
			return errorMsg("-2", "请选择一个操作对象!");
		}

		
		return equipmentService.findEquipmentById(param).toString();
	}

	/**
	 * 更新设备
	 * 
	 * @param requestParams
	 *            {"action":"equipment","method":"update","session":{"sessionId"
	 *            :"会话ID","equipmentTypeId":"频道ID"},
	 *            "param":{"equipmentId":"设备ID"
	 *            ,"name":"设备名称","chartsTypeId":"图表类型ID",
	 *            "equipmentProtocol":"链接协议","duration":"上报周期","share":"是否公开",
	 *            "devicePosition"
	 *            :"设备位置","devicePositionLng":"经度","devicePositionLat":"纬度",
	 *            "equipmentIcoId"
	 *            :"设备图标","equipmentSensors":[{"name":"名称","type"
	 *            :"类型","sign":"小数位"
	 *            ,"unit":"单位","equipmentIcoId":"图标ID","sensorParamId"
	 *            :"参数ID"}]}}
	 * @return
	 */
	@RequestMapping(value = "/update", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String update(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject session = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			session = node.getJSONObject("session");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}

		// 参数验证
		if (!param.has("equipmentId")) {
			return errorMsg("-2", "缺少设备ID!");
		}
		Equipment equipment = equipmentService.find(param
				.getLong("equipmentId"));

		if (param.has("name")) {
			equipment.setName(param.getString("name"));
		}
		if (param.has("duration")) {
			equipment.setDuration(param.getInt("duration"));
		}
		if (param.has("share")) {
			equipment.setShare(param.getInt("share"));
		}
		if (param.has("devicePosition")) {
			equipment.setDevicePosition(param.getString("devicePosition"));
		}
		if (param.has("devicePositionLng")) {
			equipment
					.setDevicePositionLng(param.getString("devicePositionLng"));
		}
		if (param.has("devicePositionLat")) {
			equipment
					.setDevicePositionLat(param.getString("devicePositionLat"));
		}
		if (param.has("workStationName"))
			equipment.setWorkStationName(param.getString("workStationName"));
		if (param.has("workStationNumber"))
			equipment
					.setWorkStationNumber(param.getString("workStationNumber"));
		if (param.has("offTime"))
			equipment.setOffTime(param.getInt("offTime"));
		
		equipment.setAlarmCount(1);
		if (param.has("alarmCount")&&param.isNullObject()){
			equipment.setAlarmCount(param.getInt("alarmCount"));
		}
		if (param.has("yieldTarget"))
			equipment.setYieldTarget(param.getInt("yieldTarget"));
		if (param.has("lineNumber"))
			equipment.setLineNumber(param.getString("lineNumber"));
		if (param.has("lineName"))
			equipment.setLineName(param.getString("lineName"));

		if (param.has("equipmentIcoId")) {
			EquipmentIco equipmentIco = new EquipmentIco();
			equipmentIco.setId(param.getLong("equipmentIcoId"));
			equipment.setEquipmentIco(equipmentIco);
		}
		if (param.has("chartsTypeId")) {
			ChartsType chartsType = new ChartsType();
			chartsType.setId(param.getLong("chartsTypeId"));
			equipment.setChartsType(chartsType);
		}

		if (param.has("idNumber")) {
			equipment.setIdNumber(param.getString("idNumber"));
		}

	 equipmentService.update(equipment);
		if (param.has("equipmentSensors")) {
			JSONArray jsonSonsors = param.getJSONArray("equipmentSensors");
			for (int i = 0; i < jsonSonsors.size(); i++) {
				try {
					JSONObject sonsorParam = jsonSonsors.getJSONObject(i);
					EquipmentSensor equipmentSensor = null;
					if (!sonsorParam.has("id")) {
						equipmentSensor = new EquipmentSensor();
						equipmentSensor.setEquipment(equipment);
					} else {
						equipmentSensor = equipmentSensorService
								.find(sonsorParam.getLong("id"));
					}
					if (equipmentSensor == null) {
						equipmentSensor = new EquipmentSensor();
						equipmentSensor.setEquipment(equipment);
						equipmentSensor.setEquipmentTypeId(session
								.getLong("equipmentTypeId"));
					}
					if (sonsorParam.has("name")) {
						equipmentSensor.setName(sonsorParam.getString("name"));
					}
					if (sonsorParam.has("unit")) {
						equipmentSensor.setUnit(sonsorParam.getString("unit"));
					}
					if (sonsorParam.has("type")) {
						equipmentSensor.setType(sonsorParam.getInt("type"));
					}
					if (sonsorParam.has("sign")) {
						equipmentSensor.setSign(sonsorParam.getInt("sign"));
					}
					if (sonsorParam.has("serialNumber")) {
						equipmentSensor.setSerialNumber(sonsorParam
								.getString("serialNumber"));
					}
					if (sonsorParam.has("sensorIcoId")) {
						EquipmentIco equipmentIco = new EquipmentIco();
						equipmentIco.setId(sonsorParam.getLong("sensorIcoId"));
						equipmentSensor.setEquipmentIco(equipmentIco);
					}

					if (sonsorParam.has("sensorParamId")) {
						EquipmentSensorParams sensorParams = new EquipmentSensorParams();
						sensorParams
								.setId(sonsorParam.getLong("sensorParamId"));
						equipmentSensor.setEquipmentSensorParams(sensorParams);
					}
					equipmentSensorService.update(equipmentSensor);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return successMsg("0", "更新设备成功!");
	}

	/**
	 * 删除设备
	 * 
	 * @param requestParams
	 *            {"action":"equipment","method":"delete","session":{"sessionId"
	 *            :"会话ID","equipmentTypeId":"频道ID"},
	 *            "param":{"equipmentId":"设备ID"}}
	 * @return
	 */
	@RequestMapping(value = "/delete", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String delete(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}

		// 参数验证
		if (!param.has("equipmentId")) {
			return errorMsg("-2", "缺少设备ID!");
		}
		Equipment equipment = equipmentService.find(param
				.getLong("equipmentId"));
		if (equipment.getEquipmentSensors().size() > 0) {
			return errorMsg("-2", "当前设备下绑定了"
					+ equipment.getEquipmentSensors().size() + "个传感器,不可删除!");
		}
		if (equipment.getEquipmentTriggers().size() > 0) {
			return errorMsg("-2", "当前设备下绑定了"
					+ equipment.getEquipmentTriggers().size() + "个触发器,不可删除!");
		}
		equipment.setIsDel(true);
		equipmentService.update(equipment);
		return successMsg("0", "删除设备成功!");
	}

}
