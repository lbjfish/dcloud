package com.sida.dcloud.auth.service;

import com.sida.dcloud.auth.po.SysResource;
import com.sida.dcloud.auth.vo.SysResourceVo;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;

public interface SysResourceService extends IBaseService<SysResource> {

    /**
     * 根据角色id获取 资源vo列表
     * @param roleId
     * @return
     */
    List<SysResourceVo> findVoListByRoleId(String roleId);

    /**
     * 保存角色-资源 关联关系
     * @param roleId
     * @param resourceIds
     * @return
     */
    int saveRoleResource(String roleId, List<String> resourceIds);

    /**
     * 根据资源类型，保存更多扩展信息至拓展表
     * @param resource
     * @param type
     */
    int insertAnotherInfoByType(SysResource resource, String type);

    /**
     * 根据id删除资源
     * @param id
     */
    int deleteById(String id);
}