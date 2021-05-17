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
import com.taojin.iot.service.user.entity.UserContact;
import com.taojin.iot.service.user.service.UserContactService;

/**
 * Controller-用户联系人 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午9:13:45 author 王杰
 * ============================================================================
 */
@Controller("internalUserContactController")
@RequestMapping("/internal/user/userContact")
public class UserContactController extends BaseController {
	@Resource(name = "userContactServiceImpl")
	private UserContactService userContactService;

	/**
	 * 联第人列表
	 * 
	 * @param requestParams
	 *            requestParams={"action":"userContact","method":"list",
	 *            "session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param"
	 *            :{"name":"姓名"
	 *            ,"phone":"手机号","email":"邮件","pageNumber":"页码","pageSize"
	 *            :"每页记录数"}}
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
		// 会话验证
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		} /*else if (super.getEquipmentTypeId(session) == null) {
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}*/

		// 参数验证
		if (!param.has("pageNumber")) {
			param.put("pageNumber", 1);
		}

		if (!param.has("pageSize")) {
			param.put("pageSize", 20);
		}
		List<Filter> filters = new ArrayList<Filter>();
		if (param.has("name")) {
			filters.add(Filter.like("name", "%" + param.getString("name") + "%"));
		}
		if (param.has("phone")) {
			filters.add(Filter.like("phone", "%" + param.getString("phone")
					+ "%"));
		}
		if (param.has("email")) {
			filters.add(Filter.like("email", "%" + param.getString("email")
					+ "%"));
		}

		// 分页检索
		Pageable pageable = new Pageable(param.getInt("pageNumber"),
				param.getInt("pageSize"));
		filters.add(Filter.eq("ownerId",
				Long.parseLong(userSession.getUserId())));
		pageable.setFilters(filters);

		Page<UserContact> page = userContactService.findPage(pageable);
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取用户联系人成功");
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < page.getContent().size(); i++) {
			JSONObject json = new JSONObject();
			UserContact userContact = page.getContent().get(i);
			
			json.put("contactId", userContact.getId());
			json.put("name", userContact.getName());
			json.put("phone", userContact.getPhone());
			json.put("email", userContact.getEmail());
			json.put("ownerId", userContact.getOwnerId());

			jsonArray.add(json);
		}
		jsonReturn.put("values", jsonArray);
		jsonReturn = super.getJsonPage(page, jsonReturn);
		return jsonReturn.toString();
	}

	/**
	 * 添加联系人
	 * 
	 * @param requestParams
	 *            requestParams={"action":"userContact","method":"save",
	 *            "session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param"
	 *            :{"name":"姓名","phone":"手机号","email":"邮件"}}
	 * @return
	 */
	@RequestMapping(value = "/save", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String save(String requestParams) {
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
		// 会话验证
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		} else if (super.getEquipmentTypeId(session) == null) {
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}

		if (!param.has("name")) {
			return errorMsg("-2", "缺少姓名!");
		}
		if (!param.has("phone")) {
			return errorMsg("-2", "缺少手机号码!");
		}
		if (!param.has("email")) {
			return errorMsg("-2", "缺少邮箱!");
		}

		UserContact userContact = new UserContact();
		userContact.setName(param.getString("name"));
		userContact.setEmail(param.getString("email"));
		userContact.setPhone(param.getString("phone"));
		userContact.setOwnerId(Long.parseLong(userSession.getUserId()));
		userContactService.save(userContact);
		return errorMsg("0", "添加用户联系人成功!");
	}

	/**
	 * 更新联系人
	 * 
	 * @param requestParams
	 *            requestParams={"action":"userContact","method":"update",
	 *            "session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param"
	 *            :{"userContactId"
	 *            :"主键","name":"姓名","phone":"手机号","email":"邮件"}}
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
		// 会话验证
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		} else if (super.getEquipmentTypeId(session) == null) {
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}

		if (!param.has("userContactId")) {
			return errorMsg("-2", "参数有误!");
		}

		UserContact userContact = userContactService.find(param
				.getLong("userContactId"));
		if(userContact == null){
			return errorMsg("-2", "对象不存在");
		}
		if (param.has("name")) {
			userContact.setName(param.getString("name"));
		}
		if (param.has("phone")) {
			userContact.setPhone(param.getString("phone"));
		}
		if (param.has("email")) {
			userContact.setEmail(param.getString("email"));
		}

		userContactService.update(userContact);
		return errorMsg("0", "修改用户联系人成功!");
	}

	/**
	 * 删除联系人
	 * 
	 * @param requestParams
	 *            requestParams={"action":"userContact","method":"update",
	 *            "session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param"
	 *            :{"userContactId"
	 *            :"主键","name":"姓名","phone":"手机号","email":"邮件"}}
	 * @return
	 */
	@RequestMapping(value = "/delete", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String delete(String requestParams) {
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
		// 会话验证
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		} else if (super.getEquipmentTypeId(session) == null) {
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}

		if (!param.has("userContactId")) {
			return errorMsg("-2", "参数有误!");
		}

		userContactService.delete(param.getLong("userContactId"));
		return errorMsg("0", "删除用户联系人成功!");
	}

	/**
	 * 根据ID获取联系人
	 * 
	 * @param requestParams
	 *            requestParams={"action":"userContact","method":
	 *            "getContactById","session":
	 *            {"sessionId":"会话ID","equipmentTypeId"
	 *            :"频道ID"},"param":{"contactId":"键值"}}
	 * @return
	 */
	@RequestMapping(value = "/getContactById", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String getContactById(String requestParams) {
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
		// 会话验证
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		} else if (super.getEquipmentTypeId(session) == null) {
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}

		// 参数验证
		if (!param.has("contactId")) {
			return errorMsg("-2", "参数有误!");
		}

		UserContact userContact = userContactService.find(param
				.getLong("contactId"));
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取用户联系人成功");
		JSONObject json = new JSONObject();

		json.put("name", userContact.getName());
		json.put("phone", userContact.getPhone());
		json.put("email", userContact.getEmail());
		json.put("ownerId", userContact.getOwnerId());
		json.put("weixin", userContact.getWeixin());

		jsonReturn.put("value", json);
		return jsonReturn.toString();
	}

}
