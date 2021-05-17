package com.taojin.iot.transmit.handler.command;

import com.taojin.iot.transmit.lib.CommunicatType;

/**
 * 用户中心-NB解析
 * @author wangjie
 *
 */
public interface UserNbCommandSendService {

	/**
	 * 发送指令
	 * @param sessionId 会话ID
	 * @param type　类型
	 * @param command　指令
	 * @return
	 */
	public boolean sendCommand(String sessionId, CommunicatType type, String command);

	/**
	 * 阀门控制
	 * @param imei 设备号
	 * @param type 类型
	 * @param state 阀门状态1开，2：关
	 * @return
	 */
	public boolean valveControl(String imei, CommunicatType type, Integer state);

	/**
	 * 设置上报周期
	 * @param imei 设备号
	 * @param type 类型
	 * @param cycle 上报周期，单位：分钟
	 * @return
	 */
	public boolean setupReportCycle(String imei, CommunicatType type, Integer cycle);

	/**
	 * 设置表计参数
	 * @param imei 设备号
	 * @param type 类型
	 * @param url 地址：udp://192.168.1.103:15804
	 * @return
	 */
	public boolean setParams(String imei, CommunicatType type, String url);

}
