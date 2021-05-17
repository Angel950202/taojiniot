package com.taojin.iot.service.equipment.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.equipment.dao.EquipmentSensorDao;
import com.taojin.iot.service.equipment.entity.EquipmentSensor;
import com.taojin.iot.service.equipment.service.EquipmentSensorService;

/**
 * Service实现-设备传感器
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午8:49:24
 * author 王杰
 * ============================================================================
 */
@Service("equipmentSensorServiceImpl")
public class EquipmentSensorServiceImpl extends BaseServiceImpl<EquipmentSensor,Long> implements EquipmentSensorService{
	@Resource(name = "equipmentSensorDaoImpl")
	private EquipmentSensorDao equipmentSensorDao;

	@Resource(name = "equipmentSensorDaoImpl")
	public void setBaseDao(EquipmentSensorDao equipmentSensorDao) {
		super.setBaseDao(equipmentSensorDao);
	}
}
