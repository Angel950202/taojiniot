package com.taojin.iot.service.kanban;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产线编码枚举类
 * 获取采集数据
 * @author Administrator
 *
 */
public enum LineEnum {
	
	RC701("RC70-1"),						//RC70-1产线		编号：0								
	RC771("RC77-1"),						//RD77-1产线		编号：1
	EPUMPGEAR("EPUMP-2-Gearless"),			//RD77-2产线		编号：2
	EPUMPMAIN("EPUMP-2-Main");				//RC70-2产线		编号：3
	
	
	/** 描述 */
    private String desc;

    private LineEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Map<String, Map<String, Object>> toMap() {
    	LineEnum[] ary = LineEnum.values();
        Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < ary.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = ary[num].name();
            map.put("desc", ary[num].getDesc());
            enumMap.put(key, map);
        }
        return enumMap;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List toList() {
    	LineEnum[] ary = LineEnum.values();
        List list = new ArrayList();
        for (int i = 0; i < ary.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("desc", ary[i].getDesc());
            list.add(map);
        }
        return list;
    }

    public static LineEnum getEnum(String name) {
    	LineEnum[] arry = LineEnum.values();
        for (int i = 0; i < arry.length; i++) {
            if (arry[i].name().equalsIgnoreCase(name)) {
                return arry[i];
            }
        }
        return null;
    }

    /**
     * 取枚举的json字符串
     *
     * @return
     */
    public static String getJsonStr() {
    	LineEnum[] enums = LineEnum.values();
        StringBuffer jsonStr = new StringBuffer("[");
        for (LineEnum senum : enums) {
            if (!"[".equals(jsonStr.toString())) {
                jsonStr.append(",");
            }
            jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
        }
        jsonStr.append("]");
        return jsonStr.toString();
    }

}
