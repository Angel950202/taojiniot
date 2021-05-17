package com.taojin.iot.service.quality.service.impl;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.quality.dao.impl.QualityStandardDaoImpl;
import com.taojin.iot.service.quality.entiy.QualityStandard;
import com.taojin.iot.service.quality.service.QualityStandardService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("qualityStandardServiceImpl")
public class QualityStandardServiceImpl extends BaseServiceImpl<QualityStandard,Long> implements QualityStandardService {
    @Resource(name = "qualityStandardDaoImpl")
    private QualityStandardDaoImpl qualityStandardDaoImpl;

    @Resource(name = "qualityStandardDaoImpl")
    public void setBaseDao(QualityStandardDaoImpl qualityStandardDaoImpl) {
        super.setBaseDao(qualityStandardDaoImpl);
    }
}