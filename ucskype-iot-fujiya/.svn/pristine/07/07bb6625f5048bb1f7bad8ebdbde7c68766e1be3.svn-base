package com.taojin.iot.service.equipment.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.taojin.iot.base.comm.dao.impl.BaseDaoImpl;
import com.taojin.iot.service.equipment.dao.EquipmentDao;
import com.taojin.iot.service.equipment.entity.Equipment;

/**
 * Dao实现-设备
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午8:36:09
 * author 王杰
 * ============================================================================
 */
@Repository("equipmentDaoImpl")
public class EquipmentDaoImpl extends BaseDaoImpl<Equipment,Long> implements EquipmentDao{

	@SuppressWarnings("unchecked")
	@Override
	public Equipment querySensorLineByAddrssANDLine(String address,
			String lineNumber) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT e.* FROM iot_equipment e,iot_equipment_sensor s WHERE e.id = s.equipment_id AND e.is_del != 1 AND s.is_del !=1");
		sql.append(" AND e.line_number =:lineNumber AND s.serial_number =:address");
		Query query = entityManager.createNativeQuery(sql.toString(),Equipment.class);
		query.setParameter("lineNumber", lineNumber);
		query.setParameter("address", address);
		List<Equipment> list = query.getResultList();
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

}
