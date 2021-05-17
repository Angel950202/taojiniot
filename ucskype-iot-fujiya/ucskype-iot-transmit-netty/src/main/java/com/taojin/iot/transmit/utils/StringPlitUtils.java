package com.taojin.iot.transmit.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串分割处理
 * @author wangjie
 *
 */
public class StringPlitUtils {

	/**
	 * 把原始字符串分割成指定长度的字符串列表
	 * 
	 * @param inputString
	 *            原始字符串
	 * @param length
	 *            指定长度
	 * @return
	 */
	public static List<String> getStrList(String inputString, int length) {
		int size = inputString.length() / length;
		if (inputString.length() % length != 0) {
			size += 1;
		}
		return getStrList(inputString, length, size);
	}

	/**
	 * 把原始字符串分割成指定长度的字符串列表
	 * 
	 * @param inputString
	 *            原始字符串
	 * @param length
	 *            指定长度
	 * @param size
	 *            指定列表大小
	 * @return
	 */
	public static List<String> getStrList(String inputString, int length,
			int size) {
		List<String> list = new ArrayList<String>();
		for (int index = 0; index < size; index++) {
			String childStr = substring(inputString, index * length,
					(index + 1) * length);
			list.add(childStr);
		}
		return list;
	}

	/**
	 * 分割字符串，如果开始位置大于字符串长度，返回空
	 * 
	 * @param str
	 *            原始字符串
	 * @param f
	 *            开始位置
	 * @param t
	 *            结束位置
	 * @return
	 */
	public static String substring(String str, int f, int t) {
		if (f > str.length())
			return null;
		if (t > str.length()) {
			return str.substring(f, str.length());
		} else {
			return str.substring(f, t);
		}
	}
	
	public static void main(String[] args){
		String tt = "2c2c35035ce3010000000000ff02170100000000000000000000051d0b52e30100000000000000000000000000000b03000071061d0b52e3010000000000000000000000000000070300006e071d0b52e3010000000000000000000000000000050300006d081d0b52e3010000000000000000000000000000010300006a091d0b52e301000000000000000000000000000008030000720a1d0b52e30100000000000000000000000000000b030000760b1d0b5ce3010000000000000000000000000000050300007b0c1d0b5ce301000000000000000000000000000000030000770d1d0b5ce301000000000000000000000000000008030000800000";
		List<String> t = getStrList(tt.substring(52,tt.length()-4),50);
		System.out.println(t.toString());
		
	}

}
