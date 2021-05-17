package com.taojin.iot.api.traffic.controller;

import java.math.BigDecimal;
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
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.base.comm.utils.UUIDTools;
import com.taojin.iot.service.equipment.entity.Equipment;
import com.taojin.iot.service.traffic.entity.TrafficCard;
import com.taojin.iot.service.traffic.entity.TrafficCardOrder;
import com.taojin.iot.service.traffic.entity.TrafficCardOrder.FreightType;
import com.taojin.iot.service.traffic.entity.TrafficCardOrder.OrderStatus;
import com.taojin.iot.service.traffic.entity.TrafficCardOrder.PaymentStatus;
import com.taojin.iot.service.traffic.service.TrafficCardOrderService;
import com.taojin.iot.service.traffic.service.TrafficCardService;

/**
 * Controller-流量卡
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午8:03:38
 * author 王杰
 * ============================================================================
 */
@Controller("internalTrafficCardController")
@RequestMapping("/internal/traffic/trafficCard")
public class TrafficCardController extends BaseController{
	@Resource(name="trafficCardServiceImpl")
	private TrafficCardService trafficCardService;
	@Resource(name="trafficCardOrderServiceImpl")
	private TrafficCardOrderService trafficCardOrderService;
	
	/**
	 * 获取流量 卡
	 * 
	 * @param requestParams
	 *            requestParams={"action":"trafficCard","method":"list","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":{"cardnumber":"号码","pageNumber":"页码","pageSize":"每页记录数"}}
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
		if(param.has("cardnumber")){
			filters.add(Filter.like("cardnumber", "%"+param.getString("cardnumber")+"%"));
		}
		//分页检索
		Pageable pageable = new Pageable(param.getInt("pageNumber"),param.getInt("pageSize"));
		filters.add(Filter.eq("ownerId", Long.parseLong(userSession.getUserId())));
		pageable.setFilters(filters);
		
		Page<TrafficCard> page = trafficCardService.findPage(pageable);
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取流量卡成功!");
		JSONArray jsonArray = new JSONArray();
		for(int i=0;i<page.getContent().size();i++){
			JSONObject json = new JSONObject();
			TrafficCard trafficCard = page.getContent().get(i);
			
			json.put("id", trafficCard.getId());
			json.put("cardnumber", trafficCard.getCardnumber());
			json.put("iccid", trafficCard.getIccid());
			json.put("totalTraffic", trafficCard.getTotalTraffic());
			json.put("surplusTraffic", trafficCard.getSurplusTraffic());
			json.put("warningLine", trafficCard.getWarningLine());
			json.put("startTime", trafficCard.getStartTime());
			json.put("endTime", trafficCard.getEndTime());
			json.put("cardState", trafficCard.getCardState());
			if(trafficCard.getEquipment() != null){
				json.put("equipmentId", trafficCard.getEquipment().getId());
				json.put("equipmentName", trafficCard.getEquipment().getName());
			}
			
			jsonArray.add(json);
		}
		jsonReturn.put("values", jsonArray);
		jsonReturn = super.getJsonPage(page, jsonReturn);
		return jsonReturn.toString();
	}
	
	/**
	 * 更新
	 * 
	 * @param requestParams
	 *            requestParams={"action":"trafficCard","method":"update","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":
	 *            {"trafficCardId":"键值","warningLine":"警戒线","equipmentId":"设备ID","isUnbind":"是否解绑"}}
	 * @return
	 */
	@RequestMapping(value = "/update", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String update(String requestParams) {
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
		if (super.getSession(session) == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		}else if(super.getEquipmentTypeId(session) == null){
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}
		
		if (!param.has("trafficCardId")) {
			return errorMsg("-2", "请选择一个操作项!");
		}
		TrafficCard trafficCard = trafficCardService.find(param.getLong("trafficCardId"));
		if (param.has("warningLine")) {
			trafficCard.setWarningLine(new BigDecimal(param.getString("warningLine")));
		}
		if(param.has("isUnbind")){
			if (param.has("equipmentId")&&param.getInt("isUnbind") == 0) {//绑定
				Equipment equipment = new Equipment();
				equipment.setId(param.getLong("equipmentId"));
				trafficCard.setEquipment(equipment);
			}else if(param.getInt("isUnbind") == 1){
				trafficCard.setEquipment(null);
			}
		}
		
		trafficCardService.update(trafficCard);
		return successMsg("0", "操作成功!");
	}
	
	/**
	 * 购买流量卡
	 * 
	 * @param requestParams
	 *            requestParams={"action":"trafficCard","method":"purchasing","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":{"companyName":"企业名称",
	 *            "contactName":"联系人姓名","phone":"电话号码","areaName":"收货地区","address":"收货街道地址",
	 *            "freightType":"运费类型","yearsNumber":"购买年限","buycardnumber":"购买数量"}}
	 * @return
	 */
	@RequestMapping(value = "/purchasing", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String purchasing(String requestParams) {
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
		if(!param.has("companyName")){
			return successMsg("-3", "请填写公司名称!");
		}
		
		if(!param.has("contactName")){
			return successMsg("-3", "请填写联系人!");
		}
		
		if(!param.has("phone")){
			return successMsg("-3", "请填写电话!");
		}
		
		if(!param.has("areaName")){
			return successMsg("-3", "缺少收货地址!");
		}
		
		if(!param.has("address")){
			return successMsg("-3", "缺少街道地址!");
		}
		
		if(!param.has("freightType")){
			return successMsg("-3", "选择运费类型!");
		}
		
		if(!param.has("yearsNumber")){
			return successMsg("-3", "请选择购买年限!");
		}
		
		if(!param.has("buycardnumber")){
			return successMsg("-3", "请填写购买数量!");
		}
		
		TrafficCardOrder cardOrder = new TrafficCardOrder();
		cardOrder.setSn(String.valueOf(System.currentTimeMillis()));
		cardOrder.setOrderStatus(OrderStatus.delivery);
		cardOrder.setCompanyName(param.getString("companyName"));
		cardOrder.setContactName(param.getString("contactName"));
		cardOrder.setConsignee(param.getString("contactName"));
		cardOrder.setAreaName(param.getString("areaName"));
		cardOrder.setAddress(param.getString("address"));
		cardOrder.setPhone(param.getString("phone"));
		cardOrder.setYearsNumber(Integer.parseInt(param.getString("yearsNumber")));
		cardOrder.setBuycardnumber(Integer.parseInt(param.getString("buycardnumber")));
		cardOrder.setFreightType(FreightType.valueOf(param.getString("freightType")));
		cardOrder.setOwnerId(Long.parseLong(userSession.getUserId()));
		cardOrder.setEquipmentTypeId(session.getLong("equipmentTypeId"));
		cardOrder.setZipCode("214011");
		
		BigDecimal fee = new BigDecimal("48");
		BigDecimal yunfei = new BigDecimal("0");
		if(cardOrder.getFreightType().name() == "CashonLine"){
			yunfei = new BigDecimal("10");
		}
		fee.add(yunfei);
		BigDecimal cardnumber = new BigDecimal(param.getString("buycardnumber")); 
		cardOrder.setFee(fee.multiply(cardnumber));
		cardOrder.setFreight(yunfei);
		cardOrder.setPaymentStatus(PaymentStatus.paid);
		
		trafficCardOrderService.save(cardOrder);
		
		//生成流量 卡
		TrafficCard card = new TrafficCard();
		card.setCardnumber(UUIDTools.getUUID());
		card.setIccid(String.valueOf(System.currentTimeMillis()));
		card.setTotalTraffic(new BigDecimal("30"));
		card.setUsedTraffic(new BigDecimal(0));
		card.setSurplusTraffic(new BigDecimal("30"));
		card.setWarningLine(new BigDecimal("5"));
		card.setStartTime(DatesUtils.getStringToday("yyyy-MM-dd"));
		card.setEndTime("2020-10-11");
		card.setCardState(1);
		card.setOwnerId(Long.parseLong(userSession.getUserId()));
		card.setEquipmentTypeId(session.getLong("equipmentTypeId"));
		
		trafficCardService.save(card);
		return successMsg("0", "操作成功!");
	}

}
