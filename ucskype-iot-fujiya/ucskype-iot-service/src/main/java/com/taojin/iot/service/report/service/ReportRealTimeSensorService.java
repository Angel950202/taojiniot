package com.taojin.iot.service.report.service;


import java.util.List;

import com.taojin.iot.base.comm.service.BaseService;
import com.taojin.iot.service.report.entity.ReportRealTimeSensor;
import com.taojin.iot.service.report.entity.Series;

/**
 * service-传感器实时数据
 * @author wangjie
 *
 */
public interface ReportRealTimeSensorService extends
		BaseService<ReportRealTimeSensor, Long> {

	List<Series> ReportList(int Order,Long id);

	/**
	 * 添加传感器数据
	 * @param dataValue 数据值
	 * @param sensorNumber 传感器编号
	 * @author wangjie
	 * @since 2019-02-27
	 */
	public void addData(String dataValue, Integer sensorNumber);
}
