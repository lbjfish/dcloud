package com.sida.dcloud.system.service;

import com.sida.dcloud.auth.po.SysEmployee;
import com.sida.dcloud.auth.po.SysPosition;
import com.sida.dcloud.auth.vo.EmpListConditionDTO;
import com.sida.dcloud.auth.vo.SysPositionVo;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SysPositionService extends IBaseService<SysPosition> {

    /**
     * 获取岗位列表
     * @param param
     * @return
     */
    Page<SysPositionVo> findPageList(SysPosition param);

    /**
     * 根据岗位id获取员工列表
     * @param param
     * @return
     */
    Page<SysEmployee> findEmpListByPositionId(EmpListConditionDTO param);

    int savePosition(SysPosition param);

    /**
     * 根据岗位id集合 获取映射的 角色编码map
     * @param posiIdList
     * @return
     */
    Map<String,String> findNewRoleCode(List<String> posiIdList);
}