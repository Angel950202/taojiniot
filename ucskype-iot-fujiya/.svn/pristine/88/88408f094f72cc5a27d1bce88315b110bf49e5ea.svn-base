package com.taojin.iot.base.comm;

import java.util.HashMap;
import java.util.Map;

/**
 * 类型:工具类--hash表 验证码 
 * ============================================================================
 * 版权所有 2013-2014 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.UcSkype.com/
 * ----------------------------------------------------------------------------
 * date Mar 4, 2015 1:58:59 PM 
 * author 邵骏晶
 * ============================================================================
 */

public class MemoryCode {

	public static Map<String,Object> mapCode = new HashMap<String,Object>();
	
	/**
	 * 获取
	 * @param
	 * @return
	 * @author 邵骏晶
	 * @date Mar 4, 2015 2:02:00 PM
	 */
	public static Object get(String key){
		return mapCode.get(key);
	}
	
	/**
	 * 写入
	 * @param
	 * @return
	 * @author 邵骏晶
	 * @date Mar 4, 2015 2:02:20 PM
	 */
	public static void put(String key,Object value){
		mapCode.put(key, value);
	}
	
	/**
	 * 判断键值是否存在
	 * @return
	 */
	public static boolean isExist(String key){
		return mapCode.containsKey(key);
	}
	
	/**
	 * 删除key
	 * @param
	 * @return
	 * @author 邵骏晶
	 * @date Mar 4, 2015 2:38:33 PM
	 */
	public static void delete(String key){
		mapCode.remove(key);
	}
	
}
