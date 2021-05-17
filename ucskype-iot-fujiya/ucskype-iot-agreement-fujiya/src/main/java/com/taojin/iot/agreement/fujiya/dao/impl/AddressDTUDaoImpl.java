package com.taojin.iot.agreement.fujiya.dao.impl;

import org.springframework.stereotype.Repository;

import com.taojin.iot.agreement.fujiya.dao.AddressDTUDao;
import com.taojin.iot.agreement.fujiya.entity.AddressDTU;
import com.taojin.iot.base.comm.dao.impl.BaseDaoImpl;

@Repository("addressDTUDaoImpl")
public class AddressDTUDaoImpl extends BaseDaoImpl<AddressDTU,Long> implements AddressDTUDao{

}
