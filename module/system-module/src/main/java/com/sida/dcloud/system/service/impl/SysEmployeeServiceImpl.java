package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.po.*;
import com.sida.dcloud.auth.vo.*;
import com.sida.dcloud.system.config.DruidConfig;
import com.sida.dcloud.system.dao.*;
import com.sida.dcloud.system.service.SysEmployeeService;
import com.sida.dcloud.system.service.SysUserService;
import com.sida.dcloud.system.vo.SysEmpStoreQueryVo;
import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.PoDefaultValueGenerator;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DruidConfig druidConfig;
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
    @Transactional
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

        status = sysUserService.insertUserWithEmployee(sysEmployee);

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

    /**
     * 根据片区、姓名、手机号获取员工列表
     * 1.按姓名查询 2。按手机好查询
     * @param employeeFilterDTO
     * @return
     */
    public  Page<EmployeeAreaDto> getEmployeeByArea(EmployeeFilterDTO employeeFilterDTO){
        String orgId = LoginManager.getCurrentOrgId();
        employeeFilterDTO.setOrgId(orgId);
        PageHelper.startPage(employeeFilterDTO.getP(), employeeFilterDTO.getS());
        String teachPositionCode = druidConfig.getTeachPositionCode();
        String posCode = "";
        for(String code:teachPositionCode.split(",")){
            posCode+=","+"'"+code+"'";
        }
        if(BlankUtil.isNotEmpty(posCode)){
            posCode=posCode.substring(1);
            employeeFilterDTO.setTeachPostionCode(posCode);
        }
        List<EmployeeAreaDto> list = sysEmployeeMapper.getEmployeeListArea(employeeFilterDTO);
        Page<EmployeeAreaDto> page = (Page) list;
        List<EmployeeAreaDto> listEmployee = page.getResult();
        getEmpListDeal(listEmployee);
        return page;

    }
    public void getEmpListDeal(List<EmployeeAreaDto> listEmployee){
        for(EmployeeAreaDto dto:listEmployee){
            String idPath = dto.getIdPath();
            List<String> ids = new ArrayList<String>();
            if(idPath!=null&&!idPath.equals("")){
                String [] queryArray = idPath.split(",");
                for(String id:queryArray) {
                    if(id!=null&&!id.equals(""))
                        ids.add(id);
                }
            }
            SysOrgVo condition = new SysOrgVo();
            condition.setType(2);
            condition.setIds(ids);
            List<SysOrg> sysOrgList=sysOrgMapper.findListByVo(condition);
            if(sysOrgList!=null&&sysOrgList.size()>0){
                dto.setAreaId(sysOrgList.get(0).getId());
                dto.setAreaName(sysOrgList.get(0).getName());
            }
        }
    }

    /**
     * 根据员工id、片区id、片区名称获取员工详情
     * @param employeeFilterDTO
     * @return
     * @throws Exception
     */

    public EmployeeAreaDetailDto getEmployeeDetailById(EmployeeFilterDTO employeeFilterDTO) throws Exception {
        EmployeeAreaDetailDto employeeAreaDetailDto = null;
        //SysEmployee sysEmployee = sysEmployeeMapper.selectByPrimaryKey(employeeFilterDTO.getId());

        SysEmployee sysEmployee = sysEmployeeMapper.getEmployeeDetail(employeeFilterDTO.getId());
        if (sysEmployee != null){
            employeeAreaDetailDto = new EmployeeAreaDetailDto();
            PropertyUtils.copyProperties(employeeAreaDetailDto,sysEmployee);

            String idPath = employeeAreaDetailDto.getIdPath();
            List<String> ids = new ArrayList<String>();
            if(idPath!=null&&!idPath.equals("")){
                String [] queryArray = idPath.split(",");
                for(String id:queryArray) {
                    if(id!=null&&!id.equals(""))
                        ids.add(id);
                }
            }
            SysOrgVo condition = new SysOrgVo();
            condition.setType(2);
            condition.setIds(ids);
            List<SysOrg> sysOrgList=sysOrgMapper.findListByVo(condition);
            if(sysOrgList!=null&&sysOrgList.size()>0){
                employeeAreaDetailDto.setAreaId(sysOrgList.get(0).getId());
                employeeAreaDetailDto.setAreaName(sysOrgList.get(0).getName());
            }



           // employeeAreaDetailDto.setAreaId(employeeFilterDTO.getAreaId());
           // employeeAreaDetailDto.setAreaName(employeeFilterDTO.getAreaName());
       }
       return employeeAreaDetailDto;
    }

    @Override
    public List<SysEmployee> findByAreaId(String areaId,String query) {
        return sysEmployeeMapper.findByAreaId(areaId,query);
    }

    public SysEmployee getSysEmployeeById(String empId) throws Exception{
        return this.sysEmployeeMapper.selectByPrimaryKey(empId);
    }

    @Override
    public List<Map<String, Object>> findEmployees(String query) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<SysEmployeeVo> empList = sysEmployeeMapper.findEmployees(query,LoginManager.getCurrentOrgId());
        if (BlankUtil.isNotEmpty(empList)){
            for (SysEmployeeVo emp : empList){
                Map<String,Object> map = new HashMap<>();
                map.put("value",emp.getId());
                map.put("label",emp.getName());
                map.put("mobile",emp.getMobile());
                map.put("deptId",emp.getDeptId());
                map.put("empType",emp.getEmpType());
                mapList.add(map);
            }
        }
        return mapList;
    }

    @Override
    public List<SysEmployee> selectNameByCondition(SysEmployee condition) {
        return sysEmployeeMapper.selectNameByCondition(condition);
    }

    @Override
    public Page<SysEmployee> findEmpsByPositionAndOrgId(EmployeeConditionDTO param) {
        PageHelper.startPage(param.getP(),param.getS());
        if (BlankUtil.isNotEmpty(param.getPositionCodeGroupStr())){
            String[] codeList = param.getPositionCodeGroupStr().split(",");
            if (BlankUtil.isEmpty(param.getPositionCodeGroup())){
                param.setPositionCodeGroup(Lists.newArrayList());
            }
            Collections.addAll(param.getPositionCodeGroup(), codeList);
        }
        List<SysEmployee> list = sysEmployeeMapper.findEmpsByPositionAndOrgId(param);
        return (Page) list;
    }

    @Override
    public List<SysEmployee> getEmpsByStoreAndPositionCode(String positionCode, String storeId) {
        SysOrg sysOrg = this.sysOrgMapper.selectByPrimaryKey(storeId);
        String areaId = sysOrg.getParentId();
        List<SysEmployee> list = sysEmployeeMapper.getEmpsByStoreAndPositionCode(positionCode,areaId);
        return list;
    }

    @Override
    public int countEmpsByStoreAndPositionCode(String positionCode, String storeId) {
        return sysEmployeeMapper.countEmpsByStoreAndPositionCode(positionCode,storeId);
    }

    /**
     * 根据岗位编码和门店ID获取员工列表--提供给营销
     * @param sysEmpStoreQueryVo
     * @return
     */
    @Override
    public Page<SysEmployee> getEmployeeListByStoreIdAndPositionCode(SysEmpStoreQueryVo sysEmpStoreQueryVo) {
        PageHelper.startPage(sysEmpStoreQueryVo.getP(),sysEmpStoreQueryVo.getS());
        List<SysEmployee> list = sysEmployeeMapper.getEmployeeListByStoreIdAndPositionCode(sysEmpStoreQueryVo);
        return (Page)list;
    }

    /**
     * 更新员工类型
     * @param empId
     * @param empType
     */
    public void updateEmpType(String empId,String empType){
        this.sysEmployeeMapper.updateEmpType(empId,empType);
    }

    @Override
    public Object getEmpPositionInfoByIds(List<String> ids) {
        Map<String,String> map = new HashMap<>();
        if (BlankUtil.isNotEmpty(ids)){
            List<SysEmployeeVo> voList = sysEmployeeMapper.getEmployeeVoListByIds(ids);
            if (BlankUtil.isNotEmpty(voList)){
                for (SysEmployeeVo vo : voList){
                    map.put(vo.getId(),vo.getPositionName());
                }
            }
        }
        return map;
    }
    @Override
    public SysEmployee getEmployeeByThirdId(String thirdId) {
        SysEmployee condition = new SysEmployee();
        condition.setThirdPartyId(Long.parseLong(thirdId));
        condition.setDeleteFlag(false);
        condition.setDisable(false);
        List<SysEmployee> listEmployee =this.sysEmployeeMapper.selectByCondition(condition);
        if(BlankUtil.isNotEmpty(listEmployee)) {
            SysEmployee employee= listEmployee.get(0);
            UserInfo userInfo = sysUserService.getUserInfo(employee.getId(), false);
            String  areaId = userInfo.getAreaId();
            employee.setAreaId(areaId);
            return employee;
        }
        return null;
    }

    @Override
    public Page<EmpSelectAllDTO> selectAll(String query, Integer p, Integer s) {
        PageHelper.startPage(p,s);
        List<EmpSelectAllDTO> list = sysEmployeeMapper.selectAll(query,LoginManager.getCurrentOrgId());
        return (Page)list;
    }
}
