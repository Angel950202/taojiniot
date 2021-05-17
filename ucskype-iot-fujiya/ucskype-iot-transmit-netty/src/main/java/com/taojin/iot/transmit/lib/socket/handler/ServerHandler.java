package com.taojin.iot.transmit.lib.socket.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taojin.iot.transmit.lib.CommunicatType;
import com.taojin.iot.transmit.lib.MessageHandle;
import com.taojin.iot.transmit.lib.socket.SocketServer;

public class ServerHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LogManager
			.getLogger(ServerHandler.class);

	private AtomicInteger idleTime = new AtomicInteger(0);

	private MessageHandle messageService;

	private String hreatMsg;
	/** 粘包处理 */
	public static Map<String, String> parsePackageMap = new HashMap<String, String>();

	public ServerHandler(String hreatMsg, MessageHandle messageService) {
		this.messageService = messageService;
		this.hreatMsg = hreatMsg;
	}

	/**
	 * 消息包处理（断包，分包，粘包）
	 * 
	 * @param sessionId
	 *            会话ID
	 * @param msg
	 *            消息ID
	 */
	public String handlerPackage(String sessionId, String msg) {
		if (msg.startsWith("fefe68") && msg.endsWith("16")) {// 完整包
			return msg;
		}
		String mapHis = parsePackageMap.get(sessionId);// 获取历史
		if (StringUtils.isNotBlank(mapHis)) {
			parsePackageMap.put(sessionId, mapHis + msg);// 加入接收流中
		} else {
			parsePackageMap.put(sessionId, msg);// 加入接收流中
		}
		String nowSessionMap = parsePackageMap.get(sessionId);// 获取当前会话流中完整字符
		if (nowSessionMap.startsWith("fefe68") && nowSessionMap.endsWith("16")) {// 完整包
			parsePackageMap.remove(sessionId);
			return nowSessionMap;
		}
		return null;
	}

	/**
	 * 有客户端注册连接时触发
	 */
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		logger.info("客户端: " + ctx.channel().id().toString() + "连接注册");
		parsePackageMap.remove(ctx.channel().id().toString());// 清除当前会话包队列
		SocketServer.channels.put(ctx.channel().id().toString(), ctx.channel());
	}

	/**
	 * 接收信息时触发
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf buf = (ByteBuf) msg;
		String str = buf.toString(CharsetUtil.ISO_8859_1);
		try {
			messageService.receiveBytes(ctx.channel().id().toString(),
					CommunicatType.SOCKET, str.getBytes("ISO_8859_1"), ctx);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}finally{
			ctx.flush();
		}
	}

	/**
	 * 将未决消息冲刷到远程节点，并且关闭该 Channel
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		// ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
		// .addListener(ChannelFutureListener.CLOSE);
		ctx.fireChannelReadComplete();
	}

	/**
	 * 异常关闭处理
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.info(ctx.channel() + "异常原因:" + cause.getMessage());
		cause.printStackTrace();
//		String sessionId = ctx.channel().id().toString();
//		SocketServer.channels.remove(sessionId);
////		parsePackageMap.remove(sessionId);// 清除当前会话包队列
//		messageService.sessionClosed(sessionId);
//		ctx.close();
//		try {
//			this.handlerRemoved(ctx);
//		} catch (Exception e) {
//			logger.info(ctx.channel() + "异常原因:" + e.getMessage());
//			System.out.println(ctx.channel() + "异常原因:" + e.getMessage());
//			e.printStackTrace();
//		}
	}

	/**
	 * 会话关闭时触发
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		String sessionId = ctx.channel().id().toString();
		logger.info("关闭客户端" + sessionId);
		SocketServer.channels.remove(sessionId);
		messageService.sessionClosed(sessionId);
		parsePackageMap.remove(sessionId);// 清除当前会话包队列
		ctx.close();
	}
}
