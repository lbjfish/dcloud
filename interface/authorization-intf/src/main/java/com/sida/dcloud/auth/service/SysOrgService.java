package com.sida.dcloud.auth.service;

import com.sida.dcloud.auth.po.SysOrg;
import com.sida.xiruo.xframework.service.IBaseService;

public interface SysOrgService extends IBaseService<SysOrg> {
    /**
     * 获取组织树
     * @return
     */
    Object findTree();

    /**
     * 根据组织id 删除组织信息，同时删除 员工-组织 关联关系
     * @param id
     * @return
     */
    void deleteById(String id);

    /**
     * 根据组织id获取对应的组织信息
     * @author xieguopei
     * @date 2017/09/05
     * @param id
     * @return
     */
    Object selectOrgMesById(String id);
}