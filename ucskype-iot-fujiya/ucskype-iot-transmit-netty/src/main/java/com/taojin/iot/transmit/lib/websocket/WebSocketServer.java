package com.taojin.iot.transmit.lib.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taojin.iot.transmit.lib.MessageHandle;
import com.taojin.iot.transmit.lib.ServerConfig;

public class WebSocketServer {

	private static final Logger			logger		= LogManager.getLogger(WebSocketServer.class);

	public static Map<String, Channel>	channels	= new HashMap<String, Channel>();

	private final EventLoopGroup		group		= new NioEventLoopGroup();
	private Channel						channel;
	private int							port;
	private ServerConfig				config;
	private MessageHandle				messageService;

	public WebSocketServer(ServerConfig config, MessageHandle messageService) {

		this.config = config;
		this.messageService = messageService;
		this.port = config.getPort();
	}

	/**
	 * 启动
	 * 
	 * @throws Exception
	 */
	public void start() throws Exception {

		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(group).channel(NioServerSocketChannel.class).childHandler(createInitializer());
		ChannelFuture future = null;
		future = bootstrap.bind(new InetSocketAddress(port));
		future.syncUninterruptibly();
		channel = future.channel();
		logger.info("WebSocket服务启动,监听" + port + "端口");
		future.channel().closeFuture().syncUninterruptibly();
		group.shutdownGracefully().sync();
	}

	protected ChannelInitializer<Channel> createInitializer() {

		return new WebSocketServerInitializer(this.config.getIdleConfig(), messageService);
	}

	/**
	 * 销毁
	 */
	public void destroy() {

		if (channel != null) {
			channel.close();
		}
		group.shutdownGracefully();
	}

	public int getPort() {

		return port;
	}

	public void setConfig(ServerConfig config) {

		this.config = config;
	}

	public void setMessageService(MessageHandle messageService) {

		this.messageService = messageService;
	}

}
