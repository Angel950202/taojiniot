package com.taojin.iot.agreement.fujiya.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enum-指令类型
 * @author wangjie
 *
 */
public enum AgreementFujiyaEnum {
	
	RC701("RC70-1"),//RC70-1    0
	RC771("RC77-1"),//RD77-1    1
	EPUMPMAIN("EPUMP-2-Main"),//RD77-2 2
	EPUMPGEAR("EPUMP-2-Gearless"),// RC70-2 3
	RCTEST("RC-Test");
	
	/** 描述 */
    private String desc;

    private AgreementFujiyaEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Map<String, Map<String, Object>> toMap() {
    	AgreementFujiyaEnum[] ary = AgreementFujiyaEnum.values();
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
    	AgreementFujiyaEnum[] ary = AgreementFujiyaEnum.values();
        List list = new ArrayList();
        for (int i = 0; i < ary.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("desc", ary[i].getDesc());
            list.add(map);
        }
        return list;
    }

    public static AgreementFujiyaEnum getEnum(String name) {
    	AgreementFujiyaEnum[] arry = AgreementFujiyaEnum.values();
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
    	AgreementFujiyaEnum[] enums = AgreementFujiyaEnum.values();
        StringBuffer jsonStr = new StringBuffer("[");
        for (AgreementFujiyaEnum senum : enums) {
            if (!"[".equals(jsonStr.toString())) {
                jsonStr.append(",");
            }
            jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
        }
        jsonStr.append("]");
        return jsonStr.toString();
    }

}
