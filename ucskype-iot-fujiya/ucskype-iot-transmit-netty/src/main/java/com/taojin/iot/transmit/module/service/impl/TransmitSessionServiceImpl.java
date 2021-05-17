//package com.taojin.iot.transmit.module.service.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import com.taojin.iot.base.comm.Filter;
//import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
//import com.taojin.iot.base.comm.utils.DatesUtils;
//import com.taojin.iot.service.equipment.entity.Equipment;
//import com.taojin.iot.service.equipment.service.EquipmentService;
//import com.taojin.iot.transmit.module.dao.TransmitSessionDao;
//import com.taojin.iot.transmit.module.entity.TransmitSession;
//import com.taojin.iot.transmit.module.service.TransmitSessionService;
//
///**
// * service-通话会话 
// * @author wangjie
// *
// */
//@Service("transmitSessionServiceImpl")
//public class TransmitSessionServiceImpl extends BaseServiceImpl<TransmitSession,Long> implements TransmitSessionService{
//	final static Logger logger = LoggerFactory.getLogger(TransmitSessionServiceImpl.class);
//	@Resource(name = "transmitSessionDaoImpl")
//	private TransmitSessionDao transmitSessionDao;
//	
//	@Resource(name="equipmentServiceImpl")
//	private EquipmentService equipmentService;
//
//	@Resource(name = "transmitSessionDaoImpl")
//	public void setBaseDao(TransmitSessionDao transmitSessionDao) {
//		super.setBaseDao(transmitSessionDao);
//	}
//	
//	@Override
//	public void bind(String sessionId,String ccid,Integer heartbeatTime){
//		logger.info("[会话绑定]---->sessionId={},ccid={}",sessionId,ccid);
//		List<Filter> filters = new ArrayList<Filter>();
//		filters.add(Filter.eq("ccid", ccid));
//		List<TransmitSession> transmitSessions = super.findList(1, filters, null);
//		if(transmitSessions.size() > 0){
//			logger.info("[会话绑定]---->获取到历史会话,sessionId={},ccid={}",transmitSessions.get(0).getSessionId(),transmitSessions.get(0).getCcid());
//			TransmitSession transmitSession = transmitSessions.get(0);
//			transmitSession.setSessionId(sessionId);
//			transmitSession.setHeartbeatTime(heartbeatTime);
//			transmitSession.setLastTime(DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"));
//			super.update(transmitSession);
//		}else{
//			TransmitSession transmitSession = new TransmitSession(sessionId, ccid,heartbeatTime);
//			transmitSession.setLastTime(DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"));
//			super.save(transmitSession);
//		}
//		
//		Equipment equipment = equipmentService.getByParam("serialNumber", ccid);
//		equipment.setState(0);
//		equipmentService.update(equipment);
//		logger.info("[会话绑定]---->成功：sessionId={},ccid={}",sessionId,ccid);
//	}
//	
//	@Override
//	public void removeBind(String sessionId){
//		logger.info("[会话移除]---->开始：sessionId={},ccid={}",sessionId);
//		List<Filter> filters = new ArrayList<Filter>();
//		filters.add(Filter.eq("sessionId", sessionId));
//		List<TransmitSession> transmitSessions = super.findList(null, filters, null);
//		for(int i=0;i<transmitSessions.size();i++){
//			transmitSessions.get(i).setSessionId(null);
//			transmitSessions.get(i).setHeartbeatTime(null);
//			transmitSessions.get(i).setLastTime(null);
//			super.update(transmitSessions.get(i));
//			Equipment equipment = equipmentService.getByParam("serialNumber", transmitSessions.get(i).getCcid());
//			equipment.setState(2);
//			equipment.setStateInfo("[事件]设备连接中断");
//			equipmentService.update(equipment);
//		}
//		logger.info("[会话移除]---->成功：sessionId={},ccid={}",sessionId);
//	}
//	
//}
