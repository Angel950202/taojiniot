package com.taojin.iot.transmit.handler.command;

/**
 * 命令解析-佛吉亚设备
 * @author wangjie
 *
 */
public interface CommandFujiyaService {

	/**
	 * 解析命令得到整数值 S7-300
	 * @param sessionId 会话ID 
	 * @param field 对应信号位名称
	 * @param commandStr 消息
	 */
	void parseCommandToInt(String sessionId, String field, String commandStr);

	/**
	 * 解析命令得到整数值 S7-300 uLong类型,6个字节为一组，同时解析三个信号位数据 0 ，2，4
	 * @param sessionId 会话ID 
	 * @param field 对应信号位名称
	 * @param commandStr 消息
	 */
	void parseCommandToUlong(String sessionId, String field, String commandStr);

	void parseCommandToIntNew(String sessionId, String field, String commandStr);

	void parseCommandToUlongNew(String sessionId, String field,
			String commandStr);

}
