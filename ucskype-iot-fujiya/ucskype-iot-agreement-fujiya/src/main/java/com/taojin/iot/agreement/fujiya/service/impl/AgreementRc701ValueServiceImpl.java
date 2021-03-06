package com.taojin.iot.agreement.fujiya.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taojin.iot.agreement.fujiya.dao.AgreementRc701ValueDao;
import com.taojin.iot.agreement.fujiya.entity.AddressDTU;
import com.taojin.iot.agreement.fujiya.entity.AddressDetail;
import com.taojin.iot.agreement.fujiya.entity.AgreementRc701;
import com.taojin.iot.agreement.fujiya.entity.AgreementRc701Value;
import com.taojin.iot.agreement.fujiya.enums.AddressTypeEnum;
import com.taojin.iot.agreement.fujiya.enums.AgreementFujiyaEnum;
import com.taojin.iot.agreement.fujiya.service.AddressDTUService;
import com.taojin.iot.agreement.fujiya.service.AddressDetailService;
import com.taojin.iot.agreement.fujiya.service.AgreementRc701Service;
import com.taojin.iot.agreement.fujiya.service.AgreementRc701ValueService;
import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.redis.JedisUtil;
import com.taojin.iot.redis.JedisUtil.Hash;

@Service("agreementRc701ValueServiceImpl")
public class AgreementRc701ValueServiceImpl extends
		BaseServiceImpl<AgreementRc701Value, Long> implements
		AgreementRc701ValueService {
	private static Logger logger = LoggerFactory
			.getLogger(AgreementRc701ValueServiceImpl.class);
	@Resource(name = "agreementRc701ValueDaoImpl")
	private AgreementRc701ValueDao agreementRc701ValueDao;
	@Resource(name = "agreementRc701ServiceImpl")
	private AgreementRc701Service agreementRc701Service;
	@Resource(name = "addressDTUServiceImpl")
	private AddressDTUService addressDTUService;
	@Resource(name = "addressDetailServiceImpl")
	private AddressDetailService addressDetailService;
	
	public static final String DTUVALUES = "DTU_VALUES";
	public static final String STOPTIME = "StopTime";

	@Resource(name = "agreementRc701ValueDaoImpl")
	public void setBaseDao(AgreementRc701ValueDao agreementRc701ValueDao) {
		super.setBaseDao(agreementRc701ValueDao);
	}

	@Override
	public void addValue(String sessionId, String field, String value,
			String typename) {
		try {
			JedisUtil jedisUtil = new JedisUtil();
			Hash hash = jedisUtil.HASH;
			String iccid = hash.hget("equipment_session_sessionId",
					sessionId);
			
			AgreementRc701Value rcValue = new AgreementRc701Value();
			Calendar c = Calendar.getInstance();
	        int hour = c.get(Calendar.HOUR_OF_DAY);
	        String dateTime = null;
	        if(hour>=7){
	        	dateTime = DatesUtils.getToday();
	        }else{
	        	dateTime = DatesUtils.getYestoday();
	        }
			List<Filter> filters = new ArrayList<Filter>();
			filters.add(Filter.eq("address", field));
			filters.add(Filter.eq("dateTime",dateTime));
			filters.add(Filter.eq("equipType",
					AgreementFujiyaEnum.valueOf(typename)));
			List<AgreementRc701Value> rcValues = super.findList(null, filters,
					null);
			if (rcValues.size() > 0) {
				rcValue = rcValues.get(0);
//				Integer v1 = Integer.parseInt(value);
//				Integer v2 = rcValue.getCommandValue();
//				Integer v3 = v1 - v2;// ??????
				rcValue.setCommandValue(Integer.parseInt(value));
				rcValue.setHistoryValue(value);
//				if (v3 < 0) {
//					rcValue.setHistoryValue("0");
//				} else {
//					rcValue.setHistoryValue(v3.toString());
//				}
				rcValue.setCommandStr(hash.hget(sessionId + "_done", field));
				rcValue.setModifyDate(new Date());
				super.update(rcValue);
			} else {
				AgreementRc701 agreementRc701  = agreementRc701Service.getByParams("address",field,"type", String.valueOf(AgreementFujiyaEnum.valueOf(typename).ordinal()));
				String filedName = null;
				if(agreementRc701 != null){
					filedName = agreementRc701.getName();
				}
				rcValue.setAddressName(filedName);
				rcValue.setAddress(field);
				rcValue.setIccid(iccid);
				rcValue.setCommandValue(Integer.parseInt(value));
				rcValue.setHistoryValue(value);
				rcValue.setCommandStr(hash.hget(sessionId + "_done", field));
				rcValue.setEquipType(AgreementFujiyaEnum.valueOf(typename));
				rcValue.setDateTime(dateTime);
				super.save(rcValue);
			}
			
			
			if(StringUtils.endsWithIgnoreCase(field, "DBW0")){//????????????
				//???????????????????????????
				this.addFailTimeByProduction(typename, field, iccid, rcValue.getCommandValue(),dateTime);
			}
			/**
			 * 2020.7.7??????????????????????????????????????????
			 * ?????????????????????????????????
			 * 7???????????? DTU_VALUES
			 */
			String agreeType =  AgreementFujiyaEnum.valueOf(typename).name(); 
			if(hash.hexists(DTUVALUES, field+"_"+agreeType)){
				//??????????????????
				AddressDetail detail = new AddressDetail();
				detail.setAddress(field);
				detail.setAgreementFujiya(AgreementFujiyaEnum.valueOf(typename));
				detail.setDateTime(dateTime);
				detail.setValue(value);
				detail.setCommandStr(hash.hget(sessionId + "_done", field));
				addressDetailService.save(detail);
				
				String result = hash.hget(DTUVALUES, field+"_"+agreeType);
				JSONObject jsonObject = JSONObject.fromObject(result);
				AddressDTU addressDTU = addressDTUService.find(jsonObject.optLong("addressId"));
				//??????????????????
				handlerRedisData(hash,addressDTU,jsonObject, field, value,agreeType);
			}else{
				//????????????????????????????????????
				AddressDTU addressDTU = addressDTUService.getByParams("address", field,"agreementFujiya",AgreementFujiyaEnum.valueOf(typename).ordinal());
				if(null != addressDTU){
					//??????????????????
					AddressDetail detail = new AddressDetail();
					detail.setAddress(field);
					detail.setAgreementFujiya(AgreementFujiyaEnum.valueOf(typename));
					detail.setDateTime(dateTime);
					detail.setValue(value);
					detail.setCommandStr(hash.hget(sessionId + "_done", field));
					addressDetailService.save(detail);
					
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("value", value);//?????????
					jsonObject.put("lastDate", DatesUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
					jsonObject.put("startDate", DatesUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
					jsonObject.put("count", 1);//??????
					jsonObject.put("addressId", addressDTU.getId());
					jsonObject.put("addressType", addressDTU.getAddressType().name());
					jsonObject.put("timeLong", 0);//??????
					jsonObject.put("totalValue", 0);//??????
					hash.hset(DTUVALUES, field+"_"+agreeType, jsonObject.toString());
				}
			}
		} catch (Exception e) {
			logger.info("[???????????????]---->??????,??????={}", e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public Double getSum(String nokNum_arr[], Integer line, String dateTime) {
		Integer sum = agreementRc701ValueDao.getSum(nokNum_arr, line, dateTime);
		if (sum == null) {
			sum = 0;
		}
		return Double.parseDouble(sum.toString());
	}

	@Override
	public boolean addDtuData(String iccid, String field, String value,
			String typename) {
		try {
			// ??????????????????
			Calendar c = Calendar.getInstance();
	        int hour = c.get(Calendar.HOUR_OF_DAY);
	        String dateTime = null;
	        if(hour>=7){
	        	dateTime = DatesUtils.getToday();
	        }else{
	        	dateTime = DatesUtils.getYestoday();
	        }
			List<Filter> agreementFilters = new ArrayList<>();
			agreementFilters.add(Filter.eq("address", field));
			agreementFilters.add(Filter.eq("dateTime",
					dateTime));
			agreementFilters.add(Filter.eq("equipType",
					AgreementFujiyaEnum.valueOf(typename)));
			List<AgreementRc701Value> values = super.findList(1,
					agreementFilters, null);
			AgreementRc701Value rcValue = new AgreementRc701Value();
			if (values.size() > 0) {
				rcValue = values.get(0);
				if (StringUtils.isNotBlank(value)) {
					rcValue.setCommandValue(Integer.parseInt(value));
				}
				super.update(rcValue);
			} else {// ??????
				rcValue.setAddress(field);
				rcValue.setIccid(iccid);
				if (StringUtils.isNotBlank(value)) {
					rcValue.setCommandValue(Integer.parseInt(value));
					rcValue.setHistoryValue(value);
				} else {
					rcValue.setCommandValue(0);
					rcValue.setHistoryValue("0");
				}
				rcValue.setCommandStr(null);
				rcValue.setEquipType(AgreementFujiyaEnum.valueOf(typename));
				rcValue.setDateTime(dateTime);
				super.save(rcValue);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addDtuIncrementData(String iccid, String field,
			Integer value, String typename) {
		try {
			Calendar c = Calendar.getInstance();
	        int hour = c.get(Calendar.HOUR_OF_DAY);
	        String dateTime = null;
	        if(hour>=7){
	        	dateTime = DatesUtils.getToday();
	        }else{
	        	dateTime = DatesUtils.getYestoday();
	        }
			// ??????????????????
			List<Filter> agreementFilters = new ArrayList<>();
			agreementFilters.add(Filter.eq("address", field));
			agreementFilters.add(Filter.eq("dateTime",
					dateTime));
			agreementFilters.add(Filter.eq("equipType",
					AgreementFujiyaEnum.valueOf(typename)));
			List<AgreementRc701Value> values = super.findList(1,
					agreementFilters, null);
			AgreementRc701Value rcValue = new AgreementRc701Value();
			if (values.size() > 0) {
				rcValue = values.get(0);
				if (value == null) {
					value = Integer.parseInt(rcValue.getHistoryValue());
				}
				rcValue.setCommandValue(value);// ?????????
				Integer v1 = Integer.parseInt(rcValue.getHistoryValue());// ????????????
				Integer v2 = v1 + value;
				rcValue.setHistoryValue(v2.toString());// ????????????
				super.update(rcValue);
			} else {// ??????
				rcValue.setAddress(field);
				rcValue.setIccid(iccid);
				if (value != null) {
					rcValue.setCommandValue(value);
					rcValue.setHistoryValue(value.toString());
				} else {
					rcValue.setCommandValue(0);
					rcValue.setHistoryValue("0");
				}
				rcValue.setCommandStr(null);
				rcValue.setEquipType(AgreementFujiyaEnum.valueOf(typename));
				rcValue.setDateTime(dateTime);
				super.save(rcValue);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// ????????????
	@Override
	public boolean runTime(String iccid, String field, String typename) {
		try {
			Calendar c = Calendar.getInstance();
	        int hour = c.get(Calendar.HOUR_OF_DAY);
	        String dateTime = null;
	        if(hour>=7){
	        	dateTime = DatesUtils.getToday();
	        }else{
	        	dateTime = DatesUtils.getYestoday();
	        }
			List<Filter> filters = new ArrayList<Filter>();
			filters.add(Filter.eq("address", field));
			filters.add(Filter.eq("dateTime",
					dateTime));
			filters.add(Filter.eq("equipType",
					AgreementFujiyaEnum.valueOf(typename)));
			List<AgreementRc701Value> values = super.findList(1, filters, null);

			AgreementRc701Value value = null;
			if (values.size() > 0) {
				value = values.get(0);
			}

			if (value != null) {
				Date lastTime = DatesUtils.stringToDate(
						dateTime + " 07:00:00",
						"yyyy-MM-dd HH:mm:ss");
				long second = DatesUtils.calLastedTime(lastTime);
				Integer milliseconds = (int) (second * 1000);// ????????????
				Integer v1 = Integer.parseInt(value.getHistoryValue());// ?????????
				Integer v2 = milliseconds - v1;
				value.setCommandValue(v2);// ?????????
				value.setHistoryValue(milliseconds.toString());// ????????????
			} else {// ???????????????
				Date lastTime = DatesUtils.stringToDate(
						dateTime + " 07:00:00",
						"yyyy-MM-dd HH:mm:ss");
				long second = DatesUtils.calLastedTime(lastTime);
				Integer milliseconds = (int) (second * 1000);// ????????????
				value = new AgreementRc701Value();
				value.setIccid(iccid);
				value.setAddress(field);
				value.setCommandValue(milliseconds);
				value.setHistoryValue(milliseconds.toString());
				value.setCommandStr(null);
				value.setEquipType(AgreementFujiyaEnum.valueOf(typename));
				value.setDateTime(dateTime);
			}
			super.update(value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// ????????????
	@Override
	public boolean addFailTime(String typename, String address,
			Integer milliseconds, String iccid) {
		try {
			Calendar c = Calendar.getInstance();
	        int hour = c.get(Calendar.HOUR_OF_DAY);
	        String dateTime = null;
	        if(hour>=7){
	        	dateTime = DatesUtils.getToday();
	        }else{
	        	dateTime = DatesUtils.getYestoday();
	        }
			List<Filter> filters = new ArrayList<Filter>();
			filters.add(Filter.eq("address", address));
			filters.add(Filter.eq("dateTime",
					dateTime));
			filters.add(Filter.eq("equipType",
					AgreementFujiyaEnum.valueOf(typename)));
			List<AgreementRc701Value> values = super.findList(1, filters, null);

			AgreementRc701Value value = null;
			if (values.size() > 0) {
				value = values.get(0);
			}

			if (value != null) {
				if (milliseconds == null) {
					long second = DatesUtils
							.calLastedTime(value.getModifyDate());
					milliseconds = (int) (second * 1000);// ????????????
				}
				value.setCommandValue(milliseconds);// ?????????
				Integer v1 = Integer.parseInt(value.getHistoryValue());// ????????????
				Integer v2 = v1 + milliseconds;
				value.setHistoryValue(v2.toString());// ????????????
			} else {// ???????????????
				if (milliseconds == null) {
					Date lastTime = DatesUtils.stringToDate(
							DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
					long second = DatesUtils.calLastedTime(lastTime);
					milliseconds = (int) (second * 1000);// ????????????
				}
				value = new AgreementRc701Value();
				value.setIccid(iccid);
				value.setAddress(address);
				value.setCommandValue(milliseconds);
				value.setHistoryValue(milliseconds.toString());
				value.setCommandStr(null);
				value.setEquipType(AgreementFujiyaEnum.valueOf(typename));
				value.setDateTime(dateTime);
			}
			super.update(value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void addFailTimeByProduction(String typename, String address,String iccid,Integer production,String dateTime){
		//??????????????????
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(Filter.eq("address", address));
		filters.add(Filter.eq("dateTime",
				dateTime));
		filters.add(Filter.eq("equipType",
				AgreementFujiyaEnum.valueOf(typename)));
		List<AgreementRc701Value> values = super.findList(1, filters, null);

		AgreementRc701Value value = new AgreementRc701Value();
		if (values.size() > 0) {
			value = values.get(0);
			
			if(production - value.getCommandValue() <= 0){//???????????????
				String[] agreements = address.split("\\.");
				//?????????????????????????????????
				this.addFailTime(typename, "DTU."+agreements[0]+".0", null, value.getIccid());
				this.addFailTime(typename, "DTU."+agreements[0]+".6", null, value.getIccid());
			}else{//???????????????
				
			}
			super.update(value);
		}
	}
	/**
	 * ??????????????????
	 * @param field
	 * @param value
	 */
	void handlerRedisData(Hash hash,AddressDTU addressDTU,JSONObject jsonObject,String field,String value,String typename){
		if(null != addressDTU){
			Long val = 0l;
			Integer count = 0;
			Long timeLong = 0l;
			if(addressDTU.getAddressType() == AddressTypeEnum.POLICE){//??????
				val = Long.valueOf(value);
				//????????????????????????
				if(val == 1){
					logger.info(addressDTU+"==========??????");
					String startTime = jsonObject.optString("startDate");
					long stopTime = DatesUtils.calLastedTime(DatesUtils.stringToDate(startTime, "yyyy-MM-dd HH:mm:ss"));
					String lastDate = jsonObject.optString("lastDate");
					//??????????????????30???
					long time = DatesUtils.calLastedTime(DatesUtils.stringToDate(lastDate, "yyyy-MM-dd HH:mm:ss"));
					if(time>30){//??????????????????30???
						logger.info(addressDTU+"=========????????????30???,???????????????1,????????????????????????");
						count = jsonObject.optInt("count")+1;
						timeLong = Long.valueOf(stopTime);
					}
					//??????????????????????????????5??????
					if(stopTime > 300){//??????????????????5??????
						//??????????????????????????????
						logger.info(addressDTU+"=========??????????????????5??????,???????????????1,????????????????????????");
						if(hash.hexists(DTUVALUES, field+"_"+typename+"_"+STOPTIME)){
							String result = hash.hget(DTUVALUES, field+"_"+typename+"_"+STOPTIME);
							handlerRedisStopTime(JSONObject.fromObject(result),stopTime,hash,field,typename);
						}else{
							//??????????????????
							addRedisStopTime(hash,field,typename,addressDTU,stopTime);
						}
					}
				}else{
					//????????????????????????
					logger.info(addressDTU+"=======?????????,??????????????????,??????????????????,????????????");
					jsonObject.put("startDate", DatesUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
					timeLong = jsonObject.optLong("timeLong");
					count = jsonObject.optInt("count");
				}
			}else{
				count = jsonObject.optInt("count")+1;
				if(jsonObject.optLong("value") > Long.valueOf(value)){//???????????????????????????????????????,???????????????
					//???????????????
					Long totalValue = jsonObject.optLong("totalValue")+jsonObject.optLong("value");//???????????????????????????
					jsonObject.put("totalValue", totalValue);
				}
				val = Long.valueOf(value);
			}
			jsonObject.put("value", val);
			jsonObject.put("count", count);
			jsonObject.put("timeLong", timeLong);
			jsonObject.put("lastDate", DatesUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			hash.hset(DTUVALUES, field+"_"+typename, jsonObject.toString());
		}
	}
	/**
	 * ????????????????????????
	 */
	void addRedisStopTime(Hash hash,String field,String typename,AddressDTU addressDTU,Long timeLong){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("value", "1");
		jsonObject.put("lastDate", DatesUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		jsonObject.put("startDate", DatesUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		jsonObject.put("count", 1);//??????
		jsonObject.put("addressId", addressDTU.getId());
		jsonObject.put("addressType", AddressTypeEnum.STOP.name());
		jsonObject.put("timeLong", timeLong);//??????
		hash.hset(DTUVALUES, field+"_"+typename+"_"+STOPTIME, jsonObject.toString());
	}
	/**
	 * ????????????????????????
	 * @param jsonObject
	 */
	void handlerRedisStopTime(JSONObject jsonObject,Long stopTime,Hash hash,String field,String typename){
		jsonObject.put("lastDate", DatesUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Long timeLong = 0l;
		if(jsonObject.optLong("timeLong") > stopTime){
			timeLong = jsonObject.optLong("timeLong")+stopTime;
		}else{
			timeLong = stopTime;
		}
		Long count = jsonObject.optLong("count")+1;
		jsonObject.put("timeLong", timeLong);
		jsonObject.put("count", count);
		hash.hset(DTUVALUES, field+"_"+typename+"_"+STOPTIME, jsonObject.toString());
	}
}
