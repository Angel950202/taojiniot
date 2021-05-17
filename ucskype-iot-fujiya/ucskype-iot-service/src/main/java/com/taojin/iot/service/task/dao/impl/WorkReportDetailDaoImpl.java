package com.taojin.iot.service.task.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.taojin.iot.base.comm.dao.impl.BaseDaoImpl;
import com.taojin.iot.service.task.dao.WorkReportDetailDao;
import com.taojin.iot.service.task.entity.WorkReportDetail;

@Repository("workReportDetailDaoImpl")
public class WorkReportDetailDaoImpl extends
		BaseDaoImpl<WorkReportDetail, Long> implements WorkReportDetailDao {

	@SuppressWarnings("unchecked")
	public HashMap<String, List<String>> findMaterial() {
		HashMap<String, List<String>> map = new HashMap<>();
		List<String> list = new ArrayList<>();
		List<String> list1 = new ArrayList<>();
		List<String> list2 = new ArrayList<>();
		List<List<Integer>> list3 = new ArrayList<>();
		
		List<String> workshops = new ArrayList<>();
		workshops.add("车间一");
		workshops.add("车间二");		
		for(String workshop:workshops){
		Query query = entityManager
				.createNativeQuery("SELECT material_code FROM `iot_work_report_detail` JOIN `iot_work_report` where iot_work_report.workshop = '"+workshop+"' GROUP BY material_code;");	
		list = (List<String>) query.getResultList();
		StringBuffer sql = new StringBuffer("select sum(planned_production),SUM(report_count),SUM(qualified_count),SUM(scrap_count) from iot_work_report_detail WHERE material_code = ");
			for (String str : list1) {
				sql.append("'").append(str).append("'");
				Query query2 = entityManager.createNativeQuery(sql.toString());
				System.out.println(JSON.toJSONString(query2.getResultList()));
				// list3.add(list2);
			
			}
	}
		return map;

	}
}
