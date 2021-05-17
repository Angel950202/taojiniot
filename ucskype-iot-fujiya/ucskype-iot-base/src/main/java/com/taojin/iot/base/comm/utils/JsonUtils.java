package com.taojin.iot.base.comm.utils;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taojin.iot.base.comm.DwzReturn;

/**
 * 类型:Utils-JSON
 * json数据处理
 * ============================================================================
 * 版权所有 2013-2015 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * date Apr 20, 2015 8:59:51 PM
 * version v1.0
 * author 王杰
 * ============================================================================
 */
public final class JsonUtils {

	/** ObjectMapper */
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 不可实例化
	 */
	private JsonUtils() {
	}

	/**
	 * 将对象转换为JSON字符串
	 * 
	 * @param value
	 *            对象
	 * @return JSOn字符串
	 */
	public static String toJson(Object value) {
		try {
			return mapper.writeValueAsString(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json
	 *            JSON字符串
	 * @param valueType
	 *            对象类型
	 * @return 对象
	 */
	public static <T> T toObject(String json, Class<T> valueType) {
		Assert.hasText(json);
		Assert.notNull(valueType);
		try {
			return mapper.readValue(json, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json
	 *            JSON字符串
	 * @param typeReference
	 *            对象类型
	 * @return 对象
	 */
	public static <T> T toObject(String json, TypeReference<?> typeReference) {
		Assert.hasText(json);
		Assert.notNull(typeReference);
		try {
			return mapper.readValue(json, typeReference);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json
	 *            JSON字符串
	 * @param javaType
	 *            对象类型
	 * @return 对象
	 */
	public static <T> T toObject(String json, JavaType javaType) {
		Assert.hasText(json);
		Assert.notNull(javaType);
		try {
			return mapper.readValue(json, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将对象转换为JSON流
	 * 
	 * @param writer
	 *            writer
	 * @param value
	 *            对象
	 */
	public static void writeValue(Writer writer, Object value) {
		try {
			mapper.writeValue(writer, value);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对象转JSOn
	 * @param dwzReturn 
	 * @return json
	 */
	public static String jsonMap(DwzReturn dwzReturn) {
		try {
			return mapper.writeValueAsString(dwzReturn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * json字符串转map
	 * @param json
	 * @return map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> jsonMap(String json){
		try {
			Map<String,String> map = mapper.readValue(json, Map.class);
			return map;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String stringToJson(String s) {
		if (s == null) {
			return nullToJson();
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
//				sb.append("\\/");
				sb.append("/");
				break;
			default:
				if (ch >= '\u0000' && ch <= '\u001F') {
					String ss = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - ss.length(); k++) {
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				} else {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}

	public static String nullToJson() {
		return " ";
	}

	public static String objectToJson(Object obj) {
		StringBuilder json = new StringBuilder();
		if (obj == null) {
			json.append("\"\"");
		} else if (obj instanceof Number) {
			json.append(numberToJson((Number) obj));
		}
		else if (obj instanceof Boolean) {
			json.append(booleanToJson((Boolean) obj));
		} else if (obj instanceof String) {
			json.append("\"").append(stringToJson(obj.toString())).append("\"");
		} else if (obj instanceof Object[]) {
			json.append(arrayToJson((Object[]) obj));
		} else if (obj instanceof List) {
			json.append(listToJson((List<?>) obj));
		} else if (obj instanceof Map) {
			json.append(mapToJson((Map<?, ?>) obj));
		} else if (obj instanceof Set) {
			json.append(setToJson((Set<?>) obj));
		}else if (obj instanceof Date) {
			json.append("\"").append(dateToJson((Date) obj)).append("\"");
		} 
		 else {
			json.append(beanToJson(obj));
		}
		return json.toString();
	}

	public static String numberToJson(Number number) {
		return number.toString();
	}
	
	

	public static String booleanToJson(Boolean bool) {
		return bool.toString();
	}
	
	public static String dateToJson(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * @param bean
	 *            bean对象
	 * @return String
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String beanToJson(Object bean) {
		StringBuilder build=new StringBuilder();
		        build.append("{");
		        Class cla=null;
		        try {
		            //反射加载类
		            cla=Class.forName(bean.getClass().getName());
		        } catch (ClassNotFoundException e) {
		            System.out.println(bean.getClass().toString().concat(" 未找到这个类"));
		            e.printStackTrace();
		            return null;
		        }

		        StringBuffer methodname=new StringBuffer();

		        //获取java类的变量

		        Field[] fields=cla.getDeclaredFields();
		        String separate="";
		        for(Field temp:fields){
		        	//过滤
		        	if(!"serialVersionUID".equals(temp.getName()) 
		        			&& !"java.util.Set".equals(temp.getType().getName())){
		            build.append(separate);
		            build.append("\"");
		            build.append(temp.getName());
		            build.append("\":");
		             
		            methodname.append("get");
		            methodname.append(temp.getName().substring(0,1).toUpperCase());
		            methodname.append(temp.getName().substring(1));
		             
		            Method method=null;
		            
		                //获取java的get方法
		                try {
							method=cla.getMethod(methodname.toString());
						} catch (SecurityException e1) {
							e1.printStackTrace();
						} catch (NoSuchMethodException e1) {
							e1.printStackTrace();
						}
		           
					Object invoke = null;
		            try {
		                //执行get方法，获取变量参数的值。
		                invoke = method.invoke(bean);
		            } catch (IllegalAccessException e) {
		                e.printStackTrace();
		            } catch (IllegalArgumentException e) {
		                e.printStackTrace();
		            } catch (InvocationTargetException e) {
		                e.printStackTrace();
		            } finally {
	                    if (invoke == null) {
	                        build.append(invoke);
	                    } else {
	                        build.append("\"");
	                        build.append(invoke);
	                        build.append("\"");
	                    }
	                }
		 
		            methodname.setLength(0);
		            separate=",";
		        }
			}
		    build.append("}");
		  return build.toString();
	}

	/**
	 * @param list
	 *            list对象
	 * @return String
	 */
	public static String listToJson(List<?> list) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(objectToJson(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		
		return json.toString();
	}

	public static String listToJson(List<?> list, String jsonName) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(objectToJson(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
			// json.append("}");
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * @param array
	 *            对象数组
	 * @return String
	 */
	public static String arrayToJson(Object[] array) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (array != null && array.length > 0) {
			for (Object obj : array) {
				json.append(objectToJson(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * @param map
	 *            map对象
	 * @return String
	 */
	public static String mapToJson(Map<?, ?> map) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		if (map != null && map.size() > 0) {
			for (Object key : map.keySet()) {
				json.append(objectToJson(key));
				json.append(":");
				json.append(objectToJson(map.get(key)));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	/**
	 * @param set
	 *            集合对象
	 * @return String
	 */
	public static String setToJson(Set<?> set) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (set != null && set.size() > 0) {
			for (Object obj : set) {
				json.append(objectToJson(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}
	
	
	/**
	 * 返回错误信息
	 */
	public static String errorMsg(String errCode,String message){
		String resultStr = "";
		String[] methodArray = new String[2];
		String[] strArray = new String[2];
		
		methodArray[0] = "errcode";
		strArray[0] = "\""+errCode+"\"";
		methodArray[1] = "errmsg";
		strArray[1] = "\""+message+"\"";
		
		resultStr = new Jsonp().toJsonp(strArray, null, methodArray);
		resultStr = resultStr.replace("\\r\\n", "");
		return resultStr;
	}


	/**
	 * 返回正确信息
	 */
	public static String successMsg(String errcode,String message){
		String resultStr = "";
		String[] methodArray = new String[2];
		String[] strArray = new String[2];
		
		methodArray[0] = "errcode";
		strArray[0] = "\""+errcode+"\"";
		methodArray[1] = "errmsg";
		strArray[1] = "\""+message+"\"";
		
		resultStr = new Jsonp().toJsonp(strArray, null, methodArray);
		resultStr = resultStr.replace("\\r\\n", "");
		return resultStr;
	}
	
	
	/**
	 * 返回正确信息(集合)
	 * @param pagerCount 
	 */
	@SuppressWarnings("rawtypes")
	public static String truePagerMsg(int total,int pagerCount, List list,String message,String errcode){
		String resultStr = "";
		String[] strArray = new String[5];
		String[] methodArray = new String[5];
		
		methodArray[0] = "errcode";
		methodArray[1] = "errmsg";
		methodArray[2] = "value";
		methodArray[3] = "total";
		methodArray[4] = "pagerNum";
		
		strArray[0] = "\""+errcode+"\"";
		strArray[1] = "\""+message+"\"";
		strArray[2] = JsonUtils.listToJson(list);
		strArray[3] = String.valueOf(total);
		strArray[4] = String.valueOf(pagerCount);
		
		resultStr = new Jsonp().toJsonp(strArray, null, methodArray);
		resultStr = resultStr.replace("\\r\\n", "");
		
		return resultStr;
	}
	
	/**
	 *  返回正确信息(集合) 多对象
	 * @param total
	 * @param pagerCount
	 * @param list
	 * @param message
	 * @param errcode
	 * @return
	 */
	public static String truePagersMsg(int total,int pagerCount, String jsons,String message,String errcode){
		String resultStr = "";
		String[] strArray = new String[5];
		String[] methodArray = new String[5];
		
		methodArray[0] = "errcode";
		methodArray[1] = "errmsg";
		methodArray[2] = "value";
		methodArray[3] = "total";
		methodArray[4] = "pagerNum";
		
		strArray[0] = "\""+errcode+"\"";
		strArray[1] = "\""+message+"\"";
		strArray[2] = jsons;
		strArray[3] = String.valueOf(total);
		strArray[4] = String.valueOf(pagerCount);
		
		resultStr = new Jsonp().toJsonp(strArray, null, methodArray);
		resultStr = resultStr.replace("\\r\\n", "");
		
		return resultStr;
	}
	
	
	/**
	 * 返回正确信息(对象)
	 */
	public static String trueObjectMsg(int total,Object object,String message,String errcode){
		String resultStr = "";
		String[] strArray = new String[4];
		String[] methodArray = new String[4];
		
		methodArray[0] = "errcode";
		methodArray[1] = "errmsg";
		methodArray[2] = "value";
		methodArray[3] = "total";
		
		strArray[0] = "\""+errcode+"\"";
		strArray[1] = "\""+message+"\"";
		strArray[2] = JsonUtils.beanToJson(object);
		strArray[3] = String.valueOf(total);
		
		resultStr = new Jsonp().toJsonp(strArray, null, methodArray);
		resultStr = resultStr.replace("\r\n", "");
		return resultStr;
	}
	
	/**
	 * 返回集合对象（非分页）
	 * @param total
	 * @param pagerCount
	 * @param list
	 * @param message
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String trueListMsg(int total,String errorCode, List list,String message){
		String resultStr = "";
		String[] strArray = new String[4];
		String[] methodArray = new String[4];
		
		methodArray[0] = "errcode";
		methodArray[1] = "errmsg";
		methodArray[2] = "value";
		methodArray[3] = "total";
		
		strArray[0] = "\""+errorCode+"\"";
		strArray[1] = "\""+message+"\"";
		strArray[2] = JsonUtils.listToJson(list);
		strArray[3] = String.valueOf(total);
		
		resultStr = new Jsonp().toJsonp(strArray, null, methodArray);
		resultStr = resultStr.replace("\\r\\n", "");
		
		return resultStr;
	}
	

}