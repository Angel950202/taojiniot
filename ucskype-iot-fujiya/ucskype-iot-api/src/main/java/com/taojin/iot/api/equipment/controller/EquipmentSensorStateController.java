package com.taojin.iot.api.equipment.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taojin.iot.BaseController;
import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.entity.UserSession;
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.service.equipment.entity.EquipmentSensor;
import com.taojin.iot.service.equipment.service.EquipmentSensorService;
import com.taojin.iot.service.equipment.service.EquipmentSensorStateService;
import com.taojin.iot.service.report.entity.ReportEquipmentSensor;
import com.taojin.iot.service.report.service.ReportEquipmentSensorService;
/**
 * Controller-传感器上报状态
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午3:47:55
 * author 王杰
 * ============================================================================
 */
@Controller("internalEquipmentSensorStateController")
@RequestMapping("/internal/report/equipmentSensorState")
public class EquipmentSensorStateController extends BaseController{
	@Resource(name="equipmentSensorStateServiceImpl")
	private EquipmentSensorStateService equipmentSensorStateService;
	@Resource(name = "reportEquipmentSensorServiceImpl")
	private ReportEquipmentSensorService reportEquipmentSensorService;
	@Resource
	private EquipmentSensorService equipmentSensorService;
	
	/**
	 * 根据传感器编号获取状态信息
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentSensor","method":"getEquipmentSensorStateByIdnumber","session":{"sessionId":"79eaa27d3c9148249260c4110f14b75a","equipmentTypeId":"1"},"param":{"idNumber":"sdfsfsfsdfsdfsd"}}
	 * @return
	 */
	@RequestMapping(value = "/getEquipmentSensorStateByIdnumber", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String getEquipmentSensorStateByIdnumber(String requestParams) {
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
		if(!param.has("idNumber")){
			return errorMsg("-2", "请选择一个操作对象!");
		}
		
		EquipmentSensor sensor = equipmentSensorService.getByParam("idNumber", param.getString("idNumber"));
		if(sensor == null){
			return errorMsg("-2", "传感器不存在，可能已经被删除!");
		}
		
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(Filter.eq("sensor_number", param.getString("idNumber")));
		List<ReportEquipmentSensor> reportEquipmentSensors = reportEquipmentSensorService.findListSensor(1, filters);
		if(reportEquipmentSensors.size() <= 0){
			return errorMsg("-3", "未收到任何上报信息!");
		}
		ReportEquipmentSensor reportEquipmentSensor = reportEquipmentSensors.get(0);
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "拉取上报信息成功!");
		JSONObject json = new JSONObject();
		json.put("id", reportEquipmentSensor.getId());
		json.put("idNumber", reportEquipmentSensor.getSensorNumber());
		json.put("values", reportEquipmentSensor.getSensorValues());
		json.put("trueValue", reportEquipmentSensor.getSensorTrueValue());
		Date nowTime = DatesUtils.stringToDate(DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
		long min = DatesUtils.compareMin(reportEquipmentSensor.getCreateDate(), nowTime);
		if(min > sensor.getEquipment().getDuration()){//上报时间大于上报周期
			json.put("state", 0);
		}else{
			json.put("state", 1);
		}
		
		EquipmentSensor equipmentSensor = equipmentSensorService.find(reportEquipmentSensor.getSensorId());
		if(equipmentSensor != null){
			if(equipmentSensor.getEquipment() != null){
				json.put("equipmentName", equipmentSensor.getEquipment().getName());
			}else{
				json.put("equipmentName", " ");
			}
			
			json.put("sensorName", equipmentSensor.getName());
			json.put("sensorType", equipmentSensor.getType());
		}else{
			json.put("equipmentName", " ");
			json.put("sensorName", " ");
			json.put("sensorType", " ");
		}
		
		json.put("modifyDate", DatesUtils.dateToString(reportEquipmentSensor.getModifyDate(), "yyyy-MM-dd HH:mm"));
		
		jsonReturn.put("value", json);
		return jsonReturn.toString();
	}

}
