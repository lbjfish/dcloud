package com.sida.dcloud.system.dao;

import com.sida.dcloud.auth.po.SysUserHiddenField;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserHiddenFieldMapper extends IMybatisDao<SysUserHiddenField> {
    /**
     * 获取用户设置的隐藏列
     * @param pageCode
     * @param userId
     * @return
     */
    List<SysUserHiddenField> findUserField(@Param("pageCode") String pageCode, @Param("userId") String userId);

    int deleteByUserIdAndPageCode(@Param("userId") String userId,@Param("pageCode") String pageCode);

    int insertMany(@Param("hiddenFields")List<SysUserHiddenField> hiddenFields);
}