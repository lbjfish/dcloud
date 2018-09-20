package com.sida.dcloud.auth.dao;

import com.sida.dcloud.auth.po.SysOrg;
import com.sida.dcloud.auth.vo.SysOrgVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysOrgMapper extends IMybatisDao<SysOrg> {

    /**
     * 根据idPath 逻辑删除组织信息（包含组织下的下级组织）
     * @param id
     */
    void deleteById(@Param("id") String id);

    /**
     * 根据idPath 物理删除组织及下级组织 与 员工 的关联关系
     */
    void deleteEmployeePositionRela(@Param("id") String id);

    /**
     * 根据ids获取组织机构
     * @param ids
     * @return
     */
    List<SysOrg> findByIds(@Param("ids")List<String> ids);

    /**
     * 条件查询组织
     * @param condition
     * @return
     */
    List<SysOrg> findListByVo(SysOrgVo condition);

    /**
     * 批量查询组织
     * @param condition
     * @return
     */
    List<SysOrg> findOrgsByIds(SysOrgVo condition);

    /**
     * 根据片区id获取门店列表
     * @param areaId
     * @return
     */
    List<SysOrg> findStoresByAreaId(@Param("areaId") String areaId);

    /**
     * 获取所有组织机构（只返回id，name）
     * @return
     */
    List<SysOrg> findAllOrg();
}