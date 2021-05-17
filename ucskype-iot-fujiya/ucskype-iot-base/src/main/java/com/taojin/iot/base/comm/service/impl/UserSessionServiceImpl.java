package com.taojin.iot.base.comm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.dao.UserSessionDao;
import com.taojin.iot.base.comm.entity.UserSession;
import com.taojin.iot.base.comm.service.UserSessionService;

/**
 * Service接口实现-用户会话 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午2:30:11 author 王杰
 * ============================================================================
 */
@Service("userSessionServiceImpl")
public class UserSessionServiceImpl extends BaseServiceImpl<UserSession, Long>
		implements UserSessionService {
	@Resource(name = "userSessionDaoImpl")
	private UserSessionDao userSessionDao;

	@Resource(name = "userSessionDaoImpl")
	public void setBaseDao(UserSessionDao userSessionDao) {
		super.setBaseDao(userSessionDao);
	}
	
	
	
}
