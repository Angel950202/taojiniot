package com.taojin.iot.service.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.user.dao.UserContactDao;
import com.taojin.iot.service.user.entity.UserContact;
import com.taojin.iot.service.user.service.UserContactService;

/**
 * Service实现-用户联系人
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午9:14:13
 * author 王杰
 * ============================================================================
 */
@Service("userContactServiceImpl")
public class UserContactServiceImpl extends BaseServiceImpl<UserContact,Long> implements UserContactService{
	@Resource(name = "userContactDaoImpl")
	private UserContactDao userContactDao;

	@Resource(name = "userContactDaoImpl")
	public void setBaseDao(UserContactDao userContactDao) {
		super.setBaseDao(userContactDao);
	}
}
