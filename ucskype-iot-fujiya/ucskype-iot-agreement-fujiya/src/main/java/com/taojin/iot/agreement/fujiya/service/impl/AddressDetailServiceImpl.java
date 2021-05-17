package com.taojin.iot.agreement.fujiya.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taojin.iot.agreement.fujiya.dao.AddressDetailDao;
import com.taojin.iot.agreement.fujiya.entity.AddressDetail;
import com.taojin.iot.agreement.fujiya.service.AddressDetailService;
import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;

/**
 * Service-信号位
 * 
 * @author bjt
 *
 */
@Service("addressDetailServiceImpl")
public class AddressDetailServiceImpl extends
		BaseServiceImpl<AddressDetail, Long> implements AddressDetailService {
	private static Logger logger = LoggerFactory
			.getLogger(AddressDetailServiceImpl.class);
	
	
	@Resource(name = "addressDetailDaoImpl")
	private AddressDetailDao addressDetailDao;
	
	@Resource(name = "addressDetailDaoImpl")
	public void setBaseDao(AddressDetailDao addressDetailDao) {
		super.setBaseDao(addressDetailDao);
	}
}
