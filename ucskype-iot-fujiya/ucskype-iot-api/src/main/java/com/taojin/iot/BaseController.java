package com.taojin.iot;

import com.taojin.iot.base.comm.Page;
import com.taojin.iot.base.comm.entity.UserSession;
import com.taojin.iot.base.comm.service.UserSessionService;
import com.taojin.iot.base.comm.utils.SpringUtils;
import com.taojin.iot.transmit.utils.CRC16M;

import net.sf.json.JSONObject;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.Set;

/**
 * Controller - 基类
 * 
 * 王杰
 * 
 */
public class BaseController {

	/** 错误视图 */
	protected static final String ERROR_VIEW = "/common/error";

	/**
	 * 成功视图
	 */
	protected static final String SUCCESS_VIEW = "redirect:/common/success.jhtml";

	/** "验证结果"参数名称 */
	private static final String CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME = "constraintViolations";

	public static final String SUCCESSMSG = "成功";

	public static final String KEY = "";

	@Resource(name = "validator")
	private Validator validator;
	public String resultStr;
	@Resource(name="userSessionServiceImpl")
	private UserSessionService userSessionService;
	
	/**
	 * 获取用户会话
	 * @param session 会话对象
	 * @return UserSession
	 */
	public UserSession getSession(JSONObject session){
		if(!session.has("sessionId")){
			return null;
		}
		System.out.println(session.getString("sessionId"));
		UserSession userSession = userSessionService.getByParam("sessionId", session.getString("sessionId"));
		return userSession;
	}
	
	/**
	 * 获取频道
	 * @param session
	 * @return
	 */
	public Long getEquipmentTypeId(JSONObject session){
		if(!session.has("equipmentTypeId")){
			return null;
		}
		return session.getLong("equipmentTypeId");
	}

	/**
	 * 获取分页信息
	 * @param page 分页对象
	 * @param jsonReturn 返回对象
	 * @return
	 */
	public JSONObject getJsonPage(Page<?> page,JSONObject jsonReturn){
		JSONObject jsonPage = new JSONObject();
		jsonPage.put("totalPage", page.getTotalPages());
		jsonPage.put("total", page.getTotal());
		jsonPage.put("pageNumber", page.getPageNumber());
		jsonPage.put("pageSize", page.getPageSize());
		jsonReturn.put("page", jsonPage);
		return jsonReturn;
	}

	/**
	 * 获取分页信息
	 * @param page 分页对象
	 * @param jsonReturn 返回对象
	 * @return
	 */
	public JSONObject getPage(Page<?> page){
		JSONObject jsonPage = new JSONObject();
		jsonPage.put("totalPage", page.getTotalPages());
		jsonPage.put("total", page.getTotal());
		jsonPage.put("pageNumber", page.getPageNumber());
		jsonPage.put("pageSize", page.getPageSize());
		return jsonPage;
	}
	
	/**
	 * 返回错误信息
	 */
	public String errorMsg(String errCode, String message) {
		String[] methodArray = new String[2];
		String[] strArray = new String[2];

		methodArray[0] = "errcode";
		strArray[0] = "\"" + errCode + "\"";
		methodArray[1] = "errmsg";
		strArray[1] = "\"" + message + "\"";
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", errCode);
		jsonReturn.put("errmsg", message);
		resultStr = jsonReturn.toString();
		return resultStr;
	}

	/**
	 * 返回正确信息
	 */
	public String successMsg(String errcode, String message) {
		String[] methodArray = new String[2];
		String[] strArray = new String[2];

		methodArray[0] = "errcode";
		strArray[0] = "\"" + errcode + "\"";
		methodArray[1] = "errmsg";
		strArray[1] = "\"" + message + "\"";

		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", errcode);
		jsonReturn.put("errmsg", message);
		resultStr = jsonReturn.toString();
		return resultStr;
	}

	/**
	 * 数据验证
	 * 
	 * @param target
	 *            验证对象
	 * @param groups
	 *            验证组
	 * @return 验证结果
	 */
	protected boolean isValid(Object target, Class<?>... groups) {
		Set<ConstraintViolation<Object>> constraintViolations = validator
				.validate(target, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		} else {
			RequestAttributes requestAttributes = RequestContextHolder
					.currentRequestAttributes();
			requestAttributes.setAttribute(
					CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations,
					RequestAttributes.SCOPE_REQUEST);
			return false;
		}
	}

	/**
	 * 数据验证
	 * 
	 * @param type
	 *            类型
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @param groups
	 *            验证组
	 * @return 验证结果
	 */
	protected boolean isValid(Class<?> type, String property, Object value,
			Class<?>... groups) {
		Set<?> constraintViolations = validator.validateValue(type, property,
				value, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		} else {
			RequestAttributes requestAttributes = RequestContextHolder
					.currentRequestAttributes();
			requestAttributes.setAttribute(
					CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations,
					RequestAttributes.SCOPE_REQUEST);
			return false;
		}
	}

	/**
	 * 获取国际化消息
	 * 
	 * @param code
	 *            代码
	 * @param args
	 *            参数
	 * @return 国际化消息
	 */
	protected String message(String code, Object... args) {
		return SpringUtils.getMessage(code, args);
	}
}