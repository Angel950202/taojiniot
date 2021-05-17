package com.taojin.iot.transmit.module.service;

import com.taojin.iot.base.comm.service.BaseService;
import com.taojin.iot.transmit.module.entity.TransmitSession;

/**
 * 会话
 * @author wangjie
 *
 */
public interface TransmitSessionService extends BaseService<TransmitSession,Long>{

	/**
	 * 绑定
	 * @param sessionId 会话ID
	 * @param ccid 设备编号
	 * @param heartbeat 心跳时间 （秒）
	 */
	void bind(String sessionId, String ccid,Integer heartbeat);

	/**
	 * 解除绑定
	 * @param sessionId 会话ID
	 */
	void removeBind(String sessionId);

}
