package com.taojin.iot.transmit.handler;

import io.netty.channel.ChannelHandlerContext;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.taojin.iot.base.comm.utils.SpringUtils;
import com.taojin.iot.redis.JedisUtil;
import com.taojin.iot.redis.JedisUtil.Hash;
import com.taojin.iot.transmit.Servers;
import com.taojin.iot.transmit.handler.command.CommandFujiyaService;
import com.taojin.iot.transmit.lib.CommunicatType;
import com.taojin.iot.transmit.lib.MessageHandle;
import com.taojin.iot.transmit.utils.CRC16M;
import com.taojin.iot.transmit.utils.ConvertUtil;
import com.taojin.iot.transmit.utils.HexUtil;

@Component("tCPHandler")
public class TCPHandler extends MessageHandle {

	final static Logger logger = LoggerFactory.getLogger(TCPHandler.class);

	/** 连接会话 */
	public static Map<String, String> equipmentSession = new ConcurrentHashMap<>();
	
	
	public static String SESSION_ID = null;
	public static String flag = null;
//	private ManagerCommandService managerCommandService = (com.taojin.iot.transmit.handler.command.ManagerCommandService) SpringUtils
//			.getBean("managerCommandServiceImpl");
	private String HAND_SHAKE_RES = "6844AAAAAAAAAAAAAA0216383938363034303231303138343031363537393402589BBA23";

	private String HEART_BEAT_MSG = "6844383630343032310500092F23";
										 
	private String HEART_BEAT_MSG2 = "68443836303430323108020001252023";
	
	private String HEART_BEAT_RES = "6844AAAAAAAAAAAAAA060568656C6C6FC14C23";

	@Override
	public void receive(String sessionId, CommunicatType communicatType,
			String msg, ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveBytes(String sessionId, CommunicatType communicatType,
			byte[] msg, ChannelHandlerContext ctx) {
		logger.info("收到设备{}的消息:{}", sessionId, Arrays.toString(msg));
		String msgString = null;
		msgString = DatatypeConverter.printHexBinary(msg);
		String controCode = msgString.substring(18,20);//获取控制码
		JedisUtil jedisUtil = new JedisUtil();
		Hash hash = jedisUtil.HASH;
		if(StringUtils.equalsIgnoreCase(controCode, "01")){//握手
			logger.info("收到设备{}的握手消息:{}", sessionId, msgString);
			String iccid = msgString.substring(22,msgString.length()-6);
			if(StringUtils.endsWithIgnoreCase(iccid, "003c")){
				iccid = iccid.substring(0,iccid.length() - 4);
			}
			hash.hset("equipment_session_equipmentId", iccid, sessionId);//注册设备ID
			hash.hset("equipment_session_sessionId", sessionId, iccid);//注册会话ID
			
			if(StringUtils.equalsIgnoreCase(iccid, "3839383630343032313031383430313634303334")){//E-pump2.0 Main生产线
				hash.hset("equipment_equipment_type", iccid, "EPUMPMAIN");
			}else if(StringUtils.equalsIgnoreCase(iccid, "3839383630343032313031383430313631323436")){//EPUMP-2Gearless
				hash.hset("equipment_equipment_type", iccid, "EPUMPGEAR");
			}else if(StringUtils.equalsIgnoreCase(iccid, "3839383630343032313031383430313631343531")){//RC70-1
				hash.hset("equipment_equipment_type", iccid, "RC701");
			}else if(StringUtils.equalsIgnoreCase(iccid, "3839383630343031313031373930313735363230")){//RC77-1
				hash.hset("equipment_equipment_type", iccid, "RC771");
			}
			//测试设备
			else if(StringUtils.equalsIgnoreCase(iccid, "3839383630343032313031383430313634343037")){
				//test-rc70-1
				hash.hset("equipment_equipment_type", iccid, "DTU_TEST");
			}
			else{
				logger.info("[握手]---->登记设备类型失败,未注册过");
			}
			hash.hset("equipment_state",iccid,"1");
			Servers.tCPHandler.send(sessionId, CommunicatType.SOCKET,HexUtil.toByteArray(HAND_SHAKE_RES));
		}else if(StringUtils.equalsIgnoreCase(controCode, "05")){//心跳
			logger.info("收到设备{}的心跳消息:{}", sessionId, msgString);
			String iccid = hash.hget("equipment_session_sessionId", sessionId);//获取会话ID
			if(StringUtils.isBlank(iccid)){
				logger.info("收到设备{}的心跳消息:{},会话丢失,不回复", sessionId, msgString);
				return;
			}
			String type = hash.hget("equipment_equipment_type", iccid);//获取会话ID
			if(StringUtils.isBlank(type)){
				logger.info("收到设备{}的心跳消息:{},iccid类型丢失,不回复", sessionId, msgString);
				return;
			}
			String sids = hash.hget("equipment_session_equipmentId", iccid);
			if(StringUtils.isBlank(sids)){
				logger.info("收到设备{}的心跳消息:{},sid类型丢失,不回复", sessionId, msgString);
				return;
			}
			
			//回复心跳
			Servers.tCPHandler.send(sessionId, CommunicatType.SOCKET,
					HexUtil.toByteArray(HEART_BEAT_RES));
		}else if(StringUtils.equalsIgnoreCase(controCode, "08")){//回复数据
			logger.info("收到设备{}的回复消息:{}", sessionId, msgString);
			String dataArea = msgString.substring(22,26);
			if(StringUtils.equalsIgnoreCase(dataArea, "0002")){
				logger.info("收到设备{}的回复消息:{},非PLC回复,忽略", sessionId, msgString);
				return;
			}
			//记录接收值
			String field = hash.hget("msgSendState", sessionId);//获取当前会话在执行的指令集
			hash.hset(sessionId+"_done", field, msgString);//更新当前任务完成情况
//			hash.hdel("msgSendState",sessionId);//清除当前会话接收状态
			//解析值,记录到redis
			CommandFujiyaService commandFujiyaService = (CommandFujiyaService) SpringUtils.getBean("commandFujiyaServiceImpl");
//			commandFujiyaService.parseCommandToInt(sessionId, field, msgString);
			commandFujiyaService.parseCommandToUlong(sessionId, field, msgString);
		}else{
			logger.info("收到设备{}的非法消息:{}", sessionId, msgString);
		}
	}

	@Override
	public void sessionClosed(String sessionId) {
//		logger.info("设备{}连接中断", sessionId);
//		equipmentSession.remove(sessionId);
//		JedisUtil jedisUtil = new JedisUtil();
//		Hash hash = jedisUtil.HASH;
//		hash.hdel("msgSendState",sessionId);//清除接收状态
//		hash.hdel(sessionId+"_done");//清除接收数据
//		String ccid = hash.hget("equipment_session_sessionId", sessionId);
//		
//		//ccid会有空值
//		if (StringUtils.isNotBlank(ccid)) {
//			hash.hdel("equipment_session_equipmentId",ccid);
//			hash.hset("equipment_state",ccid,"0");
//		}
//		if (StringUtils.isNotBlank(sessionId)) {
//			hash.hdel("equipment_session_sessionId", sessionId);//注册会话ID
//			hash.hdel("msgSendState",sessionId);//清除当前会话接收状态
//		}
		logger.info("设备{}连接中断", sessionId);
		equipmentSession.remove(sessionId);
		JedisUtil jedisUtil = new JedisUtil();
		Hash hash = jedisUtil.HASH;
		hash.hdel("msgSendState",sessionId);//清除接收状态
		hash.hdel(sessionId+"_done");//清除接收数据
		String ccid = hash.hget("equipment_session_sessionId", sessionId);
		if(StringUtils.isBlank(ccid)){
			return;
		}
		
		String nowSessionId = hash.hget("equipment_session_equipmentId",ccid);
		if(StringUtils.equalsIgnoreCase(nowSessionId, sessionId)){//同一批会话
			hash.hdel("equipment_session_equipmentId",ccid);
			hash.hdel("equipment_equipment_type",ccid);
			hash.hdel("taskLock",ccid);
			hash.hset("equipment_state",ccid,"0");
		}
		
		hash.hdel("equipment_session_sessionId", sessionId);//注册会话ID
		hash.hdel("msgSendState",sessionId);//清除当前会话接收状态
	}
	
	/**
	 * 发送消息
	 * @param sessionId 会话ID 
	 * @param field 对应域
	 * @param value 对应值
	 * @return
	 */
	public static boolean sendMsgByField(String sessionId,String field,String value){
		JedisUtil jedisUtil = new JedisUtil();
		Hash hash = jedisUtil.HASH;
		hash.hset("msgSendState", sessionId, field);//重置当前会话发送状态
		value = StringUtils.replace(value, " ", "");
		Integer dataLength = (value.length() /2);
		String msg ="6844AAAAAAAAAAAAAA07"+ConvertUtil.int2hex(dataLength)+value;
		String crc = CRC16M.doCrc16(msg);
		String message = msg + crc +"23";
		System.out.println("[数据下发]---->sessionid="+sessionId+",信号位={"+field+"},数据={"+message+"}");
		boolean isSend = Servers.tCPHandler.send(sessionId,
				CommunicatType.SOCKET,
				HexUtil.toByteArray(message));
		return isSend;
	}

	/**
	 * dtu指令处理
	 * 
	 * @param sessionId
	 *            会话ID
	 * @param communicatType
	 *            通信类型
	 * @param command
	 *            命令
	 * @return
	 */
	public void dtuHandlerCommand(String sessionId,
			CommunicatType communicatType, String command) {
//		managerCommandService.initEquipConnect(sessionId, communicatType,
//				command);
	}

	/**
	 * 指令处理
	 * 
	 * @param sessionId
	 *            会话ID
	 * @param communicatType
	 *            通信类型
	 * @param command
	 *            命令
	 * @return
	 */
	public boolean handlerCommand(String sessionId,
			CommunicatType communicatType, String command) {
//		managerCommandService.initEquipConnect(sessionId, communicatType,
//				command);
		return false;
	}

}
