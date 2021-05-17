package com.taojin.iot.transmit.lib.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

import com.taojin.iot.transmit.lib.IdleConfig;
import com.taojin.iot.transmit.lib.MessageHandle;
import com.taojin.iot.transmit.lib.socket.handler.ServerUserTcpHandler;

/**
 * 用户中心-tcp初始化
 * @author wangjie
 *
 */
public class SocketUSERTCPServerInitializer extends ChannelInitializer<SocketChannel>{
private IdleConfig config;
	
	private MessageHandle messageService;
	
	public SocketUSERTCPServerInitializer(IdleConfig config,MessageHandle messageService){
		this.config = config;
		this.messageService = messageService;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		//设置心跳
		pipeline.addLast(new IdleStateHandler(config.getReaderIdleTime(), config.getWriterIdleTime(), config.getAllIdleTime(), TimeUnit.SECONDS));//空闲时间
		//设置消息处理 业务
		pipeline.addLast(new ServerUserTcpHandler(config.getHeartMsg(),messageService));
	}
}
