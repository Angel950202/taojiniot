package com.taojin.iot.agreement.fujiya.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.taojin.iot.agreement.fujiya.dao.AgreementRc701ValueDao;
import com.taojin.iot.agreement.fujiya.entity.AgreementRc701Value;
import com.taojin.iot.base.comm.dao.impl.BaseDaoImpl;
import com.taojin.iot.base.comm.utils.DatesUtils;

import antlr.StringUtils;

@Repository("agreementRc701ValueDaoImpl")
public class AgreementRc701ValueDaoImpl extends BaseDaoImpl<AgreementRc701Value,Long> implements AgreementRc701ValueDao{

	@Override
	public Integer getSum(String nokNum_arr[],Integer line,String dateTime) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
		Root<AgreementRc701Value> root = criteriaQuery.from(AgreementRc701Value.class);
		criteriaQuery.select(criteriaBuilder.sum(root.<Integer> get("commandValue")));
		Predicate restrictions = criteriaBuilder.conjunction();
		Path<String> path = root.get("address");
		CriteriaBuilder.In<String> in = criteriaBuilder.in(path);
		for(int i=0;i<nokNum_arr.length;i++){
			in.value(nokNum_arr[i].trim());
		}
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.and(in));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Integer> get("equipType"), line));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<String> get("dateTime"), dateTime));
		criteriaQuery.where(restrictions);
		String num = String.valueOf(entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getSingleResult());
		if(org.apache.commons.lang3.StringUtils.isNotBlank(num) && !"null".equalsIgnoreCase(num)){
//			System.out.println("============="+num);
			return Integer.parseInt(num);
		}else{
//			System.out.println("============="+num);
		}
		return 0;
	}
	
	
	
}
