//package com.taojin.iot.transmit.handler.command.impl;
//
//import javax.annotation.Resource;
//
//import net.sf.json.JSONObject;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import com.taojin.iot.base.comm.utils.SpringUtils;
//import com.taojin.iot.transmit.Servers;
//import com.taojin.iot.transmit.handler.command.UserNbCommandSendService;
//import com.taojin.iot.transmit.lib.CommunicatType;
//import com.taojin.iot.transmit.module.entity.TransmitSession;
//import com.taojin.iot.transmit.module.service.TransmitSessionService;
//
///**
// * 用户中心-nb-北京钛云物联
// * @author wangjie
// *
// */
//@Service("userNbCommandSendServiceImpl")
//public class UserNbCommandSendServiceImpl implements UserNbCommandSendService{
//	final static Logger logger = LoggerFactory.getLogger(UserNbCommandSendServiceImpl.class);
//	
//	@Resource(name="transmitSessionServiceImpl")
//	private TransmitSessionService transmitSessionService = (TransmitSessionService) SpringUtils.getBean("transmitSessionServiceImpl");
//	
//	/**
//	 * 指令发送
//	 * @param sessionId
//	 * @param type
//	 * @param command
//	 * @return
//	 */
//	@Override
//	public boolean sendCommand(String sessionId,CommunicatType type,String command){
//		logger.info("[发送指令]发送："+command);
//		//Boolean isSend = Servers.userNbHandler.send(sessionId,type, command.getBytes());
//		return false;
//	}
//	
//	@Override
//	public boolean valveControl(String imei,CommunicatType type,Integer state){
//		TransmitSession transmitSession = transmitSessionService.getByParam("ccid", imei);
//		if(transmitSession == null){
//			logger.info("[发送控制指令]设备不在线");
//			return false;
//		}
//		JSONObject jsonSend = new JSONObject();
//		jsonSend.put("imei", imei);
//		jsonSend.put("valve", state);
//		
//		return sendCommand(transmitSession.getSessionId(),type,jsonSend.toString());
//	}
//	
//	@Override
//	public boolean setupReportCycle(String imei,CommunicatType type,Integer cycle){
//		TransmitSession transmitSession = transmitSessionService.getByParam("ccid", imei);
//		if(transmitSession == null){
//			logger.info("[发送控制指令]设备不在线");
//			return false;
//		}
//		JSONObject jsonSend = new JSONObject();
//		jsonSend.put("imei", imei);
//		jsonSend.put("interval", cycle.toString());
//		return sendCommand(transmitSession.getSessionId(),type,jsonSend.toString());
//	}
//	
//	@Override
//	public boolean setParams(String imei,CommunicatType type,String url){
//		TransmitSession transmitSession = transmitSessionService.getByParam("ccid", imei);
//		if(transmitSession == null){
//			logger.info("[发送控制指令]设备不在线");
//			return false;
//		}
//		JSONObject jsonSend = new JSONObject();
//		jsonSend.put("imei", imei);
//		jsonSend.put("url", url);
//		return sendCommand(transmitSession.getSessionId(),type,jsonSend.toString());
//	}
//	
//}
