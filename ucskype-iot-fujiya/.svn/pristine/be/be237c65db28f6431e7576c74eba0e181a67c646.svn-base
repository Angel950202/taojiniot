package com.taojin.iot.service.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.user.dao.UserDao;
import com.taojin.iot.service.user.entity.User;
import com.taojin.iot.service.user.service.UserService;

/**
 * Service实现-用户
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午9:12:41
 * author 王杰
 * ============================================================================
 */
@Service("userServiceImpl")
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService{
	@Resource(name = "userDaoImpl")
	private UserDao userDao;

	@Resource(name = "userDaoImpl")
	public void setBaseDao(UserDao userDao) {
		super.setBaseDao(userDao);
	}
}
