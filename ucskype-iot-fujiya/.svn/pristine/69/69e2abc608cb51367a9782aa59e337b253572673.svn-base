package com.taojin.iot.service.equipment.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.service.equipment.dao.EquipmentSensorStateDao;
import com.taojin.iot.service.equipment.entity.EquipmentSensor;
import com.taojin.iot.service.equipment.entity.EquipmentSensorState;
import com.taojin.iot.service.equipment.entity.EquipmentTrigger;
import com.taojin.iot.service.equipment.service.EquipmentSensorService;
import com.taojin.iot.service.equipment.service.EquipmentSensorStateService;
import com.taojin.iot.service.equipment.service.EquipmentTriggerService;
import com.taojin.iot.service.report.entity.ReportEquipmentSensor;
import com.taojin.iot.service.report.service.ReportEquipmentSensorService;

/**
 * Service-设备传感器 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午3:35:32 author 王杰
 * ============================================================================
 */
@Service("equipmentSensorStateServiceImpl")
public class EquipmentSensorStateServiceImpl extends
		BaseServiceImpl<EquipmentSensorState, Long> implements
		EquipmentSensorStateService {
	@Resource(name = "equipmentSensorStateDaoImpl")
	private EquipmentSensorStateDao equipmentSensorStateDao;
	@Resource
	private EquipmentSensorService equipmentSensorService;
	@Resource
	private ReportEquipmentSensorService reportEquipmentSensorService;
	@Resource
	private EquipmentTriggerService equipmentTriggerService;

	@Resource(name = "equipmentSensorStateDaoImpl")
	public void setBaseDao(EquipmentSensorStateDao equipmentSensorStateDao) {
		super.setBaseDao(equipmentSensorStateDao);
	}

	@Override
	public void reportFactory(JSONObject report) {
		String equipmentTypeName = report.getString("equipmentTypeName");
		if (StringUtils.equalsIgnoreCase(equipmentTypeName, "WaterMeter")) {// 国动水表
			reportWaterMeter(report);
		} else if (StringUtils.equalsIgnoreCase(equipmentTypeName,
				"FireProtection")) {// 陶金消防
			reportFireProtection(report);
		} else if (StringUtils.equalsIgnoreCase(equipmentTypeName,
				"GdFireProtection")) {//国动-淘金消防
			reportGdFireProtection(report);
		}
	}

	/**
	 * 水表-上报消息 国动
	 * 
	 * @param report
	 */
	public void reportWaterMeter(JSONObject report) {
		// 记录消息
		EquipmentSensor equipmentSensor = equipmentSensorService.getByParam(
				"idNumber", report.getString("addrCode"));
		if (equipmentSensor == null) {
			System.out.println("对不起，未获取到水表device");
			return;
		}

		String dateTime = DatesUtils.getStringToday("yyyy-MM-dd");
		ReportEquipmentSensor reportEquipmentSensor = new ReportEquipmentSensor();
		reportEquipmentSensor.setEquipmentTypeId(equipmentSensor
				.getEquipmentTypeId());
		reportEquipmentSensor.setOwnerId(equipmentSensor.getOwnerId());
		reportEquipmentSensor.setDateTime(dateTime);
		reportEquipmentSensor.setSensorId(equipmentSensor.getId());
		reportEquipmentSensor.setSensorNumber(equipmentSensor.getIdNumber());
		reportEquipmentSensor
				.setSensorTrueValue(report.getString("msgReceive"));

		JSONArray jsonArray = new JSONArray();
		JSONObject json = new JSONObject();
		String value = m2(equipmentSensor.getSign(),
				String.valueOf(report.getInt("tableDataCode")));
		json.put("value", value);
		jsonArray.add(json);
		reportEquipmentSensor.setSensorValues(jsonArray.toString());

		reportEquipmentSensorService.saveReport(reportEquipmentSensor, null);
		// 触发器调用
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(Filter.eq("equipmentSensor", equipmentSensor));
		List<EquipmentTrigger> equipmentTriggers = equipmentTriggerService
				.findList(null, filters, null);

		for (int i = 0; i < equipmentTriggers.size(); i++) {
			EquipmentTrigger trigger = equipmentTriggers.get(i);
			JSONArray triggerJsonarray = new JSONArray();
			JSONObject triggerJson = new JSONObject();
			triggerJson.put("value", report.getString("tableDataCode"));
			triggerJson.put("state", report.getInt("stateCode"));
			triggerJson.put("switchState", "0");
			triggerJson.put("time",
					DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"));
			triggerJsonarray.add(triggerJson);
			equipmentTriggerService.equipmentAlarmTypeTrigger(trigger,
					triggerJsonarray);
		}
	}
	
	/**
	 * 消防-上报消息 淘金国动
	 * 
	 * @param report
	 */
	public void reportGdFireProtection(JSONObject report) {
		// 记录消息
		EquipmentSensor equipmentSensor = equipmentSensorService.getByParam(
				"idNumber", report.getString("addrCode"));
		if (equipmentSensor == null) {
			System.out.println("对不起，未获取到消防device");
			return;
		}

		String dateTime = DatesUtils.getStringToday("yyyy-MM-dd");
		ReportEquipmentSensor reportEquipmentSensor = new ReportEquipmentSensor();
		reportEquipmentSensor.setEquipmentTypeId(equipmentSensor
				.getEquipmentTypeId());
		reportEquipmentSensor.setOwnerId(equipmentSensor.getOwnerId());
		reportEquipmentSensor.setDateTime(dateTime);
		reportEquipmentSensor.setSensorId(equipmentSensor.getId());
		reportEquipmentSensor.setSensorNumber(equipmentSensor.getIdNumber());
		reportEquipmentSensor
				.setSensorTrueValue(report.getString("msgReceive"));

		JSONArray jsonArray = new JSONArray();
		JSONObject json = new JSONObject();
		String value = m2(equipmentSensor.getSign(),
				String.valueOf(report.getInt("tableDataCode")));
		json.put("value", value);
		jsonArray.add(json);
		reportEquipmentSensor.setSensorValues(jsonArray.toString());

		reportEquipmentSensorService.saveReport(reportEquipmentSensor, null);
		// 触发器调用
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(Filter.eq("equipmentSensor", equipmentSensor));
		List<EquipmentTrigger> equipmentTriggers = equipmentTriggerService
				.findList(null, filters, null);

		for (int i = 0; i < equipmentTriggers.size(); i++) {
			EquipmentTrigger trigger = equipmentTriggers.get(i);
			JSONArray triggerJsonarray = new JSONArray();
			JSONObject triggerJson = new JSONObject();
			triggerJson.put("value", report.getString("tableDataCode"));
			triggerJson.put("state", report.getInt("stateCode"));
			triggerJson.put("switchState", "0");
			triggerJson.put("time",
					DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"));
			triggerJsonarray.add(triggerJson);
			equipmentTriggerService.equipmentAlarmTypeTrigger(trigger,
					triggerJsonarray);
		}
	}

	/**
	 * 消防-上报消息 淘金
	 * 
	 * @param report
	 */
	public void reportFireProtection(JSONObject report) {
		// 记录消息
		EquipmentSensor equipmentSensor = equipmentSensorService.getByParam(
				"idNumber", report.getString("sensorNumber"));
		String dateTime = DatesUtils.getStringToday("yyyy-MM-dd");
		ReportEquipmentSensor reportEquipmentSensor = new ReportEquipmentSensor();
		reportEquipmentSensor.setEquipmentTypeId(equipmentSensor
				.getEquipmentTypeId());
		reportEquipmentSensor.setOwnerId(equipmentSensor.getOwnerId());
		reportEquipmentSensor.setDateTime(dateTime);
		reportEquipmentSensor.setSensorId(equipmentSensor.getId());
		reportEquipmentSensor.setSensorNumber(equipmentSensor.getIdNumber());
		reportEquipmentSensor
				.setSensorTrueValue(report.getString("msgReceive"));

		JSONArray jsonArray = new JSONArray();
		JSONObject json = new JSONObject();
		String value = m2(equipmentSensor.getSign(), report.getString("value"));
		json.put("value", value);
		jsonArray.add(json);
		reportEquipmentSensor.setSensorValues(jsonArray.toString());

		reportEquipmentSensorService.saveReport(reportEquipmentSensor, null);
		// 触发器调用
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(Filter.eq("equipmentSensor", equipmentSensor));
		List<EquipmentTrigger> equipmentTriggers = equipmentTriggerService
				.findList(null, filters, null);

		for (int i = 0; i < equipmentTriggers.size(); i++) {
			EquipmentTrigger trigger = equipmentTriggers.get(i);
			JSONArray triggerJsonarray = new JSONArray();
			JSONObject triggerJson = new JSONObject();
			triggerJson.put("value", report.getString("value"));
			triggerJson.put("state", 1);
			triggerJson.put("switchState", "0");
			triggerJson.put("time",
					DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"));
			triggerJsonarray.add(triggerJson);
			equipmentTriggerService.equipmentAlarmTypeTrigger(trigger,
					triggerJsonarray);
		}
	}

	public static String m2(int sign, String value) {
		DecimalFormat df = new DecimalFormat();
		if (sign == 1) {
			df = new DecimalFormat("##.0");
		} else if (sign == 2) {
			df = new DecimalFormat("##.00");
		}
		return df.format(Double.parseDouble(value));
	}

}
