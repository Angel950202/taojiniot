package com.taojin.iot.transmit.lib.socket.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taojin.iot.transmit.lib.CommunicatType;
import com.taojin.iot.transmit.lib.MessageHandle;
import com.taojin.iot.transmit.lib.socket.SocketServer;

/**
 * 用户中心-tcp处理
 * @author wangjie
 *
 */
public class ServerUserTcpHandler extends ChannelInboundHandlerAdapter{

	private static final Logger logger = LogManager.getLogger(ServerUserTcpHandler.class);
	
	private AtomicInteger idleTime = new AtomicInteger(0);
	
	private MessageHandle messageService;
	
	private String hreatMsg;
	/** 粘包处理 */
	public static Map<String, String> parsePackageMap = new HashMap<String, String>();
	
	public ServerUserTcpHandler(String hreatMsg,MessageHandle messageService) {
		this.messageService = messageService;
		this.hreatMsg = hreatMsg;
	}
	
	/**
	 * 有客户端注册连接时触发
	 */
	@Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		logger.info("客户端: "  + ctx.channel().id().toString() + "连接注册");
		parsePackageMap.remove(ctx.channel().id().toString());// 清除当前会话包队列
		SocketServer.channels.put(ctx.channel().id().toString(), ctx.channel());
    }
	
	/**
	 * 接收信息时触发
	 */
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//		ByteBuf in = (ByteBuf) msg;
//		byte[] receiveMsgBytes = new byte[in.readableBytes()];
//		in.readBytes(receiveMsgBytes);
//		String msgString = Hex.encodeHexString(receiveMsgBytes);
////		String msgString = new String(receiveMsgBytes);
//		
////		logger.info("【TCP-USER】收到消息: " + msgString);
////		logger.info("Socket接收数据: "  + in.toString(CharsetUtil.UTF_8));
////		messageService.receive(ctx.channel().id().toString(), CommunicatType.SOCKET, in.toString(CharsetUtil.UTF_8));
//		messageService.receiveBytes(ctx.channel().id().toString(), CommunicatType.USERTCP, msgString.getBytes(),ctx);
		
		ByteBuf buf = (ByteBuf) msg;
		String str = buf.toString(CharsetUtil.ISO_8859_1);
		try {
			messageService.receiveBytes(ctx.channel().id().toString(),
					CommunicatType.USERTCP, str.getBytes("ISO_8859_1"), ctx);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}finally{
			ctx.flush();
		}
		
    }
	
	/**
	 * 事件处理
	 */
	@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;  
			if (event.state() == IdleState.READER_IDLE) {
        		logger.info(ctx.channel() + "服务器读空闲");
        		idleTime.incrementAndGet();
        		if(idleTime.get() > 1){
                	logger.info(ctx.channel() + "心跳无回复,关闭连接");
            		this.handlerRemoved(ctx);
                }else if (idleTime.get() > 0) {
            		logger.info("服务器向客户端"+ ctx.channel().id().toString() + "发送心跳,检测是否在线");
            		ByteBuf out = Unpooled.buffer();
            		ctx.channel().writeAndFlush(out.writeBytes(hreatMsg.getBytes()));
                }
            }
		}else{
			ctx.fireUserEventTriggered(evt);
		}
    }

	/**
	 * 将未决消息冲刷到远程节点，并且关闭该 Channel
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
//		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
//		.addListener(ChannelFutureListener.CLOSE);
		ctx.fireChannelReadComplete();
	}
	
	/**
	 * 异常关闭处理
	 */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//		logger.info(ctx.channel() +"异常关闭:"+ cause.getMessage());
//    	cause.printStackTrace();
//    	String sessionId = ctx.channel().id().toString();
//		SocketServer.channels.remove(sessionId);
//		parsePackageMap.remove(sessionId);// 清除当前会话包队列
//		messageService.sessionClosed(sessionId);
//        ctx.close();
    	logger.info(ctx.channel() + "异常原因:" + cause.getMessage());
		cause.printStackTrace();
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
