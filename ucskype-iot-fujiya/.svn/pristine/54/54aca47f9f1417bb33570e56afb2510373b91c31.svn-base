package com.taojin.iot.agreement.fujiya.service.impl;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taojin.iot.agreement.fujiya.dao.AddressDTUDao;
import com.taojin.iot.agreement.fujiya.entity.AddressDTU;
import com.taojin.iot.agreement.fujiya.service.AddressDTUService;
import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;

/**
 * Service-信号位
 * 
 * @author bjt
 *
 */
@Service("addressDTUServiceImpl")
public class AddressDTUServiceImpl extends
		BaseServiceImpl<AddressDTU, Long> implements AddressDTUService {
	private static Logger logger = LoggerFactory
			.getLogger(AddressDTUServiceImpl.class);
	@Resource(name = "addressDTUDaoImpl")
	private AddressDTUDao addressDTUDao;
	
	@Resource(name = "addressDTUDaoImpl")
	public void setBaseDao(AddressDTUDao addressDTUDao) {
		super.setBaseDao(addressDTUDao);
	}
}
