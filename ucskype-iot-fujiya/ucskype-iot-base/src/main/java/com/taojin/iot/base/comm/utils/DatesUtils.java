package com.taojin.iot.base.comm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.type.YesNoType;

/**
 * 类型:工具类-日期
 * ============================================================================
 * 版权所有 2013-2014 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.UcSkype.com/
 * ----------------------------------------------------------------------------
 * date Dec 8, 2014 12:53:40 PM author 王杰
 * ============================================================================
 */
public class DatesUtils {
	public static void main(String args[]) {
		List<String> getMondayNumber = getMondayNumber("2020-05-06", "2020-06-09", "yyyy-MM-dd");
		for (String string : getMondayNumber) {
			System.out.println(string);
		}
	}

	public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
		//设置当前时间
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);
		//设置开始时间
		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);
		//设置结束时间
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		//处于开始时间之后，和结束时间之前的判断
		if (date.after(begin) && date.before(end)) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isToday(Date inputJudgeDate) {
		boolean flag = false;
		// 获取当前系统时间
		long longDate = System.currentTimeMillis();
		Date nowDate = new Date(longDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String format = dateFormat.format(nowDate);
		String subDate = format.substring(0, 10);
		// 定义每天的24h时间范围
		String beginTime = subDate + " 00:00:00";
		String endTime = subDate + " 23:59:59";
		Date paseBeginTime = null;
		Date paseEndTime = null;
		try {
			paseBeginTime = dateFormat.parse(beginTime);
			paseEndTime = dateFormat.parse(endTime);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (inputJudgeDate.after(paseBeginTime)
				&& inputJudgeDate.before(paseEndTime)) {
			flag = true;
		}
		return flag;
	}

	public static String DEFAULT_FORMAT = "yyyy-MM-dd";

	public static String getAgeByBirth(String birthday) {
		int age = 0;
		try {
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());// 当前时间

			Calendar birth = Calendar.getInstance();
			birth.setTime(stringToDate(birthday, "yyyy-MM-dd"));

			if (birth.after(now)) {// 如果传入的时间，在当前时间的后面，返回0岁
				age = 0;
			} else {
				age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
				if (now.get(Calendar.DAY_OF_YEAR) > birth
						.get(Calendar.DAY_OF_YEAR)) {
					age += 1;
				}
			}
			return age + "";
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 获取今天
	 * 
	 * @return String
	 * */
	public static String getToday() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/**
	 * 获取昨天
	 * 
	 * @return String
	 * */
	public static String getYestoday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date time = cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(time);
	}

	/**
	 * 获取明天
	 * 
	 * @return String
	 * */
	public static String getTomorrow() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, +1);
		Date time = cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(time);
	}

	/**
	 * 获取本月开始日期
	 * 
	 * @return String
	 * **/
	public static String getMonthStart() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date time = cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 00:00:00";
	}
	/**
	 * 获取本月开始日期
	 * 
	 * @return String
	 * **/
	public static String getMonthStart2() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date time = cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(time);
	}

	/**
	 * 获取本月最后一天
	 * 
	 * @return String
	 * **/
	public static String getMonthEnd() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date time = cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 23:59:59";
	}
	
	/**
	 * 获取本月最后一天
	 * 
	 * @return String
	 * **/
	public static String getMonthEnd2() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date time = cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(time);
	}

	/**
	 * 获取本周的第一天
	 * 
	 * @return String
	 * **/
	public static String getWeekStart() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_MONTH, 0);
		cal.set(Calendar.DAY_OF_WEEK, 2);
		Date time = cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 00:00:00";
	}
	
	/**
	 * 获取本周的第一天
	 * 
	 * @return String
	 * **/
	public static String getWeekStart2() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_MONTH, 0);
		cal.set(Calendar.DAY_OF_WEEK, 2);
		Date time = cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(time);
	}

	/**
	 * 获取本周的最后一天
	 * 
	 * @return String
	 * **/
	public static String getWeekEnd() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK,
				cal.getActualMaximum(Calendar.DAY_OF_WEEK));
		cal.add(Calendar.DAY_OF_WEEK, 1);
		Date time = cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 23:59:59";
	}
	
	/**
	 * 获取本周的最后一天
	 * 
	 * @return String
	 * **/
	public static String getWeekEnd2() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK,
				cal.getActualMaximum(Calendar.DAY_OF_WEEK));
		cal.add(Calendar.DAY_OF_WEEK, 1);
		Date time = cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(time);
	}

	/**
	 * 获取本年的第一天
	 * 
	 * @return String
	 * **/
	public static String getYearStart() {
		return new SimpleDateFormat("yyyy").format(new Date())
				+ "-01-01 00:00:00";
	}
	
	/**
	 * 获取本年的第一天
	 * 
	 * @return String
	 * **/
	public static String getYearStart2() {
		return new SimpleDateFormat("yyyy").format(new Date())
				+ "-01-01";
	}

	/**
	 * 获取本年的最后一天
	 * 
	 * @return String
	 * **/
	public static String getYearEnd() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date currYearLast = calendar.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(currYearLast)
				+ " 23:59:59";
	}
	
	/**
	 * 获取本年的最后一天
	 * 
	 * @return String
	 * **/
	public static String getYearEnd2() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date currYearLast = calendar.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(currYearLast);
	}

	/**
	 * 获取当前季度
	 * 
	 */
	public static int getQuarter() {
		Calendar c = Calendar.getInstance();
		int month = c.get(c.MONTH) + 1;
		int quarter = 0;
		if (month >= 1 && month <= 3) {
			quarter = 1;
		} else if (month >= 4 && month <= 6) {
			quarter = 2;
		} else if (month >= 7 && month <= 9) {
			quarter = 3;
		} else {
			quarter = 4;
		}
		return quarter;
	}

	/**
	 * 获取某季度的第一天和最后一天
	 * 
	 * @param num第几季度
	 */
	public static String[] getCurrQuarter(int num) {
		String[] s = new String[2];
		String str = "";
		// 设置本年的季
		Calendar quarterCalendar = null;
		switch (num) {
		case 1: // 本年到现在经过了一个季度，在加上前4个季度
			quarterCalendar = Calendar.getInstance();
			quarterCalendar.set(Calendar.MONTH, 3);
			quarterCalendar.set(Calendar.DATE, 1);
			quarterCalendar.add(Calendar.DATE, -1);
			str = dateToString(quarterCalendar.getTime(), "yyyy-MM-dd");
			s[0] = str.substring(0, str.length() - 5) + "01-01";
			s[1] = str;
			break;
		case 2: // 本年到现在经过了二个季度，在加上前三个季度
			quarterCalendar = Calendar.getInstance();
			quarterCalendar.set(Calendar.MONTH, 6);
			quarterCalendar.set(Calendar.DATE, 1);
			quarterCalendar.add(Calendar.DATE, -1);
			str = dateToString(quarterCalendar.getTime(), "yyyy-MM-dd");
			s[0] = str.substring(0, str.length() - 5) + "04-01";
			s[1] = str;
			break;
		case 3:// 本年到现在经过了三个季度，在加上前二个季度
			quarterCalendar = Calendar.getInstance();
			quarterCalendar.set(Calendar.MONTH, 9);
			quarterCalendar.set(Calendar.DATE, 1);
			quarterCalendar.add(Calendar.DATE, -1);
			str = dateToString(quarterCalendar.getTime(), "yyyy-MM-dd");
			s[0] = str.substring(0, str.length() - 5) + "07-01";
			s[1] = str;
			break;
		case 4:// 本年到现在经过了四个季度，在加上前一个季度
			quarterCalendar = Calendar.getInstance();
			str = dateToString(quarterCalendar.getTime(), "yyyy-MM-dd");
			s[0] = str.substring(0, str.length() - 5) + "10-01";
			s[1] = str.substring(0, str.length() - 5) + "12-31";
			break;
		}
		return s;
	}

	/**
	 * 得到当前月的所有天
	 */
	public static List<String> getDayListOfMonth() {
		List<String> list = new ArrayList<String>();
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		int year = aCalendar.get(Calendar.YEAR);// 年份
		int month = aCalendar.get(Calendar.MONTH) + 1;// 月份
		int day = aCalendar.getActualMaximum(Calendar.DATE);
		String monthStr = "0";
		if (month < 10) {
			monthStr = "0" + month;
		} else {
			monthStr = String.valueOf(month);
		}
		for (int i = 1; i <= day; i++) {
			String days = "0";
			if (i < 10) {
				days = "0" + i;
			} else {
				days = String.valueOf(i);
			}
			String aDate = String.valueOf(year) + "年" + monthStr + "月" + days
					+ "日";
			// SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");

			// Date date = sp.parse(aDate);

			list.add(aDate);
		}
		return list;
	}

	/**
	 * 得到当前时间
	 * 
	 * @param dateFormat
	 *            时间格式
	 * @return 转换后的时间格式
	 */
	public static String getStringToday(String dateFormat) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 将字符串型日期转换成日期
	 * 
	 * @param dateStr
	 *            字符串型日期
	 * @param dateFormat
	 *            日期格式
	 * @return
	 */
	public static Date stringToDate(String dateStr, String dateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		try {
			return formatter.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 日期转字符串
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String dateToString(Date date, String dateFormat) {
		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(date);
	}

	/**
	 * 两个时间点的间隔时长（分钟）
	 * 
	 * @param before
	 *            开始时间
	 * @param after
	 *            结束时间
	 * @return 两个时间点的间隔时长（分钟）
	 */
	public static long compareMin(Date before, Date after) {
		if (before == null || after == null) {
			return 0l;
		}
		long dif = 0;
		if (after.getTime() >= before.getTime()) {
			dif = after.getTime() - before.getTime();
		} else if (after.getTime() < before.getTime()) {
			dif = after.getTime() + 86400000 - before.getTime();
		}
		dif = Math.abs(dif);
		return dif / 60000;
	}

	public static Long compareS(Date before, Date after) {
		if (before == null || after == null) {
			return 0L;
		}
		long dif = 0;
		if (after.getTime() >= before.getTime()) {
			dif = after.getTime() - before.getTime();
		} else if (after.getTime() < before.getTime()) {
			dif = after.getTime() + 86400000 - before.getTime();
		}
		dif = Math.abs(dif);
		return dif / 1000L;
	}

	/**
	 * 获取指定时间间隔分钟后的时间
	 * 
	 * @param date
	 *            指定的时间
	 * @param min
	 *            间隔分钟数
	 * @return 间隔分钟数后的时间
	 */
	public static Date addMinutes(Date date, int min) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, min);
		return calendar.getTime();
	}

	/**
	 * 根据时间返回指定术语，自娱自乐，可自行调整
	 * 
	 * @param hourday
	 *            小时
	 * @return
	 */
	public static String showTimeView(int hourday) {
		if (hourday >= 22 && hourday <= 24) {
			return "晚上";
		} else if (hourday >= 0 && hourday <= 6) {
			return "凌晨";
		} else if (hourday > 6 && hourday <= 12) {
			return "上午";
		} else if (hourday > 12 && hourday < 22) {
			return "下午";
		}
		return null;
	}

	/**
	 * 根据时期段获取指定日期
	 * 
	 * @param startTime
	 *            起始时间
	 * @param days
	 *            日期
	 * @return
	 * @throws ParseException
	 */
	public static String dateChanageFormat(String startTime, int days)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = sdf.parse(startTime);
		long day = 0x5265c00L;
		long day_ = day * days;
		long fTime = startDate.getTime() + day_;
		Date newDate = new Date(fTime);
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(newDate);
	}

	/**
	 * 根据时期段获取指定日期
	 * 
	 * @param startTime
	 *            起始时间
	 * @param days
	 *            日期
	 * @return
	 * @throws ParseException
	 */
	public static String dateChanageFormats(String startTime, int days)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = sdf.parse(startTime);
		long day = 0x5265c00L;
		long day_ = day * days;
		long fTime = startDate.getTime() + day_;
		Date newDate = new Date(fTime);
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(newDate);
	}

	/**
	 * 判断是否是时间格式
	 * 
	 * @param type
	 *            :yyyy-MM-dd HH:mm:ss
	 * @param s
	 * @return
	 */
	public static boolean isValidDate(String type, String s) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(type);
			dateFormat.parse(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 两个日期相减 获取天数
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static int dateCompare(String startTime, String endTime)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(sdf.parse(startTime));
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(sdf.parse(endTime));
		long l = cal2.getTimeInMillis() - cal1.getTimeInMillis();
		int days = (new Long(l / 0x5265c00L)).intValue();
		return days;
	}

	/**
	 * 两个日期相减 获取时间差
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static String dateCompare1(Date date, String endTime)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(sdf.parse(endTime));

		if (cal1.after(cal2)) {
			long l = cal1.getTimeInMillis() - cal2.getTimeInMillis();
			long days = l / (1000 * 60 * 60 * 24);
			return "超过" + days;
		} else {
			long l = cal2.getTimeInMillis() - cal1.getTimeInMillis();
			long days = l / (1000 * 60 * 60 * 24);
			return "剩余" + days;
		}
	}

	/**
	 * 时间相减，得到秒数
	 * @param startDate
	 * @return
	 */
	public static long calLastedTime(Date startDate) {
		long a = new Date().getTime();
		long b = startDate.getTime();
		long c = ((a - b) / 1000);
		return c;
	}
	
	/**
	 * 获取开始时间
	 * @param hour
	 * @return
	 */
	public static String getBeginTime(int hour){
		String time = null;
		if (hour >= 7) {//白班
			time = getToday() +" 07:00:00";
		}else{
			time = getYestoday() +" 07:00:00";
		}
		return time;
	}
	
	/**
	 * 查询日期间有几天一周中的每一天 日期格式 yyyy-MM-dd yyyy-MM-dd(表示周一到周日)
	 * @return map<monday,次数>
	 */
	public static List<String> getMondayNumber(String startDay, String endDay,String format) {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		try {
			Date startDate = f.parse(startDay);
			Date endDate = f.parse(endDay);
			start.setTime(startDate);
			end.setTime(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (start.compareTo(end) <= 0) {
			list.add(sdf.format(start.getTime()));
			// 循环，每次天数加1
			start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);
		}
		return list;
	}
}
