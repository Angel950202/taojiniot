package com.taojin.iot.service.task.dao.impl;

import org.springframework.stereotype.Repository;

import com.taojin.iot.base.comm.dao.impl.BaseDaoImpl;
import com.taojin.iot.service.task.dao.WorkFinishDao;
import com.taojin.iot.service.task.entity.WorkFinish;

@Repository("workFinishDaoImpl")
public class WorkFinishDaoImpl extends BaseDaoImpl<WorkFinish,Long> implements WorkFinishDao{

}
