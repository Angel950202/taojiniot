package com.taojin.iot.transmit.lib.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

import com.taojin.iot.transmit.lib.IdleConfig;
import com.taojin.iot.transmit.lib.MessageHandle;
import com.taojin.iot.transmit.lib.websocket.handler.HttpRequestHandler;
import com.taojin.iot.transmit.lib.websocket.handler.TextWebSocketFrameHandler;

public class WebSocketServerInitializer extends ChannelInitializer<Channel> {
	private IdleConfig config;
	
	private MessageHandle messageService;
	
	public WebSocketServerInitializer(IdleConfig config, MessageHandle messageService) {
		this.config = config;
		this.messageService = messageService;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new IdleStateHandler(config.getReaderIdleTime(), config.getWriterIdleTime(), config.getAllIdleTime(), TimeUnit.SECONDS));//空闲时间
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(64 * 1024));
		//处理握手 httpRequest请求
		pipeline.addLast(new HttpRequestHandler("/ws"));
		//处理webSocket通信
		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
		//处理文本信息
		pipeline.addLast(new TextWebSocketFrameHandler(config.getHeartMsg(),messageService));
	}
}
