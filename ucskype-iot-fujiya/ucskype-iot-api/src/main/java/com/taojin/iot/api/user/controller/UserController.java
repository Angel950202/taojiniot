package com.taojin.iot.api.user.controller;

import java.math.BigDecimal;
import java.security.interfaces.RSAPublicKey;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.taojin.iot.BaseController;
import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.Order;
import com.taojin.iot.base.comm.Page;
import com.taojin.iot.base.comm.Pageable;
import com.taojin.iot.base.comm.entity.UserSession;
import com.taojin.iot.base.comm.service.RSAService;
import com.taojin.iot.base.comm.service.UserSessionService;
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.base.comm.utils.UUIDTools;
import com.taojin.iot.service.user.entity.CompanyProfile;
import com.taojin.iot.service.user.entity.SeniorManagement;
import com.taojin.iot.service.user.entity.User;
import com.taojin.iot.service.user.entity.UserOrder;
import com.taojin.iot.service.user.entity.UserOrder.PayStatus;
import com.taojin.iot.service.user.entity.UserOrder.PayType;
import com.taojin.iot.service.user.entity.UserRole;
import com.taojin.iot.service.user.service.CompanyProfileService;
import com.taojin.iot.service.user.service.SeniorManagementService;
import com.taojin.iot.service.user.service.UserOrderService;
import com.taojin.iot.service.user.service.UserRoleService;
import com.taojin.iot.service.user.service.UserService;

/**
 * Controller-用户 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午8:34:24 author 王杰
 * ============================================================================
 */
@Controller("internalUserController")
@RequestMapping("/internal/user/user")
public class UserController extends BaseController {
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "userSessionServiceImpl")
	private UserSessionService userSessionService;
	@Resource(name = "userOrderServiceImpl")
	private UserOrderService userOrderService;
	@Resource(name = "seniorManagementServiceImpl")
	private SeniorManagementService seniorManagementService;
	@Resource(name = "companyProfileServiceImpl")
	private CompanyProfileService companyProfileService;
	@Resource(name = "userRoleServiceImpl")
	private UserRoleService userRoleService;
	@Autowired
	private RSAService rsaService;
	/**
	 * 获取用户信息
	 * 
	 * @param requestParams
	 *            requestParams={"action":"user","method":"userInfo","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"}}
	 * @return
	 */
	@RequestMapping(value = "/userInfo", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String userInfo(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node = new JSONObject();
		JSONObject session = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
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

		User user = userService.find(Long.parseLong(userSession.getUserId()));
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("username", user.getUsername());
		json.put("apiKey", user.getApiKey());
		json.put("idNumber", user.getIdNumber());
		json.put("fee", user.getFee());
		json.put("name", user.getName());
		json.put("headPic", user.getHeadPic());
		json.put("sex", user.getSex());
		json.put("birthday", user.getBirthday());
		json.put("phone", user.getPhone());
		json.put("email", user.getEmail());
		json.put("address", user.getAddress());
		json.put("companyName", user.getCompanyName());
		json.put("userType", user.getUserType());
		json.put("smsCount", user.getSmsCount());
		json.put("department", user.getDepartment());
		json.put("position", user.getPosition());
		json.put("roleName", user.getRoleName());
		json.put("roleId",user.getRoleId());
		json.put("roleList",
				userRoleService.find(user.getRoleId()).getRoleList());
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "获取信息成功!");
		jsonReturn.put("value", json);
		return jsonReturn.toString();
	}

	/**
	 * 修改密码
	 * 
	 * @param requestParams
	 *            requestParams={"action":"user","method":"modifyPassword",
	 *            "session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":
	 *            {"oldPassword"
	 *            :"原始密码","newPassword":"新密码","reNewPassword":"确认新密码"}}
	 * @return
	 */
	@RequestMapping(value = "/modifyPassword", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String modifyPassword(String requestParams) {
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

		if (!param.has("oldPassword")) {
			return errorMsg("-2", "请填写原始密码!");
		}
		if (!param.has("newPassword")) {
			return errorMsg("-2", "请填写新密码!");
		}
		if (!param.has("reNewPassword")) {
			return errorMsg("-2", "请确认新密码!");
		}
		if (!StringUtils.equalsIgnoreCase(param.getString("newPassword"),
				param.getString("reNewPassword"))) {
			return errorMsg("-2", "新密码两次输入不一致!");
		}

		if (param.getString("newPassword").length() < 6
				|| param.getString("newPassword").length() > 15) {
			return errorMsg("-2", "密码长度只能是6-15位数字、字母、下划线!");
		}

		User user = userService.find(Long.parseLong(userSession.getUserId()));
		String newPassword = DigestUtils.md5Hex(param.getString("newPassword"));
		String oldPassword = DigestUtils.md5Hex(param.getString("oldPassword"));

		if (!StringUtils.equalsIgnoreCase(oldPassword, user.getPassword())) {
			return errorMsg("-2", "原始密码错误!");
		}

		user.setPassword(newPassword);
		userService.update(user);
		return successMsg("0", "密码修改成功!");
	}

	/**
	 * 登录
	 * 
	 * @param requestParams
	 *            requestParams={"action":"user","method":"login","session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":
	 *            {"username"
	 *            :"用户名","password":"密码","device":"登录终端","systemVersion":"版本号"}}
	 * @return
	 */
	@RequestMapping(value = "/login", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String login(String requestParams) {
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

		if (!param.has("username")) {
			return successMsg("-1", "请输入用户名!");
		}

		if (!param.has("password")) {
			return successMsg("-1", "请输入密码!");
		}

		if (!param.has("device")) {
			return successMsg("-1", "非法登录!");
		}

		if (!param.has("systemVersion")) {
			return successMsg("-1", "非法登录!");
		}

		User user = userService.getByParam("username",
				param.getString("username"));
		if (user == null) {			
			return successMsg("-1", "账号不正确 !");
		}
		String password = DigestUtils.md5Hex(param.getString("password"));
		if (!StringUtils.equalsIgnoreCase(user.getPassword(), password)) {
			return successMsg("-1", "密码不正确 !");
		}

		UserSession userSession = userSessionService.getByParam("username",
				user.getUsername());
		if (userSession == null) {
			userSession = new UserSession();
		}

		userSession.setUsername(user.getUsername());
		userSession.setUserId(String.valueOf(user.getId()));
		userSession.setDevice(param.getString("device"));
		userSession.setSystemVersion(param.getString("systemVersion"));
		userSessionService.update(userSession);

		JSONObject json = new JSONObject();
		json.put("userId", userSession.getUserId());
		json.put("username", userSession.getUsername());
		json.put("sessionId", userSession.getSessionId());
		json.put("bindTime", userSession.getBindTime());
		json.put("device", userSession.getDevice());
		json.put("systemVersion", userSession.getSystemVersion());
		json.put("headPic", user.getHeadPic());
		json.put("sex", user.getSex());
		json.put("birthday", user.getBirthday());
		json.put("phone", user.getPhone());
		json.put("email", user.getEmail());
		json.put("address", user.getAddress());
		json.put("companyName", user.getCompanyName());
		json.put("userType", user.getUserType());
		json.put("department", user.getDepartment());
		json.put("roleName", user.getRoleName());
		json.put("roleList",
		userRoleService.find(user.getRoleId()).getRoleList());
		json.put("roleId",user.getRoleId());
		json.put("position", user.getPosition());
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "登录成功!");
		jsonReturn.put("value", json);
		return jsonReturn.toString();
	}

	@RequestMapping(value="/userList", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String userList(String requestParams){
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		node = JSONObject.fromObject(requestParams);
		param = node.getJSONObject("param");
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(Filter.ne("isDel",true));
		if(param.has("name")&&!param.getString("name").equals("")){
		filters.add(Filter.like("name","%"+param.getString("name")+"%"));
		}
		List<User> list = userService.findList(null,filters,null);
		Map<String,Object> jsonReturn = new HashMap<>();
		jsonReturn.put( "code", "0");
		jsonReturn.put("msg", "用户列表获取成功");
		jsonReturn.put("values",list);
		return JSON.toJSONString(jsonReturn);
	}
	
	@RequestMapping(value="/userUpdate", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String userUpdate(String requestParams){
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject jsonReturn = new JSONObject();
		node = JSONObject.fromObject(requestParams);
		param = node.getJSONObject("param");
		User user = userService.getByParam("username",
				param.getString("username"));
		user =  com.alibaba.fastjson.JSONObject.parseObject(param.toString(), User.class);	
		userService.update(user);	
		jsonReturn.put( "code", "0");
		jsonReturn.put("msg", "用户编辑成功");
		return jsonReturn.toString();
	}
	
	
	/**
	 * 注册
	 * 
	 * @param requestParams
	 *            requestParams={"action":"user","method":"regist","param":
	 *            {"username"
	 *            :"用户名","password":"密码","rePassword":"确认密码","type":"注册方式"}}
	 * @return
	 */
	@RequestMapping(value = "/regist", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String regist(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-3", "参数解析错误!");
		}
/*
		if (!param.has("type")) {
			return successMsg("-3", "参数有误!");
		}
*/
		if (!param.has("username")) {
			return successMsg("-3", "请输入用户名!");
		}

		if (!param.has("password")) {
			return successMsg("-3", "请输入密码!");
		}

		/*if (!param.has("rePassword")) {
			return successMsg("-3", "请确认密码!");
		}

		if (!StringUtils.equalsIgnoreCase(param.getString("password"),
				param.getString("rePassword"))) {
			return successMsg("-1", "两次密码输入不一致!");
		}*/

		if (StringUtils.length(param.getString("password")) < 6
				|| StringUtils.length(param.getString("password")) > 16) {
			return errorMsg("-3", "密码必须是6-16位的字符、数字、字母");
		}

		User user = userService.getByParam("username",
				param.getString("username"));
		if (user != null) {
			return successMsg("-1", "用户已存在 !");
		}
		String password = DigestUtils.md5Hex(param.getString("password"));
		user =  com.alibaba.fastjson.JSONObject
				.parseObject(param.toString(), User.class);
		user.setUsername(param.getString("username"));
		user.setPassword(password);
		user.setApiKey(UUIDTools.getUUID());
		user.setIdNumber(String.valueOf(System.currentTimeMillis()));
		user.setFee(new BigDecimal("0"));
		/*if (StringUtils.equalsIgnoreCase(param.getString("type"), "email")) {
			user.setEmail(param.getString("username"));
		} else if (StringUtils.equalsIgnoreCase(param.getString("type"),
				"phone")) {
			user.setPhone(param.getString("username"));
		}*/
		user.setAge(DatesUtils.getAgeByBirth(user.getBirthday()));
		user.setUserType(0);
		user.setEquipmentTypeId(1L);
		user.setSmsCount(0);	
		UserRole role = userRoleService.find(user.getRoleId()+0L);
		user.setRoleName(role.getRoleName());
		userService.save(user);
		Map<String,Object> jsonReturn = new HashMap<>();
		jsonReturn.put("code", "0");
		jsonReturn.put("msg", "员工添加成功");
		return JSON.toJSONString(jsonReturn);

	}

	/**
	 * 充值
	 * 
	 * @param requestParams
	 *            requestParams={"action":"user","method":"pay", "session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":
	 *            {"money":"金额","type":"充值类型","num":"数量"}}
	 * @return
	 */
	@RequestMapping(value = "/pay", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String pay(String requestParams) {
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
			return errorMsg("302", "会话超时,请重新登录!");
		} else if (super.getEquipmentTypeId(session) == null) {
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}

		//参数验证
		if(!param.has("type")){
			return errorMsg("-2", "参数有误!");
		}
		
		if(!param.has("money")){
			return errorMsg("-2", "请填写充值金额!");
		}
		
		if(!param.has("num")){
			param.put("num", 1);
		}
		
		BigDecimal money = new BigDecimal(param.getString("money"));
		if(money.compareTo(new BigDecimal("100")) == -1){
			return errorMsg("-3", "充值金额不能小于100元!");
		}
		User user = userService.find(Long.parseLong(userSession.getUserId()));
		if(user == null){
			return errorMsg("302", "会话超时,请重新登录!");
		}
		if(StringUtils.equalsIgnoreCase(param.getString("type"), "user")){
			BigDecimal nowMoney = user.getFee();
			user.setFee(nowMoney.add(money));
		}else if(StringUtils.equalsIgnoreCase(param.getString("type"), "sms")){
			if(!param.has("num")){
				return errorMsg("-2", "充值失败,参数有误!");
			}
			if(!param.has("amount")){
				return errorMsg("-2", "充值失败,请填写购买条数!");
			}
			user.setSmsCount(user.getSmsCount()+param.getInt("amount"));
		}
		userService.update(user);
		//记录用户 订单
		UserOrder userOrder = new UserOrder();
		userOrder.setSn(UUIDTools.getUUID());
		userOrder.setPayType(PayType.valueOf(param.getString("type")));
		userOrder.setPayNum(param.getInt("num"));
		userOrder.setMoney(money);
		userOrder.setPayTime(DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"));
		userOrder.setPayStatus(PayStatus.paid);
		userOrder.setOwnerId(Long.parseLong(userSession.getUserId()));
		userOrder.setEquipmentTypeId(session.getLong("equipmentTypeId"));
		userOrderService.save(userOrder);
		return successMsg("0", "充值成功!");
	}
	
	/**
	 * 平台授权登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loginAuthor", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loginAuthor(String authorkey){
		try{
//			String authorkey = rsaService.decryptParameter("authorkey", request);//获得授权key
//			rsaService.removePrivateKey(request);//移除私钥
			//业务逻辑处理 authorkey=用户名_密码
			
			if(StringUtils.isBlank(authorkey)){
				return super.errorMsg("-1", "iot授权失败");
			}
			
			String[] authors = authorkey.split("\\_");
			String username = authors[0];
			String passwords = authors[1];
			User user = userService.getByParam("username",username);
			if (user == null) {
				user = new User();
				String password = DigestUtils.md5Hex(passwords);
				user = new User();
				user.setUsername(username);
				user.setPassword(password);
				user.setApiKey(UUIDTools.getUUID());
				user.setIdNumber(String.valueOf(System.currentTimeMillis()));
				user.setFee(new BigDecimal("0"));
				//user.setSex(0);
				user.setPhone(username);
				user.setUserType(0);
				user.setEquipmentTypeId(1L);
				user.setSmsCount(0);
				userService.save(user);
			}
			String password = DigestUtils.md5Hex(passwords);
			if (!StringUtils.equalsIgnoreCase(user.getPassword(), password)) {
				return successMsg("-1", "密码不正确 !");
			}

			UserSession userSession = userSessionService.getByParam("username",
					user.getUsername());
			if (userSession == null) {
				userSession = new UserSession();
			}

			userSession.setUsername(user.getUsername());
			userSession.setUserId(String.valueOf(user.getId()));
			userSession.setDevice("userauthor");
			userSession.setSystemVersion("1.0.0");

			userSessionService.update(userSession);

			JSONObject json = new JSONObject();
			json.put("userId", userSession.getUserId());
			json.put("username", userSession.getUsername());
			json.put("sessionId", userSession.getSessionId());
			json.put("bindTime", userSession.getBindTime());
			json.put("device", userSession.getDevice());
			json.put("systemVersion", userSession.getSystemVersion());
			json.put("headPic", user.getHeadPic());
			json.put("sex", user.getSex());
			json.put("birthday", user.getBirthday());
			json.put("phone", user.getPhone());
			json.put("email", user.getEmail());
			json.put("address", user.getAddress());
			json.put("companyName", user.getCompanyName());
			json.put("userType", user.getUserType());

			JSONObject jsonReturn = new JSONObject();
			jsonReturn.put("errcode", "0");
			jsonReturn.put("errmsg", "授权登录成功!!");
			jsonReturn.put("authorurl", "http://iot.ucskype.com/#/access/navList");
			jsonReturn.put("authorplat", "IOT");
			jsonReturn.put("value", json);
			return jsonReturn.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return super.errorMsg("-1", "IOT授权失败");
		
	}
	
	/**
	 * 公钥
	 */
	@RequestMapping(value = "/public_key", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, String> publicKey(HttpServletRequest request) {
		RSAPublicKey publicKey = rsaService.generateKey(request);
		Map<String, String> data = new HashMap<String, String>();
		data.put("modulus", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
		data.put("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));
		return data;
	}
	
	/**
	 * 设备开关
	 *	@param requestParams
	 *            requestParams={"action":"user","method":"updateremote", "session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":
	 *            {"remote":"true/false"}}
	 * @return
	 */	
	@RequestMapping(value="/updateremote", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String update(String requestParams) {
			JSONObject node = new JSONObject();
			JSONObject param = new JSONObject();
			JSONObject jsonReturn = new JSONObject();
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			SeniorManagement sm = new SeniorManagement();
			sm.setRemote(!param.getBoolean("remote"));
			sm.setId(param.optLong("id"));
			try {			
			seniorManagementService.update(sm,"belong","number","company","netflow","startdate","status","username");
			jsonReturn.put("remote", param.getInt("remote"));
			jsonReturn.put("errcode", "0");
			jsonReturn.put("errmsg", "设备关闭成功");
			if(sm.getRemote()){
			jsonReturn.put("errcode", "0");
			jsonReturn.put("errmsg", "设备开启成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonReturn.put("errcode", "400");
			jsonReturn.put("errmsg", "服务器错误");
			return jsonReturn.toString();
		}
		return jsonReturn.toString();
		
	}
	
	/**
	 * 流量充值
	 *	@param requestParams
	 *            requestParams={"action":"user","method":"updateremote", "session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param":
	 *            {"netflow":1000}}
	 * @return
	 */	
	@RequestMapping(value="/addNetflow", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String addNetflow(String requestParams) {
			JSONObject node = new JSONObject();
			JSONObject param = new JSONObject();
			JSONObject jsonReturn = new JSONObject();
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			SeniorManagement sm = seniorManagementService.find(param.getLong("id"));
			try {			
			seniorManagementService.payFlow(sm.getNumber(),param.getInt("netflow"));
			//sm.setNetflow(sm.getNetflow()+param.getInt("netflow"));		
			jsonReturn.put("errcode", "0");
			jsonReturn.put("errmsg", "流量充值成功");
		} catch (Exception e) {
			jsonReturn.put("errcode", "400");
			jsonReturn.put("errmsg", "服务器错误");
			return jsonReturn.toString();
		}
		return jsonReturn.toString();
		
	}
	
	/**
	 * 高级管理列表
	 * 	@param requestParams
	 *	{"action":"user","method":"seniorList","session":
	 *	{"sessionId":"9d366a992372499b961860b301750ec5","equipmentTypeId":0},"param":
	 *	{"pageNumber":1,"pageSize":20,"start":"2019-02-18 00:00:00","end":"2019-02-28 00:00:00","searchContent":"无锡淘金智能科技有限公司","searchName":"company"}}
	 * @return
	 */	
	@RequestMapping(value="/seniorList", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String list(String requestParams) {
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject jsonReturn = new JSONObject();
		
		node = JSONObject.fromObject(requestParams);
		param = node.getJSONObject("param");			
		List<Filter> filters = new ArrayList<Filter>();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
		if(param.has("start")&&param.has("end"))	 				
			filters.add(Filter.between("startdate", format.parse(param.getString("start")),format.parse(param.getString("end"))));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		filters.add(Filter.ne("status", "未激活"));
//		param.getString("search");
		if(param.has("searchName")){
		StringBuffer s = new StringBuffer();
		if(param.getString("searchName").equals("company"))
			filters.add(Filter.like("company",s.append("%").append(param.getString("searchContent")).append("%").toString()));
		if(param.getString("searchName").equals("belong"))	
			filters.add(Filter.like("belong",s.append("%").append(param.getString("belong")).append("%").toString()));
		if(param.getString("searchName").equals("username"))
			filters.add(Filter.like("username",s.append("%").append(param.getString("username")).append("%").toString()));
		if(param.getString("searchName").equals("status"))	
			filters.add(Filter.like("status",s.append("%").append(param.getString("status")).append("%").toString()));		
		}
		Pageable pageable = new Pageable(param.getInt("pageNumber"),param.getInt("pageSize"));
		pageable.setFilters(filters);
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.desc("id"));
		pageable.setOrders(orders);
		Page<SeniorManagement> page = seniorManagementService.findPage(pageable);
		String str = JSON.toJSONString(page.getContent());
		jsonReturn.put("values", str);
		jsonReturn = super.getJsonPage(page, jsonReturn);
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "设备列表获取成功");
		return jsonReturn.toString();
	}

	/**
	 * 档案管理列表
	 * 	@param requestParams
	 *	{"action":"user","method":"seniorList","session":
	 *	{"sessionId":"9d366a992372499b961860b301750ec5","equipmentTypeId":0},"param":
	 *	{"pageNumber":1,"pageSize":20,"start":"2019-02-18 00:00:00","end":"2019-02-28 00:00:00","searchContent":"无锡淘金智能科技有限公司","searchName":"company"}}
	 * @return
	 */	
	@RequestMapping(value="/companyList", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String companylist(String requestParams) {
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject jsonReturn = new JSONObject();
		node = JSONObject.fromObject(requestParams);
		param = node.getJSONObject("param");			
		List<Filter> filters = new ArrayList<Filter>();				
		List<Filter> orFiltrers = new ArrayList<Filter>();
		if(param.has("searchContent")&&param.get("searchContent")!=null){
			filters.add(Filter.like("company","%"+param.getString("searchContent")+"%"));
			orFiltrers.add(Filter.like("name","%"+param.getString("searchContent")+"%"));		
		}
		filters.add(Filter.or(orFiltrers));
		Pageable pageable = new Pageable(param.getInt("pageNumber"),param.getInt("pageSize"));
		pageable.setFilters(filters);
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.asc("id"));
		pageable.setOrders(orders);
		Page<CompanyProfile> page = companyProfileService.findPage(pageable);
		String str = JSON.toJSONString(page.getContent());
		jsonReturn.put("values", str);
		jsonReturn = super.getJsonPage(page, jsonReturn);
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "公司列表获取成功");
		return jsonReturn.toString();
	}
	
	/**
	 * 查看单位设备
	 */
	@RequestMapping(value="/eList", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String eList(String requestParams) {
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject jsonReturn = new JSONObject();		
		node = JSONObject.fromObject(requestParams);
		param = node.getJSONObject("param");			
		List<Filter> filters = new ArrayList<Filter>();
//		filters.add(Filter.ne("status", "未激活"));
//		param.getString("search");
		filters.add(Filter.eq("company",param.getString("company")));	
		Pageable pageable = new Pageable(param.getInt("pageNumber"),param.getInt("pageSize"));
		pageable.setFilters(filters);
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.desc("id"));
		pageable.setOrders(orders);
		Page<SeniorManagement> page = seniorManagementService.findPage(pageable);
		String str = JSON.toJSONString(page.getContent());
		jsonReturn.put("values", str);
		jsonReturn = super.getJsonPage(page, jsonReturn);
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "设备列表获取成功");
		return jsonReturn.toString();
	}
	
	/**
	 * 单位更新
	 */
	@RequestMapping(value="/companyUpdate", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String companyUpdate(String requestParams) {
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject jsonReturn = new JSONObject();
		node = JSONObject.fromObject(requestParams);
		param = node.getJSONObject("param");
		CompanyProfile c = new CompanyProfile();
		c.setId(param.getLong("id"));
		c.setCompany(param.getString("company"));
		c.setName(param.getString("name"));
		c.setPhone(param.getString("phone"));
		if(param.has("newCompany")&&!param.get("newCompany").equals(c.getCompany())){
			if(companyProfileService.getByParam("company",param.getString("newCompany"))==null){		
				c.setCompany(param.getString("newCompany"));
				companyProfileService.update(c);
				seniorManagementService.updatecompany(param.getString("newCompany"),c.getName());		
				jsonReturn.put( "errcode", "0");
				jsonReturn.put("errmsg", "更新成功");
				return jsonReturn.toString();
			}
			jsonReturn.put( "errcode", "1");
			jsonReturn.put("errmsg", "公司名重复");
			return jsonReturn.toString();
		}
		companyProfileService.update(c);
		jsonReturn.put( "errcode", "0");
		jsonReturn.put("errmsg", "更新成功");
		return jsonReturn.toString();		
	}
	
	
	/**
	 * 单位添加
	 */
	@RequestMapping(value="/companyAdd", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String companyAdd(String requestParams) {
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject jsonReturn = new JSONObject();
		node = JSONObject.fromObject(requestParams);
		param = node.getJSONObject("param");	
		if(companyProfileService.count(Filter.eq("company",param.getString("company")))==0){
			CompanyProfile c = new CompanyProfile();
			c.setCompany(param.getString("company"));
			c.setName(param.getString("name"));
			c.setPhone(param.getString("phone"));
			companyProfileService.save(c);
			jsonReturn.put( "errcode", "0");
			jsonReturn.put("errmsg", "公司添加成功");
			return jsonReturn.toString();
		}
		jsonReturn.put( "errcode", "1");
		jsonReturn.put("errmsg", "公司重复");
		return jsonReturn.toString();
	}
	
	/**
	 * 单位删除
	 */
	@RequestMapping(value="/companyDelete", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String companyDelete(String requestParams) {	
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject jsonReturn = new JSONObject();
		node = JSONObject.fromObject(requestParams);
		param = node.getJSONObject("param");	
		CompanyProfile c = new CompanyProfile();
		c.setId(param.getLong("id"));
		if(companyProfileService.find(param.getLong("id")).getCount()==0){
		companyProfileService.delete(param.getLong("id"));
		jsonReturn.put( "errcode", "0");
		jsonReturn.put("errmsg", "公司删除成功");
		}
		jsonReturn.put( "errcode", "1");
		jsonReturn.put("errmsg", "公司删除失败");
		return jsonReturn.toString();
	}
	
	@RequestMapping(value="/roleAdd", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String roleAdd(String requestParams){
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject jsonReturn = new JSONObject();
		node = JSONObject.fromObject(requestParams);
		param = node.getJSONObject("param");
		UserRole role =  com.alibaba.fastjson.JSONObject.parseObject(param.toString(), UserRole.class);	
		userRoleService.save(role);	
		jsonReturn.put( "code", "0");
		jsonReturn.put("msg", "角色添加成功");
		return jsonReturn.toString();
	}
	
	@RequestMapping(value="/roleUpdate", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String roleUpdate(String requestParams){
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject jsonReturn = new JSONObject();
		node = JSONObject.fromObject(requestParams);
		param = node.getJSONObject("param");
		UserRole role =  com.alibaba.fastjson.JSONObject.parseObject(param.toString(), UserRole.class);	
		userRoleService.update(role);	
		jsonReturn.put( "code", "0");
		jsonReturn.put("msg", "角色更新成功");
		return jsonReturn.toString();
	}
	
	@RequestMapping(value="/roleDelete", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String roleDelete(String requestParams){
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject jsonReturn = new JSONObject();
		node = JSONObject.fromObject(requestParams);
		param = node.getJSONObject("param");
		UserRole role =  com.alibaba.fastjson.JSONObject.parseObject(param.toString(), UserRole.class);	
		User user = userService.getByParam("roleId",role.getId());
		if(user!=null&&!user.getIsDel()){
			jsonReturn.put("code", "1");
			jsonReturn.put("msg", "有员工使用此权限");
			return jsonReturn.toString();
		}
		userRoleService.delete(role.getId());	
		jsonReturn.put( "code", "0");
		jsonReturn.put("msg", "角色删除成功");
		return jsonReturn.toString();
	}
	
	@RequestMapping(value="/roleStop", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String roleStop(String requestParams){
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject jsonReturn = new JSONObject();
		node = JSONObject.fromObject(requestParams);
		param = node.getJSONObject("param");
		UserRole role =  com.alibaba.fastjson.JSONObject.parseObject(param.toString(), UserRole.class);	
		role.setIsDel(true);
		if(userService.getByParam("roleId",role.getId())!=null){
			jsonReturn.put( "code", "1");
			jsonReturn.put("msg", "有员工使用此权限");
			return jsonReturn.toString();
		}
		userRoleService.update(role);	
		jsonReturn.put( "code", "0");
		jsonReturn.put("msg", "角色停用成功");
		return jsonReturn.toString();
	}
	
	@RequestMapping(value="/roleOn", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String roleOn(String requestParams){
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject jsonReturn = new JSONObject();
		node = JSONObject.fromObject(requestParams);
		param = node.getJSONObject("param");
		UserRole role =  com.alibaba.fastjson.JSONObject.parseObject(param.toString(), UserRole.class);	
		role.setIsDel(false);
		userRoleService.update(role);	
		jsonReturn.put( "code", "0");
		jsonReturn.put("msg", "角色启用成功");
		return jsonReturn.toString();
	}
	
	@RequestMapping(value="/roleList", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String roleList(String requestParams){
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		node = JSONObject.fromObject(requestParams);
		param = node.getJSONObject("param");
		List<Filter> filters = new ArrayList<Filter>();
		//filters.add(Filter.ne("isDel",true));
		if(param.has("roleName")&&!"".equals(param.getString("roleName"))){
		filters.add(Filter.like("roleName","%"+param.getString("roleName")+"%"));
		}
		List<UserRole> list = userRoleService.findList(null,filters,null);
		Map<String,Object> jsonReturn = new HashMap<>();
		jsonReturn.put( "code", "0");
		jsonReturn.put("msg", "角色列表获取成功");
		jsonReturn.put("values",list);
		return JSON.toJSONString(jsonReturn);
	}
	
	

	@RequestMapping(value="/roleList2", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String roleList2(String requestParams){
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(Filter.ne("isDel",true));
		List<UserRole> list = userRoleService.findList(null,filters,null);
		Map<String,Object> jsonReturn = new HashMap<>();
		jsonReturn.put("code", "0");
		jsonReturn.put("msg", "角色列表获取成功");
		jsonReturn.put("values",list);
		return JSON.toJSONString(jsonReturn);
	}
	
}
