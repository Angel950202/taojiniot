//package com.taojin.iot.service.kanban.service.impl;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.context.annotation.Lazy;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.taojin.iot.agreement.fujiya.entity.AgreementRc701Value;
//import com.taojin.iot.agreement.fujiya.service.AgreementRc701ValueService;
//import com.taojin.iot.base.comm.Filter;
//import com.taojin.iot.base.comm.entity.UserSession;
//import com.taojin.iot.base.comm.utils.DatesUtils;
//import com.taojin.iot.service.equipment.entity.Equipment;
//import com.taojin.iot.service.equipment.entity.EquipmentSensor;
//import com.taojin.iot.service.equipment.service.EquipmentSensorService;
//import com.taojin.iot.service.equipment.service.EquipmentService;
//import com.taojin.iot.service.kanban.LineEnum;
//import com.taojin.iot.service.kanban.entiy.Trs;
//import com.taojin.iot.service.kanban.service.TrsService;
//import com.taojin.iot.service.task.entity.ProductionLine;
//import com.taojin.iot.service.task.entity.WorkOrder;
//import com.taojin.iot.service.task.entity.WorkReport;
//import com.taojin.iot.service.task.entity.WorkReportDetail;
//import com.taojin.iot.service.task.service.ProductionLineService;
//import com.taojin.iot.service.task.service.WorkOrderService;
//import com.taojin.iot.service.task.service.WorkReportDetailService;
//import com.taojin.iot.service.task.service.WorkReportService;
//import com.taojin.iot.service.user.entity.User;
//import com.taojin.iot.service.user.service.UserService;
//
///**
// * 看板Trs调度任务
// * @author Administrator
// *
// */
//@Component("trsKanBanJon")
//@Lazy(false)
//@EnableScheduling
//public class TrsKanBanJon {
//	
//	@Resource(name = "workOrderServiceImpl")
//	private WorkOrderService workOrderService;
//	
//	@Resource(name = "productionLineServiceImpl")
//	private ProductionLineService productionLineService;
//	
//	@Resource(name = "equipmentServiceImpl")
//	private EquipmentService equipmentService;
//	
//	@Resource(name = "equipmentSensorServiceImpl")
//	private EquipmentSensorService equipmentSensorService;
//	
//	@Resource(name = "agreementRc701ValueServiceImpl")
//    private AgreementRc701ValueService agreementRc701ValueService;
//	
//	@Resource(name = "trsServiceImpl")
//    private TrsService trsService;
//	
//	@Resource(name = "workReportDetailServiceImpl")
//	private WorkReportDetailService workReportDetailService;
//	
//	@Resource(name = "workReportServiceImpl")
//	private WorkReportService workReportService;
//	
//	@Resource(name = "userServiceImpl")
//	private UserService userService;
//	
//	
//	/**
//	 * 依据每个班次结束时间存储班次的TRS值，班次生产总数、班次不良数
//	 */
//	@Scheduled(cron = "0 0 1 * * ?")
////	@Scheduled(cron = "0/5 * * * * ? ")
//	public void trsSave() {
//		System.out.println("班次TRS任务初始化... ...");
//		//获取所有产线
//		List<ProductionLine> linList = productionLineService.findAll();
//		if (!linList.isEmpty()) {
//			for (ProductionLine line : linList) {
//				LineEnum agreementFujiyaEnum = LineEnum.valueOf(line.getLineNumber());
//				//获取产线下的所有设备
//				List<Filter> filters = new ArrayList<>();
//				filters.add(Filter.eq("isDel", false));
//				filters.add(Filter.eq("lineName", line.getLineNumber()));
//				List<Equipment> list = equipmentService.findList(null, filters,null);
//				Double totalNum = 0.0;
//				Double okNum = 0.0;
//				Double NokNum = 0.0;
//				for (Equipment equipment : list) {
//					//获取产线生产总数
//					totalNum = lineTotalNum(equipment, agreementFujiyaEnum.ordinal(),"生产总数");
//					//获取产线OK总数
//					okNum = lineTotalNum(equipment, agreementFujiyaEnum.ordinal(),"OK件");
//					//获取产线NOK数
//					NokNum = lineTotalNum(equipment, agreementFujiyaEnum.ordinal(),"NOK件");
//				}
//				
//				/*
//				 * 获取任务单实际生产时间
//				 * 若产线没有任务单，实际生产时间 = 班次结束时间 - 班次开始时间
//				 * 若产线当前班次任务单没有完结，系统自动完结当前产线、当前班次任务单
//				 * 若当前班次没有任务单则TRS值为0
//				 */
////		        List<Filter> filters1 = new ArrayList<>();
////		        filters1.add(Filter.eq("lineNumber", line.getLineNumber()));
////			    List<WorkOrder> workOrders = workOrderService.findList(null,filters1,null);
////			    if (!workOrders.isEmpty()) {
////			    	for(WorkOrder info : workOrders){
////			    		actualTime += info.getFinishTime();
////			    	}
////				}
//				Double actualTime = getFinishTime(line.getLineNumber(), totalNum, NokNum, okNum);
//			    Double trsValue = 0.0;
//			    if (actualTime != 0.0) {
//			    	trsValue = totalNum * line.getCt() / actualTime;
//				}
//				Trs trs = new Trs();
//				trs.setLineName(line.getLineName());
//				trs.setLineNumber(line.getLineNumber());
//				trs.setCt(line.getCt());
//				trs.setTrsTarget(line.getTrsTarget());
//				trs.setTrs(trsValue);
//				trs.setFinishTime(actualTime.toString());
//				trs.setTotalNum(totalNum);
//				trs.setOKnum(okNum);
//				trs.setNOKnum(NokNum);
//				trs.setShifts(0);
//				trsService.save(trs);
//			}
//		}
//	}
//	
//	/**
//	 * 获取产线实际生产时间
//	 * @param line
//	 * @param totalNum
//	 * @param NokNum
//	 * @param okNum
//	 * @return
//	 */
//	private Double getFinishTime(String lineNumber,Double totalNum,Double NokNum,Double okNum){
//		
//		Double finishTime = 0.0;
//		
//        List<Filter> filters = new ArrayList<>();
//        filters.add(Filter.eq("lineNumber", lineNumber));
//        filters.add(Filter.eq("startTime", getShiftsTime()));
//	    List<WorkOrder> workOrders = workOrderService.findList(null,filters,null);
//	    for (WorkOrder workOrder : workOrders) {
//			if ("已完成".equals(workOrder.getStatus())) {
//				finishTime = (double) workOrder.getFinishTime();
//				break;
//			}
//			//更新任务单
//			long longDate = System.currentTimeMillis();
//			Date nowDate = new Date(longDate);
//			workOrder.setFinishTime(DatesUtils.compareS(workOrder.getCreateDate(), nowDate));
//			workOrder.setUpdateTime(DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"));
//			workOrderService.update(workOrder);
//			
//			//更新追溯信息
//			WorkReportDetail workReportDetail = new WorkReportDetail();
//			workReportDetail.setCreatTime(DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"));
//			workReportDetail.setLineName(workOrder.getLineName());
//			workReportDetail.setLineCode(workOrder.getLineNumber());
//			workReportDetail.setOrderNumber(workOrder.getOrderNumber());
//			workReportDetail.setMsg("任务单完结");
//			workReportDetailService.save(workReportDetail);
//			
//			//生成报表
//			WorkReport workReport = new WorkReport();
//			workReport.setWorkReporNumber("BG"+ DatesUtils.getStringToday("yyyyMMddHHmmss"));
//			workReport.setOrderNumber(workOrder.getOrderNumber());
//			workReport.setProductionModel(workOrder.getProductionModel());
//			workReport.setProductionLine(workOrder.getLineName());
//			workReport.setPlanCount(workOrder.getCount());
//			workReport.setActualCount((new Double(totalNum)).intValue());
//			workReport.setOkCount((new Double(okNum)).intValue());
//			workReport.setNokCount((new Double(NokNum)).intValue());
//			workReport.setNokPercent(0 + "");
//			workReport.setYieldPercent(0 + "");
//			workReport.setReporter("system");
//			workReport.setReportTime(DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"));
//			workReportService.save(workReport);
//			
//			finishTime = (double) workOrder.getFinishTime();
//		}
//		return finishTime;
//	}
//	
//	/**
//	 * 获取开始时间
//	 * @return
//	 */
//	private String getShiftsTime(){
//		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
//		String startTime = "";
////		String endTime = "";
//        Calendar calendar = Calendar.getInstance();
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        if (hour >= 7 && hour < 15) {//早班
//        	startTime = sdFormat.format(new Date()) +" 07:00:00";
////        	endTime = sdFormat.format(new Date()) +" 15:00:00";
//		}
//        else if (hour >= 15 && hour < 23) {//中班
//        	startTime = sdFormat.format(new Date()) +" 15:00:00";
////        	endTime = sdFormat.format(new Date()) +" 23:00:00";
//		}
//        else{//晚班
//        	startTime = sdFormat.format(new Date()) +" 23:00:00";
////        	endTime = sdFormat.format(new Date()) +" 07:00:00";
//		}
//		return startTime;
//	}
//	
//	/**
//	 * 获取产线下生成总数
//	 * @param equipment
//	 * @param line
//	 * @param name
//	 * @return
//	 */
//	private Double lineTotalNum(Equipment equipment,Integer line,String name){
//		Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Double totalNum = 0.0;
//		List<Filter> filters = new ArrayList<>();
//		filters.add(Filter.eq("isDel", false));
//		filters.add(Filter.eq("equipment", equipment.getId()));
//		filters.add(Filter.eq("name", name));
//		List<EquipmentSensor> eslist = equipmentSensorService.findList(null, filters, null);
//		if (!eslist.isEmpty()) {
//			for (EquipmentSensor equipmentSensor : eslist) {
//				List<Filter> filters1 = new ArrayList<>();
//				filters1.add(Filter.eq("address", equipmentSensor.getSerialNumber()));
//				filters1.add(Filter.eq("equipType", line));
//				filters1.add(Filter.eq("dateTime", sdf.format(date)));
//	            List<AgreementRc701Value> aValues = agreementRc701ValueService.findList(null,filters1,null);
//	            if (!aValues.isEmpty()) {
//	            	Integer value = Integer.valueOf(aValues.get(0).getCommandValue());
//	            	totalNum += value;
//	            }
//			}
//		}
//		return totalNum;
//	}
//	
//	/**
//	 * 获取前一天日期
//	 * @param date 		当前日期
//	 * @return
//	 */
//	public static String getPreDay(Date date) {
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		calendar.add(Calendar.DAY_OF_MONTH, -1);
//		date = calendar.getTime();
//		String curDateStr = df.format(date);
//		return curDateStr;
//	}
//
////	/**
////	 * 根据时间判断班次
////	 * @param time
////	 * @return
////	 */
////	private Integer isShifts(String time){
////		Integer shifts = null;
////		try {
////			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////			Date date = sdFormat.parse(time);
////			SimpleDateFormat df = new SimpleDateFormat("HH");
////	        String str = df.format(date);
////	        Integer hour = Integer.parseInt(str);
////	        if (hour >= 7 && hour < 15) {//早班
////	        	shifts = 1;
////			}else if (hour >= 15 && hour < 23) {//中班
////				shifts = 2;
////			}else {
////				shifts = 3;
////			}
////		} catch (ParseException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		return shifts;
////	}
//
//}
