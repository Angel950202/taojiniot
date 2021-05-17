package com.taojin.iot.transmit.handler.command;

import com.taojin.iot.transmit.lib.CommunicatType;

/**
 * 指令-iot平台-通用
 * @author wangjie
 *
 */
public interface TcpUserCommandSendService {
	
	/**
	 * 指令发送
	 * @param sessionId 会话ID
	 * @param type　类型
	 * @param command　命令
	 * @return
	 */
	public boolean sendCommand(String sessionId,CommunicatType type,String command);
	
	/**
	 * 发送通信握手
	 * @param sessionId 会话ID
	 * @param type 通信类型
	 * @param receivecommand 接收命令
	 * @return
	 */
	public boolean sendShakHands(String sessionId,CommunicatType type,String receivecommand);

	/**
	 * 发送心跳回复
	 * @param sessionId 会话ID
	 * @param type 通信类型
	 * @param receivecommand 接收命令
	 * @return
	 */
	public boolean sendHeart(String sessionId, CommunicatType type,
			String receivecommand);

	/**
	 * 下发控制指令
	 * @param ccid 设备ID
	 * @param address　表地址
	 * @param type　连接类型
	 * @param state　0 关，1开
	 * @return
	 */
	boolean sendValve(String ccid, String address, CommunicatType type,
			Integer state);

	/**
	 * 收取数据处理
	 * @param command 命令
	 * @param type 类型
	 * @return
	 */
	boolean receiveData(String sessionId,String command,CommunicatType type);
	
}
