package com.taojin.iot.base.comm.interceptor;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taojin.iot.base.comm.entity.UserSession;
import com.taojin.iot.base.comm.service.UserSessionService;
import com.taojin.iot.base.comm.utils.DatesUtils;

/**
 * 类型:会话 拦截器
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午6:23:33 author wangjie
 * ============================================================================
 */
public class SessionInterceptor implements HandlerInterceptor {
	@Resource(name = "userSessionServiceImpl")
	private UserSessionService userSessionService;

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 获取sessionId
		//{"action":"user","method":"list","session":{"sessionId":"sdfsfsfsdfsd"}}
		String requestParams = request.getParameter("requestParams");
		String sessionId = "";
		try{
			JSONObject json = JSONObject.fromObject(requestParams);
			JSONObject session = json.getJSONObject("session");
			sessionId = session.optString("sessionId");
		}catch(Exception e){
			JSONObject returnJson = new JSONObject();
			returnJson.put("errcode", "301");
			returnJson.put("errmsg", "登录信息已失效，请重新登录!");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(returnJson.toString());
			return false;
		}
		
		if(StringUtils.isBlank(sessionId)){
			JSONObject returnJson = new JSONObject();
			returnJson.put("errcode", "301");
			returnJson.put("errmsg", "登录信息已失效，请重新登录!");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(returnJson.toString());
			return false;
		}

		UserSession useSession = userSessionService.getByParam("sessionId", sessionId);
		if (useSession == null) {
			JSONObject returnJson = new JSONObject();
			returnJson.put("errcode", "301");
			returnJson.put("errmsg", "登录信息已失效，请重新登录!");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(returnJson.toString());
			return false;
		} else {
			Date now = DatesUtils.stringToDate(
					DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"),
					"yyyy-MM-dd HH:mm:ss");
			Date bindTime = DatesUtils.stringToDate(useSession.getBindTime(),
					"yyyy-MM-dd HH:mm:ss");

			if (DatesUtils.compareMin(now, bindTime) > 30) {// 超过30分钟则超时
				JSONObject returnJson = new JSONObject();
				returnJson.put("errcode", "301");
				returnJson.put("errmsg", "登录信息已失效，请重新登录!");
				response.setHeader("Content-type", "text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(returnJson.toString());
				return false;
			}
		}
		return true;
	}
}
