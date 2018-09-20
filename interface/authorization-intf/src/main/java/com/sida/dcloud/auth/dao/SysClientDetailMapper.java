package com.sida.dcloud.auth.dao;

import com.sida.dcloud.auth.po.SysClientDetail;
import com.sida.xiruo.xframework.dao.IMybatisDao;

public interface SysClientDetailMapper extends IMybatisDao<SysClientDetail> {
    SysClientDetail selectClientDetailByClientId(String clientId);
}