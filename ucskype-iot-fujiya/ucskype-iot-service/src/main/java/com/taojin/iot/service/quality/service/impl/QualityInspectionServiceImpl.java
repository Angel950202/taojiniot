package com.taojin.iot.service.quality.service.impl;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.quality.dao.impl.QualityInspectionDaoImpl;
import com.taojin.iot.service.quality.entiy.QualityInspection;
import com.taojin.iot.service.quality.service.QualityInspectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("qualityInspectionServiceImpl")
public class QualityInspectionServiceImpl extends BaseServiceImpl<QualityInspection,Long> implements QualityInspectionService {
    @Resource(name = "qualityInspectionDaoImpl")
    private QualityInspectionDaoImpl qualityInspectionDaoImpl;

    @Resource(name = "qualityInspectionDaoImpl")
    public void setBaseDao(QualityInspectionDaoImpl qualityInspectionDaoImpl) {
        super.setBaseDao(qualityInspectionDaoImpl);
    }
}