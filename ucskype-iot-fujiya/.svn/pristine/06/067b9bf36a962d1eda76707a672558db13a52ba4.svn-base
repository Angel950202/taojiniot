package com.taojin.iot.transmit.lib.socket;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taojin.iot.transmit.lib.MessageHandle;
import com.taojin.iot.transmit.lib.ServerConfig;
import com.taojin.iot.transmit.lib.socket.handler.ServerUdpHandler;
import com.taojin.iot.transmit.lib.socket.handler.ServerUserNbUdpHandler;
import com.taojin.iot.transmit.lib.socket.handler.ServerUserUdpHandler;

public class SocketServer {

	private static final Logger logger = LogManager.getLogger(SocketServer.class);
	
	public static Map<String,Channel> channels = new HashMap<String, Channel>();
	
	private final EventLoopGroup group = new NioEventLoopGroup();
	private Channel channel;
	private int port;
	private ServerConfig config;
	private MessageHandle messageService;

	public SocketServer(ServerConfig config, MessageHandle messageService) {
		this.config = config;
		this.messageService = messageService;
		this.port = config.getPort();
	}

	/**
	 * 启动
	 * @throws Exception
	 */
	public void start() throws Exception {
		logger.info("Socket服务启动,监听" + port + "端口");
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(group)
			 .channel(NioServerSocketChannel.class)
			 .localAddress(new InetSocketAddress(port))
			 .childHandler(new SocketServerInitializer(config.getIdleConfig(), messageService));
			ChannelFuture future = bootstrap.bind().sync();
			channel = future.channel();
			channel.closeFuture().sync();
			logger.info("socket" + port + "端口服务启动失败");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("socket" + port + "端口服务启动失败");
		} finally {
			group.shutdownGracefully().sync();
		}
	}
	
	/**
	 * 启动用户中心TCP
	 * @throws Exception
	 */
	public void startUserTcp() throws Exception {
		logger.info("Socket 用户中心TCP服务启动,监听" + port + "端口");
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(group)
			 .channel(NioServerSocketChannel.class)
			 .localAddress(new InetSocketAddress(port))
			 .childHandler(new SocketUSERTCPServerInitializer(config.getIdleConfig(), messageService));
			ChannelFuture future = bootstrap.bind().sync();
			channel = future.channel();
			channel.closeFuture().sync();
			logger.info("socket" + port + "端口服务启动失败");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("socket" + port + "端口服务启动失败");
		} finally {
			group.shutdownGracefully().sync();
		}
	}
	
	/**
	 * udp启动
	 * @throws Exception
	 */
	public void startUdp() throws Exception {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ServerUdpHandler(messageService));
            logger.info("UDP服务启动，监听端口："+port+"...");
            ChannelFuture future = bootstrap.bind(port).sync();
			channel = future.channel();
			channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
		
	}
	
	/**
	 * udp启动
	 * @throws Exception
	 */
	public void startUserUdp() throws Exception {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ServerUserUdpHandler(messageService));
            logger.info("UDP服务启动，监听端口："+port+"...");
            ChannelFuture future = bootstrap.bind(port).sync();
			channel = future.channel();
			channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
		
	}
	
	/**
	 * udp启动
	 * @throws Exception
	 */
	public void startUserNbUdp() throws Exception {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ServerUserNbUdpHandler(messageService));
            logger.info("NB-UDP服务启动，监听端口："+port+"...");
            ChannelFuture future = bootstrap.bind(port).sync();
			channel = future.channel();
			channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
		
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
