import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import com.taojin.iot.base.comm.utils.DatesUtils;



public class test {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Calendar c = Calendar.getInstance();
//    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        //过去七天
//        c.setTime(new Date());
//        c.add(Calendar.DATE, - 7);
//        Date d = c.getTime();
//        String start = format.format(d);
//    	String end = DatesUtils.getYestoday();
//    	List<String> days = DatesUtils.getMondayNumber(start, end, "yyyy-MM-dd");
//    	for (String string : days) {
//			System.out.println(string);
//		}
		
//		String today = DatesUtils.getToday();
//		String now = DatesUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
//		System.out.println(now);
//		String a = today +" 19:40:50";
//		System.out.println(DatesUtils.calLastedTime(DatesUtils.stringToDate(a, "yyyy-MM-dd HH:mm:ss")));
		
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("value", 20);
		jsonObject.put("lastDate", DatesUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		jsonObject.put("startDate", DatesUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		jsonObject.put("count", 1);//次数
		jsonObject.put("addressId", 38);
		jsonObject.put("addressType", "NOK件");
		jsonObject.put("timeLong", 0);//时长
		System.out.println(jsonObject.toString());

	}
	

}
