package com.taojin.iot.service.equipment.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enum-设备触发条件
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午11:08:26
 * author 王杰
 * ============================================================================
 */
public enum EquipmentAlarmTypeEnum {
	
	val_above("数值高于X"),
	val_below("数值低于Y"),
	val_above_below("数值高于X低于Y"),
	val_above_below_ofm("数值高于X低于Y超过M分钟"),
	x_tir_y_rec("数值高于X报警低于Y恢复"),
	val_between("数值在X和Y之间"),
	val_above_bound("数值超过M分钟高于X"),
	val_below_bound("数值超过M分钟低于Y"),
	offline("传感器未连接"),
	offline_for_minutes("超过M分钟未连接"),
	switch_on("开关ON"),
	switch_off("开关OFF");
	
	/** 描述 */
    private String desc;

    private EquipmentAlarmTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Map<String, Map<String, Object>> toMap() {
    	EquipmentAlarmTypeEnum[] ary = EquipmentAlarmTypeEnum.values();
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
        EquipmentAlarmTypeEnum[] ary = EquipmentAlarmTypeEnum.values();
        List list = new ArrayList();
        for (int i = 0; i < ary.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("desc", ary[i].getDesc());
            list.add(map);
        }
        return list;
    }

    public static EquipmentAlarmTypeEnum getEnum(String name) {
        EquipmentAlarmTypeEnum[] arry = EquipmentAlarmTypeEnum.values();
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
        EquipmentAlarmTypeEnum[] enums = EquipmentAlarmTypeEnum.values();
        StringBuffer jsonStr = new StringBuffer("[");
        for (EquipmentAlarmTypeEnum senum : enums) {
            if (!"[".equals(jsonStr.toString())) {
                jsonStr.append(",");
            }
            jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
        }
        jsonStr.append("]");
        return jsonStr.toString();
    }

}
