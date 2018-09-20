package com.sida.dcloud.auth.dao;

import com.sida.dcloud.auth.po.SysRoleResource;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleResourceMapper extends IMybatisDao<SysRoleResource> {

    /**
     * 根据角色id和资源类型物理删除 角色-资源关联关系
     * @param roleId
     * @param typeList
     * @return
     */
    int deleteByRoleIdAndType(@Param("roleId") String roleId, @Param("typeList") List<String> typeList);

    /**
     * 批量插入关联关系
     * @param roleResources
     * @return
     */
    int addManyRoleResource(@Param("roleResources") List<SysRoleResource> roleResources);

    /**
     * 根据角色id和父级id删除 角色-资源关联关系
     * @param roleId
     * @param parentIdList
     * @return
     */
    int deleteByRoleIdAndParentId(@Param("roleId") String roleId, @Param("parentIdList") List<String> parentIdList);

    /**
     * 根据角色id和子资源id集合，批量插入数据（包含其父级资源）
     * @param roleId
     * @param resourceIds
     */
    int addByRoleIdAndChildrenResIds(@Param("roleId") String roleId, @Param("resourceIds") List<String> resourceIds);
}