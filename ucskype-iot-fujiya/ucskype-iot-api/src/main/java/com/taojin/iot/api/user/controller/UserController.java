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
 * Controller-?????? iot???????????????
 * ============================================================================
 * ???????????? 2013-2016 ???????????????????????????????????????????????????????????????
 * ----------------------------------------------------------------------------
 * ?????????????????????UcSkype????????????????????????????????????????????????????????????????????????UcSkype???????????????????????????
 * ----------------------------------------------------------------------------
 * ???????????????http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * ??????8:34:24 author ??????
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
	 * ??????????????????
	 * 
	 * @param requestParams
	 *            requestParams={"action":"user","method":"userInfo","session":
	 *            {"sessionId":"??????ID","equipmentTypeId":"??????ID"}}
	 * @return
	 */
	@RequestMapping(value = "/userInfo", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String userInfo(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "????????????????????????");
		}
		JSONObject node = new JSONObject();
		JSONObject session = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
			session = node.getJSONObject("session");
		} catch (Exception e) {
			return successMsg("-1", "??????????????????!");
		}
		// ????????????
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "????????????,?????? ?????????!");
		} else if (super.getEquipmentTypeId(session) == null) {
			return errorMsg("401", "????????????,?????????????????????!");
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
		jsonReturn.put("errmsg", "??????????????????!");
		jsonReturn.put("value", json);
		return jsonReturn.toString();
	}

	/**
	 * ????????????
	 * 
	 * @param requestParams
	 *            requestParams={"action":"user","method":"modifyPassword",
	 *            "session":
	 *            {"sessionId":"??????ID","equipmentTypeId":"??????ID"},"param":
	 *            {"oldPassword"
	 *            :"????????????","newPassword":"?????????","reNewPassword":"???????????????"}}
	 * @return
	 */
	@RequestMapping(value = "/modifyPassword", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String modifyPassword(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "????????????????????????");
		}
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject session = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			session = node.getJSONObject("session");
		} catch (Exception e) {
			return successMsg("-1", "??????????????????!");
		}
		// ????????????
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "????????????,?????? ?????????!");
		} else if (super.getEquipmentTypeId(session) == null) {
			return errorMsg("401", "????????????,?????????????????????!");
		}

		if (!param.has("oldPassword")) {
			return errorMsg("-2", "?????????????????????!");
		}
		if (!param.has("newPassword")) {
			return errorMsg("-2", "??????????????????!");
		}
		if (!param.has("reNewPassword")) {
			return errorMsg("-2", "??????????????????!");
		}
		if (!StringUtils.equalsIgnoreCase(param.getString("newPassword"),
				param.getString("reNewPassword"))) {
			return errorMsg("-2", "??????????????????????????????!");
		}

		if (param.getString("newPassword").length() < 6
				|| param.getString("newPassword").length() > 15) {
			return errorMsg("-2", "?????????????????????6-15??????????????????????????????!");
		}

		User user = userService.find(Long.parseLong(userSession.getUserId()));
		String newPassword = DigestUtils.md5Hex(param.getString("newPassword"));
		String oldPassword = DigestUtils.md5Hex(param.getString("oldPassword"));

		if (!StringUtils.equalsIgnoreCase(oldPassword, user.getPassword())) {
			return errorMsg("-2", "??????????????????!");
		}

		user.setPassword(newPassword);
		userService.update(user);
		return successMsg("0", "??????????????????!");
	}

	/**
	 * ??????
	 * 
	 * @param requestParams
	 *            requestParams={"action":"user","method":"login","session":
	 *            {"sessionId":"??????ID","equipmentTypeId":"??????ID"},"param":
	 *            {"username"
	 *            :"?????????","password":"??????","device":"????????????","systemVersion":"?????????"}}
	 * @return
	 */
	@RequestMapping(value = "/login", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String login(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "????????????????????????");
		}
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject session = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			session = node.getJSONObject("session");
		} catch (Exception e) {
			return successMsg("-1", "??????????????????!");
		}

		if (!param.has("username")) {
			return successMsg("-1", "??????????????????!");
		}

		if (!param.has("password")) {
			return successMsg("-1", "???????????????!");
		}

		if (!param.has("device")) {
			return successMsg("-1", "????????????!");
		}

		if (!param.has("systemVersion")) {
			return successMsg("-1", "????????????!");
		}

		User user = userService.getByParam("username",
				param.getString("username"));
		if (user == null) {			
			return successMsg("-1", "??????????????? !");
		}
		String password = DigestUtils.md5Hex(param.getString("password"));
		if (!StringUtils.equalsIgnoreCase(user.getPassword(), password)) {
			return successMsg("-1", "??????????????? !");
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
		jsonReturn.put("errmsg", "????????????!");
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
		jsonReturn.put("msg", "????????????????????????");
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
		jsonReturn.put("msg", "??????????????????");
		return jsonReturn.toString();
	}
	
	
	/**
	 * ??????
	 * 
	 * @param requestParams
	 *            requestParams={"action":"user","method":"regist","param":
	 *            {"username"
	 *            :"?????????","password":"??????","rePassword":"????????????","type":"????????????"}}
	 * @return
	 */
	@RequestMapping(value = "/regist", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String regist(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "????????????????????????");
		}
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-3", "??????????????????!");
		}
/*
		if (!param.has("type")) {
			return successMsg("-3", "????????????!");
		}
*/
		if (!param.has("username")) {
			return successMsg("-3", "??????????????????!");
		}

		if (!param.has("password")) {
			return successMsg("-3", "???????????????!");
		}

		/*if (!param.has("rePassword")) {
			return successMsg("-3", "???????????????!");
		}

		if (!StringUtils.equalsIgnoreCase(param.getString("password"),
				param.getString("rePassword"))) {
			return successMsg("-1", "???????????????????????????!");
		}*/

		if (StringUtils.length(param.getString("password")) < 6
				|| StringUtils.length(param.getString("password")) > 16) {
			return errorMsg("-3", "???????????????6-16??????????????????????????????");
		}

		User user = userService.getByParam("username",
				param.getString("username"));
		if (user != null) {
			return successMsg("-1", "??????????????? !");
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
		jsonReturn.put("msg", "??????????????????");
		return JSON.toJSONString(jsonReturn);

	}

	/**
	 * ??????
	 * 
	 * @param requestParams
	 *            requestParams={"action":"user","method":"pay", "session":
	 *            {"sessionId":"??????ID","equipmentTypeId":"??????ID"},"param":
	 *            {"money":"??????","type":"????????????","num":"??????"}}
	 * @return
	 */
	@RequestMapping(value = "/pay", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String pay(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "????????????????????????");
		}
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject session = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			session = node.getJSONObject("session");
		} catch (Exception e) {
			return successMsg("-1", "??????????????????!");
		}

		// ????????????
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "????????????,???????????????!");
		} else if (super.getEquipmentTypeId(session) == null) {
			return errorMsg("401", "????????????,?????????????????????!");
		}

		//????????????
		if(!param.has("type")){
			return errorMsg("-2", "????????????!");
		}
		
		if(!param.has("money")){
			return errorMsg("-2", "?????????????????????!");
		}
		
		if(!param.has("num")){
			param.put("num", 1);
		}
		
		BigDecimal money = new BigDecimal(param.getString("money"));
		if(money.compareTo(new BigDecimal("100")) == -1){
			return errorMsg("-3", "????????????????????????100???!");
		}
		User user = userService.find(Long.parseLong(userSession.getUserId()));
		if(user == null){
			return errorMsg("302", "????????????,???????????????!");
		}
		if(StringUtils.equalsIgnoreCase(param.getString("type"), "user")){
			BigDecimal nowMoney = user.getFee();
			user.setFee(nowMoney.add(money));
		}else if(StringUtils.equalsIgnoreCase(param.getString("type"), "sms")){
			if(!param.has("num")){
				return errorMsg("-2", "????????????,????????????!");
			}
			if(!param.has("amount")){
				return errorMsg("-2", "????????????,?????????????????????!");
			}
			user.setSmsCount(user.getSmsCount()+param.getInt("amount"));
		}
		userService.update(user);
		//???????????? ??????
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
		return successMsg("0", "????????????!");
	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loginAuthor", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loginAuthor(String authorkey){
		try{
//			String authorkey = rsaService.decryptParameter("authorkey", request);//????????????key
//			rsaService.removePrivateKey(request);//????????????
			//?????????????????? authorkey=?????????_??????
			
			if(StringUtils.isBlank(authorkey)){
				return super.errorMsg("-1", "iot????????????");
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
				return successMsg("-1", "??????????????? !");
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
			jsonReturn.put("errmsg", "??????????????????!!");
			jsonReturn.put("authorurl", "http://iot.ucskype.com/#/access/navList");
			jsonReturn.put("authorplat", "IOT");
			jsonReturn.put("value", json);
			return jsonReturn.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return super.errorMsg("-1", "IOT????????????");
		
	}
	
	/**
	 * ??????
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
	 * ????????????
	 *	@param requestParams
	 *            requestParams={"action":"user","method":"updateremote", "session":
	 *            {"sessionId":"??????ID","equipmentTypeId":"??????ID"},"param":
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
			jsonReturn.put("errmsg", "??????????????????");
			if(sm.getRemote()){
			jsonReturn.put("errcode", "0");
			jsonReturn.put("errmsg", "??????????????????");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonReturn.put("errcode", "400");
			jsonReturn.put("errmsg", "???????????????");
			return jsonReturn.toString();
		}
		return jsonReturn.toString();
		
	}
	
	/**
	 * ????????????
	 *	@param requestParams
	 *            requestParams={"action":"user","method":"updateremote", "session":
	 *            {"sessionId":"??????ID","equipmentTypeId":"??????ID"},"param":
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
			jsonReturn.put("errmsg", "??????????????????");
		} catch (Exception e) {
			jsonReturn.put("errcode", "400");
			jsonReturn.put("errmsg", "???????????????");
			return jsonReturn.toString();
		}
		return jsonReturn.toString();
		
	}
	
	/**
	 * ??????????????????
	 * 	@param requestParams
	 *	{"action":"user","method":"seniorList","session":
	 *	{"sessionId":"9d366a992372499b961860b301750ec5","equipmentTypeId":0},"param":
	 *	{"pageNumber":1,"pageSize":20,"start":"2019-02-18 00:00:00","end":"2019-02-28 00:00:00","searchContent":"????????????????????????????????????","searchName":"company"}}
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
		filters.add(Filter.ne("status", "?????????"));
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
		jsonReturn.put("errmsg", "????????????????????????");
		return jsonReturn.toString();
	}

	/**
	 * ??????????????????
	 * 	@param requestParams
	 *	{"action":"user","method":"seniorList","session":
	 *	{"sessionId":"9d366a992372499b961860b301750ec5","equipmentTypeId":0},"param":
	 *	{"pageNumber":1,"pageSize":20,"start":"2019-02-18 00:00:00","end":"2019-02-28 00:00:00","searchContent":"????????????????????????????????????","searchName":"company"}}
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
		jsonReturn.put("errmsg", "????????????????????????");
		return jsonReturn.toString();
	}
	
	/**
	 * ??????????????????
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
//		filters.add(Filter.ne("status", "?????????"));
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
		jsonReturn.put("errmsg", "????????????????????????");
		return jsonReturn.toString();
	}
	
	/**
	 * ????????????
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
				jsonReturn.put("errmsg", "????????????");
				return jsonReturn.toString();
			}
			jsonReturn.put( "errcode", "1");
			jsonReturn.put("errmsg", "???????????????");
			return jsonReturn.toString();
		}
		companyProfileService.update(c);
		jsonReturn.put( "errcode", "0");
		jsonReturn.put("errmsg", "????????????");
		return jsonReturn.toString();		
	}
	
	
	/**
	 * ????????????
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
			jsonReturn.put("errmsg", "??????????????????");
			return jsonReturn.toString();
		}
		jsonReturn.put( "errcode", "1");
		jsonReturn.put("errmsg", "????????????");
		return jsonReturn.toString();
	}
	
	/**
	 * ????????????
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
		jsonReturn.put("errmsg", "??????????????????");
		}
		jsonReturn.put( "errcode", "1");
		jsonReturn.put("errmsg", "??????????????????");
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
		jsonReturn.put("msg", "??????????????????");
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
		jsonReturn.put("msg", "??????????????????");
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
			jsonReturn.put("msg", "????????????????????????");
			return jsonReturn.toString();
		}
		userRoleService.delete(role.getId());	
		jsonReturn.put( "code", "0");
		jsonReturn.put("msg", "??????????????????");
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
			jsonReturn.put("msg", "????????????????????????");
			return jsonReturn.toString();
		}
		userRoleService.update(role);	
		jsonReturn.put( "code", "0");
		jsonReturn.put("msg", "??????????????????");
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
		jsonReturn.put("msg", "??????????????????");
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
		jsonReturn.put("msg", "????????????????????????");
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
		jsonReturn.put("msg", "????????????????????????");
		jsonReturn.put("values",list);
		return JSON.toJSONString(jsonReturn);
	}
	
}
