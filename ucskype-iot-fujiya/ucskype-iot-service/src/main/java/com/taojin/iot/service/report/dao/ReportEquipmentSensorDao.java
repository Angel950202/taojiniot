package com.taojin.iot.service.report.dao;

import java.util.List;

import com.taojin.iot.base.comm.dao.BaseDao;
import com.taojin.iot.service.report.entity.ReportEquipmentSensor;

/**
 * Dao-设备传感器记录 按天
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午3:03:56
 * author 王杰
 * ============================================================================
 */
public interface ReportEquipmentSensorDao extends BaseDao<ReportEquipmentSensor,Long>{

	/**
	 * 根据条件sql统计
	 * @param sql
	 * @return
	 */
	Long count(String sql);

	/**
	 * 根据条件sql查询
	 * @param sql
	 * @return
	 */
	List<ReportEquipmentSensor> findByfindByProperty(String sql);

}
