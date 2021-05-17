//package com.taojin.iot.transmit.handler;
//
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelHandlerContext;
//
//import java.io.UnsupportedEncodingException;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.taojin.iot.base.comm.utils.SpringUtils;
//import com.taojin.iot.transmit.bean.CommonCommand;
//import com.taojin.iot.transmit.handler.command.TcpUserCommandSendService;
//import com.taojin.iot.transmit.lib.CommunicatType;
//import com.taojin.iot.transmit.lib.MessageHandle;
//import com.taojin.iot.transmit.lib.socket.SocketServer;
//import com.taojin.iot.transmit.module.service.TransmitSessionService;
//import com.taojin.iot.transmit.utils.ConvertUtil;
//import com.taojin.iot.transmit.utils.HexUtil;
//
///**
// * 指令处理
// * @author wangjie
// *
// */
//public class TCPUserHandler extends MessageHandle {
//	
//	final static Logger logger = LoggerFactory.getLogger(TCPUserHandler.class);
//
//	private TcpUserCommandSendService tcpUserCommandSendService = (com.taojin.iot.transmit.handler.command.TcpUserCommandSendService) SpringUtils.getBean("tcpUserCommandSendServiceImpl");
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
//		
//		this.handlerCommand(sessionId, communicatType, msgString);
//	}
//
//	@Override
//	public void sessionClosed(String sessionId) {
//		transmitSessionService.removeBind(sessionId);//解除绑定
//	}
//	
//	/**
//	 * 命令处理
//	 * @param sessionId 会话ID
//	 * @param type 通信类型
//	 * @param command 命令
//	 * @return
//	 */
//	public boolean handlerCommand(String sessionId,CommunicatType type,String command){
//		CommonCommand commandParse = new CommonCommand();
//		commandParse.parse(command);
//		String controlCode = commandParse.getControlCode();
//		
//		if(StringUtils.equalsIgnoreCase(controlCode, "01")){//握手指令
//			logger.info("[收到数据]收到握手指令,command={}",command);
//			boolean isSend = tcpUserCommandSendService.sendShakHands(sessionId, type, command);
//			if(isSend){
//				String ccid = commandParse.getData().substring(0,commandParse.getData().length() - 4);//获取ccid
//				String heartbeat = HexUtil.hexDesc(commandParse.getData().substring(commandParse.getData().length() - 2));
//				transmitSessionService.bind(sessionId, ConvertUtil.hex2Str(ccid),CommonCommand.hex2Int(heartbeat));//绑定
//			}
//			return isSend;
//		}else if(StringUtils.equalsIgnoreCase(controlCode, "05")){//心跳指令
//			logger.info("[收到数据]收到心跳指令,command={}",command);
//			return tcpUserCommandSendService.sendHeart(sessionId, type, command);
//		}else if(StringUtils.equalsIgnoreCase(controlCode, "03")){//数据传输指令
//			tcpUserCommandSendService.receiveData(sessionId,command, type);
//		}else if(StringUtils.equalsIgnoreCase(controlCode, "08")){//数据传输指令
//			logger.info("[收到数据]下发控制指令回复,command={}",command);
//		}
//		return false;
//	}
//
//}
