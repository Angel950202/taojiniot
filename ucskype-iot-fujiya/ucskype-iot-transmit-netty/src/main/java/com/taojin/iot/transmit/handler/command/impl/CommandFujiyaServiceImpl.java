package com.taojin.iot.transmit.handler.command.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.utils.SpringUtils;
import com.taojin.iot.redis.JedisUtil;
import com.taojin.iot.redis.JedisUtil.Hash;
import com.taojin.iot.transmit.handler.command.CommandFujiyaService;
import com.taojin.iot.transmit.utils.ConvertUtil;

@Service("commandFujiyaServiceImpl")
public class CommandFujiyaServiceImpl implements CommandFujiyaService{
	final static Logger logger = LoggerFactory.getLogger(CommandFujiyaServiceImpl.class);
	/**
	 * 解析命令得到整数值 S7-300
	 * @param sessionId 会话ID 
	 * @param field 对应信号位名称
	 * @param commandStr 消息
	 */
	@Override
	public void parseCommandToInt(String sessionId,String field,String commandStr){
		JedisUtil jedisUtil = new JedisUtil();
		Hash hash = jedisUtil.HASH;
		String iccid = hash.hget("equipment_session_sessionId", sessionId);//获取ICCID
		String typename = hash.hget("equipment_equipment_type", iccid);
		commandStr = commandStr.substring(22,commandStr.length() - 6);//获取数据域
//		commandStr = commandStr.substring(10,commandStr.length() - 6);//获取数据域
		
		/**
		 * 数据解析修改
		 * 增加长度52位解析
		 * 增加长度58位解析
		 * 其它长度不解析
		 * @author king
		 * @Date 2019-08-23 10:29
		 */
		String dataArea = null;
		if(commandStr.length() == 52){//长度为52位
			dataArea = commandStr.substring(commandStr.length()-4);
		}
		if(commandStr.length() == 58){//长度为58位
			dataArea = commandStr.substring(50,54);
		}
		if(StringUtils.isBlank(dataArea)){
			dataArea = "00";
		}
		Integer dataValue = ConvertUtil.hex2Int(dataArea);//得到数据
		hash.hset(sessionId+"_parse_done", field, dataValue.toString());//记录解析值
		//记录值到数据库
		
		try {
			Object obj = SpringUtils.getBean("agreementRc701ValueServiceImpl");
			obj.getClass()
					.getDeclaredMethod("addValue",String.class,String.class,String.class,String.class)
					.invoke(obj, sessionId, field, dataValue.toString(),typename);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		hash.hdel(sessionId+"_done",field);
	}
	
	
	/**
	 * 解析命令得到整数值 S7-300(新版DTU)
	 * @param sessionId 会话ID 
	 * @param field 对应信号位名称
	 * @param commandStr 消息
	 */
	@Override
	public void parseCommandToIntNew(String sessionId,String field,String commandStr){
		JedisUtil jedisUtil = new JedisUtil();
		Hash hash = jedisUtil.HASH;
		String iccid = hash.hget("equipment_session_sessionId", sessionId);//获取ICCID
		String typename = hash.hget("equipment_equipment_type", iccid);
//		commandStr = commandStr.substring(22,commandStr.length() - 6);//获取数据域
		commandStr = commandStr.substring(10,commandStr.length() - 6);//获取数据域
		
		/**
		 * 数据解析修改
		 * 增加长度52位解析
		 * 增加长度58位解析
		 * 其它长度不解析
		 * @author king
		 * @Date 2019-08-23 10:29
		 */
		String dataArea = null;
		if(commandStr.length() == 52){//长度为52位
			dataArea = commandStr.substring(commandStr.length()-4);
		}
		if(commandStr.length() == 58){//长度为58位
			dataArea = commandStr.substring(50,54);
		}
		if(StringUtils.isBlank(dataArea)){
			dataArea = "00";
		}
		Integer dataValue = ConvertUtil.hex2Int(dataArea);//得到数据
		hash.hset(sessionId+"_parse_done", field, dataValue.toString());//记录解析值
		//记录值到数据库
		
		try {
			Object obj = SpringUtils.getBean("agreementRc701ValueServiceImpl");
			obj.getClass()
					.getDeclaredMethod("addValue",String.class,String.class,String.class,String.class)
					.invoke(obj, sessionId, field, dataValue.toString(),typename);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		hash.hdel(sessionId+"_done",field);
	}
	
	
	/**
	 * 解析命令得到整数值 S7-300 uLong类型,6个字节为一组，同时解析三个信号位数据 0 ，2，4
	 * @param sessionId 会话ID 
	 * @param field 对应信号位名称
	 * @param commandStr 消息
	 */
	@Override
	public void parseCommandToUlongNew(String sessionId,String field,String commandStr){
		JedisUtil jedisUtil = new JedisUtil();
		Hash hash = jedisUtil.HASH;
		String iccid = hash.hget("equipment_session_sessionId", sessionId);//获取ICCID
		String typename = hash.hget("equipment_equipment_type", iccid);
//		commandStr = commandStr.substring(22,commandStr.length() - 6);//获取数据域
		commandStr = commandStr.substring(10,commandStr.length() - 6);//获取数据域
		
		String ulongData = commandStr.substring(commandStr.length() - 12);//DB0,DB2,DB4 共6个字节，2个字节代表一个信号位
		String dbw0 = null;
		String dbw2 = null;
		String dbw4 = null;
		Integer dbw0Value = 0;
		Integer dbw2Value = 0;
		Integer dbw4Value = 0;
		
		if(field.contains("DBX")){
			//转成2进制
			ulongData = commandStr.substring(commandStr.length() - 2);
			String dbw = ulongData.substring(0,2);//db0信号位
			byte[] v1 = ConvertUtil.hexStr2BinArr(dbw);
			String v2 = ConvertUtil.bytes2BinStr(v1);
			while(v2.length() < 8){
				v2 = "0"+v2;
			}
			String v3 = v2.substring(0,3);
			dbw4 = v3.substring(0,1);
			dbw2 = v3.substring(1,2);
			dbw0 = v3.substring(2,3);
			dbw0Value = Integer.parseInt(dbw0);
			dbw2Value = Integer.parseInt(dbw2);
			dbw4Value = Integer.parseInt(dbw4);
		}else{
			if(ulongData.length() >=4){
				dbw0 = ulongData.substring(0,4);//db0信号位
				dbw0Value = ConvertUtil.hex2Int(dbw0);
			}
			if(ulongData.length() >= 8){
				dbw2 = ulongData.substring(4,8);//db2信号位
				dbw2Value = ConvertUtil.hex2Int(dbw2);
			}
			if(ulongData.length() >=12){
				dbw4 = ulongData.substring(8,12);//db4信号位
				dbw4Value = ConvertUtil.hex2Int(dbw4);
			}
		}
		
		
		if(StringUtils.isBlank(dbw0)){
			logger.info("[批量计算]---->信号位长度不够，无法解析到值");
			return;
		}
		
		hash.hset(sessionId+"_parse_done", field, dbw0Value.toString());//记录解析值
		//记录值到数据库
		try {
			Object obj = SpringUtils.getBean("agreementRc701ValueServiceImpl");
			obj.getClass()
					.getDeclaredMethod("addValue",String.class,String.class,String.class,String.class)
					.invoke(obj, sessionId, field, dbw0Value.toString(),typename);
			
			String[] fieldWeis = null;
			if(field.contains("DBW")){
				fieldWeis = field.split("DBW");
			}else if(field.contains("DBX")){
				fieldWeis = field.split("DBX");
			}else{
				return;
			}
			
			String field2 = "";
			String field3 = "";
			if(StringUtils.equalsIgnoreCase(fieldWeis[1], "5.5")){
				if(StringUtils.isNotBlank(dbw2)){
					field2 = fieldWeis[0]+"DBX" + (Double.parseDouble(fieldWeis[1]) + 0.1);
				}
				if(StringUtils.isNotBlank(dbw4)){
					field3 = fieldWeis[0] +"DBX" + (Double.parseDouble(fieldWeis[1]) + 0.2);
				}
				
			}else{
				if(StringUtils.isNotBlank(dbw2)){
					field2 = fieldWeis[0]+"DBW" + (Integer.parseInt(fieldWeis[1]) + 2);
				}
				if(StringUtils.isNotBlank(dbw4)){
					field3 = fieldWeis[0] + "DBW"+ (Integer.parseInt(fieldWeis[1]) + 4);
				}
			}
			
			if(StringUtils.isNotBlank(field2)){
				obj.getClass()
				.getDeclaredMethod("addValue",String.class,String.class,String.class,String.class)
				.invoke(obj, sessionId, field2, dbw2Value.toString(),typename);
			}
			if(StringUtils.isNotBlank(field3)){
				obj.getClass()
				.getDeclaredMethod("addValue",String.class,String.class,String.class,String.class)
				.invoke(obj, sessionId, field3, dbw4Value.toString(),typename);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		hash.hdel(sessionId+"_done",field);
	}
	
	
	/**
	 * 解析命令得到整数值 S7-300 uLong类型,6个字节为一组，同时解析三个信号位数据 0 ，2，4
	 * @param sessionId 会话ID 
	 * @param field 对应信号位名称
	 * @param commandStr 消息
	 */
	@Override
	public void parseCommandToUlong(String sessionId,String field,String commandStr){
		JedisUtil jedisUtil = new JedisUtil();
		Hash hash = jedisUtil.HASH;
		String iccid = hash.hget("equipment_session_sessionId", sessionId);//获取ICCID
		String typename = hash.hget("equipment_equipment_type", iccid);
		commandStr = commandStr.substring(22,commandStr.length() - 6);//获取数据域
//		commandStr = commandStr.substring(10,commandStr.length() - 6);//获取数据域
		
		String ulongData = commandStr.substring(commandStr.length() - 12);//DB0,DB2,DB4 共6个字节，2个字节代表一个信号位
		String dbw0 = null;
		String dbw2 = null;
		String dbw4 = null;
		Integer dbw0Value = 0;
		Integer dbw2Value = 0;
		Integer dbw4Value = 0;
		
		if(field.contains("DBX")){
			//转成2进制
			ulongData = commandStr.substring(commandStr.length() - 2);
			String dbw = ulongData.substring(0,2);//db0信号位
			byte[] v1 = ConvertUtil.hexStr2BinArr(dbw);
			String v2 = ConvertUtil.bytes2BinStr(v1);
			while(v2.length() < 8){
				v2 = "0"+v2;
			}
			String v3 = v2.substring(0,3);
			dbw4 = v3.substring(0,1);
			dbw2 = v3.substring(1,2);
			dbw0 = v3.substring(2,3);
			dbw0Value = Integer.parseInt(dbw0);
			dbw2Value = Integer.parseInt(dbw2);
			dbw4Value = Integer.parseInt(dbw4);
		}else{
			if(ulongData.length() >=4){
				dbw0 = ulongData.substring(0,4);//db0信号位
				dbw0Value = ConvertUtil.hex2Int(dbw0);
			}
			if(ulongData.length() >= 8){
				dbw2 = ulongData.substring(4,8);//db2信号位
				dbw2Value = ConvertUtil.hex2Int(dbw2);
			}
			if(ulongData.length() >=12){
				dbw4 = ulongData.substring(8,12);//db4信号位
				dbw4Value = ConvertUtil.hex2Int(dbw4);
			}
		}
		
		
		if(StringUtils.isBlank(dbw0)){
			logger.info("[批量计算]---->信号位长度不够，无法解析到值");
			return;
		}
		
		hash.hset(sessionId+"_parse_done", field, dbw0Value.toString());//记录解析值
		//记录值到数据库
		try {
			Object obj = SpringUtils.getBean("agreementRc701ValueServiceImpl");
			obj.getClass()
					.getDeclaredMethod("addValue",String.class,String.class,String.class,String.class)
					.invoke(obj, sessionId, field, dbw0Value.toString(),typename);
			
			String[] fieldWeis = null;
			if(field.contains("DBW")){
				fieldWeis = field.split("DBW");
			}else if(field.contains("DBX")){
				fieldWeis = field.split("DBX");
			}else{
				return;
			}
			
			String field2 = "";
			String field3 = "";
			if(StringUtils.equalsIgnoreCase(fieldWeis[1], "5.5")){
				if(StringUtils.isNotBlank(dbw2)){
					field2 = fieldWeis[0]+"DBX" + (Double.parseDouble(fieldWeis[1]) + 0.1);
				}
				if(StringUtils.isNotBlank(dbw4)){
					field3 = fieldWeis[0] +"DBX" + (Double.parseDouble(fieldWeis[1]) + 0.2);
				}
				
			}else{
				if(StringUtils.isNotBlank(dbw2)){
					field2 = fieldWeis[0]+"DBW" + (Integer.parseInt(fieldWeis[1]) + 2);
				}
				if(StringUtils.isNotBlank(dbw4)){
					field3 = fieldWeis[0] + "DBW"+ (Integer.parseInt(fieldWeis[1]) + 4);
				}
			}
			
			if(StringUtils.isNotBlank(field2)){
				obj.getClass()
				.getDeclaredMethod("addValue",String.class,String.class,String.class,String.class)
				.invoke(obj, sessionId, field2, dbw2Value.toString(),typename);
			}
			if(StringUtils.isNotBlank(field3)){
				obj.getClass()
				.getDeclaredMethod("addValue",String.class,String.class,String.class,String.class)
				.invoke(obj, sessionId, field3, dbw4Value.toString(),typename);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		String commandStr = "684438363034303231081D0300001D02F0803203000000010002000800000401FF040020000E0000C92923";
//		String commandStr = "684438363034303231081A0300001A02F0803203000000010002000500000401FF03000100676723";
//		commandStr = commandStr.substring(22,commandStr.length() - 6);//获取数据域
//		System.out.println("截取后在原始值："+commandStr);
//		
//		String dataArea = null;
//		if(commandStr.length() == 52){
//			dataArea = commandStr.substring(commandStr.length()-4);
//		}
//		if(commandStr.length() == 58){
//			dataArea = commandStr.substring(50,54);
//		}
//		System.out.println("未转换的数据值："+dataArea);
//		Integer dataValue = ConvertUtil.hex2Int(dataArea);//得到数据
//		System.out.println("转换后在数据值："+dataValue);
//		System.out.println(Double.parseDouble("5.5") + 0.1);
		byte[] v1 = ConvertUtil.hexStr2BinArr("E0");
		System.out.println(ConvertUtil.bytes2BinStr(v1));
	}
	
}
