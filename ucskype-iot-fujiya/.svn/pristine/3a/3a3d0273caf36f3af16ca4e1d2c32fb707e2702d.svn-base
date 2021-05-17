//package com.taojin.iot.transmit.handler;
//
//import java.io.UnsupportedEncodingException;
//
//import net.sf.json.JSONObject;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import io.netty.channel.ChannelHandlerContext;
//
//import com.taojin.iot.base.comm.utils.SpringUtils;
//import com.taojin.iot.transmit.lib.CommunicatType;
//import com.taojin.iot.transmit.lib.MessageHandle;
//import com.taojin.iot.transmit.module.service.TransmitSessionService;
//
///**
// * 用户中心-nb消息处理
// * @author wangjie
// *
// */
//public abstract class UserNbHandler extends MessageHandle {
//
//	final static Logger logger = LoggerFactory.getLogger(UserNbHandler.class);
//	private TransmitSessionService transmitSessionService = (TransmitSessionService) SpringUtils.getBean("transmitSessionServiceImpl");
//	@Override
//	public void receive(String sessionId, CommunicatType communicatType,
//			String msg, ChannelHandlerContext ctx) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void receiveBytes(String sessionId, CommunicatType communicatType,
//			byte[] msg, ChannelHandlerContext ctx) {
//		String msgString = null;
//		try {
//			msgString = new String(msg, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		logger.info("收到消息:【{}】,sessionId={}", msgString, sessionId);
//		logger.info("消息类型:【{}】,sessionId={}", communicatType.name(), sessionId);
//		handlerCommand(sessionId,communicatType,msgString);
//	}
//
//	@Override
//	public void sessionClosed(String sessionId) {
//		transmitSessionService.removeBind(sessionId);
//	}
//	
//	/**
//	 * 指令处理
//	 * @param sessionId 会话ID
//	 * @param type 通信类型
//	 * @param command 命令
//	 * @return
//	 */
//	public boolean handlerCommand(String sessionId,CommunicatType type,String command){
//		JSONObject jsonParse = new JSONObject();
//		try{
//			jsonParse = JSONObject.fromObject(command);
//		}catch(Exception e){
//			logger.info("[收到数据]解析失败:"+command);
//		}
//		
//		String imei = jsonParse.optString("imei");
//		if(StringUtils.isNotBlank(imei)){
//			transmitSessionService.bind(sessionId,imei,0);//绑定
//		}
//		return false;
//	}
//	
//
//}
