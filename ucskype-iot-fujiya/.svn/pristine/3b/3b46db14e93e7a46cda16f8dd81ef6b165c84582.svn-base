package com.taojin.iot.transmit.lib.websocket;


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

public class WebSocketManager extends Server{

	private static final Logger logger = LogManager.getLogger(WebSocketManager.class);
	
	private WebSocketServer webSocketServer;
	
	private ServerStatus serverStatus = null;
	
	/**
	 * 初始化
	 */
	@Override
	protected Server init(ServerConfig config, MessageHandle messageService) {
		serverStatus = ServerStatus.INIT;
		if(webSocketServer != null){
			webSocketServer.setConfig(config);
			webSocketServer.setMessageService(messageService);
			return this;
		}
		webSocketServer = new WebSocketServer(config, messageService);
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
			} else {
				throw new ServerStatusException(serverStatus);
			}
		}
		try {
			webSocketServer.start();
			serverStatus = ServerStatus.STARTED;
		} catch (Exception e) {
			logger.info("WebSocket" + webSocketServer.getPort() + "端口服务启动失败");
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
		webSocketServer.destroy();
		serverStatus = ServerStatus.INIT;
	}

	/**
	 * 是否启动
	 * @return
	 */
	public boolean isStarted() {
		if (serverStatus == ServerStatus.STARTED) {
			return true;
		}
		return false;
	}

	@Override
	public List<String> getSessionIds(){
		List<String> sessionIds = new ArrayList<String>();
		Map<String,Channel> channels = WebSocketServer.channels;
		for (String key : channels.keySet()) {
			sessionIds.add(key);
		}
		return sessionIds;
	}
}
