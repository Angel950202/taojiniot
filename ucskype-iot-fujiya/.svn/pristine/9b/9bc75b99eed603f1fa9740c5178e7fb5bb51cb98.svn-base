package com.taojin.iot.transmit.lib;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import com.taojin.iot.transmit.lib.socket.SocketServer;
import com.taojin.iot.transmit.lib.socket.handler.ServerUdpHandler;
import com.taojin.iot.transmit.lib.socket.handler.ServerUserNbUdpHandler;
import com.taojin.iot.transmit.lib.socket.handler.ServerUserUdpHandler;
import com.taojin.iot.transmit.lib.websocket.WebSocketServer;

public abstract class MessageHandle {

	/**
	 * 接收信息业务处理
	 * 
	 * @param sessionId
	 *            会话id
	 * @param communicatType
	 *            通信类型
	 * @param msg
	 *            接收信息
	 */
	public abstract void receive(String sessionId,
			CommunicatType communicatType, String msg, ChannelHandlerContext ctx);

	/**
	 * 发送信息业务处理
	 * 
	 * @param sessionId
	 *            会话id
	 * @param communicatType
	 *            通信类型
	 * @param msg
	 *            接收信息
	 * @return
	 */
	public boolean send(String sessionId, CommunicatType communicatType,
			String msg) {

		boolean result = false;

		switch (communicatType) {
		case SOCKET:
			result = this.dtuockeSend(sessionId, msg);
			break;
		case WEBSOCKET:
			result = this.webSockeSend(sessionId, msg);
			break;
		case SOCKETUDP:
			result = this.sockeUDPSend(sessionId, msg.getBytes());
		default:
		}

		return result;
	}

	/**
	 * 16进制发送
	 * 
	 * @param sessionId
	 * @param communicatType
	 * @param msg
	 * @return
	 */
	public boolean send(String sessionId, CommunicatType communicatType,
			byte[] msg) {

		boolean result = false;

		switch (communicatType) {
		case SOCKET:
			result = this.sockeSend(sessionId, msg);
			break;
		case SOCKETUDP:
			result = this.sockeUDPSend(sessionId, msg);
			break;
		case USERTCP:
			result = this.sockeUserTcpSend(sessionId, msg);
			break;
		case USERUDP:
			result = this.sockeUserUdpSend(sessionId, msg);
			break;
		case USERNBUDP:
			result = this.sockeUserNbUdpSend(sessionId, msg);
			break;
		default:
		}

		return result;
	}
	
	
	public boolean dtuockeSend(String sessionId, String msg) {
		Channel channel = SocketServer.channels.get(sessionId);
		if (channel == null) {
			return false;
		}
		channel.writeAndFlush(msg);
		return true;
	}
	
	
	
	
	public boolean sockeRTUTCPSend(String sessionId, byte[] msg) {
		Channel channel = SocketServer.channels.get(sessionId);
		if (channel == null) {
			return false;
		}
		channel.writeAndFlush(Unpooled.buffer().writeBytes(msg));
		return true;
	}

	public boolean webSockeSend(String sessionId, String msg) {
		Channel channel = WebSocketServer.channels.get(sessionId);
		if (channel == null) {
			return false;
		}
		channel.writeAndFlush(new TextWebSocketFrame(msg));
		return true;
	}

	public boolean sockeSend(String sessionId, byte[] msg) {
		Channel channel = SocketServer.channels.get(sessionId);
		if (channel == null) {
			return false;
		}
		channel.writeAndFlush(Unpooled.buffer().writeBytes(msg));
		return true;
	}
	
	public boolean sockeUserTcpSend(String sessionId, byte[] msg) {
		Channel channel = SocketServer.channels.get(sessionId);
		if (channel == null) {
			return false;
		}
		channel.writeAndFlush(Unpooled.buffer().writeBytes(msg));
		return true;
	}

	/**
	 * udp发送
	 * 
	 * @param sessionId
	 * @param msg
	 * @return
	 */
	public boolean sockeUDPSend(String sessionId, byte[] msg) {
		Channel channel = SocketServer.channels.get(sessionId);
		if (channel == null) {
			System.out.println("通道丢失");
			return false;
		}

		try {
			channel.writeAndFlush(new DatagramPacket(Unpooled
					.wrappedBuffer(msg), ServerUdpHandler.getPacket(channel)));
			// channel.writeAndFlush(Unpooled.buffer().writeBytes(msg)).sync();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}
	
	public boolean sockeUserUdpSend(String sessionId, byte[] msg) {
		Channel channel = SocketServer.channels.get(sessionId);
		if (channel == null) {
			System.out.println("通道丢失");
			return false;
		}

		try {
			channel.writeAndFlush(new DatagramPacket(Unpooled
					.wrappedBuffer(msg), ServerUserUdpHandler.getPacket(channel)));
			// channel.writeAndFlush(Unpooled.buffer().writeBytes(msg)).sync();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}
	
	public boolean sockeUserNbUdpSend(String sessionId, byte[] msg) {
		Channel channel = SocketServer.channels.get(sessionId);
		if (channel == null) {
			System.out.println("通道丢失");
			return false;
		}

		try {
			channel.writeAndFlush(new DatagramPacket(Unpooled
					.wrappedBuffer(msg), ServerUserNbUdpHandler.getPacket(sessionId)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}
	
	public boolean sockeRTUUDPSend(String sessionId, byte[] msg) {
		Channel channel = SocketServer.channels.get(sessionId);
		if (channel == null) {
			System.out.println("通道丢失");
			return false;
		}

		try {
			channel.writeAndFlush(new DatagramPacket(Unpooled
					.wrappedBuffer(msg), ServerUdpHandler.getPacket(channel)));
			// channel.writeAndFlush(Unpooled.buffer().writeBytes(msg)).sync();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * 接收信息业务处理
	 * 
	 * @param sessionId
	 *            会话id
	 * @param communicatType
	 *            通信类型
	 * @param msg
	 *            接收信息
	 */
	public abstract void receiveBytes(String sessionId,
			CommunicatType communicatType, byte[] msg, ChannelHandlerContext ctx);

	/**
	 * 断开连接
	 * 
	 * @param sessionId
	 */
	public abstract void sessionClosed(String sessionId);

}
