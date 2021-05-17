package com.taojin.iot.service.equipment.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.Page;
import com.taojin.iot.base.comm.Pageable;
import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.equipment.dao.EquipmentMaintainTypeDao;
import com.taojin.iot.service.equipment.entity.EquipmentMaintainType;
import com.taojin.iot.service.equipment.service.EquipmentMaintainTypeService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Service实现-设备类型
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午8:53:38
 * author 王杰
 * ============================================================================
 */
@Service("equipmentMaintainTypeServiceImpl")
public class EquipmentMaintainTypeServiceImpl extends BaseServiceImpl<EquipmentMaintainType,Long> implements EquipmentMaintainTypeService{
	
	@Resource(name = "equipmentMaintainTypeDaoImpl")
	private EquipmentMaintainTypeDao equipmentTypeMaintainDao;

	@Resource(name = "equipmentMaintainTypeDaoImpl")
	public void setBaseDao(EquipmentMaintainTypeDao equipmentMaintainTypeDao) {
		super.setBaseDao(equipmentMaintainTypeDao);
	}

	@Override
	public JSONObject findEquipmentMaintainTypeList(JSONObject param) {

		// 分页检索
		Pageable pageable = new Pageable(param.getInt("pageNumber"),
				param.getInt("pageSize"));
		List<Filter> filters = pageable.getFilters();
		filters.add(Filter.eq("isDel", false));
		Page<EquipmentMaintainType> pageContent = findPage(pageable);
		
		
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "拉取成功");
		JSONArray jsonArray = new JSONArray();
		JSONObject json = null;
		for (EquipmentMaintainType equipmentMaintainType : pageContent.getContent()) {
			json = new JSONObject();
			json.put("id", equipmentMaintainType.getId());
			json.put("name", equipmentMaintainType.getName());
			json.put("icopath", equipmentMaintainType.getIcopath());
			jsonArray.add(json);
		}
		jsonReturn.put("values", jsonArray);
		
		return jsonReturn;
	}
}
