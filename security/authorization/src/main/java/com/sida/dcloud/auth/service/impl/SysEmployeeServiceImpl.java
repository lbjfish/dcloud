package com.sida.dcloud.auth.service.impl;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.dao.*;
import com.sida.dcloud.auth.po.*;
import com.sida.dcloud.auth.service.SysEmployeeService;
import com.sida.dcloud.auth.vo.EmployeeAddDTO;
import com.sida.dcloud.auth.vo.EmployeeFilterDTO;
import com.sida.dcloud.auth.vo.EmployeeListDTO;
import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.PoDefaultValueGenerator;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysEmployeeServiceImpl extends BaseServiceImpl<SysEmployee> implements SysEmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(SysEmployeeServiceImpl.class);

    @Autowired
    private SysEmployeeMapper sysEmployeeMapper;

    @Autowired
    private SysPositionMapper sysPositionMapper;

    @Autowired
    private SysOrgMapper sysOrgMapper;

    @Autowired
    private SysEmployeePositionMapper sysEmployeePositionMapper;

    @Autowired
    private SysEmployeeOrgMapper sysEmployeeOrgMapper;

    @Override
    public IMybatisDao<SysEmployee> getBaseDao() {
        return sysEmployeeMapper;
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * 获取员工管理列表信息
     *
     * @param employeeFilterDTO
     * @return
     * @author xieguopei
     * @date 2017/09/04
     */
    @Override
    public Page<EmployeeListDTO> getEmployeeList(EmployeeFilterDTO employeeFilterDTO) {
        Map<String, Object> map = new HashMap<>();

        map.put("0", employeeFilterDTO);

        PageHelper.startPage(employeeFilterDTO.getP(), employeeFilterDTO.getS());

        List<EmployeeListDTO> list = sysEmployeeMapper.getEmployeeListFilter(map);
        return (Page) list;
    }

    /**
     * 保存员工信息
     *
     * @param employeeAddDTO
     * @return
     * @author xieguopei
     * @date 2017/09/05
     */
    @Override
    public Boolean saveEmployeeMes(EmployeeAddDTO employeeAddDTO) throws ParseException {
        SysEmployee sysEmployee;
        SysPosition sysPosition;
        SysOrg sysOrg;
        SysEmployeeOrg sysEmployeeOrg = new SysEmployeeOrg();
        SysEmployeePosition sysEmployeePosition = new SysEmployeePosition();
        String posId;
        String orgId;
        String employeeId;
        int status;

        posId = employeeAddDTO.getPosId();
        orgId = employeeAddDTO.getOrgId();

        sysEmployee = getEmployeeByMes(employeeAddDTO);

        status = sysEmployeeMapper.insertSelective(sysEmployee);

        if(status <= 0){
            return false;
        }

        employeeId = sysEmployee.getId();

        sysPosition = sysPositionMapper.selectByPrimaryKey(posId);

        if(null == sysPosition) {
            return false;
        }

        sysOrg = sysOrgMapper.selectByPrimaryKey(orgId);

        if(null == sysOrg) {
            return false;
        }

        sysEmployeeOrg.setEmployeeId(employeeId);
        sysEmployeeOrg.setOrgId(orgId);

        status = sysEmployeeOrgMapper.insert(sysEmployeeOrg);

        if(status <= 0) {
            return false;
        }

        sysEmployeePosition.setEmployeeId(employeeId);
        sysEmployeePosition.setPositionId(posId);

        status = sysEmployeePositionMapper.insert(sysEmployeePosition);

        if(status <= 0) {
            return false;
        }

        return true;
    }

    /**
     * 获取员工明细信息
     *
     * @param employeeId
     * @return
     * @author xieguopei
     * @date 2017/09/05
     */
    @Override
    public EmployeeListDTO getEmployeeMesById(String employeeId) {
        SysEmployee sysEmployee = new SysEmployee();

        sysEmployee.setId(employeeId);
        PageHelper.startPage(0, 1);
        List<EmployeeListDTO> list = sysEmployeeMapper.getEmployeeList(sysEmployee);
        Page<EmployeeListDTO> page = (Page) list;
        if (page.getResult()!=null){
            return page.getResult().get(0);
        }
        return new EmployeeListDTO();
    }

    /**
     * 根据对应视图信息获取员工信息
     * @author xieguopei
     * @date 2017/09/05
     * @param employeeAddDTO
     * @return
     */
    private SysEmployee getEmployeeByMes(EmployeeAddDTO employeeAddDTO) throws ParseException {
        SysEmployee sysEmployee = new SysEmployee();
        Date dumpDate;
        String birthday;
        String sex;
        String validDate;
        String invalidDate;
        String id;

        birthday = employeeAddDTO.getBirthday();
        sex = employeeAddDTO.getSex();
        validDate = employeeAddDTO.getBeginTime();
        invalidDate = employeeAddDTO.getEndTime();
        id = employeeAddDTO.getId();

        dumpDate = sdf.parse(birthday);

        if(!StringUtils.isBlank(id)) {
            sysEmployee.setId(id);
        }

        PoDefaultValueGenerator.putDefaultValue(sysEmployee);

        sysEmployee.setName(employeeAddDTO.getEmpName());
        sysEmployee.setWorkCard(employeeAddDTO.getWorkCard());
        sysEmployee.setIdcardNumber(employeeAddDTO.getIdNumber());
        sysEmployee.setBirthday(dumpDate);

        if(SecConstant.MAN.equals(sex)) {
            sysEmployee.setSex("0");
        } else {
            sysEmployee.setSex("1");
        }

        sysEmployee.setNation(employeeAddDTO.getNation());
        sysEmployee.setIdcardAddressDetail(employeeAddDTO.getAddress());
        sysEmployee.setIdcardIissuingAuthority(employeeAddDTO.getAuthority());

        dumpDate = sdf.parse(validDate);

        sysEmployee.setIdcardValidDate(dumpDate);

        dumpDate = sdf.parse(invalidDate);

        sysEmployee.setIdcardInvalidDate(dumpDate);

        sysEmployee.setPhone(employeeAddDTO.getPhone());

        return sysEmployee;
    }
}