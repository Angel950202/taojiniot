package com.taojin.iot.agreement.fujiya.service;

import com.taojin.iot.agreement.fujiya.enums.AgreementFujiyaEnum;

/**
 * Service-指令控制-佛吉亚
 * @author wangjie
 *
 */
public interface AgreementFujiyaHandlerService {

	/**
	 * 批量采集发送
	 * @param agreementEnum 指令种类
	 * @param iccid 设备ICCID
	 * @return true/false
	 */
	public Boolean collectSend(AgreementFujiyaEnum agreementEnum,String iccid) ;
	
}
