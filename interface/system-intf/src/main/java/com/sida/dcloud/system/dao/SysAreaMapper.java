package com.sida.dcloud.system.dao;

import com.sida.dcloud.auth.po.SysOrg;
import com.sida.dcloud.system.po.SysArea;
import com.sida.dcloud.system.vo.SysAreaVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAreaMapper extends IMybatisDao<SysArea> {
    /**
     * 条件查询片区列表
     * @param area
     * @return
     */
    List<SysAreaVo> findVoList(SysArea area);

    /**
     * 初始化片区-插入新增数据
     * @return
     */
    int insertAreaByNewOrg(@Param(value = "type") Integer type);

    /**
     * 初始化片区-更新修改数据
     * @return
     */
    int updateAreaByModifyOrg(@Param(value = "orgList") List<SysOrg> orgList);

    /**
     * 逻辑删除所有数据
     * @return
     */
    int deleteAll();

    /**
     * 根据ids获取片区列表
     * @param ids
     * @return
     */
    List<SysArea> findByIdIn(@Param(value = "ids") List<String> ids);

    /**
     * 逻辑删除片区
     * @param ids
     */
    void deleteByIds(@Param(value = "ids") List<String> ids);

    /**
     * 新增片区
     * @param orgList
     */
    void insertWithOrgs(@Param(value = "orgList") List<SysOrg> orgList);

    /**
     * 物理删除所有
     */
    void removeAll();

    /**
     * 根据组织全量录入片区信息
     */
    void insertWithAllOrgs();
}