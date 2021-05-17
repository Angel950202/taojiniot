package com.taojin.iot.api.user.controller;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taojin.iot.BaseController;

/**
 * 
 * iot物联网平台-充值
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午3:54:03 author 王杰
 * ============================================================================
 */
@Controller("internalUserPayController")
@RequestMapping("/internal/user/userPay")
public class UserPayController extends BaseController {

	/**
	 * 获取用户信息
	 * 
	 * @param requestParams
	 *            requestParams={"action":"userPay","method":"paycard",param:{
	 *            "userSec":"用户key","cardnum":"卡号","type":"unico
	 *            ,loco,teleco","money":"金额"}}
	 * @return
	 */
	@RequestMapping(value = "/paycard", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String paycard(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}

		//参数验证
		if(!param.has("userSec")){
			return successMsg("-1", "用户key!");
		}
		if(!param.has("cardnum")){
			return successMsg("-1", "缺少卡号!");
		}
		if(!param.has("type")){
			return successMsg("-1", "缺少卡类型!");
		}
		if(!param.has("money")){
			return successMsg("-1", "缺少金额!");
		}
		
		if(param.getDouble("money") < 100  ){
			return successMsg("-1", "金额最少100元!");
		}
		
		if(!StringUtils.equalsIgnoreCase(param.getString("userSec"), "98ecec39fe7e457d9809a777ea3b0695")){
			return successMsg("-1", "充值失败,未授权!");
		}
		
		return successMsg("0", "充值成功!");
	}

}
