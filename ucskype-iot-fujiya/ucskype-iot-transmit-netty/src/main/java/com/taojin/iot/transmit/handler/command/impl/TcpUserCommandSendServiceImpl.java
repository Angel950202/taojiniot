//package com.taojin.iot.transmit.handler.command.impl;
//
//import java.util.List;
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
//import com.taojin.iot.service.report.entity.ReportEquipmentSensor;
//import com.taojin.iot.service.report.service.ReportEquipmentSensorService;
//import com.taojin.iot.service.report.service.ReportRealTimeSensorService;
//import com.taojin.iot.transmit.Servers;
//import com.taojin.iot.transmit.bean.CommonCommand;
//import com.taojin.iot.transmit.handler.command.TcpUserCommandSendService;
//import com.taojin.iot.transmit.lib.CommunicatType;
//import com.taojin.iot.transmit.module.entity.TransmitSession;
//import com.taojin.iot.transmit.module.service.TransmitSessionService;
//import com.taojin.iot.transmit.utils.ConvertUtil;
//import com.taojin.iot.transmit.utils.StringPlitUtils;
//
///**
// * 指令-iot平台-通用
// * 
// * @author wangjie
// *
// */
//@Service("tcpUserCommandSendServiceImpl")
//public class TcpUserCommandSendServiceImpl implements TcpUserCommandSendService {
//	final static Logger logger = LoggerFactory
//			.getLogger(TcpUserCommandSendServiceImpl.class);
//	@Resource(name = "transmitSessionServiceImpl")
//	private TransmitSessionService transmitSessionService = (TransmitSessionService) SpringUtils
//			.getBean("transmitSessionServiceImpl");
//	
//	/**
//	 * 指令发送
//	 * 
//	 * @param sessionId
//	 * @param type
//	 * @param command
//	 * @return
//	 */
//	@Override
//	public boolean sendCommand(String sessionId, CommunicatType type,
//			String command) {
//		logger.info("[发送指令]---->发送：communicatType={},command={},session="+sessionId+"",type.name(),command);
//		byte[] b = ConvertUtil.strToToHexByte(command);
//		Boolean isSend = Servers.tcpUserHandler.send(sessionId, type, b);
//		logger.info("[发送指令]---->发送结果：isSend={},command={},session="+sessionId+"",isSend,command);
//		return isSend;
//	}
//
//	/**
//	 * 获取公共组包
//	 * 
//	 * @param receivecommand
//	 * @return
//	 */
//	public String getCommnCommand(String receivecommand, String controlCode) {
//		CommonCommand commandParse = new CommonCommand();
//		commandParse.parse(receivecommand);
//
//		String address = "00000000001000";
//		CommonCommand command = new CommonCommand("68", "44", address,
//				controlCode, commandParse.getData(), commandParse.getEnd());
//		return command.toString();
//	}
//
//	/**
//	 * 接收握手协议并回复
//	 * 
//	 * @param sessionId
//	 *            会话ID
//	 * @param type
//	 *            通信类型
//	 * @param receivecommand
//	 *            接收命令
//	 * @return
//	 */
//	@Override
//	public boolean sendShakHands(String sessionId, CommunicatType type,
//			String receivecommand) {
//		logger.info("[通信握手]---->收到：sessionId="+sessionId+",CommunicatType={},command={}",type.name(),receivecommand);
//		Boolean isSend = sendCommand(sessionId, type,
//				getCommnCommand(receivecommand, "02"));
//		logger.info("[通信握手]---->回复：sessionId="+sessionId+",CommunicatType={},isSend={}",type.name(),isSend);
//		return isSend;
//	}
//
//	@Override
//	public boolean sendHeart(String sessionId, CommunicatType type,
//			String receivecommand) {
//		logger.info("[心跳]---->收到:sessionId="+sessionId+",communicatType={},command={}",type.name(),receivecommand);
//		Boolean isSend = sendCommand(sessionId, type,
//				getCommnCommand(receivecommand, "06"));
//		logger.info("[心跳]---->回复：sessionId="+sessionId+",CommunicatType={},isSend={}",type.name(),isSend);
//		return isSend;
//	}
//
//	@Override
//	public boolean sendValve(String ccid, String address, CommunicatType type,
//			Integer state) {
//		logger.info("[开关阀控制]---->开始：ccid={},address={},state="+state+"",ccid,address);
//		TransmitSession transmitSession = transmitSessionService.getByParam(
//				"ccid", ccid);
//		if (transmitSession == null) {
//			logger.info("[开关阀控制]---->设备不在线:ccid={}",ccid);
//			return false;
//		}
//		address = "00000000001000";
//		String stateHex = "";
//		if (state == 0) {
//			stateHex = "01";
//		} else {
//			stateHex = "02";
//		}
//		CommonCommand command = new CommonCommand("68", "44", address, "07",
//				stateHex, "23");
//		Boolean isSend = sendCommand(transmitSession.getSessionId(), type,
//				command.toString());
//		logger.info("[开关阀控制]---->结果:ccid={},isSend={}",ccid,isSend);
//		return isSend;
//	}
//
//	@Override
//	public boolean receiveData(String sessionId, String command, CommunicatType type) {
//		logger.info("[数据处理]---->开始:sessionId={},command={}",sessionId,command);
//		TransmitSession transmitSession = transmitSessionService.getByParam(
//				"sessionId", sessionId);
//		// 解析
//		CommonCommand commandParse = new CommonCommand();
//		commandParse.parse(command);
//		if (transmitSession == null) {
//			logger.info("[数据处理]---->设备不在线:sessionId={},command={}",sessionId,command);
//			return false;
//		}
//		
//		String equipmentNumber = transmitSession.getCcid();//设备号
//		
//		ReportRealTimeSensorService reportRealTimeSensorService = (com.taojin.iot.service.report.service.ReportRealTimeSensorService) SpringUtils
//				.getBean("reportRealTimeSensorServiceImpl");
//		ReportEquipmentSensorService reportEquipmentSensorService = (ReportEquipmentSensorService) SpringUtils.getBean("reportEquipmentSensorServiceImpl");
//
//		String dataArea = commandParse.getData();
//		
//		List<String> dataAreas = StringPlitUtils.getStrList(dataArea, 4);
//		for(int i=0;i<dataAreas.size();i++){
//			String dataAreaa = dataAreas.get(i);
//			logger.info("[数据处理]---->第"+i+"条:dataArea={}",dataAreaa);
//		/*	JSONObject jsonData = CommonCommand.parseData(dataAreaa);
//
//			reportRealTimeSensorService.addData(jsonData.optString("数据"),
//					jsonData.optInt("序号"));
//			
//			ReportEquipmentSensor reportEquipmentSensor = new ReportEquipmentSensor();
//			reportEquipmentSensor.setSensorTrueValue(jsonData.optString("数据"));
//			reportEquipmentSensorService.addData(reportEquipmentSensor, null, jsonData.optInt("序号"), equipmentNumber);
//			logger.info("[数据处理]---->第"+i+"条完成:数据={},序号={}",jsonData.optString("数据"),jsonData.optInt("序号"));
//	*/	}
//		return true;
//	}
//
//}
