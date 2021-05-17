package com.taojin.iot.service.task.service.impl;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.task.dao.impl.ProductionLineDaoImpl;
import com.taojin.iot.service.task.entity.ProductionLine;
import com.taojin.iot.service.task.service.ProductionLineService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;



@Service("productionLineServiceImpl")
public class ProductionLineServiceImpl extends BaseServiceImpl<ProductionLine,Long>  implements ProductionLineService{
@Resource(name = "productionLineDaoImpl")
private ProductionLineDaoImpl productionLineDaoImpl;

@Resource(name = "productionLineDaoImpl")
public void setBaseDao(ProductionLineDaoImpl productionLineDaoImpl) {
	super.setBaseDao(productionLineDaoImpl);
}
	
}
