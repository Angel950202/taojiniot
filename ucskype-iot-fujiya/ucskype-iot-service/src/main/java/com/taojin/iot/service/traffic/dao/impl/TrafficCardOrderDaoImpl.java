package com.taojin.iot.service.traffic.dao.impl;

import org.springframework.stereotype.Repository;

import com.taojin.iot.base.comm.dao.impl.BaseDaoImpl;
import com.taojin.iot.service.traffic.dao.TrafficCardOrderDao;
import com.taojin.iot.service.traffic.entity.TrafficCardOrder;

/**
 * Dao-购卡订单
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午9:00:16
 * author 王杰
 * ============================================================================
 */
@Repository("trafficCardOrderDaoImpl")
public class TrafficCardOrderDaoImpl extends BaseDaoImpl<TrafficCardOrder,Long> implements TrafficCardOrderDao{

}
