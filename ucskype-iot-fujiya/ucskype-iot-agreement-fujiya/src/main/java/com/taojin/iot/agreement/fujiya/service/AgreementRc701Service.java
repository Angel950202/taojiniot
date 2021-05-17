package com.taojin.iot.agreement.fujiya.service;

import com.taojin.iot.agreement.fujiya.entity.AgreementRc701;
import com.taojin.iot.agreement.fujiya.enums.AgreementFujiyaEnum;
import com.taojin.iot.base.comm.service.BaseService;

/**
 * Service-指令-rc701
 * @author wangjie
 *
 */
public interface AgreementRc701Service extends BaseService<AgreementRc701,Long>{

	/**
	 * 周期读
	 * @param agreementType 产线类型
	 */
	void readByTimer(AgreementFujiyaEnum agreementType);

}
