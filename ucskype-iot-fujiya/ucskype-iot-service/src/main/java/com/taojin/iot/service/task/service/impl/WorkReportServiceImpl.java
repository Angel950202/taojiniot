package com.taojin.iot.service.task.service.impl;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.task.dao.impl.WorkReportDaoImpl;
import com.taojin.iot.service.task.entity.WorkReport;
import com.taojin.iot.service.task.service.WorkReportService;




@Service("workReportServiceImpl")
public class WorkReportServiceImpl extends BaseServiceImpl<WorkReport,Long>  implements WorkReportService{
@Resource(name = "workReportDaoImpl")
private WorkReportDaoImpl workReportDaoImpl;

@Resource(name = "workReportDaoImpl")
public void setBaseDao(WorkReportDaoImpl workReportDaoImpl) {
	super.setBaseDao(workReportDaoImpl);
}


	
}
