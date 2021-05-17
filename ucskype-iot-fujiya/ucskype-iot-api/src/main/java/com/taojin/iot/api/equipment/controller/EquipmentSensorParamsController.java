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
import com.taojin.iot.service.equipment.entity.EquipmentSensorParams;
import com.taojin.iot.service.equipment.service.EquipmentSensorParamsService;

/**
 * Controller-设备传感器自定义字段
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午4:34:20
 * author 王杰
 * ============================================================================
 */
@Controller("internalEquipmentSensorParamsController")
@RequestMapping("/internal/equipment/equipmentSensorParams")
public class EquipmentSensorParamsController extends BaseController{
	@Resource(name="equipmentSensorParamsServiceImpl")
	private EquipmentSensorParamsService equipmentSensorParamsService;
	
	/**
	 * 添加
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentSensorParams","method":"save","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":{"name":"名称","description":"描述","parameter":[{"name":"值1","isShow":"0"},{"name":"值2","isShow":"1"}]}}
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
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		}else if(super.getEquipmentTypeId(session) == null){
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}
		
		//参数验证
		if(!param.has("name")){
			return errorMsg("-2", "请写一个名称!");
		}
		if(!param.has("parameter")){
			return errorMsg("-2", "请添加自定义参数!");
		}
		
		try{
			param.getJSONArray("parameter");
		}catch(Exception parameter){
			return errorMsg("-2", "自定义参数格式不合法!");
		}
		
		if(equipmentSensorParamsService.count(Filter.eq("name", param.getString("name"))) > 0){
			return errorMsg("-2", "名称已经存在啦!");
		}
		
		EquipmentSensorParams sensorParams = new EquipmentSensorParams();
		sensorParams.setName(param.getString("name"));
		sensorParams.setDescription(param.optString("description"));
		sensorParams.setParameter(param.getJSONArray("parameter").toString());
		sensorParams.setOwnerId(Long.parseLong(userSession.getUserId()));
		
		equipmentSensorParamsService.save(sensorParams);
		
		return successMsg("0", "添加传感器自定义参数成功!");
	}
	
	/**
	 * 修改
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentSensorParams","method":"update","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":{"sensorParamsId":"主键","name":"名称","description":"描述","parameter":[{"name":"值1","isShow":"0"},{"name":"值2","isShow":"1"}]}}
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
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		}else if(super.getEquipmentTypeId(session) == null){
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}
		
		//参数验证
		if(!param.has("sensorParamsId")){
			return errorMsg("-2", "参数有误!");
		}
		
		EquipmentSensorParams sensorParams = equipmentSensorParamsService.find(param.getLong("sensorParamsId"));
		
		if(param.has("name")){
			if(equipmentSensorParamsService.count(Filter.eq("name", param.getString("name")),Filter.ne("name", sensorParams.getName())) > 0){
				return errorMsg("-2", "名称已经存在啦!");
			}
			sensorParams.setName(param.getString("name"));
		}
		if(param.has("description")){
			sensorParams.setDescription(param.optString("description"));
		}
		if(param.has("parameter")){
			try{
				param.getJSONArray("parameter");
			}catch(Exception parameter){
				return errorMsg("-2", "自定义参数格式不合法!");
			}
			sensorParams.setParameter(param.getJSONArray("parameter").toString());
		}
		
		equipmentSensorParamsService.update(sensorParams);
		
		return successMsg("0", "修改传感器自定义参数成功!");
	}
	
	/**
	 * 删除
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentSensorParams","method":"delete","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":{"sensorParamsId":"主键"}}
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
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		}else if(super.getEquipmentTypeId(session) == null){
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}
		
		//参数验证
		if(!param.has("sensorParamsId")){
			return errorMsg("-2", "参数有误!");
		}
		
		equipmentSensorParamsService.delete(param.getLong("sensorParamsId"));
		return successMsg("0", "删除传感器自定义参数成功!");
	}
	
	/**
	 * 获取设备传感器参数列表
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentSensorParams","method":"list","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":{"name":"名称","description":"描述",
	 *           	"pageNumber":"页码","pageSize":"每页记录数"}}
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
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		}/*else if(super.getEquipmentTypeId(session) == null){
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
		
		if(param.has("name")){
			filters.add(Filter.like("name", "%"+param.getString("name")+"%"));
		}
		if(param.has("description")){
			filters.add(Filter.like("description", "%"+param.getString("description")+"%"));
		}
		
		//分页检索
		Pageable pageable = new Pageable(param.getInt("pageNumber"),param.getInt("pageSize"));
		filters.add(Filter.eq("ownerId", Long.parseLong(userSession.getUserId())));
		pageable.setFilters(filters);
		
		Page<EquipmentSensorParams> page = equipmentSensorParamsService.findPage(pageable);
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取数据成功");
		JSONArray jsonArray = new JSONArray();
		for(int i=0;i<page.getContent().size();i++){
			JSONObject json = new JSONObject();
			EquipmentSensorParams sensorParams = page.getContent().get(i);
			
			json.put("id", sensorParams.getId());
			json.put("name", sensorParams.getName());
			json.put("ownerId", sensorParams.getOwnerId());
			json.put("description", sensorParams.getDescription());
			json.put("parameter", sensorParams.getParameter());
			
			jsonArray.add(json);
		}
		jsonReturn.put("values", jsonArray);
		jsonReturn = super.getJsonPage(page, jsonReturn);
		return jsonReturn.toString();
	}
	
	/**
	 * 获取设备列表
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentSensorParams","method":"getSensorParamsById","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":{"sensorParamsId":"主键"}}
	 * @return
	 */
	@RequestMapping(value = "/getSensorParamsById", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String getSensorParamsById(String requestParams) {
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
		}else if(super.getEquipmentTypeId(session) == null){
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}
		
		//参数验证
		if(!param.has("sensorParamsId")){
			return errorMsg("-1", "参数有误!");
		}
		
		EquipmentSensorParams sensorParams = equipmentSensorParamsService.find(param.getLong("sensorParamsId"));
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取数据成功");
		JSONObject json = new JSONObject();
		json.put("id", sensorParams.getId());
		json.put("name", sensorParams.getName());
		json.put("ownerId", sensorParams.getOwnerId());
		json.put("description", sensorParams.getDescription());
		json.put("parameter", sensorParams.getParameter());
			
		jsonReturn.put("value", json);
		return jsonReturn.toString();
	}

}
