package com.taojin.iot.service.equipment.service;

import com.taojin.iot.base.comm.service.BaseService;
import com.taojin.iot.service.equipment.entity.EquipmentType;

import net.sf.json.JSONObject;

/**
 * Service-设备类型
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午8:45:08
 * author 王杰
 * ============================================================================
 */
public interface EquipmentTypeService extends BaseService<EquipmentType,Long>{

	/**
	 * 查询设备列表
	 * @param param
	 * @return
	 */
	JSONObject findEquipmentList(JSONObject param);
}
