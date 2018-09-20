package com.sida.dcloud.system.service;

import com.sida.dcloud.auth.po.SysOrg;
import com.sida.dcloud.auth.vo.*;
import com.sida.xiruo.xframework.service.IBaseService;

import java.util.List;
import java.util.Map;

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
    List<SysOrg> findStoresByAreaId(String areaId);


    Map<String, String> findAllForCache();
    /**
     * 初始化组织机构：缓存数据至redis
     */
    void loadOrgsToRedis();

    /**
     * 初始化片区
     */
    void initArea();

    /**
     * 初始化门店
     */
    void initStore();

    /**
     * 获取指定用户所在驾校的片区数据
     * @param userId
     * @return
     */
    List<SysOrg> findAreasByUserId(String userId);

    /**
     * 获取登录人所在片区的门店数据
     * @param userId
     * @return
     */
    List<SysOrg> findStoresByUserId(String userId);

    /**
     * 获取指定用户所在驾校的 片区-门店
     * @param userId
     * @return
     */
    List<SysOrg> findAreasAndStoresByUserId(String userId);

    /**
     * 条件查询 组织拓展信息
     * @param condition
     * @return
     */
    List<SysOrgVo> findVoByCondition(SysOrgVo condition);

    /**
     * 新增 | 更新组织
     * @param param
     * @return
     */
    int saveOrUpdateOrg(SysOrg param);

    /**
     * 根据片区id集合获取下属片区列表
     * @param areaIds
     * @return
     */
    List<SysOrg> findStoresByAreaIds(List<String> areaIds);

    /**
     * 获取所有门店
     * @return
     */
    List<Map<String,Object>> findAllStores();

    /**
     * 获取所有片区
     * @return
     */
    List<Map<String,Object>> findAllAreas();

    /**
     * 从某个组织往下追溯组织树
     * @param orgId
     * @return
     */
    Object findTreeFrom(String orgId);

    /**
     * 获取该组织下所有的员工及其岗位
     * @param orgId
     * @return
     */
    List<EmpDTO> orgEmps(String orgId);

    /**
     * 获取登录用户片区候选值
     * @return
     */
    List<Map<String,Object>> findUserOrgsList(SysBaseDTO param);

    /**
     * 获取登录用户片区候选值
     * @return
     */
    List<Map<String,Object>> findUserOrgsWithInsert();

    /**
     * 首次初始化片区门店
     */
    void firstInitOrg();

    /**
     * 增量同步触发刷新片区门店
     * @param orgList
     */
    InitStoreDTO incrSyncInitOrg(List<SysOrg> orgList);


    /**
     * 批量更新机构缓存
     * @param data
     */
    void batchUpdateCache(List<SysOrg> data);

    /**
     * 获取门店信息
     * @param type
     * @return
     */
    List<SysOrgVo> findAllOrgsByType(Integer type);

    /**
     * 初始化门店业务状态
     */
    void updateStoreBusinessStatus();

    /**
     * 初始化驾校标识
     */
    void updateSchoolFlag();

    /**
     * 初始化驾校标识
     */
    void updateSchoolFlag(List<String> ids);

    /**
     * 获取驾校
     * @return
     */
    List<SysOrg> findOrgsBySchoolFlag();

    /**
     * 根据ids获取组织
     * @param ids
     * @return
     */
    Map<String,SysOrg> findMapByIds(List<String> ids);

    /**
     * 车务事故理赔专用-根据责任人员工id获取部门负责人列表（部门|片区经理）优先
     * @param employeeId
     * @return
     */
    Map<String,Object> findLeaderFirstByEmployeeId(String employeeId);

    /**
     * 获取员工所属责任部门信息
     */
    EmpOrgDTO findEmpOrgInfoByEmpId(String employeeId);

    /**
     * 从某个组织往下追溯组织树-排除门店级别
     * @param orgId
     * @return
     */
    Object findTreeFromWithoutStore(String orgId);

    /**
     * 根据orgId获取下属员工，如果是片区id，则同时追加门店人员进去
     * @param orgId
     * @param plateColor  排序优先级：如果车牌颜色为0-黄色 则优先显示教练，否则优先显示非教练
     * @return
     */
    List<EmpDTO> orgEmpsForCar(String orgId, String plateColor);

    /**
     * 根据第三方ids获取组织
     * @param thirdPartyIds
     * @return
     */
    public List<SysOrg> findSysOrgByThirdPartyIds(List<String> thirdPartyIds);

    /**
     * 根据名称获取ID:唯一一条则返回名称，无或多条返回空字符串
     * @param name
     * @return
     */
    String findOrgIdByName(String name);

    /**
     * 根据驾校id查询该驾校下的所有部门
     * @param orgId
     * @return
     */
    List<SysOrg> findDeptsByOrgId(String orgId);

    /**
     * 营销修改营业状态
     * @param sysOrg
     */
    void updateStoreByMarketing(SysOrg sysOrg);
}