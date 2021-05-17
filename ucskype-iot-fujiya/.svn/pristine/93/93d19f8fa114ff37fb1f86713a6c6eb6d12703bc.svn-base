package com.taojin.iot.service.management.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.management.dao.EquipmentUpdateDao;
import com.taojin.iot.service.management.entity.EquipmentUpdate;
import com.taojin.iot.service.management.service.EquipmentUpdateService;


@Service("equipmentUpdateServiceImpl")
public class EquipmentUpdateServiceImpl extends BaseServiceImpl<EquipmentUpdate, Long>
implements EquipmentUpdateService  {
	@Resource(name = "equipmentUpdateDaoImpl")
	private EquipmentUpdateDao equipmentUpdateDao;

	@Resource(name = "equipmentUpdateDaoDaoImpl")
	public void setBaseDao(EquipmentUpdateDao equipmentUpdateDao) {
		super.setBaseDao(equipmentUpdateDao);
	}
}
