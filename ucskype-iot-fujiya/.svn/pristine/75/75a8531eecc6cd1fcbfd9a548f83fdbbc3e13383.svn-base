package com.taojin.iot.service.equipment.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.equipment.dao.EquipmentIpaddressDao;
import com.taojin.iot.service.equipment.entity.EquipmentIpaddress;
import com.taojin.iot.service.equipment.service.EquipmentIpaddressService;

/**
 * Service-设备IP
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午4:45:33
 * author 王杰
 * ============================================================================
 */
@Service("equipmentIpaddressServiceImpl")
public class EquipmentIpaddressServiceImpl extends BaseServiceImpl<EquipmentIpaddress,Long> implements EquipmentIpaddressService{
	@Resource(name = "equipmentIpaddressDaoImpl")
	private EquipmentIpaddressDao equipmentIpaddressDao;

	@Resource(name = "equipmentIpaddressDaoImpl")
	public void setBaseDao(EquipmentIpaddressDao equipmentIpaddressDao) {
		super.setBaseDao(equipmentIpaddressDao);
	}
}
