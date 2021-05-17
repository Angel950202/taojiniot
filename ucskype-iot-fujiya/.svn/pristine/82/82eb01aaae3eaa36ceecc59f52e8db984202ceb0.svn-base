package com.taojin.iot.service.report.service;

import java.util.List;

import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.Page;
import com.taojin.iot.base.comm.Pageable;
import com.taojin.iot.base.comm.service.BaseService;
import com.taojin.iot.service.report.entity.ReportEquipmentSensor;

/**
 * Service-设备传感器历史记录 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午3:05:55 author 王杰
 * ============================================================================
 */
public interface ReportEquipmentSensorService extends
		BaseService<ReportEquipmentSensor, Long> {

	/**
	 * 分页查询
	 * @param startTime 开始时间 yyyy-MM-dd
	 * @param endTime 结束时间 yyyy-MM-dd
	 * @param pageable
	 * @return
	 */
	public Page<ReportEquipmentSensor> findPageSensor(String startTime,
			String endTime, Pageable pageable);

	/**
	 * 列表查询
	 * 
	 * @param count 数量
	 * @param filters 过滤
	 * @return
	 */
	public List<ReportEquipmentSensor> findListSensor(Integer count,
			List<Filter> filters);
	
	/**
	 * 添加传感器记录
	 * 
	 * @param reportEquipmentSensor
	 * @param datetime
	 */
	public void saveReport(ReportEquipmentSensor reportEquipmentSensor,
			String datetime);

	/**
	 * 添加传感器记录
	 * @param reportEquipmentSensor 
	 * @param datetime 日期
	 * @param uniqueParam 序号
	 * @param equipmentNumber 设备号
	 */
	void addData(ReportEquipmentSensor reportEquipmentSensor, String datetime,
			Integer uniqueParam, String equipmentNumber);
	
}
