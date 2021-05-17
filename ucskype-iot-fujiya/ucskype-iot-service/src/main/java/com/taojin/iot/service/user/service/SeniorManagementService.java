package com.taojin.iot.service.user.service;

import com.taojin.iot.base.comm.service.BaseService;
import com.taojin.iot.service.user.entity.SeniorManagement;

public interface SeniorManagementService extends BaseService<SeniorManagement,Long>{


	void updatecompany(String newname, String name);
	
	/**
	 * 设备状态更新
	 * @param number 设备号
	 * @param netflow 流量 
	 * @param status 正常/故障/中断
	 * @return
	 */
	void updateEquipment(String number,int netflow,String status);

	/**
	 * 根据设备号获取充值流量，充值完清0
	 * @param number 设备号
	 * @author wangjie
	 * @return
	 */
	int getPayFlow(String number);

	/**
	 * 充值失败归还
	 * @param number 设备号
	 * @param backFlow 归还流量
	 * @author wangjie
	 */
	void payBackFlow(String number, int backFlow);

	/**
	 * 根据设备号更新设备状态
	 * @param number 设备号
	 * @param status 状态 正常/故障/中断
	 */
	void updateEquipmentStatus(String number,String status);

	/**
	 * 流量充值
	 * @param number 设备号
	 * @param payFlow 充值流量（M）
	 */
	void payFlow(String number, int payFlow);
}
