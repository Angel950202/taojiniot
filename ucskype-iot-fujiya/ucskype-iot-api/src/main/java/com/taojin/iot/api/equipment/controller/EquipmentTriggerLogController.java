package com.taojin.iot.api.equipment.controller;

import java.sql.PseudoColumnUsage;
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
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.service.equipment.entity.EquipmentTriggerLog;
import com.taojin.iot.service.equipment.service.EquipmentTriggerLogService;

/**
 * Controller-触发器日志
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ---------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午7:49:24
 * author 王杰
 * ============================================================================
 */
@Controller("internalEquipmentTriggerLogController")
@RequestMapping("/internal/equipment/equipmentTriggerLog")
public class EquipmentTriggerLogController extends BaseController{
	@Resource(name="equipmentTriggerLogServiceImpl")
	private EquipmentTriggerLogService equipmentTriggerLogService;
	
	/**
	 * 获取触发器日志列表
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentTriggerLog","method":"list","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":{"equipmentTriggerId":"触发器ID","target":"触发类型","sendState":"发送状态","pageNumber":"页码","pageSize":"每页记录数"}}
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
		//生产线查询参数
		if(param.has("productionLine")){
			String line = param.get("productionLine").toString();
			if (line != null && !"".equals(line) && !"null".equals(line) && line.length() > 0) {
				filters.add(Filter.eq("productionLine",line));
			}
		}
		//工作站查询参数
		if (param.has("workStationName")) {
			String work = param.get("workStationName").toString();
			if (work != null && !"".equals(param.get(work)) && !"null".equals(work) && work.length() > 0) {
				filters.add(Filter.eq("workStationName",work));
			}
		}
		/**
		 * 报警时间查询
		 * 1.只有开始时间
		 * 2.只有结束时间
		 * 3.开始时间都有
		 */
		
//		String startTime = null;
//		String endTime = null;
//		if (param.has("startTime") && !param.has("endTime")) {
//			filters.add(Filter.between("createDate", param.get("startTime"),param.get("endTime")));
//		}
//		if (!param.has("startTime") && param.has("endTime")) {
//			filters.add(Filter.between("createDate", param.get("endTime"),null));
//		}
//		if (param.has("startTime") && param.has("endTime") && 
//				!"".equals(param.get("startTime")) && "".equals(param.get("endTime"))) {
//			filters.add(Filter.between("createDate", param.get("startTime"),param.get("startTime")));
//		}
//		filters.add(Filter.between_two("createDate", param.get("startTime"),param.get("startTime")));
		
		if (param.has("startTime") && param.has("endTime") && 
				!"".equals(param.get("startTime")) && "".equals(param.get("endTime"))) {
			filters.add(Filter.between("createDate", param.get("startTime"),param.get("startTime")));
		}
		
		//报警类型
		if (param.has("equipmentSensorId")) {
			String type = param.get("equipmentSensorId").toString();
			if (type != null && !"".equals(param.get(type)) && !"null".equals(type) && type.length() > 0) {
				filters.add(Filter.eq("type",param.get("equipmentSensorId")));
			}
		}
		
		//分页检索
		Pageable pageable = new Pageable(param.getInt("pageNumber"),param.getInt("pageSize"));
		pageable.setFilters(filters);
		
		Page<EquipmentTriggerLog> page = equipmentTriggerLogService.findPage(pageable);
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取触发器日志成功");
		JSONArray jsonArray = new JSONArray();
		for(int i=0;i<page.getContent().size();i++){
			JSONObject json = new JSONObject();
			EquipmentTriggerLog triggerLog = page.getContent().get(i);			
			json.put("id", triggerLog.getId());

			json.put("productionModel",triggerLog.getProductionModel());
	
			json.put("productionLine",triggerLog.getProductionLine());

			json.put("workStationName",triggerLog.getWorkStationName());

			json.put("type",triggerLog.getType());

			json.put("createDate", DatesUtils.dateToString(triggerLog.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
			json.put("triggerValue", triggerLog.getTriggerValue());
//			json.put("target", triggerLog.getTarget().name() == null ?"" : triggerLog.getTarget().name());
			json.put("sendState", triggerLog.getSendState());
			json.put("faileReason", triggerLog.getFaileReason());
			json.put("triggerContent", triggerLog.getTriggerContent());
			if(triggerLog.getEquipmentTrigger() != null){
				json.put("equipmentName", triggerLog.getEquipmentTrigger().getEquipment().getName());
				json.put("sensorName", triggerLog.getEquipmentTrigger().getEquipmentSensor().getName());
			}
			json.put("owerId", triggerLog.getOwnerId());
			jsonArray.add(json);
		}
		jsonReturn.put("values", jsonArray);
		jsonReturn = super.getJsonPage(page, jsonReturn);
		return jsonReturn.toString();
	}

}
