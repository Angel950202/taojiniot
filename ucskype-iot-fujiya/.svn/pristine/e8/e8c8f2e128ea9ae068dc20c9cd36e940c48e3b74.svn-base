//package com.taojin.iot.transmit.job;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.taojin.iot.base.comm.Page;
//import com.taojin.iot.base.comm.Pageable;
//import com.taojin.iot.base.comm.utils.DatesUtils;
//import com.taojin.iot.service.equipment.entity.Equipment;
//import com.taojin.iot.service.equipment.service.EquipmentService;
//import com.taojin.iot.transmit.Servers;
//import com.taojin.iot.transmit.module.entity.TransmitSession;
//import com.taojin.iot.transmit.module.service.TransmitSessionService;
//
///**
// * Job - 设备状态任务
// * 
// * 
// * 
// */
//@Component("equipmentStateJob")
//@Lazy(false)
//public class EquipmentStateJob {
//
//	@Resource(name = "transmitSessionServiceImpl")
//	private TransmitSessionService transmitSessionService;
//	@Resource(name="equipmentServiceImpl")
//	private EquipmentService equipmentService;
//
//	@Scheduled(cron = "${job.equipment_state.cron}")
//	public void changeState() {
////		Pageable pageable = new Pageable(1, 20);
////		Page<TransmitSession> page = transmitSessionService.findPage(pageable);
////		int totalPages = page.getTotalPages();
////		for(int i=1;i<=totalPages;i++){
////			if(i > 1){
////				pageable = new Pageable(i, 20);
////				page = transmitSessionService.findPage(pageable);
////			}
////			for(int j=0;j<page.getContent().size();j++){
////				//获取最后一次上报数据时间 
////				String lastTime = page.getContent().get(j).getLastTime();
////				if(StringUtils.isBlank(lastTime)){
////					return;
////				}
////				//获取心跳时间时隔
////				Integer heartbeatTime = page.getContent().get(j).getHeartbeatTime();
////				if(heartbeatTime == null || heartbeatTime == 0){
////					return;
////				}
////				String dateTime = DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss");
////				long min = DatesUtils.compareMin(DatesUtils.stringToDate(lastTime, "yyyy-MM-dd HH:mm:ss"),
////						DatesUtils.stringToDate(dateTime, "yyyy-MM-dd HH:mm:ss"));
////				long second = min * 60;
////				if(second > heartbeatTime){//超过心跳时间 
////					//变更设备状态为下线
////					Equipment equipment = equipmentService.getByParam("serialNumber",  page.getContent().get(j).getCcid());
////					if(equipment != null){
////						equipment.setState(2);//断开
////						equipment.setStateInfo("设备长时间无响应");
////						equipmentService.update(equipment);
////						Servers.tcpUserHandler.sessionClosed(page.getContent().get(j).getSessionId());
////					}
////					
////				}
////				
////			}
////		}
//		
//	}
//	
//	
//
//}