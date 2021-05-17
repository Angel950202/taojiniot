package com.taojin.iot.service.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.user.dao.UserOrderDao;
import com.taojin.iot.service.user.entity.UserOrder;
import com.taojin.iot.service.user.service.UserOrderService;

/**
 * Service-用户订单
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 上午10:06:09
 * author 王杰
 * ============================================================================
 */
@Service("userOrderServiceImpl")
public class UserOrderServiceImpl extends BaseServiceImpl<UserOrder,Long> implements UserOrderService{
	@Resource(name = "userOrderDaoImpl")
	private UserOrderDao userOrderDao;

	@Resource(name = "userOrderDaoImpl")
	public void setBaseDao(UserOrderDao userOrderDao) {
		super.setBaseDao(userOrderDao);
	}
}
