package com.taojin.iot.base.comm.utils;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 类型:
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午1:07:47
 * author cff
 * ============================================================================
 */
public class MessageSendUtils {
	private static final String API_AUTH_KEY = "0CC175B9C0F1B6A831C399E269772661";
	public static Boolean sendMessage(String receiver, String title, String action){
		String url = "http://112.74.75.202:8020/taojinim-chat/cgi/message/push.api";
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("receiver", receiver);//用户名
		parameterMap.put("content", title);
		parameterMap.put("format", "0");
		parameterMap.put("action", action);
		parameterMap.put("API_AUTH_KEY", API_AUTH_KEY);
		String resultStr = PostGetUtil.post(url, parameterMap);
		System.out.println(resultStr);
		JSONObject jsonReturn = JSONObject.fromObject(resultStr);
		if(jsonReturn != null){
			if("200".equals(jsonReturn.getString("code"))){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	} 
}
