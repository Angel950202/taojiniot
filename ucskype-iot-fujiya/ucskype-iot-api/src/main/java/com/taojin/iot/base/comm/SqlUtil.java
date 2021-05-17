package com.taojin.iot.base.comm;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.taojin.iot.base.comm.Filter.Operator;
import com.taojin.iot.base.comm.Order.Direction;
import com.taojin.iot.base.comm.utils.DatesUtils;


/**
 * 类型:Comm - sql公共方法
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 上午10:20:03 author 陶友
 * ============================================================================
 */
public class SqlUtil {
	/**时间类型*/
	public enum DateType{
		day,
		week,
		month,
		season,
		year,
		other
	}
	
	/**
	 * 将对象转化为List集合
	 * 
	 * @param array
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List makeArrayObjects(Object array) {
		List tem = new ArrayList();
		for (int i = 0; i < Array.getLength(array); i++) {
			tem.add(Array.get(array, i));
		}
		return tem;
	}

	/**
	 * 获取检索条件
	 * 
	 * @param pageable
	 * @return
	 */
	public static Map<String, Object> getOrderMap(Pageable pageable) {
		if (pageable == null) {
			pageable = new Pageable();
		}

		Map<String, Object> orderMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(pageable.getOrderField())
				&& pageable.getOrderDirection() != null) {
			if (pageable.getOrderDirection() == Direction.asc) {
				orderMap.put("property", pageable.getOrderField());
				orderMap.put("direction", "asc");
			} else if (pageable.getOrderDirection() == Direction.desc) {
				orderMap.put("property", pageable.getOrderField());
				orderMap.put("direction", "desc");
			} else {
				orderMap.put("property", pageable.getOrderField());
				orderMap.put("direction", "desc");
			}
		} else {
			pageable.setOrderField("create_date");
			pageable.setOrderDirection(Direction.desc);
			orderMap.put("property", pageable.getOrderField());
			orderMap.put("direction", "desc");
		}

		String whereStr = " where 1=1 ";

		// 匹配查询
		for (Filter filter : pageable.getFilters()) {
			if (filter == null || StringUtils.isEmpty(filter.getProperty())) {
				continue;
			}
			if (filter.getOperator() == Operator.eq
					&& filter.getValue() != null) {
				if (filter.getIgnoreCase() != null && filter.getIgnoreCase()
						&& filter.getValue() instanceof String) {
					whereStr += " and " + filter.getProperty() + "='"
							+ filter.getValue() + "'";
				} else {
					whereStr += " and " + filter.getProperty() + "='"
							+ filter.getValue() + "'";
				}
			} else if (filter.getOperator() == Operator.ne
					&& filter.getValue() != null) {
				if (filter.getIgnoreCase() != null && filter.getIgnoreCase()
						&& filter.getValue() instanceof String) {
					whereStr += " and " + filter.getProperty() + " <> '"
							+ filter.getValue() + "'";
				} else {
					whereStr += " and " + filter.getProperty() + " <> '"
							+ filter.getValue() + "'";
				}
			} else if (filter.getOperator() == Operator.gt
					&& filter.getValue() != null) {
				whereStr += " and " + filter.getProperty() + " > '"
						+ filter.getValue() + "'";
			} else if (filter.getOperator() == Operator.lt
					&& filter.getValue() != null) {
				whereStr += " and " + filter.getProperty() + " < '"
						+ filter.getValue() + "'";
			} else if (filter.getOperator() == Operator.ge
					&& filter.getValue() != null) {
				whereStr += " and " + filter.getProperty() + " >= '"
						+ filter.getValue() + "'";
			} else if (filter.getOperator() == Operator.le
					&& filter.getValue() != null) {
				whereStr += " and " + filter.getProperty() + " <= '"
						+ filter.getValue() + "'";
			} else if (filter.getOperator() == Operator.like
					&& filter.getValue() != null
					&& filter.getValue() instanceof String) {
				whereStr += " and " + filter.getProperty() + " like '%"
						+ filter.getValue() + "%'";
			} else if (filter.getOperator() == Operator.in
					&& filter.getValue() != null) {

				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) filter.getValue();
				StringBuffer sb = new StringBuffer();
				int len = list.size();
				for (int i = 0; i < len; i++) {
					sb.append(list.get(i));
					if (i < len - 1) {
						sb.append(",");
					}
				}
				whereStr += " and " + filter.getProperty() + " in ("
						+ sb.toString() +

						")";
			} else if (filter.getOperator() == Operator.isNull) {
				whereStr += " and " + filter.getProperty() + " is null";
			} else if (filter.getOperator() == Operator.isNotNull) {
				whereStr += " and " + filter.getProperty() + " is not null";
			} else if (filter.getOperator() == Operator.between
					&& filter.getValue() != null && filter.getValue1() != null) {
				String start = DatesUtils.dateToString(
						(Date) filter.getValue(), "yyyy-MM-dd");
				String end = DatesUtils.dateToString((Date) filter.getValue1(),
						"yyyy-MM-dd");
				whereStr += " and " + filter.getProperty() + " between '"
						+ start + " 00:00:00' and '" + end + " 23:59:59'";
			}
		}

		orderMap.put("pageNumber", pageable.getPageNumber());
		orderMap.put("pageSize", pageable.getPageSize());
		orderMap.put("whereStr", whereStr);
		return orderMap;
	}
}
