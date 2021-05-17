package com.taojin.iot.api.equipment.controller;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taojin.iot.BaseController;
import com.taojin.iot.base.comm.Page;
import com.taojin.iot.base.comm.Pageable;
import com.taojin.iot.service.equipment.entity.EquipmentFaultType;
import com.taojin.iot.service.equipment.entity.EquipmentType;
import com.taojin.iot.service.equipment.service.EquipmentFaultTypeService;

/**
 * Controller-设备类型 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午9:27:48 author 王杰
 * ============================================================================
 */
@Controller("internalEquipmentFaultTypeController")
@RequestMapping("/internal/equipment/equipmentFaultType")
public class EquipmentFaultTypeController extends BaseController {
	
	@Resource(name = "equipmentFaultTypeServiceImpl")
	private EquipmentFaultTypeService equipmentFaultTypeService;

	/**
	 * 获取设备故障列表
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentFaultType","method":"list",
	 *            "session":{"sessionId":"会话ID","equipmentTypeId":"频道ID"}}
	 * @return
	 */
	@RequestMapping(value = "/list", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String list(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node = new JSONObject();
		JSONObject session = new JSONObject();
		JSONObject param = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			session = node.getJSONObject("session");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
		if (!param.has("pageNumber")) {
			param.put("pageNumber", 1);
		}
		if (!param.has("pageSize")) {
			param.put("pageSize", 200);
		}
		if (super.getSession(session) == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		}
		
		return equipmentFaultTypeService.findEquipmentFaultTypeList(param).toString();
	}
	
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
			return errorMsg("-2", "缺少故障类型名称!");
		}
		EquipmentFaultType equipmentFaultType = new EquipmentFaultType();
		equipmentFaultType.setName(param.getString("name"));
		equipmentFaultTypeService.save(equipmentFaultType);
		
		return successMsg("0", "添加故障类型成功!");
	}
	
	
	@RequestMapping(value = "/update", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String update(String requestParams) {
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
			return errorMsg("-2", "缺少故障类型名称!");
		}
		if (!param.has("id")) {
			return errorMsg("-2", "缺少故障类型ID!");
		}
		EquipmentFaultType equipmentFaultType = com.alibaba.fastjson.JSONObject.parseObject(
				param.toString(), EquipmentFaultType.class);
		equipmentFaultTypeService.update(equipmentFaultType);
		
		return successMsg("0", "更新故障类型成功!");
	}
	
	/**
	 * 删除设备故障类型
	 * @param requestParams
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
		if (!param.has("id")) {
			return errorMsg("-2", "缺少设备故障类型ID!");
		}
		EquipmentFaultType equipmentType = equipmentFaultTypeService.find(param
				.getLong("id"));
		equipmentType.setIsDel(true);
		equipmentFaultTypeService.update(equipmentType);
		return successMsg("0", "删除故障类型成功!");
	}
	
	/**
	 * 查询设备故障类别
	 * @param requestParams
	 * @return
	 */
	@RequestMapping(value = "/query", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String query(String requestParams) {
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

		if (!param.has("id")) {
			return errorMsg("-2", "缺少设备故障类型ID!");
		}
		
		EquipmentFaultType equipmentFaultType = equipmentFaultTypeService.find(param.getLong("id"));
		if (equipmentFaultType == null) {
			return errorMsg("-2", "对象不存在，可能已经被删除!");
		}
		JSONObject json = JSONObject.fromObject(equipmentFaultType);//将java对象转换为json对象
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取设备故障类型成功");
		jsonReturn.put("value", json);
		return jsonReturn.toString();
	}

}
