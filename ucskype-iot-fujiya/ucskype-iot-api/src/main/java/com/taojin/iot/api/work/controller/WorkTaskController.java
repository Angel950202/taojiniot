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
	 * ??????????????????
	 * @throws ParseException 
	 */

	@RequestMapping(value = "/workOrderAdd", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workOrderAdd(String requestParams) throws ParseException {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "????????????????????????");
		}
		JSONObject node;
		JSONObject param;
		JSONObject session;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			session = node.getJSONObject("session");
		} catch (Exception e) {
			return successMsg("-1", "??????????????????!");
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
			jsonReturn.put("msg", "????????????????????????");
		}else{
			jsonReturn.put("code", "-2");
			jsonReturn.put("msg", "????????????????????????");
		}
		return jsonReturn.toString();
		//???????????????????????????
//		if (workOrder.getCount() > 0) {
//			/*
//			 * ??????????????????????????????????????????
//			 * ?????????????????????????????????
//			 * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
//			 * 
//			 */
//			if (isToday(start)) {
//				List<Filter> filters = new ArrayList<>();
//				filters.add(Filter.eq("status", "?????????"));
//				filters.add(Filter.eq("isDel", false));
//				filters.add(Filter.eq("lineNumber", workOrder.getLineNumber()));
//				List<WorkOrder> list = workOrderService.findList(null, filters, null);
//				if (list.size() > 0) {
//					return successMsg("-6", "???????????????????????????????????????");
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
//			        if (hour >= 7 && hour < 15) {//??????
//			        	startTime = sdFormat.format(new Date()) +" 07:00:00";
//			        	endTime = sdFormat.format(new Date()) +" 15:00:00";
//					}
//			        else if (hour >= 15 && hour < 23) {//??????
//			        	startTime = sdFormat.format(new Date()) +" 15:00:00";
//			        	endTime = sdFormat.format(new Date()) +" 23:00:00";
//					}
//			        else{//??????
//			        	startTime = sdFormat.format(new Date()) +" 23:00:00";
//			        	endTime = sdFormat.format(new Date()) +" 07:00:00";
//					}
//			        workOrder.setStartTime(startTime);
//			        workOrder.setEndTime(endTime);
//				}
//				workOrder.setStatus("?????????");
//				workOrderService.save(workOrder);
//			}
//			
//			/*
//			 * ?????????????????????????????????????????????
//			 * ???????????????????????????????????????????????????????????????????????????????????????????????????
//			 * ????????????????????????????????????8??????
//			 */
//			if (!isToday(start)) {
//				if (timeCalculation(workOrder.getStartTime(),workOrder.getEndTime()) > 8) {
//					return successMsg("-6", "????????????????????????????????????8??????");
//				}
//				Date startDate = sdf.parse(workOrder.getStartTime());
//				Date endDate = sdf.parse(workOrder.getStartTime());
//				SimpleDateFormat df = new SimpleDateFormat("HH");
//		        String str = df.format(startDate);
//		        Integer hour = Integer.parseInt(str);
//		        String startTime = "";
//				String endTime = "";
//		        if (hour >= 7 && hour < 15) {//??????
//		        	startTime = sdFormat.format(startDate) +" 07:00:00";
//		        	endTime = sdFormat.format(endDate) +" 15:00:00";
//				}else if (hour >= 15 && hour < 23) {//??????
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
//				workOrder.setStatus("?????????");
//				workOrder.setStartTime(startTime);
//				workOrder.setEndTime(endTime);
//				workOrderService.save(workOrder);
//			}
//			JSONObject jsonReturn = new JSONObject();
//			jsonReturn.put("code", "0");
//			jsonReturn.put("msg", "????????????????????????");
//			return jsonReturn.toString();
//		}
	}
	
	/**
     * ???????????????
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
    		//???
    		double day = diff/nd;
    		//??????
    		double hour = diff/nh;
    		//??????
    		double minute = diff/nm;
    		time = hour;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return time;
    }

	/**
	 * ????????????????????????
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
	        if (hour >= 7 && hour < 15) {//??????
	        	
			}else if (hour >= 15 && hour < 23) {//??????
				
			}else {
				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ???????????????
	 */
	@RequestMapping(value = "/workReportAdd", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workReportAdd(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "????????????????????????");
		}
		JSONObject node;
		JSONObject param;
		JSONObject session;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			session = node.getJSONObject("session");
		} catch (Exception e) {
			return successMsg("-1", "??????????????????!");
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
		jsonReturn.put("msg", "?????????????????????");
		return jsonReturn.toString();
	}

	/**
	 * ??????????????????
	 */
	@RequestMapping(value = "/workOrderUpdate", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workOrderUpdate(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "????????????????????????");
		}
		JSONObject node;
		JSONObject param;
		JSONObject session;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			session = node.getJSONObject("session");
		} catch (Exception e) {
			return successMsg("-1", "??????????????????!");
		}
		WorkOrder workOrder = com.alibaba.fastjson.JSONObject.parseObject(
				param.toString(), WorkOrder.class);
		if ("?????????".equals(workOrder.getStatus())) {
			WorkOrder workOrder1 = workOrderService.find(workOrder.getId());
			if ("?????????".equals(workOrder1.getStatus())) {
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
				workReportDetail.setMsg("???????????????");
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
				 * ?????????????????????
				 * ???????????????0
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
				 * ??????
				 */
				// workReport.setEquipmentName(workOrder1);
				workReport.setOrderNumber(workOrder1.getOrderNumber());
				workReport.setProductionModel(workOrder1.getProductionModel());
				workReport.setProductionLine(workOrder1.getLineName());
				workReport.setPlanCount(workOrder1.getCount());
				/**
				 * ??????????????????
				 */
				workReport.setActualCount(x);
				/**
				 * ok??????
				 */
				workReport.setOkCount(x - y);
				/**
				 * nok??????
				 */
				workReport.setNokCount(y);

				/**
				 * ?????????
				 */
				workReport.setNokPercent(0 + "");
				/**
				 * ?????????
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
		jsonReturn.put("msg", "????????????????????????");
		return jsonReturn.toString();
	}

	/**
	 * ???????????????
	 */
	@RequestMapping(value = "/workReportUpdate", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workReportUpdate(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "????????????????????????");
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-1", "??????????????????!");
		}
		WorkReport workReport = com.alibaba.fastjson.JSONObject.parseObject(
				param.toString(), WorkReport.class);
		workReportService.update(workReport);
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("code", "0");
		jsonReturn.put("msg", "?????????????????????");
		return jsonReturn.toString();
	}

	@RequestMapping(value = "/workReportDetailList", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workReportDetailList(String requestParams) {
		Map<String, Object> map = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "????????????????????????");
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");

		} catch (Exception e) {
			return successMsg("-1", "??????????????????!");
		}
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("workReport", param.getLong("id")));
		List<WorkReportDetail> list = workReportDetailService.findList(null,
				filters, null);
		map.put("code", 0);
		map.put("msg", "????????????????????????????????????");
		map.put("values", list);
		return JSON.toJSONString(map);
	}

	/**
	 * ??????????????????
	 * 
	 */
	@RequestMapping(value = "/workOrderList", produces = "application/josn;charset=utf-8")
	@ResponseBody
	public String workOrderList(String requestParams) {
		Map<String, Object> map = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			map.put("-31", "????????????????????????");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "??????????????????!");
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
		map.put("msg", "??????????????????????????????");
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
			map.put("-31", "????????????????????????");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "??????????????????!");
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
		map.put("msg", "???????????????????????????");
		map.put("values", list);
		map.put("page", super.getPage(page));
		return JSON.toJSONString(map);
	}

	/**
	 * ?????????????????????
	 * 
	 */
	@RequestMapping(value = "/workOrderNumber", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workOrderNumber() {
		Map<String, Object> map = new HashMap<>(16);
		List<Map<String, Object>> values = new ArrayList<>();
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.ne("status", "?????????"));
		filters.add(Filter.eq("isDel", 0));
		List<WorkOrder> list = workOrderService.findList(null, filters, null);
		for (WorkOrder workOrder : list) {
			map.put("id", workOrder.getId());
			map.put("orderNumber", workOrder.getOrderNumber());
			values.add(map);
		}
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("code", "0");
		jsonReturn.put("msg", "??????????????????????????????");
		jsonReturn.put("values", values);
		return jsonReturn.toString();
	}

	/**
	 * ????????????????????????
	 * @param requestParams
	 * @return
	 */
	@RequestMapping(value = "/workOrderNumberAll", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workOrderNumberAll(String requestParams) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "????????????????????????");
		}
		JSONObject node;
		JSONObject param=null;
		try {
			node = JSONObject.fromObject(requestParams);
			if(!"".equals(node.getString("param")))
			param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-1", "??????????????????!");
		}
//		filters.add(Filter.eq("status", "?????????"));
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
		map.put("msg", "???????????????????????????");
		map.put("values", list);
		return JSON.toJSONString(map);
	}

	/**
	 * ????????????
	 * 
	 */
	@RequestMapping(value = "/trace", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String trace(String requestParams) {
		HashMap<String, Object> map2 = new HashMap<>();
		List<HashMap<String, Object>> list = new ArrayList<>();
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "????????????????????????");
		}
		// JSONObject node;
		// JSONObject param;
		try {
			// node = JSONObject.fromObject(requestParams);
			// param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-1", "??????????????????!");
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
		map2.put("msg", "????????????????????????");
		map2.put("values", list);
		return JSON.toJSONString(map2);
	}

	/**
	 * ??????
	 * 
	 */
	@RequestMapping(value = "/traceDetail", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String traceDetail(String requestParams) {

		HashMap<String, Object> map2 = new HashMap<>();
		List<String> list = new ArrayList<>();
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "????????????????????????");
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-1", "??????????????????!");
		}
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("isDel", 0));
		// filters.add(Filter.eq("id", param.getLong("id")));
		/*
		 * ????????????
		 * ???????????????????????????????????????????????????
		 * ????????????????????????????????????
		 */
		WorkOrder order = workOrderService.find(param.getLong("id"));
		list.add(DatesUtils.dateToString(order.getCreateDate(),
				"yyyy-MM-dd HH:mm:ss")
				+ " "
				+ order.getCreatorName()
				+ " ?????????????????????");
		filters.add(Filter.eq("orderNumber", order.getOrderNumber()));
		List<WorkReportDetail> msglist = workReportDetailService.findList(null,filters, null);
		for (WorkReportDetail workReportDetail : msglist) {
			if (!"???????????????".equals(workReportDetail.getMsg())) {
				list.add(workReportDetail.getCreatTime().replace(".0", "") + " " +"??????  "+workReportDetail.getWorks() + workReportDetail.getMsg());
			}
			if ("???????????????".equals(workReportDetail.getMsg())) {
				list.add(workReportDetail.getCreatTime().replace(".0", "") + " " + workReportDetail.getMsg());
			}
		}
		/*
		 * ?????????????????????OK?????????NOK??? 
		 */
		if("?????????".equals(order.getStatus())){
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
		map2.put("msg", "????????????????????????");
		return JSON.toJSONString(map2);
	}

	/**
	 * ???????????????
	 * 
	 */
	@RequestMapping(value = "/workReport", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workReport(String requestParams) {
		Map<String, Object> map2 = new HashMap<>();
		if (StringUtils.isBlank(requestParams)) {
			map2.put("-31", "????????????????????????");
			return JSON.toJSONString(map2);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map2.put("-1", "??????????????????!");
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
		//????????????
		if (param.has("startTime") || param.has("endTime")) {
			filters.add(Filter.between_two("reportTime", param.getString("startTime"),param.getString("endTime")));
		}
		Pageable pageable = new Pageable(param.getInt("pageNumber"),param.getInt("pageSize"));
		pageable.setFilters(filters);
		Page<WorkReport> page = workReportService.findPage(pageable);
//		List<WorkReport> list = workReportService.findList(null, filters, null);
		
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "???????????????????????????");
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
	 * ???????????????
	 * 
	 */
	@RequestMapping(value = "/workReportBad", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String nokReport(String requestParams) {
		Map<String, Object> map2 = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			map2.put("-31", "????????????????????????");
			return JSON.toJSONString(map2);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map2.put("-1", "??????????????????!");
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
		//??????????????????
		if (param.has("productionModel") && !param.getString("productionModel").equals("") && !"".equals(param.getString("productionModel"))) {
			filters.add(Filter.eq("model",param.getString("productionModel")));
		}
		//????????????
		if (param.has("productionLine") && !"null".equals(param.getString("productionLine")) && !"".equals(param.getString("productionLine"))) {
			filters.add(Filter.eq("lineName",param.getString("productionLine")));
		}
		//????????????
		if (param.has("equipmentName") && !"null".equals(param.getString("equipmentName")) && !"".equals(param.getString("equipmentName"))) {
			filters.add(Filter.eq("works",param.getString("equipmentName")));
		}
		//????????????
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
		jsonReturn.put("errmsg", "???????????????????????????");
		jsonReturn.put("values", jsonArray);
		jsonReturn = super.getJsonPage(page, jsonReturn);
		return jsonReturn.toString();
	}

	@RequestMapping(value = "/lineList", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String lineList(String requestParams) {
		Map<String, Object> map2 = new HashMap<>();
		if (StringUtils.isBlank(requestParams)) {
			map2.put("-31", "????????????????????????");
			return JSON.toJSONString(map2);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map2.put("-1", "??????????????????!");
			return JSON.toJSONString(map2);
		}
		if(!param.has("lineName")){
			map2.put("-2", "????????????????????????!");
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
			map.put("-31", "????????????????????????");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "??????????????????!");
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
			map.put("-31", "????????????????????????");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "??????????????????!");
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
			map.put("-31", "????????????????????????");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "??????????????????!");
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
				if (StringUtils.equalsIgnoreCase(sensor.getName(), "OK???")) {
					json.put("okAddress", sensor.getSerialNumber());
				} else if (StringUtils.equalsIgnoreCase(sensor.getName(),
						"NOK???")) {
					json.put("nokAddress", sensor.getSerialNumber());
				} else if (StringUtils.equalsIgnoreCase(sensor.getName(),
						"????????????")) {
					json.put("totalAddress", sensor.getSerialNumber());
				} else if (StringUtils.contains(sensor.getName(), "??????")) {
					json.put("armAddress", sensor.getSerialNumber());
				}
			}
			jsonArray.add(json);
		}
		map.put("code", 0);
		map.put("msg", "?????????????????????");
		map.put("values", jsonArray);
		return JSON.toJSONString(map);
	}

	/**
	 * ??????????????????
	 * 
	 */
	@RequestMapping(value = "/kanbanbase", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String kanbanbase(String requestParams) {
		Map<String, Object> map = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			map.put("-31", "????????????????????????");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "??????????????????!");
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
		filters2.add(Filter.eq("devicePosition", 10));//????????????10 ???????????????????????????
		List<Equipment> list2 = equipmentService.findList(null, filters2, null);
		for (Equipment equipment : list2) {
			int flag = 0;
			for (EquipmentSensor sensor : equipment.getEquipmentSensors()) {
				if (StringUtils.equalsIgnoreCase(sensor.getName(), "OK???")) {
					ok.add(sensor.getSerialNumber());
				} else if (StringUtils.equalsIgnoreCase(sensor.getName(),
						"NOK???")) {
					nok.add(sensor.getSerialNumber());
				} else if (StringUtils.equalsIgnoreCase(sensor.getName(),
						"????????????")) {
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
		map.put("msg", "????????????????????????");
		map.put("values", map2);
		return JSON.toJSONString(map);
	}

	/**
	 * ??????????????????
	 * 
	 */
	@RequestMapping(value = "/warningbase", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String warningbase(String requestParams) {
		Map<String, Object> map = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			map.put("-31", "????????????????????????");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "??????????????????!");
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
		map.put("msg", "????????????????????????");
		map.put("values", map2);
		return JSON.toJSONString(map);
	}

	
	/**
	 * ????????????????????????
	 * 
	 */
	@RequestMapping(value = "/deviceStatus", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String deviceStatus(String requestParams) {
		Map<String, Object> map = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			map.put("-31", "????????????????????????");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "??????????????????!");
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
		map.put("msg", "????????????????????????");
		map.put("values", map2);
		return JSON.toJSONString(map);
	}
	
	/**
	 * ????????????????????????????????????
	 * @param inputJudgeDate
	 * @return
	 */
	public boolean isToday(Date inputJudgeDate) {
		boolean flag = false;
		//????????????????????????
		long longDate = System.currentTimeMillis();
		Date nowDate = new Date(longDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = dateFormat.format(nowDate);
		String subDate = format.substring(0, 10);
		//???????????????24h????????????
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
	 * ??????????????????
	 */
	@RequestMapping(value = "/queryWorkFinish", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String queryWorkFinish(String requestParams) {
		Map<String, Object> map2 = new HashMap<>();
		if (StringUtils.isBlank(requestParams)) {
			map2.put("-31", "????????????????????????");
			return JSON.toJSONString(map2);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map2.put("-1", "??????????????????!");
			return JSON.toJSONString(map2);
		}
		if(StringUtils.isBlank(param.optString("lineNumber"))){
			map2.put("-2", "?????????????????????!");
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
		Collections.reverse(days);//??????
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
		jsonReturn.put("errmsg", "??????????????????????????????");
		jsonReturn.put("values", jsonArray);
		return jsonReturn.toString();
	}
	
	
	/**
	 * ????????????????????????
	 */
	@RequestMapping(value = "/queryPoliceWorkFinish", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String queryPoliceWorkFinish(String requestParams) {
		Map<String, Object> map2 = new HashMap<>();
		if (StringUtils.isBlank(requestParams)) {
			map2.put("-31", "????????????????????????");
			return JSON.toJSONString(map2);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map2.put("-1", "??????????????????!");
			return JSON.toJSONString(map2);
		}
		if(StringUtils.isBlank(param.optString("lineNumber"))){
			map2.put("-2", "?????????????????????!");
			return JSON.toJSONString(map2);
		}
		if(StringUtils.isBlank(param.optString("errType"))){
			map2.put("-2", "??????????????????!");
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
		Collections.reverse(days);//??????
		for (String day : days) {
			List<Filter> filters = new ArrayList<>();
			filters.add(Filter.eq("isDel", false));
			filters.add(Filter.eq("dateTime", day));
			filters.add(Filter.eq("lineNumber",param.optString("lineNumber")));
			if(param.optString("errType").equals("??????")){
				filters.add(Filter.ne("addressType", AddressTypeEnum.NOK.ordinal()));
				filters.add(Filter.ne("addressType", AddressTypeEnum.NUM.ordinal()));
				filters.add(Filter.ne("addressType", AddressTypeEnum.OK.ordinal()));
				filters.add(Filter.ne("addressType", AddressTypeEnum.STOP.ordinal()));
			}if(param.optString("errType").equals("??????")){
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
					//??????????????????????????????????????????
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
		jsonReturn.put("errmsg", "????????????????????????????????????");
		jsonReturn.put("values", jsonArray);
		return jsonReturn.toString();
	}
}
