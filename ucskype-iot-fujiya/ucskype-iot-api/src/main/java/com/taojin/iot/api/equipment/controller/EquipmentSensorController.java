package com.taojin.iot.api.equipment.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.taojin.iot.base.comm.entity.UserSession;
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.service.equipment.entity.Equipment;
import com.taojin.iot.service.equipment.entity.EquipmentIco;
import com.taojin.iot.service.equipment.entity.EquipmentSensor;
import com.taojin.iot.service.equipment.entity.EquipmentSensorParams;
import com.taojin.iot.service.equipment.service.EquipmentSensorService;
import com.taojin.iot.service.equipment.service.EquipmentSensorStateService;
import com.taojin.iot.service.report.entity.ReportEquipmentSensor;
import com.taojin.iot.service.report.service.ReportEquipmentSensorService;

/**
 * Controller-传感器 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午1:46:14 author 王杰
 * ============================================================================
 */
@Controller("internalEquipmentSensorController")
@RequestMapping("/internal/equipment/equipmentSensor")
public class EquipmentSensorController extends BaseController {
	@Resource(name = "equipmentSensorServiceImpl")
	private EquipmentSensorService equipmentSensorService;
	@Resource(name = "equipmentSensorStateServiceImpl")
	private EquipmentSensorStateService equipmentSensorStateService;
	@Resource(name = "reportEquipmentSensorServiceImpl")
	private ReportEquipmentSensorService reportEquipmentSensorService;

	/**
	 * 获取传感器（分页）
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentSensor","method":"list",
	 *            "session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param"
	 *            :{"name":
	 *            "名称","equipmentId":"所属设备ID","pageNumber":"页码","pageSize"
	 *            :"每页记录数"}}
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
		JSONObject session = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			session = node.getJSONObject("session");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
		// 会话验证
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		} else if (super.getEquipmentTypeId(session) == null) {
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}

		// 参数验证
		if (!param.has("pageNumber")) {
			param.put("pageNumber", 1);
		}

		if (!param.has("pageSize")) {
			param.put("pageSize", 20);
		}

		List<Filter> filters = new ArrayList<Filter>();
		if (param.has("name")) {
			filters.add(Filter.like("name", "%" + param.getString("name") + "%"));
		}
		if (param.has("equipmentId")) {
			Equipment equipment = new Equipment();
			equipment.setId(param.getLong("equipmentId"));
			filters.add(Filter.eq("equipment", equipment));
		}

		Pageable pageable = new Pageable(param.getInt("pageNumber"),
				param.getInt("pageSize"));
		filters.add(Filter.eq("equipmentTypeId",
				session.getLong("equipmentTypeId")));
		filters.add(Filter.eq("ownerId",
				Long.parseLong(userSession.getUserId())));
		pageable.setFilters(filters);
		Page<EquipmentSensor> page = equipmentSensorService.findPage(pageable);

		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取设备传感器");
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < page.getContent().size(); i++) {
			JSONObject json = new JSONObject();
			EquipmentSensor equipmentSensor = page.getContent().get(i);
			json.put("id", equipmentSensor.getId());
			json.put("idNumber", equipmentSensor.getIdNumber());
			json.put("name", equipmentSensor.getName());
			json.put("type", equipmentSensor.getType());
			json.put("sign", equipmentSensor.getSign());
			json.put("unit", equipmentSensor.getUnit());
			if (equipmentSensor.getEquipmentIco() != null) {
				json.put("equipmentIcoId", equipmentSensor.getEquipmentIco()
						.getId());
				json.put("equipmentIcoPath", equipmentSensor.getEquipmentIco()
						.getIcoPath());
				json.put("equipmentIcoOnlinePath", equipmentSensor
						.getEquipmentIco().getOnlineIco());
			}
			if (equipmentSensor.getEquipment() != null) {
				json.put("equipmentId", equipmentSensor.getEquipment().getId());
				json.put("equipment", equipmentSensor.getEquipment().getName());
				if(equipmentSensor.getEquipment().getChartsType() != null){
					json.put("chartsId", equipmentSensor.getEquipment().getChartsType().getId());
					json.put("chartsTypes", equipmentSensor.getEquipment().getChartsType().getEchartTypeParams());
					json.put("chartsName", equipmentSensor.getEquipment().getChartsType().getName());
				}
			}

			//获取传感器最近上报
			List<Filter> sensorStateFilters = new ArrayList<Filter>();
			sensorStateFilters.add(Filter.eq("sensor_number", equipmentSensor.getIdNumber()));
			List<ReportEquipmentSensor> reportEquipmentSensors = reportEquipmentSensorService.findListSensor(1, sensorStateFilters);
			
			if(reportEquipmentSensors.size() > 0){
				ReportEquipmentSensor reportEquipmentSensor = reportEquipmentSensors.get(0);
				json.put("sensorValues", reportEquipmentSensor.getSensorValues());
				json.put("sensorTrueValues", reportEquipmentSensor.getSensorTrueValue());
				Date nowTime = DatesUtils.stringToDate(DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
				long min = DatesUtils.compareMin(reportEquipmentSensor.getCreateDate(), nowTime);
				if(min > equipmentSensor.getEquipment().getDuration()){//上报时间大于上报周期
					json.put("sensorState", 0);
				}else{
					json.put("sensorState", 1);
				}
				json.put("sensorReportTime", DatesUtils.dateToString(reportEquipmentSensor.getCreateDate(), "yyyy-MM-dd HH:mm"));
			}else{
				JSONArray jsonSensorValues = new JSONArray();
				JSONObject jsonSensorValue = new JSONObject();
				jsonSensorValue.put("value", "0");
				jsonSensorValues.add(jsonSensorValue);
				json.put("sensorState", 0);
				json.put("sensorValues", jsonSensorValues);
				json.put("sensorTrueValues", "");
				json.put("sensorReportTime", "");
			}

			jsonArray.add(json);
		}
		jsonReturn.put("values", jsonArray);
		jsonReturn = super.getJsonPage(page, jsonReturn);
		return jsonReturn.toString();
	}

	/**
	 * 添加传感器
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentSensor","method":"save",
	 *            "session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":
	 *            {"name":
	 *            "名称","type":"类型","sign":"小数位","unit":"单位","equipmentIcoId"
	 *            :"图标ID","equipmentId":"所属设备ID","sensorParamId":"自定义参数ID"}}
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
		JSONObject session = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			session = node.getJSONObject("session");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
		// 会话验证
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		} else if (super.getEquipmentTypeId(session) == null) {
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}

		if (!param.has("name")) {
			return errorMsg("-2", "请填写传感器名称!");
		}
		if (!param.has("equipmentId")) {
			return errorMsg("-2", "请为传感器添加关联设备!");
		}
		if (!param.has("unit")) {
			return errorMsg("-2", "请填写传感器单位!");
		}
		EquipmentSensor equipmentSensor = new EquipmentSensor();
		if (!param.has("type")) {
			param.put("type", 1);
		}
		if (!param.has("sign")) {
			param.put("sign", 0);
		}
		if (!param.has("equipmentIcoId")) {
			param.put("equipmentIcoId", "1");
		}
		equipmentSensor.setName(param.getString("name"));
		equipmentSensor.setType(param.getInt("type"));
		equipmentSensor.setSign(param.getInt("sign"));
		equipmentSensor.setUnit(param.getString("unit"));
		Equipment equipment = new Equipment();
		equipment.setId(param.getLong("equipmentId"));
		equipmentSensor.setEquipment(equipment);
		EquipmentIco equipmentIco = new EquipmentIco();
		equipmentIco.setId(param.getLong("equipmentIcoId"));
		equipmentSensor.setEquipmentIco(equipmentIco);
		equipmentSensor.setEquipmentTypeId(session.getLong("equipmentTypeId"));
		equipmentSensor.setOwnerId(Long.parseLong(userSession.getUserId()));
		if (param.has("sensorParamId")) {
			EquipmentSensorParams sensorParams = new EquipmentSensorParams();
			sensorParams.setId(param.getLong("sensorParamId"));
			equipmentSensor.setEquipmentSensorParams(sensorParams);
		}
		equipmentSensor.setChannel(0);

		equipmentSensorService.save(equipmentSensor);
		return successMsg("0", "添加传感器成功!");
	}

	/**
	 * 根据传感器ID获取编辑信息
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentSensor","method":
	 *            "getEquipmentSensorById"
	 *            ,"session":{"sessionId":"79eaa27d3c9148249260c4110f14b75a"
	 *            ,"equipmentTypeId":"1"},"param":{"equipmentSensorId":"1"}}
	 * @return
	 */
	@RequestMapping(value = "/getEquipmentSensorById", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String getEquipmentSensorById(String requestParams) {
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
		// 会话验证
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		} else if (super.getEquipmentTypeId(session) == null) {
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}

		// 参数验证
		if (!param.has("equipmentSensorId")) {
			return errorMsg("-2", "请选择一个操作对象!");
		}

		EquipmentSensor equipmentSensor = equipmentSensorService.find(param
				.getLong("equipmentSensorId"));
		if (equipmentSensor == null) {
			return errorMsg("-2", "对象不存在，可能已经被删除!");
		}

		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取传感器成功");
		JSONObject json = new JSONObject();
		json.put("id", equipmentSensor.getId());
		json.put("idNumber", equipmentSensor.getIdNumber());
		json.put("name", equipmentSensor.getName());
		json.put("type", equipmentSensor.getType());
		json.put("sign", equipmentSensor.getSign());
		json.put("unit", equipmentSensor.getUnit());
		if (equipmentSensor.getEquipmentIco() != null) {
			json.put("equipmentIcoId", equipmentSensor.getEquipmentIco()
					.getId());
			json.put("equipmentIcoPath", equipmentSensor.getEquipmentIco()
					.getIcoPath());
			json.put("equipmentIcoOnlinePath", equipmentSensor
					.getEquipmentIco().getOnlineIco());
		}
		if (equipmentSensor.getEquipment() != null) {
			json.put("equipmentId", equipmentSensor.getEquipment().getId());
			json.put("equipment", equipmentSensor.getEquipment().getName());
			if(equipmentSensor.getEquipment().getChartsType() != null){
				json.put("chartsId", equipmentSensor.getEquipment().getChartsType().getId());
				json.put("chartsTypes", equipmentSensor.getEquipment().getChartsType().getEchartTypeParams());
				json.put("chartsName", equipmentSensor.getEquipment().getChartsType().getName());
			}
		}
		if (equipmentSensor.getEquipmentIco() != null) {
			json.put("sensorParamId", equipmentSensor
					.getEquipmentSensorParams().getId());
			json.put("sensorParamPamater", equipmentSensor
					.getEquipmentSensorParams().getParameter());
		}

		//获取传感器最近上报
		List<Filter> sensorStateFilters = new ArrayList<Filter>();
		sensorStateFilters.add(Filter.eq("sensor_number", equipmentSensor.getIdNumber()));
		List<ReportEquipmentSensor> reportEquipmentSensors = reportEquipmentSensorService.findListSensor(1, sensorStateFilters);
		
		if(reportEquipmentSensors.size() > 0){
			ReportEquipmentSensor reportEquipmentSensor = reportEquipmentSensors.get(0);
			json.put("sensorValues", reportEquipmentSensor.getSensorValues());
			json.put("sensorTrueValues", reportEquipmentSensor.getSensorTrueValue());
			Date nowTime = DatesUtils.stringToDate(DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
			long min = DatesUtils.compareMin(reportEquipmentSensor.getCreateDate(), nowTime);
			if(min > equipmentSensor.getEquipment().getDuration()){//上报时间大于上报周期
				json.put("sensorState", 0);
			}else{
				json.put("sensorState", 1);
			}
			json.put("sensorReportTime", DatesUtils.dateToString(reportEquipmentSensor.getCreateDate(), "yyyy-MM-dd HH:mm"));
		}else{
			JSONArray jsonSensorValues = new JSONArray();
			JSONObject jsonSensorValue = new JSONObject();
			jsonSensorValue.put("value", "0");
			jsonSensorValues.add(jsonSensorValue);
			json.put("sensorState", 0);
			json.put("sensorValues", jsonSensorValues);
			json.put("sensorTrueValues", "");
			json.put("sensorReportTime", "");
		}
		
		jsonReturn.put("value", json);
		return jsonReturn.toString();
	}

	/**
	 * 更新传感器
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentSensor","method":"update",
	 *            "session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":
	 *            {"equipmentSensorId"
	 *            :"键值","name":"名称","type":"类型","sign":"小数位",
	 *            "unit":"单位","equipmentIcoId"
	 *            :"图标ID","equipmentId":"所属设备ID","sensorParamId":"自定义参数ID"}}
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
		// 会话验证
		if (super.getSession(session) == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		} else if (super.getEquipmentTypeId(session) == null) {
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}

		if (!param.has("equipmentSensorId")) {
			return errorMsg("-2", "请选择一个操作项!");
		}
		EquipmentSensor equipmentSensor = equipmentSensorService.find(param
				.getLong("equipmentSensorId"));
		if (param.has("name")) {
			equipmentSensor.setName(param.getString("name"));
		}
		if (param.has("equipmentId")) {
			Equipment equipment = new Equipment();
			equipment.setId(param.getLong("equipmentId"));
			equipmentSensor.setEquipment(equipment);
		}
		if (param.has("unit")) {
			equipmentSensor.setUnit(param.getString("unit"));
		}
		if (param.has("type")) {
			equipmentSensor.setType(param.getInt("type"));
		}
		if (param.has("sign")) {
			equipmentSensor.setSign(param.getInt("sign"));
		}
		if (param.has("equipmentIcoId")) {
			EquipmentIco equipmentIco = new EquipmentIco();
			equipmentIco.setId(param.getLong("equipmentIcoId"));
			equipmentSensor.setEquipmentIco(equipmentIco);
		}

		if (param.has("sensorParamId")) {
			EquipmentSensorParams sensorParams = new EquipmentSensorParams();
			sensorParams.setId(param.getLong("sensorParamId"));
			equipmentSensor.setEquipmentSensorParams(sensorParams);
		}

		equipmentSensorService.update(equipmentSensor);
		return successMsg("0", "更新传感器成功!");
	}

	/**
	 * 删除传感器
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentSensor","method":"delete",
	 *            "session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param"
	 *            :{"equipmentSensorId":"键值"}}
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
		JSONObject session = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			session = node.getJSONObject("session");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
		// 会话验证
		if (super.getSession(session) == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		} /*else if (super.getEquipmentTypeId(session) == null) {
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}*/

		if (!param.has("equipmentSensorId")) {
			return errorMsg("-2", "请选择一个操作项!");
		}

		equipmentSensorService.delete(param.getLong("equipmentSensorId"));
		return successMsg("0", "删除传感器成功!");
	}

	/**
	 * 根据设备ID获取全部传感器（不分页）
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentSensor","method":
	 *            "sensorList", "session":
	 *            {"sessionId":"会话ID","equipmentTypeId":
	 *            "频道ID"},"param":{"equipmentId":"所属设备ID"}}
	 * @return
	 */
	@RequestMapping(value = "/sensorList", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String sensorList(String requestParams) {
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
		/*// 会话验证
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		} else if (super.getEquipmentTypeId(session) == null) {
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}*/

		// 参数验证
		List<Filter> filters = new ArrayList<Filter>();
		if (!param.has("equipmentId")) {
			return errorMsg("-2", "缺少设备ID!");
		}
		filters.add(Filter.eq("equipment", param.getLong("equipmentId")));
	/*	filters.add(Filter.eq("equipmentTypeId",
				session.getLong("equipmentTypeId")));*/
		List<EquipmentSensor> equipmentSensors = equipmentSensorService
				.findList(null, filters, null);

		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取设备传感器");
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < equipmentSensors.size(); i++) {
			JSONObject json = new JSONObject();
			EquipmentSensor equipmentSensor = equipmentSensors.get(i);
			json.put("id", equipmentSensor.getId());
			json.put("idNumber", equipmentSensor.getIdNumber());
			json.put("name", equipmentSensor.getName());
			json.put("type", equipmentSensor.getType());
			json.put("sign", equipmentSensor.getSign());
			json.put("unit", equipmentSensor.getUnit());
			if (equipmentSensor.getEquipmentIco() != null) {
				json.put("equipmentIcoId", equipmentSensor.getEquipmentIco()
						.getId());
				json.put("equipmentIcoPath", equipmentSensor.getEquipmentIco()
						.getIcoPath());
				json.put("equipmentIcoOnlinePath", equipmentSensor
						.getEquipmentIco().getOnlineIco());
			}
			if (equipmentSensor.getEquipment() != null) {
				json.put("equipmentId", equipmentSensor.getEquipment().getId());
				json.put("equipment", equipmentSensor.getEquipment().getName());
				if(equipmentSensor.getEquipment().getChartsType() != null){
					json.put("chartsId", equipmentSensor.getEquipment().getChartsType().getId());
					json.put("chartsTypes", equipmentSensor.getEquipment().getChartsType().getEchartTypeParams());
					json.put("chartsName", equipmentSensor.getEquipment().getChartsType().getName());
				}
			}

			//获取传感器最近上报
			List<Filter> sensorStateFilters = new ArrayList<Filter>();
			sensorStateFilters.add(Filter.eq("sensor_number", equipmentSensor.getIdNumber()));
			List<ReportEquipmentSensor> reportEquipmentSensors = reportEquipmentSensorService.findListSensor(1, sensorStateFilters);
			
			if(reportEquipmentSensors.size() > 0){
				ReportEquipmentSensor reportEquipmentSensor = reportEquipmentSensors.get(0);
				json.put("sensorValues", reportEquipmentSensor.getSensorValues());
				json.put("sensorTrueValues", reportEquipmentSensor.getSensorTrueValue());
				Date nowTime = DatesUtils.stringToDate(DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
				long min = DatesUtils.compareMin(reportEquipmentSensor.getCreateDate(), nowTime);
				if(min > equipmentSensor.getEquipment().getDuration()){//上报时间大于上报周期
					json.put("sensorState", 0);
				}else{
					json.put("sensorState", 1);
				}
				json.put("sensorReportTime", DatesUtils.dateToString(reportEquipmentSensor.getCreateDate(), "yyyy-MM-dd HH:mm"));
			}else{
				JSONArray jsonSensorValues = new JSONArray();
				JSONObject jsonSensorValue = new JSONObject();
				jsonSensorValue.put("value", "0");
				jsonSensorValues.add(jsonSensorValue);
				json.put("sensorState", 0);
				json.put("sensorValues", jsonSensorValues);
				json.put("sensorTrueValues", "");
				json.put("sensorReportTime", "");
			}

			jsonArray.add(json);
		}
		jsonReturn.put("values", jsonArray);
		return jsonReturn.toString();
	}

}
