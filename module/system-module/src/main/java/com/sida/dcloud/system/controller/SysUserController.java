package com.sida.dcloud.system.controller;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.po.SysEmployee;
import com.sida.dcloud.auth.po.SysUser;
import com.sida.dcloud.auth.vo.*;
import com.sida.dcloud.system.service.SysEmployeeService;
import com.sida.dcloud.system.service.SysUserService;
import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.common.util.MD5Util;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.exception.ServiceException;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sysUser")
@Api(description = "用户管理api")
public class SysUserController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysEmployeeService sysEmployeeService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表")
    public Object list(@RequestBody @ApiParam("JSON参数") SysUser param){
        Page<SysUserVo> userPage = sysUserService.findPageList(param);
        if (userPage!=null && userPage.getResult()!=null){
            for (SysUserVo vo : userPage.getResult()){
                vo.setPassword(SecConstant.RETURN_PASSWORD);
            }
        }
        return toResult(userPage);
    }

    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "新增或更新用户")
    public Object saveOrUpdate(@RequestBody @ApiParam("JSON参数") SysUserVo param) {
        if (StringUtils.isBlank(param.getMobile())){
            param.setMobile(param.getAccount());
        }
        if (StringUtils.isNotBlank(param.getPassword())){
            if (!SecConstant.RETURN_PASSWORD.equals(param.getPassword())){
                //为用户密码进行MD5加密
                param.setPassword(MD5Util.MD5PWD(param.getPassword()));
            }else {
                param.setPassword(null);
            }
        }
        if (BlankUtil.isEmpty(param.getId())){
            if (BlankUtil.isEmpty(param.getLocked())){
                param.setLocked(false);
            }
            if (BlankUtil.isEmpty(param.getValidDate())){
                param.setValidDate(new Date());
            }
        }
        sysUserService.saveOrUpdateUser(param);
        return toResult();
    }

    @RequestMapping(value = "/enableUsers", method = RequestMethod.POST)
    @ApiOperation(value = "批量启用用户")
    public Object enableRoles(@RequestBody @ApiParam("JSON参数") List<String> ids) {
        SysUser po = new SysUser();
        po.setStatus(false);
        sysUserService.updateByIdsSelective(po,ids);
        return toResult();
    }

    @RequestMapping(value = "/disableUsers", method = RequestMethod.POST)
    @ApiOperation(value = "批量禁用用户")
    public Object disableRoles(@RequestBody @ApiParam("JSON参数") List<String> ids) {
        SysUser po = new SysUser();
        po.setStatus(true);
        sysUserService.updateByIdsSelective(po,ids);
        return toResult();
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "获取登录用户信息，包含前端资源信息")
    public Object getUserInfo() {
        Object object = sysUserService.getUserInfo();
        return toResult(object);
    }

    @RequestMapping(value = "/getLoginUser", method = RequestMethod.POST)
    @ApiOperation(value = "获取登录用户信息，不含前端资源信息,userId为空或为空字符串则表示取登录用户")
    public Object getLoginUser(@RequestBody @ApiParam("JSON参数")String userId) {
        Object object = sysUserService.getUserInfo(userId,false);
        return toResult(object);
    }

    /**************************** 以下接口为微服务调用 **************************************************************************/

    @RequestMapping(value = "/findUsersByCondition", method = RequestMethod.POST)
    @ApiOperation(value = "微服务用：根据条件获取用户列表")
    public Object findUsersByCondition(@RequestBody @ApiParam("JSON参数") UserConditionDTO param) {
        List<SysUserVo> list = sysUserService.findUsersByCondition(param);
        list = CustomHandler(list,param);
        List<String> idList = Lists.newArrayList();
        if (list!=null && list.size() >0){
            for (SysUserVo vo : list){
                idList.add(vo.getId());
            }
        }else {
            throw new ServiceException("下一节点审批人不存在，请及时配置！");
//            throw new ServiceException(ErrorCodeEnums.WORKFLOW_ERROR_USER_NOT_FOUND.getCode(),ErrorCodeEnums.WORKFLOW_ERROR_USER_NOT_FOUND.getMessage().replace("::ROLE",param.getRoleCode()));
        }
        return toResult(idList);
    }

    private List<SysUserVo> CustomHandler(List<SysUserVo> list, UserConditionDTO param) {
        if (list==null || list.size()==0){
            //安全部经理若无，则取安全部副经理
            if (BlankUtil.isNotEmpty(param.getRoleCode()) && param.getRoleCode().equals("jl")){
                param.setRoleCode("fjl");
                param.setOrganizationId(null);
                list = sysUserService.findUsersByCondition(param);
            }
        }
        return list;
    }


    @RequestMapping(value = "/findUsersByNames", method = RequestMethod.POST)
    @ApiOperation(value = "微服务用：根据用户名称获取用户列表")
    public Object findUsersByNames(@RequestBody SysUser user) {
        if(BlankUtil.isEmpty(user.getIds())) {
            throw new ServiceException("ids不能为空");
        }
        List<SysUser> users = sysUserService.findUsersByNames(user.getOrgId(), user.getIds());
        return toResult(users);
    }


    @RequestMapping(value = "/findUsersByIds", method = RequestMethod.POST)
    @ApiOperation(value = "根据ids批量获取用户")
    public Object findUsersByIds(@RequestBody @ApiParam("JSON参数") List<String> ids) {
        Map<String,SysUser> users = new HashMap<>();
        SysUser condition = new SysUser();
        condition.setIds(ids);
        condition.setOrderField(SecConstant.CREATED_AT);
        condition.setOrderString(SecConstant.ASC);
        List<SysUser> userList = sysUserService.selectByCondition(condition);
        if (BlankUtil.isNotEmpty(userList)){
            for (SysUser user : userList){
                users.put(user.getId(),user);
            }
        }
        return toResult(users);
    }

    @RequestMapping(value = "/findUserNamesByIds", method = RequestMethod.POST)
    @ApiOperation(value = "根据ids批量获取用户姓名")
    public Object findUserNamesByIds(@RequestBody @ApiParam("JSON参数") List<String> ids) {
        Map<String,String> userNames = new HashMap<>();
        SysUser condition = new SysUser();
        condition.setIds(ids);
        condition.setOrderField(SecConstant.CREATED_AT);
        condition.setOrderString(SecConstant.ASC);
        List<SysUser> userList = sysUserService.selectNamesByCondition(condition);
        if (BlankUtil.isNotEmpty(userList)){
            for (SysUser user : userList){
                userNames.put(user.getId(),user.getName());
            }
        }
        return toResult(userNames);
    }

    @RequestMapping(value = "/insertUserWithEmployee", method = RequestMethod.POST)
    @ApiOperation(value = "由员工和角色编码，创建对应用户")
    public Object insertUserWithEmployee(@RequestBody @ApiParam("JSON参数") EmployeeDTO employeeDTO) {
        if (BlankUtil.isEmpty(employeeDTO.getEmpId())){
            if (BlankUtil.isNotEmpty(employeeDTO.getSysEmployee())){
                sysUserService.insertUserWithEmployeeAndRoleCode(employeeDTO.getSysEmployee(),employeeDTO.getRoleCode());
            }
        }else {
            SysEmployee emp = sysEmployeeService.selectByPrimaryKey(employeeDTO.getEmpId());
            if (BlankUtil.isNotEmpty(emp)){
                sysUserService.insertUserWithEmployeeAndRoleCode(emp,employeeDTO.getRoleCode());
            }
        }
        return toResult();
    }

    @RequestMapping(value = "/createUserByOrder", method = RequestMethod.POST)
    @ApiOperation(value = "从营销模块创建订单入口进入，触发获取|创建用户，并返回用户id")
    public Object createUserByOrder(@RequestBody @ApiParam("JSON参数") OrderUserDTO userDTO) {
        OrderUserDTO dto = null;
        if (BlankUtil.isNotEmpty(userDTO)){
            dto = sysUserService.createUserByOrder(userDTO);
        }else {
            throw new ServiceException("非法入参！");
        }
        return toResult(dto);
    }

    @RequestMapping(value = "/insertUserWithEmployees", method = RequestMethod.POST)
    @ApiOperation(value = "同步数据时为符合条件的员工创建用户")
    public Object insertUserWithEmployees(@RequestBody @ApiParam("JSON参数") List<SysEmployee> employeeList) {
        if (BlankUtil.isNotEmpty(employeeList)){
            sysUserService.insertUserWithEmployees(employeeList);
        }
        return toResult();
    }

    @RequestMapping(value = "/selectByIdTypeAndNumber", method = RequestMethod.POST)
    @ApiOperation(value = "根据证件类型和证件号码获取用户，至多返回一个，若无则返回异常，前端根据异常忽略")
    public Object selectByIdTypeAndNumber(@RequestBody @ApiParam("JSON参数") SysUser sysUser) {
        if (BlankUtil.isNotEmpty(sysUser) && BlankUtil.isNotEmpty(sysUser.getIdType()) && BlankUtil.isNotEmpty(sysUser.getIdNum())){
            SysUser user = sysUserService.selectByIdTypeAndNumber(sysUser.getIdType(),sysUser.getIdNum());
            if (BlankUtil.isNotEmpty(user)){
                return toResult(user);
            }
        }
        return toResult();
    }

    @RequestMapping(value = "/findHistoryStudent", method = RequestMethod.POST)
    @ApiOperation(value = "获取历史学员")
    public Object findHistoryStudent(@RequestBody @ApiParam("JSON参数") SysUser sysUser) {
        Object object = sysUserService.findHistoryStudent(sysUser.getQuery(), LoginManager.getCurrentOrgId());
        return toResult(object);
    }

    @RequestMapping(value = "/selectUserName", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户名称")
    public Object selectUserName(@RequestParam @ApiParam("JSON参数") String id) {
        SysUser user = sysUserService.selectByPrimaryKey(id);
        if (BlankUtil.isNotEmpty(user) && BlankUtil.isNotEmpty(user.getName())){
            return toResult(user.getName());
        }
        return toResult();
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.POST)
    @ApiOperation(value = "重置密码-无需校验原密码")
    public Object passwordReset(@RequestBody @ApiParam("JSON参数") PasswordResetDTO param) {
        sysUserService.changePassword(LoginManager.getCurrentUserId(),null,param.getPassword());
        return toResult();
    }

    @RequestMapping(value = "/password/modify", method = RequestMethod.POST)
    @ApiOperation(value = "修改密码-需要验证原密码")
    public Object passwordModify(@RequestBody @ApiParam("JSON参数") PasswordModifyDTO param) {
        sysUserService.changePassword(LoginManager.getCurrentUserId(),param.getOldPassword(),param.getNewPassword());
        return toResult();
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    @ApiOperation(value = "app端获取个人信息")
    public Object getUserInfoForApp() {
        //获取个人信息
        String userId = LoginManager.getCurrentUserId();
        Object object = sysUserService.getUserInfoForApp(userId);
        return toResult(object);
    }


    /*********************************** 远程教育相关接口 *********************************************/
    @RequestMapping(value = "/remoteRegister", method = RequestMethod.POST)
    @ApiOperation(value = "远程教育平台注册接口")
    public Object remoteRegister(@RequestBody @ApiParam("JSON参数") SysUserDTO param) {
        String id = sysUserService.remoteRegister(param);
        return toResult(id);
    }

    @RequestMapping(value = "/remoteUser/approval", method = RequestMethod.POST)
    @ApiOperation(value = "用户审核")
    public Object remoteUserApproval(@RequestBody @ApiParam("JSON参数") SysUserDTO param) {
        sysUserService.userApproval(param.getIds(),param.getAction());
        return toResult();
    }

    @RequestMapping(value = "/remoteUser/list", method = RequestMethod.POST)
    @ApiOperation(value = "用户审核")
    public Object remoteUserList(@RequestBody @ApiParam("JSON参数") SysUserDTO param) {
        Object object = sysUserService.remoteUserList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/remoteUser/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增用户（支持批量）")
    public Object remoteUserAdd(@RequestBody @ApiParam("JSON参数") List<SysUserDTO> list) {
        Map<String, String> map = sysUserService.addRemoteUser(list);
        return toResult(map);
    }

    @RequestMapping(value = "/remoteUser/selectUserCount", method = RequestMethod.POST)
    @ApiOperation(value = "查询总用户数")
    public Object selectUserCount() {
        int count = sysUserService.selectUserCount();
        return toResult(count);
    }
    @RequestMapping(value = "/selectByCondition", method = RequestMethod.POST)
    @ApiOperation(value = "根据条件查询用户")
    public Object selectByCondition(@RequestBody @ApiParam("JSON参数") SysUser sysUser) {
        List<SysUser> sysUsers = this.sysUserService.selectByCondition(sysUser);
        return toResult(sysUsers);
    }
    @RequestMapping(value = "/createUserByOrderList", method = RequestMethod.POST)
    @ApiOperation(value = "批量插入用户")
    public Object createUserByOrderList(@RequestBody List<SysUser> listUser){
        this.sysUserService.createUserByOrderList(listUser);
        return toResult();
    }



}