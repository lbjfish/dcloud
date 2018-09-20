package com.sida.dcloud.system.service;

import com.sida.dcloud.auth.po.SysResource;
import com.sida.dcloud.auth.vo.PageResourceDTO;
import com.sida.dcloud.auth.vo.SysResourceVo;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;
import java.util.Map;

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
    int insertAnotherInfoByType(SysResourceVo resource, String type);

    /**
     * 根据id删除资源
     * @param id
     */
    int deleteById(String id);

    /**
     * 初始化资源
     */
    void init();

    /**
     * 根据角色信息获取对应资源信息
     * @param roleCode
     * @return
     */
    List<SysResourceVo> findListByRoleCode(String roleId,String roleCode);

    /**
     * 根据页面编码（前端id）获取页面资源map
     * @param pageCode
     * @return
     */
    PageResourceDTO findPageResourceMapByPageCode(String pageCode, String userId);

    /**
     * 获取用户具有的页面按钮权限
     * @param pageCode
     * @param userId
     * @return
     */
    Map<String,SysResource> selectUserButtons(String pageCode, String userId);

    /**
     * 获取用户具有的页面字段权限
     * @param pageCode
     * @param userId
     * @return
     */
    Map<String,SysResource> findUserFields(String pageCode, String userId);
}