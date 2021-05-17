package com.taojin.iot.agreement.fujiya.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.taojin.iot.agreement.fujiya.entity.AgreementRc701;
import com.taojin.iot.agreement.fujiya.enums.AgreementFujiyaEnum;
import com.taojin.iot.agreement.fujiya.service.AgreementFujiyaHandlerService;
import com.taojin.iot.agreement.fujiya.service.AgreementRc701Service;
import com.taojin.iot.agreement.fujiya.service.AgreementRc701ValueService;
import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.utils.SpringUtils;
import com.taojin.iot.redis.JedisUtil;
import com.taojin.iot.redis.JedisUtil.Hash;

/**
 * Service-指令控制-佛吉亚
 * 
 * @author wangjie
 *
 */
@Service("agreementFujiyaHandlerServiceImpl")
public class AgreementFujiyaHandlerServiceImpl implements
		AgreementFujiyaHandlerService {

	private static Logger logger = LoggerFactory
			.getLogger(AgreementFujiyaHandlerServiceImpl.class);

	@Resource
	private AgreementRc701Service agreementRc701Service;
	@Resource
	private TaskExecutor taskExecutor;
	
	/**
	 * 重置dtu状态
	 * @param agreementEnum
	 */
	public void resetDtuState(AgreementFujiyaEnum agreementEnum){
		String type = agreementEnum.name();
		JedisUtil jedisUtil = new JedisUtil();
		Hash hash = jedisUtil.HASH;
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
	

	public Boolean collectSend(AgreementFujiyaEnum agreementEnum,
			String iccid) {
		if (StringUtils.isBlank(iccid)) {
			resetDtuState(agreementEnum);
			return false;
		}

		JedisUtil jedisUtil = new JedisUtil();
		Hash hash = jedisUtil.HASH;
		String state = hash.hget("taskLock", iccid);//获取当前采集任务锁
		if (StringUtils.equalsIgnoreCase(state, "1")) {// 执行中
			logger.info("[任务执行]---->队列繁忙，等待执行,产线={}",agreementEnum.name());
//			resetDtuState(agreementEnum);
			return false;
		} else {// 空闲
			hash.hset("taskLock", iccid, "0");//解锁当前采集任务
		}
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(Filter.eq("type", agreementEnum.ordinal()));
		String sessionId = hash.hget("equipment_session_equipmentId",iccid);//根据设备ID获取当前会话ID
		List<AgreementRc701> agreementRc701s = agreementRc701Service.findList(null, filters, null);
		String taskId = "equipment_rc7_"+sessionId;
		for (int i = 0; i < agreementRc701s.size(); i++) {
			AgreementRc701 rc701 = agreementRc701s.get(i);
			hash.hset(taskId, rc701.getAddress(), rc701.getCommandValue());
		}
		addTask(iccid, taskId,agreementEnum.name());
		return false;
	}

	/**
	 * 添加异步处理任务
	 * 
	 * @param equipmentId
	 *            设备ID
	 * @param taskId
	 *            任务ID
	 */
	public void addTask(final String iccid, final String taskId,final String type) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				JedisUtil jedisUtil = new JedisUtil();
				Hash hash = jedisUtil.HASH;
				try{
					hash.hset("taskLock", iccid, "1");//锁定当前采集任务
					String sessionId = hash.hget("equipment_session_equipmentId",
							iccid);//根据设备ID获取当前会话ID
					Map<String, String> agreementsMap = hash.hgetAll(taskId);
					AgreementRc701ValueService agreementRc701ValueService = (AgreementRc701ValueService) SpringUtils.getBean("agreementRc701ValueServiceImpl");
					for (Map.Entry<String, String> agreement : agreementsMap
							.entrySet()) {
						
						String state = hash.hget("equipment_state", iccid);
						if(StringUtils.equalsIgnoreCase(state, "0") 
								|| StringUtils.isBlank(state) 
								|| StringUtils.isBlank(sessionId)){
//							logger.info("[指令任务]---->失败,设备掉线,退出任务下发key={},发送键名={},发送内容={},产线={}",
//									taskId, agreement.getKey(),
//									agreement.getValue(),type);
							break;
						}
						try {
							try {
								String msg = StringUtils.replace(agreement.getValue(), " ", "");
								Object obj = null;
								if(StringUtils.equalsIgnoreCase(type, "RCTEST")){
									obj = SpringUtils.getBean("tCPNewHandler");
								}else{
									obj = SpringUtils.getBean("tCPHandler");
								}
								boolean isSend = (boolean) obj.getClass()
										.getDeclaredMethod("sendMsgByField",String.class,String.class,String.class)
										.invoke(obj, sessionId, agreement.getKey().toString(), msg);
//								logger.info("[指令任务]---->开始下发key={},发送键名={},发送内容={},产线={},下发状态={}",
//										taskId, agreement.getKey(),
//										agreement.getValue(),type,isSend);
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							Thread.sleep(5000);// 等待5秒

							// 获取当前接收状态
							String rst = hash.hget(sessionId + "_done",
									agreement.getKey());
							if (StringUtils.isNotBlank(rst)) {// 接收到
//								logger.info(
//										"[指令任务]---->等待5秒结束,收到数据回复,key={},发送键名={},接收内容={},产线={}",
//										sessionId, agreement.getKey(), rst,type);
								hash.hdel(sessionId+"_done",agreement.getKey());
								
								try{
									//记录设备状态
									String[] agreements = agreement.getKey().split("\\.");
									agreementRc701ValueService.addDtuData(iccid, "DTU."+agreements[0]+".8", "1", type);//初始化设备状态
									agreementRc701ValueService.runTime(iccid, "DTU."+agreements[0]+".4", type);//添加运行时长
								}catch(Exception e){
									e.printStackTrace();
									logger.info("[指令任务]---->更新DTU运行时长失败，产线={},异常,原因={}",type,e.getMessage());
								}
								
							} else {// 未接收到,跳过，发送下一个
//								logger.info(
//										"[指令任务]---->等5秒结束,未收到任何响应,key={},发送键名={},发送内容={},产线={}",
//										taskId, agreement.getKey(),
//										agreement.getValue(),type);
								
								try{
									String[] agreements = agreement.getKey().split("\\.");
									//未采集到，记录停机时长、故障时长、空转时长
									agreementRc701ValueService.addFailTime(type, "DTU."+agreements[0]+".0", 5*1000, iccid);
									agreementRc701ValueService.addFailTime(type, "DTU."+agreements[0]+".2", 5*1000, iccid);
									agreementRc701ValueService.addFailTime(type, "DTU."+agreements[0]+".6", 5*1000, iccid);
								}catch(Exception e){
									e.printStackTrace();
									logger.info("[指令任务]---->更新DTU时长失败，产线={},异常,原因={}",type,e.getMessage());
								}
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
							logger.info("[指令任务]---->产线={},异常,原因={}",type,e.getMessage());
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					// 清除队列
					hash.hdel(taskId);
					hash.hset("taskLock", iccid, "0");//解锁当前采集任务
					if(StringUtils.equalsIgnoreCase(type, "RC701")){
						hash.hset("iot_dtu_state", "rc701", "0");
						logger.info("[指令任务]---->产线={},抓取完成",type);
					}else if(StringUtils.equalsIgnoreCase(type, "RC771")){
						hash.hset("iot_dtu_state", "RC771", "0");
						logger.info("[指令任务]---->产线={},抓取完成",type);
					}else if(StringUtils.equalsIgnoreCase(type, "EPUMPMAIN")){
						hash.hset("iot_dtu_state", "EPUMPMAIN", "0");
						logger.info("[指令任务]---->产线={},抓取完成",type);
					}else if(StringUtils.equalsIgnoreCase(type, "EPUMPGEAR")){
						hash.hset("iot_dtu_state", "EPUMPGEAR", "0");
						logger.info("[指令任务]---->产线={},抓取完成",type);
					}else if(StringUtils.equalsIgnoreCase(type, "RCTEST")){
						hash.hset("iot_dtu_state", "RC-Test", "0");
						logger.info("[指令任务]---->产线={},抓取完成",type);
					}
				}
			}
		});
	}

}
