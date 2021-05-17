package com.taojin.iot.base.comm.utils;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;


/**
 * 工具类 - 公用
* ============================================================================
 * 版权所有 无锡淘金网络科技有限公司
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com
 * ============================================================================
 */

public class CommonUtil {

	/**
	 * 随机获取UUID字符串(无中划线)
	 * 
	 * @return UUID字符串
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
	}
	
	/**
	 * 随机获取字符串
	 * 
	 * @param length
	 *            随机字符串长度
	 * 
	 * @return 随机字符串
	 */
	public static String getRandomString(int length) {
		if (length <= 0) {
			return "";
		}
		char[] randomChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
				'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(randomChar[Math.abs(random.nextInt()) % randomChar.length]);
		}
		return stringBuffer.toString();
	}

	/**
	 * 随机获取数字字符串
	 * @param length  随机字符串长度
	 * @return 随机字符串
	 */
	public static String getRandomNumString(int length) {
		if (length <= 0) {
			return "";
		}
		char[] randomChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(randomChar[Math.abs(random.nextInt()) % randomChar.length]);
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 根据指定长度 分隔字符串
	 * 
	 * @param str
	 *            需要处理的字符串
	 * @param length
	 *            分隔长度
	 * 
	 * @return 字符串集合
	 */
	public static List<String> splitString(String str, int length) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < str.length(); i += length) {
			int endIndex = i + length;
			if (endIndex <= str.length()) {
				list.add(str.substring(i, i + length));
			} else {
				list.add(str.substring(i, str.length() - 1));
			}
		}
		return list;
	}

	/**
	 * 将字符串List转化为字符串，以分隔符间隔.
	 * 
	 * @param list
	 *            需要处理的List.
	 *            
	 * @param separator
	 *            分隔符.
	 * 
	 * @return 转化后的字符串
	 */
	public static String toString(List<String> list, String separator) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String str : list) {
			stringBuffer.append(separator + str);
		}
		stringBuffer.deleteCharAt(0);
		return stringBuffer.toString();
	}

	
	/**
	 * 将字符串中的html标签去除
	 */
	public static String stripHtml(String content){
		//<p>段落替换为换行
		content=content.replaceAll("<p.*?>","rn");
		//<br><br/>替换为换行 　
		content=content.replaceAll("<brs*/?>","rn");
		//去掉其它的<>之间的东西 　
		content=content.replaceAll("<.*?>","");
		return content;
	}
	
	/** 
	* 将String型的格式化日期串转换为Date类型 * *
	 @param strDate 经过测试，传进来的参数格式必须和new的 SimpDateFormat格式一致，本例是new SimpleDateFormat("yyyy-MM-dd");


	* @return */
	 public static Date strToDate(String strDate) {//把格式是2012-12-02的日期串转为Date类型 
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
	    ParsePosition pos = new ParsePosition(0); 
	    Date strtodate = formatter.parse(strDate, pos); 
	    return strtodate;

	 }
	 /**
	  * 日期相减得到天数
	  * @param e
	  * @param begindate
	  * @return
	  */
	 public static int getIntervalDays(Date enddate, Date begindate) {
		    long millisecond = enddate.getTime() - begindate.getTime();
		    int day = (int) (millisecond / 24L / 60L / 60L / 1000L);
		    return day;
		  }
	 
	 /**
	  * 日期相加得到天数
	  */
	 public static int getAddDays(Date enddate, Date begindate){
		 long millisecond = enddate.getTime() + begindate.getTime();
		 int day = (int) (millisecond / 24L / 60L / 60L / 1000L);
		 return day;
	 }
	 
	 
	 /**
	  * 获取当前日期
	  * @return
	  * @throws ParseException
	  */
	public static Date GetNowDate() throws ParseException{  
			String temp_str="";   
		    Date dt = new Date();   
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
		    temp_str=sdf.format(dt);   
		    Date date=sdf.parse(temp_str);
		    return date;   
	} 
	
	/**
	 * 获取系统时间 转化为leixing:"yyyy-MM-dd HH:mm:ss"
	 * @param type
	 * @return
	 */
	public static String system_time(String type){
		SimpleDateFormat format = new SimpleDateFormat(type);
		return format.format(new Timestamp(System.currentTimeMillis()));
	}
	    
	/**
	 * 字符转时间格式
	 * @param date1
	 * @return
	 * @throws ParseException
	 */
	public static Date setDateToDate(String date1) throws ParseException{  
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
	    Date date=sdf.parse(date1);
	    return date;   
	} 
	
	/**
	 * 获取数组中的最大值
	 * @param arr
	 * @return
	 */

	 public static int getMax(int[] arr){
	  int max = arr[0];
	  for(int i=1;i<arr.length;i++){
	   if(arr[i] > max){
	    max=arr[i];
	   }
	  }
	  return max;
	 }
	
	/**
	* 获取当前月 时间 leixing：year,month,day
	*/
	public static String now_time_year_weak(String leixing) {
		Calendar a = Calendar.getInstance();

		if (leixing.endsWith("year")) {
			return a.get(Calendar.YEAR) + "";
		}
		if (leixing.endsWith("month")) {
			return (a.get(Calendar.MONTH) + 1) + "";
		}
		if (leixing.endsWith("day")) {
			return a.get(Calendar.DATE) + "";
		}

		return null;
	}
	 
	/**
	 * 获取文件大小
	 * @param fileS
	 * @return
	 */
	
	 public static String FormetFileSize(long fileS) {//转换文件大小
	        DecimalFormat df = new DecimalFormat("#.00");
	        String fileSizeString = "";
	        if (fileS < 1024) {
	            fileSizeString = df.format((double) fileS) + "B";
	        } else if (fileS < 1048576) {
	            fileSizeString = df.format((double) fileS / 1024) + "K";
	        } else if (fileS < 1073741824) {
	            fileSizeString = df.format((double) fileS / 1048576) + "M";
	        } else {
	            fileSizeString = df.format((double) fileS / 1073741824) + "G";
	        }
	        return fileSizeString;
	    }
	 
	 
	 public static boolean isNumber(String str) {
			if(isNOT_Null(str)==false){
				return false;
			}
			Pattern pattern = Pattern.compile("^[0-9]*$");

			return pattern.matcher(str).matches();
	}
	
	 public static boolean isEmail(String str){
		 if(isNOT_Null(str)==false){
				return false;
			}
			Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

			return pattern.matcher(str).matches();
	 }
	 
	 /**
	  * 验证年龄格式(1~99)
	  * @param str
	  * @return
	  */
    public static boolean isAge(String str){
        if(isNOT_Null(str)==false){
            return false;
        }
        Pattern pattern = Pattern.compile("^[1-9]{1,2}$");
        return pattern.matcher(str).matches();
    }
	
	 
 	/**
	 * 判断字符串是否为空
	 */
	public static boolean isNOT_Null(String str) {
		if (str == null) {
			return false;
		}
		if (str == "") {
			return false;
		}
		if (str.equals("")) {
			return false;
		}
		return true;
	}
		
		/**
		 * 验证字符串 是否含有非法字符
		 * @param str
		 * @return
		 */
		public static boolean isLegalString(String str){
			if(!isNOT_Null(str)){
				return false;
			}
			Pattern pattern = Pattern.compile("^[\u4E00-\u9FA5A-Za-z0-9_]?");
			Matcher matcher = pattern.matcher(str);
			return matcher.matches();
		}
		
		/**
		 * 验证金额
		 * @param str
		 * @return
		 */
		public static boolean isBigDecimal(String str) {
			String strs[] = str.split("\\.");
			String weishu = "1";
			if(strs.length >=2){//含小数
				if(strs[1].length() == 1){//小数后一位
					weishu = "1";
				}else if(strs[1].length() == 2){
					weishu = "2";
				}else if(strs[1].length() == 3){
					weishu = "3";
				}else if(strs[1].length() == 4){
					weishu = "4";
				}else if(strs[1].length() == 5){
					weishu = "5";
				}
			}
			Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{"+weishu+"})?$");// 小数点后2位
			Pattern pattern_one = Pattern.compile("^[0-9]+(.[0-9]{1})?$");// 小数点后1位
			if (pattern.matcher(str).matches() == true
					|| pattern_one.matcher(str).matches() == true) {
				return true;
			}
			return false;
		}
		
		
		/**
		 * 计算时间
		 * 时间相差：0天0小时-1分钟-8秒。
		 *调用方法 new Common().dateDiff(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), "2013-02-22 15:27:20", "yyyy-MM-dd HH:mm:ss");
		 * @param startTime
		 * @param endTime
		 * @param format
		 */
		public static String dateDiff(String startTime, String endTime, String format) {

			String resultStr = "";
			// 按照传入的格式生成一个simpledateformate对象

			SimpleDateFormat sd = new SimpleDateFormat(format);

			long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数

			long nh = 1000 * 60 * 60;// 一小时的毫秒数

			long nm = 1000 * 60;// 一分钟的毫秒数

			long ns = 1000;// 一秒钟的毫秒数

			long diff;

			try {

				// 获得两个时间的毫秒时间差异

				diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();

				@SuppressWarnings("unused")
				long day = diff / nd;// 计算差多少天

				long hour = diff % nd / nh;// 计算差多少小时

				long min = diff % nd % nh / nm;// 计算差多少分钟

				long sec = diff % nd % nh % nm / ns;// 计算差多少秒

				// 输出结果

				resultStr = hour+":"+min+":"+sec;
//				System.out.println("时间相差：" + day + "天" + hour + "小时" + min + "分钟"
//						+ sec + "秒。");

			} catch (ParseException e) {

				e.printStackTrace();

			}
			return resultStr;

		}
		
		//获取会议编号
		public static Long getConfNO(){
			Calendar calendar = Calendar.getInstance();
			return calendar.getTime().getTime();
		}
		
		
		/**
	     * 获得指定日期的前一天
	     * 
	     * @param specifiedDay
	     * @return
	     * @throws Exception
	     */
	    public static String getSpecifiedDayBefore(String specifiedDay) {
	        Calendar c = Calendar.getInstance();
	        Date date = null;
	        try {
	            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        c.setTime(date);
	        int day = c.get(Calendar.DATE);
	        c.set(Calendar.DATE, day - 1);

	        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
	                .getTime());
	        return dayBefore;
	    }
	    
	    /**
	     * 判断是否是时间格式
	     * @param type :yyyy-MM-dd HH:mm:ss
	     * @param s
	     * @return
	     */
	    public static boolean isValidDate(String type,String s)
	    {
	        try
	        {
	            SimpleDateFormat dateFormat = new SimpleDateFormat(type);
	            dateFormat.parse(s);
	            return true;
	        }
	        catch (Exception e)
	        {
	            return false;
	        }
	    }
		
	    @SuppressWarnings("unchecked")
		public static List makeArrayObject(Object array) {
			List tem = new ArrayList();
			for (int i = 0; i < Array.getLength(array); i++) {
				tem.add(Array.get(array, i));
			}
			return tem;
		}
	    
	    /**
	     * 求百分比
	     * @param x
	     * @param total
	     * @return
	     */
	    public static String getPercent(Integer x,Integer total){  
	    	   String result="";//接受百分比的值  
	    	   double baiy = x * 1.0;  
	           double baiz = total * 1.0;
	    	   double tempresult=baiy/baiz;
	    	   //NumberFormat nf   =   NumberFormat.getPercentInstance();     注释掉的也是一种方法  
	    	   //nf.setMinimumFractionDigits( 2 );        保留到小数点后几位  
	    	   DecimalFormat df1 = new DecimalFormat("0.00%");    //##.00%   百分比格式，后面不足2位的用0补齐  
	    	   //result=nf.format(tempresult);     
	    	   result= df1.format(tempresult);    
	    	   return result;  
	    }  
	    
	    
		/**
		 * 判断是否是手机号码(+86)
		 * @param mobiles
		 * @return
		 */
		public static boolean isMobile86(String mobiles){
			if(isNOT_Null(mobiles)==false){
				return false;
			}
			Pattern pattern = Pattern.compile("^861[0-9]{10}$");
			Matcher matcher = pattern.matcher(mobiles);
			return matcher.matches();
		}
		
		/**
		 * 判断是否是手机号码
		 * @param mobiles
		 * @return
		 */
		public static boolean isMobile(String mobiles){
			if(isNOT_Null(mobiles)==false){
				return false;
			}
			Pattern pattern = Pattern.compile("^1[34578][0-9]{9}$");
			Matcher matcher = pattern.matcher(mobiles);
			return matcher.matches();
		}
		
		/**
		 * 验证纯数字密码
		 * @param password
		 * @return
		 */
		public static boolean isPassword(String password,int minLen,int maxLen){
			if(isNOT_Null(password)==false){
				return false;
			}
			if(password.length()>maxLen||password.length()<minLen){
				return false;
			}
			Pattern pattern = Pattern.compile("^[0-9]+$");
			Matcher matcher = pattern.matcher(password);
			return matcher.matches();
		}
		
		/**
		 * 验证银行卡卡号
		 * @param num
		 * @return
		 */
		public static boolean isDebitCardNum(String num){
			Pattern pattern = Pattern.compile("^[34569]\\d{16,18}$");
			Matcher matcher = pattern.matcher(num);
			if (matcher.matches()) {
				return matcher.matches();
			}
			return false;
		}
		
		/**
		 * Date 时间转字符串
		 * @param date
		 * @param strFormat 格式。例："yyyy-MM-dd"
		 * @return
		 */
		public static String conversionDateToString(Date date,String strFormat){
			String strDate ="";
			SimpleDateFormat format = new SimpleDateFormat(strFormat);
			strDate =format.format(date);
			return strDate;
		}
		
		/**
		 * 字符串 时间转 Date
		 * @param date
		 * @param strFormat 格式。例："yyyy-MM-dd"
		 * @return
		 */
		public static Date conversionStringToDate(String date,String strFormat)throws ParseException{
			SimpleDateFormat format = new SimpleDateFormat(strFormat);
			return format.parse(date);
		}
		
		/**
		 * 验证字符串字节长度及非法字符
		 * @param str 字符串
		 * @param length 验证字节长度 
		 * @return false 不符合 true 符合
		 */
		public static boolean validStringLength(String str,Integer length){
			if(!isNOT_Null(str.trim())){
				return false;
			}
			if(length!=null&&length<str.getBytes().length){
				return false;
			};
			return true;
		}
		
		/**
		 * 创建16位随机账号
		 * @return
		 */
		public static String createRandomAccount(){
		    return DigestUtils.md5Hex(getRandomString(3)+System.currentTimeMillis()).substring(0, 15);
		}
		
		/**
		 * 根据长度为最后一个数字填充“0”
		 * @param
		 * @return
		 * @author 邵骏晶
		 * @date Feb 25, 2015 5:15:46 PM
		 */
		public static String addStr_0(int length,long currentSn){
			if(length>12){
				length = 12;
			}
			if(length<6){
				length = 6;
			}
			String temp = currentSn+"";
			String pre = ""; 
			int cha = length - temp.length();
			//采用这种方式可以加快速度（生成大量充值卡的时候尽量避免循环）
			switch(cha){
			case 1: 
				pre ="0";
				break;
			case 2: 
				pre ="00";
				break;
			case 3: 
				pre ="000";
				break;
			case 4: 
				pre ="0000";
				break;
			case 5: 
				pre ="00000";
				break;
			case 6: 
				pre ="000000";
				break;
			case 7: 
				pre ="0000000";
				break;
			case 8: 
				pre ="00000000";
				break;
			case 9: 
				pre ="000000000";
				break;
			case 10: 
				pre ="0000000000";
				break;
			case 11: 
				pre ="00000000000";
				break;
			}
			return pre+currentSn;
		}
		
		public  static boolean isMoney(String m){
			if(isNOT_Null(m)==false){
				return false;
			}
			Pattern pattern = Pattern.compile("^(([1-9][0-9]{0,3})|([0-9]))(.[0-9]{1,2})?$");
			return pattern.matcher(m).matches();
		} 
		
		
		public static List<String> getWeekDays(String start,String end){
		    List<String> list = new ArrayList<String>();
		    list.add(start);
		    if(start.equals(end)){
		        return list;
		    }

		    String temp = start;
		    while(!temp.equals(end)){
		        try {
					temp = conversionDateToString(new Date(conversionStringToDate(temp, "yyyy-MM-dd").getTime()+(long)(24*3600*1000)),"yyyy-MM-dd");
				} catch (ParseException e) {
					break;
				} 
		        list.add(temp);
		    }
		    
		    return list;
		}
		
		/**
		 * ip验证
		 * @param ipAddress
		 * @return
		 */
		public static boolean isIpv4(String ipAddress) {

			String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				    +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				    +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				    +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

			Pattern pattern = Pattern.compile(ip);
			Matcher matcher = pattern.matcher(ipAddress);
			return matcher.matches();
		}
		
		/**
		 * 获取http ip
		 * @param request
		 * @return
		 */
		public static String getIp(HttpServletRequest request) {
	           String ip = request.getHeader("X-Forwarded-For");
	           if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
	               //多次反向代理后会有多个ip值，第一个ip才是真实ip
	               int index = ip.indexOf(",");
	               if(index != -1){
	                   return ip.substring(0,index);
	               }else{
	                   return ip;
	               }
	           }
	           ip = request.getHeader("X-Real-IP");
	           if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
	               return ip;
	           }
	           return request.getRemoteAddr();
	      }
		
		/**
		 * 获取指定日期所在周所有日期
		 * @param days
		 * @return List<String>
		 */
		public static List<String> getWeekDays(String day){
			// 定义输出日期格式
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date currentDate = DatesUtils.stringToDate(day, "yyyy-MM-dd");
	        List<String> dayList = new ArrayList<String>();
	        // 比如今天是2012-12-25
	        List<Date> days = dateToWeek(currentDate);
	        for (Date date : days) {
	        	dayList.add(sdf.format(date));
	        }
	        return dayList;
		}
		
		/**
	     * 根据日期获得所在周的日期 
	     * @param mdate
	     * @return
	     */
	    @SuppressWarnings("deprecation")
	    public static List<Date> dateToWeek(Date mdate) {
	        int b = mdate.getDay();
	        Date fdate;
	        List<Date> list = new ArrayList<Date>();
	        Long fTime = mdate.getTime() - b * 24 * 3600000;
	        for (int a = 1; a <= 7; a++) {
	            fdate = new Date();
	            fdate.setTime(fTime + (a * 24 * 3600000));
	            list.add(a-1, fdate);
	        }
	        return list;
	    }
	    
	    /**
	     * 根据日期获取当月所有日期
	     * @param day
	     * @return
	     */
	    public static List<String> getMonthDays(String day){
			// 定义输出日期格式
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date currentDate = DatesUtils.stringToDate(day, "yyyy-MM-dd");
	        List<String> dayList = new ArrayList<String>();
	        
	        List<Date> days = getAllTheDateOftheMonth(currentDate);
	        for (Date date : days) {
	        	dayList.add(sdf.format(date));
	        }
	        return dayList;
		}
	    
	    /**
	     * 根据日期获得所在月的所有日期
	     * @param date
	     * @return
	     */
	    private static List<Date> getAllTheDateOftheMonth(Date date) {
	    	List<Date> list = new ArrayList<Date>();
	    	Calendar cal = Calendar.getInstance();
	    	cal.setTime(date);
	    	cal.set(Calendar.DATE, 1);

	    	int month = cal.get(Calendar.MONTH);
	    	while(cal.get(Calendar.MONTH) == month){
	    	list.add(cal.getTime());
	    	cal.add(Calendar.DATE, 1);
	    	}
	    	return list;
	    }
	    
	    /**
	     * 获取上月所有日期
	     * @param day
	     * @return
	     */
	    public static List<String> getBeforeMonthDays(String day){
			// 定义输出日期格式
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String[] dayss = day.split("\\-");
	        String beforeMonthDate = getLastDayOfMonth(Integer.parseInt(dayss[0]),Integer.parseInt(dayss[1]));
	        List<String> dayList = new ArrayList<String>();
	        
	        List<Date> days = getAllTheDateOftheMonth(DatesUtils.stringToDate(beforeMonthDate, "yyyy-MM-dd"));
	        for (Date date : days) {
	        	dayList.add(sdf.format(date));
	        }
	        return dayList;
		}
	    
	    /**
	     * 获取某月的最后一天
	     * @Title:getLastDayOfMonth
	     * @Description:
	     * @param:@param year
	     * @param:@param month
	     * @param:@return
	     * @return:String
	     * @throws
	     */
	    public static String getLastDayOfMonth(int year,int month)
	    {
	        Calendar cal = Calendar.getInstance();
	        //设置年份
	        cal.set(Calendar.YEAR,year);
	        //设置月份
	        cal.set(Calendar.MONTH, month-2);
	        //获取某月最大天数
	        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        //设置日历中月份的最大天数
	        cal.set(Calendar.DAY_OF_MONTH, lastDay);
	        //格式化日期
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String lastDayOfMonth = sdf.format(cal.getTime());
	         
	        return lastDayOfMonth;
	    }
	    
	    /**
	     * 根据日期获取当月所有日期
	     * @param day
	     * @return
	     */
	    public static List<String> getYeads(String day){
			// 定义输出日期格式
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        List<String> dayList = new ArrayList<String>();
	        
	        List<Date> days = getAllYeads(day);
	        for (Date date : days) {
	        	dayList.add(sdf.format(date));
	        }
	        return dayList;
		}
	    
	    /**
	     * 获取当前年份所有日期
	     * @param year yyyy
	     * @return
	     */
	    public static List<Date> getAllYeads(String nowDate){
	    	String[] nowDates = nowDate.split("\\-");
	    	List<Date> allDateOfYear = new ArrayList<Date>();
	    	for(int i=1;i<=12;i++){
	    		String datetime = nowDates[0]+"-"+i+"-01";
	    		List<Date> getAllTheDateOftheMonth = getAllTheDateOftheMonth(DatesUtils.stringToDate(datetime, "yyyy-MM-dd"));
	    		allDateOfYear.addAll(getAllTheDateOftheMonth);
	    	}
	    	
	    	return allDateOfYear;
	    }
		
	    /**
	     * 判断非空
	     * @param val
	     * @return
	     */
	    public static boolean isNotBlank(Object val){
	    	if(val != null && !"".equals(val.toString().trim())){
	    		return true;
	    	}else{
	    		return false;
	    	}
	    }
}