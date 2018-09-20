package com.sida.dcloud.system.dao;

import com.sida.dcloud.auth.po.SysOrg;
import com.sida.dcloud.auth.vo.EmpDTO;
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

    /**
     * 获取已存在的片区对应的org数据
     * @param type
     * @return
     */
    List<SysOrg> findModifyOrg(@Param(value = "type") Integer type);

    /**
     * 获取已存在的片区对应的org数据
     * @param type
     * @return
     */
    List<SysOrgVo> findModifyStore(@Param(value = "type") Integer type);

    int updateStoreByModifyOrg(@Param(value = "orgList") List<SysOrgVo> orgList);

    int insertStoreByNewOrg(@Param(value = "type") Integer type,@Param(value = "areaType") Integer areaType);

    /**
     * 根据片区idpath获取下属门店
     * @param areaPath
     * @return
     */
    List<SysOrg> findStoresByAreaPath(@Param(value = "areaPath") String areaPath,@Param(value = "type")Integer type);

    /**
     * 获取指定驾校的 片区-门店
     * @param orgId
     * @return
     */
    List<SysOrg> selectAreasAndStores(@Param(value = "orgId") String orgId,
                                      @Param(value = "areaType") Integer areaType,
                                      @Param(value = "storeType") Integer storeType);

    /**
     * 条件查询 组织拓展信息
     * @param condition
     * @return
     */
    List<SysOrgVo> findVoByCondition(SysOrgVo condition);

    /**
     * 根据片区ids，获取下属门店信息
     * @param areaIds
     * @return
     */
    List<SysOrg> findStoresByAreaIds(@Param(value = "areaIds") List<String> areaIds,
                                     @Param(value = "areaType") Integer areaType,
                                     @Param(value = "storeType") Integer storeType);

    /**
     * 获取所有门店信息
     * @return
     */
    List<SysOrg> findAllStores();

    /**
     * 从某个组织往下追溯组织树
     * @param orgId
     * @return
     */
    List<SysOrg> findTreeFrom(@Param(value = "orgId") String orgId);

    /**
     * 获取组织下员工id集合
     * @param orgId
     * @return
     */
    List<EmpDTO> findOrgEmps(@Param(value = "orgId") String orgId);

    /**
     * 根据ids获取vo集合
     * @param ids
     * @return
     */
    List<SysOrgVo> findVoByIds(@Param(value = "ids") List<String> ids);

    /**
     * 获取门店信息
     * @param type
     * @return
     */
    List<SysOrgVo> findAllOrgsByType(@Param(value = "type") Integer type);

    /**
     * 初始化门店业务状态
     */
    void updateStoreBusinessStatus();

    /**
     * 初始化驾校标识
     */
    void updateSchoolFlag();

    /**
     * 获取驾校
     * @return
     */
    List<SysOrg> findOrgsBySchoolFlag();

    /**
     * 设置驾校标识
     * @param ids
     */
    void updateSchoolFlagWithIds(@Param(value = "ids") List<String> ids);

    /**
     * 获取员工关联组织
     * @param employeeId
     * @return
     */
    SysOrg findOrgByEmpId(@Param(value = "employeeId") String employeeId);

    /**
     * 车务获取使用人
     * @param example
     * @return
     */
    List<EmpDTO> orgEmpsForCar(SysOrg example);

    /**
     * 根据第三方ids获取org
     * @param thirdPartyIds
     * @return
     */
    List<SysOrg> findSysOrgByThirdPartyIds(@Param("list") List<String> thirdPartyIds);

    /**
     *
     * @param orgId
     * @return
     */
    List<SysOrg> findDeptsByOrgId(String orgId);
}