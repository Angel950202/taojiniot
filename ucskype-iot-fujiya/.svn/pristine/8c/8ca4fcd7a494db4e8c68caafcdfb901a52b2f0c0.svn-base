package com.taojin.iot.transmit.lib;


import java.util.HashMap;
import java.util.Map;

import com.taojin.iot.transmit.lib.socket.SocketManager;
import com.taojin.iot.transmit.lib.socket.SocketUdpManager;
import com.taojin.iot.transmit.lib.socket.SocketUserNbUdpManager;
import com.taojin.iot.transmit.lib.socket.SocketUserTcpManager;
import com.taojin.iot.transmit.lib.socket.SocketUserUdpManager;
import com.taojin.iot.transmit.lib.websocket.WebSocketManager;

public class ServerFactory {

	/**
	 * socketManager map
	 * key = port
	 * value = SocketManager
	 */
	private static Map<Integer, SocketManager> socketManagers = new HashMap<Integer, SocketManager>();
	/**
	 * WebSocketManager map
	 * key = port
	 * value = WebSocketManager
	 */
	private static Map<Integer, WebSocketManager> webSocketManagers = new HashMap<Integer, WebSocketManager>();

	/**
	 * udp连接管理
	 */
	private static Map<Integer,SocketUdpManager> socketUdpManagers = new HashMap<Integer, SocketUdpManager>();
	
	/**
	 * 用户中心-tcp管理
	 */
	private static Map<Integer, SocketUserTcpManager> socketUserTcpManagers = new HashMap<Integer, SocketUserTcpManager>();
	
	/**
	 * 用户中心-udp管理
	 */
	private static Map<Integer, SocketUserUdpManager> socketUserUdpManagers = new HashMap<Integer, SocketUserUdpManager>();
	
	/**
	 * 用户中心-nb-udp管理
	 */
	private static Map<Integer, SocketUserNbUdpManager> socketUserNbUdpManagers = new HashMap<Integer, SocketUserNbUdpManager>();
	
	/**
	 * 获取Server
	 * @param communicatType  服务通信类型
	 * @param config   初始化配置
	 * @param messageService   接收信息业务处理service
	 * @return
	 */
	
	public Server getInstance(CommunicatType communicatType, ServerConfig config, MessageHandle messageService){
		if(config == null || messageService == null){
			return null;
		}
		
		Server server = null;
		switch (communicatType) {
		case SOCKET://tcp连接
			server = this.createSocketServer(config, messageService);
			break;
		case WEBSOCKET:
			server = this.createWebSocketServer(config, messageService);
			break;
		case SOCKETUDP://upd连接
			server = this.createUDPSocketServer(config, messageService);
			break;
		case USERTCP://用户中心TCP连接
			server = this.createUserTcpServer(config, messageService);
			break;
		case USERUDP://用户中心UDP连接
			server = this.createUserUDPSocketServer(config, messageService);
			break;
		case USERNBUDP://用户中心Nb-UDP连接
			server = this.createUserNbUDPSocketServer(config, messageService);
			break;
		default:
			break;
		}
		return server;
	}

	/**
	 * 创建WebSocket服务
	 * @param config  初始化 配置
	 * @param messageService 接收信息业务处理service
	 * @return
	 */
	private Server createWebSocketServer(ServerConfig config, MessageHandle messageService) {
		Server server = null;
		//存在该服务
		if (webSocketManagers.containsKey(config.getPort())) {
			WebSocketManager webSocketManager = webSocketManagers.get(config.getPort());
			//服务启动中
			if(webSocketManager.isStarted()){
				webSocketManager.close();
			}
			server = webSocketManager;
			//服务初始化
			server.init(config, messageService);
			return server;
		}
		//服务不存在 创建
		server = new WebSocketManager();
		server.init(config, messageService);
		webSocketManagers.put(config.getPort(), (WebSocketManager)server);
		return server;
	}

	/**
	 * 创建Socket服务
	 * @param config  初始化 配置
	 * @param messageService 接收信息业务处理service
	 * @return
	 */
	private Server createSocketServer(ServerConfig config, MessageHandle messageService) {
		Server server = null;
		//存在该服务
		if (socketManagers.containsKey(config.getPort())) {
			SocketManager socketManager = socketManagers.get(config.getPort());
			//服务启动中
			if(socketManager.isStarted()){
				socketManager.close();
			}
			server = socketManager;
			//服务初始化
			server.init(config, messageService);
			return server;
		}
		//服务不存在 创建
		server = new SocketManager();
		server.init(config, messageService);
		socketManagers.put(config.getPort(), (SocketManager)server);
		return server;
	}
	
	/**
	 * 创建用户中心TCP服务
	 * @param config  初始化 配置
	 * @param messageService 接收信息业务处理service
	 * @return
	 */
	private Server createUserTcpServer(ServerConfig config, MessageHandle messageService) {
		Server server = null;
		//存在该服务
		if (socketUserTcpManagers.containsKey(config.getPort())) {
			SocketUserTcpManager socketManager = socketUserTcpManagers.get(config.getPort());
			//服务启动中
			if(socketManager.isStarted()){
				socketManager.close();
			}
			server = socketManager;
			//服务初始化
			server.init(config, messageService);
			return server;
		}
		//服务不存在 创建
		server = new SocketUserTcpManager();
		server.init(config, messageService);
		socketUserTcpManagers.put(config.getPort(), (SocketUserTcpManager)server);
		return server;
	}
	
	private Server createUDPSocketServer(ServerConfig config, MessageHandle messageService) {
		Server server = null;
		//存在该服务
		if (socketUdpManagers.containsKey(config.getPort())) {
			SocketUdpManager socketManager = socketUdpManagers.get(config.getPort());
			//服务启动中
			if(socketManager.isStarted()){
				socketManager.close();
			}
			server = socketManager;
			//服务初始化
			server.init(config, messageService);
			return server;
		}
		//服务不存在 创建
		server = new SocketUdpManager();
		server.init(config, messageService);
		
		socketUdpManagers.put(config.getPort(), (SocketUdpManager)server);
		return server;
	}
	
	private Server createUserUDPSocketServer(ServerConfig config, MessageHandle messageService) {
		Server server = null;
		//存在该服务
		if (socketUserUdpManagers.containsKey(config.getPort())) {
			SocketUserUdpManager socketManager = socketUserUdpManagers.get(config.getPort());
			//服务启动中
			if(socketManager.isStarted()){
				socketManager.close();
			}
			server = socketManager;
			//服务初始化
			server.init(config, messageService);
			return server;
		}
		//服务不存在 创建
		server = new SocketUserUdpManager();
		server.init(config, messageService);
		
		socketUserUdpManagers.put(config.getPort(), (SocketUserUdpManager)server);
		return server;
	}
	
	private Server createUserNbUDPSocketServer(ServerConfig config, MessageHandle messageService) {
		Server server = null;
		//存在该服务
		if (socketUserNbUdpManagers.containsKey(config.getPort())) {
			SocketUserNbUdpManager socketManager = socketUserNbUdpManagers.get(config.getPort());
			//服务启动中
			if(socketManager.isStarted()){
				socketManager.close();
			}
			server = socketManager;
			//服务初始化
			server.init(config, messageService);
			return server;
		}
		//服务不存在 创建
		server = new SocketUserNbUdpManager();
		server.init(config, messageService);
		
		socketUserNbUdpManagers.put(config.getPort(), (SocketUserNbUdpManager)server);
		return server;
	}
	
	
	/**
	 * 获取 对应 类型 和 端口 的服务
	 * @param communicatType 通信类型
	 * @param port  端口号
	 * @return 不存在 返回null
	 */
	public Server getServer(CommunicatType communicatType, int port){
		Server server = null;
		switch (communicatType) {
		case SOCKET:
			server = socketManagers.get(port);
			break;
		case WEBSOCKET:
			server = webSocketManagers.get(port);
			break;
		case SOCKETUDP:
			server = socketUdpManagers.get(port);
		default:
			break;
		}
		return server;
	}
}
