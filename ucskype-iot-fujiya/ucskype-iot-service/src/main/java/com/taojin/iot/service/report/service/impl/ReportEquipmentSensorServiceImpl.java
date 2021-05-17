package com.taojin.iot.service.report.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.Filter.Operator;
import com.taojin.iot.base.comm.Order.Direction;
import com.taojin.iot.base.comm.Page;
import com.taojin.iot.base.comm.Pageable;
import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.service.equipment.entity.Equipment;
import com.taojin.iot.service.equipment.entity.EquipmentSensor;
import com.taojin.iot.service.equipment.entity.EquipmentTrigger;
import com.taojin.iot.service.equipment.service.EquipmentSensorService;
import com.taojin.iot.service.equipment.service.EquipmentService;
import com.taojin.iot.service.equipment.service.EquipmentTriggerService;
import com.taojin.iot.service.report.dao.ReportEquipmentSensorDao;
import com.taojin.iot.service.report.entity.ReportEquipmentSensor;
import com.taojin.iot.service.report.service.ReportEquipmentSensorService;

/**
 * Service-设备传感器历史记录 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午3:06:31 author 王杰
 * ============================================================================
 */
@Service("reportEquipmentSensorServiceImpl")
public class ReportEquipmentSensorServiceImpl extends
		BaseServiceImpl<ReportEquipmentSensor, Long> implements
		ReportEquipmentSensorService {
	final static Logger logger = LoggerFactory
			.getLogger(ReportEquipmentSensorServiceImpl.class);
	@Resource(name = "reportEquipmentSensorDaoImpl")
	private ReportEquipmentSensorDao reportEquipmentSensorDao;
	@Resource(name="equipmentSensorServiceImpl")
	private EquipmentSensorService equipmentSensorService;
	@Resource(name="equipmentServiceImpl")
	private EquipmentService equipmentService;
	@Resource(name="equipmentTriggerServiceImpl")
	private EquipmentTriggerService equipmentTriggerService;

	@Resource(name = "reportEquipmentSensorDaoImpl")
	public void setBaseDao(ReportEquipmentSensorDao reportEquipmentSensorDao) {
		super.setBaseDao(reportEquipmentSensorDao);
	}

	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.username}")
	private String jdbcUsername;
	@Value("${jdbc.password}")
	private String jdbcPassword;

	/**
	 * 获取检索条件
	 * 
	 * @param pageable
	 * @return
	 */
	public Map<String, Object> getOrderMap(Pageable pageable) {
		if (pageable == null) {
			pageable = new Pageable();
		}

		Map<String, Object> orderMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(pageable.getOrderField())
				&& pageable.getOrderDirection() != null) {
			if (pageable.getOrderDirection() == Direction.asc) {
				orderMap.put("property", pageable.getOrderField());
				orderMap.put("direction", "asc");
			} else if (pageable.getOrderDirection() == Direction.desc) {
				orderMap.put("property", pageable.getOrderField());
				orderMap.put("direction", "desc");
			} else {
				orderMap.put("property", pageable.getOrderField());
				orderMap.put("direction", "desc");
			}
		} else {
			pageable.setOrderField("create_date");
			pageable.setOrderDirection(Direction.desc);
			orderMap.put("property", pageable.getOrderField());
			orderMap.put("direction", "desc");
		}

		String whereStr = " where 1=1 ";

		// 匹配查询
		for (Filter filter : pageable.getFilters()) {
			if (filter == null || StringUtils.isEmpty(filter.getProperty())) {
				continue;
			}
			if (filter.getOperator() == Operator.eq
					&& filter.getValue() != null) {
				if (filter.getIgnoreCase() != null && filter.getIgnoreCase()
						&& filter.getValue() instanceof String) {
					whereStr += " and " + filter.getProperty() + "='"
							+ filter.getValue() + "'";
				} else {
					whereStr += " and " + filter.getProperty() + "='"
							+ filter.getValue() + "'";
				}
			} else if (filter.getOperator() == Operator.ne
					&& filter.getValue() != null) {
				if (filter.getIgnoreCase() != null && filter.getIgnoreCase()
						&& filter.getValue() instanceof String) {
					whereStr += " and " + filter.getProperty() + " <> '"
							+ filter.getValue() + "'";
				} else {
					whereStr += " and " + filter.getProperty() + " <> '"
							+ filter.getValue() + "'";
				}
			} else if (filter.getOperator() == Operator.gt
					&& filter.getValue() != null) {
				whereStr += " and " + filter.getProperty() + " > '"
						+ filter.getValue() + "'";
			} else if (filter.getOperator() == Operator.lt
					&& filter.getValue() != null) {
				whereStr += " and " + filter.getProperty() + " < '"
						+ filter.getValue() + "'";
			} else if (filter.getOperator() == Operator.ge
					&& filter.getValue() != null) {
				whereStr += " and " + filter.getProperty() + " >= '"
						+ filter.getValue() + "'";
			} else if (filter.getOperator() == Operator.le
					&& filter.getValue() != null) {
				whereStr += " and " + filter.getProperty() + " <= '"
						+ filter.getValue() + "'";
			} else if (filter.getOperator() == Operator.like
					&& filter.getValue() != null
					&& filter.getValue() instanceof String) {
				whereStr += " and " + filter.getProperty() + " like '%"
						+ filter.getValue() + "%'";
			} else if (filter.getOperator() == Operator.in
					&& filter.getValue() != null) {
			} else if (filter.getOperator() == Operator.isNull) {
				whereStr += " and " + filter.getProperty() + "is null";
			} else if (filter.getOperator() == Operator.isNotNull) {
				whereStr += " and " + filter.getProperty() + "is not null";
			}
		}

		orderMap.put("pageNumber", pageable.getPageNumber());
		orderMap.put("pageSize", pageable.getPageSize());
		orderMap.put("whereStr", whereStr);
		return orderMap;
	}

	/**
	 * 获取检索条件
	 * 
	 * @param pageable
	 * @return
	 */
	public Map<String, Object> getOrderMap(List<Filter> filters) {
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("property", "create_date");
		orderMap.put("direction", "desc");
		String whereStr = " where 1=1 ";

		// 匹配查询
		for (Filter filter : filters) {
			if (filter == null || StringUtils.isEmpty(filter.getProperty())) {
				continue;
			}
			if (filter.getOperator() == Operator.eq
					&& filter.getValue() != null) {
				if (filter.getIgnoreCase() != null && filter.getIgnoreCase()
						&& filter.getValue() instanceof String) {
					whereStr += " and " + filter.getProperty() + "='"
							+ filter.getValue() + "'";
				} else {
					whereStr += " and " + filter.getProperty() + "='"
							+ filter.getValue() + "'";
				}
			} else if (filter.getOperator() == Operator.ne
					&& filter.getValue() != null) {
				if (filter.getIgnoreCase() != null && filter.getIgnoreCase()
						&& filter.getValue() instanceof String) {
					whereStr += " and " + filter.getProperty() + " <> '"
							+ filter.getValue() + "'";
				} else {
					whereStr += " and " + filter.getProperty() + " <> '"
							+ filter.getValue() + "'";
				}
			} else if (filter.getOperator() == Operator.gt
					&& filter.getValue() != null) {
				whereStr += " and " + filter.getProperty() + " > '"
						+ filter.getValue() + "'";
			} else if (filter.getOperator() == Operator.lt
					&& filter.getValue() != null) {
				whereStr += " and " + filter.getProperty() + " < '"
						+ filter.getValue() + "'";
			} else if (filter.getOperator() == Operator.ge
					&& filter.getValue() != null) {
				whereStr += " and " + filter.getProperty() + " >= '"
						+ filter.getValue() + "'";
			} else if (filter.getOperator() == Operator.le
					&& filter.getValue() != null) {
				whereStr += " and " + filter.getProperty() + " <= '"
						+ filter.getValue() + "'";
			} else if (filter.getOperator() == Operator.like
					&& filter.getValue() != null
					&& filter.getValue() instanceof String) {
				if (StringUtils.startsWith(filter.getValue().toString(), "*")
						&& StringUtils.endsWith(filter.getValue().toString(),
								"*")) {
					whereStr += " and "
							+ filter.getProperty()
							+ " like '%"
							+ filter.getValue()
									.toString()
									.substring(
											1,
											filter.getValue().toString()
													.length() - 1) + "%'";
				} else if (StringUtils.startsWith(filter.getValue().toString(),
						"*")) {
					whereStr += " and "
							+ filter.getProperty()
							+ " like '"
							+ filter.getValue()
									.toString()
									.substring(
											1,
											filter.getValue().toString()
													.length()) + "%'";
				} else if (StringUtils.endsWith(filter.getValue().toString(),
						"*")) {
					whereStr += " and "
							+ filter.getProperty()
							+ " like '%"
							+ filter.getValue()
									.toString()
									.substring(
											0,
											filter.getValue().toString()
													.length() - 1) + "'";
				} else {
					whereStr += " and " + filter.getProperty() + " like '"
							+ filter.getValue() + "'";
				}
			} else if (filter.getOperator() == Operator.in
					&& filter.getValue() != null) {
			} else if (filter.getOperator() == Operator.isNull) {
				whereStr += " and " + filter.getProperty() + " is null";
			} else if (filter.getOperator() == Operator.isNotNull) {
				whereStr += " and " + filter.getProperty() + " is not null";
			}
		}

		orderMap.put("whereStr", whereStr);
		return orderMap;
	}

	/**
	 * 分页查询
	 */
	public Page<ReportEquipmentSensor> findPageSensor(String startTime,
			String endTime, Pageable pageable) {
		logger.info("[传感器数据查询]---->分页查询:startTime={},endTime={}",startTime,endTime);
		Map<String, Object> orderMap = getOrderMap(pageable);

		if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {// 如果日期为空则取当天日期
			startTime = DatesUtils.getStringToday("yyyy-MM-dd");
			try {
				endTime = DatesUtils.dateChanageFormat(startTime, 1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			try {
				endTime = DatesUtils.dateChanageFormat(endTime, 1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		logger.info("[传感器数据查询]---->时间处理结果:startTime={},endTime={}",startTime,endTime);
		String whereStrs = "";// 条件

		if (StringUtils.isBlank(pageable.getOrderField())) {
			pageable.setOrderField("createDate");
			pageable.setOrderDirection(Direction.desc);
		}

		String timeLimit = " and date_time >='" + startTime
				+ "' and date_time<='" + endTime + "'";
		String sql = "";// sql
		String whereStr = orderMap.get("whereStr").toString() + whereStrs;// 条件
		String selectStr = "id,create_date,creator,equipment_type_id,is_del,modify_date,owner_id,date_time,"
				+ "sensor_id,sensor_number,sensor_true_value,sensor_values";// 查询字段
		int start = (pageable.getPageNumber() - 1) * pageable.getPageSize();
		int end = pageable.getPageSize();
		Long total = 0L;

		// 取总记录数
		sql = "select count(*) from iot_report_equipment_sensor" + whereStr
				+ timeLimit;
		total = reportEquipmentSensorDao.count(sql);
		logger.info("[传感器数据查询]---->sql-count:sql={},total={}",sql,total);
		sql = "select " + selectStr + " from iot_report_equipment_sensor"
				+ whereStr + timeLimit;
		sql += " order by " + orderMap.get("property") + " "
				+ orderMap.get("direction") + " limit " + start + "," + end;
		logger.info("[传感器数据查询]---->sql字符串:sql={}",sql);
		List<ReportEquipmentSensor> reportEquipmentSensors = reportEquipmentSensorDao
				.findByfindByProperty(sql);
		if (reportEquipmentSensors == null) {
			logger.warn("[传感器数据查询]---->sql查询为空:sql={}",sql);
			reportEquipmentSensors = new ArrayList<ReportEquipmentSensor>();
		}
		logger.info("[传感器数据查询]---->sql查询成功:sql={}",sql);
		return new Page<ReportEquipmentSensor>(reportEquipmentSensors, total,
				pageable);
	}

	/**
	 * 列表查询
	 * 
	 * @param count
	 * @param filters
	 * @return
	 */
	public List<ReportEquipmentSensor> findListSensor(Integer count,
			List<Filter> filters) {
		Map<String, Object> orderMap = this.getOrderMap(filters);

		String whereStrs = "";// 条件
		String sql = "";// sql
		String whereStr = orderMap.get("whereStr").toString() + whereStrs;// 条件
		String selectStr = "id,create_date,creator,equipment_type_id,is_del,modify_date,owner_id,date_time,"
				+ "sensor_id,sensor_number,sensor_true_value,sensor_values";// 查询字段
		String limit = "";
		if (count != null) {
			int start = 0;
			int end = count;
			limit = " limit " + start + "," + end;
		}

		sql = "select " + selectStr + " from iot_report_equipment_sensor"
				+ whereStr;
		sql += " order by " + orderMap.get("property") + " "
				+ orderMap.get("direction") + limit;
		List<ReportEquipmentSensor> reportEquipmentSensors = reportEquipmentSensorDao
				.findByfindByProperty(sql);
		if (reportEquipmentSensors == null) {
			reportEquipmentSensors = new ArrayList<ReportEquipmentSensor>();
		}
		return reportEquipmentSensors;
	}

	/**
	 * 添加传感器记录
	 * 
	 * @param reportEquipmentSensor
	 * @param datetime
	 */
	@SuppressWarnings("null")
	public void saveReport(ReportEquipmentSensor reportEquipmentSensor,
			String datetime) {
		Connection conn = null;
		try {
			conn = (Connection) DriverManager.getConnection(jdbcUrl,
					jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			try {
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		String nowDay = DatesUtils.getStringToday("yyyyMMdd");
		if (StringUtils.isNotBlank(datetime)) {
			nowDay = datetime;
		}
		String tablename = "iot_report_equipment_sensor_" + nowDay;
		StringBuffer insertSql = new StringBuffer();
		insertSql
				.append("INSERT INTO "
						+ tablename
						+ "("
						+ "create_date,creator,equipment_type_id,is_del,modify_date,owner_id,date_time,"
						+ "sensor_id,sensor_number,sensor_true_value,sensor_values"
						+ ") VALUES ");
		insertSql.append("(");
		insertSql.append("'" + DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss")
				+ "',");
		if (reportEquipmentSensor.getCreator() != null) {
			insertSql.append(" " + reportEquipmentSensor.getCreator() + ",");
		} else {
			insertSql.append(null+",");
		}
		if (reportEquipmentSensor.getEquipmentTypeId() != null) {
			insertSql.append("'" + reportEquipmentSensor.getEquipmentTypeId()
					+ "',");
		} else {
			insertSql.append(null+",");
		}
		insertSql.append("0,");
		insertSql.append("'" + DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss")
				+ "',");
		if (reportEquipmentSensor.getOwnerId() != null) {
			insertSql.append("'" + reportEquipmentSensor.getOwnerId() + "',");
		} else {
			insertSql.append(null+",");
		}
		if (reportEquipmentSensor.getDateTime() != null) {
			insertSql.append("'" + reportEquipmentSensor.getDateTime() + "',");
		} else {
			insertSql.append("'',");
		}
		if (reportEquipmentSensor.getSensorId() != null) {
			insertSql.append("'" + reportEquipmentSensor.getSensorId() + "',");
		} else {
			insertSql.append(null+",");
		}
		if (reportEquipmentSensor.getSensorNumber() != null) {
			insertSql.append("'" + reportEquipmentSensor.getSensorNumber()
					+ "',");
		} else {
			insertSql.append("'',");
		}
		if (reportEquipmentSensor.getSensorTrueValue() != null) {
			insertSql.append("'" + reportEquipmentSensor.getSensorTrueValue()
					+ "',");
		} else {
			insertSql.append("'',");
		}
		if (reportEquipmentSensor.getSensorValues() != null) {
			insertSql.append("'" + reportEquipmentSensor.getSensorValues()
					+ "'");
		} else {
			insertSql.append("''");
		}
		
		insertSql.append(")");
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(insertSql
					.toString());
			pstmt.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			try {
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加传感器记录
	 * 
	 * @param reportEquipmentSensor
	 * @param datetime
	 */
	@SuppressWarnings("null")
	@Override
	public void addData(ReportEquipmentSensor reportEquipmentSensor,
			String datetime,Integer uniqueParam,String equipmentNumber) {
		logger.info("[传感器记录添加]---->开始：dateTime={},equipmentNumber={}",uniqueParam,equipmentNumber);
		//获取设备对应序号的传感器
		Equipment equipment = equipmentService.getByParam("serialNumber", equipmentNumber);
		if(equipment == null){
			logger.error("[传感器记录添加]---->失败，传感器设备不存在,equipmentNumber={}",equipmentNumber);
			return;
		}
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(Filter.eq("serialNumber", uniqueParam));
		filters.add(Filter.eq("equipment", equipment));
		List<EquipmentSensor> equipmentSensors = equipmentSensorService.findList(1, filters, null);
		
		if(equipmentSensors.size() == 0){
			logger.error("[传感器记录添加]---->失败，传感器不存在,编号={}",uniqueParam);
			return;
		}
		
		reportEquipmentSensor.setEquipmentTypeId(equipment.getEquipmentTypeId());
		reportEquipmentSensor.setSensorId(equipmentSensors.get(0).getId());
		reportEquipmentSensor.setSensorNumber(equipmentSensors.get(0).getIdNumber());
		reportEquipmentSensor.setDateTime(DatesUtils.getStringToday("yyyy-MM-dd"));
		JSONObject json = new JSONObject();
		json.put("value", reportEquipmentSensor.getSensorTrueValue());
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(json);
		reportEquipmentSensor.setSensorValues(jsonArray.toString());
		Connection conn = null;
		try {
			conn = (Connection) DriverManager.getConnection(jdbcUrl,
					jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			try {
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		String nowDay = DatesUtils.getStringToday("yyyyMMdd");
		if (StringUtils.isNotBlank(datetime)) {
			nowDay = datetime;
		}
		String tablename = "iot_report_equipment_sensor_" + nowDay;
		StringBuffer insertSql = new StringBuffer();
		insertSql
				.append("INSERT INTO "
						+ tablename
						+ "("
						+ "create_date,creator,equipment_type_id,is_del,modify_date,owner_id,date_time,"
						+ "sensor_id,sensor_number,sensor_true_value,sensor_values"
						+ ") VALUES ");
		insertSql.append("(");
		insertSql.append("'" + DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss")
				+ "',");
		if (reportEquipmentSensor.getCreator() != null) {
			insertSql.append(" " + reportEquipmentSensor.getCreator() + ",");
		} else {
			insertSql.append(null+",");
		}
		if (reportEquipmentSensor.getEquipmentTypeId() != null) {
			insertSql.append("'" + reportEquipmentSensor.getEquipmentTypeId()
					+ "',");
		} else {
			insertSql.append(null+",");
		}
		insertSql.append("0,");
		insertSql.append("'" + DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss")
				+ "',");
		if (reportEquipmentSensor.getOwnerId() != null) {
			insertSql.append("'" + reportEquipmentSensor.getOwnerId() + "',");
		} else {
			insertSql.append(null+",");
		}
		if (reportEquipmentSensor.getDateTime() != null) {
			insertSql.append("'" + reportEquipmentSensor.getDateTime() + "',");
		} else {
			insertSql.append("'',");
		}
		if (reportEquipmentSensor.getSensorId() != null) {
			insertSql.append("'" + reportEquipmentSensor.getSensorId() + "',");
		} else {
			insertSql.append(null+",");
		}
		if (reportEquipmentSensor.getSensorNumber() != null) {
			insertSql.append("'" + reportEquipmentSensor.getSensorNumber()
					+ "',");
		} else {
			insertSql.append("'',");
		}
		if (reportEquipmentSensor.getSensorTrueValue() != null) {
			insertSql.append("'" + reportEquipmentSensor.getSensorTrueValue()
					+ "',");
		} else {
			insertSql.append("'',");
		}
		if (reportEquipmentSensor.getSensorValues() != null) {
			insertSql.append("'" + reportEquipmentSensor.getSensorValues()
					+ "'");
		} else {
			insertSql.append("''");
		}
		
		insertSql.append(")");
		logger.info("[传感器记录添加]---->insertSql={}",insertSql);
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(insertSql
					.toString());
			pstmt.executeUpdate();
			conn.close();
			logger.info("[传感器记录添加]---->成功：insertSql={}",insertSql);
			try{
				List<Filter> triggerFilters = new ArrayList<Filter>();
				triggerFilters.add(Filter.eq("equipmentSensor", equipmentSensors.get(0)));
				triggerFilters.add(Filter.eq("equipment", equipment));
				List<EquipmentTrigger> equipmentTriggers = equipmentTriggerService.findList(null, triggerFilters, null);
				for(int i=0;i<equipmentTriggers.size();i++){
					JSONArray triggerJsonarray = new JSONArray();
					JSONObject triggerJson = new JSONObject();
					triggerJson.put("value", reportEquipmentSensor.getSensorTrueValue());
					triggerJson.put("state", 1);
					triggerJson.put("switchState", "1");
					triggerJson.put("time", DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"));
					triggerJsonarray.add(triggerJson);
					equipmentTriggerService.equipmentAlarmTypeTrigger(equipmentTriggers.get(i), triggerJsonarray);
				}
			}catch(Exception e1){
				logger.error("[触发器判断失败]---->异常：errMesg={}",e1.getMessage());
			}
		} catch (SQLException e) {
			try {
				conn.close();
			} catch (SQLException e1) {
				logger.error("[传感器记录添加]---->异常：errMesg={}",e1.getMessage());
				e1.printStackTrace();
			}
			logger.error("[传感器记录添加]---->异常：errMesg={}",e.getMessage());
			e.printStackTrace();
		}
	}

}
