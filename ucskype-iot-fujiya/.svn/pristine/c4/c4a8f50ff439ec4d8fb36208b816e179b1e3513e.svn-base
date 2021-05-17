package com.taojin.iot.transmit.lib.websocket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	
	private static final Logger logger = LogManager.getLogger(HttpRequestHandler.class);
	
	private final String wsUri;

	public HttpRequestHandler(String wsUri) {
		this.wsUri = wsUri;
	}

	/**
	 * 接收信息处理
	 */
	@Override
	public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		String uri = request.uri();
		if (wsUri.equalsIgnoreCase(uri)) {
			logger.info("WebSocket请求握手");
			ctx.fireChannelRead(request.retain());
		} else {
			logger.info("HttpRequest请求,关闭连接");
			ctx.close();
		}
	}
	
	/**
	 * 异常关闭处理
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}