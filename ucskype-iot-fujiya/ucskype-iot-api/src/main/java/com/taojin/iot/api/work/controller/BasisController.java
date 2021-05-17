package com.taojin.iot.api.work.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.taojin.iot.BaseController;
import com.taojin.iot.agreement.fujiya.entity.AgreementRc701Value;
import com.taojin.iot.agreement.fujiya.service.AgreementRc701ValueService;
import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.Order;
import com.taojin.iot.base.comm.Page;
import com.taojin.iot.base.comm.Pageable;
import com.taojin.iot.base.comm.entity.UserSession;
import com.taojin.iot.service.equipment.entity.Equipment;
import com.taojin.iot.service.equipment.entity.EquipmentSensor;
import com.taojin.iot.service.equipment.service.EquipmentSensorService;
import com.taojin.iot.service.equipment.service.EquipmentService;
import com.taojin.iot.service.kanban.LineEnum;
import com.taojin.iot.service.task.entity.ProductionLine;
import com.taojin.iot.service.task.service.ProductionLineService;

@Controller("internalWorkBasisController")
@RequestMapping({ "/internal/task/basis" })
public class BasisController extends BaseController {

	@Resource(name = "productionLineServiceImpl")
	private ProductionLineService productionLineService;
	
	 @Resource(name = "equipmentServiceImpl")
    private EquipmentService equipmentService;

    @Resource(name = "agreementRc701ValueServiceImpl")
    private AgreementRc701ValueService agreementRc701ValueService;
    
	@Resource(name = "equipmentSensorServiceImpl")
	private EquipmentSensorService equipmentSensorService;

	@RequestMapping(value = { "/productionLineAdd" }, produces = { "application/josn; charset=utf-8" })
	@ResponseBody
	public String productionLineAdd(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject session;
		JSONObject param;
		JSONObject node;
		try {
			node = net.sf.json.JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			session = node.getJSONObject("session");
		} catch (Exception e) {

			return successMsg("-1", "参数解析错误!");
		}

		ProductionLine productionLine = (ProductionLine) com.alibaba.fastjson.JSONObject
				.parseObject(param.toString(), ProductionLine.class);
		UserSession userSession = super.getSession(session);
		productionLine.setCreaterName(userSession.getUsername());
		this.productionLineService.save(productionLine);
		net.sf.json.JSONObject jsonReturn = new net.sf.json.JSONObject();
		jsonReturn.put("code", "0");
		jsonReturn.put("msg", "添加生产线成功");
		return jsonReturn.toString();
	}

	@RequestMapping(value = { "/productionLineUpdate" }, produces = { "application/josn; charset=utf-8" })
	@ResponseBody
	public String productionLineUpdate(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		net.sf.json.JSONObject param;
		net.sf.json.JSONObject node;
		try {
			node = net.sf.json.JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}

		ProductionLine productionLine = (ProductionLine) com.alibaba.fastjson.JSONObject
				.parseObject(param.toString(), ProductionLine.class);
		this.productionLineService.update(productionLine);
		net.sf.json.JSONObject jsonReturn = new net.sf.json.JSONObject();
		jsonReturn.put("code", "0");
		jsonReturn.put("msg", "更新生产线成功");
		return jsonReturn.toString();
	}

	@RequestMapping(value = { "/productionLineList" }, produces = { "application/josn; charset=utf-8" })
	@ResponseBody
	public String productionLineList(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject param;
		JSONObject node;
		try {
			node = net.sf.json.JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}

		List<Filter> filters = new ArrayList<Filter>();
		filters.add(Filter.eq("isDel", Integer.valueOf(0)));

		if ((param.has("startTime")) && (param.has("endTime"))
				&& (!param.getString("startTime").equals(""))
				&& (!param.getString("endTime").equals(""))) {
			filters.add(Filter.between("creatTime", param.get("startTime"),
					param.get("endTime")));
		}
		if (!param.has("pageNumber")) {
			param.put("pageNumber", Integer.valueOf(0));
		}

		if (!param.has("pageSize")) {
			param.put("pageSize", Integer.valueOf(0));
		}
		Pageable pageable = new Pageable(Integer.valueOf(param
				.getInt("pageNumber")), Integer.valueOf(param
				.getInt("pageSize")));
		if ((param.has("lineName"))
				&& (!param.getString("lineName").equals(""))) {
			filters.add(Filter.like("lineName", "%" + param.get("lineName")
					+ "%"));
		}
		pageable.setFilters(filters);
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.asc("id"));
		pageable.setOrders(orders);
		Page<ProductionLine> page = this.productionLineService
				.findPage(pageable);
		List<ProductionLine> list = page.getContent();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Integer.valueOf(0));
		map.put("msg", "生产线列表获取成功");
		map.put("values", list);
		map.put("page", super.getPage(page));
		return JSON.toJSONString(map);
	}
	
	/**
	 * 获取产线状态
	 * @return
	 */
	@RequestMapping(value = { "/lineStatus" }, produces = { "application/josn; charset=utf-8" })
	@ResponseBody
	public String lineStatus() {
		List list = new ArrayList<>();
		List<Filter> LineFilters = new ArrayList<Filter>();
		LineFilters.add(Filter.eq("isDel", false));
		List<ProductionLine> productionLines = productionLineService.findList(null, LineFilters, null);
		for (ProductionLine productionLine : productionLines) {
			LineEnum agreementFujiyaEnum = LineEnum.valueOf(productionLine.getLineNumber());
	        List<Filter> filters1 = new ArrayList<>();
	        filters1.add(Filter.eq("lineNumber", productionLine.getLineNumber()));
	        List<Equipment> equipmentList = equipmentService.findList(null,filters1,null);
	        Map<String, Object> map = new HashMap<>();
	        map.put("lineName", productionLine.getLineName());
	        map.put("lineNumber", productionLine.getLineNumber());
	        for (Equipment equipment : equipmentList){
	        	/*
	        	 * 名称长度大于4的是工作站
	        	 * C121-S1、C122-S1
	        	 */
	        	if (equipment.getName().length() > 4) {
	        		String workstationAddressString  = "";
	        		List<Filter> filters = new ArrayList<>();
	        		filters.add(Filter.eq("equipment", equipment.getId()));
					List<EquipmentSensor> sensors = equipmentSensorService.findList(null,filters,null);
					for (EquipmentSensor equipmentSensor : sensors) {
						if(equipmentSensor.getSerialNumber().startsWith("DB")){
							String[] workStations = equipmentSensor.getSerialNumber().split("\\.");
							workstationAddressString = workStations[0];//获取工站地址分组位
							break;
						}
					}
					if(!workstationAddressString.equals("")){
						String statusAddress = "DTU."+workstationAddressString +".8";
						if (workStation(statusAddress,agreementFujiyaEnum.ordinal()) == 0) {
							map.put("status", false);
							break;
						}
					}
					map.put("status", true);
	        	}
	        }
	        list.add(map);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Integer.valueOf(0));
		map.put("msg", "产线状态获取成功");
		map.put("values", list);
		return JSON.toJSONString(map);
	}
	
	/**
	 * 获取设备状态
	 * @param address
	 * @param line
	 * @return
	 */
	 private Integer workStation(String address,Integer line){
    	Integer num = 0;
    	Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	
    	List<Filter> filters = new ArrayList<>();
	    filters.add(Filter.eq("address", address));
	    filters.add(Filter.eq("equipType", line));
	    filters.add(Filter.eq("dateTime", sdf.format(date)));
	    
	    List<AgreementRc701Value> aValues = agreementRc701ValueService.findList(null,filters,null);
	    if (!aValues.isEmpty()) {
        	Integer value = Integer.valueOf(aValues.get(0).getCommandValue());
        	num += value;
        }
    	return num;
    }
}