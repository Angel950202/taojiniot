package com.taojin.iot.service.equipment.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.equipment.dao.EquipmentSensorParamsDao;
import com.taojin.iot.service.equipment.entity.EquipmentSensorParams;
import com.taojin.iot.service.equipment.service.EquipmentSensorParamsService;

/**
 * Service-设备传感器自定义参数
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午4:32:51
 * author 王杰
 * ============================================================================
 */
@Service("equipmentSensorParamsServiceImpl")
public class EquipmentSensorParamsServiceImpl extends BaseServiceImpl<EquipmentSensorParams,Long> implements EquipmentSensorParamsService{
	@Resource(name = "equipmentSensorParamsDaoImpl")
	private EquipmentSensorParamsDao equipmentSensorParamsDao;

	@Resource(name = "equipmentSensorParamsDaoImpl")
	public void setBaseDao(EquipmentSensorParamsDao equipmentSensorParamsDao) {
		super.setBaseDao(equipmentSensorParamsDao);
	}
}
