package com.taojin.iot.service.equipment.dao;

import com.taojin.iot.base.comm.dao.BaseDao;
import com.taojin.iot.service.equipment.entity.Equipment;

/**
 * Dao-设备
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午8:32:49
 * author 王杰
 * ============================================================================
 */
public interface EquipmentDao extends BaseDao<Equipment,Long>{
	
	/**
	 * 根据地址位，产线查询设备
	 * @param address
	 * @param lineNumber
	 * @return
	 */
	Equipment querySensorLineByAddrssANDLine(String address,String lineNumber);

}
