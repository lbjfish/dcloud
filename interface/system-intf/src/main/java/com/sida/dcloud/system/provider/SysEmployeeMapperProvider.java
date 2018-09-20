package com.sida.dcloud.system.provider;

import com.sida.dcloud.auth.vo.EmployeeFilterDTO;
import com.sida.xiruo.common.components.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * Created by xieguopei on 2017/9/6.
 */
public class SysEmployeeMapperProvider {
    /**
     * 查询员工信息
     * @author xieguopei
     * @date 2017/09/06
     * @param map
     * @return
     */
    public String selEmployee(Map<String, Object> map) {
        StringBuffer stringBuffer = new StringBuffer("");
        EmployeeFilterDTO employeeFilterDTO;
        String name;
        String phone;
        String posId;
        String dumpSql;
        int page;
        int size;

        employeeFilterDTO = (EmployeeFilterDTO) map.get("0");

        name = employeeFilterDTO.getName();
        phone = employeeFilterDTO.getPhone();
        posId = employeeFilterDTO.getPosId();
        page = employeeFilterDTO.getP();
        size = employeeFilterDTO.getS();

        dumpSql = new SQL() {{
            String dumpSql;

            SELECT(" employee.id as id," +
                    "employee.work_card as work_card," +
                    "employee.name as emp_name, " +
                    "org.name as emp_org, " +
                    "pos.name as emp_pos, " +
                    "employee.sex as sex, " +
                    "employee.idcard_number as idcard_number ");
            FROM("sys_employee employee");
            LEFT_OUTER_JOIN(
                    "sys_employee_org empOrg on empOrg.employee_id = employee.id ",
                    "sys_org org on org.id = empOrg.org_id ",
                    "sys_employee_position empPos on empPos.employee_id = employee.id ",
                    "sys_position pos on pos.id = empPos.position_id ");

            if(!StringUtils.isBlank(phone)) {
                stringBuffer.append(" employee.phone like '%" + phone + "%'");
            }

            if(!StringUtils.isBlank(name)) {
                stringBuffer.append(" employee.name like '%" + name + "%'");
            }

            if(!StringUtils.isBlank(posId)) {
                stringBuffer.append(" pos.id = '" + posId + "'");
            }

            dumpSql = stringBuffer.toString();

            if(!StringUtils.isBlank(dumpSql)) {
                WHERE(dumpSql);
            }
        }}.toString();

        return dumpSql;
    }
}
