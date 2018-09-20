package com.sida.dcloud.auth.service;

import com.sida.dcloud.auth.po.SysEmployee;
import com.sida.dcloud.auth.vo.EmployeeAddDTO;
import com.sida.dcloud.auth.vo.EmployeeFilterDTO;
import com.sida.dcloud.auth.vo.EmployeeListDTO;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

import java.text.ParseException;

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
}