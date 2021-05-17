package com.taojin.iot.service.quality.service.impl;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.quality.dao.impl.QualityInspectionDetailDaoImpl;
import com.taojin.iot.service.quality.entiy.QualityInspectionDetail;
import com.taojin.iot.service.quality.service.QualityInspectionDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("qualityInspectionDetailServiceImpl")
public class QualityInspectionDetailServiceImpl extends BaseServiceImpl<QualityInspectionDetail,Long> implements QualityInspectionDetailService {
    @Resource(name = "qualityInspectionDetailDaoImpl")
    private QualityInspectionDetailDaoImpl qualityInspectionDetailDaoImpl;

    @Resource(name = "qualityInspectionDetailDaoImpl")
    public void setBaseDao(QualityInspectionDetailDaoImpl qualityInspectionDetailDaoImpl) {
        super.setBaseDao(qualityInspectionDetailDaoImpl);
    }
}