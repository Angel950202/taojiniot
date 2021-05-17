package com.taojin.iot.service.report.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.equipment.entity.EquipmentSensor;
import com.taojin.iot.service.equipment.service.EquipmentSensorService;
import com.taojin.iot.service.report.dao.ReportRealTimeSensorDao;
import com.taojin.iot.service.report.entity.ReportRealTimeSensor;
import com.taojin.iot.service.report.entity.Series;
import com.taojin.iot.service.report.service.ReportRealTimeSensorService;

/**
 * Service-传感器实时数据
 * @author wangjie
 *
 */
@Service("reportRealTimeSensorServiceImpl")
public class ReportRealTimeSensorServiceImpl extends
		BaseServiceImpl<ReportRealTimeSensor, Long> implements
		ReportRealTimeSensorService {
	final static Logger logger = LoggerFactory.getLogger(ReportRealTimeSensorServiceImpl.class);
	
	@Resource(name = "reportRealTimeSensorDaoImpl")
	private ReportRealTimeSensorDao reportRealTimeSensorDao;
	@Resource(name="equipmentSensorServiceImpl")
	private EquipmentSensorService equipmentSensorService;

	@Resource(name = "reportRealTimeSensorDaoImpl")
	public void setBaseDao(ReportRealTimeSensorDao reportRealTimeSensorDao) {
		super.setBaseDao(reportRealTimeSensorDao);
	}

	@Override
	public List<Series> ReportList(int Order,Long id) {
		return reportRealTimeSensorDao.ReportList(Order,id);
	}
	
	@Override
	public void addData(String dataValue,Integer sensorNumber){
		logger.info("[添加传感器实时数据]---->开始：dataVakye={},sensorNumber={}",dataValue,sensorNumber);
		EquipmentSensor equipmentSensor = equipmentSensorService.getByParam("serialNumber",sensorNumber);
		if(equipmentSensor == null){
			logger.warn("[添加传感器实时数据]---->失败：传感器编号={}不存在",sensorNumber);
			return;
		}
		
		try{
			//存储
			ReportRealTimeSensor realTimeSensor = new ReportRealTimeSensor(equipmentSensor.getId(), 
					equipmentSensor.getIdNumber(), 
					equipmentSensor.getEquipment().getId(), 
					equipmentSensor.getName(), 
					Integer.parseInt(dataValue), 
					equipmentSensor.getUnit(), 
					null);
			super.save(realTimeSensor);
			logger.info("[添加传感器实时数据]---->成功:id={}",realTimeSensor.getId());
		}catch(Exception e){
			logger.error("[添加传感器实时数据]---->异常:exception={}",e.getMessage());
		}
	}

}