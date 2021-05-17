package com.taojin.iot.service.equipment.service;

import net.sf.json.JSONArray;

import com.taojin.iot.base.comm.service.BaseService;
import com.taojin.iot.service.equipment.entity.EquipmentTrigger;

/**
 * Service-设备触发器 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午8:44:04 author 王杰
 * ============================================================================
 */
public interface EquipmentTriggerService extends
		BaseService<EquipmentTrigger, Long> {

	/**
	 * 数据触发处理
	 * 
	 * @param trigger 触发对象
	 * @param jsonArray
	 *            [{"value":"值","state":"状态","switchState":"开关状态","time":"时间"}]
	 */
	public void equipmentAlarmTypeTrigger(EquipmentTrigger trigger,
			JSONArray jsonArray);

}
