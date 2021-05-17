package com.taojin.iot.transmit.lib;

public class ServerConfig {

	/**
	 * 端口
	 */
	private int port = 8888;
	/**
	 * 心跳数据
	 */
	private String heartMsg = "heart";
	/**
	 * 读空闲时间
	 */
	private long readerIdleTime = 60;
	/**
	 * 写空闲时间
	 */
	private long writerIdleTime = 0;
	/**
	 * 读或写空间时间
	 */
	private long allIdleTime = 0;
	
	/**
	 * 心跳配置
	 */
	private IdleConfig idleConfig;
	
	public int getPort() {
		return port;
	}
	public ServerConfig setPort(int port) {
		this.port = port;
		return this;
	}
	public String getHeartMsg() {
		return heartMsg;
	}
	public ServerConfig setHeartMsg(String heartMsg) {
		this.heartMsg = heartMsg;
		return this;
	}
	public long getReaderIdleTime() {
		return readerIdleTime;
	}
	public ServerConfig setReaderIdleTime(long readerIdleTime) {
		this.readerIdleTime = readerIdleTime;
		return this;
	}
	public long getWriterIdleTime() {
		return writerIdleTime;
	}
	public ServerConfig setWriterIdleTime(long writerIdleTime) {
		this.writerIdleTime = writerIdleTime;
		return this;
	}
	public long getAllIdleTime() {
		return allIdleTime;
	}
	public ServerConfig setAllIdleTime(long allIdleTime) {
		this.allIdleTime = allIdleTime;
		return this;
	}
	public IdleConfig getIdleConfig() {
		if (idleConfig == null) {
			idleConfig = new IdleConfig();
		}
		idleConfig.setAllIdleTime(this.allIdleTime);
		idleConfig.setReaderIdleTime(this.readerIdleTime);
		idleConfig.setWriterIdleTime(this.writerIdleTime);
		idleConfig.setHeartMsg(this.heartMsg);
		return idleConfig;
	}
}
