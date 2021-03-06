package com.taojin.iot.service.equipment.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>功能说明:设备协议.</b>
 * @author  wangjie
 */
public enum EquipmentProtocolEnum {
   MQTT("MQTT"),
   TCP("TCP"),
   MODBUS("MB RTU"),
   MDTCP("MB TCP");

    /** 描述 */
    private String desc;

    private EquipmentProtocolEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Map<String, Map<String, Object>> toMap() {
        EquipmentProtocolEnum[] ary = EquipmentProtocolEnum.values();
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
        EquipmentProtocolEnum[] ary = EquipmentProtocolEnum.values();
        List list = new ArrayList();
        for (int i = 0; i < ary.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("desc", ary[i].getDesc());
            list.add(map);
        }
        return list;
    }

    public static EquipmentProtocolEnum getEnum(String name) {
        EquipmentProtocolEnum[] arry = EquipmentProtocolEnum.values();
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
        EquipmentProtocolEnum[] enums = EquipmentProtocolEnum.values();
        StringBuffer jsonStr = new StringBuffer("[");
        for (EquipmentProtocolEnum senum : enums) {
            if (!"[".equals(jsonStr.toString())) {
                jsonStr.append(",");
            }
            jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
        }
        jsonStr.append("]");
        return jsonStr.toString();
    }
}
