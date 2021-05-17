package com.taojin.iot.service.quality.service.impl;

import com.taojin.iot.base.comm.service.impl.BaseServiceImpl;
import com.taojin.iot.service.quality.dao.impl.QualityStandardDetailDaoImpl;
import com.taojin.iot.service.quality.entiy.QualityStandardDetail;
import com.taojin.iot.service.quality.service.QualityStandardDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("qualityStandardDetailServiceImpl")
public class QualityStandardDetailServiceImpl extends BaseServiceImpl<QualityStandardDetail,Long> implements QualityStandardDetailService {
    @Resource(name = "qualityStandardDetailDaoImpl")
    private QualityStandardDetailDaoImpl qualityStandardDetailDaoImpl;

    @Resource(name = "qualityStandardDetailDaoImpl")
    public void setBaseDao(QualityStandardDetailDaoImpl qualityStandardDetailDaoImpl) {
        super.setBaseDao(qualityStandardDetailDaoImpl);
    }
}