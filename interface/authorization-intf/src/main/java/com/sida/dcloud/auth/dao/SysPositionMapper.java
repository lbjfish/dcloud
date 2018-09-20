package com.sida.dcloud.auth.dao;

import com.sida.dcloud.auth.po.SysPosition;
import com.sida.dcloud.auth.vo.SysPositionVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;

import java.util.List;

public interface SysPositionMapper extends IMybatisDao<SysPosition> {
    List<SysPositionVo> findVoList(SysPosition param);
}