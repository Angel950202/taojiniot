package com.taojin.iot.base.comm;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.lucene.document.Document;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.builtin.NumericFieldBridge;

/**
 * BigDecimal类型转换
 * 
 * 
 * 
 */
public class BigDecimalNumericFieldBridge extends NumericFieldBridge {

	/**
	 * 获取BigDecimal对象
	 * 
	 * @param name
	 *            名称
	 * @param document
	 *            document
	 * @return BigDecimal对象
	 */
	@Override
	public Object get(String name, Document document) {
		return new BigDecimal(document.getFieldable(name).stringValue());
	}

	/**
	 * 设置BigDecimal对象
	 * 
	 * @param name
	 *            名称
	 * @param value
	 *            值
	 * @param document
	 *            document
	 * @param luceneOptions
	 *            luceneOptions
	 */
	@Override
	public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {
		if (value != null) {
			BigDecimal decimalValue = (BigDecimal) value;
			luceneOptions.addNumericFieldToDocument(name, decimalValue.doubleValue(), document);
		}
	}

	/**
	 * 保留小数
	 * @param bigDecimalValue  BigDecimal 值
	 * @param decimalDigits    保留小数的位数
	 * @return
	 */
	public static BigDecimal bigDecimalFormat(BigDecimal bigDecimalValue, int decimalDigits){
		StringBuilder formatString= new StringBuilder();
		formatString.append("#.");
		if(decimalDigits > 0){
			for (int i = 0; i < decimalDigits; i++) {
				formatString.append("0");
			}
		}else{
			return new BigDecimal(bigDecimalValue.intValue());
		}
		DecimalFormat decimalFormat = new DecimalFormat(formatString.toString());
		return new BigDecimal(decimalFormat.format(bigDecimalValue));
	}
}