package com.taojin.iot.service.equipment.dao;

import java.util.List;

import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.dao.BaseDao;
import com.taojin.iot.service.equipment.entity.EquipmentIco;

/**
 * Dao-设备图标
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午8:33:31
 * author 王杰
 * ============================================================================
 */
public interface EquipmentIcoDao extends BaseDao<EquipmentIco,Long>{

	/**
	 * 列表查询
	 * @param filters 过滤条件
	 * @param userId 用户ID 
	 * @return
	 */
	List<EquipmentIco> findListByParam(List<Filter> filters, String userId);

}
