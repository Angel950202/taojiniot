package com.taojin.iot.agreement.fujiya.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taojin.iot.agreement.fujiya.dao.AgreementRc701Dao;
import com.taojin.iot.agreement.fujiya.entity.AgreementRc701;
import com.taojin.iot.agreement.fujiya.entity.AgreementRc701Value;
import com.taojin.iot.agreement.fujiya.enums.AgreementFujiyaEnum;
import com.taojin.iot.agreement.fujiya.service.AgreementFujiyaHandlerService;
import com.taojin.iot.agreement.fujiya.service.AgreementRc701Service;
import com.taojin.iot.agreement.fujiya.service.AgreementRc701ValueService;
import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.base.comm.utils.SpringUtils;
import com.taojin.iot.redis.JedisUtil;
import com.taojin.iot.redis.JedisUtil.Hash;

/**
 * Service-指令-rc701
 * 
 * @author wangjie
 *
 */
@Service("agreementRc701ServiceImpl")
public class AgreementRc701ServiceImpl extends
		BaseServiceImpl<AgreementRc701, Long> implements AgreementRc701Service {
	private static Logger logger = LoggerFactory
			.getLogger(AgreementRc701ServiceImpl.class);
	@Resource(name = "agreementRc701DaoImpl")
	private AgreementRc701Dao agreementRc701Dao;
	@Resource
	private AgreementFujiyaHandlerService agreementFujiyaHandlerService;

	@Resource(name = "agreementRc701DaoImpl")
	public void setBaseDao(AgreementRc701Dao agreementRc701Dao) {
		super.setBaseDao(agreementRc701Dao);
	}

	/**
	 * 执行定时抄读PLC
	 */
	@Override
	public void readByTimer(AgreementFujiyaEnum agreementType) {
		JedisUtil jedisUtil = new JedisUtil();
		Hash hash = jedisUtil.HASH;
		Map<String, String> keysMap = hash
				.hgetAll("equipment_session_equipmentId");
		AgreementRc701ValueService agreementRc701ValueService = (AgreementRc701ValueService) SpringUtils
				.getBean("agreementRc701ValueServiceImpl");
		String type = agreementType.name();
		// 获取当前所有在线的设备
		for (Map.Entry<String, String> entry : keysMap.entrySet()) {
			try {
				System.out.println("设备ID = " + entry.getKey() + ", 会话ID = "
						+ entry.getValue());
				logger.info("[定时抄取]---->设备ID={},会话ID={}", entry.getKey(),
						entry.getValue());
				String iccid = entry.getKey();
				String sessionId = entry.getValue();
				String channelType = hash.hget("equipment_equipment_type", iccid);
				if(!StringUtils.equalsIgnoreCase(channelType, type)){
					continue;
				}
				
				String field = hash.hget("msgSendState", sessionId);// 获取当前会话在执行的指令集
				if(StringUtils.isNotBlank(field)){
					hash.hdel(sessionId + "_done", field);// 更新当前任务完成情况
				}
				if (StringUtils.equalsIgnoreCase(iccid,
						"3839383630343032313031383430313634303334")) {// E-pump2.0
					hash.hset("iot_dtu_state", "EPUMPMAIN", "1");													// Main生产线
					agreementFujiyaHandlerService.collectSend(
							agreementType.EPUMPMAIN, iccid);
				} else if (StringUtils.equalsIgnoreCase(iccid,
						"3839383630343032313031383430313631323436")) {// EPUMP-2Gearless
					hash.hset("iot_dtu_state", "EPUMPGEAR", "1");
					agreementFujiyaHandlerService.collectSend(
							agreementType.EPUMPGEAR, iccid);
				} else if (StringUtils.equalsIgnoreCase(iccid,
						"3839383630343032313031383430313631343531")) {// RC70-1
					hash.hset("iot_dtu_state", "rc701", "1");
					agreementFujiyaHandlerService.collectSend(
							agreementType.RC701, iccid);
				} else if (StringUtils.equalsIgnoreCase(iccid,
						"3839383630343031313031373930313735363230")) {// RC77-1
					hash.hset("iot_dtu_state", "RC771", "1");
					agreementFujiyaHandlerService.collectSend(
							agreementType.RC771, iccid);
				} else if (StringUtils.equalsIgnoreCase(iccid,
						"285414434E52303531FFD605")) {// RC-Test
					hash.hset("iot_dtu_state", "RC-Test", "1");
						agreementFujiyaHandlerService.collectSend(
								agreementType.RCTEST, iccid);
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.info("[指令任务]---->产线={},定时任务出错,异常原因={}", type,e.getMessage());
				if (StringUtils.equalsIgnoreCase(type, "RC701")) {
					hash.hset("iot_dtu_state", "rc701", "0");
					logger.info("[指令任务]---->产线={},抓取完成", type);
				} else if (StringUtils.equalsIgnoreCase(type, "RC771")) {
					hash.hset("iot_dtu_state", "RC771", "0");
					logger.info("[指令任务]---->产线={},抓取完成", type);
				} else if (StringUtils.equalsIgnoreCase(type, "EPUMPMAIN")) {
					hash.hset("iot_dtu_state", "EPUMPMAIN", "0");
					logger.info("[指令任务]---->产线={},抓取完成", type);
				} else if (StringUtils.equalsIgnoreCase(type, "EPUMPGEAR")) {
					hash.hset("iot_dtu_state", "EPUMPGEAR", "0");
					logger.info("[指令任务]---->产线={},抓取完成", type);
				} else if (StringUtils.equalsIgnoreCase(type, "RCTEST")) {
					hash.hset("iot_dtu_state", "RC-Test", "0");
					logger.info("[指令任务]---->产线={},抓取完成", type);
				}
			}
		}
		
		try{
			if (keysMap.size() == 0) {
				if (StringUtils.equalsIgnoreCase(type, "RC701")) {
					hash.hset("iot_dtu_state", "rc701", "0");
					logger.info("[指令任务]---->产线={},抓取完成", type);
				} else if (StringUtils.equalsIgnoreCase(type, "RC771")) {
					hash.hset("iot_dtu_state", "RC771", "0");
					logger.info("[指令任务]---->产线={},抓取完成", type);
				} else if (StringUtils.equalsIgnoreCase(type, "EPUMPMAIN")) {
					hash.hset("iot_dtu_state", "EPUMPMAIN", "0");
					logger.info("[指令任务]---->产线={},抓取完成", type);
				} else if (StringUtils.equalsIgnoreCase(type, "EPUMPGEAR")) {
					hash.hset("iot_dtu_state", "EPUMPGEAR", "0");
					logger.info("[指令任务]---->产线={},抓取完成", type);
				} else if (StringUtils.equalsIgnoreCase(type, "RCTEST")) {
					hash.hset("iot_dtu_state", "RC-Test", "0");
					logger.info("[指令任务]---->产线={},抓取完成", type);
				}
				List<Filter> filters = new ArrayList<Filter>();
				filters.add(Filter.like("address", "DTU%"));
				filters.add(Filter.eq("dateTime",
						DatesUtils.getStringToday("yyyy-MM-dd")));
				List<AgreementRc701Value> values = agreementRc701ValueService
						.findList(null, filters, null);
				for (int i = 0; i < values.size(); i++) {
					String address = values.get(i).getAddress();
					if (StringUtils.startsWithIgnoreCase(address, "DTU")
							&& StringUtils.endsWithIgnoreCase(address, "0")) {
						String[] agreements = address.split("\\.");
						// 未采集到，记录停机时长、故障时长、空转时长
						agreementRc701ValueService.addFailTime(type, "DTU."
								+ agreements[0] + ".0", 60 * 1000, values
								.get(i).getIccid());
					} else if (StringUtils.startsWithIgnoreCase(address,
							"DTU")
							&& StringUtils.endsWithIgnoreCase(address, "2")) {
						String[] agreements = address.split("\\.");
						agreementRc701ValueService.addFailTime(type, "DTU."
								+ agreements[0] + ".2", 60 * 1000, values
								.get(i).getIccid());
					} else if (StringUtils.startsWithIgnoreCase(address,
							"DTU")
							&& StringUtils.endsWithIgnoreCase(address, "8")) {
						String[] agreements = address.split("\\.");
						agreementRc701ValueService.addDtuData(values.get(i)
								.getIccid(), "DTU." + agreements[0] + ".8",
								"0", type);// 初始化设备状态
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			if (StringUtils.equalsIgnoreCase(type, "RC701")) {
				hash.hset("iot_dtu_state", "rc701", "0");
				logger.info("[指令任务]---->产线={},抓取完成", type);
			} else if (StringUtils.equalsIgnoreCase(type, "RC771")) {
				hash.hset("iot_dtu_state", "RC771", "0");
				logger.info("[指令任务]---->产线={},抓取完成", type);
			} else if (StringUtils.equalsIgnoreCase(type, "EPUMPMAIN")) {
				hash.hset("iot_dtu_state", "EPUMPMAIN", "0");
				logger.info("[指令任务]---->产线={},抓取完成", type);
			} else if (StringUtils.equalsIgnoreCase(type, "EPUMPGEAR")) {
				hash.hset("iot_dtu_state", "EPUMPGEAR", "0");
				logger.info("[指令任务]---->产线={},抓取完成", type);
			} else if (StringUtils.equalsIgnoreCase(type, "RCTEST")) {
				hash.hset("iot_dtu_state", "RC-Test", "0");
				logger.info("[指令任务]---->产线={},抓取完成", type);
			}
		}
	}
}
