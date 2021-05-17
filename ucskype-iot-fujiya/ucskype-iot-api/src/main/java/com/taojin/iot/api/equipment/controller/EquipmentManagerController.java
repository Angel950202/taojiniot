package com.taojin.iot.api.equipment.controller;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taojin.iot.BaseController;

/**
 * 设备管理-api接口
 * @author wangjie
 *
 */
@Controller("internalEquipmentManagerController")
@RequestMapping("/internal/equipment/equipmentManager")
public class EquipmentManagerController extends BaseController
{
	
	/**
	 * 握手
	 * @param equipmentNumber 设备号
	 * @param state 0，1设备状态
	 * @param liuliang 当前流量
	 * @return
	 */
	@RequestMapping(value = "/bind", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String bind(String equipmentNumber,Integer state,String liuliang) {
		System.out.println("equipmentNumber="+equipmentNumber+",state="+state+",liuliang="+liuliang);
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("code", "ok");
		if(StringUtils.isBlank(equipmentNumber)){
			jsonReturn.put("equipmentNumber", "12345678");
		}else{
			jsonReturn.put("equipmentNumber", equipmentNumber);
		}
		jsonReturn.put("state", "1");
		
		String resultStrs = jsonReturn.optString("code")+";"+
						jsonReturn.optString("equipmentNumber")+";"+
						jsonReturn.optString("state")+";0";
		return resultStrs;
	}
	

}
