package com.taojin.iot.transmit.lib;

import java.util.List;

public abstract class Server {

	/**
	 * 初始化
	 * @param config 初始化 配置
	 * @param messageService  消息 service
	 * @return
	 */
	protected abstract  Server init(ServerConfig config, MessageHandle messageService);
	/**
	 * 启动
	 */
	public abstract  void start();
	/**
	 * 关闭
	 */
	public abstract void close();
	
	/**
	 * 获取通信中的sessionIds
	 * @return
	 */
	public abstract List<String> getSessionIds();
	
}
