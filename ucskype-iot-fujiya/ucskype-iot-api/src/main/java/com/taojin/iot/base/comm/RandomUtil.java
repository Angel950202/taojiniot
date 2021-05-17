package com.taojin.iot.base.comm;

import java.util.Random;

/**
 * 类型:随机参数
 * ============================================================================
 * 版权所有 2013-2014 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.UcSkype.com/
 * ----------------------------------------------------------------------------
 * date 2014-6-12 下午03:42:03 
 * author 邵骏晶
 * ============================================================================
 */

public class RandomUtil {
	private static final char[] base = {'0','1','2','3','4','5','6','7','8','9',
										'a','b','c','d','e','f','g','h','i','j',
										'k','l','m','n','o','p','q','r','s','t',
										'u','v','w','x','y','z',
										'A','B','C','D','E','F','G','H','I','J',
										'K','L','M','N','O','P','Q','R','S','T',
										'U','V','W','X','Y','Z'};
	/**
	 * 获取随机值
	 * @param length	长度
	 * @param type		类型	【0|数字;1|英文小写;2|英文大写;3|小写英文+数字;4|全部】
	 * @return
	 */
	public static String getCode(Integer length,Integer type){
		if( length <= 0){
			return null;
		}
		String code="";
		Random random = new Random();
		if(type == 0 ){
			for(int i = 0 ; i < length ; i++){
				code +=base[random.nextInt(10)];
			}
		}else if(type == 1 ){
			for(int i = 0 ; i < length ; i++){
				code +=base[random.nextInt(26)+10];
			}
		}else if(type == 2 ){
			for(int i = 0 ; i < length ; i++){
				code +=base[random.nextInt(26)+36];
			}
		}else if(type == 3){
			for(int i = 0 ; i < length ; i++){
				code +=base[random.nextInt(36)];
			}
		}else if(type == 4){
			for(int i = 0 ; i < length ; i++){
				code +=base[random.nextInt(62)];
			}
		}else{
			return null;
		}
		return code;
	}
	/**
	 * 获取范围内参数
	 * @param	range：随机数范围
	 * @return
	 * @author 徐鼎文
	 * @date Sep 11, 2014 11:45:32 AM
	 */
	public static Integer randomInt(Integer range){
		Random ra = new Random();
		return ra.nextInt(range)+1;
	}
	
//	public static void main(String[] args) {
//		RandomUtil util = new RandomUtil();
//		System.out.println(util.getCode(4, 0));
//	}
}
