package com.taojin.iot.service.task.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.equipment.entity.Equipment;
import com.taojin.iot.service.equipment.entity.EquipmentSensor;
import com.taojin.iot.service.equipment.service.EquipmentSensorService;
import com.taojin.iot.service.equipment.service.EquipmentService;
import com.taojin.iot.service.task.dao.impl.WorkReportDetailDaoImpl;
import com.taojin.iot.service.task.entity.WorkReportDetail;
import com.taojin.iot.service.task.service.WorkOrderService;
import com.taojin.iot.service.task.service.WorkReportDetailService;

@Service("workReportDetailServiceImpl")
public class WorkReportDetailServiceImpl extends
		BaseServiceImpl<WorkReportDetail, Long> implements
		WorkReportDetailService {
	@Resource(name = "workReportDetailDaoImpl")
	private WorkReportDetailDaoImpl workReportDetailDaoImpl;
	@Resource(name = "workOrderServiceImpl")
	private WorkOrderService workOrderService;
	@Resource(name = "equipmentServiceImpl")
	private EquipmentService equipmentService;
	@Resource(name = "equipmentSensorServiceImpl")
	private EquipmentSensorService equipmentSensorService;

	@Resource(name = "workReportDetailDaoImpl")
	public void setBaseDao(WorkReportDetailDaoImpl workReportDetailDaoImpl) {
		super.setBaseDao(workReportDetailDaoImpl);
	}

	@Override
	public HashMap<String, List<String>> findMaterial() {
		// TODO Auto-generated method stub
		return workReportDetailDaoImpl.findMaterial();
	}

	@Override
	public void saveTrace(String address, String nok) {
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("serialNumber", address));
		List<EquipmentSensor> list = equipmentSensorService.findList(null,
				filters, null);
		Equipment equipment = list.get(0).getEquipment();
		WorkReportDetail workReportDetail = new WorkReportDetail();
		workReportDetail.setLineCode(equipment.getLineNumber());
		workReportDetail.setLineName(equipment.getLineName());
		workReportDetail.setWorks(equipment.getName());

		List<Filter> filters2 = new ArrayList<>();
		filters2.add(Filter.eq("isDel", false));
		filters2.add(Filter.eq("lineNumber", equipment.getLineNumber()));
		filters2.add(Filter.eq("status", "进行中"));
		workOrderService.findList(null, filters2, null);
		workReportDetail.setOrderNumber(workOrderService
				.findList(null, filters2, null).get(0).getOrderNumber());
		if (nok != null) {
			workReportDetail.setMsg(nok);
		}
		workReportDetailDaoImpl.persist(workReportDetail);
	}
}
