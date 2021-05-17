package com.taojin.iot.transmit.lib.socket;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taojin.iot.transmit.lib.MessageHandle;
import com.taojin.iot.transmit.lib.Server;
import com.taojin.iot.transmit.lib.ServerConfig;
import com.taojin.iot.transmit.lib.ServerStatus;
import com.taojin.iot.transmit.lib.exception.ServerStatusException;

public class SocketManager extends Server{

	private SocketServer socketServer;

	private static final Logger logger = LogManager.getLogger(SocketManager.class);

	private ServerStatus serverStatus = null;
	
	/**
	 * 初始化
	 */
	@Override
	protected Server init(ServerConfig config, MessageHandle messageService) {
		serverStatus = ServerStatus.INIT;
		if(socketServer != null){
			socketServer.setConfig(config);
			socketServer.setMessageService(messageService);
			return this;
		}
		socketServer = new SocketServer(config, messageService);
		return this;
	}

	/**
	 * 启动
	 */
	@Override
	public void start() {
		if(serverStatus != ServerStatus.INIT) {
			if(serverStatus == ServerStatus.STARTED){
				this.close();
			}else{
				throw new ServerStatusException(serverStatus);
			}
		}
		try {
			socketServer.start();
			serverStatus = ServerStatus.STARTED;
		} catch (Exception e) {
			logger.info("Socket" + socketServer.getPort() + "端口服务启动失败");
			e.printStackTrace();
		}
	}

	/**
	 * 关闭
	 */
	@Override
	public void close() {
		if(serverStatus != ServerStatus.STARTED) {
			return;
		}
		socketServer.destroy();
		serverStatus = ServerStatus.INIT;
	}

	public boolean isStarted() {
		if (serverStatus == ServerStatus.STARTED) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<String> getSessionIds(){
		List<String> sessionIds = new ArrayList<String>();
		Map<String,Channel> channels = SocketServer.channels;
		for (String key : channels.keySet()) {
			sessionIds.add(key);
		}
		return sessionIds;
	}
}
