package com.taojin.iot.service.report.dao.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.alibaba.fastjson.JSON;
import com.taojin.iot.base.comm.dao.impl.BaseDaoImpl;
import com.taojin.iot.service.report.dao.ReportRealTimeSensorDao;
import com.taojin.iot.service.report.entity.ReportRealTimeSensor;
import com.taojin.iot.service.report.entity.Series;


@Repository("reportRealTimeSensorDaoImpl")
public class ReportRealTimeSensorDaoImpl extends BaseDaoImpl<ReportRealTimeSensor,Long> implements ReportRealTimeSensorDao{

	@Override
	public List<Series> ReportList(int order,Long id) {
			 StringBuffer str = new StringBuffer();
		switch(order){
		  case 1:
			  str.append("SELECT DATE_FORMAT(create_date,'%Y-%m-%d') as create_date,sensor_unit,SUM(sensor_values) FROM iot_report_real_time_sensor where Month(create_date)=Month(NOW()) and equipment_number = ").append(id).append(" GROUP BY DATE_FORMAT(create_date,'%Y-%m-%d'),sensor_unit");
			  break;
		  case 2:
			  str.append("SELECT DATE_FORMAT(create_date,'%Y-%m') as create_date,sensor_unit,SUM(sensor_values) FROM iot_report_real_time_sensor where YEAR(create_date)=YEAR(NOW()) and equipment_number =").append(id).append(" GROUP BY DATE_FORMAT(create_date,'%Y-%m'),sensor_unit");
			  break;
		  case 3:
			  str.append("SELECT DATE_FORMAT(create_date,'%Y-%m') as create_date,sensor_unit,SUM(sensor_values) FROM iot_report_real_time_sensor where QUARTER(create_date)=QUARTER(now()) and equipment_number =").append(id).append(" GROUP BY DATE_FORMAT(create_date,'%Y-%m'),sensor_unit");      
			  break;
		  case 4:
			  str.append("SELECT DATE_FORMAT(create_date,'%Y') as create_date,sensor_unit,SUM(sensor_values) FROM iot_report_real_time_sensor where equipment_number =").append(id).append(" GROUP BY DATE_FORMAT(create_date,'%Y'),sensor_unit");          
			  break;
		  default:
			  str.append("select DATE_FORMAT(create_date,'%Y-%m-%d') as create_date,sensor_unit,SUM(sensor_values) FROM iot_report_real_time_sensor where YEARWEEK(date_format(create_date,'%Y-%m-%d')) = YEARWEEK(now()) and equipment_number = ").append(id).append(" GROUP BY DATE_FORMAT(create_date,'%Y-%m-%d'),sensor_unit");
			  break;
		    }
		Query query = entityManager.createNativeQuery(str.toString());
		List<?> result = query.getResultList();
		String rep = JSON.toJSONString(result);
        Iterator<?> iterator = result.iterator();
        Map<String, List<String>> map = new HashMap<>();
        Map<String, ArrayList<String>> map2 = new HashMap<>();
    	//HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();       
        while(iterator.hasNext()){
        	Object[] o = (Object[]) iterator.next();
    			if(map.containsKey(o[1])){//map中存在此单位，将数据存放当前key的map中
    				map.get(o[1]).add(o[2]+"");
    				map2.get(o[1]).add(o[0]+"");
    			}else{//map中不存在，新建key，用来存放数据
    				List<String> tmpList = new ArrayList<>();
    				ArrayList<String> tmpList2 = new ArrayList<>();
    				tmpList.add(o[2]+"");
    				tmpList2.add(o[0]+"");
    				map.put(o[1]+"", tmpList);
    				map2.put(o[1]+"", tmpList2);
    			}
    		}
        List<Series> data = new ArrayList<Series>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()){
        	Series series = new Series();
        	series.setName(entry.getKey());	
        	series.setData(entry.getValue());
        	series.setStack("总值");
        	series.setType("line");       	
        	String s = entry.getKey();
        	ArrayList<String> x = map2.get(s);
        	series.setTime(x);
        	data.add(series);
        }
	 		return data;
        }
}
		
		


	

	

