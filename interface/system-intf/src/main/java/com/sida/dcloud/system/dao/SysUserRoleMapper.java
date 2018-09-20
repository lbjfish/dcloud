package com.sida.dcloud.system.dao;

import com.sida.dcloud.auth.po.SysUserRole;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleMapper extends IMybatisDao<SysUserRole> {
    /**
     * 批量插入
     * @param list
     */
    void batchInsert(@Param(value = "list") List<SysUserRole> list);

    /**
     * 全量更新
     */
    void insertWithEmployeePosition();

    /**
     * 根据用户ids删除关联关系:仅删除那些具备third_party_id 的同步岗位对应的角色信息
     * @param userIds
     */
    void deleteByUserIds(@Param(value = "userIds") List<String> userIds);

    /**
     * 物理删除所有
     */
    void removeAll();
}