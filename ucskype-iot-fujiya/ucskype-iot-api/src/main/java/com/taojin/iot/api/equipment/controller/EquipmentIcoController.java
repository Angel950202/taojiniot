package com.taojin.iot.api.equipment.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taojin.iot.BaseController;
import com.taojin.iot.base.comm.FileInfo.FileType;
import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.entity.UserSession;
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.base.comm.utils.UUIDTools;
import com.taojin.iot.service.equipment.entity.EquipmentIco;
import com.taojin.iot.service.equipment.entity.EquipmentIco.IcoType;
import com.taojin.iot.service.equipment.service.EquipmentIcoService;

/**
 * Controller-设备图标 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 上午11:29:59 author 王杰
 * ============================================================================
 */
@Controller("internalEquipmentIcoController")
@RequestMapping("/internal/equipment/equipmentIco")
public class EquipmentIcoController extends BaseController {
	@Resource(name = "equipmentIcoServiceImpl")
	private EquipmentIcoService equipmentIcoService;
	@Value("${file.imagePath}")
	private String imagePath;

	/**
	 * 获取图标
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentIco","method":"list",
	 *            "session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param"
	 *            :{"icoType":"图标类型"}}
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
		UserSession userSession = super.getSession(session);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		} /*else if (super.getEquipmentTypeId(session) == null) {
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}
*/
		List<Filter> filters = new ArrayList<Filter>();
		if (param.has("icoType")) {
			filters.add(Filter.eq("icoType",
					IcoType.valueOf(param.getString("icoType"))));
		}

		List<EquipmentIco> equipmentIcos = equipmentIcoService.findListByParam(
				filters, userSession.getUserId());
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "查询成功!");
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < equipmentIcos.size(); i++) {
			JSONObject json = new JSONObject();
			json.put("id", equipmentIcos.get(i).getId());
			json.put("name", equipmentIcos.get(i).getName());
			json.put("icoPath", equipmentIcos.get(i).getIcoPath());
			json.put("icoType", equipmentIcos.get(i).getIcoType().name());
			json.put("icoOnlinePath", equipmentIcos.get(i).getOnlineIco());

			jsonArray.add(json);
		}

		jsonReturn.put("values", jsonArray);
		return jsonReturn.toString();
	}

	/**
	 * 添加图标
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentIco","method":"save",
	 *            "session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param"
	 *            :{"name":"图标名称","icoPath":"图标路径","icoType":"图标类型"}}
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

		// 参数验证
		if (!param.has("name")) {
			param.put("name", UUIDTools.getUUID());
		}
		if (!param.has("icoPath")) {
			return errorMsg("-2", "参数有误!");
		}
		if (!param.has("icoType")) {
			param.put("icoType", "device");
		}

		EquipmentIco equipmentIco = new EquipmentIco();
		equipmentIco.setName(param.getString("name"));
		equipmentIco.setIcoPath(param.getString("icoPath"));
		equipmentIco.setIcoType(IcoType.valueOf(param.getString("icoType")));
		equipmentIco.setOwnerId(Long.parseLong(userSession.getUserId()));
		equipmentIco.setIssystem(0);
		equipmentIco.setEquipmentTypeId(session.getLong("equipmentTypeId"));

		equipmentIcoService.save(equipmentIco);
		return successMsg("0", "添加图标成功!");
	}

	/**
	 * 更新图标
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentIco","method":"update",
	 *            "session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param"
	 *            :{"equipmentIcoId":"键值","icoPath":"图标路径"}}
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
		if (super.getSession(session) == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		} else if (super.getEquipmentTypeId(session) == null) {
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}

		if (!param.has("equipmentIcoId")) {
			return errorMsg("-1", "请选择一个修改项!");
		}

		EquipmentIco equipmentIco = equipmentIcoService.find(param
				.getLong("equipmentIcoId"));
		if (equipmentIco.getIssystem() == 1) {
			return errorMsg("-2", "当前图标为系统图标,不可修改!");
		}
		// 参数验证
		if (param.has("icoPath")) {
			equipmentIco.setIcoPath(param.getString("icoPath"));
			equipmentIcoService.update(equipmentIco);
		}

		return successMsg("0", "更新图标成功!");
	}

	/**
	 * 删除图标
	 * 
	 * @param requestParams
	 *            requestParams={"action":"equipmentIco","method":"delete",
	 *            "session":
	 *            {"sessionId":"会话ID","equipmentTypeId":"频道ID"},"param"
	 *            :{"equipmentIcoId":"键值"}}
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
		if (super.getSession(session) == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		} else if (super.getEquipmentTypeId(session) == null) {
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}

		if (!param.has("equipmentIcoId")) {
			return errorMsg("-1", "请选择一个修改项!");
		}

		EquipmentIco equipmentIco = equipmentIcoService.find(param
				.getLong("equipmentIcoId"));
		if (equipmentIco.getIssystem() == 1) {
			return errorMsg("-2", "当前图标为系统图标,不可删除!");
		}

		equipmentIcoService.delete(param.getLong("equipmentIcoId"));
		return successMsg("0", "删除图标成功!");
	}

	/**
	 * 上传图片
	 * 
	 * @param requestParams
	 * @param model
	 * @param file
	 *            文件名称
	 * @return
	 */
	@RequestMapping(value = "/imageUpdate")
	@ResponseBody
	public String imageUpdate(@RequestParam("file") MultipartFile[] file,
			String session, String icoType, ModelMap model) {
		JSONObject sessionObj = new JSONObject();
		try {
			sessionObj = JSONObject.fromObject(session);
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}

		if (StringUtils.isBlank(icoType)) {
			return successMsg("-1", "参数有误!");
		}

		// 会话验证
		UserSession userSession = super.getSession(sessionObj);
		if (userSession == null) {
			return errorMsg("302", "会话超时,请重 新登录!");
		} else if (super.getEquipmentTypeId(sessionObj) == null) {
			return errorMsg("401", "频道丢失,请重新进入频道!");
		}
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", " 图标上传成功!");
		for (int i = 0; i < file.length; i++) {
			if (file != null && !file[i].isEmpty()) {
				try {
					JSONObject obj1 = new JSONObject();
					String filePath = uploadLocal(FileType.image, file[i]);
					EquipmentIco equipmentIco = new EquipmentIco();
					equipmentIco.setOwnerId(Long.parseLong(userSession
							.getUserId()));
					equipmentIco.setName(String.valueOf(System
							.currentTimeMillis()));
					equipmentIco.setIcoPath(filePath);
					equipmentIco.setOnlineIco(filePath);
					equipmentIco.setIcoType(IcoType.valueOf(icoType));
					equipmentIco.setIssystem(0);

					equipmentIcoService.save(equipmentIco);

					System.out.println(filePath);
					obj1.put("image_path", filePath);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return jsonReturn.toString();
	}

	public String uploadLocal(FileType fileType, MultipartFile multipartFile) {
		if (multipartFile == null) {
			return null;
		}
		String uploadPath;
		uploadPath = "/upload/images/" + DatesUtils.getStringToday("yyyyMM")
				+ "/";
		System.out.println(uploadPath);
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("uuid", UUID.randomUUID().toString());
			String path = imagePath + uploadPath;
			String destPath1 = UUID.randomUUID()
					+ "."
					+ FilenameUtils.getExtension(multipartFile
							.getOriginalFilename());
			String destPath = path + destPath1;
			File destFile = new File(destPath);
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}
			multipartFile.transferTo(destFile);
			return uploadPath + destPath1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
