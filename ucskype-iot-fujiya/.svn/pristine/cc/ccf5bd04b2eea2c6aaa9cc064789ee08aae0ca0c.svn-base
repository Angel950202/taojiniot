package com.taojin.iot.transmit.lib.socket.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taojin.iot.transmit.lib.CommunicatType;
import com.taojin.iot.transmit.lib.MessageHandle;
import com.taojin.iot.transmit.lib.socket.SocketServer;

public class ServerUserUdpHandler extends
		SimpleChannelInboundHandler<DatagramPacket> {
	final static Logger logger = LoggerFactory
			.getLogger(ServerUdpHandler.class);

	private static Map<String, DatagramPacket> packetMap = new HashMap<String, DatagramPacket>();

	private MessageHandle messageService;

	public ServerUserUdpHandler(MessageHandle messageService) {
		this.messageService = messageService;
	}

	/** 粘包处理 */
	public static Map<String, String> parsePackageMap = new HashMap<String, String>();

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
	 * 获取packet
	 * 
	 * @param sessionId
	 * @return
	 */
	public static InetSocketAddress getPacket(Channel channel) {
		DatagramPacket packets = packetMap.get(channel.id().toString());
		return packets.sender();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet)
			throws Exception {
		packetMap.put(ctx.channel().id().toString(), packet);
		SocketServer.channels.put(ctx.channel().id().toString(), ctx.channel());
		// 读取收到的数据
		ByteBuf buf = (ByteBuf) packet.copy().content();
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String msgString = Hex.encodeHexString(req);
//		String msgWoshou = new String(req);

		logger.info("【UDP-USER】收到消息: " + msgString);
//		logger.info("【UDP-USER】收到消息: " + msgWoshou);
		messageService.receiveBytes(ctx.channel().id().toString(),
				CommunicatType.USERUDP, msgString.getBytes(), ctx);
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

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.info("【UDP】" + ctx.channel() + "异常关闭:" + cause.getMessage());
		System.out.println("【UDP】" + ctx.channel() + "异常关闭:"
				+ cause.getMessage());
		cause.printStackTrace();
		String sessionId = ctx.channel().id().toString();
		SocketServer.channels.remove(sessionId);
		parsePackageMap.remove(sessionId);// 清除当前会话包队列
		messageService.sessionClosed(sessionId);
		ctx.close();
	}

	/**
	 * 会话关闭时触发
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		logger.info("关闭客户端" + ctx.channel().id().toString());
		SocketServer.channels.remove(ctx.channel().id().toString());
		messageService.sessionClosed(ctx.channel().id().toString());
		parsePackageMap.remove(ctx.channel().id().toString());// 清除当前会话包队列
		ctx.close();
	}
}
