package com.sida.dcloud.system.dao;

import com.sida.dcloud.auth.po.SysResource;
import com.sida.dcloud.auth.vo.SysResourceVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysResourceMapper extends IMybatisDao<SysResource> {

    /**
     * 根据资源类型获取资源列表
     * @param typeList
     * @return
     */
    List<SysResource> selectByTypeIn(@Param("typeList") List<String> typeList);

    /**
     * 根据id删除资源
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 根据id集合获取资源数据
     * @param resourceIds
     * @return
     */
    List<SysResource> selectByIds(@Param("resourceIds") List<String> resourceIds);

    /**
     * 根据角色信息获取对应资源信息
     * @param roleCode
     * @return
     */
    List<SysResourceVo> findListByRoleCode(@Param("roleId")String roleId, @Param("roleCode") String roleCode);

    /**
     * 获取用户具有的按钮权限
     * @param pageCode
     * @param userId
     * @return
     */
    List<SysResource> findUserButtons(@Param("pageCode")String pageCode, @Param("userId")String userId);

    /**
     * 获取用户具有的字段权限
     * @param pageCode
     * @param userId
     * @return
     */
    List<SysResource> findUserFields(@Param("pageCode")String pageCode, @Param("userId")String userId);

    /**
     * 获取用户拥有的资源列表
     * @param userId
     * @return
     */
    List<SysResourceVo> findUserResources(@Param("userId") String userId);


    /**
     * 获取所有资源
     * @return
     */
    List<SysResourceVo> findResources();
}