package com.taojin.iot.service.report.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.report.dao.ReportDTUDao;
import com.taojin.iot.service.report.entity.ReportDTU;
import com.taojin.iot.service.report.service.ReportDTUService;


/**
 * Service-传感器实时数据
 * @author wangjie
 *
 */
@Service("reportDTUServiceImpl")
public class ReportDTUServiceImpl extends
		BaseServiceImpl<ReportDTU, Long> implements
		ReportDTUService {
	final static Logger logger = LoggerFactory.getLogger(ReportDTUServiceImpl.class);
	
	@Resource(name = "reportDTUDaoImpl")
	private ReportDTUDao reportDTUSensorDao;
	@Resource(name="reportDTUServiceImpl")
	private ReportDTUService reportDTUService;

	@Resource(name = "reportDTUDaoImpl")
	public void setBaseDao(ReportDTUDao reportDTUDao) {
		super.setBaseDao(reportDTUSensorDao);
	}


}