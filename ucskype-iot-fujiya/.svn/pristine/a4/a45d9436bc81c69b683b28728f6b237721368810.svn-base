package com.taojin.iot.transmit.utils;

/**
 * 进制转换工具类
 * 类型:ConvertUtil
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
 *         ============================================================================
 */
public class ConvertUtil {

	private static String	hexStr		= "0123456789ABCDEF";
	private static String[]	binaryArray	=
										{ "0000", "0001", "0010", "0011",
										"0100", "0101", "0110", "0111",
										"1000", "1001", "1010", "1011",
										"1100", "1101", "1110", "1111" };

	/**
	 * 字符串转化成为16进制字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String str2Hex(String s) {

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
	 * 
	 * @param s
	 * @return
	 */
	public static String hex2Str(String s) {

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

	/**
	 * 16进制转int
	 * 
	 * @param hexString
	 * @return
	 */
	/*
	 * public static int hex2Int(String hexString){
	 * return (int)ConvertUtil.hex2Char(hexString)[0];
	 * }
	 */

	/**
	 * 16进制转int
	 * 
	 * @param hexString
	 * @return
	 */
	public static Integer hex2Int(String hexString) {
		Long dec_num = Long.parseLong(hexString, 16);
		Integer num = Integer.parseInt(dec_num.toString());
//		return Integer.parseInt(hexString, 16);
		return num;
	}
	
	/**
	 * 16进制转int
	 * @param hexString
	 * @return
	 */
	public static Long hex2Ints(String hexString){
		 long dec_num = Long.parseLong(HexUtil.hexDesc(hexString), 16);  
	     return dec_num;
	}

	/**
	 * int转16进制
	 * 
	 * @param hexString
	 * @return
	 */
	public static String int2hex(int num) {

		String result = Integer.toHexString(num);
		if (result.length() < 2) {
			result = "0" + result;
		}
		return result;
	}
	
	/**
	 * int转16进制 2个字节
	 * @param num 
	 * @return
	 */
	public static String int2hex1(int num) {
		String result = Integer.toHexString(num);
		if (result.length() < 2) {
			result = "0" + result;
		}
		
		result = addZeroForNum(result,4);
//		result = HexUtil.decToHex(result);
		return result;
	}
	
	/**
	 * 不足补0
	 * @param str 字符
	 * @param strLength 长度
	 * @return
	 */
	public static String addZeroForNum(String str, int strLength) {
	    int strLen = str.length();
	    if (strLen < strLength) {
	        while (strLen < strLength) {
	            StringBuffer sb = new StringBuffer();
//	            sb.append("0").append(str);// 左补0
	             sb.append(str).append("0");//右补0
	            str = sb.toString();
	            strLen = str.length();
	        }
	    }
	 
	    return str;
	}

	/**
	 * int转16进制 4的倍数位数拼接 位数不足 高位补 0
	 * 
	 * @param hexString
	 * @return
	 */
	public static String int2hexFill0(Integer num) {

		StringBuilder val = new StringBuilder();
		val.append(Integer.toHexString(num));
		int valLen = 4 - val.length() % 4;
		if (valLen > 0 && valLen != 4) {
			for (int i = 0; i < valLen; i++) {
				val.insert(0, "0");
			}
		}
		return val.toString().toUpperCase();
	}

	/**
	 * 16进制转char
	 * 
	 * @param hexString
	 * @return
	 */
	public static char[] hex2Char(String hexString) {

		if (!hexString.matches("[0-9a-fA-F]+")) {
			throw new IllegalArgumentException("参数应是一个十六进制字符串");
		}
		int codePoint = Integer.parseInt(hexString, 16);
		if ((codePoint < 0) || (codePoint > Character.MAX_CODE_POINT)) {
			throw new IllegalArgumentException(hexString + " 所能表示的字符范围溢出");
		}
		return Character.toChars(codePoint);
	}

	/**
	 * 将十六进制转换为二进制字节数组
	 * 
	 * @param hexString
	 * @return byte[]
	 */
	public static byte[] hexStr2BinArr(String hexString) {

		// hexString的长度对2取整，作为bytes的长度
		int len = hexString.length() / 2;
		byte[] bytes = new byte[len];
		byte high = 0;// 字节高四位
		byte low = 0;// 字节低四位
		for (int i = 0; i < len; i++) {
			// 右移四位得到高位
			high = (byte) ((hexStr.indexOf(hexString.charAt(2 * i))) << 4);
			low = (byte) hexStr.indexOf(hexString.charAt(2 * i + 1));
			bytes[i] = (byte) (high | low);// 高地位做或运算
		}
		return bytes;
	}

	public static void main(String[] args) {

		System.out.println(hexStr2BinArr("01"));
	}

	// / <summary>
	// / 字符串转16进制字节数组
	// / </summary>
	// / <param name="hexString"></param>
	// / <returns></returns>
	public static byte[] strToToHexByte(String hexString)
	{

		hexString = hexString.replace(" ", "");
		if ((hexString.length() % 2) != 0)
			hexString += "0";
		byte[] returnBytes = new byte[hexString.length() / 2];
		for (int i = 0; i < returnBytes.length; i++) {
			String hexStringSub = hexString.substring(i * 2, i * 2 + 2);
			int a = ConvertUtil.hex2Int(hexStringSub);
			returnBytes[i] = (byte) a;
		}
		return returnBytes;
	}

	/**
	 * 二进制数组转换为二进制字符串
	 * 
	 * @param bArray
	 * @return String
	 */
	public static String bytes2BinStr(byte[] bArray){  
        String outStr = "";  
        int pos = 0;  
        for(byte b:bArray){  
            //高四位  
            pos = (b&0xF0)>>4;  
            outStr+=binaryArray[pos];  
            //低四位  
            pos=b&0x0F;  
            outStr+=binaryArray[pos];  
        }  
        return outStr;  
    }
	/**
	 * 将十六进制转换为二进制字符串
	 * 
	 * @param hexString
	 * @return String
	 */
	public static String hexStr2BinStr(String hexString) {

		return bytes2BinStr(hexStr2BinArr(hexString));
	}

	/**
	 * 数组转换成十六进制字符串
	 * 
	 * @param byte[]
	 * @return HexString
	 */
	public static final String bytesToHexString(byte[] bArray) {

		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

}
