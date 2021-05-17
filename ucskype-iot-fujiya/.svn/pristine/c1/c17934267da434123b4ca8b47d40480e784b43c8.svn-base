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
import com.taojin.iot.base.comm.entity.UserSession;
import com.taojin.iot.service.equipment.entity.Equipment;
import com.taojin.iot.service.equipment.entity.EquipmentSensor;
import com.taojin.iot.service.equipment.entity.EquipmentTrigger;
import com.taojin.iot.service.equipment.entity.EquipmentTrigger.Target;
import com.taojin.iot.service.equipment.enums.EquipmentAlarmTypeEnum;
import com.taojin.iot.service.equipment.service.EquipmentTriggerService;

/**
 * Controller-触发器
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午4:02:18
 * author 王杰
 * ============================================================================
 */
@Controller("internalEquipmentTriggerController")
@RequestMapping("/internal/equipment/equipmentTrigger")
public class EquipmentTriggerController extends BaseController{
	@Resource(name="equipmentTriggerServiceImpl")
	private EquipmentTriggerService equipmentTriggerService;
	
	/**
	 * 获取触发器列表
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentTrigger","method":"list","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":{"equipmentId":"设备ID","equipmentSensorId":"传感器ID","state":"状态","pageNumber":"页码","pageSize":"每页记录数"}}
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
		//会话验证
		/*UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		}else if(super.getEquipmentTypeId(session) == null){
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}*/
		
		//参数验证
		if(!param.has("pageNumber")){
			param.put("pageNumber", 1);
		}
				
		if(!param.has("pageSize")){
			param.put("pageSize", 20);
		}
		List<Filter> filters = new ArrayList<Filter>();
		if(param.has("equipmentId")){
			filters.add(Filter.eq("equipment", param.getLong("equipmentId")));
		}
		if(param.has("equipmentSensorId")){
			filters.add(Filter.eq("equipmentSensor", param.getLong("equipmentSensorId")));
		}
		
		if(param.has("state")){
			filters.add(Filter.eq("state", param.getInt("state")));
		}
		
		//分页检索
		Pageable pageable = new Pageable(param.getInt("pageNumber"),param.getInt("pageSize"));
	//	filters.add(Filter.eq("equipmentTypeId", session.getLong("equipmentTypeId")));
	//	filters.add(Filter.eq("ownerId", Long.parseLong(userSession.getUserId())));
		pageable.setFilters(filters);
		Page<EquipmentTrigger> page = equipmentTriggerService.findPage(pageable);
		
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取触发器成功");
		JSONArray jsonArray = new JSONArray();
		for(int i=0;i<page.getContent().size();i++){
			JSONObject json = new JSONObject();
			EquipmentTrigger trigger = page.getContent().get(i);
			json.put("id", trigger.getId());
			if(trigger.getEquipment() != null){
				json.put("equipmentId", trigger.getEquipment().getId());
				json.put("equipmentName", trigger.getEquipment().getName());
			}
			json.put("equipmentSensorId", trigger.getEquipmentSensor().getId());
			json.put("equipmentSensorName", trigger.getEquipmentSensor().getName());
			json.put("equipmentAlarmType", trigger.getEquipmentAlarmTypeEnum().name());
			json.put("upperBoundC", trigger.getUpperBoundC());
			json.put("target", trigger.getTarget().name());
			json.put("targetValue", trigger.getTargetValue());
			json.put("isTransfer", trigger.getIsTransfer());
			json.put("state", trigger.getState());
			json.put("userName", trigger.getUserName());
			json.put("equipmentTypeId", trigger.getEquipmentTypeId());
			json.put("type", trigger.getType());
			
			jsonArray.add(json);
		}
		jsonReturn.put("values", jsonArray);
		jsonReturn = super.getJsonPage(page, jsonReturn);
		return jsonReturn.toString();
	}
	
	/**
	 * 添加触发器
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentTrigger","method":"save","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":{"equipmentId":"设备ID","equipmentSensorId":"传感器ID","equipmentAlarmType":"触发类型":"upperBoundC":{},"target":"报警方式","targetValue":"报警值","isTransfer":"是否转发","state":"触发器状态"}}
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
		//会话验证
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		}/*else if(super.getEquipmentTypeId(session) == null){
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}*/
		
		//参数验证
		if(!param.has("equipmentId")){
			return errorMsg("-2", "请选择设备!");
		}
		if(!param.has("equipmentSensorId")){
			return errorMsg("-2", "请选择设备传感器!");
		}
		if(!param.has("equipmentAlarmType")){
			return errorMsg("-2", "请选择触发条件!");
		}
		if(!param.has("upperBoundC")){
			return errorMsg("-2", "请填写触发值!");
		}
		if(!param.has("target")){
			return errorMsg("-2", "请选择报警方式!");
		}
		if(!param.has("targetValue")){
			return errorMsg("-2", "请选择报警联系人!");
		}
		if(!param.has("isTransfer")){
			return errorMsg("-2", "请选择报警联系人!");
		}
		if(!param.has("state")){
			param.put("state", 1);
		}
		
		EquipmentTrigger equipmentTrigger = new EquipmentTrigger();
		Equipment equipment = new Equipment();
		equipment.setId(param.getLong("equipmentId"));
		equipmentTrigger.setEquipment(equipment);
		EquipmentSensor equipmentSensor = new EquipmentSensor();
		equipmentSensor.setId(param.getLong("equipmentSensorId"));
		equipmentTrigger.setEquipmentSensor(equipmentSensor);
		equipmentTrigger.setUserName(param.getString("userName"));
		equipmentTrigger.setEquipmentAlarmTypeEnum(EquipmentAlarmTypeEnum.valueOf(param.getString("equipmentAlarmType")));
		equipmentTrigger.setUpperBoundC(param.getString("upperBoundC"));
		equipmentTrigger.setTarget(Target.valueOf(param.getString("target")));
		equipmentTrigger.setTargetValue(param.getString("targetValue"));
		equipmentTrigger.setIsTransfer(param.getInt("isTransfer"));
		equipmentTrigger.setState(param.getInt("state"));
	//	equipmentTrigger.setEquipmentTypeId(session.getLong("equipmentTypeId"));
		equipmentTrigger.setOwnerId(Long.parseLong(userSession.getUserId()));
		equipmentTrigger.setType(param.getInt("type"));
		equipmentTriggerService.save(equipmentTrigger);		
		return successMsg("0", "添加设备触发器成功!");
	}
	
	/**
	 * 根据触发器ID获取编辑信息
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentTrigger","method":"getEquipmentTriggerById","session":{"sessionId":"79eaa27d3c9148249260c4110f14b75a","equipmentTypeId":"1"},"param":{"equipmentTriggerId":"1"}}
	 * @return
	 */
	@RequestMapping(value = "/getEquipmentTriggerById", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String getEquipmentTriggerById(String requestParams) {
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
		//会话验证
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		}/*else if(super.getEquipmentTypeId(session) == null){
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}*/
		
		//参数验证
		if(!param.has("equipmentTriggerId")){
			return errorMsg("-2", "请选择一个操作对象!");
		}
		
		EquipmentTrigger trigger = equipmentTriggerService.find(param.getLong("equipmentTriggerId"));
		if(trigger == null){
			return errorMsg("-2", "对象不存在，可能已经被删除!");
		}
		
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取触发器成功");
		JSONObject json = new JSONObject();
		json.put("id", trigger.getId());
		if(trigger.getEquipment() != null){
			json.put("equipmentId", trigger.getEquipment().getId());
			json.put("equipmentName", trigger.getEquipment().getName());
		}
		json.put("equipmentSensorId", trigger.getEquipmentSensor().getId());
		json.put("equipmentSensorName", trigger.getEquipmentSensor().getName());
		json.put("equipmentAlarmType", trigger.getEquipmentAlarmTypeEnum().name());
		json.put("upperBoundC", trigger.getUpperBoundC());
		json.put("target", trigger.getTarget().name());
		json.put("userName", trigger.getUserName());
		json.put("targetValue", trigger.getTargetValue());
		json.put("isTransfer", trigger.getIsTransfer());
		json.put("state", trigger.getState());
		json.put("equipmentTypeId", trigger.getEquipmentTypeId());
		json.put("type", trigger.getType());
		
		jsonReturn.put("value", json);
		return jsonReturn.toString();
	}
	
	/**
	 * 更新触发器
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentTrigger","method":"update","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":{"equipmentTriggerId":"触发器ID","equipmentId":"设备ID","equipmentSensorId":"传感器ID","equipmentAlarmType":"触发类型":"upperBoundC":{},"target":"报警方式","targetValue":"报警值","isTransfer":"是否转发","state":"触发器状态"}}
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
		//会话验证
		if (super.getSession(session) == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		}/*else if(super.getEquipmentTypeId(session) == null){
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}*/
		
		//参数验证
		if(!param.has("equipmentTriggerId")){
			return errorMsg("-2", "请选择一个操作项!");
		}
		EquipmentTrigger equipmentTrigger = equipmentTriggerService.find(param.getLong("equipmentTriggerId"));
		if(param.has("equipmentId")){
			Equipment equipment = new Equipment();
			equipment.setId(param.getLong("equipmentId"));
			equipmentTrigger.setEquipment(equipment);
		}
		if(param.has("equipmentSensorId")){
			EquipmentSensor equipmentSensor = new EquipmentSensor();
			equipmentSensor.setId(param.getLong("equipmentSensorId"));
			equipmentTrigger.setEquipmentSensor(equipmentSensor);
		}
		if(param.has("equipmentAlarmType")){
			equipmentTrigger.setEquipmentAlarmTypeEnum(EquipmentAlarmTypeEnum.valueOf(param.getString("equipmentAlarmType")));
		}
		if(param.has("upperBoundC")){
			equipmentTrigger.setUpperBoundC(param.getString("upperBoundC"));
		}
		if(param.has("target")){
			equipmentTrigger.setTarget(Target.valueOf(param.getString("target")));
		}
		if(param.has("targetValue")){
			equipmentTrigger.setTargetValue(param.getString("targetValue"));
		}
		if(param.has("isTransfer")){
			equipmentTrigger.setIsTransfer(param.getInt("isTransfer"));
		}
		if(param.has("state")){
			equipmentTrigger.setState(param.getInt("state"));
		}
		if(param.has("userName")){
			equipmentTrigger.setUserName(param.getString("userName"));
		}
		if(param.has("type")){
			equipmentTrigger.setType(param.getInt("type"));
		}
		equipmentTriggerService.update(equipmentTrigger);
		return successMsg("0", "更新设备触发器成功!");
	}
	
	/**
	 * 删除触发器
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentTrigger","method":"delete","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":{"equipmentTriggerId":"触发器ID"}}
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
		//会话验证
		if (super.getSession(session) == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		}/*else if(super.getEquipmentTypeId(session) == null){
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}*/
		
		//参数验证
		if(!param.has("equipmentTriggerId")){
			return errorMsg("-2", "请选择一个操作项!");
		}
		
		equipmentTriggerService.delete(param.getLong("equipmentTriggerId"));
		return successMsg("0", "删除触发器成功!");
	}
	
}
