package com.taojin.iot.transmit.lib.msghandle;

import io.netty.channel.ChannelHandlerContext;

import com.taojin.iot.transmit.lib.CommunicatType;
import com.taojin.iot.transmit.lib.MessageHandle;
import com.taojin.iot.transmit.lib.utils.ConvertUtil;
import com.taojin.iot.transmit.lib.utils.TypeConversion;


public abstract class HexMessageHandle extends MessageHandle{

	/**
	 * 接收信息处理
	 */
	@Override
	public void receive(String sessionId, CommunicatType communicatType, String msg,ChannelHandlerContext ctx) {
		msg = ConvertUtil.hexToStr(msg);
		this.receive0(sessionId, communicatType, msg);
		this.receiveBytes(sessionId, communicatType, TypeConversion.hexString2Bytes(msg),ctx);
	}
	
	/**
	 * 子类重写 接收 数据 
	 * @param sessionId sessionId
	 * @param communicatType 通信类型
	 * @param msg 16进制
	 */
	public abstract void receive0(String sessionId, CommunicatType communicatType, String msg);
	
	/**
	 * 发送信息业务处理
	 * @param sessionId 会话id
	 * @param communicatType 通信类型
	 * @param msg 接收信息
	 * @return
	 */
	@Override
	public boolean send(String sessionId, CommunicatType communicatType, String msg){
		msg = ConvertUtil.strToHex(msg);
		return super.send(sessionId, communicatType, msg);
	}
	
}
