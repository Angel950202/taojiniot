package com.taojin.iot.service.task.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.taojin.iot.base.comm.dao.impl.BaseDaoImpl;
import com.taojin.iot.service.task.dao.WorkReportDao;
import com.taojin.iot.service.task.entity.WorkReport;

@Repository("workReportDaoImpl")
public class WorkReportDaoImpl extends BaseDaoImpl<WorkReport,Long> implements WorkReportDao{

	public HashMap<String, List<String>> findMaterial() {
		// TODO Auto-generated method stub
		return null;
	}

}
