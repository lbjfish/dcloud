package com.sida.dcloud.system.controller;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.po.SysEmployee;
import com.sida.dcloud.auth.vo.EmployeeAddDTO;
import com.sida.dcloud.auth.vo.EmployeeConditionDTO;
import com.sida.dcloud.auth.vo.EmployeeFilterDTO;
import com.sida.dcloud.system.service.SysEmployeeService;
import com.sida.dcloud.system.vo.SysEmpStoreQueryVo;
import com.sida.xiruo.common.util.ErrorCodeEnums;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.util.BlankUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sysEmployee")
@Api(description = "员工信息api")
public class SysEmployeeController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysEmployeeController.class);

    @Autowired
    private SysEmployeeService sysEmployeeService;

    /**
     * 获取员工列表
     * @author xieguopei
     * @date 2017/09/04
     * @param employeeFilterDTO
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取员工列表")
    public Object getEmployeeList(@RequestBody @ApiParam("JSON参数") EmployeeFilterDTO employeeFilterDTO) {

        Object object = sysEmployeeService.getEmployeeList(employeeFilterDTO);

        return toResult(object);
    }

    /**
     * 新增或更新员工信息
     * @author xieguopei
     * @date 2017/09/04
     * @param employeeAddDTO
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "新增或更新员工信息")
    public Object saveOrUpdate(@RequestBody @ApiParam("JSON参数") EmployeeAddDTO employeeAddDTO) {
        Boolean sign;

        try {
            sign = sysEmployeeService.saveEmployeeMes(employeeAddDTO);

            if(!sign) {
                return toResult(null, ErrorCodeEnums.INSERT.getMessage(),false);
            } else {
                return toResult();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return toResult(null, ErrorCodeEnums.INSERT.getMessage(),false);
        }
    }

    /**
     * 根据员工id查询员工信息
     * @author xieguopei
     * @date 2017/09/05
     * @param id
     * @return
     */
    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "新增或更新员工信息")
    public Object getEmployeeById(@PathVariable("id") @ApiParam("员工id") String id) {
        Object result = sysEmployeeService.getEmployeeMesById(id);

        return toResult(result);
    }

    /**
     * 根据片区id、名称、手机号、姓名获取员工列表
     * @author xieguopei
     * @date 2017/09/04
     * @param employeeFilterDTO
     * @return
     */
    @RequestMapping(value = "/getEmployeeListByArea", method = RequestMethod.POST)
    @ApiOperation(value = "获取员工列表提供给教务教练选择员工使用")
    public Object getEmployeeListByArea(@RequestBody @ApiParam("JSON参数") EmployeeFilterDTO employeeFilterDTO) {

        if(employeeFilterDTO.getS()==null||employeeFilterDTO.getS()==0){
            employeeFilterDTO.setS(10);
        }
        Object object = sysEmployeeService.getEmployeeByArea(employeeFilterDTO);

        return toResult(object);
    }
    /**
     * 根据片区id、名称、手机号、姓名获取员工详情
     * @author xieguopei
     * @date 2017/09/04
     * @param employeeFilterDTO
     * @return
     */
    @RequestMapping(value = "/getEmployeeDetailById", method = RequestMethod.POST)
    @ApiOperation(value = "获取员工列表")
    public Object getEmployeeDetailById(@RequestBody @ApiParam("JSON参数") EmployeeFilterDTO employeeFilterDTO) {

        Object object = null;
        try {
            object = sysEmployeeService.getEmployeeDetailById(employeeFilterDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return toResult(null);
        }
        return toResult(object);
    }

    @RequestMapping(value = "/areaEmps", method = RequestMethod.GET)
    @ApiOperation(value = "获取片区员工列表:车辆调动申请新增表单使用，不分页")
    public Object areaEmps(@RequestParam(value = "areaId") @ApiParam("片区id") String areaId,
                           @RequestParam(required = false,value = "query") @ApiParam("员工姓名") String query){
        if (BlankUtil.isNotEmpty(areaId)){
            Object object = sysEmployeeService.findByAreaId(areaId,query);
            return toResult(object);
        }
        return toResult();
    }
    @RequestMapping(value = "/getSysEmployeeById", method = RequestMethod.GET)
    @ApiOperation(value = "获取员工详情")
    public Object getSysEmployeeById(@RequestParam(value = "empId")String empId){
        try {
            if (BlankUtil.isNotEmpty(empId)) {
                Object object = sysEmployeeService.getSysEmployeeById(empId);
                return toResult(object);
            }
        }catch (Exception ex){
            return toResult();
        }
        return toResult();
    }

    @PostMapping(value = "/findEmployeesByIds")
    @ApiOperation(value = "批量获取员工信息")
    public Object findEmployeesByIds(@RequestBody @ApiParam("JSON参数") List<String> ids){
        Map<String,SysEmployee> map = new HashMap<>();
        if (BlankUtil.isNotEmpty(ids)){
            SysEmployee condition = new SysEmployee();
            condition.setIds(ids);
            condition.setOrderField(SecConstant.CREATED_AT);
            condition.setOrderString(SecConstant.DESC);
            List<SysEmployee> poList = sysEmployeeService.selectByCondition(condition);
            if (BlankUtil.isNotEmpty(poList)){
                for (SysEmployee po : poList){
                    map.put(po.getId(),po);
                }
            }
        }
        return toResult(map);
    }

    @PostMapping(value = "/findEmployeeNamesByIds")
    @ApiOperation(value = "批量获取员工信息")
    public Object findEmployeeNamesByIds(@RequestBody @ApiParam("JSON参数") List<String> ids){
        Map<String,String> map = new HashMap<>();
        if (BlankUtil.isNotEmpty(ids)){
            SysEmployee condition = new SysEmployee();
            condition.setIds(ids);
            condition.setOrderField(SecConstant.CREATED_AT);
            condition.setOrderString(SecConstant.DESC);
            List<SysEmployee> poList = sysEmployeeService.selectNameByCondition(condition);
            if (BlankUtil.isNotEmpty(poList)){
                for (SysEmployee po : poList){
                    map.put(po.getId(),po.getName());
                }
            }
        }
        return toResult(map);
    }

    @PostMapping(value = "/findEmployees")
    @ApiOperation(value = "获取员工候选值，排除离职，根据员工id获取候选列表，限制最多30条！")
    public Object findEmployees(@RequestBody @ApiParam("JSON参数") SysEmployee param){
        Object object = sysEmployeeService.findEmployees(param.getQuery());
        return toResult(object);
    }

    @PostMapping(value = "/findEmpsByPositionAndOrgId")
    @ApiOperation(value = "根据组织id和岗位编码获取人员信息：过滤离职员工")
    public Object findEmpsByPositionAndOrgId(@RequestBody @ApiParam("JSON参数") EmployeeConditionDTO param){
        Object object = sysEmployeeService.findEmpsByPositionAndOrgId(param);
        return toResult(object);
    }

    @GetMapping(value = "/selectEmployeeName")
    @ApiOperation(value = "获取员工名称")
    public Object selectEmployeeName(@RequestParam @ApiParam("JSON参数") String id){
        SysEmployee emp = sysEmployeeService.selectByPrimaryKey(id);
        if (BlankUtil.isNotEmpty(emp) && BlankUtil.isNotEmpty(emp)){
            return toResult(emp.getName());
        }
        return toResult();
    }

    @GetMapping(value = "/getEmployeeByIdNum")
    @ApiOperation(value = "根据身份证号码获取员工信息")
    public List<SysEmployee> getEmployeeByIdNum(@RequestParam("idNum") String idNum){
        SysEmployee condition = new SysEmployee();
        condition.setDeleteFlag(false);
        condition.setIdcardNumber(idNum);
        List<SysEmployee> list = sysEmployeeService.selectByCondition(condition);
        return list;
    }



    @GetMapping(value = "/getEmpsByStoreAndPositionCode")
    @ApiOperation(value = "根据门店和岗位编码获取人员")
    public Object getEmpsByStoreAndPositionCode(@RequestParam(value = "storeId") @ApiParam("门店ID") String storeId,@RequestParam(value = "postionCode") @ApiParam("岗位编码") String postionCode){
        List<SysEmployee> emps = sysEmployeeService.getEmpsByStoreAndPositionCode(postionCode,storeId);
        if (BlankUtil.isNotEmpty(emps)){
            return toResult(emps);
        }
        return toResult();
    }

    @GetMapping(value = "/countEmpsByStoreAndPositionCode")
    @ApiOperation(value = "根据岗位编码和门店获取人数")
    public Object countEmpsByStoreAndPositionCode(@RequestParam(value = "storeId") @ApiParam("门店ID") String storeId,@RequestParam(value = "postionCode") @ApiParam("岗位编码") String postionCode){
        int count= sysEmployeeService.countEmpsByStoreAndPositionCode(postionCode,storeId);
        return toResult(count);
    }


    @PostMapping(value = "/getEmployeeListByStoreIdAndPositionCode")
    @ApiOperation(value = "根据岗位编码等条件和门店获取人员列表")
    public Object getEmployeeListByStoreIdAndPositionCode(@RequestBody SysEmpStoreQueryVo sysEmpStoreQueryVo){
        return toResult(this.sysEmployeeService.getEmployeeListByStoreIdAndPositionCode(sysEmpStoreQueryVo));
    }
    /**
     * 更新员工类型
     * @param empId
     * @param empType
     */
    @GetMapping(value = "/updateEmpType")
    @ApiOperation(value = "更新员工类型")
    public Object updateEmpType(@RequestParam(value = "empId") String empId,@RequestParam(value = "empType") String empType){
        this.sysEmployeeService.updateEmpType(empId,empType);
        return toResult();
    }

    @PostMapping(value = "/getEmpPositionInfoByIds")
    @ApiOperation(value = "根据ids批量获取员工岗位信息")
    public Object getEmpPositionInfoByIds(@RequestBody List<String> ids){
        return toResult(sysEmployeeService.getEmpPositionInfoByIds(ids));
    }
    @GetMapping(value = "/getEmployeeByThirdId")
    @ApiOperation(value = "根据员工第三方ID获取员工信息")
    public SysEmployee getEmployeeByThirdId(@RequestParam(value = "thirdId")String thirdId){
        return sysEmployeeService.getEmployeeByThirdId(thirdId);
    }

    @GetMapping(value = "/selectAll")
    @ApiOperation(value = "获取登录人所在驾校所有员工")
    public Object selectAll(@RequestParam(required = false) @ApiParam("姓名|手机号码")String query,
                                 @RequestParam @ApiParam("页码")Integer p,
                                 @RequestParam @ApiParam("每页条数")Integer s){
        if (BlankUtil.isEmpty(p)){
            p = 0;
        }
        if (BlankUtil.isEmpty(s)){
            s = 10;
        }
        return toResult(sysEmployeeService.selectAll(query,p,s));
    }

}