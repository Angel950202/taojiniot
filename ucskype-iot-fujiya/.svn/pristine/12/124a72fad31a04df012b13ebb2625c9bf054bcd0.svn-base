package com.taojin.iot.transmit.utils;


import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * 本项目专用 16进制 工具类 类型:HexUtli
 * ============================================================================
 * 版权所有 2013-2017无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 2017年12月27日
 * 
 * @author sjn
 *         ==================================================================
 *         ==========
 */
public class HexUtil {

	public static void main(String[] args) {
		System.out.println(HexUtil.hexDesc("DB201.DBW160"));
		//System.out.println(CommonCommand.hex2Int("DB201.DBW160"));
	}
	static final Pattern UNICODE = Pattern.compile("\\\\u([0-9a-fA-F]{4})"); 

	public static String decodeUnicodeEscape(String s) { 
	    Matcher m = UNICODE.matcher(s); 
	    StringBuffer sb = new StringBuffer(); 
	    while (m.find()) 
	     m.appendReplacement(sb, 
	      String.valueOf((char)Integer.parseInt(m.group(1), 16))); 
	    m.appendTail(sb); 
	    return sb.toString(); 
	} 
	
	public static String decToHex(String hexStr) {
		String regex = "(.{2})";
        String temp = hexStr.replaceAll (regex, "$1 ");
		List<String> list = Arrays.asList(temp.split(" "));
		Collections.reverse(list);

		StringBuffer stringBuffer = new StringBuffer();
		for (String string : list) {
			stringBuffer.append(string);
		}
		temp = stringBuffer.toString();
		return temp;
	}

	/**
	 * 低位在前，高位在后转换
	 * 
	 * @param datalengthHex
	 *            16进制字符
	 * @return
	 */
	public static String lowSwop(String datalengthHex) {
		if (datalengthHex.length() % 4 != 0) {
			int add0len = 4 - datalengthHex.length() % 4;
			for (int i = 0; i < add0len; i++) {
				datalengthHex = "0" + datalengthHex;
			}
		}
		return higtLowSwop(datalengthHex);
	}

	/**
	 * 16进制数 高低位转换
	 * 
	 * @param protectionHexStr
	 * @return
	 */
	public static String higtLowSwop(String hexStr) {
		if (!hexStr.matches("[0-9a-fA-F]+") || hexStr.length() % 4 != 0) {// 16进制
																			// 2size
																			// 1位
			throw new IllegalArgumentException("参数应是一个十六进制字符串,并且位数应该为2的倍数");
		}
		StringBuilder returnValue = new StringBuilder();
		int len = hexStr.length() / 4;
		for (int i = len - 1; i > -1; i--) {
			String swopStr = hexStr.substring(i * 4, i * 4 + 4);
			returnValue.append(swopStr.substring(2, 4));
			returnValue.append(swopStr.substring(0, 2));
		}
		return returnValue.toString();
	}

	public static String hexDesc(String hexStr) {
		if (!hexStr.matches("[0-9a-fA-F]+") || hexStr.length() % 2 != 0) {// 16进制
																			// 2size
																			// 1位
			throw new IllegalArgumentException("参数应是一个十六进制字符串,并且位数应该为2的倍数");
		}

		StringBuilder returnValue = new StringBuilder();
		int len = hexStr.length() / 2;
		for (int i = len - 1; i > -1; i--) {
			String descStr = hexStr.substring(i * 2, i * 2 + 2);
			returnValue.append(descStr.substring(0, 2));
		}
		return returnValue.toString();
	}

	/**
	 * bytes转换成十六进制字符串
	 * 
	 * @param byte[] b byte数组
	 * @return String 每个Byte值之间空格分隔
	 */
	public static String byte2HexStr(byte[] b) {
		String stmp = "";
		StringBuilder sb = new StringBuilder("");
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
			// sb.append(" ");
		}
		return sb.toString().toUpperCase().trim();
	}

	public static String bytes2hex(byte[] bytes)
	{
		final String HEX = "0123456789ABCDEF";
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes)
		{
			// 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
			sb.append(HEX.charAt((b >> 4) & 0x0f));
			// 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
			sb.append(HEX.charAt(b & 0x0f));
		}
 
		return sb.toString();
	}	
	/**
	 * byte[]转换成16进制字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * bytes字符串转换为Byte值
	 * 
	 * @param String
	 *            src Byte字符串，每个Byte之间没有分隔符
	 * @return byte[]
	 */
	public static byte[] hexStr2Bytes(String src) {
		int m = 0, n = 0;
		int l = src.length() / 2;
		String res = "";
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			m = i * 2 + 1;
			n = m + 1;
			ret[i] = Byte.decode("0x" + src.substring(i * 2, m)
					+ src.substring(m, n));
			res += ret[i];
		}
		System.out.println(res);
		return ret;
	}
	
	/**
	 * 获取七位长度的采集器地址
	 * @param collectAddress 原地址
	 * @return
	 */
	public static String getHexAddress(String collectAddress){
		if(collectAddress.length() < 2){
			collectAddress = "0"+collectAddress;
		}
		ByteBuffer byteBuffer = ByteBuffer.allocate(7);
		byte[] bytes = HexUtil.hexStr2Bytes(collectAddress);
		byteBuffer.put(bytes);
		String hex = HexUtil.bytesToHexString(byteBuffer.array());
		return hex;
	}
	
	/**
	 * 16进制数转10进制日期
	 * @param time 16进制数
	 * @return
	 */
	public static String hexToDateTime(String time){
		String dateTimes = HexUtil.hexDesc(time);//高低位转换
		String year = dateTimes.substring(0, 4);//年
		String month = dateTimes.substring(4,6);//月
		String day = dateTimes.substring(6,8);//日
		String hour = dateTimes.substring(8,10);//时
		String min = dateTimes.substring(10,12);//分
		String sec = dateTimes.substring(12,14);//秒
		String date = hex10Time(year)+"-"
		+hex10Time(month)+"-"
		+hex10Time(day)+" "
		+hex10Time(hour)+":"
		+hex10Time(min)+":"
		+hex10Time(sec);
		return date;
	}
	
	public static String hexToDate(String time){
		String dateTimes = HexUtil.hexDesc(time);//高低位转换
		String year = dateTimes.substring(0, 2);//年
		String month = dateTimes.substring(2,4);//月
		String day = dateTimes.substring(4,6);//日
		String hour = dateTimes.substring(6,8);//时
		String sec = dateTimes.substring(8,10);//分
		String date = "20"+year+"-"
		+month+"-"
		+day+" "
		+hour+":"
		+sec;
		return date;
	}
	
	/**
	 * 6进制数转10进制日期 （时分）
	 * @param time 16进制数
	 * @return
	 */
	public static String hexToHourSecond(String time){
		String hour = time.substring(0, 2);//时
		String second = time.substring(2,4);//分
		String date = hour+second;
		return date;
	}
	
	/**
	 * 16进制转10进制数时间 ,补齐位数
	 * @param hex
	 * @return
	 */
	public static String hex10Time(String hex){
		String hex10 = Integer.valueOf(hex,16).toString();
		
		if((hex10.length() % 2) != 0 ){
			hex10 = "0"+hex10;
		}
		return hex10;
	}
	
	 public static byte[] toByteArray(String hexString) {
		  hexString = hexString.toLowerCase();
		  final byte[] byteArray = new byte[hexString.length() / 2];
		  int k = 0;
		  for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
		   byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
		   byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
		   byteArray[i] = (byte) (high << 4 | low);
		   k += 2;
		  }
		  return byteArray;
		 } 

}
