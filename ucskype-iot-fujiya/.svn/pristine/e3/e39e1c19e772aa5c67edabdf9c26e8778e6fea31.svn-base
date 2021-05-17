package com.taojin.iot.service.equipment.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.base.comm.utils.PostGetUtil;
import com.taojin.iot.service.equipment.dao.EquipmentTriggerDao;
import com.taojin.iot.service.equipment.entity.Equipment;
import com.taojin.iot.service.equipment.entity.EquipmentSensor;
import com.taojin.iot.service.equipment.entity.EquipmentTrigger;
import com.taojin.iot.service.equipment.entity.EquipmentTriggerLog;
import com.taojin.iot.service.equipment.service.EquipmentService;
import com.taojin.iot.service.equipment.service.EquipmentTriggerLogService;
import com.taojin.iot.service.equipment.service.EquipmentTriggerService;

/**
 * Service-设备触发器 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午8:51:07 author 王杰
 * ============================================================================
 */
@Service("equipmentTriggerServiceImpl")
public class EquipmentTriggerServiceImpl extends
		BaseServiceImpl<EquipmentTrigger, Long> implements
		EquipmentTriggerService {
	final static Logger logger = LoggerFactory
			.getLogger(EquipmentTriggerService.class);
	@Resource(name = "equipmentTriggerDaoImpl")
	private EquipmentTriggerDao equipmentTriggerDao;
	@Resource(name = "equipmentTriggerLogServiceImpl")
	private EquipmentTriggerLogService equipmentTriggerLogService;
	@Resource(name="equipmentServiceImpl")
	private EquipmentService equipmentService;
	
	@Value("${sms.url}")
	private String smsSendUrl;
	@Value("${sms.sn}")
	private String smsSn;
	@Value("${sms.pwd}")
	private String smsPwd;

	@Resource(name = "equipmentTriggerDaoImpl")
	public void setBaseDao(EquipmentTriggerDao equipmentTriggerDao) {
		super.setBaseDao(equipmentTriggerDao);
	}

	/**
	 * 数据触发处理
	 * @param trigger
	 * @param jsonArray [{"value":"值","state":"状态","switchState":"开关状态","time":"时间"}]
	 */
	public void equipmentAlarmTypeTrigger(EquipmentTrigger trigger,
			JSONArray jsonArray) {
		logger.info("[触发器处理]--->开始：报警值："+trigger.getTargetValue());
		JSONObject json = jsonArray.getJSONObject(0);
		Double num = json.getDouble("value");
		Integer state = json.getInt("state");
		Integer switchState = json.getInt("switchState");
		Date uptime = DatesUtils.stringToDate(json.getString("time"),
				"yyyy-MM-dd");
		Date nowTime = DatesUtils.stringToDate(
				DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"),
				"yyyy-MM-dd HH:mm:ss");
		JSONObject jsonupper = JSONObject.fromObject(trigger.getUpperBoundC());

		EquipmentTriggerLog triggerLog = new EquipmentTriggerLog();
		triggerLog.setTriggerValue(num.toString());
		triggerLog.setTarget(trigger.getTarget());
		triggerLog.setSendState(0);
		triggerLog.setFaileReason("未知");
		triggerLog.setTriggerContent(trigger.getEquipmentAlarmTypeEnum()
				.getDesc());
		triggerLog.setEquipmentTrigger(trigger);
		triggerLog.setOwnerId(trigger.getOwnerId());
		triggerLog.setEquipmentTypeId(trigger.getEquipmentTypeId());
		String content = "";
		boolean isSendSms = false;
		if(StringUtils.equalsIgnoreCase(trigger.getTarget().name(), "SMS1") ){
			triggerLog.setFaileReason("短信通道故障");
			isSendSms = true;
		}else{
			triggerLog.setFaileReason("通知服务未开通");
		}
		String receiver = trigger.getTargetValue();
		boolean isWarn = false;//是否报警 
		if (StringUtils.equalsIgnoreCase("val_above", trigger
				.getEquipmentAlarmTypeEnum().name())) {// 数值高于X
			if (num > jsonupper.getDouble("key1")) {
				isWarn = true;
				// 报警
				if(this.sendSms(content, receiver,isSendSms)){
					triggerLog.setSendState(1);
				}
				triggerLog.setTriggerContent("数值高于"
						+ jsonupper.getDouble("key1"));
				equipmentTriggerLogService.save(triggerLog);
			}
		} else if (StringUtils.equalsIgnoreCase("val_below", trigger
				.getEquipmentAlarmTypeEnum().name())) {// 数值低于Y
			if (num < jsonupper.getDouble("key1")) {
				isWarn = true;
				// 报警
				if(this.sendSms(content, receiver,isSendSms)){
					triggerLog.setSendState(1);
				}
				triggerLog.setTriggerContent("数值低于"
						+ jsonupper.getDouble("key1"));
				equipmentTriggerLogService.save(triggerLog);
			}
		} else if (StringUtils.equalsIgnoreCase("val_above_below", trigger
				.getEquipmentAlarmTypeEnum().name())) {// 数值高于X低于Y
			if (num < jsonupper.getDouble("key2")
					|| num > jsonupper.getDouble("key1")) {
				isWarn = true;
				//  报警
				if(this.sendSms(content, receiver,isSendSms)){
					triggerLog.setSendState(1);
				}
				triggerLog.setTriggerContent("数值高于"
						+ jsonupper.getDouble("key1") + "低于"
						+ jsonupper.getDouble("key2"));
				equipmentTriggerLogService.save(triggerLog);
			}

		} else if (StringUtils.equalsIgnoreCase("val_above_below_ofm", trigger
				.getEquipmentAlarmTypeEnum().name())) {// 数值高于X低于Y超过M分钟
			if (num < jsonupper.getDouble("key2")
					|| num > jsonupper.getDouble("key1")) {
				if (DatesUtils.compareMin(nowTime, uptime) > jsonupper
						.getDouble("key3")) {
					isWarn = true;
					//  报警
					if(this.sendSms(content, receiver,isSendSms)){
						triggerLog.setSendState(1);
					}
					triggerLog.setTriggerContent("数值高于"
							+ jsonupper.getDouble("key1") + "低于"
							+ jsonupper.getDouble("key2") + "超过"
							+ jsonupper.getDouble("key3") + "分钟");
					equipmentTriggerLogService.save(triggerLog);
				}
			}

		} else if (StringUtils.equalsIgnoreCase("x_tir_y_rec", trigger
				.getEquipmentAlarmTypeEnum().name())) {// 数值高于X报警低于Y恢复
			if (num > jsonupper.getDouble("key1")) {
				//  报警
				isWarn = true;
				if(this.sendSms(content, receiver,isSendSms)){
					triggerLog.setSendState(1);
				}
				triggerLog.setTriggerContent("数值高于"
						+ jsonupper.getDouble("key1") + "报警低于"
						+ jsonupper.getDouble("key2") + "恢复");
				equipmentTriggerLogService.save(triggerLog);
			} else if (num < jsonupper.getDouble("key2")) {
				//  恢复
				isWarn = true;
				if(this.sendSms(content, receiver,isSendSms)){
					triggerLog.setSendState(1);
				}
				triggerLog.setTriggerContent("数值高于"
						+ jsonupper.getDouble("key1") + "报警低于"
						+ jsonupper.getDouble("key2") + "恢复");
				equipmentTriggerLogService.save(triggerLog);
			}

		} else if (StringUtils.equalsIgnoreCase("val_between", trigger
				.getEquipmentAlarmTypeEnum().name())) {// 数值在X和Y之间
			if (num >= jsonupper.getDouble("key1")
					&& num <= jsonupper.getDouble("key2")) {
				// 报警
				isWarn = true;
				if(this.sendSms(content, receiver,isSendSms)){
					triggerLog.setSendState(1);
				}
				triggerLog.setTriggerContent("数值在"
						+ jsonupper.getDouble("key1") + "和"
						+ jsonupper.getDouble("key2") + "之间");
				equipmentTriggerLogService.save(triggerLog);
			}

		} else if (StringUtils.equalsIgnoreCase("val_above_bound", trigger
				.getEquipmentAlarmTypeEnum().name())) {// 数值超过M分钟高于X
			if (num > jsonupper.getDouble("key2")) {
				if (DatesUtils.compareMin(nowTime, uptime) > jsonupper
						.getDouble("key1")) {
					//  报警
					isWarn = true;
					if(this.sendSms(content, receiver,isSendSms)){
						triggerLog.setSendState(1);
					}
					triggerLog.setTriggerContent("数值超过"
							+ jsonupper.getDouble("key1") + "分钟高于"
							+ jsonupper.getDouble("key2"));

					equipmentTriggerLogService.save(triggerLog);
				}
			}

		} else if (StringUtils.equalsIgnoreCase("val_below_bound", trigger
				.getEquipmentAlarmTypeEnum().name())) {// 数值超过M分钟低于Y
			if (num < jsonupper.getDouble("key2")) {
				if (DatesUtils.compareMin(nowTime, uptime) > jsonupper
						.getDouble("key1")) {
					//  报警
					isWarn = true;
					if(this.sendSms(content, receiver,isSendSms)){
						triggerLog.setSendState(1);
					}
					triggerLog.setTriggerContent("数值超过"
							+ jsonupper.getDouble("key1") + "分钟低于"
							+ jsonupper.getDouble("key2"));

					equipmentTriggerLogService.save(triggerLog);
				}
			}

		} else if (StringUtils.equalsIgnoreCase("offline", trigger
				.getEquipmentAlarmTypeEnum().name())) {// 传感器未连接
			if (state == 0) {
				// 报警
				isWarn = true;
				if(this.sendSms(content, receiver,isSendSms)){
					triggerLog.setSendState(1);
				}
				triggerLog.setTriggerContent("传感器未连接");
				equipmentTriggerLogService.save(triggerLog);
			}

		} else if (StringUtils.equalsIgnoreCase("offline_for_minutes", trigger
				.getEquipmentAlarmTypeEnum().name())) {// 超过M分钟未连接
			if (state == 0) {
				if (DatesUtils.compareMin(nowTime, uptime) > jsonupper
						.getDouble("key1")) {
					// 报警
					isWarn = true;
					if(this.sendSms(content, receiver,isSendSms)){
						triggerLog.setSendState(1);
					}
					triggerLog.setTriggerContent("超过"
							+ jsonupper.getDouble("key1") + "分钟未连接");
					equipmentTriggerLogService.save(triggerLog);
				}
			}

		} else if (StringUtils.equalsIgnoreCase("switch_on", trigger
				.getEquipmentAlarmTypeEnum().name())) {// 开关ON
			if (switchState == 1) {
				// 报警
				isWarn = true;
				if(this.sendSms(content, receiver,isSendSms)){
					triggerLog.setSendState(1);
				}
				triggerLog.setTriggerContent("开关ON");
				equipmentTriggerLogService.save(triggerLog);
			}
		} else if (StringUtils.equalsIgnoreCase("switch_off", trigger
				.getEquipmentAlarmTypeEnum().name())) {// 开关OFF

			if (switchState == 0) {
				// 报警
				isWarn = true;
				if(this.sendSms(content, receiver,isSendSms)){
					triggerLog.setSendState(1);
				}
				triggerLog.setTriggerContent("开关OFF");
				equipmentTriggerLogService.save(triggerLog);
			}
		}
		
		if(isWarn == true){//有报警
			logger.info("[触发器处理]--->完成：报警："+triggerLog.getTriggerContent());
			Equipment equipment = trigger.getEquipment();
			EquipmentSensor sensor = trigger.getEquipmentSensor();
			equipment.setState(1);//报警/故障
			equipment.setStateInfo("触发器报警,"+sensor.getName()+"传感器:"+triggerLog.getTriggerContent());
			equipmentService.update(equipment);
		}else{
			logger.info("[触发器处理]--->完成：报警：无");
			Equipment equipment = trigger.getEquipment();
			EquipmentSensor sensor = trigger.getEquipmentSensor();
			equipment.setState(0);//恢复正常
			equipment.setStateInfo("触发器报警:,"+sensor.getName()+"传感器:正常");
			equipmentService.update(equipment);
		}
	}

	public boolean sendSms(String content,String receiver,boolean isSendSms) {
		if(isSendSms == false){
			return false;
		}
		System.out.println("==========开始发送短信================"+receiver);
		String[] receivers = receiver.split("\\,");
		
		try {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			JSONObject json = new JSONObject();
			json.put("action", "sms");
			json.put("method", "sendSms");
			json.put("key", "授权key");
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("sn", smsSn);
			jsonParam.put("pwd", smsPwd);
			jsonParam.put("content", content.trim());
			json.put("param", jsonParam);

			for(int i=0;i<receivers.length;i++){
				jsonParam.put("phone", receivers[i]);
				parameterMap.put("requestParams", json.toString());
				if (StringUtils.isNotBlank(smsSendUrl)) {
					System.out.println("+++++++++发送短信++++++++" + json.toString());
					System.out.println("+++++++++发送短信++++++++" + receiver);
					String resultStr = PostGetUtil.post(smsSendUrl, parameterMap);
					System.out.println(receiver+ "+++++++++短信结果++++++++++++++" + resultStr);
					JSONObject jsonResult = JSONObject.fromObject(smsSendUrl);
					if(jsonResult.getInt("errcode") == 0){
						return true;
					}else{
						return false;
					}
				}
			}
			

		} catch (Exception eContent) {
			eContent.printStackTrace();
			return false;
		}
		return false;
	}

}
