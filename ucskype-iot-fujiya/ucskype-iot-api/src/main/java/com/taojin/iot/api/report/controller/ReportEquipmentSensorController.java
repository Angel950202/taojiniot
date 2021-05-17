package com.taojin.iot.api.report.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.taojin.iot.BaseController;
import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.Page;
import com.taojin.iot.base.comm.Pageable;
import com.taojin.iot.base.comm.entity.UserSession;
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.service.equipment.entity.DeviceReport;
import com.taojin.iot.service.equipment.service.DeviceReportService;
import com.taojin.iot.service.equipment.service.EquipmentService;
import com.taojin.iot.service.report.entity.ReportEquipmentSensor;
import com.taojin.iot.service.report.entity.Series;
import com.taojin.iot.service.report.service.ReportEquipmentSensorService;
import com.taojin.iot.service.report.service.ReportRealTimeSensorService;

/**
 * Controller-设备传感器记录 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午4:05:34 author 王杰
 * ============================================================================
 */
@Controller("internalReportEquipmentSensorController")
@RequestMapping("/internal/report/reportEquipmentSensor")
public class ReportEquipmentSensorController extends BaseController {
	@Resource(name = "reportEquipmentSensorServiceImpl")
	private ReportEquipmentSensorService reportEquipmentSensorService;
	@Resource(name = "reportRealTimeSensorServiceImpl")
	private ReportRealTimeSensorService reportRealTimeSensorService;
	@Resource(name = "equipmentServiceImpl")
	private EquipmentService equipmentService;
	
	@Resource(name = "deviceReportServiceImpl")
	private DeviceReportService deviceReportService;
	
	
	/**
	 * 获取设备列表
	 * 
	 * @param requestParams
	 *            requestParams={"action":"reportEquipmentSensor","method":
	 *            "listByPage","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"}
	 *            ,"param":{"startTime":"开始时间","endTime":"结束时间",
	 *            "pageNumber":"页码","pageSize":"每页记录数","sensorId":"传感器ID"}}
	 * @return
	 */
	@RequestMapping(value = "/listByPage", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String listByPage(String requestParams) {
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
		if(!param.has("sensorId")){
			return errorMsg("-2", "缺少传感器ID!");
		}
		List<Filter> filters = new ArrayList<Filter>();

		filters.add(Filter.eq("sensor_id",param.getString("sensorId")));

		if (!param.has("startTime") || !param.has("endTime")) {
			param.put("startTime", DatesUtils.getStringToday("yyyy-MM-dd"));
			param.put("endTime", DatesUtils.getStringToday("yyyy-MM-dd"));
		}

		// 分页检索
		Pageable pageable = new Pageable(param.getInt("pageNumber"),
				param.getInt("pageSize"));
//		filters.add(Filter.eq("equipment_type_id",
//				session.getLong("equipmentTypeId")));
//		filters.add(Filter.eq("owner_id",
//				Long.parseLong(userSession.getUserId())));
		pageable.setFilters(filters);
		Page<ReportEquipmentSensor> page = reportEquipmentSensorService
				.findPageSensor(param.getString("startTime"),
						param.getString("endTime"), pageable);
		
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取传感器记录成功");
		JSONArray jsonArray = new JSONArray();
		for(int i=0;i<page.getContent().size();i++){
			JSONObject json = new JSONObject();
			ReportEquipmentSensor reportEquipmentSensor = page.getContent().get(i);
			json.put("id", reportEquipmentSensor.getId());
			json.put("sensorId", reportEquipmentSensor.getSensorId());
			json.put("sensorNumber", reportEquipmentSensor.getSensorNumber());
			json.put("sensorValues", reportEquipmentSensor.getSensorValues());
			json.put("sensorTrueValue", reportEquipmentSensor.getSensorTrueValue());
			json.put("dateTime", reportEquipmentSensor.getDateTime());
			json.put("createDate", DatesUtils.dateToString(reportEquipmentSensor.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
			json.put("modifyDate", DatesUtils.dateToString(reportEquipmentSensor.getModifyDate(), "yyyy-MM-dd HH:mm:ss"));
			String[] dates = DatesUtils.dateToString(reportEquipmentSensor.getModifyDate(), "yyyy-MM-dd").split("\\-");
			json.put("year", dates[0]);
			json.put("month", dates[1]);
			json.put("day", dates[2]);
			dates = DatesUtils.dateToString(reportEquipmentSensor.getModifyDate(), "HH:mm:ss").split("\\:");
			json.put("hour", dates[0]);
			json.put("minute", dates[1]);
			json.put("ownerId", reportEquipmentSensor.getOwnerId());
			
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
	 *            requestParams={"action":"reportEquipmentSensor","method":
	 *            "listNoPage","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"}
	 *            ,"param":{"dateTime":"日期","sensorId":"传感器ID"}}
	 * @return
	 */
	@RequestMapping(value = "/listNoPage", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String listNoPage(String requestParams) {
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
		if(!param.has("sensorId")){
			return errorMsg("-2", "缺少传感器ID!");
		}
		List<Filter> filters = new ArrayList<Filter>();
		 Calendar nowTime = Calendar.getInstance();
		 nowTime.add(Calendar.MINUTE,-5);
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date beforeD = nowTime.getTime();
		 String time = sdf.format(beforeD);
		filters.add(Filter.ge("modify_date", time));
		filters.add(Filter.eq("sensor_id", param.getLong("sensorId")));

		filters.add(Filter.eq("equipment_type_id",
				session.getLong("equipmentTypeId")));
//		filters.add(Filter.eq("owner_id",
//				Long.parseLong(userSession.getUserId())));
		List<ReportEquipmentSensor> list = reportEquipmentSensorService.findListSensor(null, filters);
		
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取传感器记录成功");
		JSONArray jsonArray = new JSONArray();
		for(int i=0;i<list.size();i++){
			JSONObject json = new JSONObject();
			ReportEquipmentSensor reportEquipmentSensor = list.get(i);
			json.put("id", reportEquipmentSensor.getId());
			json.put("sensorId", reportEquipmentSensor.getSensorId());
			json.put("sensorNumber", reportEquipmentSensor.getSensorNumber());
			json.put("sensorValues", reportEquipmentSensor.getSensorValues());
			json.put("sensorTrueValue", reportEquipmentSensor.getSensorTrueValue());
			json.put("dateTime", reportEquipmentSensor.getDateTime());
			json.put("createDate", DatesUtils.dateToString(reportEquipmentSensor.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
			json.put("modifyDate", DatesUtils.dateToString(reportEquipmentSensor.getModifyDate(), "yyyy-MM-dd HH:mm:ss"));
			String[] dates = DatesUtils.dateToString(reportEquipmentSensor.getModifyDate(), "yyyy-MM-dd").split("\\-");
			json.put("year", dates[0]);
			json.put("month", dates[1]);
			json.put("day", dates[2]);
			dates = DatesUtils.dateToString(reportEquipmentSensor.getModifyDate(), "HH:mm:ss").split("\\:");
			json.put("hour", dates[0]);
			json.put("minute", dates[1]);
			json.put("ownerId", reportEquipmentSensor.getOwnerId());
			
			jsonArray.add(json);
		}
		jsonReturn.put("values", jsonArray);
		return jsonReturn.toString();
	}
	 
		@RequestMapping(value = "/realTimeReport", produces = "application/josn; charset=utf-8")
		@ResponseBody
		public String addlog(String requestParams) {
			JSONObject node = new JSONObject();
			JSONObject param = new JSONObject();
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			HashMap<String, Object> map = new HashMap<String, Object>();						
/*			if (StringUtils.isBlank(requestParams)) {
				return errorMsg("-31", "获取默认参数失败");
			}
			JSONObject node = new JSONObject();
			JSONObject param = new JSONObject();
			JSONObject session = new JSONObject();
			try {
				
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
			}*/				
			List<Series> Serie = reportRealTimeSensorService.ReportList(param.getInt("flag"), param.getLong("id"));
			List<String> jsonReturn2=new ArrayList<String>();
			List<HashMap<String,List<String>>> jsonReturn3 = new ArrayList<HashMap<String,List<String>>>();
			List<List<Object>> jsonReturn4 = new ArrayList<List<Object>>();
			ArrayList<String> time = new ArrayList<>();
			int y = 0;
			int t = 0;
			int f = 0;
			for(Series s : Serie) {
				HashMap<String, List<String>> map2 = new HashMap<String, List<String>>();
				jsonReturn2.add(s.getName());
				map2.put(s.getName(), s.getData());
				jsonReturn3.add(map2);
				if(t<s.getTime().size()){
					t=s.getTime().size();
				}
				if(s.getTime().size()==t){
					map.put("date",s.getTime());
					time=s.getTime();
				}				
				if(y<s.getData().size()||y<10){
					y=s.getData().size();
				}
			}
			map.put("series",Serie);			
			map.put("titleData",jsonReturn2);
			for (int z = 0; z < y; z++) {
				List<Object> jsonReturn5 = new ArrayList<Object>();
			for (int x = 0; x < jsonReturn2.size(); x++) {									
					if(jsonReturn3.get(x).get(jsonReturn2.get(x)).size()>z){
						jsonReturn5.add(jsonReturn3.get(x).get(jsonReturn2.get(x)).get(z));
					}else{
						jsonReturn5.add("");
					}				
				}
			if(f<t){
				jsonReturn5.add(time.get(f));
				f++;
			}		
			jsonReturn4.add(jsonReturn5);
			}
			
			map.put("table",jsonReturn4);
			return JSON.toJSONString(map);
		}
		
		/**
		 * 设备运行报表
		 * 
		 */
		@RequestMapping(value = "/equipmentReport", produces = "application/josn; charset=utf-8")
		@ResponseBody
		public String workReportBad(String requestParams) {
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> map2 = new HashMap<>();
			Map<String, Object> map3 = new HashMap<>();
			if (StringUtils.isBlank(requestParams)) {
				map2.put("-31", "获取默认参数失败");
				return JSON.toJSONString(map2);
			}
			JSONObject node;
			JSONObject param;
			try {
				node = JSONObject.fromObject(requestParams);
				param = node.getJSONObject("param");
			} catch (Exception e) {
				map2.put("-1", "参数解析错误!");
				return JSON.toJSONString(map2);
			}
			List<Filter> filters = new ArrayList<>();
//			if (param.has("startTime") && param.has("endTime") && !param.getString("startTime").equals("") && !param.getString("endTime").equals("")) {
//				filters.add(Filter.between("createDate", param.get("startTime")+" 00:00:00",param.get("endTime")+" 23:59:59"));
//			} else {
//				String startTime = null;
//				String endTime = null;
//				if (param.has("flag")) {
//					if (param.get("flag").equals("y")) {
//						startTime = DatesUtils.getYearStart() + " 00:00:00";
//						endTime = DatesUtils.getYearEnd() + " 23:59:59";
//					} else if (param.get("flag").equals("m")) {
//						startTime = DatesUtils.getMonthStart() + " 00:00:00";
//						endTime = DatesUtils.getMonthEnd() + " 23:59:59";
//					} else if (param.get("flag").equals("q")) {
//						startTime = DatesUtils.getCurrQuarter(DatesUtils
//								.getQuarter())[0] + " 00:00:00";
//						endTime = DatesUtils
//								.getCurrQuarter(DatesUtils.getQuarter())[1] + " 23:59:59";
//					} else if (param.get("flag").equals("w")) {
//						startTime = DatesUtils.getWeekStart() + " 00:00:00";
//						endTime = DatesUtils.getWeekEnd() + " 23:59:59";
//					} else {
//						startTime = DatesUtils.getToday() + " 00:00:00";
//						endTime = DatesUtils.getToday() + " 23:59:59";
//					}
//					filters.add(Filter.between("createDate", startTime, endTime));
//				}
//			}
			if (!param.has("pageNumber")) {
				param.put("pageNumber", 1);
			}
			if (!param.has("pageSize")) {
				param.put("pageSize", 20);
			}
			//工站查询
			if (param.has("equipmentName")) {
				String work = param.get("equipmentName").toString();
				if (work != null && !"".equals(param.get(work)) && !"null".equals(work) && work.length() > 0) {
					filters.add(Filter.eq("workStationName",param.getString("equipmentName")));
				}
			}
			//产线查询
			if (param.has("productionLine")) {
				String line = param.get("productionLine").toString();
				if (line != null && !"".equals(line) && !"null".equals(line) && line.length() > 0) {
					filters.add(Filter.eq("lineName",param.getString("productionLine")));
				}
			}
			//时间查询
			if (param.has("startTime") || param.has("endTime")) {
				filters.add(Filter.between_two("createDate", param.getString("startTime"),param.getString("endTime")));
			}
			Pageable pageable = new Pageable(param.getInt("pageNumber"),param.getInt("pageSize"));
			pageable.setFilters(filters);
			Page<DeviceReport> page = deviceReportService.findPage(pageable);
			JSONObject jsonReturn = new JSONObject();
			jsonReturn.put("errcode", "0");
			jsonReturn.put("errmsg", "获取设备成功");
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < page.getContent().size(); i++) {
				JSONObject json = new JSONObject();
				DeviceReport deviceReport = page.getContent().get(i);
				json.put("id", deviceReport.getId());
				json.put("reportTime", DatesUtils.dateToString(deviceReport.getCreateDate(), "yyyy-MM-dd"));
				json.put("productionLine", deviceReport.getLineName());
				json.put("equipmentName", deviceReport.getWorkStationName());
				json.put("bootUpTime", deviceReport.getBootUpTime());
				json.put("runningTime", deviceReport.getRunningTime());
				json.put("failureTime", deviceReport.getFailureTime());
				json.put("deviceTrs", deviceReport.getDeviceTrs());
				jsonArray.add(json);
			}
//			List<DeviceReport> reports = deviceReportService.findList(null, filters, null);
//			List<Map<String, Object>> list = new ArrayList<>();
//			for(DeviceReport deviceReport : reports){
//				Map<String, Object> map1 = new HashMap<>();
//				map1.put("reportTime",DatesUtils.dateToString(deviceReport.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
//				map1.put("productionLine",deviceReport.getLineName());
//				map1.put("equipmentName",deviceReport.getWorkStationName());
//				map1.put("bootUpTime",deviceReport.getBootUpTime());
//				map1.put("runningTime",deviceReport.getRunningTime());
//				map1.put("failureTime",deviceReport.getFailureTime());
//				map1.put("deviceTrs",deviceReport.getDeviceTrs());
//				list.add(map1);	
//			}
//			map2.put("code", 0);			
//			map2.put("msg", "设备报表获取成功");
//			map2.put("values",list);
			jsonReturn.put("values", jsonArray);
			jsonReturn = super.getJsonPage(page, jsonReturn);
			return jsonReturn.toString();
		} 
}

