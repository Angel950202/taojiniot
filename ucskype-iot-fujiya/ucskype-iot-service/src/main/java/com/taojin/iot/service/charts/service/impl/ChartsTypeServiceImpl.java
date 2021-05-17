package com.taojin.iot.service.charts.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.charts.dao.ChartsTypeDao;
import com.taojin.iot.service.charts.entity.ChartsType;
import com.taojin.iot.service.charts.service.ChartsTypeService;

/**
 * Service
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 上午10:23:22
 * author 王杰
 * ============================================================================
 */
@Service("chartsTypeServiceImpl")
public class ChartsTypeServiceImpl extends BaseServiceImpl<ChartsType,Long> implements ChartsTypeService{
	@Resource(name = "chartsTypeDaoImpl")
	private ChartsTypeDao chartsTypeDao;

	@Resource(name = "chartsTypeDaoImpl")
	public void setBaseDao(ChartsTypeDao chartsTypeDao) {
		super.setBaseDao(chartsTypeDao);
	}
	
}
