package com.sida.dcloud.system.service;

import com.sida.dcloud.auth.po.SysEmployee;
import com.sida.dcloud.auth.vo.*;
import com.sida.dcloud.system.vo.SysEmpStoreQueryVo;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface SysEmployeeService extends IBaseService<SysEmployee> {
    /**
     * 获取员工管理列表信息
     * @author xieguopei
     * @date 2017/09/04
     * @param employeeFilterDTO
     * @return
     */
    Page<EmployeeListDTO> getEmployeeList(EmployeeFilterDTO employeeFilterDTO);

    /**
     * 保存员工信息
     * @author xieguopei
     * @date 2017/09/05
     * @param employeeAddDTO
     * @return
     */
    Boolean saveEmployeeMes(EmployeeAddDTO employeeAddDTO) throws ParseException;

    /**
     * 获取员工明细信息
     * @author xieguopei
     * @date 2017/09/05
     * @param employeeId
     * @return
     */
    EmployeeListDTO getEmployeeMesById(String employeeId);
    /**
     * 根据片区、姓名、手机号获取员工列表
     * @param employeeFilterDTO
     * @return
     */
    public Page<EmployeeAreaDto> getEmployeeByArea(EmployeeFilterDTO employeeFilterDTO);
    /**
     * 根据员工id、片区id、片区名称获取员工详情
     * @param employeeFilterDTO
     * @return
     * @throws Exception
     */
    public EmployeeAreaDetailDto getEmployeeDetailById(EmployeeFilterDTO employeeFilterDTO) throws Exception;


    /**
     * 根据片区id获取用户
     * @return
     */
    List<SysEmployee> findByAreaId(String areaId, String query);

    /**
     * 获取员工详细信息
     * @param empId
     * @return
     * @throws Exception
     */
    public SysEmployee getSysEmployeeById(String empId) throws Exception;

    /**
     * 获取员工候选值，根据员工id获取候选列表，限制最多30条！
     * @param query
     * @return
     */
    List<Map<String, Object>> findEmployees(String query);

    /**
     * 获取名称
     * @param condition
     * @return
     */
    List<SysEmployee> selectNameByCondition(SysEmployee condition);

    /**
     * 根据组织id和岗位编码获取人员信息：过滤离职员工
     * @param param
     * @return
     */
    Page<SysEmployee> findEmpsByPositionAndOrgId(EmployeeConditionDTO param);

    /**
     * 根据门店和岗位编码获取片区经理
     * @param positionCode
     * @param storeId
     * @return
     */
    List<SysEmployee> getEmpsByStoreAndPositionCode(String positionCode,String storeId);

    /**
     * 根据岗位编码和门店获取人数
     * @param positionCode
     * @param storeId
     * @return
     */
    public int countEmpsByStoreAndPositionCode( String positionCode, String storeId);

    /**
     * 根据岗位编码和门店ID获取员工列表--提供给营销
     * @param sysEmpStoreQueryVo
     * @return
     */
    public Page<SysEmployee> getEmployeeListByStoreIdAndPositionCode(SysEmpStoreQueryVo sysEmpStoreQueryVo);


    /**
     * 更新员工类型
     * @param empId
     * @param empType
     */
    public void updateEmpType(String empId,String empType);

    /**
     * 根据ids批量获取员工岗位信息
     * @param ids
     * @return
     */
    Object getEmpPositionInfoByIds(List<String> ids);

    SysEmployee getEmployeeByThirdId(String thirdId);

    Page<EmpSelectAllDTO> selectAll(String query, Integer p, Integer s);
}