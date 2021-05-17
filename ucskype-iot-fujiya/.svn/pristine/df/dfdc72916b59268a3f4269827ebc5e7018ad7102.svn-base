package com.taojin.iot.service.user.dao.impl;


import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.taojin.iot.base.comm.dao.impl.BaseDaoImpl;
import com.taojin.iot.service.user.dao.SeniorManagementDao;
import com.taojin.iot.service.user.entity.SeniorManagement;


@Repository("seniorManagementDaoImpl")
public class SeniorManagementDaoImpl extends BaseDaoImpl<SeniorManagement,Long> implements SeniorManagementDao{
	
	
	public void updatecompany(String newname,String name) {
		StringBuffer str= new StringBuffer("update iot_senior_management set company = '");
		str.append(newname).append("' where company = '").append(name).append("'");
		Query query = entityManager.createNativeQuery(str.toString());
		query.executeUpdate();
	}

	@Override
	public void updateEquipment(String number, int netflow,String status) {
		StringBuffer str= new StringBuffer("update iot_senior_management set status = '");
		str.append(status).append("' , netflow = ").append(netflow).append(" where number = '").append(number).append("'");
		Query query = entityManager.createNativeQuery(str.toString());
		query.executeUpdate();
		
	}
}
