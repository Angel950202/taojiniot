package com.taojin.iot.api.quality.controller;

import com.alibaba.fastjson.JSON;
import com.taojin.iot.BaseController;
import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.Page;
import com.taojin.iot.base.comm.Pageable;
import com.taojin.iot.base.comm.entity.UserSession;
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.service.quality.entiy.QualityInspection;
import com.taojin.iot.service.quality.entiy.QualityInspectionDetail;
import com.taojin.iot.service.quality.entiy.QualityStandard;
import com.taojin.iot.service.quality.entiy.QualityStandardDetail;
import com.taojin.iot.service.quality.service.QualityInspectionDetailService;
import com.taojin.iot.service.quality.service.QualityInspectionService;
import com.taojin.iot.service.quality.service.QualityStandardDetailService;
import com.taojin.iot.service.quality.service.QualityStandardService;
import com.taojin.iot.service.user.entity.User;
import com.taojin.iot.service.user.service.UserService;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.util.*;

/**
 * @program: ucskype-iot
 * @description:质量管理控制器
 * @author: LiHeYang
 * @create: 2019-07-13 21:29
 **/
@Controller("qualityController")
@RequestMapping("/internal/quality/quality")
public class QualityController extends BaseController {
	@Resource(name = "qualityStandardServiceImpl")
	private QualityStandardService qualityStandardService;
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "qualityStandardDetailServiceImpl")
	private QualityStandardDetailService qualityStandardDetailService;
	@Resource(name = "qualityInspectionServiceImpl")
	private QualityInspectionService qualityInspectionService;
	@Resource(name = "qualityInspectionDetailServiceImpl")
	private QualityInspectionDetailService qualityInspectionDetailService;

	/**
	 * 添加质量标准
	 */
	@RequestMapping(value = "/qualityStandardAdd", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String qualityStandardAdd(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node;
		JSONObject param;
		JSONObject session = new JSONObject();
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			session = node.getJSONObject("session");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
		QualityStandard qualityStandard = new QualityStandard();
		qualityStandard.setInspectionStandardCode(param
				.getString("inspectionStandardCode"));
		qualityStandard.setInspectionStandardName(param
				.getString("inspectionStandardName"));
		qualityStandard.setInspectionType(param.getString("inspectionType"));
		UserSession userSession = super.getSession(session);
		User user = userService.find(Long.valueOf(userSession.getUserId()));
		 qualityStandard.setCreatorName(user.getName());
		qualityStandardService.save(qualityStandard);
		String wodList = param.getString("qualityStandardDetail");
		List<QualityStandardDetail> qualityStandardDetails = com.alibaba.fastjson.JSONObject
				.parseArray(wodList, QualityStandardDetail.class);
		QualityStandard qualityStandard1 = qualityStandardService.getByParam(
				"inspectionStandardCode",
				qualityStandard.getInspectionStandardCode());
		for (QualityStandardDetail qualityStandardDetail : qualityStandardDetails) {
			qualityStandardDetail.setQualityStandard(qualityStandard1);
			qualityStandardDetailService.save(qualityStandardDetail);
		}
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("code", "0");
		jsonReturn.put("msg", "添加质量标准成功");
		return jsonReturn.toString();
	}

	/**
	 * 更新质量标准
	 */
	@RequestMapping(value = "/qualityStandardUpdate", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String qualityStandardUpdate(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
		QualityStandard qualityStandard = new QualityStandard();
		qualityStandard.setId(param.getLong("id"));
		qualityStandard.setInspectionStandardCode(param
				.getString("inspectionStandardCode"));
		qualityStandard.setInspectionStandardName(param
				.getString("inspectionStandardName"));
		qualityStandard.setInspectionType(param.getString("inspectionType"));
		// qualityStandard.setCreatorName(param.getString("creatorName"));
		qualityStandard.setIsDel(param.getBoolean("isDel"));
		qualityStandardService.update(qualityStandard);
		String wodList = param.getString("qualityStandardDetail");
		List<QualityStandardDetail> qualityStandardDetails = com.alibaba.fastjson.JSONObject
				.parseArray(wodList, QualityStandardDetail.class);
		for (QualityStandardDetail qualityStandardDetail : qualityStandardDetails) {
			qualityStandardDetail.setQualityStandard(qualityStandard);
			qualityStandardDetailService.update(qualityStandardDetail);
		}
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("code", "0");
		jsonReturn.put("msg", "更新质量标准成功");
		return jsonReturn.toString();
	}

	/**
	 * 添加质量检验单
	 */
	@RequestMapping(value = "/qualityInspectionAdd", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String qualityInspectionAdd(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node;
		JSONObject param;
		JSONObject session;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
			session = node.getJSONObject("session");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
		UserSession userSession = super.getSession(session);
		User user = userService.find(Long.valueOf(userSession.getUserId()));
		
		QualityInspection qualityInspection = new QualityInspection();
		qualityInspection.setCreatorName(user.getName());
		qualityInspection.setReceiptNumber(param.getString("receiptNumber"));
		qualityInspection.setCreatTime(DatesUtils
				.getStringToday("yyyy-MM-dd HH:mm:ss"));
		String list = param.getString("qualityInspectionDetails");
		qualityInspectionService.save(qualityInspection);
		List<QualityInspectionDetail> qualityInspectionDetails = com.alibaba.fastjson.JSONObject
				.parseArray(list, QualityInspectionDetail.class);
		QualityInspection qualityInspection1 = qualityInspectionService
				.getByParam("receiptNumber", param.getString("receiptNumber"));
		for (QualityInspectionDetail qualityInspectionDetail : qualityInspectionDetails) {
			qualityInspectionDetail.setQualityInspection(qualityInspection1);
			qualityInspectionDetail.setReceiptNumber(param.getString("receiptNumber"));
			String x = com.taojin.iot.base.comm.utils.DatesUtils.getToday();
			qualityInspectionDetail.setCreatTime(x);
			qualityInspectionDetail.setOwnerId(qualityInspectionDetail
					.getQualified()
					/ qualityInspectionDetail.getInspectionCount() * 100L);
			qualityInspectionDetailService.save(qualityInspectionDetail);
		}
		
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("code", "0");
		jsonReturn.put("msg", "添加质量检验单成功");
		return jsonReturn.toString();
	}

	/**
	 * 更新质量检验单
	 */
	@RequestMapping(value = "/qualityInspectionUpdate", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String qualityInspectionUpdate(String requestParams) {
		if (StringUtils.isBlank(requestParams)) {
			return errorMsg("-31", "获取默认参数失败");
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			return successMsg("-1", "参数解析错误!");
		}
		QualityInspection qualityInspection = new QualityInspection();
		qualityInspection.setId(param.getLong("id"));
		qualityInspection.setReceiptNumber(param.getString("receiptNumber"));
		qualityInspection.setIsDel(param.getBoolean("isDel"));
		String list = param.getString("qualityInspectionDetail");
		qualityInspectionService.update(qualityInspection);
		List<QualityInspectionDetail> qualityInspectionDetails = com.alibaba.fastjson.JSONObject
				.parseArray(list, QualityInspectionDetail.class);
		for (QualityInspectionDetail qualityInspectionDetail : qualityInspectionDetails) {
			qualityInspectionDetail.setQualityInspection(qualityInspection);
			qualityInspectionDetailService.update(qualityInspectionDetail);
		}
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("code", "0");
		jsonReturn.put("msg", "更新质量检验单成功");
		return jsonReturn.toString();
	}

	/**
	 * 质量标准列表
	 * 
	 */
	@RequestMapping(value = "/qualityStandardList", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workReportList(String requestParams) {
		Map<String, Object> map = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			map.put("-31", "获取默认参数失败");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "参数解析错误!");
			return JSON.toJSONString(map);
		}
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("isDel", 0));
		if (param.has("startTime") && param.has("endTime")
				&& !param.getString("startTime").equals("")
				&& !param.getString("endTime").equals("")) {
			filters.add(Filter.between("creatTime", param.get("startTime")+" 00:00:00",
					param.get("endTime")+" 23:59:59"));
		}
		if (!param.has("pageNumber")) {
			param.put("pageNumber", 1);
		}

		if (!param.has("pageSize")) {
			param.put("pageSize", 20);
		}
		Pageable pageable = new Pageable(param.getInt("pageNumber"),
				param.getInt("pageSize"));
		if (param.has("inspectionStandardName")&&!param.getString("inspectionStandardName").equals("")) {
			filters.add(Filter.like("inspectionStandardName","%"+param.get("inspectionStandardName")+"%"));
		}
		if (param.has("inspectionType")&&!param.getString("inspectionType").equals("null")) {
			filters.add(Filter.eq("inspectionType", param.get("inspectionType")));
		}
		pageable.setFilters(filters);
		Page<QualityStandard> page = qualityStandardService.findPage(pageable);
		List<QualityStandard> list = page.getContent();
		for (QualityStandard qualityStandard : list) {
			qualityStandard.setCreatTime(qualityStandard.getCreatTime()
					.substring(0, 10));
			Iterator<QualityStandardDetail> it = qualityStandard
					.getQualityStandardDetail().iterator();
			while (it.hasNext()) {
				QualityStandardDetail qualityStandardDetail = it.next();
				if (qualityStandardDetail.getIsDel()) {
					it.remove();
				} else {
					qualityStandardDetail.setQualityStandard(null);
				}
			}
		}
		map.put("code", 0);
		map.put("msg", "质量标准列表获取成功");
		map.put("values", list);
		map.put("page", super.getPage(page));
		return JSON.toJSONString(map);
	}

	/**
	 * 质量标准列表
	 * 
	 */
	@RequestMapping(value = "/qualityStandardListAll", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String workReportListAll(String requestParams) {
		Map<String, Object> map = new HashMap<>(16);

		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("isDel", false));
		List<QualityStandard> list = qualityStandardService.findList(null,
				filters, null);
		for (QualityStandard qualityStandard : list) {
			qualityStandard.setCreatTime(qualityStandard.getCreatTime()
					.substring(0, 10));
			Iterator<QualityStandardDetail> it = qualityStandard
					.getQualityStandardDetail().iterator();
			while (it.hasNext()) {
				QualityStandardDetail qualityStandardDetail = it.next();
				if (qualityStandardDetail.getIsDel()) {
					it.remove();
				} else {
					qualityStandardDetail.setQualityStandard(null);
				}
			}
		}
		map.put("code", 0);
		map.put("msg", "质量标准列表获取成功");
		map.put("values", list);
		return JSON.toJSONString(map);
	}

	/**
	 * 质量检验单列表
	 */
	@RequestMapping(value = "/qualityInspectionList", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String qualityInspectionList(String requestParams) {
		Map<String, Object> map = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			map.put("-31", "获取默认参数失败");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "参数解析错误!");
			return JSON.toJSONString(map);
		}
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("isDel", 0));
		if (param.has("startTime") && param.has("endTime")
				&& !param.getString("startTime").equals("")
				&& !param.getString("endTime").equals("")) {
			filters.add(Filter.between("creatTime", param.get("startTime"),
					param.get("endTime")));
		}
		if (!param.has("pageNumber")) {
			param.put("pageNumber", 1);
		}
		if (!param.has("pageSize")) {
			param.put("pageSize", 20);
		}
		Pageable pageable = new Pageable(param.getInt("pageNumber"),
		param.getInt("pageSize"));
		if (param.has("receiptNumber")
				&& !param.getString("receiptNumber").equals("")) {
			filters.add(Filter.like("receiptNumber", "%"+param.get("receiptNumber")+"%"));
		}
		pageable.setFilters(filters);
		Page<QualityInspection> page = qualityInspectionService
				.findPage(pageable);
		List<QualityInspection> list = page.getContent();
		
		
		for (QualityInspection qualityInspection : list) {
	/*		List<Filter> filters2 = new ArrayList<>();
			filters2.add(Filter.eq("qualityInspection", qualityInspection.getId()));
			List<QualityInspectionDetail> list2 = qualityInspectionDetailService.findList(null, filters2, null);		
			System.out.println(JSON.toJSONString(list2));
			qualityInspection.setQualityInspectionDetail(list2);*/
			Iterator<QualityInspectionDetail> it = qualityInspection
					.getQualityInspectionDetail().iterator();
			while (it.hasNext()) {
				QualityInspectionDetail qualityInspectionDetail = it.next();
				if (qualityInspectionDetail.getIsDel()) {
					it.remove();
				} else {
					qualityInspectionDetail.setQualityInspection(null);
				}
			}
		}
		System.out.println(JSON.toJSONString(list));
		map.put("code", 0);
		map.put("msg", "质量检验单列表获取成功");
		map.put("values", list);
		map.put("page", super.getPage(page));
		return JSON.toJSONString(map);
	}

	/**
	 * 质量检验报表
	 */
	@RequestMapping(value = "/qualityInspectionReport", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String qualityInspectionReport(String requestParams) {
		Map<String, Object> map = new HashMap<>(16);
		if (StringUtils.isBlank(requestParams)) {
			map.put("-31", "获取默认参数失败");
			return JSON.toJSONString(map);
		}
		JSONObject node;
		JSONObject param;
		try {
			node = JSONObject.fromObject(requestParams);
			param = node.getJSONObject("param");
		} catch (Exception e) {
			map.put("-1", "参数解析错误!");
			return JSON.toJSONString(map);
		}
		List<Filter> filters = new ArrayList<>();
		filters.add(Filter.eq("isDel", 0));
		if (param.has("startTime") && param.has("endTime")) {
			filters.add(Filter.between_two("createDate", param.get("startTime"),param.get("endTime")));
		} else {
			String startTime = null;
			String endTime = null;
			if (param.has("flag")) {
				if (param.get("flag").equals("y")) {
					startTime = DatesUtils.getYearStart();
					endTime = DatesUtils.getYearEnd();
				} else if (param.get("flag").equals("m")) {
					startTime = DatesUtils.getMonthStart();
					endTime = DatesUtils.getMonthEnd();
				} else if (param.get("flag").equals("q")) {
					startTime = DatesUtils.getCurrQuarter(DatesUtils
							.getQuarter())[0];
					endTime = DatesUtils
							.getCurrQuarter(DatesUtils.getQuarter())[1];
				} else if (param.get("flag").equals("w")) {
					startTime = DatesUtils.getWeekStart();
					endTime = DatesUtils.getWeekEnd();
				} else if (param.get("flag").equals("d")) {
					startTime = DatesUtils.getToday();
					endTime = DatesUtils.getToday();
				}
				filters.add(Filter.between_two("creatTime", startTime, endTime));
			}
		}
		if (param.has("materialName")
				&& !"".equals(param.getString("materialName"))) {
			filters.add(Filter.like("materialName", "%"+param.get("materialName")+"%"));
		}
		if (param.has("inspectionType")
				&& !"".equals(param.getString("inspectionType"))) {
			filters.add(Filter.eq("inspectionType", param.get("inspectionType")));
		}
		List<QualityInspectionDetail> list = qualityInspectionDetailService.findList(null, filters, null);
		Iterator<QualityInspectionDetail> it = list.iterator();
		while (it.hasNext()) {
			QualityInspectionDetail qualityInspectionDetail = it.next();
			qualityInspectionDetail.setQualityInspection(null);
			qualityInspectionDetail.setCreatTime(qualityInspectionDetail
					.getCreatTime().substring(0, 10));
		}
		map.put("code", 0);
		map.put("msg", "质量报表获取成功");
		map.put("values", list);
		return JSON.toJSONString(map);
	}
}
