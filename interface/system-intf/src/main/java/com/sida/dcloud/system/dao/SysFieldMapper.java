package com.sida.dcloud.system.dao;

import com.sida.dcloud.auth.po.SysField;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysFieldMapper extends IMybatisDao<SysField> {
    /**
     * 根据页面编码获取按钮
     * @return
     */
    List<SysField> findByPageCode(@Param("pageCode") String pageCode);

    /**
     * 获取字段资源
     * @param pageCode
     * @param nameList
     * @param codeList
     * @return
     */
    List<SysField> findFields(@Param("pageCode") String pageCode,
                              @Param("nameList") List<String> nameList,
                              @Param("codeList") List<String> codeList);
}