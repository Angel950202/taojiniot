package com.taojin.iot.api.traffic.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taojin.iot.BaseController;
import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.Page;
import com.taojin.iot.base.comm.Pageable;
import com.taojin.iot.base.comm.entity.UserSession;
import com.taojin.iot.service.traffic.entity.TrafficCardOrder;
import com.taojin.iot.service.traffic.entity.TrafficCardOrder.OrderStatus;
import com.taojin.iot.service.traffic.service.TrafficCardOrderService;

/**
 * Controller-流量卡订单
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午8:21:33
 * author 王杰
 * ============================================================================
 */
@Controller("internalTrafficCardOrderController")
@RequestMapping("/internal/traffic/trafficCardOrder")
public class TrafficCardOrderController extends BaseController{
	@Resource(name="trafficCardOrderServiceImpl")
	private TrafficCardOrderService trafficCardOrderService;
	
	/**
	 * 获取流量卡订单列表
	 * 
	 * @param requestParams
	 *            requestParams={"action":"trafficCardOrder","method":"list","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":{"orderStatus":"订单状态","paymentStatus":"支付状态","phone":"手机号","sn":"订单号","pageNumber":"页码","pageSize":"每页记录数"}}
	 * @return
	 */
	@RequestMapping(value = "/list", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String list(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject session = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			session = node.getJSONObject("session");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
		//会话验证
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		}else if(super.getEquipmentTypeId(session) == null){
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}
		
		//参数验证
		if(!param.has("pageNumber")){
			param.put("pageNumber", 1);
		}
				
		if(!param.has("pageSize")){
			param.put("pageSize", 20);
		}
		
		List<Filter> filters = new ArrayList<Filter>();
		if(param.has("orderStatus")){
			filters.add(Filter.eq("orderStatus", OrderStatus.valueOf(param.getString("orderStatus"))));
		}
		if(param.has("paymentStatus")){
			filters.add(Filter.eq("paymentStatus", OrderStatus.valueOf(param.getString("paymentStatus"))));
		}
		if(param.has("sn")){
			filters.add(Filter.like("sn", "%"+param.getString("sn")+"%"));
		}
		if(param.has("phone")){
			filters.add(Filter.like("phone", "%"+param.getString("phone")+"%"));
		}
		
		//分页检索
		Pageable pageable = new Pageable(param.getInt("pageNumber"),param.getInt("pageSize"));
		filters.add(Filter.eq("ownerId", Long.parseLong(userSession.getUserId())));
		pageable.setFilters(filters);
		
		Page<TrafficCardOrder> page = trafficCardOrderService.findPage(pageable);
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取流量卡订单成功!");
		JSONArray jsonArray = new JSONArray();
		
		for(int i=0;i<page.getContent().size();i++){
			JSONObject json = new JSONObject();
			TrafficCardOrder cardOrder = page.getContent().get(i);
			
			json.put("id", cardOrder.getId());
			json.put("sn", cardOrder.getSn());
			json.put("orderStatus", cardOrder.getOrderStatus().name());
			json.put("paymentStatus", cardOrder.getPaymentStatus().name());
			json.put("companyName", cardOrder.getCompanyName());
			json.put("contactName", cardOrder.getContactName());
			json.put("consignee", cardOrder.getConsignee());
			json.put("areaName", cardOrder.getAreaName());
			json.put("address", cardOrder.getAddress());
			json.put("zipCode", cardOrder.getZipCode());
			json.put("phone", cardOrder.getPhone());
			json.put("fee", cardOrder.getFee());
			json.put("yearsNumber", cardOrder.getYearsNumber());
			json.put("buycardnumber", cardOrder.getBuycardnumber());
			json.put("freightType", cardOrder.getBuycardnumber());
			json.put("freight", cardOrder.getFreight());
			
			jsonArray.add(json);
		}
		jsonReturn.put("values", jsonArray);
		jsonReturn = super.getJsonPage(page, jsonReturn);
		return jsonReturn.toString();
	}

}
