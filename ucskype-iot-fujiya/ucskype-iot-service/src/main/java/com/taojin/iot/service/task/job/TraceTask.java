package com.taojin.iot.service.task.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.taojin.iot.agreement.fujiya.entity.AgreementRc701Value;
import com.taojin.iot.agreement.fujiya.service.AgreementRc701ValueService;
import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.service.equipment.entity.Equipment;
import com.taojin.iot.service.equipment.entity.EquipmentSensor;
import com.taojin.iot.service.equipment.service.EquipmentSensorService;
import com.taojin.iot.service.equipment.service.EquipmentService;
import com.taojin.iot.service.kanban.LineEnum;
import com.taojin.iot.service.task.entity.WorkOrder;
import com.taojin.iot.service.task.entity.WorkReportDetail;
import com.taojin.iot.service.task.entity.WorkTraceDetail;
import com.taojin.iot.service.task.service.WorkOrderService;
import com.taojin.iot.service.task.service.WorkReportDetailService;
import com.taojin.iot.service.task.service.WorkTraceDetailService;

/**
 * 生产追溯
 * 每一小执行一次
 * @author Administrator
 *
 */
@Component("traceTask")
@Lazy(false)
@EnableScheduling
public class TraceTask {
	
	@Resource(name = "workOrderServiceImpl")
	private WorkOrderService workOrderService;
	
	@Resource(name = "equipmentServiceImpl")
	EquipmentService equipmentService;
	
	@Resource(name = "equipmentSensorServiceImpl")
	private EquipmentSensorService equipmentSensorService;
	
	@Resource(name = "agreementRc701ValueServiceImpl")
    private AgreementRc701ValueService agreementRc701ValueService;
	
	@Resource(name = "workReportDetailServiceImpl")
	private WorkReportDetailService reportDetailService;
	
	@Resource(name = "workTraceDetailServiceImpl")
	private WorkTraceDetailService workTraceDetailService;
	
	/**
	 * 每小时执行一次
	 * 只记录正在生产的任务单
	 */
	@Scheduled(cron = "0 0 0/1 * * ? ")
//	@Scheduled(cron = "0/10 * * * * ? ")
	public void trace(){
		System.out.println("追溯任务初始化... ...");
		/*
		 * 1.获取正在生产的任务单
		 * 2.根据任务单获取产线、设备、工站
		 * 3.根据设备获取传感器，故障时间地址、停机时间地址、NOK件地址
		 */
		
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("status", "进行中"));
		List<WorkOrder> workOrders = workOrderService.findList(null,filters,null);
		for (WorkOrder order : workOrders) {
			/*
			 * 当前班次的任务单
			 */
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String startTime = order.getStartTime();
			Date start = null;
			try {
				start = sdFormat.parse(startTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (isToday(start)) {
				/*
				 * 获取产线、设备
				 */
				LineEnum agreementFujiyaEnum = LineEnum.valueOf(order.getLineNumber());
				Integer line = agreementFujiyaEnum.ordinal();
				List<Filter> eqFilters = new ArrayList<>();
				filters.add(Filter.eq("lineNumber", order.getLineNumber()));
				List<Equipment> equipments = equipmentService.findList(null,eqFilters,null);
				for (Equipment equipment : equipments) {
					/*
					 * 获取工站、传感器
					 */
					if (equipment.getName().length() > 4) {
						getSensors(equipment,order,line);
					}
				}
			}
		}
	}
	
	/**
	 * 获取传感器
	 * @param sensors
	 * @param equipment
	 * @param order
	 * @param line
	 */
	private void getSensors(Equipment equipment,WorkOrder order,Integer line){
		try {
			List<Filter> sensFilters = new ArrayList<>();
			sensFilters.add(Filter.eq("idNumber", equipment.getId()));
			List<EquipmentSensor> sensors = equipmentSensorService.findList(null,sensFilters,null);
			
			//获取传感器、地址
			String nokMsg = "";
			String faultMsg = "";
			String bootUpMsg = "";
			for (EquipmentSensor equipmentSensor : sensors) {
				if (equipmentSensor.getName().indexOf("NOK件") != -1) {
					Map<String, Object> nokNum = history(equipmentSensor.getSerialNumber(),line);
					if ((Integer)nokNum.get("num") > 0) {
						nokMsg = "NOK: " + nokNum;
						Date time =  (Date) nokNum.get("time");
						saveTrace(nokMsg,equipment,order,time);
					}
				}
				if (equipmentSensor.getName().indexOf("故障时间") != -1) {
					Map<String, Object> fault = history(equipmentSensor.getSerialNumber(),line);
					if ((Integer)fault.get("num") > 0) {
						faultMsg = "故障:  " + fault;
						Date time =  (Date) fault.get("time");
						saveTrace(faultMsg,equipment,order,time);
					}
				}
				if (equipmentSensor.getName().indexOf("停机时间") != -1) {
					Map<String, Object> bootUp = history(equipmentSensor.getSerialNumber(),line);
					if ((Integer)bootUp.get("num") > 0) {
						bootUpMsg = "停机:  " + bootUp;
						Date time =  (Date) bootUp.get("time");
						saveTrace(bootUpMsg,equipment,order,time);
					}
				}
				//定时存储生成总数、OK数、NOK数
				saveWorkTrace(equipment,equipmentSensor,order,line);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 生产总数、ok数、nok数
	 * 生产中的任务单、每小时保存一次
	 * @param equipmentSensor
	 * @param order
	 * @param line
	 */
	private void saveWorkTrace(Equipment equipment,EquipmentSensor equipmentSensor,WorkOrder order,Integer line){
		try {
			Integer totalNum = 0;
			Integer okNum = 0;
			Integer nokNUm = 0;
			if ("生产总数".equals(equipmentSensor.getName())) {
				totalNum = getDate(equipmentSensor.getSerialNumber(),line);
			}
			if ("OK件".equals(equipmentSensor.getName())) {
				okNum = getDate(equipmentSensor.getSerialNumber(),line);
			}
			if ("NOK件".equals(equipmentSensor.getName())) {
				nokNUm = getDate(equipmentSensor.getSerialNumber(),line);
			}
			/*
			 * 保存追溯目录
			 */
			WorkTraceDetail detail = new WorkTraceDetail();
			//生产任务单号
			detail.setOrderNumber(order.getOrderNumber());
			//产线编号
			detail.setLineCode(equipment.getLineNumber());
			//产线名称
			detail.setLineName(equipment.getLineName());
			//工站名称
			detail.setWorks(equipment.getName());
			//创建时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//生产总数
			detail.setTotalNum(totalNum);
			//ok数量
			detail.setOkNum(okNum);
			//nok数量
			detail.setNokNum(nokNUm);
			//生产型号
			detail.setModel(order.getProductionModel());
			detail.setCreatTime(sdf.format(new Date()));
			workTraceDetailService.save(detail);
		} catch (Exception e) {
			// TODO: 保存生成总数、OK数量、NOK数
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取生产总数、ok数量、nok数量
	 * @param address
	 * @param line
	 * @return
	 */
	private Integer getDate(String address,Integer line){
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
	 * 获取故障时间、停机时间、NOK
	 * @param 	address
	 * @param 	line
	 * @return	数量、时间
	 */
	private Map<String,Object> history(String address,Integer line){
		Map<String, Object> map = new HashMap<>();
		Integer realNum = 0;
		Date time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("address", address));
		filters.add(Filter.eq("equipType", line));
		filters.add(Filter.eq("dateTime", sdf.format(new Date())));
        List<AgreementRc701Value> aValues = agreementRc701ValueService.findList(null,filters,null);
        if (!aValues.isEmpty()) {
        	Integer value = Integer.valueOf(aValues.get(0).getCommandValue());
        	realNum = value;
        	time = aValues.get(0).getModifyDate();
        }
        map.put("num", realNum);
    	map.put("time", time);
		return map;
	}
	
	/**
	 * 保存追溯目录
	 * @param msg
	 * @param equipment
	 * @param order
	 * @param createTime
	 */
	private void saveTrace(String msg,Equipment equipment,WorkOrder order,Date createTime){
		try {
			/*
			 * 保存追溯目录
			 */
			WorkReportDetail detail = new WorkReportDetail();
			//生产任务单号
			detail.setOrderNumber(order.getOrderNumber());
			//产线编号
			detail.setLineCode(equipment.getLineNumber());
			//产线名称
			detail.setLineName(equipment.getLineName());
			//追溯信息
			detail.setMsg(msg);
			//工站名称
			detail.setWorks(equipment.getName());
			//创建时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (createTime == null) {
				createTime = new Date();
			}
			detail.setCreatTime(sdf.format(createTime));
			reportDetailService.save(detail);
		} catch (Exception e) {
			// TODO: 追溯目录
			e.printStackTrace();
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
