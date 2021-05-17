package com.taojin.iot.service.equipment.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.dao.impl.BaseDaoImpl;
import com.taojin.iot.service.equipment.dao.EquipmentIcoDao;
import com.taojin.iot.service.equipment.entity.EquipmentIco;

/**
 * Dao-设备图标
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午8:36:54
 * author 王杰
 * ============================================================================
 */
@Repository("equipmentIcoDaoImpl")
public class EquipmentIcoDaoImpl extends BaseDaoImpl<EquipmentIco,Long> implements EquipmentIcoDao{

	@Override
	public List<EquipmentIco> findListByParam(List<Filter> filters,
			String userId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<EquipmentIco> criteriaQuery = criteriaBuilder.createQuery(EquipmentIco.class);
		Root<EquipmentIco> root = criteriaQuery.from(EquipmentIco.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Long> get("ownerId"), Long.parseLong(userId)));
		restrictions = criteriaBuilder.or(restrictions, criteriaBuilder.isNull(root.<Long> get("ownerId")));
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("createDate")));
		return super.findList(criteriaQuery, null, null, filters, null);
	}

}
