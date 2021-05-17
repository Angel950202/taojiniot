package com.taojin.iot.service.equipment.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.equipment.dao.EquipmentIcoDao;
import com.taojin.iot.service.equipment.entity.EquipmentIco;
import com.taojin.iot.service.equipment.service.EquipmentIcoService;

/**
 * Service实现-设备图标
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午8:47:56
 * author 王杰
 * ============================================================================
 */
@Service("equipmentIcoServiceImpl")
public class EquipmentIcoServiceImpl extends BaseServiceImpl<EquipmentIco,Long> implements EquipmentIcoService{
	@Resource(name = "equipmentIcoDaoImpl")
	private EquipmentIcoDao equipmentIcoDao;

	@Resource(name = "equipmentIcoDaoImpl")
	public void setBaseDao(EquipmentIcoDao equipmentIcoDao) {
		super.setBaseDao(equipmentIcoDao);
	}

	@Override
	public List<EquipmentIco> findListByParam(List<Filter> filters,
			String userId) {
		return equipmentIcoDao.findListByParam(filters,userId);
	}
}
