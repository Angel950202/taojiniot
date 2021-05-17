package com.taojin.iot.service.equipment.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.equipment.dao.DeviceReportDao;
import com.taojin.iot.service.equipment.entity.DeviceReport;
import com.taojin.iot.service.equipment.service.DeviceReportService;


@Service("deviceReportServiceImpl")
public class DeviceReportServiceImpl extends BaseServiceImpl<DeviceReport,Long> implements DeviceReportService {

	@Resource(name = "deviceReportDaoImpl")
	private DeviceReportDao deviceReportDao;

	@Resource(name = "deviceReportDaoImpl")
	public void setBaseDao(DeviceReportDao deviceReportDao) {
		super.setBaseDao(deviceReportDao);
	}
	
}
