package com.taojin.iot.service.equipment.service;

import net.sf.json.JSONObject;

import com.taojin.iot.base.comm.service.BaseService;
import com.taojin.iot.service.equipment.entity.EquipmentSensorState;

/**
 * Service-设备传感器状态
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午3:35:02
 * author 王杰
 * ============================================================================
 */
public interface EquipmentSensorStateService extends BaseService<EquipmentSensorState,Long>{

	/**
	 * 收到上报数据处理
	 * @param report
	 */
	public void reportFactory(JSONObject report);
	
	
	
}
