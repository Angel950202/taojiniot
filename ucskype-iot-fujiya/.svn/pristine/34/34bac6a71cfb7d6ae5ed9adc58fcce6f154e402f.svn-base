package com.taojin.iot.agreement.fujiya.service;

import com.taojin.iot.agreement.fujiya.entity.AgreementRc701Value;
import com.taojin.iot.base.comm.service.BaseService;

/**
 * 指令-rc701-值处理
 * @author wangjie
 *
 */
public interface AgreementRc701ValueService extends BaseService<AgreementRc701Value,Long>{

	/**
	 * 添加信号对应值 
	 * @param sessionId 会话ID
	 * @param field 信号位地址
	 * @param value 数据值
	 * @param typename 产线类型名
	 */
	void addValue(String sessionId, String field, String value,String typename);

	/**
	 * 产线统计求和
	 * @param nokNum_arr 地址码
	 * @param line 产线类型
	 * @param dateTime 日期yyyy-MM-dd
	 * @return
	 */
	Double getSum(String[] nokNum_arr, Integer line, String dateTime);

	/**
	 * 添加DTU信号位
	 * @param iccid 设备号
	 * @param field 地址码
	 * @param value 值
	 * @param typename 产线类型名称
	 * @return
	 */
	boolean addDtuData(String iccid, String field, String value, String typename);

	/**
	 * 添加DTU增量信号位
	 * @param iccid 设备号
	 * @param field 地址码
	 * @param value 增量值
	 * @param typename 产线类型名称
	 * @return
	 */
	boolean addDtuIncrementData(String iccid, String field, Integer value,
			String typename);

	/**
	 * 添加运行时间 信号位计算
	 * @param iccid 设备号
	 * @param field 地址码
	 * @param typename 产线类型名称
	 * @return
	 */
	boolean runTime(String iccid, String field, String typename);

	
	/**
	 * 添加停机,故障，空转时长 
	 * @param typename 产线类型名称
	 * @param address 地址码
	 * @param milliseconds 增量毫秒数
	 * @param iccid 设备号
	 * @return
	 */
	boolean addFailTime(String typename, String address, Integer milliseconds,
			String iccid);

}
