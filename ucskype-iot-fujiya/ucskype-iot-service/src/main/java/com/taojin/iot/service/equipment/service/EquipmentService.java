package com.taojin.iot.service.equipment.service;

import com.taojin.iot.base.comm.service.BaseService;
import com.taojin.iot.service.equipment.entity.Equipment;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Service-设备
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午8:42:09
 * author 王杰
 * ============================================================================
 */
public interface EquipmentService extends BaseService<Equipment,Long>{
	
	JSONObject findEquipmentById(JSONObject param);
	
	void addEquipment(JSONObject param);
	
	JSONObject findEquipmentList(JSONObject param);
	
	/**
	 * 根据地址位，产线查询设备
	 * @param address
	 * @param lineNumber
	 * @return
	 */
	Equipment querySensorLineByAddrssANDLine(String address,String lineNumber);

}
