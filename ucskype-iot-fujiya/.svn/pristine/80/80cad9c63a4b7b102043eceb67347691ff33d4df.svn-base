package com.taojin.iot.service.equipment.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.taojin.iot.agreement.fujiya.entity.AgreementRc701Value;
import com.taojin.iot.agreement.fujiya.service.AgreementRc701ValueService;
import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.service.equipment.entity.Equipment;
import com.taojin.iot.service.equipment.entity.EquipmentSensor;
import com.taojin.iot.service.equipment.entity.EquipmentTrigger;
import com.taojin.iot.service.equipment.entity.EquipmentTriggerLog;
import com.taojin.iot.service.equipment.enums.EquipmentAlarmTypeEnum;
import com.taojin.iot.service.equipment.service.EquipmentSensorService;
import com.taojin.iot.service.equipment.service.EquipmentService;
import com.taojin.iot.service.equipment.service.EquipmentTriggerLogService;
import com.taojin.iot.service.equipment.service.EquipmentTriggerService;
import com.taojin.iot.service.kanban.LineEnum;
import com.taojin.iot.service.task.entity.WorkOrder;
import com.taojin.iot.service.task.service.WorkOrderService;

/**
 * 异常记录定时任务
 * @author Administrator
 *
 */
@Component("abnormalJon")
@Lazy(false)
@EnableScheduling
public class AbnornalAlarm{

	@Resource(name = "equipmentTriggerServiceImpl")
	EquipmentTriggerService equipmentTriggerService;
	
	@Resource(name = "equipmentServiceImpl")
	EquipmentService equipmentService;
	
	@Resource(name = "workOrderServiceImpl")
	private WorkOrderService workOrderService;
	
	@Resource(name = "equipmentSensorServiceImpl")
	private EquipmentSensorService equipmentSensorService;
	
	@Resource(name = "agreementRc701ValueServiceImpl")
    private AgreementRc701ValueService agreementRc701ValueService;
	
	@Resource(name = "equipmentTriggerLogServiceImpl")
	private EquipmentTriggerLogService equipmentTriggerLogService;
	
	/**
	 * 异常报警定时任务
	 * 每30分钟执行一次
	 */
	@Scheduled(cron = "0 0/30 * * * ? ")
//	@Scheduled(cron = "0/30 * * * * ? ")
//	@Scheduled(cron = "0/10 * * * * ? ")
	public void abnormal() {
		System.out.println("异常记录任务初始化... ...");
		//获取所有触发器
		List<EquipmentTrigger> triggerList = equipmentTriggerService.findAll();
		for (EquipmentTrigger trigger : triggerList) {
			
			/*
			 * 获取设备信息
			 * 获取生产任务单，若生产任务单为空则不执行
			 */
			Equipment equipment =equipmentService.find(trigger.getEquipment().getId());
			List<Filter> filters1 = new ArrayList<>();
	        filters1.add(Filter.eq("lineNumber", equipment.getLineNumber()));
		    List<WorkOrder> workOrders = workOrderService.findList(null,filters1,null);
		    if (!workOrders.isEmpty()) {
		    	
		    	//获取传感器信息
				EquipmentSensor sensor= equipmentSensorService.find(trigger.getEquipmentSensor().getId());
		    	
		    	//获取采集实时数据
				LineEnum agreementFujiyaEnum = LineEnum.valueOf(equipment.getLineNumber());
				Integer realNum = realNum(sensor.getSerialNumber(),agreementFujiyaEnum.ordinal());
				
		    	//触发器条件
				EquipmentAlarmTypeEnum alarmTypeEnum = trigger.getEquipmentAlarmTypeEnum();
				//将字符串转换为json
				JSONObject jsonObject = JSONObject.fromObject(trigger.getUpperBoundC());
				
//				System.out.println("设备所属产线======"+agreementFujiyaEnum.ordinal());
//				System.out.println("设备采集值======"+realNum);
//				System.out.println("设定值名称======"+sensor.getName());
//				System.out.println("设定值地址======"+sensor.getSerialNumber());
				//报警类型
				String type = trigger.getType().toString();
				if ("数值高于X".equals(alarmTypeEnum.getDesc())) {
					Integer target = Integer.parseInt(jsonObject.get("key1").toString());
					if (realNum > target) {
					    //报警内容
						String content = sensor.getName()+"{高于设定值:"+target+"}";
					    savelog(workOrders.get(0).getProductionModel(),workOrders.get(0).getLineName(),equipment.getName(),content,type);
					    
					}
				}
				
				if ("数值低于Y".equals(alarmTypeEnum.getDesc())) {
					Integer target = Integer.parseInt(jsonObject.get("key1").toString());
					if (realNum < target) {
						//报警内容
						String content = sensor.getName()+"{低于设定值:"+target+"}";
					    savelog(workOrders.get(0).getProductionModel(),workOrders.get(0).getLineName(),equipment.getName(),content,type);
					}
				}
				
				if ("数值高于X低于Y".equals(alarmTypeEnum.getDesc())) {
					Integer key1 = Integer.parseInt(jsonObject.get("key1").toString());
					Integer key2 = Integer.parseInt(jsonObject.get("key2").toString());
					if (realNum > key1 || realNum < key2) {
						//报警内容
						String content = sensor.getName()+"{高于设定值:"+key1+",或小于设定值:"+key2+"}";
					    savelog(workOrders.get(0).getProductionModel(),workOrders.get(0).getLineName(),equipment.getName(),content,type);
					}
				}
				if ("数值在X和Y之间".equals(alarmTypeEnum.getDesc())) {
					Integer key1 = Integer.parseInt(jsonObject.get("key1").toString());
					Integer key2 = Integer.parseInt(jsonObject.get("key2").toString());
					if (realNum > key1 && realNum < key2) {
						//报警内容
					    String content = sensor.getName()+"{高于设定值:"+key1+",并且小于设定值:"+key2+"}";
					    savelog(workOrders.get(0).getProductionModel(),workOrders.get(0).getLineName(),equipment.getName(),content,type);
					}
				}
			}
		}
	}
	
	/**
	 * 获取实时数据
	 * @param address
	 * @param line
	 * @return
	 */
	private Integer realNum(String address,Integer line){
		Integer realNum = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("address", address));
		filters.add(Filter.eq("equipType", line));
		filters.add(Filter.eq("dateTime", sdf.format(new Date())));
        List<AgreementRc701Value> aValues = agreementRc701ValueService.findList(null,filters,null);
        if (!aValues.isEmpty()) {
        	Integer value = Integer.valueOf(aValues.get(0).getCommandValue());
        	realNum += value;
        }
		return realNum;
	}
	
	/**
	 * 保存异常报警记录
	 * @param model
	 * @param lineName
	 * @param work
	 * @param content
	 * @param type
	 */
	private void savelog(String model,String lineName,String work,String content,String type){
		EquipmentTriggerLog log = new EquipmentTriggerLog();
	    //生产型号
	    log.setProductionModel(model);
	    //生产线
	    log.setProductionLine(lineName);
	    //工站
	    log.setWorkStationName(work);
	    //报警类型
	    log.setType(type);
	    //发送状态
	    log.setSendState(1);
	    //报警内容
	    log.setTriggerContent(content);
	    equipmentTriggerLogService.save(log);
	}
}
