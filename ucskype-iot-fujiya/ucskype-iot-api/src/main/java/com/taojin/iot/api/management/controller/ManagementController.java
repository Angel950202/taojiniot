package com.taojin.iot.api.management.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;


//import com.sargeraswang.util.ExcelUtil.ExcelLogs;
//import com.sargeraswang.util.ExcelUtil.ExcelUtil;
import com.taojin.iot.BaseController;
import com.taojin.iot.service.user.entity.CompanyProfile;
import com.taojin.iot.service.user.entity.SeniorManagement;
import com.taojin.iot.service.user.service.CompanyProfileService;
import com.taojin.iot.service.user.service.SeniorManagementService;

@Controller("internalManagementController")
@RequestMapping("/internal/management/management")
public class ManagementController extends BaseController {
	@Resource(name = "seniorManagementServiceImpl")
	private SeniorManagementService seniorManagementService;
	@Resource(name = "companyProfileServiceImpl")
	private CompanyProfileService companyProfileService;
//	@SuppressWarnings("rawtypes")
//	@RequestMapping("/excel")
//	public String addTrainPicture(String company, String name, Long id,int count, MultipartFile file) {
//		JSONObject jsonReturn = new JSONObject();
//		ExcelLogs logs = new ExcelLogs();
//		System.out.println(file.getSize());
//		System.out.println(file.getOriginalFilename());
//		System.out.println(company);
//		System.out.println(name);
//		System.out.println(count);
//		System.out.println(id);
//		CompanyProfile c = new CompanyProfile();
//		c.setId(id);	
//		Collection<Map> importExcel;
//		int x = 0;
//		try {
//			importExcel = ExcelUtil.importExcel(Map.class,
//					file.getInputStream(), null, logs, 0);
//			for ( Map m : importExcel) {
//				SeniorManagement s = new SeniorManagement();
//				s.setCompany(company); // 所属公司
//				s.setBelong(name); // 所属账户
//				s.setNumber(m.get("设备号").toString());
//				s.setUsername(m.get("设备名").toString());
//				s.setStatus("未激活");
//				seniorManagementService.save(s);
//				x++;
//			}
//			c.setCount(count + x);
//			companyProfileService.update(c,"company", "belong","name","phone","creator");
//			jsonReturn.put("errcode", "0");
//			jsonReturn.put("errmsg", "设备添加成功!");
//			return jsonReturn.toString();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		jsonReturn.put("errcode", "1");
//		jsonReturn.put("errmsg", "设备添加失败!");
//		return jsonReturn.toString();
//	}
	
/*	
	@RequestMapping(value="/status", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String EquipmentStatus(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject session = new JSONObject();
		JSONObject jsonReturn = new JSONObject();
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
		if(!param.has("equipmentId")){
			return errorMsg("-2", "缺少设备ID!");
		}
			jsonReturn.put( "errcode", "0");
			jsonReturn.put("errmsg", "设备状态获取成功");
			return jsonReturn.toString();
	}*/
}
