package com.taojin.iot.transmit.handler.command;

import com.taojin.iot.transmit.lib.CommunicatType;

/**
 * 管理中心-命令处理
 * @author wangjie
 *
 */
public interface ManagerCommandService {

	/**
	 * 连接初始化，获取设备状态，当前流量
	 * @param sessionId 会话ID 
	 * @param type 连接类型
	 * @param receivecommand 指令
	 */
	void initEquipConnect(String sessionId, CommunicatType type,
			String receivecommand);

	/**
	 * 上报连接状态 
	 * @param sessionId 会话ID
	 * @param status 正常/故障/中断
	 */
	void reportConnectState(String sessionId,String status);
	

	void reportConnect(String code,String flag,String sessionId, String msgString);

}
