package com.taojin.iot.service.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.user.dao.UserRoleDao;
import com.taojin.iot.service.user.entity.UserRole;
import com.taojin.iot.service.user.service.UserRoleService;

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
@Service("userRoleServiceImpl")
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole,Long> implements UserRoleService{
	@Resource(name = "userRoleDaoImpl")
	private UserRoleDao userRoleDao;

	@Resource(name = "userRoleDaoImpl")
	public void setBaseDao(UserRoleDao userRoleDao) {
		super.setBaseDao(userRoleDao);
	}
}
