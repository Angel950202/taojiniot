package com.taojin.iot.base.comm;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.taojin.iot.base.comm.utils.CommonUtil;
import com.taojin.iot.base.comm.utils.DatesUtils;

/**
 * 类型:Filter-自定义
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 9:57:10 AM
 * author 王杰
 * ============================================================================
 */
public class Filtercustomer {
	
	/**
	 * 快速检索 按日，周，月过滤 
	 * @param searchType 过滤条件
	 * @param property 检索字段
	 * @param filters 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static void fastsearch(String searchType,String property,List<Filter> filters){
		if(StringUtils.isNotBlank(searchType)){//存在快速检索类型
        	String nowDay = DatesUtils.getStringToday("yyyy-MM-dd");
        	if(StringUtils.equalsIgnoreCase(searchType, "1")){//本周
        		filters.add(new Filter().in(property, CommonUtil.getWeekDays(nowDay)));
            }else if(StringUtils.equalsIgnoreCase(searchType, "2")){//本月
            	filters.add(new Filter().in(property, CommonUtil.getMonthDays(nowDay)));
            }else if(StringUtils.equalsIgnoreCase(searchType, "3")){//上月
            	filters.add(new Filter().in(property, CommonUtil.getBeforeMonthDays(nowDay)));
            }
        }
	}
	
	/**
	 * 时间段检索
	 * @param searchType
	 * @param startTime
	 * @param endTime
	 * @param property
	 * @param filters
	 * @param model
	 */
	@SuppressWarnings("static-access")
	public static void timeSearch(String searchType,String startTime,String endTime,String property,
			List<Filter> filters){
		if(StringUtils.isBlank(searchType) && StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)){
//			String nowDay = DatesUtils.getStringToday("yyyy-MM-dd");
			try {
//				filters.add(new Filter().in(property, CommonUtil.getWeekDays(nowDay, nowDay)));
			} catch (Exception e) {
				e.printStackTrace();
			}
//        	model.addAttribute("startTime",startTime);
//	        model.addAttribute("endTime",endTime);
		}else if (StringUtils.isBlank(searchType) && StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
			try {
				filters.add(new Filter().in(property, CommonUtil.getWeekDays(startTime, endTime)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	

}
