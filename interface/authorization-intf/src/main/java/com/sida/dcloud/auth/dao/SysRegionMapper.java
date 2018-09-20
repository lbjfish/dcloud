package com.sida.dcloud.auth.dao;

import com.sida.dcloud.auth.po.SysRegion;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

public interface SysRegionMapper extends IMybatisDao<SysRegion> {

    /**
     * 根据path逻辑删除其本身和子级数据
     * @param id
     * @return
     */
    int deleteById(@Param("id") String id);
}