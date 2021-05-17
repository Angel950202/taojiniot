package com.taojin.iot.api.user.controller;

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
import com.taojin.iot.service.user.entity.UserOrder;
import com.taojin.iot.service.user.entity.UserOrder.PayStatus;
import com.taojin.iot.service.user.entity.UserOrder.PayType;
import com.taojin.iot.service.user.service.UserOrderService;

/**
 * Controller-用户订单
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 上午10:07:35
 * author 王杰
 * ============================================================================
 */
@Controller("internalUserOrderController")
@RequestMapping("/internal/user/userOrder")
public class UserOrderController extends BaseController{
	@Resource(name = "userOrderServiceImpl")
	private UserOrderService userOrderService;
	
	/**
	 * 获取用户订单
	 * 
	 * @param requestParams
	 *            requestParams={"action":"userOrder","method":"getUserOrder","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":{"sn":"订单号","payStatus":"支付状态","payType":"支付类型","pageNumber":"页码","pageSize":"每页记录数"}}
	 * @return
	 */
	@RequestMapping(value = "/getUserOrder", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String getUserOrder(String requestParams) {
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
		if(param.has("payType")){
			filters.add(Filter.eq("payType", PayType.valueOf(param.getString("payType"))));
		}
		if(param.has("payStatus")){
			filters.add(Filter.eq("payStatus", PayStatus.valueOf(param.getString("payStatus"))));
		}
		if(param.has("sn")){
			filters.add(Filter.like("sn", "%"+param.getString("sn")+"%"));
		}
		
		//分页检索
		Pageable pageable = new Pageable(param.getInt("pageNumber"),param.getInt("pageSize"));
		filters.add(Filter.eq("ownerId", Long.parseLong(userSession.getUserId())));
		filters.add(Filter.eq("equipmentTypeId", session.getLong("equipmentTypeId")));
		pageable.setFilters(filters);
		
		Page<UserOrder> page = userOrderService.findPage(pageable);
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取用户订单成功!");
		JSONArray jsonArray = new JSONArray();
		
		for(int i=0;i<page.getContent().size();i++){
			JSONObject json = new JSONObject();
			UserOrder userOrder = page.getContent().get(i);
			
			json.put("id", userOrder.getId());
			json.put("sn", userOrder.getSn());
			json.put("payType", userOrder.getPayType().name());
			json.put("payNum", userOrder.getPayNum());
			json.put("money", userOrder.getMoney());
			json.put("payTime", userOrder.getPayTime());
			json.put("payStatus", userOrder.getPayStatus().name());
			json.put("createDate", DatesUtils.dateToString(userOrder.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
			json.put("ownerId", userOrder.getOwnerId());
			json.put("equipmentTypeId", userOrder.getEquipmentTypeId());
			
			jsonArray.add(json);
		}
		jsonReturn.put("values", jsonArray);
		jsonReturn = super.getJsonPage(page, jsonReturn);
		return jsonReturn.toString();
	}

}
