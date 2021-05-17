package com.taojin.iot.transmit;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taojin.iot.transmit.handler.TCPHandler;
import com.taojin.iot.transmit.handler.TCPNewHandler;
import com.taojin.iot.transmit.lib.CommunicatType;
import com.taojin.iot.transmit.lib.Server;
import com.taojin.iot.transmit.lib.ServerConfig;
import com.taojin.iot.transmit.lib.ServerFactory;

/**
 * 类型:Servers通信服务
 * ============================================================================
 * 版权所有 2013-2017无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 2017年12月23日
 * 
 * @author sjn
 *         ==================================================================
 *         ==========
 */
public class Servers {

	final static Logger logger = LoggerFactory.getLogger(Servers.class);
	private static final ServerFactory factory = new ServerFactory();

	public static Map<Integer, Server> tcpServers = new HashMap<Integer, Server>();

	public static Map<Integer, Server> webSocketServers = new HashMap<Integer, Server>();

	public static Map<Integer, Server> udpServers = new HashMap<Integer, Server>();

	/** 管理中心tcp/udp处理 */
	public static TCPHandler tCPHandler = new TCPHandler();
	
	public static TCPNewHandler tcpNewHandler = new TCPNewHandler();
	
	/** 用户中心-tcp/udp处理 */
//	public static TCPUserHandler tcpUserHandler = new TCPUserHandler();

	/** 用户中心-udp/tcp处理 */
	// public static UserNbHandler userNbHandler = new UserNbHandler();

	// ---------------------socket-----------------------
	// 开启通信
	public static void startSocket(int port, long readerIdleTime,
			String heartMsg) {

		ServerConfig config = new ServerConfig();
		config.setPort(port);
		config.setReaderIdleTime(readerIdleTime);
		if (StringUtils.isBlank(heartMsg)) {
			config.setHeartMsg(heartMsg);
		}

		if(port == 8033){
			logger.info("TCP服务启动中...");
			Server server = factory.getInstance(CommunicatType.SOCKET, config,
					tCPHandler);
			tcpServers.put(config.getPort(), server);
			server.start();
		}else if(port == 8035){
			logger.info("TCPNew服务启动中...");
			Server server = factory.getInstance(CommunicatType.USERTCP, config,
					tcpNewHandler);
			tcpServers.put(config.getPort(), server);
			server.start();
		}
		

		/*
		 * logger.info("UDP-USER-NB服务启动中..."); Server serverUpd =
		 * factory.getInstance(CommunicatType.USERNBUDP, config, userNbHandler);
		 * tcpServers.put(config.getPort(), serverUpd); serverUpd.start();
		 * logger.info("UDP-USER-NB服务启动，监听端口："+port+"...");
		 */

	}

	// 关闭通信
	public static void closeSocket(int port) {
		Server server = tcpServers.get(port);
		if (server == null) {
			return;
		}
		server.close();
	}
}
