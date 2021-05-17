package com.taojin.iot.service.kanban.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.equipment.dao.EquipmentIcoDao;
import com.taojin.iot.service.kanban.dao.TrsDao;
import com.taojin.iot.service.kanban.entiy.Trs;
import com.taojin.iot.service.kanban.service.TrsService;

@Service("trsServiceImpl")
public class TrsServiceImpl extends BaseServiceImpl<Trs, Long> implements TrsService {
	@Resource(name = "trsDaoImpl")
	private TrsDao trsDao;

	@Resource(name = "trsDaoImpl")
	public void setBaseDao(TrsDao trsDao) {
		super.setBaseDao(trsDao);
	}
}
