package com.taojin.iot.transmit.lib.websocket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taojin.iot.transmit.lib.CommunicatType;
import com.taojin.iot.transmit.lib.MessageHandle;
import com.taojin.iot.transmit.lib.websocket.WebSocketServer;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	
	private static final Logger logger = LogManager.getLogger(TextWebSocketFrameHandler.class);

	private AtomicInteger idleTime = new AtomicInteger(0);
	
	private MessageHandle messageService;
	
	private String hreatMsg;
	
	public TextWebSocketFrameHandler(String hreatMsg, MessageHandle messageService) {
		this.messageService = messageService;
		this.hreatMsg = hreatMsg;
	}

	/**
	 * 事件状态处理
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
			logger.info("客户端"+ ctx.channel().id().toString() + "握手成功");
			ctx.pipeline().remove(HttpRequestHandler.class);
			WebSocketServer.channels.put(ctx.channel().id().toString(), ctx.channel());
		} else if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;  
            if (event.state() == IdleState.READER_IDLE) {
        		logger.info(ctx.channel() + "服务器读空闲");
        		idleTime.incrementAndGet();
        		if(idleTime.get() > 1){
                	logger.info(ctx.channel() + "心跳无回复,关闭连接");
            		this.handlerRemoved(ctx);
                }else if (idleTime.get() > 0) {
            		logger.info("服务器向客户端"+ ctx.channel().id().toString() + "发送心跳,检测是否在线");
                	ctx.channel().writeAndFlush(new TextWebSocketFrame(hreatMsg));
                } 
            }
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}
	
	/**
	 * 接收数据处理
	 */
	@Override
	public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		logger.info("来自客户端" + ctx.channel().id().toString() + "文本数据："+msg.retain().text());
		messageService.receive(ctx.channel().id().toString(), CommunicatType.WEBSOCKET, msg.retain().text(),ctx);
	}
	
	/**
	 * 会话关闭时触发
	 */
	@Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		logger.info("关闭客户端" + ctx.channel());
		WebSocketServer.channels.remove(ctx.channel().id().toString());
		ctx.close();
    }
}
