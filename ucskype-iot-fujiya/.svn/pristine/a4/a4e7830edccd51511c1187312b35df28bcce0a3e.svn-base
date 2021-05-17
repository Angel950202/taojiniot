package com.taojin.iot.transmit.lib.utils;

public class ConvertUtil {

	/**
	 * 字符串转化成为16进制字符串
	 * @param s
	 * @return
	 */
	public static String strToHex(String s) {
	    String str = "";
	    for (int i = 0; i < s.length(); i++) {
	        int ch = (int) s.charAt(i);
	        String hexString = Integer.toHexString(ch);
	        str = str + hexString;
	    }
	    return str;
	}
	
	/**
	 * 16进制转换成为string类型字符串
	 * @param s
	 * @return
	 */
	public static String hexToStr(String s) {
	    if (s == null || s.equals("")) {
	        return null;
	    }
	    s = s.replace(" ", "");
	    byte[] baKeyword = new byte[s.length() / 2];
	    for (int i = 0; i < baKeyword.length; i++) {
	        try {
	            baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    try {
	        s = new String(baKeyword, "UTF-8");
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }
	    return s;
	}
}
