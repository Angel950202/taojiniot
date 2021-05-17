package com.taojin.iot.service.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.user.dao.CompanyProfileDao;
import com.taojin.iot.service.user.entity.CompanyProfile;
import com.taojin.iot.service.user.service.CompanyProfileService;



@Service("companyProfileServiceImpl")
public class CompanyProfileServiceImpl extends BaseServiceImpl<CompanyProfile,Long> implements CompanyProfileService{
	@Resource(name = "companyProfileDaoImpl")
	private CompanyProfileDao companyProfileDao;

	@Resource(name = "companyProfileDaoImpl")
	public void setBaseDao(CompanyProfileDao companyProfileDao) {
		super.setBaseDao(companyProfileDao);
	}
}
