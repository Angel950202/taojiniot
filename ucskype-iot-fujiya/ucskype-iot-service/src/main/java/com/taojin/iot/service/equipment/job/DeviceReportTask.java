package com.taojin.iot.service.equipment.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.taojin.iot.agreement.fujiya.entity.AgreementRc701Value;
import com.taojin.iot.agreement.fujiya.service.AgreementRc701ValueService;
import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.service.equipment.entity.DeviceReport;
import com.taojin.iot.service.equipment.entity.Equipment;
import com.taojin.iot.service.equipment.entity.EquipmentSensor;
import com.taojin.iot.service.equipment.service.DeviceReportService;
import com.taojin.iot.service.equipment.service.EquipmentSensorService;
import com.taojin.iot.service.equipment.service.EquipmentService;
import com.taojin.iot.service.kanban.LineEnum;
import com.taojin.iot.service.task.entity.WorkOrder;
import com.taojin.iot.service.task.service.WorkOrderService;

/**
 * 设备运行报表，每个班次结束
 * 校验是否有当前班次的任务单
 * 07:00:00
 * 15:00:00
 * 23:00:00
 * 
 * 07:00:00 - 15:00:00,
 * 15:00:00 - 23:00:00,
 * 23:00:00 - 07:00:00
 * @author Administrator
 *
 */
@Component("deviceReportTask")
@Lazy(false)
@EnableScheduling
public class DeviceReportTask {
	
	@Resource(name = "equipmentServiceImpl")
	EquipmentService equipmentService;
	
	@Resource(name = "equipmentSensorServiceImpl")
	private EquipmentSensorService equipmentSensorService;
	
	@Resource(name = "agreementRc701ValueServiceImpl")
    private AgreementRc701ValueService agreementRc701ValueService;
	
	@Resource(name = "workOrderServiceImpl")
	private WorkOrderService workOrderService;
	
	@Resource(name = "deviceReportServiceImpl")
	private DeviceReportService deviceReportService;

	/**
	 * 每天七点生成设备运行报表
	 */
	@Scheduled(cron = "0 0 7 * * ? ")
//	@Scheduled(cron = "0/10 * * * * ? ")
	public void early() {
		System.out.println("设备运行报表调度任务初始化... ...");
		//获取所有工作站，不包含工位
		List<Equipment> equipments =equipmentService.findAll();
		for (Equipment equipment : equipments) {
			if (equipment.getName().length() > 4) {
				//工作站地址
				List<Filter> filters = new ArrayList<>();
				filters.add(Filter.eq("idNumber", equipment.getId()));
				List<EquipmentSensor> sensors = equipmentSensorService.findList(null,filters,null);
				//运行时间
				Integer runningTime = 0;
				//故障时间
				Integer failureTime = 0;
				//开机时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String bootUpTime = sdf.format(new Date())+" 07:00:00";
				long time = 0;
				try {
					time = dateformat.parse(bootUpTime).getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for (EquipmentSensor equipmentSensor : sensors) {
					LineEnum agreementFujiyaEnum = LineEnum.valueOf(equipment.getLineNumber());
					if ("运行时间".equals(equipmentSensor.getName())) {
						runningTime  = realNum(equipmentSensor.getSerialNumber(),agreementFujiyaEnum.ordinal());
					}
					if ("故障时间".equals(equipmentSensor.getName())) {
						failureTime  = realNum(equipmentSensor.getSerialNumber(),agreementFujiyaEnum.ordinal());
					}
				}
				
				/**
				 * 设备稼动率=设备运行时间/（目前时间-生产型号开始时间)
				 */
				long currentTime = System.currentTimeMillis();
				Double trs = (double) (runningTime / (currentTime - time));
				
				DeviceReport deviceReport = new DeviceReport();
				deviceReport.setLineName(equipment.getLineName());
				deviceReport.setLineNumber(equipment.getLineNumber());
				deviceReport.setWorkStationName(equipment.getName());
				deviceReport.setBootUpTime(bootUpTime);
				deviceReport.setRunningTime(runningTime.toString());
				deviceReport.setFailureTime(failureTime.toString());
				deviceReport.setDeviceTrs(trs.toString());
				
				deviceReportService.save(deviceReport);
			}
		}
		updateOrder();
	}
	
	/**
	 * 每天15点生成设备运行报表
	 */
	@Scheduled(cron = "0 0 15 * * ? ")
	public void middle() {
		System.out.println("设备运行报表调度任务初始化... ...");
		//获取所有工作站，不包含工位
		List<Equipment> equipments =equipmentService.findAll();
		for (Equipment equipment : equipments) {
			if (equipment.getName().length() > 4) {
				//工作站地址
				List<Filter> filters = new ArrayList<>();
				filters.add(Filter.eq("idNumber", equipment.getId()));
				List<EquipmentSensor> sensors = equipmentSensorService.findList(null,filters,null);
				//运行时间
				Integer runningTime = 0;
				//故障时间
				Integer failureTime = 0;
				//开机时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String bootUpTime = sdf.format(new Date())+" 15:00:00";
				long time = 0;
				try {
					time = dateformat.parse(bootUpTime).getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for (EquipmentSensor equipmentSensor : sensors) {
					LineEnum agreementFujiyaEnum = LineEnum.valueOf(equipment.getLineNumber());
					if ("运行时间".equals(equipmentSensor.getName())) {
						runningTime  = realNum(equipmentSensor.getSerialNumber(),agreementFujiyaEnum.ordinal());
//						System.out.println("运行时间地址："+equipmentSensor.getSerialNumber());
					}
					if ("故障时间".equals(equipmentSensor.getName())) {
						failureTime  = realNum(equipmentSensor.getSerialNumber(),agreementFujiyaEnum.ordinal());
//						System.out.println("故障时间地址："+equipmentSensor.getSerialNumber());
					}
				}
				
				/**
				 * 设备稼动率=设备运行时间/（目前时间-生产型号开始时间)
				 */
				long currentTime = System.currentTimeMillis();
				Double trs = (double) (runningTime / (currentTime - time));
				
				DeviceReport deviceReport = new DeviceReport();
				deviceReport.setLineName(equipment.getLineName());
				deviceReport.setLineNumber(equipment.getLineNumber());
				deviceReport.setWorkStationName(equipment.getName());
				deviceReport.setBootUpTime(bootUpTime);
				deviceReport.setRunningTime(runningTime.toString());
				deviceReport.setFailureTime(failureTime.toString());
				deviceReport.setDeviceTrs(trs.toString());
				
				deviceReportService.save(deviceReport);
			}
		}
		updateOrder();
	}
	
	/**
	 * 每天晚上23点生成设备运行报表
	 */
	@Scheduled(cron = "0 0 23 * * ? ")
	public void evening () {
		System.out.println("设备运行报表调度任务初始化... ...");
		//获取所有工作站，不包含工位
		List<Equipment> equipments =equipmentService.findAll();
		for (Equipment equipment : equipments) {
			if (equipment.getName().length() > 4) {
				//工作站地址
				List<Filter> filters = new ArrayList<>();
				filters.add(Filter.eq("idNumber", equipment.getId()));
				List<EquipmentSensor> sensors = equipmentSensorService.findList(null,filters,null);
				//运行时间
				Integer runningTime = 0;
				//故障时间
				Integer failureTime = 0;
				//开机时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String bootUpTime = sdf.format(new Date())+" 23:00:00";
				long time = 0;
				try {
					time = dateformat.parse(bootUpTime).getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for (EquipmentSensor equipmentSensor : sensors) {
					LineEnum agreementFujiyaEnum = LineEnum.valueOf(equipment.getLineNumber());
					if ("运行时间".equals(equipmentSensor.getName())) {
						runningTime  = realNum(equipmentSensor.getSerialNumber(),agreementFujiyaEnum.ordinal());
					}
					if ("故障时间".equals(equipmentSensor.getName())) {
						failureTime  = realNum(equipmentSensor.getSerialNumber(),agreementFujiyaEnum.ordinal());
					}
				}
				
				/**
				 * 设备稼动率=设备运行时间/（目前时间-生产型号开始时间)
				 */
				long currentTime = System.currentTimeMillis();
				Double trs = (double) (runningTime / (currentTime - time));
				
				DeviceReport deviceReport = new DeviceReport();
				deviceReport.setLineName(equipment.getLineName());
				deviceReport.setLineNumber(equipment.getLineNumber());
				deviceReport.setWorkStationName(equipment.getName());
				deviceReport.setBootUpTime(bootUpTime);
				deviceReport.setRunningTime(runningTime.toString());
				deviceReport.setFailureTime(failureTime.toString());
				deviceReport.setDeviceTrs(trs.toString());
				
				deviceReportService.save(deviceReport);
			}
		}
		updateOrder();
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
	 * 根据班次开始更新未开始的任务单
	 */
	private void updateOrder(){
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("status", "未开始"));
		List<WorkOrder> workOrders = workOrderService.findList(null,filters,null);
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		for (WorkOrder workOrder : workOrders) {
			String time = workOrder.getStartTime();
			Date start = null;
			try {
				start = sdFormat.parse(time);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			 * 只更新当天的任务单
			 * 
			 */
			if (isToday(start)) {
				String startTime = "";
				String endTime = "";
		        calendar.setTime(start);
		        int hour = calendar.get(calendar.HOUR_OF_DAY);
		        if (hour >= 7 && hour < 15) {//早班
		        	startTime = sdf.format(new Date()) +" 07:00:00";
		        	endTime = sdf.format(new Date()) +" 15:00:00";
				}
		        if (hour >= 15 && hour < 23) {//中班
		        	startTime = sdf.format(new Date()) +" 15:00:00";
		        	endTime = sdf.format(new Date()) +" 23:00:00";
				}
		        if (hour >= 23 && hour < 7) {//晚班
		        	startTime = sdf.format(new Date()) +" 23:00:00";
		        	endTime = sdf.format(new Date()) +" 07:00:00";
				}
		        workOrder.setStatus("进行中");
				workOrder.setStartTime(startTime);
				workOrder.setEndTime(endTime);
				workOrder.setUpdateTime(DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"));
				workOrderService.update(workOrder);
			}
		}
	}
	
	/**
	 * 判断时间是否是当天的时间
	 * @param inputJudgeDate
	 * @return
	 */
	public boolean isToday(Date inputJudgeDate) {
		boolean flag = false;
		//获取当前系统时间
		long longDate = System.currentTimeMillis();
		Date nowDate = new Date(longDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = dateFormat.format(nowDate);
		String subDate = format.substring(0, 10);
		//定义每天的24h时间范围
		String beginTime = subDate + " 00:00:00";
		String endTime = subDate + " 23:59:59";
		Date paseBeginTime = null;
		Date paseEndTime = null;
		try {
			paseBeginTime = dateFormat.parse(beginTime);
			paseEndTime = dateFormat.parse(endTime);
		} catch (Exception e) {
			
		}
		if(inputJudgeDate.after(paseBeginTime) && inputJudgeDate.before(paseEndTime)) {
			flag = true;
		}
		return flag;
	}
}
