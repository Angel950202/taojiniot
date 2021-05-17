package com.taojin.iot.service.kanban.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.taojin.iot.agreement.fujiya.entity.AddressDTU;
import com.taojin.iot.agreement.fujiya.entity.AgreementRc701Value;
import com.taojin.iot.agreement.fujiya.enums.AddressTypeEnum;
import com.taojin.iot.agreement.fujiya.enums.AgreementFujiyaEnum;
import com.taojin.iot.agreement.fujiya.service.AddressDTUService;
import com.taojin.iot.agreement.fujiya.service.AgreementRc701ValueService;
import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.redis.JedisUtil;
import com.taojin.iot.redis.JedisUtil.Hash;
import com.taojin.iot.service.equipment.entity.Equipment;
import com.taojin.iot.service.equipment.entity.EquipmentSensor;
import com.taojin.iot.service.equipment.service.EquipmentSensorService;
import com.taojin.iot.service.equipment.service.EquipmentService;
import com.taojin.iot.service.kanban.KanBanFormula;
import com.taojin.iot.service.kanban.LineEnum;
import com.taojin.iot.service.kanban.service.ProductionKanBanService;
import com.taojin.iot.service.task.entity.ProductionLine;
import com.taojin.iot.service.task.entity.WorkFinish;
import com.taojin.iot.service.task.entity.WorkOrder;
import com.taojin.iot.service.task.service.ProductionLineService;
import com.taojin.iot.service.task.service.WorkFinishService;
import com.taojin.iot.service.task.service.WorkOrderService;

/**
 * @program: server
 * @author: king
 * @description: 生产监控看板services实现类
 * FileName: productionKanBanServiceImpl
 * Date:     2019-08-05 0005 17:14:37
 **/
@Service("productionKanBanServiceImpl")
public class ProductionKanBanServiceImpl implements ProductionKanBanService {

    @Resource(name = "productionLineServiceImpl")
    private ProductionLineService productionLineService;

    @Resource(name = "workOrderServiceImpl")
    private WorkOrderService workOrderService;

    @Resource(name = "equipmentServiceImpl")
    private EquipmentService equipmentService;

    @Resource(name = "agreementRc701ValueServiceImpl")
    private AgreementRc701ValueService agreementRc701ValueService;
    
	@Resource(name = "equipmentSensorServiceImpl")
	private EquipmentSensorService equipmentSensorService;
	@Resource(name ="addressDTUServiceImpl")
	private AddressDTUService addressDTUService;
	@Resource(name ="workFinishServiceImpl")
	private WorkFinishService workFinishService;
	
	public static final String DTUVALUES = "DTU_VALUES";
	public static final String STOPTIME = "StopTime";
	
    //看板计算公式类
    KanBanFormula kanBanFormula = new KanBanFormula();
    
    //保留两位小数
    DecimalFormat df = new DecimalFormat("0.00");
    
    private static Logger log = LoggerFactory.getLogger(ProductionKanBanServiceImpl.class);

    @Override
    public List splitScreenKanBan(String line,String lineCode,String works,String Station,String okAddress,String nokAddress,String totalAddress) {
		List<Map<String,Object>> list = new ArrayList();
        /**
         * 获取产线目标值（生产进度目标值、良品率目标值、trs目标值）
         */
        Double target = 0.0;
        Double lpTarget = 0.0;
        Double trsTarget = 0.0;
        Double ctValue = 0.0;
        ProductionLine productionLine = productionLineService.getByParam("lineNumber",lineCode);
        if (productionLine != null) {
        	target = Double.valueOf(productionLine.getProgressTarget());
        	lpTarget = 100 - Double.valueOf(productionLine.getYieldTarget());
        	trsTarget = Double.valueOf(productionLine.getTrsTarget());
        	ctValue = Double.valueOf(productionLine.getCt());
		}
        JedisUtil jedisUtil = new JedisUtil();
		Hash hash = jedisUtil.HASH;
		//产线实际生产总数
		Double actualNum = 0.0;
		//产线不ok数量
        Double nokNum = 0.0;
        //产线ok数量
        Double okNum = 0.0;
        AddressDTU addressNOK = addressDTUService.getByParams("agreementFujiya", AgreementFujiyaEnum.valueOf(productionLine.getLineNumber()).ordinal(),"addressType",AddressTypeEnum.NOK.ordinal());
        if(null != addressNOK){
        	String result = hash.hget(DTUVALUES, addressNOK.getAddress()+"_"+productionLine.getLineNumber());
        	if(StringUtils.isNotBlank(result)){
        		JSONObject jsonNum = JSONObject.fromObject(result);
        		nokNum += jsonNum.optDouble("value");
        		nokNum += jsonNum.optDouble("totalValue");
        	}
        }
        AddressDTU addressOK = addressDTUService.getByParams("agreementFujiya", AgreementFujiyaEnum.valueOf(productionLine.getLineNumber()).ordinal(),"addressType",AddressTypeEnum.OK.ordinal());
        if(null != addressOK){
        	String result = hash.hget(DTUVALUES, addressOK.getAddress()+"_"+productionLine.getLineNumber());
        	if(StringUtils.isNotBlank(result)){
        		JSONObject jsonNum = JSONObject.fromObject(result);
        		okNum += jsonNum.optDouble("value");
        		okNum += jsonNum.optDouble("totalValue");
        	}
        }
        actualNum = nokNum + okNum;
//        Double actualNum = 0.0;
////        Double actualNum1 = 0.0;
//        if (!"".equals(totalAddress) && totalAddress != null) {
//        	String total_arr [] = totalAddress.split(",");
//        	actualNum = lineNumByAddress(total_arr,agreementFujiyaEnum.ordinal());
//        
//        }

        //产线不ok数量
//        Double nokNum = 0.0;
//        if (!"".equals(nokAddress) && nokAddress != null) {
//        	String nok_arr [] = nokAddress.split(",");
//        	//生产总数数组
//        	nokNum = lineNumByAddress(nok_arr,agreementFujiyaEnum.ordinal());
//		}
//        
//        Double okNum = 0.0;
//        if (!"".equals(okAddress) && okAddress != null) {
//        	String ok_arr [] = okAddress.split(",");
//        	okNum = lineNumByAddress(ok_arr,agreementFujiyaEnum.ordinal());
//		}
        
     	Map<String,Object> progressMap = new HashMap<>();
    	progressMap.put("name","生产数量");
    	progressMap.put("target",target);
    	progressMap.put("actualNum",okNum);
        list.add(progressMap);

        /**
         * 不良品率
         */
        Double YIELD = kanBanFormula.goodProducts(nokNum,actualNum);
        Map<String,Object> lpMap = new HashMap<>();
        lpMap.put("name","不良品率");
        lpMap.put("value",YIELD);
        lpMap.put("target",lpTarget);
        list.add(lpMap);


        /**
         * 获取实际生产时间
         * 时间生产时间   =  当前时间  - 开始时间
         * 最新计算公式
         * TRS=OK件数量* CT/(24*3600) *100%
         */
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        Double actualTime = millisecond(DatesUtils.getBeginTime(hour),DatesUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        Double trs = kanBanFormula.trs(ctValue,okNum,actualTime);
        System.out.println("========"+okNum);
//        Double trs = okNum * ctValue / (24 * 3600);
        Map<String,Object> trsMap = new HashMap<>();
        trsMap.put("name","TRS");
        trsMap.put("value",trs);
        trsMap.put("target",trsTarget);
        list.add(trsMap);
        return list;
    }
    
    /**
     * 计算时间差
     * 返回毫秒
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException 
     */
    private Double millisecond(String startTime,String endTime) {
    	double time = 0;
    	try {
    		double nd = 1000 * 24 * 60 * 60;
    		double nh = 1000 * 60 *60;
    		double nm = 1000 * 60;
    		double mi = 1000;
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		Date start = sdf.parse(startTime);
    		Date end = sdf.parse(endTime);
    		double diff = end.getTime() - start.getTime();
    		//天
    		double day = diff/nd;
    		//小时
    		double hour = diff/nh;
    		//分钟
    		double minute = diff/nm;
    		//秒
    		double millisecond = diff/mi;
    		return millisecond;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return time;
    }
    
    /**
     * 计算时间差
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException 
     */
    private Double timeCalculation(String startTime,String endTime) {
    	double time = 0;
    	try {
    		double nd = 1000 * 24 * 60 * 60;
    		double nh = 1000 * 60 *60;
    		double nm = 1000 * 60;
    		double mis = 1000;
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		Date start = sdf.parse(startTime);
    		Date end = sdf.parse(endTime);
    		double diff = end.getTime() - start.getTime();
    		//天
    		double day = diff/nd;
    		//小时
    		double hour = diff/nh;
    		//分钟
    		double minute = diff/nm;
    		double mi = diff/ mis;
    		time = minute;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return time;
    }

    @Override
    public List deviceStatus(String line,String lineCode,String works,String Station,String address) {
        List list = new ArrayList();
        LineEnum agreementFujiyaEnum = LineEnum.valueOf(lineCode);
        
        List<Filter> filters1 = new ArrayList<>();
        filters1.add(Filter.eq("lineNumber", lineCode));
        List<Equipment> equipmentList = equipmentService.findList(null,filters1,null);
        for (Equipment equipment : equipmentList){
        	/*
        	 * 名称长度大于4的是工作站
        	 * C121-S1、C122-S1
        	 */
        	
        	Map<String,Object> map = new HashMap<>();
        	if ("C167-171".equals(equipment.getName())) {
				continue;
			}
        	//主要设备
        	if (equipment.getMainEquipment() != null && equipment.getMainEquipment()) {
        		Integer deviceStatus = 0;
        		String workstationAddressString  = "";
        		List<Filter> filters = new ArrayList<>();
        		filters.add(Filter.eq("equipment", equipment.getId()));
				List<EquipmentSensor> sensors = equipmentSensorService.findList(null,filters,null);
				for (EquipmentSensor equipmentSensor : sensors) {
					if(equipmentSensor.getSerialNumber().startsWith("DB")){
						String[] workStations = equipmentSensor.getSerialNumber().split("\\.");
						workstationAddressString = workStations[0];//获取工站地址分组位
						break;
					}
//					if ("设备故障".equals(equipmentSensor.getName())) {
//						deviceStatus = workStation(equipmentSensor.getSerialNumber(),agreementFujiyaEnum.ordinal());
//					}
				}
				
				if(!workstationAddressString.equals("")){
					String statusAddress = "DTU."+workstationAddressString +".8";
					deviceStatus = workStation(statusAddress,agreementFujiyaEnum.ordinal());
				}
				
				map.put("name",equipment.getName());
	    		map.put("code", equipment.getIdNumber());
	    		map.put("status",deviceStatus);
                list.add(map);
        	}
        }
        return list;
    }

    @Override
    public Map downtime(String line,String lineCode,String works,String Station,String address,String startTime,String endTime) {
        Map<String,Object> downtimeMap = new HashMap<>();
        //工作站list
        List workList = new ArrayList();
        //目标值list
        List targetList = new ArrayList();
        //实际值list
        List actualList = new ArrayList();
        String equipmentLineCode = null;//设备的生产线RD77-2跟RC77-2是反的
        if(lineCode.equals("EPUMPGEAR")){
        	equipmentLineCode = "EPUMPMAIN";
        }else if(lineCode.equals("EPUMPMAIN")){
        	equipmentLineCode = "EPUMPGEAR";
        }else{
        	equipmentLineCode = lineCode;
        }
        
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(Filter.eq("addressType", AddressTypeEnum.POLICE.ordinal()));
        filters.add(Filter.eq("agreementFujiya", AgreementFujiyaEnum.valueOf(lineCode)));
        List<AddressDTU> list = addressDTUService.findList(null, filters, null);
        JedisUtil jedisUtil = new JedisUtil();
		Hash hash = jedisUtil.HASH;
        for (AddressDTU addressDTU : list) {
        	double time = 0;
        	String result = hash.hget(DTUVALUES, addressDTU.getAddress()+"_"+lineCode+"_"+STOPTIME);
        	Equipment equipment = equipmentService.querySensorLineByAddrssANDLine(addressDTU.getAddress(), equipmentLineCode);
        	if(equipment != null){
        		targetList.add(equipment.getOffTime());
            	workList.add(equipment.getName());
        	}
			if(StringUtils.isNotBlank(result)){
        		JSONObject jsonNum = JSONObject.fromObject(result);
        		time = jsonNum.optDouble("timeLong");
        	}
			actualList.add(df.format((double)time / 60));
		}
        
//        for (Equipment equipment : equipmentList){
//        	/*
//        	 * 名称长度大于4的是工作站
//        	 * C121-S1、C122-S1
//        	 */
//        	if (equipment.getName().length() > 4) {
//                workList.add(equipment.getName());
//                targetList.add(equipment.getOffTime());
//        		List<Filter> filters1 = new ArrayList<>();
//        		filters1.add(Filter.eq("equipment", equipment.getId()));
//        		filters1.add(Filter.eq("name", "停机时间"));
//				List<EquipmentSensor> sensors = equipmentSensorService.findList(null,filters1,null);
//				double time = 0;
//				for (EquipmentSensor equipmentSensor : sensors) {
//					time = lineOkNum(equipmentSensor.getSerialNumber(),agreementFujiyaEnum.ordinal(),startTime,endTime);
//					break;
//				}
//				actualList.add(df.format((double)time / 60000));
//        	}
//        }
        downtimeMap.put("work",workList);
        downtimeMap.put("target",targetList);
        downtimeMap.put("actual",actualList);
        return downtimeMap;
    }

    @Override
    public Map alarmNum(String line,String lineCode,String works,String Station,String address,String startTime,String endTime) {
        Map<String,Object> alarmMap = new HashMap<>();
        //工作站list
        List workList = new ArrayList();
        //目标值list
        List targetList = new ArrayList();
        //实际值list
        List actualList = new ArrayList();
        LineEnum agreementFujiyaEnum = LineEnum.valueOf(lineCode);
        
        /**
         * 根据产线编码获取产线下的工位，报警次数
         * RD77-1、RD77-2有报警次数
         * 
         */
        String equipmentLineCode = null;//设备的生产线RD77-2跟RC77-2是反的
        if(lineCode.equals("EPUMPGEAR")){
        	equipmentLineCode = "EPUMPMAIN";
        }else if(lineCode.equals("EPUMPMAIN")){
        	equipmentLineCode = "EPUMPGEAR";
        }else{
        	equipmentLineCode = lineCode;
        }
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(Filter.ne("addressType", AddressTypeEnum.NOK.ordinal()));
        filters.add(Filter.ne("addressType", AddressTypeEnum.NUM.ordinal()));
        filters.add(Filter.ne("addressType", AddressTypeEnum.OK.ordinal()));
        filters.add(Filter.ne("addressType", AddressTypeEnum.STOP.ordinal()));
        filters.add(Filter.eq("agreementFujiya", AgreementFujiyaEnum.valueOf(lineCode)));
        List<AddressDTU> list = addressDTUService.findList(null, filters, null);
        JedisUtil jedisUtil = new JedisUtil();
		Hash hash = jedisUtil.HASH;
        for (AddressDTU addressDTU : list) {
        	long count =0;
        	String result = hash.hget(DTUVALUES, addressDTU.getAddress()+"_"+lineCode);
        	Equipment equipment = equipmentService.querySensorLineByAddrssANDLine(addressDTU.getAddress(), equipmentLineCode);
        	if(equipment != null){
        		workList.add(equipment.getName());
        		targetList.add(equipment.getAlarmCount());
        		if(StringUtils.isNotBlank(result)){
            		JSONObject jsonNum = JSONObject.fromObject(result);
            		if(addressDTU.getAddressType() == AddressTypeEnum.POLICENUM){
            			count = jsonNum.optLong("value")+jsonNum.optLong("totalValue");
            		}else{
            			count = jsonNum.optLong("count");
            		}
            	}
    			actualList.add(count);
        	}
			
		}
        
        
//        JedisUtil jedisUtil = new JedisUtil();
//		Hash hash = jedisUtil.HASH;
//        List<Filter> filters1 = new ArrayList<>();
//        filters1.add(Filter.eq("lineNumber", lineCode));
//        filters1.add(Filter.eq("mainEquipment", 1));
//        List<Equipment> equipmentList = equipmentService.findList(null,filters1,null);
//        for (Equipment equipment : equipmentList) {
//        	//工站名称
//        	workList.add(equipment.getName());
//    		targetList.add(equipment.getAlarmCount());
//    		List<Filter> filters = new ArrayList<>();
//			filters.add(Filter.eq("equipment", equipment.getId()));
//			filters1.add(Filter.eq("name", "工位报警"));
//			List<EquipmentSensor> sensors = equipmentSensorService.findList(null,filters,null);
//			long count =0;
//			if(sensors.size() > 0){
//				for (EquipmentSensor sensor : sensors) {
//					String result = hash.hget(DTUVALUES, sensor.getSerialNumber()+"_"+equipment.getLineNumber());
//					if(StringUtils.isNotBlank(result)){
//		        		JSONObject jsonNum = JSONObject.fromObject(result);
//		        		count += jsonNum.optLong("count");
//		        	}
//				}
//			}	
//			actualList.add(count);
//        }
//        if (agreementFujiyaEnum.ordinal() == 1 || agreementFujiyaEnum.ordinal() == 2) {
//        	List<Filter> filters1 = new ArrayList<>();
//            filters1.add(Filter.eq("lineNumber", lineCode));
//            filters1.add(Filter.eq("mainEquipment", 1));
//            List<Equipment> equipmentList = equipmentService.findList(null,filters1,null);
//            for (Equipment equipment : equipmentList) {
//            	/*
//            	 * 名称长度为4的是工位
//            	 * C121、C122
//            	 */
//            	if (equipment.getName().length() > 4) {
//            		//工站名称
//                	workList.add(equipment.getName());
//            		targetList.add(equipment.getAlarmCount());
//            		List<Filter> filters = new ArrayList<>();
//    				filters.add(Filter.eq("equipment", equipment.getId()));
//    				List<EquipmentSensor> sensors = equipmentSensorService.findList(null,filters,null);
//    				long count =0;
//    				for (EquipmentSensor equipmentSensor : sensors) {
//    					
//    					if ("报警次数".equals(equipmentSensor.getName())) {
//    					/*
//    					 * 获取当天的报警次数
//    					 */
//    			            count += agreementRc701ValueService.count(
//    			            		Filter.eq("address", equipmentSensor.getSerialNumber()),
//    			            		Filter.eq("equipType", agreementFujiyaEnum.ordinal()),
//    			            		Filter.eq("dateTime", DatesUtils.getToday()));
//    					}
//    				}
//    				actualList.add(count);
//    			}
//    		}
//		}
   
        alarmMap.put("work",workList);
        alarmMap.put("target",targetList);
        alarmMap.put("actual",actualList);
        return alarmMap;
    }

    @Override
    public Map productLP(String line,String lineCode,String works,String Station,String okAddress,String totalAddress,String startTime,String endTime) {
        Map<String,Object> productLPMap = new HashMap<>();
        //工作站list
        List workList = new ArrayList();
        //目标值list
        List targetList = new ArrayList();
        //实际值list
        List actualList = new ArrayList();
        
        LineEnum agreementFujiyaEnum = LineEnum.valueOf(lineCode);
        
        /**
         * 根据产线编码获取工位，地址
         * 计算良品率
         */
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.eq("lineNumber", lineCode));
        List<Equipment> equipmentList = equipmentService.findList(null,filters,null);
        for (Equipment equipment : equipmentList) {
        	/*
        	 * 名称长度大于4，是工作站
        	 */
        	if ("C167-171".equals(equipment.getName())) {
				continue;
			}
        	if (equipment.getName().length() > 4) {
        		workList.add(equipment.getName());
        		targetList.add(equipment.getYieldTarget());
        		List<Filter> filters1 = new ArrayList<>();
        		filters1.add(Filter.eq("equipment", equipment.getId()));
				List<EquipmentSensor> sensors = equipmentSensorService.findList(null,filters1,null);
				//工作站OK数
				Double okNum = 0.0;
				//工作站实际生产总数
				Double totalNum = 0.0;
				for (EquipmentSensor equipmentSensor : sensors) {
					if ("OK件".equals(equipmentSensor.getName())) {
						okNum += lineOkNum(equipmentSensor.getSerialNumber(),agreementFujiyaEnum.ordinal(),startTime,endTime);
					}
					if ("生产总数".equals(equipmentSensor.getName())) {
						totalNum += lineOkNum(equipmentSensor.getSerialNumber(),agreementFujiyaEnum.ordinal(),startTime,endTime);
					}
				}
				//工作站良品率集合
				double good = kanBanFormula.goodProducts(okNum,totalNum);
				if (good > 100) {
					actualList.add(100);
				}else{
					actualList.add(good);
				}
        	}
        }
        productLPMap.put("work",workList);
        productLPMap.put("target",targetList);
        productLPMap.put("actual",actualList);
        return productLPMap;
    }

    
    @Override
    public Map productNLP(String line,String lineCode,String works,String Station,String nokAddress,String totalAddress,String startTime,String endTime) {
        Map<String,Object> productLPMap = new HashMap<>();
        //工作站list
        List workList = new ArrayList();
        //目标值list
        List targetList = new ArrayList();
        //实际值list
        List actualList = new ArrayList();
        
        LineEnum agreementFujiyaEnum = LineEnum.valueOf(lineCode);
        
        /**
         * 根据产线编码获取工位，地址
         * 计算良品率
         */
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.eq("lineNumber", lineCode));
        List<Equipment> equipmentList = equipmentService.findList(null,filters,null);
        for (Equipment equipment : equipmentList) {
        	/*
        	 * 名称长度大于4，是工作站
        	 */
        	if ("C167-171".equals(equipment.getName())) {
				continue;
			}
        	if (equipment.getName().length() > 4) {
        		workList.add(equipment.getName());
        		targetList.add(100 - equipment.getYieldTarget());
        		List<Filter> filters1 = new ArrayList<>();
        		filters1.add(Filter.eq("equipment", equipment.getId()));
				List<EquipmentSensor> sensors = equipmentSensorService.findList(null,filters1,null);
				//工作站OK数
				Double nokNum = 0.0;
				//工作站实际生产总数
				Double totalNum = 0.0;
				for (EquipmentSensor equipmentSensor : sensors) {
					if ("NOK件".equals(equipmentSensor.getName())) {
						nokNum = lineOkNum(equipmentSensor.getSerialNumber(),agreementFujiyaEnum.ordinal(),startTime,endTime);
						continue;
					}
					if ("生产总数".equals(equipmentSensor.getName())) {
						totalNum = lineOkNum(equipmentSensor.getSerialNumber(),agreementFujiyaEnum.ordinal(),startTime,endTime);
						continue;
					}
				}
				//工作站良品率集合
				double good = kanBanFormula.goodProducts(nokNum,totalNum);
				if (good > 100) {
					actualList.add(100);
				}else{
					actualList.add(good);
				}
        	}
        }
        productLPMap.put("work",workList);
        productLPMap.put("target",targetList);
        productLPMap.put("actual",actualList);
        return productLPMap;
    }
    @Override
    public Map trsTrend(String line,String lineCode,String works,String Station,String totalAddress) {
    	
    	/*
    	 * trs趋势是一个产线每天的trs值
    	 */
    	
         Double ctValue = 0.0;
         ProductionLine productionLine = productionLineService.getByParam("lineNumber",lineCode);
         if (productionLine != null) {
         	ctValue = Double.valueOf(productionLine.getCt());
 		 }
         //产线实际生产总数
         String total_arr [] = totalAddress.split(",");
         Double actualNum = lineTotalNum(total_arr,0);
    	
    	/**
         * trs
         */
        //实际生产时间
        Double actualTime = 0.0;
        List<Filter> filters = new ArrayList<>();
	    filters.add(Filter.eq("lineNumber", lineCode));
	    List<WorkOrder> workOrders = workOrderService.findList(null,filters,null);
	    if (!workOrders.isEmpty()) {
	    	for(WorkOrder info : workOrders){
	    		actualTime += info.getFinishTime();
	    	}
		}
        Double trs = kanBanFormula.trs(ctValue,actualNum,actualTime);
    	List trsList = new ArrayList();
        trsList.add(trs);
      
        List dateList = new ArrayList();
        Map<String,Object> map = new HashMap<>();
        map.put("trs",trsList);
        map.put("date",dateList);
        return map;
    }

    @Override
    public List<Map<String, Object>> workstationKanBan(String line,String works,String Station,String okAddress,String nokAddress,String totalAddress,String armAddress,String taskorderCode,String lineCode,String code) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String,Object> map = new HashMap<>();
        LineEnum agreementFujiyaEnum = LineEnum.valueOf(lineCode);
        
        Integer okNum = 0;
        Integer totalNum = 0;
        Integer nokNum = 0;
        String runingTime = "";
        Double runTime = 0.0;
        String faultTime = "";
        String idleTime = "";
        Integer deviceStatus = 0;
        Boolean flg = true;
		
        //产线
        map.put("line",line);
        //工作站
        map.put("work",works);
        /**
         * 根据产线编码、工作站编码
         * 获取工作站生产总数、OK数量、NOK数量等数据
         */
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.eq("lineNumber", lineCode));
        filters.add(Filter.eq("idNumber", code));
        List<Equipment> equipmentList = equipmentService.findList(null,filters,null);
        // TODO Auto-generated catch block
        for (Equipment equipment : equipmentList) {
        	List<Filter> filters1 = new ArrayList<>();
    		filters1.add(Filter.eq("equipment", equipment.getId()));
			List<EquipmentSensor> sensors = equipmentSensorService.findList(null,filters1,null);
			for (EquipmentSensor equipmentSensor : sensors) {
				if ("OK件".equals(equipmentSensor.getName())) {
					okNum = workStation(equipmentSensor.getSerialNumber(),agreementFujiyaEnum.ordinal());
				}
				else if ("生产总数".equals(equipmentSensor.getName())) {
					totalNum = workStation(equipmentSensor.getSerialNumber(),agreementFujiyaEnum.ordinal());
				}
				else if ("NOK件".equals(equipmentSensor.getName())) {
					nokNum = workStation(equipmentSensor.getSerialNumber(),agreementFujiyaEnum.ordinal());
				}
				else if ("故障时间".equals(equipmentSensor.getName())) {
					Integer time = workStation(equipmentSensor.getSerialNumber(),agreementFujiyaEnum.ordinal());
					faultTime = df.format((double)((double)time / 60000));
				}
				else if ("运行时间".equals(equipmentSensor.getName())) {
					Integer time = workStation(equipmentSensor.getSerialNumber(),agreementFujiyaEnum.ordinal());
					runTime =  (double)time;
					runingTime = df.format((double)((double)time / 60000));
				}
				else if ("空转时间".equals(equipmentSensor.getName())) {
					Integer time = workStation(equipmentSensor.getSerialNumber(),agreementFujiyaEnum.ordinal());
					idleTime = df.format((double)((double)time / 60000));
				}
				
				//获取工位状态，只执行一次查询
				if (flg) {
					if(equipmentSensor.getSerialNumber().startsWith("DB")){
						String[] workStations = equipmentSensor.getSerialNumber().split("\\.");
						String status = workStations[0];
						if (!"".equals(status)) {
							String statusAddress = "DTU."+status +".8";
							deviceStatus = workStation(statusAddress,agreementFujiyaEnum.ordinal());
							flg = false;
						}
					}
				}
			}
        }
	    
        /*
         * 根据班次获取开机时间
         * 07:00:00 - 15:00:00
         * 15:00:00 - 23:00:00
         * 23:00:00 - 07:00:00
         */
     
        String bootUpTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 7 && hour < 15) {//早班
        	bootUpTime = sdf.format(new Date()) +" 07:00:00";
		}
        else if (hour >= 15 && hour < 23) {//中班
        	bootUpTime = sdf.format(new Date()) +" 15:00:00";
		}
        else if (hour >= 23) {//晚班
        	bootUpTime = sdf.format(new Date()) +" 23:00:00";
		}else if(hour < 7){
			bootUpTime = DatesUtils.getYestoday() +" 23:00:00";
		}
        
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        /*
         * 获取当前班次生产型号且产线编码不为空
         * 
         * 当前时间大于计划开始时间且小于结束时间，未完成的任务单即当前班次生产任务单
         */
        if (taskorderCode != null && !"".equals(taskorderCode) && !"null".equals(taskorderCode)) {
        	WorkOrder workOrders = workOrderService.getByParam("orderNumber", taskorderCode);
        	try {
        		Date startTime = dateformat.parse(workOrders.getStartTime());
				Date endTime = dateformat.parse(workOrders.getEndTime());
				Date date = new Date();
				if (date.compareTo(startTime) > 0 && date.compareTo(endTime) < 0 && "进行中".equals(workOrders.getStatus())) {
					//生产型号
		        	map.put("model",workOrders.getProductionModel());
		        	//计划数量
		            map.put("jiJan_num",workOrders.getCount());
		            /*
		             * 设备稼动率
		             * 设备稼动率=设备运行时间/（目前时间-生产型号开始时间)
		             */
		            Double currentTime = (double) System.currentTimeMillis();
		        	Double time = 0.0;
		        	time = (double) startTime.getTime();
		        	Double deviceTrs = runTime / (currentTime - time);
			        map.put("trs",deviceTrs);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        //开机时间
        map.put("power_on_time",bootUpTime);
        //运行时间
        map.put("power_off_time",runingTime);
        //空转时间
        map.put("idle_time",idleTime);
        //故障时间
        map.put("fault_time",faultTime);
        //生产总数
        map.put("production_num",totalNum);
        //OK件
        map.put("ok_num",okNum);
        //NOK件
        map.put("no_ok_num",nokNum);
        //设备状态
        map.put("deviceStatus", deviceStatus);
        list.add(map);
        return list;
    }
    
    /**
     * 工作站统计
     * 生产总数、OK数、NOK数
     * @param address
     * @param line
     * @return
     */
    private Integer workStation(String address,Integer line){
    	Integer num = 0;
    	Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	
    	List<Filter> filters = new ArrayList<>();
	    filters.add(Filter.eq("address", address));
	    filters.add(Filter.eq("equipType", line));
	    filters.add(Filter.eq("dateTime", sdf.format(date)));
	    
	    List<AgreementRc701Value> aValues = agreementRc701ValueService.findList(null,filters,null);
	    if (!aValues.isEmpty()) {
        	Integer value = Integer.valueOf(aValues.get(0).getCommandValue());
        	num += value;
        }
    	return num;
    }

    /**
     * 信号位统计求和
     * @param ok_arr 信号地址
     * @param line 产线
     * @return
     */
    private Double lineNumByAddress(String ok_arr[],Integer line){
    	Double num = 0.0;
    	List<String> arrs = new ArrayList<String>();
    	for (String arr : ok_arr) {
    		arrs.add(arr);
		}
    	List<Filter> filters = new ArrayList<Filter>();
    	filters.add(Filter.eq("equipType", line));
    	filters.add(Filter.in("address", arrs));
    	filters.add(Filter.eq("dateTime", DatesUtils.getToday()));
    	List<AgreementRc701Value> values= agreementRc701ValueService.findList(null, filters, null);
    	for (AgreementRc701Value value : values) {
    		num+=value.getCommandValue();
		}
    	return num;
    }
    
    /**
     * 信号位统计求和
     * @param ok_arr 信号地址
     * @param line 产线
     * @param dateTime 日期
     * @return
     */
    private Double lineNumByAddress(String ok_arr[],Integer line,String dateTime){
    	Double num = 0.0;
    	List<String> arrs = new ArrayList<String>();
    	for (String arr : ok_arr) {
    		arrs.add(arr);
		}
    	List<Filter> filters = new ArrayList<Filter>();
    	filters.add(Filter.eq("equipType", line));
    	filters.add(Filter.in("address", arrs));
    	filters.add(Filter.eq("dateTime", dateTime));
    	List<AgreementRc701Value> values= agreementRc701ValueService.findList(null, filters, null);
    	for (AgreementRc701Value value : values) {
    		num+=value.getCommandValue();
		}
    	return num;
    }
    
    
    /**
     * 产线OK数
     * @param ok_arr
     * @param line
     * @return
     */
    private Double lineOkNum(String ok_arr[],Integer line){
    	Double okNum = 0.0;
    	Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	for (int i = 0;i< ok_arr.length;i++){
    		List<Filter> filters = new ArrayList<>();
		    filters.add(Filter.eq("address", ok_arr[i]));
		    filters.add(Filter.eq("equipType", line));
		    filters.add(Filter.eq("dateTime", sdf.format(date)));
		    
            List<AgreementRc701Value> aValues = agreementRc701ValueService.findList(null,filters,null);
            if (!aValues.isEmpty()) {
            	Integer value = Integer.valueOf(aValues.get(0).getCommandValue());
                okNum += value;
            }
        }
    	return okNum;
    }
    /**
     * 产线OK数
     * 带时间查询
     * @param ok_arr
     * @param line
     * @param startTime
     * @param endTime
     * @return
     */
    private Double lineOkNum(String ok_arr,Integer line,String startTime,String endTime){
    	Double okNum = 0.0;
		List<Filter> filters = new ArrayList<>();
	    filters.add(Filter.eq("address", ok_arr));
	    filters.add(Filter.eq("equipType", line));
	    /*
	     * 开始时间为空或结束时间为空则默认当天0点到23点
	     */
//	    Date date = new Date();
//	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	    if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
//			startTime = sdf.format(date) + " 00:00:00";
//			endTime = sdf.format(date) + " 23:59:59";
//		}
//        filters.add(Filter.between_two("modifyDate", startTime,endTime));
	    filters.add(Filter.eq("dateTime", DatesUtils.getToday()));
        List<AgreementRc701Value> aValues = agreementRc701ValueService.findList(null,filters,null);
        for (AgreementRc701Value agreementRc701Value : aValues) {
        	Integer value = Integer.valueOf(agreementRc701Value.getCommandValue());
            okNum += value;
		}
    	return okNum;
    }
    
    /**
     * 产线生产总数
     * @param total_arr
     * @param line
     * @return
     */
    private Double lineTotalNum(String total_arr[],Integer line){
    	Double actualNum = 0.0;
    	Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	for (int i = 0;i< total_arr.length;i++){
    		List<Filter> filters = new ArrayList<>();
		    filters.add(Filter.eq("address", total_arr[i]));
		    filters.add(Filter.eq("equipType", line));
		    filters.add(Filter.eq("dateTime", sdf.format(date)));
    	     
            List<AgreementRc701Value> aValues = agreementRc701ValueService.findList(null,filters,null);
            if (!aValues.isEmpty()) {
            	Integer value = Integer.valueOf(aValues.get(0).getCommandValue());
                actualNum += value;
            }
        }
    	return actualNum;
    }
    
    /**
     * 产线生产总数
     * 带时间
     * @param total_arr
     * @param line
     * @param startTime
     * @param endTime
     * @return
     */
    private Double lineTotalNum(String total_arr,Integer line,String startTime,String endTime){
    	Double actualNum = 0.0;
		List<Filter> filters = new ArrayList<>();
	    filters.add(Filter.eq("address", total_arr));
	    filters.add(Filter.eq("equipType", line));
//	    filters.add(Filter.eq("createDate", startTime));
//        filters.add(Filter.eq("modifyDate", endTime));
	     
//	    Date date = new Date();
//	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	    if (StringUtils.isNotBlank(startTime) || StringUtils.isNotBlank(endTime)) {
//			startTime = sdf.format(date) + "00:00:00";
//			endTime = sdf.format(date) + "23:59:59";
//		}
//        filters.add(Filter.between_two("modifyDate", startTime,endTime));
	    filters.add(Filter.eq("dateTime", DatesUtils.getToday()));
        List<AgreementRc701Value> aValues = agreementRc701ValueService.findList(null,filters,null);
        if (!aValues.isEmpty()) {
        	Integer value = Integer.valueOf(aValues.get(0).getCommandValue());
            actualNum += value;
        }
    	return actualNum;
    }

    /**
     * 产线NOK数量
     * @param ok_arr
     * @param line
     * @return
     */
    private Double lineNOKNum(String nokNum_arr[],Integer line){
    	Double NOKNum = 0.0;
    	Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	for (int i = 0;i< nokNum_arr.length;i++){
    		List<Filter> filters = new ArrayList<>();
		    filters.add(Filter.eq("address", nokNum_arr[i]));
		    filters.add(Filter.eq("equipType", line));
		    filters.add(Filter.eq("dateTime", sdf.format(date)));
    	     
            List<AgreementRc701Value> aValues = agreementRc701ValueService.findList(null,filters,null);
            if (!aValues.isEmpty()) {
            	Integer value = Integer.valueOf(aValues.get(0).getCommandValue());
            	NOKNum += value;
            }
        }
    	return NOKNum;
    }

    /**
     * 设备状态
     * @param adder_arr
     * @param line
     * @return
     */
    private Integer deviceStatus(String adder_arr[],Integer line){
    	Integer device_status = 0;
    	Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	for (int i = 0;i< adder_arr.length;i++){
    		List<Filter> filters = new ArrayList<>();
		    filters.add(Filter.eq("address", adder_arr[i]));
		    filters.add(Filter.eq("equipType", line));
		    filters.add(Filter.eq("dateTime", sdf.format(date)));
    	     
            List<AgreementRc701Value> aValues = agreementRc701ValueService.findList(null,filters,null);
            if (!aValues.isEmpty()) {
            	device_status = aValues.get(0).getCommandValue();
            }
        }
    	
//    	Double num = agreementRc701ValueService.getSum(adder_arr, line, DatesUtils.getStringToday("yyyy-MM-dd"));
//    	if(num > 0){
//    		device_status = 1;
//    	}
    	return device_status;
    }

	@Override
	public JSONObject trsList(String lineCode,
			String okAddress, String nokAddress, String totalAddress) {
		JSONObject jsonReturn = new JSONObject();
		Double ctValue = 0.0;
		Double trsTarget = 0.0;
    	ProductionLine productionLine = productionLineService.getByParam("lineNumber",lineCode);
    	if(null != productionLine){
    		ctValue = Double.valueOf(productionLine.getCt());
    		trsTarget = Double.valueOf(productionLine.getTrsTarget());
    	}
//    	LineEnum agreementFujiyaEnum = LineEnum.valueOf(lineCode);
    	Calendar c = Calendar.getInstance();
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //过去七天
        c.setTime(new Date());
        c.add(Calendar.DATE, - 7);
        Date d = c.getTime();
        String start = format.format(d);
    	String end = DatesUtils.getYestoday();
    	List<String> days = DatesUtils.getMondayNumber(start, end, "yyyy-MM-dd");
    	JSONArray jsonArray = new JSONArray();
		JSONArray jsonTrs = new JSONArray();
		JSONArray jsonDate = new JSONArray();
    	for (String day : days) {
    		List<Filter> filters = new ArrayList<>();
    		filters.add(Filter.eq("lineNumber", productionLine.getLineNumber()));
    		filters.add(Filter.eq("addressType", AddressTypeEnum.OK.ordinal()));
    		filters.add(Filter.eq("dateTime", day));
    		List<WorkFinish> list = workFinishService.findList(null, filters, null);
    		Double actualNum = 0.0;
    		Double actualTime = 0.0;
    		if(list.size() > 0){
    			WorkFinish workFinish = list.get(0);
    			actualNum = workFinish.getValue().doubleValue();
    			actualTime = workFinish.getTimeLong().doubleValue();
    		}
    		Double trs = kanBanFormula.trs(ctValue,actualNum,actualTime);
    		jsonDate.add(day);
    		jsonArray.add(trsTarget);
    		jsonTrs.add(trs);
//    		Double actualNum = 0.0;
//        	if (!"".equals(okAddress) && okAddress != null) {
//             	String ok_arr [] = okAddress.split(",");
//             	actualNum = lineNumByAddress(ok_arr,agreementFujiyaEnum.ordinal(),day);
//            }
//        	Double actualTime = 86400.0;
//            Double trs = kanBanFormula.trs(ctValue,actualNum,actualTime);
//            jsonDate.add(day);
//            jsonArray.add(trsTarget);
//            jsonTrs.add(trs);
		}
    	jsonReturn.put("trs", jsonTrs);
    	jsonReturn.put("createDate", jsonDate);
    	jsonReturn.put("trsTarget", jsonArray);
		return jsonReturn;
	}
}
