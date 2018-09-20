package com.sida.dcloud.auth.dao;

import com.sida.dcloud.auth.po.SysEmployee;
import com.sida.dcloud.auth.provider.SysEmployeeMapperProvider;
import com.sida.dcloud.auth.vo.EmpListConditionDTO;
import com.sida.dcloud.auth.vo.EmployeeListDTO;
import com.sida.xiruo.xframework.dao.IMybatisDao;
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
}