package com.taojin.iot.service.task.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.task.dao.WorkFinishDao;
import com.taojin.iot.service.task.entity.WorkFinish;
import com.taojin.iot.service.task.service.WorkFinishService;

@Service("workFinishServiceImpl")
public class WorkFinishServiceImpl extends BaseServiceImpl<WorkFinish, Long>
		implements WorkFinishService {
	@Resource(name = "workFinishDaoImpl")
	private WorkFinishDao workFinishDao;

	@Resource(name = "workFinishDaoImpl")
	public void setBaseDao(WorkFinishDao workFinishDao) {
		super.setBaseDao(workFinishDao);
	}
}
