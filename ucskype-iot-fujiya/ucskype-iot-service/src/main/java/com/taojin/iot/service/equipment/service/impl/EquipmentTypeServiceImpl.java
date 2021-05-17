package com.taojin.iot.service.equipment.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.Page;
import com.taojin.iot.base.comm.Pageable;
import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.equipment.dao.EquipmentTypeDao;
import com.taojin.iot.service.equipment.entity.EquipmentType;
import com.taojin.iot.service.equipment.service.EquipmentTypeService;

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
@Service("equipmentTypeServiceImpl")
public class EquipmentTypeServiceImpl extends BaseServiceImpl<EquipmentType,Long> implements EquipmentTypeService{
	@Resource(name = "equipmentTypeDaoImpl")
	private EquipmentTypeDao equipmentTypeDao;

	@Resource(name = "equipmentTypeDaoImpl")
	public void setBaseDao(EquipmentTypeDao equipmentTypeDao) {
		super.setBaseDao(equipmentTypeDao);
	}

	@Override
	public JSONObject findEquipmentList(JSONObject param) {

		Pageable pageable = new Pageable(param.getInt("pageNumber"),
				param.getInt("pageSize"));
		List<Filter> filters = pageable.getFilters();
		filters.add(Filter.eq("isDel", false));

		Page<EquipmentType> pageContent = findPage(pageable);

		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("errcode", "0");
		jsonReturn.put("errmsg", "拉取成功");
		JSONArray jsonArray = new JSONArray();
		JSONObject json = null;
		for (EquipmentType equipmentType : pageContent.getContent()) {
			json = new JSONObject();
			json.put("id", equipmentType.getId());
			json.put("name", equipmentType.getName());
			jsonArray.add(json);
		}
		jsonReturn.put("values", jsonArray);
		return jsonReturn;
	}
}
