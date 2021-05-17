package com.taojin.iot.api.management.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.taojin.iot.BaseController;
import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.Order;
import com.taojin.iot.base.comm.Page;
import com.taojin.iot.base.comm.Pageable;
import com.taojin.iot.service.management.entity.EquipmentUpdate;
import com.taojin.iot.service.management.service.EquipmentUpdateService;

@Controller("internalManagementUpdateController")
@RequestMapping("/internal/management/management")
public class ManagementUpdateController extends BaseController { 
	@Resource(name = "equipmentUpdateServiceImpl")
	private EquipmentUpdateService equipmentUpdateService;
	
	
	@RequestMapping(value="/addVersion")
	@ResponseBody
	public String addversion(HttpSession session,String name,String idNumber,String version,@RequestParam("file") MultipartFile file) {
		EquipmentUpdate e = new EquipmentUpdate();
		JSONObject jsonReturn = new JSONObject();
		e.setName(name);
		e.setIdNumber(idNumber);
		e.setVersion(version);
		String path = session.getServletContext().getRealPath("/EquipmentSystem/");
		String filename = idNumber+version+".hex";
		File f = new File(path,filename);
		if (!f.exists()) {
			f.mkdir();
			}
		try {
			file.transferTo(f);
		} catch (Exception e1) {
			jsonReturn.put( "errcode","1");
			jsonReturn.put("errmsg", "文件上传失败");
			return jsonReturn.toString();
		}
		e.setFileAddress(path);
		equipmentUpdateService.save(e);
			jsonReturn.put( "errcode", "0");
			jsonReturn.put("errmsg", "版本添加成功");
			return jsonReturn.toString();
	}
	
	@RequestMapping(value="/updateVersion")
	@ResponseBody
	public String updateVersion(HttpSession session,Long id,String name,String idNumber,String version,@RequestParam("file") MultipartFile file) {
		EquipmentUpdate ne = new EquipmentUpdate();
		JSONObject jsonReturn = new JSONObject();
		ne.setId(id);
		ne.setName(name);
		ne.setIdNumber(idNumber);
		ne.setVersion(version);
		EquipmentUpdate e = equipmentUpdateService.find(id);
		File deletefile=new File(e.getFileAddress());
        if( deletefile.exists()&& deletefile.isFile())
        	 deletefile.delete();
		String path = session.getServletContext().getRealPath("/EquipmentSystem");
		String filename = idNumber+version+".hex";
		File f = new File(path,filename);
		try {
			file.transferTo(f);
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {		
			e1.printStackTrace();
		}
		e.setFileAddress(path);
		equipmentUpdateService.update(ne);
			jsonReturn.put( "errcode", "0");
			jsonReturn.put("errmsg", "版本添加成功");
			return jsonReturn.toString();
	}
	
	@RequestMapping(value="/deleteVersion", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deleteVersion(String requestParams) {
		JSONObject node = new JSONObject();
		JSONObject jsonReturn = new JSONObject();
		node = JSONObject.fromObject(requestParams);
		EquipmentUpdate e = JSON.parseObject(node.getJSONObject("param").toString(),EquipmentUpdate.class);
		EquipmentUpdate de = equipmentUpdateService.find(e.getId());
		File deletefile=new File(de.getFileAddress());
        if( deletefile.exists()&& deletefile.isFile())
        	 deletefile.delete();
		equipmentUpdateService.delete(e);
			jsonReturn.put( "errcode", "0");
			jsonReturn.put("errmsg", "版本删除成功");
			return jsonReturn.toString();
	}
	
	@RequestMapping(value="/listVersion", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listVersion(String requestParams) {
		JSONObject node = new JSONObject();
		JSONObject param = new JSONObject();
		JSONObject jsonReturn = new JSONObject();
		node = JSONObject.fromObject(requestParams);
		param = node.getJSONObject("param");			
		List<Filter> filters = new ArrayList<Filter>();	
		if(param.has("searchName")){
			StringBuffer s = new StringBuffer();
			if(param.getString("searchName").equals("name"))
				filters.add(Filter.like("company",s.append("%").append(param.getString("searchContent")).append("%").toString()));
			if(param.getString("searchName").equals("idNumber"))	
				filters.add(Filter.like("name",s.append("%").append(param.getString("searchContent")).append("%").toString()));
		}
		Pageable pageable = new Pageable(param.getInt("pageNumber"),param.getInt("pageSize"));
		pageable.setFilters(filters);
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.asc("id"));
		pageable.setOrders(orders);
		Page<EquipmentUpdate> page = equipmentUpdateService.findPage(pageable);
		String str = JSON.toJSONString(page.getContent());
		jsonReturn.put("values", str);
		jsonReturn = super.getJsonPage(page, jsonReturn);
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "设备版本列表获取成功");
			return jsonReturn.toString();
}
	
}
