package com.taojin.iot.service.traffic.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.traffic.dao.TrafficCardOrderDao;
import com.taojin.iot.service.traffic.entity.TrafficCardOrder;
import com.taojin.iot.service.traffic.service.TrafficCardOrderService;

/**
 * Service-购卡订单
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午9:03:13
 * author 王杰
 * ============================================================================
 */
@Service("trafficCardOrderServiceImpl")
public class TrafficCardOrderServiceImpl extends BaseServiceImpl<TrafficCardOrder,Long> implements TrafficCardOrderService{
	@Resource(name = "trafficCardOrderDaoImpl")
	private TrafficCardOrderDao trafficCardOrderDao;

	@Resource(name = "trafficCardOrderDaoImpl")
	public void setBaseDao(TrafficCardOrderDao trafficCardOrderDao) {
		super.setBaseDao(trafficCardOrderDao);
	}
}
