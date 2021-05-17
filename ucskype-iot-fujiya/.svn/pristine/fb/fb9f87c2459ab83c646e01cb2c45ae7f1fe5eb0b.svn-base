package com.taojin.iot.api.work.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.taojin.iot.BaseController;
import com.taojin.iot.agreement.fujiya.entity.AgreementRc701Value;
import com.taojin.iot.agreement.fujiya.enums.AddressTypeEnum;
import com.taojin.iot.agreement.fujiya.service.AgreementRc701ValueService;
import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.Order;
import com.taojin.iot.base.comm.Page;
import com.taojin.iot.base.comm.Pageable;
import com.taojin.iot.base.comm.entity.UserSession;
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.service.equipment.entity.Equipment;
import com.taojin.iot.service.equipment.entity.EquipmentSensor;
import com.taojin.iot.service.equipment.service.EquipmentSensorService;
import com.taojin.iot.service.equipment.service.EquipmentService;
import com.taojin.iot.service.kanban.service.ProductionKanBanService;
import com.taojin.iot.service.task.entity.ProductionLine;
import com.taojin.iot.service.task.entity.WorkFinish;
import com.taojin.iot.service.task.entity.WorkOrder;
import com.taojin.iot.service.task.entity.WorkReport;
import com.taojin.iot.service.task.entity.WorkReportDetail;
import com.taojin.iot.service.task.entity.WorkTraceDetail;
import com.taojin.iot.service.task.service.ProductionLineService;
import com.taojin.iot.service.task.service.WorkFinishService;
import com.taojin.iot.service.task.service.WorkOrderService;
import com.taojin.iot.service.task.service.WorkReportDetailService;
import com.taojin.iot.service.task.service.WorkReportService;
import com.taojin.iot.service.task.service.WorkTraceDetailService;
import com.taojin.iot.service.user.entity.User;
import com.taojin.iot.service.user.service.UserService;

@Controller("internalWorkTaskController")
@RequestMapping("/internal/task/workTask")
public class WorkTaskController extends BaseController {
	@Autowired
	private WorkOrderService workOrderService;
	@Resource(name = "workReportServiceImpl")
	private WorkReportService workReportService;
	@Resource(name = "workReportDetailServiceImpl")
	private WorkReportDetailService workReportDetailService;
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "productionLineServiceImpl")
	private ProductionLineService productionLineService;
	@Resource(name = "equipmentServiceImpl")
	private EquipmentService equipmentService;
	@Resource(name = "equipmentSensorServiceImpl")
	private EquipmentSensorService equipmentSensorService;
	@Resource(name = "productionKanBanServiceImpl")
	private ProductionKanBanService productionKanBanService;
	@Resource(name = "agreementRc701ValueServiceImpl")
	private AgreementRc701ValueService agreementRc701ValueService;
	
	@Resource(name = "workTraceDetailServiceImpl")
	private WorkTraceDetailService workTraceDetailService;
	@Resource(name = "workFinishServiceImpl")
	private WorkFinishService workFinishService;

	/**
	 * 添加生产任务
	 * @throws ParseException 
	 */

	@RequestMapping(value = "/workOrderAdd", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workOrderAdd(String requestParams) throws ParseException {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node;
		JSONObject param;
		JSONObject session;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			session = node.getJSONObject("session");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date start = new Date();
//		
		WorkOrder workOrder = com.alibaba.fastjson.JSONObject.parseObject(param.toString(), WorkOrder.class);
//		
//		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		UserSession userSession = super.getSession(session);
		User user = userService.find(Long.valueOf(userSession.getUserId()));
		JSONObject jsonReturn = new JSONObject();
		if(workOrderService.addWorkOrder(workOrder, user)){
			jsonReturn.put("code", "0");
			jsonReturn.put("msg", "生产任务添加成功");
		}else{
			jsonReturn.put("code", "-2");
			jsonReturn.put("msg", "生产任务添加失败");
		}
		return jsonReturn.toString();
		//生产数量只能为整数
//		if (workOrder.getCount() > 0) {
//			/*
//			 * 计划开始时间属于当天时间范围
//			 * 查询是否有进行中的任务
//			 * 若开始时间、结束时间为空开始时间默认为班次开始时间、结束时间默认为班次结束时间
//			 * 
//			 */
//			if (isToday(start)) {
//				List<Filter> filters = new ArrayList<>();
//				filters.add(Filter.eq("status", "进行中"));
//				filters.add(Filter.eq("isDel", false));
//				filters.add(Filter.eq("lineNumber", workOrder.getLineNumber()));
//				List<WorkOrder> list = workOrderService.findList(null, filters, null);
//				if (list.size() > 0) {
//					return successMsg("-6", "当前生产线有在执行中的任务");
//				}
//				UserSession userSession = super.getSession(session);
//				User user = userService.find(Long.valueOf(userSession.getUserId()));
//				workOrder.setLineName(productionLineService.getByParam("lineNumber",workOrder.getLineNumber()).getLineName());
//				workOrder.setCreatorName(user.getName());
//				workOrder.setCreatTime(DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"));
//				
//				if (workOrder.getStartTime() == null || "".equals(workOrder.getStartTime()) || "null".equals(workOrder.getStartTime())) {
//					String startTime = "";
//					String endTime = "";
//			        Calendar calendar = Calendar.getInstance();
//			        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//			        if (hour >= 7 && hour < 15) {//早班
//			        	startTime = sdFormat.format(new Date()) +" 07:00:00";
//			        	endTime = sdFormat.format(new Date()) +" 15:00:00";
//					}
//			        else if (hour >= 15 && hour < 23) {//中班
//			        	startTime = sdFormat.format(new Date()) +" 15:00:00";
//			        	endTime = sdFormat.format(new Date()) +" 23:00:00";
//					}
//			        else{//晚班
//			        	startTime = sdFormat.format(new Date()) +" 23:00:00";
//			        	endTime = sdFormat.format(new Date()) +" 07:00:00";
//					}
//			        workOrder.setStartTime(startTime);
//			        workOrder.setEndTime(endTime);
//				}
//				workOrder.setStatus("进行中");
//				workOrderService.save(workOrder);
//			}
//			
//			/*
//			 * 计划开始时间不属于今天时间范围
//			 * 根据开始时间判断班次，计划开始时间、计划结束时间为班次时间的时间点
//			 * 开始时间结束时间不能超过8小时
//			 */
//			if (!isToday(start)) {
//				if (timeCalculation(workOrder.getStartTime(),workOrder.getEndTime()) > 8) {
//					return successMsg("-6", "开始时间、结束时间间隔超8小时");
//				}
//				Date startDate = sdf.parse(workOrder.getStartTime());
//				Date endDate = sdf.parse(workOrder.getStartTime());
//				SimpleDateFormat df = new SimpleDateFormat("HH");
//		        String str = df.format(startDate);
//		        Integer hour = Integer.parseInt(str);
//		        String startTime = "";
//				String endTime = "";
//		        if (hour >= 7 && hour < 15) {//早班
//		        	startTime = sdFormat.format(startDate) +" 07:00:00";
//		        	endTime = sdFormat.format(endDate) +" 15:00:00";
//				}else if (hour >= 15 && hour < 23) {//中班
//					startTime = sdFormat.format(startDate) +" 15:00:00";
//		        	endTime = sdFormat.format(endDate) +" 23:00:00";
//				}else {
//					startTime = sdFormat.format(startDate) +" 23:00:00";
//		        	endTime = sdFormat.format(endDate) +" 07:00:00";
//				}
//				UserSession userSession = super.getSession(session);
//				User user = userService.find(Long.valueOf(userSession.getUserId()));
//				workOrder.setLineName(productionLineService.getByParam("lineNumber",workOrder.getLineNumber()).getLineName());
//				workOrder.setCreatorName(user.getName());
//				workOrder.setCreatTime(DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"));
//				workOrder.setStatus("未开始");
//				workOrder.setStartTime(startTime);
//				workOrder.setEndTime(endTime);
//				workOrderService.save(workOrder);
//			}
//			JSONObject jsonReturn = new JSONObject();
//			jsonReturn.put("code", "0");
//			jsonReturn.put("msg", "添加生产任务成功");
//			return jsonReturn.toString();
//		}
	}
	
	/**
     * 计算时间差
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException 
     */
    private Double timeCalculation(String startTime,String endTime) {
    	double time = 0;
    	try {
    		double nd = 1000 * 24 * 60 * 60;
    		double nh = 1000 * 60 *60;
    		double nm = 1000 * 60;
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		Date start = sdf.parse(startTime);
    		Date end = sdf.parse(endTime);
    		double diff = end.getTime() - start.getTime();
    		//天
    		double day = diff/nd;
    		//小时
    		double hour = diff/nh;
    		//分钟
    		double minute = diff/nm;
    		time = hour;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return time;
    }

	/**
	 * 根据时间判断班次
	 * @param time
	 * @return
	 */
	private Integer isShifts(String time){
		try {
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdFormat.parse(time);
			SimpleDateFormat df = new SimpleDateFormat("HH");
	        String str = df.format(date);
	        Integer hour = Integer.parseInt(str);
	        if (hour >= 7 && hour < 15) {//早班
	        	
			}else if (hour >= 15 && hour < 23) {//中班
				
			}else {
				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 添加报工单
	 */
	@RequestMapping(value = "/workReportAdd", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workReportAdd(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node;
		JSONObject param;
		JSONObject session;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			session = node.getJSONObject("session");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
		WorkReport workReport = com.alibaba.fastjson.JSONObject.parseObject(
				param.toString(), WorkReport.class);
		UserSession userSession = super.getSession(session);
		User user = userService.find(Long.valueOf(userSession.getUserId()));
		workReport.setReporter(user.getName());
		workReport.setProductionLine(workOrderService.getByParam("orderNumber",
				workReport.getOrderNumber()).getLineName());
		workReport.setReportTime(DatesUtils.getStringToday("yyyy-MM-dd"));
		workReportService.save(workReport);
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("code", "0");
		jsonReturn.put("msg", "添加报工单成功");
		return jsonReturn.toString();
	}

	/**
	 * 更新生产任务
	 */
	@RequestMapping(value = "/workOrderUpdate", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workOrderUpdate(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node;
		JSONObject param;
		JSONObject session;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			session = node.getJSONObject("session");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
		WorkOrder workOrder = com.alibaba.fastjson.JSONObject.parseObject(
				param.toString(), WorkOrder.class);
		if ("已完成".equals(workOrder.getStatus())) {
			WorkOrder workOrder1 = workOrderService.find(workOrder.getId());
			if ("进行中".equals(workOrder1.getStatus())) {
				long longDate = System.currentTimeMillis();
				Date nowDate = new Date(longDate);
				workOrder.setFinishTime(DatesUtils.compareS(
						workOrder1.getCreateDate(), nowDate));
				workOrder.setUpdateTime(DatesUtils
						.getStringToday("yyyy-MM-dd HH:mm:ss"));
				WorkReportDetail workReportDetail = new WorkReportDetail();
				workReportDetail.setCreatTime(DatesUtils
						.getStringToday("yyyy-MM-dd HH:mm:ss"));
				workReportDetail.setLineName(workOrder.getLineName());
				workReportDetail.setLineCode(workOrder.getLineNumber());
				workReportDetail.setOrderNumber(workOrder.getOrderNumber());
				workReportDetail.setMsg("任务单完结");
				workReportDetailService.save(workReportDetail);
				WorkReport workReport = new WorkReport();
				workReport.setWorkReporNumber("BG"
						+ DatesUtils.getStringToday("yyyyMMddHHmmss"));
				int x = 0;
				int y = 0;
				List<Filter> filters = new ArrayList<Filter>();
				List<Filter> filters2 = new ArrayList<Filter>();
				List<Order> orders = new ArrayList<Order>();
				orders.add(Order.desc("modifyDate"));
				if ("RC70-1".equals(workOrder.getLineName())) {
					filters.add(Filter.eq("address", "DB214.DBW160"));
					filters2.add(Filter.eq("address", "DB214.DBW164"));
				}
				if ("RD77-1".equals(workOrder.getLineName())) {
					filters.add(Filter.eq("address", "DB1503.DBW0"));
					filters2.add(Filter.eq("address", "DB1503.DBW4"));
				}
				if ("EPUMP-2Main".equals(workOrder.getLineName())) {
					filters.add(Filter.eq("address", "DB1704.DBW0"));
					filters2.add(Filter.eq("address", "DB1704.DBW4"));
				}
				if ("EPUMP-2Gearless".equals(workOrder.getLineName())) {
					filters.add(Filter.eq("address", "DB302.DBW0"));
					filters2.add(Filter.eq("address", "DB305.DBW4"));
				}
				/**
				 * 获取实际生产数
				 * 若没有则为0
				 */
				List<AgreementRc701Value> agreementRc701Value = agreementRc701ValueService.findList(null, filters, orders);
				if (agreementRc701Value.size() != 0) {
					if (agreementRc701Value.get(0).getCommandValue() != null) {
						x = agreementRc701Value.get(0).getCommandValue();
					} else {
						x = 0;
					}
					agreementRc701Value = agreementRc701ValueService.findList(
							null, filters2, orders);
					if (agreementRc701Value.size() != 0) {
						if (agreementRc701Value.get(0).getCommandValue() != null) {
							y = agreementRc701Value.get(0).getCommandValue();
						} else {
							y = 0;
						}
					}
				} else {
					x = 0;
					y = 0;
				}
				/**
				 * 工站
				 */
				// workReport.setEquipmentName(workOrder1);
				workReport.setOrderNumber(workOrder1.getOrderNumber());
				workReport.setProductionModel(workOrder1.getProductionModel());
				workReport.setProductionLine(workOrder1.getLineName());
				workReport.setPlanCount(workOrder1.getCount());
				/**
				 * 实际生产数量
				 */
				workReport.setActualCount(x);
				/**
				 * ok数量
				 */
				workReport.setOkCount(x - y);
				/**
				 * nok数量
				 */
				workReport.setNokCount(y);

				/**
				 * 缺陷率
				 */
				workReport.setNokPercent(0 + "");
				/**
				 * 合格率
				 */
				workReport.setYieldPercent(0 + "");
				UserSession userSession = super.getSession(session);
				User user = userService.find(Long.valueOf(userSession.getUserId()));
				workReport.setReporter(user.getName());

				workReport.setReportTime(DatesUtils
						.getStringToday("yyyy-MM-dd HH:mm:ss"));
				workReportService.save(workReport);
			}
		}
		workOrderService.update(workOrder);
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("code", "0");
		jsonReturn.put("msg", "更新生产任务成功");
		return jsonReturn.toString();
	}

	/**
	 * 更新报工单
	 */
	@RequestMapping(value = "/workReportUpdate", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workReportUpdate(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
		WorkReport workReport = com.alibaba.fastjson.JSONObject.parseObject(
				param.toString(), WorkReport.class);
		workReportService.update(workReport);
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("code", "0");
		jsonReturn.put("msg", "更新报工单成功");
		return jsonReturn.toString();
	}

	@RequestMapping(value = "/workReportDetailList", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workReportDetailList(String requestParams) {
		Map<String, Object> map = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");

		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("workReport", param.getLong("id")));
		List<WorkReportDetail> list = workReportDetailService.findList(null,
				filters, null);
		map.put("code", 0);
		map.put("msg", "生产任务详情列表获取成功");
		map.put("values", list);
		return JSON.toJSONString(map);
	}

	/**
	 * 生产任务列表
	 * 
	 */
	@RequestMapping(value = "/workOrderList", produces = "application/josn;charset=utf-8")
	@ResponseBody
	public String workOrderList(String requestParams) {
		Map<String, Object> map = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			map.put("-31", "获取默认参数失败");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "参数解析错误!");
			return JSON.toJSONString(map);
		}
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("isDel", false));
//		if (!param.has("startTime1") && !param.has("startTime2")||"".equals(param.getString("startTime1"))
//				&& "".equals(param.getString("startTime2"))) {
//			param.put("startTime1", DatesUtils.getYestoday() + " 23:59:59");
//			param.put("startTime2", DatesUtils.getTomorrow() + " 00:00:00");
//		}
		if (param.has("startTime1") && param.has("startTime2")) {
			filters.add(Filter.between("startTime", param.get("startTime1"),
					param.get("startTime2")));
		}
		if (param.has("endTime1") && param.has("endTime2")
				&& !param.getString("endTime1").equals("")
				&& !param.getString("endTime2").equals("")) {
			filters.add(Filter.between("endTime", param.getString("endTime1"),
					param.get("endTime2")));
		}
		if (!param.has("pageNumber")) {
			param.put("pageNumber", 1);
		}
		if (!param.has("pageSize")) {
			param.put("pageSize", 20);
		}
		Pageable pageable = new Pageable(param.getInt("pageNumber"),
				param.getInt("pageSize"));
		if (param.has("productionModel")
				&& !param.getString("productionModel").equals("")) {
			filters.add(Filter.like("productionModel",
					"%" + param.get("productionModel") + "%"));
		}
		if (param.has("lineNumber")
				&& !param.getString("lineNumber").equals("")
				&& !param.getString("lineNumber").equals("null")) {
			filters.add(Filter.like("lineNumber", "%" + param.get("lineNumber")
					+ "%"));
		}
		List<Order> orders = new ArrayList<>();
		orders.add(Order.desc("createDate"));
		pageable.setFilters(filters);
		pageable.setOrders(orders);
		Page<WorkOrder> page = workOrderService.findPage(pageable);
		List<WorkOrder> list = page.getContent();
		for (WorkOrder wOrder : list) {
			wOrder.setStartTime(wOrder.getStartTime().replace(".0", ""));
			wOrder.setEndTime(wOrder.getEndTime().replace(".0", ""));
		}
		map.put("code", 0);
		map.put("msg", "生产任务列表获取成功");
		map.put("values", list);
		map.put("page", super.getPage(page));
		return JSON.toJSONString(map);
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/workReportList", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workReportList(String requestParams) {
		Map<String, Object> map = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			map.put("-31", "获取默认参数失败");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "参数解析错误!");
			return JSON.toJSONString(map);
		}
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("isDel", 0));
		if (!param.has("startTime") && !param.has("endTime")) {
			param.put("startTime", DatesUtils.getToday() + " 00:00:00");
			param.put("endTime", DatesUtils.getToday() + " 23:59:59");
		}
		if (!param.getString("startTime").equals("") && !param.getString("endTime").equals("")) {
			filters.add(Filter.between("reportTime", param.get("startTime"),param.get("endTime")));
		}
		if (!param.has("pageNumber")) {
			param.put("pageNumber", 1);
		}

		if (!param.has("pageSize")) {
			param.put("pageSize", 20);
		}
		Pageable pageable = new Pageable(param.getInt("pageNumber"),param.getInt("pageSize"));
		if (param.has("workReporNumber") && !param.getString("workReporNumber").equals("")) {
			filters.add(Filter.like("workReporNumber","%" + param.get("workReporNumber") + "%"));
		}
		if (param.has("M2type") && !param.getString("M2type").equals("")) {
			filters.add(Filter.like("productionModel","%" + param.get("M2type") + "%"));
		}
		List<Order> orders = new ArrayList<>();
		orders.add(Order.desc("createTime"));
		pageable.setFilters(filters);
		Page<WorkReport> page = workReportService.findPage(pageable);
		List<WorkReport> list = page.getContent();
		map.put("code", 0);
		map.put("msg", "报工单列表获取成功");
		map.put("values", list);
		map.put("page", super.getPage(page));
		return JSON.toJSONString(map);
	}

	/**
	 * 已完成任务单号
	 * 
	 */
	@RequestMapping(value = "/workOrderNumber", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workOrderNumber() {
		Map<String, Object> map = new HashMap<>(16);
		List<Map<String, Object>> values = new ArrayList<>();
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.ne("status", "已完成"));
		filters.add(Filter.eq("isDel", 0));
		List<WorkOrder> list = workOrderService.findList(null, filters, null);
		for (WorkOrder workOrder : list) {
			map.put("id", workOrder.getId());
			map.put("orderNumber", workOrder.getOrderNumber());
			values.add(map);
		}
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("code", "0");
		jsonReturn.put("msg", "任务单号列表获取成功");
		jsonReturn.put("values", values);
		return jsonReturn.toString();
	}

	/**
	 * 获取当天所有单号
	 * @param requestParams
	 * @return
	 */
	@RequestMapping(value = "/workOrderNumberAll", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workOrderNumberAll(String requestParams) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node;
		JSONObject param=null;
		try {
			node = JSONObject.fromObject(requestParams);
			if(!"".equals(node.getString("param")))
			param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
//		filters.add(Filter.eq("status", "进行中"));
//		filters.add(Filter.eq("isDel", false));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(new Date());
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("isDel", false));
		if(param!=null){
			if (param.has("lineNumber") && !"".equals(param.getString("lineNumber"))) {
				filters.add(Filter.eq("lineNumber", param.getString("lineNumber")));
				filters.add(Filter.between("startTime", time + " 00:00:00", time+" 23:59:59"));
			}
		}
		List<WorkOrder> list = workOrderService.findList(null, filters, null);
		map.put("code", 0);
		map.put("msg", "任务单列表获取成功");
		map.put("values", list);
		return JSON.toJSONString(map);
	}

	/**
	 * 追溯目录
	 * 
	 */
	@RequestMapping(value = "/trace", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String trace(String requestParams) {
		HashMap<String, Object> map2 = new HashMap<>();
		List<HashMap<String, Object>> list = new ArrayList<>();
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		// JSONObject node;
		// JSONObject param;
		try {
			// node = JSONObject.fromObject(requestParams);
			// param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("isDel", 0));
		// filters.add(Filter.eq("id", param.getLong("id")));
		List<ProductionLine> productionLineList = productionLineService
				.findList(null, filters, null);
		for (ProductionLine productionLine : productionLineList) {
			List<Filter> filters2 = new ArrayList<>();
			filters2.add(Filter.eq("isDel", 0));
			filters2.add(Filter.eq("lineNumber", productionLine.getLineNumber()));
			List<WorkOrder> orderList = workOrderService.findList(null,
					filters2, null);
			HashMap<String, Object> map = new HashMap<>();
			map.put("name", productionLine.getLineName());
			map.put("list", orderList);
			list.add(map);
		}
		map2.put("code", 0);
		map2.put("msg", "追溯目录获取成功");
		map2.put("values", list);
		return JSON.toJSONString(map2);
	}

	/**
	 * 追溯
	 * 
	 */
	@RequestMapping(value = "/traceDetail", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String traceDetail(String requestParams) {

		HashMap<String, Object> map2 = new HashMap<>();
		List<String> list = new ArrayList<>();
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("isDel", 0));
		// filters.add(Filter.eq("id", param.getLong("id")));
		/*
		 * 追溯目录
		 * 已完成或未完成、都可以查看追溯目录
		 * 未完成的任务单不展示总数
		 */
		WorkOrder order = workOrderService.find(param.getLong("id"));
		list.add(DatesUtils.dateToString(order.getCreateDate(),
				"yyyy-MM-dd HH:mm:ss")
				+ " "
				+ order.getCreatorName()
				+ " 新建生产任务单");
		filters.add(Filter.eq("orderNumber", order.getOrderNumber()));
		List<WorkReportDetail> msglist = workReportDetailService.findList(null,filters, null);
		for (WorkReportDetail workReportDetail : msglist) {
			if (!"任务单完结".equals(workReportDetail.getMsg())) {
				list.add(workReportDetail.getCreatTime().replace(".0", "") + " " +"工站  "+workReportDetail.getWorks() + workReportDetail.getMsg());
			}
			if ("任务单完结".equals(workReportDetail.getMsg())) {
				list.add(workReportDetail.getCreatTime().replace(".0", "") + " " + workReportDetail.getMsg());
			}
		}
		/*
		 * 获取生产总数、OK总数、NOK数 
		 */
		if("已完成".equals(order.getStatus())){
			WorkReport workReport = workReportService.getByParam("orderNumber",order.getOrderNumber());		
			int x = workReport.getActualCount();
			int y = workReport.getOkCount();
			int z = workReport.getNokCount();
			WorkReport report = new WorkReport();
			report.setOrderNumber(order.getOrderNumber());
			report.setProductionModel(order.getProductionModel());
			report.setPlanCount(order.getCount());
			report.setActualCount(x);
			report.setOkCount(y);
			report.setNokCount(z);
			map2.put("title2", report);
			int a = 100;
			if (y != 0) {
				a = y / x * 100;
			}
			map2.put("okPercent", a);
		}else{
			List<Filter> tracFilters = new ArrayList<>();
			tracFilters.add(Filter.eq("orderNumber", order.getOrderNumber()));
			List<WorkTraceDetail> details = workTraceDetailService.findList(null,tracFilters,null);
			Integer totalNum = 0;
			Integer okNum = 0;
			Integer nokNum = 0;
			for (WorkTraceDetail trace : details) {
				totalNum += trace.getTotalNum();
				okNum += trace.getOkNum();
				nokNum += trace.getNokNum();
			}
			WorkReport report = new WorkReport();
			report.setOrderNumber(order.getOrderNumber());
			report.setProductionModel(order.getProductionModel());
			report.setPlanCount(order.getCount());
			report.setActualCount(totalNum);
			report.setOkCount(okNum);
			report.setNokCount(nokNum);
			map2.put("title2", report);
		}
		map2.put("title1", order.getLineName());
		
		map2.put("detail", list);
		
		
		map2.put("code", 0);
		map2.put("msg", "追溯详情获取成功");
		return JSON.toJSONString(map2);
	}

	/**
	 * 生产日报表
	 * 
	 */
	@RequestMapping(value = "/workReport", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workReport(String requestParams) {
		Map<String, Object> map2 = new HashMap<>();
		if (StringUtils.isBlank(requestParams)) {
			map2.put("-31", "获取默认参数失败");
			return JSON.toJSONString(map2);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map2.put("-1", "参数解析错误!");
			return JSON.toJSONString(map2);
		}
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("isDel", false));
		if (param.has("startTime") && param.has("endTime")
				&& !param.getString("startTime").equals("") && !"null".equals(param.getString("startTime"))
				&& !param.getString("endTime").equals("") && !"null".equals(param.getString("endTime"))) {
			filters.add(Filter.between_two("reportTime", param.get("startTime"),param.get("endTime")));
		} else {
			String startTime = null;
			String endTime = null;
			if (param.has("flag")) {
				if (param.get("flag").equals("y")) {
					startTime = DatesUtils.getYearStart();
					endTime = DatesUtils.getYearEnd();
				} else if (param.get("flag").equals("m")) {
					startTime = DatesUtils.getMonthStart();
					endTime = DatesUtils.getMonthEnd();
				} else if (param.get("flag").equals("q")) {
					startTime = DatesUtils.getCurrQuarter(DatesUtils
							.getQuarter())[0];
					endTime = DatesUtils
							.getCurrQuarter(DatesUtils.getQuarter())[1];
				} else if (param.get("flag").equals("w")) {
					startTime = DatesUtils.getWeekStart();
					endTime = DatesUtils.getWeekEnd();
				} else {
					startTime = DatesUtils.getToday() + " 00:00:00";
					endTime = DatesUtils.getToday() + " 23:59:59";
				}
				filters.add(Filter.between("reportTime", startTime, endTime));
			}
		}
		if (!param.has("pageNumber")) {
			param.put("pageNumber", 1);
		}
		if (!param.has("pageSize")) {
			param.put("pageSize", 20);
		}
		if (param.has("productionModel") && !param.getString("productionModel").equals("") && !"null".equals(param.getString("productionModel"))) {
			filters.add(Filter.eq("productionModel",param.getString("productionModel")));
		}
		if (param.has("productionLine") && 
				!"null".equals(param.getString("productionLine")) && 
				!"".equals(param.getString("productionLine"))) {
			filters.add(Filter.eq("productionLine",param.getString("productionLine")));
		}
		//时间查询
		if (param.has("startTime") || param.has("endTime")) {
			filters.add(Filter.between_two("reportTime", param.getString("startTime"),param.getString("endTime")));
		}
		Pageable pageable = new Pageable(param.getInt("pageNumber"),param.getInt("pageSize"));
		pageable.setFilters(filters);
		Page<WorkReport> page = workReportService.findPage(pageable);
//		List<WorkReport> list = workReportService.findList(null, filters, null);
		
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "生产日报表获取成功");
		JSONArray jsonArray = new JSONArray();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i = 0; i < page.getContent().size(); i++) {
			JSONObject json = new JSONObject();
			WorkReport report = page.getContent().get(i);
			try {
				Date time = sdf.parse(report.getReportTime());
				json.put("id", report.getId());
				json.put("reportTime", sdf.format(time));
				json.put("productionLine", report.getProductionLine());
				json.put("productionModel", report.getProductionModel());
				json.put("equipmentName", report.getEquipmentName());
				json.put("planCount", report.getPlanCount());
				json.put("actualCount", report.getActualCount());
				json.put("okCount", report.getOkCount());
				json.put("nokCount", report.getNokCount());
				Double yieldPercent = 0.0;
				if (report.getActualCount() != 0 && "".equals(report.getActualCount())) {
					yieldPercent = (double) (report.getOkCount() / report.getActualCount());
				}
				json.put("yieldPercent", yieldPercent);
				jsonArray.add(json);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
			}
		}
		jsonReturn.put("values", jsonArray);
		jsonReturn = super.getJsonPage(page, jsonReturn);
		return jsonReturn.toString();
	}

	/**
	 * 不良品报表
	 * 
	 */
	@RequestMapping(value = "/workReportBad", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String nokReport(String requestParams) {
		Map<String, Object> map2 = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			map2.put("-31", "获取默认参数失败");
			return JSON.toJSONString(map2);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map2.put("-1", "参数解析错误!");
			return JSON.toJSONString(map2);
		}
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("isDel", false));
		if (param.has("startTime") && param.has("endTime")
				&& !param.getString("startTime").equals("")
				&& !param.getString("endTime").equals("")) {
			filters.add(Filter.between("reportTime", param.get("startTime"),
					param.get("endTime")));
		} else {
			String startTime = null;
			String endTime = null;
			if (param.has("flag")) {
				if (param.get("flag").equals("y")) {
					startTime = DatesUtils.getYearStart();
					endTime = DatesUtils.getYearEnd();
				} else if (param.get("flag").equals("m")) {
					startTime = DatesUtils.getMonthStart();
					endTime = DatesUtils.getMonthEnd();
				} else if (param.get("flag").equals("q")) {
					startTime = DatesUtils.getCurrQuarter(DatesUtils
							.getQuarter())[0];
					endTime = DatesUtils
							.getCurrQuarter(DatesUtils.getQuarter())[1];
				} else if (param.get("flag").equals("w")) {
					startTime = DatesUtils.getWeekStart() + " 00:00:00";
					endTime = DatesUtils.getWeekEnd()+ " 23:59:59";
				} else {
					startTime = DatesUtils.getToday() + " 00:00:00";
					endTime = DatesUtils.getToday() + " 23:59:59";
				}
				filters.add(Filter.between("creatTime", startTime, endTime));
			}
		}
		if (!param.has("pageNumber")) {
			param.put("pageNumber", 1);
		}
		if (!param.has("pageSize")) {
			param.put("pageSize", 20);
		}
		//生产型号查询
		if (param.has("productionModel") && !param.getString("productionModel").equals("") && !"".equals(param.getString("productionModel"))) {
			filters.add(Filter.eq("model",param.getString("productionModel")));
		}
		//产线查询
		if (param.has("productionLine") && !"null".equals(param.getString("productionLine")) && !"".equals(param.getString("productionLine"))) {
			filters.add(Filter.eq("lineName",param.getString("productionLine")));
		}
		//工站查询
		if (param.has("equipmentName") && !"null".equals(param.getString("equipmentName")) && !"".equals(param.getString("equipmentName"))) {
			filters.add(Filter.eq("works",param.getString("equipmentName")));
		}
		//时间查询
		if (param.has("startTime") || param.has("endTime")) {
			filters.add(Filter.between_two("creatTime", param.getString("startTime"),param.getString("endTime")));
		}
		filters.add(Filter.gt("nokNum", 0));
		
		Pageable pageable = new Pageable(param.getInt("pageNumber"),param.getInt("pageSize"));
		pageable.setFilters(filters);
		Page<WorkTraceDetail> page = workTraceDetailService.findPage(pageable);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonReturn = new JSONObject();
		for (int i = 0; i < page.getContent().size(); i++) {
			JSONObject json = new JSONObject();
			WorkTraceDetail detail = page.getContent().get(i);
//			if (detail.getNokNum() != 0 && detail.getNokNum() != null) {
				json.put("id", detail.getId());
				json.put("reportTime", detail.getCreatTime());
				json.put("productionLine", detail.getLineName());
				json.put("productionModel", detail.getModel());
				json.put("equipmentName", detail.getWorks());
				json.put("actualCount", detail.getTotalNum());
				json.put("nokCount", detail.getNokNum());
				Double percent = 0.0;
				if (detail.getTotalNum() != 0 && detail.getTotalNum() != null) {
					percent = (double) (detail.getNokNum() / detail.getTotalNum());
				}
				json.put("nokPercent",percent );
				jsonArray.add(json);
//			}
		}
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取不良品报表成功");
		jsonReturn.put("values", jsonArray);
		jsonReturn = super.getJsonPage(page, jsonReturn);
		return jsonReturn.toString();
	}

	@RequestMapping(value = "/lineList", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String lineList(String requestParams) {
		Map<String, Object> map2 = new HashMap<>();
		if (StringUtils.isBlank(requestParams)) {
			map2.put("-31", "获取默认参数失败");
			return JSON.toJSONString(map2);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map2.put("-1", "参数解析错误!");
			return JSON.toJSONString(map2);
		}
		if(!param.has("lineName")){
			map2.put("-2", "产线名称不能为空!");
		}
		List<Filter> filters = new ArrayList<Filter>();
		if(StringUtils.isNotBlank(param.optString("lineName"))){
			filters.add(Filter.eq("lineName", param.optString("lineName")));
		}
		
		return JSON.toJSONString(productionLineService.findList(null, filters, null));
	}

	@RequestMapping(value = "/taskTime", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String taskTime(String requestParams) {
		Map<String, Object> map = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			map.put("-31", "获取默认参数失败");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "参数解析错误!");
			return JSON.toJSONString(map);
		}
		String startTime = DatesUtils.getYestoday();
		String endTime = DatesUtils.getTomorrow();
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("isDel", false));
		filters.add(Filter.eq("isDel", false));
		filters.add(Filter.eq("lineName", param.getString("lineName")));
		filters.add(Filter.between("creatTime", startTime, endTime));
		List<WorkOrder> list = workOrderService.findList(null, filters, null);
		return JSON.toJSONString(list);
	}

	@RequestMapping(value = "/monitoringBase", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String monitoringBase(String requestParams) {
		Map<String, Object> map = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			map.put("-31", "获取默认参数失败");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "参数解析错误!");
			return JSON.toJSONString(map);
		}
		List<Filter> filters = new ArrayList<>();

		filters.add(Filter.eq("isDel", false));
		filters.add(Filter.eq("isDel", false));
		filters.add(Filter.eq("lineName", param.getString("lineName")));
		List<Equipment> list = equipmentService.findList(null, filters, null);
		return JSON.toJSONString(list.get(0).getEquipmentSensors());
	}

	@RequestMapping(value = "/stationBase", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String stationBase(String requestParams) {
		Map<String, Object> map = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			map.put("-31", "获取默认参数失败");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "参数解析错误!");
			return JSON.toJSONString(map);
		}
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("isDel", false));
		filters.add(Filter.eq("lineName", param.getString("lineName")));
		filters.add(Filter.eq("name", param.getString("name")));
		List<Equipment> list = equipmentService.findList(null, filters, null);
		JSONArray jsonArray = new JSONArray();
		for (Equipment equipment : list) {
			JSONObject json = new JSONObject();
			List<EquipmentSensor> sensors = equipment.getEquipmentSensors();
			json.put("lineCode", equipment.getLineNumber());
			json.put("works", equipment.getName());
			for (EquipmentSensor sensor : sensors) {
				if (StringUtils.equalsIgnoreCase(sensor.getName(), "OK件")) {
					json.put("okAddress", sensor.getSerialNumber());
				} else if (StringUtils.equalsIgnoreCase(sensor.getName(),
						"NOK件")) {
					json.put("nokAddress", sensor.getSerialNumber());
				} else if (StringUtils.equalsIgnoreCase(sensor.getName(),
						"生产总数")) {
					json.put("totalAddress", sensor.getSerialNumber());
				} else if (StringUtils.contains(sensor.getName(), "报警")) {
					json.put("armAddress", sensor.getSerialNumber());
				}
			}
			jsonArray.add(json);
		}
		map.put("code", 0);
		map.put("msg", "工作站获取成功");
		map.put("values", jsonArray);
		return JSON.toJSONString(map);
	}

	/**
	 * 看板基础数据
	 * 
	 */
	@RequestMapping(value = "/kanbanbase", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String kanbanbase(String requestParams) {
		Map<String, Object> map = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			map.put("-31", "获取默认参数失败");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "参数解析错误!");
			return JSON.toJSONString(map);
		}
		Map<String, Object> map2 = new HashMap<>();
		List<String> works = new ArrayList<>();
		List<String> ok = new ArrayList<>();
		List<String> nok = new ArrayList<>();
		List<String> total = new ArrayList<>();
		List<String> alarm = new ArrayList<>();
		map2.put("lineCode", param.getString("lineNumber"));
		map2.put("lineName", param.getString("lineName"));
		List<Filter> filters2 = new ArrayList<>();
		filters2.add(Filter.eq("isDel", false));
		filters2.add(Filter.eq("lineName", param.getString("lineName")));
		filters2.add(Filter.eq("devicePosition", 10));//设备位置10 代表最后个工站设备
		List<Equipment> list2 = equipmentService.findList(null, filters2, null);
		for (Equipment equipment : list2) {
			int flag = 0;
			for (EquipmentSensor sensor : equipment.getEquipmentSensors()) {
				if (StringUtils.equalsIgnoreCase(sensor.getName(), "OK件")) {
					ok.add(sensor.getSerialNumber());
				} else if (StringUtils.equalsIgnoreCase(sensor.getName(),
						"NOK件")) {
					nok.add(sensor.getSerialNumber());
				} else if (StringUtils.equalsIgnoreCase(sensor.getName(),
						"生产总数")) {
					total.add(sensor.getSerialNumber());
				} else {
					alarm.add(sensor.getSerialNumber());
					flag = 1;
				}
			}
			if (flag == 0) {
				works.add(equipment.getName());
			}
		}
		map2.put("works", works);
		map2.put("okAddress", ok);
		map2.put("nokAddress", nok);
		map2.put("totalAddress", total);
//		map2.put("alarm", alarm);
		map.put("code", 0);
		map.put("msg", "看板基础获取成功");
		map.put("values", map2);
		return JSON.toJSONString(map);
	}

	/**
	 * 报警基础数据
	 * 
	 */
	@RequestMapping(value = "/warningbase", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String warningbase(String requestParams) {
		Map<String, Object> map = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			map.put("-31", "获取默认参数失败");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "参数解析错误!");
			return JSON.toJSONString(map);
		}
		
		Map<String, Object> map2 = new HashMap<>();
		List<String> works = new ArrayList<>();
		List<String> alarm = new ArrayList<>();
		List<String> warning = new ArrayList<>();
		List<String> msg = new ArrayList<>();
		map2.put("lineCode", param.getString("lineNumber"));
		map2.put("lineName", param.getString("lineName"));
		List<Filter> filters2 = new ArrayList<>();
		filters2.add(Filter.eq("isDel", false));
		filters2.add(Filter.eq("lineName", param.getString("lineName")));
		List<Equipment> list2 = equipmentService.findList(null, filters2, null);
		String worksname="";
		for (Equipment equipment : list2) {
			List<EquipmentSensor> sensors = equipment.getEquipmentSensors();
			int flag = 0;
			for (EquipmentSensor equipmentSensores : sensors) {
				if (StringUtils.contains(equipmentSensores.getSerialNumber(),
						"DBX5.5")) {
					alarm.add(equipmentSensores.getSerialNumber());
				} else if (StringUtils.contains(
						equipmentSensores.getSerialNumber(), "DBX5.6")) {
					warning.add(equipmentSensores.getSerialNumber());
				} else if (StringUtils.contains(
						equipmentSensores.getSerialNumber(), "DBX5.7")) {
					msg.add(equipmentSensores.getSerialNumber());
				} else {
					flag = 1;
					break;
				}
			}
			if (flag == 1) {
				String str = equipment.getName().substring(0,equipment.getName().length()-3);			
				if (!str.equals(worksname)) {
					works.add(str);
				}
				worksname = str;			
			}
		}
		map2.put("works", works);
		map2.put("alarmAddress", alarm);
		map2.put("warningAddress", warning);
		map2.put("msgAddress", msg);
		map.put("code", 0);
		map.put("msg", "报警基础获取成功");
		map.put("values", map2);
		return JSON.toJSONString(map);
	}

	
	/**
	 * 工站状态基础数据
	 * 
	 */
	@RequestMapping(value = "/deviceStatus", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String deviceStatus(String requestParams) {
		Map<String, Object> map = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			map.put("-31", "获取默认参数失败");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "参数解析错误!");
			return JSON.toJSONString(map);
		}
		
		Map<String, Object> map2 = new HashMap<>();
		List<String> works = new ArrayList<>();
		List<String> alarm = new ArrayList<>();
		List<String> warning = new ArrayList<>();
		List<String> msg = new ArrayList<>();
		map2.put("lineCode", param.getString("lineNumber"));
		map2.put("lineName", param.getString("lineName"));
		List<Filter> filters2 = new ArrayList<>();
		filters2.add(Filter.eq("isDel", false));
		filters2.add(Filter.eq("lineName", param.getString("lineName")));
		List<Equipment> list2 = equipmentService.findList(null, filters2, null);
		String worksname="";
		for (Equipment equipment : list2) {
			List<EquipmentSensor> sensors = equipment.getEquipmentSensors();
			int flag = 0;
			for (EquipmentSensor equipmentSensores : sensors) {
				if (StringUtils.contains(equipmentSensores.getSerialNumber(),
						"DBX5.5")) {
					alarm.add(equipmentSensores.getSerialNumber());
				} else if (StringUtils.contains(
						equipmentSensores.getSerialNumber(), "DBX5.6")) {
					warning.add(equipmentSensores.getSerialNumber());
				} else if (StringUtils.contains(
						equipmentSensores.getSerialNumber(), "DBX5.7")) {
					msg.add(equipmentSensores.getSerialNumber());
				} else {
					flag = 1;
					break;
				}
			}
			if (flag == 1) {
				works.add(equipment.getName());
			}
		}
		map2.put("works", works);
		map2.put("alarmAddress", alarm);
		map2.put("warningAddress", warning);
		map2.put("msgAddress", msg);
		map.put("code", 0);
		map.put("msg", "报警基础获取成功");
		map.put("values", map2);
		return JSON.toJSONString(map);
	}
	
	/**
	 * 判断时间是否是当天的时间
	 * @param inputJudgeDate
	 * @return
	 */
	public boolean isToday(Date inputJudgeDate) {
		boolean flag = false;
		//获取当前系统时间
		long longDate = System.currentTimeMillis();
		Date nowDate = new Date(longDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = dateFormat.format(nowDate);
		String subDate = format.substring(0, 10);
		//定义每天的24h时间范围
		String beginTime = subDate + " 00:00:00";
		String endTime = subDate + " 23:59:59";
		Date paseBeginTime = null;
		Date paseEndTime = null;
		try {
			paseBeginTime = dateFormat.parse(beginTime);
			paseEndTime = dateFormat.parse(endTime);
		} catch (Exception e) {
			
		}
		if(inputJudgeDate.after(paseBeginTime) && inputJudgeDate.before(paseEndTime)) {
			flag = true;
		}
		return flag;
	}
	
	
	/**
	 * 产线历史报表
	 */
	@RequestMapping(value = "/queryWorkFinish", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String queryWorkFinish(String requestParams) {
		Map<String, Object> map2 = new HashMap<>();
		if (StringUtils.isBlank(requestParams)) {
			map2.put("-31", "获取默认参数失败");
			return JSON.toJSONString(map2);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map2.put("-1", "参数解析错误!");
			return JSON.toJSONString(map2);
		}
		if(StringUtils.isBlank(param.optString("lineNumber"))){
			map2.put("-2", "生产线不能为空!");
			return JSON.toJSONString(map2);
		}
		String startTime = null;
		String endTime = null;
		if(StringUtils.isBlank(param.optString("startTime")) && StringUtils.isBlank(param.optString("endTime"))) {
			
			if (param.has("flag")) {
				if (param.get("flag").equals("y")) {
					startTime = DatesUtils.getYearStart2();
					endTime = DatesUtils.getYearEnd2();
				} else if (param.get("flag").equals("m")) {
					startTime = DatesUtils.getMonthStart2();
					endTime = DatesUtils.getMonthEnd2();
				} else if (param.get("flag").equals("q")) {
					startTime = DatesUtils.getCurrQuarter(DatesUtils
							.getQuarter())[0];
					endTime = DatesUtils
							.getCurrQuarter(DatesUtils.getQuarter())[1];
				} else if (param.get("flag").equals("w")) {
					startTime = DatesUtils.getWeekStart2();
					endTime = DatesUtils.getWeekEnd2();
				} else {
					startTime = DatesUtils.getYestoday() ;
					endTime = DatesUtils.getYestoday();
				}
			}
		}else{
			startTime = param.optString("startTime");
			endTime = param.optString("endTime");
		}
		net.sf.json.JSONArray jsonArray = new net.sf.json.JSONArray();
		List<String> days = DatesUtils.getMondayNumber(startTime, endTime, "yyyy-MM-dd");
		Collections.reverse(days);//反转
		for (String day : days) {
			List<Filter> filters = new ArrayList<>();
			filters.add(Filter.eq("isDel", false));
			filters.add(Filter.eq("dateTime", day));
			filters.add(Filter.eq("lineNumber",param.optString("lineNumber")));
			filters.add(Filter.ne("addressType", AddressTypeEnum.POLICE.ordinal()));
			filters.add(Filter.ne("addressType", AddressTypeEnum.NUM.ordinal()));
			filters.add(Filter.ne("addressType", AddressTypeEnum.POLICENUM.ordinal()));
			filters.add(Filter.ne("addressType", AddressTypeEnum.STOP.ordinal()));
			List<WorkFinish> list = workFinishService.findList(null, filters, null);
			JSONObject jo = new JSONObject();
			jo.put("lineNumber", param.optString("lineNumber"));
			jo.put("dateTime", day);
			Long OKValue = 0l;
			Long NOKValue = 0l;
			
			for (WorkFinish workFinish : list) {
				if(workFinish.getAddressType() == AddressTypeEnum.OK){
					OKValue = workFinish.getValue();
					continue;
				}
				if(workFinish.getAddressType() == AddressTypeEnum.NOK){
					NOKValue = workFinish.getValue();
					continue;
				}
			}
			jo.put("OKValue", OKValue);
			jo.put("NOKValue", NOKValue);
			jo.put("totalValue", OKValue+NOKValue);
			jsonArray.add(jo);
		}
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "产线历史报表获取成功");
		jsonReturn.put("values", jsonArray);
		return jsonReturn.toString();
	}
	
	
	/**
	 * 产线报警历史报表
	 */
	@RequestMapping(value = "/queryPoliceWorkFinish", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String queryPoliceWorkFinish(String requestParams) {
		Map<String, Object> map2 = new HashMap<>();
		if (StringUtils.isBlank(requestParams)) {
			map2.put("-31", "获取默认参数失败");
			return JSON.toJSONString(map2);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map2.put("-1", "参数解析错误!");
			return JSON.toJSONString(map2);
		}
		if(StringUtils.isBlank(param.optString("lineNumber"))){
			map2.put("-2", "生产线不能为空!");
			return JSON.toJSONString(map2);
		}
		if(StringUtils.isBlank(param.optString("errType"))){
			map2.put("-2", "类型不能为空!");
			return JSON.toJSONString(map2);
		}
		String startTime = null;
		String endTime = null;
		if(StringUtils.isBlank(param.optString("startTime")) && StringUtils.isBlank(param.optString("endTime"))) {
			
			startTime = DatesUtils.getYestoday() ;
			endTime = DatesUtils.getYestoday();
		}else{
			startTime = param.optString("startTime");
			endTime = param.optString("endTime");
		}
		net.sf.json.JSONArray jsonArray = new net.sf.json.JSONArray();
		List<String> days = DatesUtils.getMondayNumber(startTime, endTime, "yyyy-MM-dd");
		Collections.reverse(days);//反转
		for (String day : days) {
			List<Filter> filters = new ArrayList<>();
			filters.add(Filter.eq("isDel", false));
			filters.add(Filter.eq("dateTime", day));
			filters.add(Filter.eq("lineNumber",param.optString("lineNumber")));
			if(param.optString("errType").equals("故障")){
				filters.add(Filter.ne("addressType", AddressTypeEnum.NOK.ordinal()));
				filters.add(Filter.ne("addressType", AddressTypeEnum.NUM.ordinal()));
				filters.add(Filter.ne("addressType", AddressTypeEnum.OK.ordinal()));
				filters.add(Filter.ne("addressType", AddressTypeEnum.STOP.ordinal()));
			}if(param.optString("errType").equals("停机")){
				filters.add(Filter.eq("addressType", AddressTypeEnum.STOP.ordinal()));
			}
			List<WorkFinish> list = workFinishService.findList(null, filters, null);
			net.sf.json.JSONArray array = new net.sf.json.JSONArray();
			for (WorkFinish workFinish : list) {
				String equipmentName = "";
				Equipment equipment = equipmentService.querySensorLineByAddrssANDLine(workFinish.getAddress(), param.optString("lineNumber"));
				if(equipment != null){
					JSONObject jo = new JSONObject();
					jo.put("lineNumber", param.optString("lineNumber"));
					jo.put("dateTime", day);
					Long count = 0l;
					Long timeLong = null;
					//根据地址位，产线查询设备工站
					equipmentName = equipment.getName();
					if(workFinish.getAddressType() == AddressTypeEnum.POLICENUM){
						count = workFinish.getValue();
					}else{
						count = workFinish.getCount();
						if(workFinish.getTimeLong() != null){
							timeLong = workFinish.getTimeLong();
						}
					}
					jo.put("count", count);
					jo.put("timeLong", timeLong);
					jo.put("equipmentName", equipmentName);
					jo.put("address", workFinish.getAddress());
					array.add(jo);
				}
			}
			jsonArray.add(array);
		}
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "产线报警历史报表获取成功");
		jsonReturn.put("values", jsonArray);
		return jsonReturn.toString();
	}
}
