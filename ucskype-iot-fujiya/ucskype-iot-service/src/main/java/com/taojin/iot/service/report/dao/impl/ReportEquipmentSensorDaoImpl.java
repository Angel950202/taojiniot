package com.taojin.iot.service.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.taojin.iot.base.comm.utils.CommonUtil;
import com.taojin.iot.base.comm.utils.DatesUtils;

/**
 * Dao-设备传感器历史记录
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午3:04:39
 * author 王杰
 * ============================================================================
 */
@Repository("reportEquipmentSensorDaoImpl")
public class ReportEquipmentSensorDaoImpl extends BaseDaoImpl<ReportEquipmentSensor,Long> implements ReportEquipmentSensorDao{

	@Override
	public Long count(String sql) {
		if (StringUtils.isBlank(sql)) {
			return null;
		}
		try {
			Object list = entityManager.createNativeQuery(sql)
					.getSingleResult();
			Long count = Long.parseLong(list.toString());
			return count;
		} catch (Exception e) {
			return 0L;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ReportEquipmentSensor> findByfindByProperty(String sql) {
		if(StringUtils.isBlank(sql)){
			return null;
		}
		try {
			List<ReportEquipmentSensor> reportEquipmentSensors = entityManager.createNativeQuery(sql).getResultList();
			List<ReportEquipmentSensor> list = new ArrayList<ReportEquipmentSensor>();
			for(int i=0;i<reportEquipmentSensors.size();i++){
				ReportEquipmentSensor reportEquipmentSensor = new ReportEquipmentSensor();
				Object obj = reportEquipmentSensors.get(i);
				List it = CommonUtil.makeArrayObject(obj);
				
				if(it.get(0) != null){
					reportEquipmentSensor.setId(Long.parseLong(it.get(0).toString()));
				}
				if(it.get(1) != null){
					reportEquipmentSensor.setCreateDate(DatesUtils.stringToDate(it.get(1).toString(), "yyyy-MM-dd HH:mm:ss"));
				}
				if(it.get(2) != null){
					reportEquipmentSensor.setCreator(Long.parseLong(it.get(2).toString()));
				}
				if(it.get(3) != null){
					reportEquipmentSensor.setEquipmentTypeId(Long.parseLong(it.get(3).toString()));
				}
				if(it.get(4) != null){
					reportEquipmentSensor.setIsDel(Boolean.valueOf(it.get(4).toString()));
				}
				if(it.get(5) != null){
					reportEquipmentSensor.setModifyDate(DatesUtils.stringToDate(it.get(5).toString(), "yyyy-MM-dd HH:mm:ss"));
				}
				if(it.get(6) != null){
					reportEquipmentSensor.setOwnerId(Long.parseLong(it.get(6).toString()));
				}
				if(it.get(7) != null){
					reportEquipmentSensor.setDateTime(it.get(7).toString());
				}
				if(it.get(8) != null){
					reportEquipmentSensor.setSensorId(Long.parseLong(it.get(8).toString()));
				}
				if(it.get(9) != null){
					reportEquipmentSensor.setSensorNumber(it.get(9).toString());
				}
				if(it.get(10) != null){
					reportEquipmentSensor.setSensorTrueValue(it.get(10).toString());
				}
				if(it.get(11) != null){
					reportEquipmentSensor.setSensorValues(it.get(11).toString());
				}
				
				list.add(reportEquipmentSensor);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
