package com.taojin.iot.transmit.bean;

/**
 * 管理监控
 * @author wangjie
 *
 */
public class Manager {
	
	/**
	 * 获取流量
	 * @param liuliang 流量MB
	 * @return　bit
	 */
	public static String getLiuliang(int liuliang){
		Integer v1 = liuliang;
		return v1.toString();
	}

}
