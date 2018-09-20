package com.sida.dcloud.system.dao;

import com.sida.dcloud.auth.po.SysPosition;
import com.sida.dcloud.auth.vo.SysPositionVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysPositionMapper extends IMybatisDao<SysPosition> {
    List<SysPositionVo> findVoList(SysPosition param);

    /**
     * 根据岗位id集合 获取映射的 角色编码集合
     * @param ids
     * @return
     */
    List<SysPosition> findNewRoleCode(@Param(value = "ids") List<String> ids);
}