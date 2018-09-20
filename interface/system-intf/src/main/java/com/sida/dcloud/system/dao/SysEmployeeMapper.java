package com.sida.dcloud.system.dao;

import com.sida.dcloud.auth.po.SysEmployee;
import com.sida.dcloud.auth.vo.*;
import com.sida.dcloud.system.provider.SysEmployeeMapperProvider;
import com.sida.dcloud.system.vo.SysEmpStoreQueryVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

public interface SysEmployeeMapper extends IMybatisDao<SysEmployee> {
    /**
     * 查询员工管理列表信息
     * @author xieguopei
     * @date 2017/09/04
     * @param sysEmployee
     * @return
     */
    @Select("select employee.id as id," +
            "employee.work_card as work_card," +
            "employee.name as emp_name, " +
            "org.name as emp_org, " +
            "pos.name as emp_pos, " +
            "employee.sex as sex, " +
            "employee.idcard_number as idcard_number " +
            "from sys_employee employee " +
            "left join sys_employee_org empOrg on empOrg.employee_id = employee.id " +
            "left join sys_org org on org.id = empOrg.org_id " +
            "left join sys_employee_position empPos on empPos.employee_id = employee.id " +
            "left join sys_position pos on pos.id = empPos.position_id ")
    @ResultMap("SysEmployeeDtoList")
    List<EmployeeListDTO> getEmployeeList(SysEmployee sysEmployee);

    /**
     * 查询员工信息
     * @author xieguopei
     * @date 2017/09/06
     * @param map
     * @return
     */
    @SelectProvider(type = SysEmployeeMapperProvider.class, method = "selEmployee")
    @ResultMap("SysEmployeeDtoList")
    List<EmployeeListDTO> getEmployeeListFilter(Map<String, Object> map);

    /**
     * 根据岗位id获取对应的员工列表
     * @param param
     * @return
     */
    List<SysEmployee> findEmpListByPositionId(EmpListConditionDTO param);

    List<EmployeeAreaDto>  getEmployeeListArea(EmployeeFilterDTO employeeFilterDTO);

    List<EmployeeAreaDto>  getEmployeeAllListArea(EmployeeFilterDTO employeeFilterDTO);


    /**
     * 根据片区id获取用户
     * @param areaId
     * @return
     */
    List<SysEmployee> findByAreaId(@Param("areaId") String areaId,@Param("query") String query);

    SysEmployee getEmployeeDetail(@Param("id")String id);

    /**
     * 获取员工候选值列表，最多30条
     * @param query
     * @param orgId
     * @return
     */
    List<SysEmployeeVo> findEmployees(@Param(value = "query") String query, @Param(value = "orgId") String orgId);

    /**
     * 获取姓名
     * @param condition
     * @return
     */
    List<SysEmployee> selectNameByCondition(SysEmployee condition);

    /**
     * 根据组织id和岗位编码获取人员信息：过滤离职员工
     * @param param
     * @return
     */
    List<SysEmployee> findEmpsByPositionAndOrgId(EmployeeConditionDTO param);

    /**
     * 获取员工对象-拓展组织岗位信息
     * @param id
     * @return
     */
    SysEmployeeVo findEmployeeVoById(@Param(value = "id") String id);

    /**
     * 根据门店ID和岗位获取人员
     * @param positionCode
     * @param areaId
     * @return
     */
    List<SysEmployee> getEmpsByStoreAndPositionCode(@Param("positionCode")String positionCode,  @Param("areaId")String areaId);

    /**
     * 根据门店ID和岗位编码获取人员人数
     * @param positionCode
     * @param areaId
     * @return
     */
    int countEmpsByStoreAndPositionCode(@Param("positionCode")String positionCode, @Param("storeId")String areaId);

    /**
     * 根据门店ID和岗位编码获取人员列表--提供给营销
     * @param sysEmpStoreQueryVo
     * @return
     */
    List<SysEmployee> getEmployeeListByStoreIdAndPositionCode(SysEmpStoreQueryVo sysEmpStoreQueryVo);

    /**
     * 更新员工类型
     * @param empId
     * @param empType
     */
    void updateEmpType(@Param("empId") String empId, @Param("empType") String empType);

    /**
     * 批量获取与员工扩展信息
     * @param ids
     * @return
     */
    List<SysEmployeeVo> getEmployeeVoListByIds(@Param(value = "ids") List<String> ids);

    /**
     * 获取所有员工
     * @param query
     * @return
     */
    List<EmpSelectAllDTO> selectAll(@Param(value = "query") String query, @Param(value = "orgId") String orgId);
}