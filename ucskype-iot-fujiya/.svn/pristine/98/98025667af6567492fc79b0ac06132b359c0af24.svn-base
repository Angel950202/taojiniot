//package com.taojin.iot.transmit.handler.command.impl;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//import com.taojin.iot.base.comm.utils.SpringUtils;
//import com.taojin.iot.service.user.service.SeniorManagementService;
//import com.taojin.iot.transmit.Servers;
//import com.taojin.iot.transmit.bean.CommonCommand;
//import com.taojin.iot.transmit.bean.Manager;
//import com.taojin.iot.transmit.handler.TCPHandler;
//import com.taojin.iot.transmit.handler.command.ManagerCommandService;
//import com.taojin.iot.transmit.lib.CommunicatType;
//import com.taojin.iot.transmit.utils.ConvertUtil;
//import com.taojin.iot.service.report.entity.ReportDTU;
//import  com.taojin.iot.service.report.service.*;
//
///**
// * service-管理中心-指令处理
// * 
// * @author wangjie
// *
// */
//@Service("managerCommandServiceImpl")
//public class ManagerCommandServiceImpl implements ManagerCommandService {
//	final static Logger logger = LoggerFactory
//			.getLogger(ManagerCommandServiceImpl.class);
//	
//	private SeniorManagementService seniorManagementService = (SeniorManagementService) SpringUtils.getBean("seniorManagementServiceImpl");
//	private ReportDTUService reportDtuService = (ReportDTUService) SpringUtils.getBean("reportDTUServiceImpl");
//	/**
//	 * 指令发送
//	 * 
//	 * @param sessionId
//	 * @param type
//	 * @param command
//	 * @return
//	 */
//	public boolean sendCommand(String sessionId, CommunicatType type,
//			String command) {
//		logger.info("[发送指令]发送：" + command);
//		byte[] b = ConvertUtil.strToToHexByte(command);
//		Boolean isSend = Servers.tCPHandler.send(sessionId, type, b);
//		return isSend;
//	}
//
//	@Override
//	public void initEquipConnect(String sessionId, CommunicatType type,
//			String receivecommand) {
//		// 解析指令
//		String liuliang = "";// 得到充值流量
//		String iccid = "";// 获取设备号
//		String[] commands = receivecommand.split("\\&");
//		for (int i = 0; i < commands.length; i++) {
//			if (StringUtils.equalsIgnoreCase(commands[i], "liuliang")) {
//				String[] v1 = commands[i].split("\\=");
//				liuliang = v1[1];
//			} else if (StringUtils.equalsIgnoreCase(commands[i], "ICCID")) {
//				String[] v1 = commands[i].split("\\=");
//				iccid = v1[1];
//			}
//		}
//
//		//通知应用层
//		if(StringUtils.isNotBlank(liuliang) && StringUtils.isNotBlank(iccid)){
//			TCPHandler.equipmentSession.put(sessionId, iccid);//添加会话
//			//更新设备状态
//			seniorManagementService.updateEquipment(iccid, Integer.parseInt(liuliang), "正常");
//			//获取需要充值的流量,并回复
//			Integer payLiuliang = seniorManagementService.getPayFlow(iccid);
//			String receiveMsg = "&liuliang=" + Manager.getLiuliang(payLiuliang);// 充值2M流量
//			if(!this.sendCommand(sessionId, type, receiveMsg)){
//				//充值失败,归还充值流量
//				seniorManagementService.payBackFlow(iccid, payLiuliang);
//			}
//		}else{
//			String receiveMsg = "&liuliang=" + Manager.getLiuliang(0);// 充值2M流量
//			this.sendCommand(sessionId, type, receiveMsg);
//		}
//	}
//	
//	@Override
//	public void reportConnectState(String sessionId,String status){
//		String number = TCPHandler.equipmentSession.get(sessionId);//获取当前会话对应设备号
//		seniorManagementService.updateEquipmentStatus(number, status);//向业务层提供状态
//		TCPHandler.equipmentSession.remove(sessionId);//移除当前会话
//	}
//
//	@Override
//	public void reportConnect(String code,String flag,String sessionId, String msgString) {
//		CommonCommand commonCommand = new CommonCommand();
//		commonCommand.parse(msgString);
////		CommonCommand.parseData(commonCommand.getData());
//		ReportDTU reportDtu = new ReportDTU();
//		reportDtu.setEquipmenrtNumber(flag);
//		reportDtu.setValuess(CommonCommand.parseData(commonCommand.getData()));
//		reportDtu.setReply(msgString);
//		reportDtuService.save(reportDtu);
//		
//	}
//	
//	
//
//}
