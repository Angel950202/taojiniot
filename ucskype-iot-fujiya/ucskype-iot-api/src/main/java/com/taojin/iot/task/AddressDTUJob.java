package com.taojin.iot.task;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.taojin.iot.agreement.fujiya.enums.AddressTypeEnum;
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.redis.JedisUtil;
import com.taojin.iot.redis.JedisUtil.Hash;
import com.taojin.iot.service.task.entity.WorkFinish;
import com.taojin.iot.service.task.service.WorkFinishService;

@Component("addressDTUJob")
@Lazy(false)
public class AddressDTUJob {
	private static Logger logger = LoggerFactory
			.getLogger(AddressDTUJob.class);
	public static final String DTUVALUES = "DTU_VALUES";
	public static final String STOPTIME = "StopTime";
	
	@Resource(name ="workFinishServiceImpl")
	private WorkFinishService workFinishService;
	
	/**
	 * 定时处理指令部分
	 */
	@Scheduled(cron = "0 5 7 * * ?")
//	@Scheduled(cron = "0 5 15 * * ?")
	public void evictExpired() {
		System.out.println("==========================");
		JedisUtil jedisUtil = new JedisUtil();
		Hash hash = jedisUtil.HASH;
		Map<String, String> map =hash.hgetAll(DTUVALUES);
		//获取昨天
		String dateTime = DatesUtils.getYestoday();
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String [] fileds = key.split("_");
			String value = map.get(key);
			if(StringUtils.isNotBlank(value)){
				JSONObject jsonObject = JSONObject.fromObject(value);
				WorkFinish workFinish = new WorkFinish();
				workFinish.setAddress(fileds[0]);
				workFinish.setLineNumber(fileds[1]);
				workFinish.setValue(jsonObject.optLong("value")+jsonObject.optLong("totalValue"));
				workFinish.setCount(jsonObject.optLong("count"));
				workFinish.setAddressType(AddressTypeEnum.valueOf(jsonObject.optString("addressType")));
				
				if(AddressTypeEnum.POLICE == AddressTypeEnum.valueOf(jsonObject.optString("addressType")) || 
						AddressTypeEnum.STOP == AddressTypeEnum.valueOf(jsonObject.optString("addressType"))){
					if(workFinish.getCount() == 0){
						continue;
					}
					workFinish.setTimeLong(jsonObject.optLong("timeLong"));
				}else{
					//根据开始日期计算时长
					workFinish.setTimeLong(86400l);
				}
				workFinish.setDateTime(dateTime);
				workFinishService.save(workFinish);
			}
		}
		hash.hdel(DTUVALUES);
		System.out.println("删除addressDTU缓存数据...");
		logger.info("删除addressDTU缓存数据...");
	}
}
