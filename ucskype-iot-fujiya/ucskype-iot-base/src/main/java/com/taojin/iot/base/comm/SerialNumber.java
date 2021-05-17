package com.taojin.iot.base.comm;

import java.util.Random;
import java.util.logging.Logger;

import com.taojin.iot.base.comm.utils.DatesUtils;

/**
 * 类型:编号生成 全局编号生成
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午1:23:57 author 王杰
 * ============================================================================
 */
public class SerialNumber {
	/** logger */
	private static final Logger logger = Logger.getLogger(SerialNumber.class.getName());

	/** 长度 */
	private static final int LENGTH = 8;

	/** 编号前缀 */
	public enum Prefix {
		/** 仓库 */
		WH,
		/** 仓库储位 */
		WHS,
		/**产品货号*/
		AN,
		/**订单编号*/
		SY,
		/**报价单编号*/
		BJ,
		/**花纸制作单号*/
		HZ,
		/**花纸编号*/
		HZN,
		/**合同*/
		HT,
		/**客户*/
		KH,
		/**员工*/
		YG,
		/**送货单*/
		FH,
		/**客户回访*/
		QG,
		/**箱号*/
		BS,
		/**供应商*/
		GYS,
		/**模具编号*/
		MDL,
		/**委外公司编号*/
		OS,
		/**物料*/
		MAT
	}
	
	/**
	 * 根据类别获取随机编号
	 * @param prefix 类别
	 * @return
	 */
	public static String getRandNumber(Prefix prefix){
		String number = prefix.name()+generateNumber();
		logger.info(prefix.name()+"生成随机编号:"+number);
		return number;
	}
	
	/**
	 * 根据类别获取日期格式的编号
	 * @param prefix
	 * @return 前缀+yyyyMMddHHmmss
	 */
	public static String getDateNumber(Prefix prefix){
		String dateTime = DatesUtils.getStringToday("yyyyMMddHHmmss");
		String number = prefix.name()+dateTime;
		logger.info(prefix.name()+"生成日期格式编号:"+number);
		return number;
	}
	

	/**
	 * 　　* 这是典型的随机洗牌算法。
	 * 
	 * 　　* 流程是从备选数组中选择一个放入目标数组中，将选取的数组从备选数组移除（放至最后，并缩小选择区域）
	 * 
	 * 　　* 算法时间复杂度O（n）
	 * 
	 * 　　* @return 随机8为不重复数组
	 * 
	 * 　　
	 */

	private static String generateNumber() {

		String no = "";

		// 初始化备选数组

		int[] defaultNums = new int[10];

		for (int i = 0; i < defaultNums.length; i++) {

			defaultNums[i] = i;

		}

		Random random = new Random();

		int[] nums = new int[LENGTH];

		// 默认数组中可以选择的部分长度

		int canBeUsed = 10;

		// 填充目标数组

		for (int i = 0; i < nums.length; i++) {

			// 将随机选取的数字存入目标数组

			int index = random.nextInt(canBeUsed);

			nums[i] = defaultNums[index];

			// 将已用过的数字扔到备选数组最后，并减小可选区域

			swap(index, canBeUsed - 1, defaultNums);

			canBeUsed--;

		}

		if (nums.length > 0) {

			for (int i = 0; i < nums.length; i++) {

				no += nums[i];

			}

		}

		return no;

	}

	/**
	 * 
	 * 交换方法
	 * 
	 * @param i
	 *            交换位置
	 * 
	 * @param j
	 *            互换的位置
	 * 
	 * @param nums
	 *            数组
	 */

	private static void swap(int i, int j, int[] nums) {

		int temp = nums[i];

		nums[i] = nums[j];

		nums[j] = temp;

	}

	/**
	 * 
	 * 获取8位数
	 * 
	 * @return
	 */

	@SuppressWarnings("unused")
	private static String generateNumber2() {

		String no = "";

		int num[] = new int[8];

		int c = 0;

		for (int i = 0; i < 8; i++) {

			num[i] = new Random().nextInt(10);

			c = num[i];

			for (int j = 0; j < i; j++) {

				if (num[j] == c) {

					i--;

					break;

				}

			}

		}

		if (num.length > 0) {

			for (int i = 0; i < num.length; i++) {

				no += num[i];

			}

		}

		return no;
	}
	
	public static void main(String[] args){
		System.out.println(getRandNumber(Prefix.MAT));
		System.out.println(getDateNumber(Prefix.MAT));
	}

}
