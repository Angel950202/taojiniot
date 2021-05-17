package com.taojin.iot.agreement.fujiya.job;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.taojin.iot.agreement.fujiya.enums.AgreementFujiyaEnum;
import com.taojin.iot.agreement.fujiya.service.AgreementRc701Service;
import com.taojin.iot.base.comm.utils.SpringUtils;
import com.taojin.iot.redis.JedisUtil;
import com.taojin.iot.redis.JedisUtil.Hash;

@Component("readJobGearless")
@Lazy(false)
public class ReadJobGearles {
	private static Logger logger = LoggerFactory
			.getLogger(ReadJobGearles.class);
	public static int jobState = 0;
	
	/**
	 * 定时处理指令部分
	 */
	@Scheduled(cron = "${job.readGearless.cron}")
	public void evictExpired() {
		JedisUtil jedisUtil = new JedisUtil();
		Hash hash = jedisUtil.HASH;
		String jobState = hash.hget("iot_dtu_state", "EPUMPGEAR");
		if(StringUtils.isBlank(jobState)){
			hash.hset("iot_dtu_state", "EPUMPGEAR", "0");
			jobState = "0";
		}
		logger.info("定时抄读检索...jobState={},type={}",jobState,"EPUMP-GearLess");
		if(StringUtils.equalsIgnoreCase(jobState, "1")){
			logger.info("定时抄读检索,跳过,当前任务执行中...jobState={},type",jobState,"EPUMP-GearLess");
			return;
		}
		
		try{
//			hash.hset("iot_dtu_state", "EPUMPGEAR", "1");
			AgreementRc701Service  agreementRc701Service = (AgreementRc701Service) SpringUtils.getBean("agreementRc701ServiceImpl");
			agreementRc701Service.readByTimer(AgreementFujiyaEnum.EPUMPGEAR);
		}catch(Exception e){
			logger.info("定时抄读检索,执行失败,错误原因={},type={}",e.getMessage(),AgreementFujiyaEnum.EPUMPGEAR.getDesc());
			e.printStackTrace();
		}
		logger.info("定时抄读检索完成...type=EPUMP-GearLess");
	}
}
