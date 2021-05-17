package com.taojin.iot.service.task.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.task.dao.WorkTraceDetailDao;
import com.taojin.iot.service.task.entity.WorkTraceDetail;
import com.taojin.iot.service.task.service.WorkTraceDetailService;

@Service("workTraceDetailServiceImpl")
public class WorkTraceDetailServiceImpl extends BaseServiceImpl<WorkTraceDetail,Long> implements WorkTraceDetailService {

	@Resource(name = "workTraceDetailDaoImpl")
	private WorkTraceDetailDao workTraceDetailDao;
	
	@Resource(name = "workTraceDetailDaoImpl")
	public void setBaseDao(WorkTraceDetailDao workTraceDetailDao) {
		super.setBaseDao(workTraceDetailDao);
	}
}
