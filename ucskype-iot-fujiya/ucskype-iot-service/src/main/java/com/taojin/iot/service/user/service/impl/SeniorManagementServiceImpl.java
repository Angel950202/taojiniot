package com.taojin.iot.service.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.user.dao.SeniorManagementDao;
import com.taojin.iot.service.user.entity.SeniorManagement;
import com.taojin.iot.service.user.service.SeniorManagementService;

/**
 * Service-设备管理
 * @author Administrator
 *
 */
@Service("seniorManagementServiceImpl")
public class SeniorManagementServiceImpl extends BaseServiceImpl<SeniorManagement,Long> implements SeniorManagementService{
	@Resource(name = "seniorManagementDaoImpl")
	private SeniorManagementDao seniorManagementDao;

	@Resource(name = "seniorManagementDaoImpl")
	public void setBaseDao(SeniorManagementDao seniorManagementDao) {
		super.setBaseDao(seniorManagementDao);
	}

	@Override
	public void updatecompany(String newname, String name) {
		seniorManagementDao.updatecompany(newname,name);
	}

	@Override
	public void updateEquipment(String number, int netflow,String status) {
		seniorManagementDao.updateEquipment(number,netflow,status);
	}
	
	@Override
	public void updateEquipmentStatus(String number, String status){
		SeniorManagement seniorManagement = super.getByParam("number", number);
		if(seniorManagement != null){
			seniorManagement.setStatus(status);
			super.update(seniorManagement);
		}
	}
	
	@Override
	public void payFlow(String number,int payFlow){
		SeniorManagement seniorManagement = super.getByParam("number", number);
		if(seniorManagement != null){
			int flow = seniorManagement.getPayNetFlow();//获取充值流量
			seniorManagement.setPayNetFlow(payFlow+flow);//累计
			super.update(seniorManagement);
		}
	}
	
	
	@Override
	public int getPayFlow(String number){
		SeniorManagement seniorManagement = super.getByParam("number", number);
		if(seniorManagement != null){
			int payFlow = seniorManagement.getPayNetFlow();//获取充值流量
			seniorManagement.setPayNetFlow(0);//清0
			super.update(seniorManagement);
			return payFlow;
		}
		return 0;
	}
	
	@Override
	public void payBackFlow(String number,int backFlow){
		SeniorManagement seniorManagement = super.getByParam("number", number);
		if(seniorManagement != null){
			int payFlow = seniorManagement.getPayNetFlow();//获取充值流量
			seniorManagement.setPayNetFlow(payFlow+backFlow);//归还
			super.update(seniorManagement);
		}
	}
}
