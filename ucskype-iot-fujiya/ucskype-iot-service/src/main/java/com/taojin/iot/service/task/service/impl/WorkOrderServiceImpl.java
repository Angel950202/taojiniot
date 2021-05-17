package com.taojin.iot.service.task.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.service.task.dao.WorkOrderDao;
import com.taojin.iot.service.task.entity.WorkOrder;
import com.taojin.iot.service.task.service.ProductionLineService;
import com.taojin.iot.service.task.service.WorkOrderService;
import com.taojin.iot.service.user.entity.User;

@Service("workOrderServiceImpl")
public class WorkOrderServiceImpl extends BaseServiceImpl<WorkOrder, Long>
		implements WorkOrderService {
	@Resource(name = "workOrderDaoImpl")
	private WorkOrderDao workOrderDao;
	@Resource(name = "productionLineServiceImpl")
	private ProductionLineService productionLineService;

	@Resource(name = "workOrderDaoImpl")
	public void setBaseDao(WorkOrderDao workOrderDao) {
		super.setBaseDao(workOrderDao);
	}

	@Override
	public boolean addWorkOrder(WorkOrder workOrder,User user) {
		// TODO Auto-generated method stub
		try {
			List<Filter> filters = new ArrayList<>();
			filters.add(Filter.eq("status", "进行中"));
			filters.add(Filter.eq("isDel", false));
			filters.add(Filter.eq("lineNumber", workOrder.getLineNumber()));
			List<WorkOrder> list = this.findList(null, filters, null);
			if (list.size() > 0) {
				for (WorkOrder workOrder2 : list) {
					workOrder2.setStatus("已完成");
					workOrder2.setEndTime(DatesUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
					this.update(workOrder2);
				}
			}
			workOrder.setLineName(productionLineService.getByParam("lineNumber",workOrder.getLineNumber()).getLineName());
			workOrder.setCreatorName(user.getName());
			workOrder.setCreatTime(DatesUtils.getStringToday("yyyy-MM-dd HH:mm:ss"));
			workOrder.setStartTime(DatesUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			workOrder.setStatus("进行中");
			this.save(workOrder);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

}
