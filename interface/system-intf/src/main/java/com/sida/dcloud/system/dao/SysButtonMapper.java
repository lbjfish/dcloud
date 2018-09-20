package com.sida.dcloud.system.dao;

import com.sida.dcloud.auth.po.SysButton;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysButtonMapper extends IMybatisDao<SysButton> {
    /**
     * 根据页面编码获取按钮
     * @return
     */
    List<SysButton> findByPageCode(@Param("pageCode") String pageCode);
}