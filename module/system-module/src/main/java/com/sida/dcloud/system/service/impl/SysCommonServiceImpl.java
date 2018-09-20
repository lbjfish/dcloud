package com.sida.dcloud.system.service.impl;

import com.sida.dcloud.auth.common.SecConstant;
import com.sida.dcloud.auth.common.SysEnums;
import com.sida.dcloud.auth.po.*;
import com.sida.dcloud.system.service.*;
import com.sida.xiruo.common.util.MD5Util;
import com.sida.xiruo.xframework.exception.ServiceException;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.PoDefaultValueGenerator;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysCommonServiceImpl implements SysCommonService {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;


    /**
     * 默认创建用户
     */
    private void createdDefaultUser(List<SysOrg> schools,Boolean isFirstTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            List<SysUser> userList = Lists.newArrayList();
            List<SysUserRole> urList = Lists.newArrayList();
            Map<String,String> accountMap = new HashMap<>();
            List<String> accountList = Lists.newArrayList();
            if (isFirstTime){
                //车厘子管理员用户
                SysUser clzAdmin = new SysUser("1", null, df.parse("2017-01-01"), df.parse("2017-01-01"), "1", "1", false, false, "chelizi", MD5Util.MD5PWD(SecConstant.DEFAULT_PASSWORD), "chelizi", null, null, null, df.parse("2017-01-01"), false, "", "系统默认创建用户，请勿改动！", false, "0", "0", "", null, null, null, null, null, "1");
                userList.add(clzAdmin);
                SysUserRole ur = new SysUserRole();
                ur.setUserId(clzAdmin.getId());
                ur.setRoleId("1");
                urList.add(ur);
                accountList.add(clzAdmin.getAccount());
                accountMap.put(clzAdmin.getAccount(),clzAdmin.getId());
            }
            //驾校管理员用户
            if (BlankUtil.isNotEmpty(schools)){
                for (SysOrg school : schools){
                    SysUser jxAdmin = null;
                    if (school.getDeleteFlag()){
                        jxAdmin = new SysUser(null,school.getId(),df.parse("2017-01-01"),df.parse("2017-01-01"),"1","1",school.getDisable(),school.getDeleteFlag(),school.getThirdPartyId().toString(),MD5Util.MD5PWD(SecConstant.DEFAULT_PASSWORD),school.getName()+"管理员", null, null, null, df.parse("2017-01-01"), false, "", "系统默认创建用户，请勿改动！", false, "0", "0", "", null, null, null, null, null, "1");
                        PoDefaultValueGenerator.putDefaultValueWithSource(jxAdmin);
                        userList.add(jxAdmin);
                    }else {
                        jxAdmin = new SysUser(null,school.getId(),df.parse("2017-01-01"),df.parse("2017-01-01"),"1","1",school.getDisable(),school.getDeleteFlag(),SysEnums.SchoolNameEnums.getCodeByName(school.getName()).equals("")?school.getThirdPartyId().toString():(SysEnums.SchoolNameEnums.getCodeByName(school.getName())+"Admin"),MD5Util.MD5PWD(SecConstant.DEFAULT_PASSWORD),SysEnums.SchoolNameEnums.getAbNameByName(school.getName()).equals("")?school.getName():(SysEnums.SchoolNameEnums.getAbNameByName(school.getName())+"管理员"), null, null, null, df.parse("2017-01-01"), false, "", "系统默认创建用户，请勿改动！", false, "0", "0", "", null, null, null, null, null, "1");
                        PoDefaultValueGenerator.putDefaultValueWithSource(jxAdmin);
                        userList.add(jxAdmin);
                    }
                    SysUserRole jxUR = new SysUserRole();
                    jxUR.setUserId(jxAdmin.getId());
                    jxUR.setRoleId("2");
                    urList.add(jxUR);
                    accountList.add(jxAdmin.getAccount());
                    accountMap.put(jxAdmin.getAccount(),jxAdmin.getId());
                }
            }
            //根据账户获取已存在列表
            List<SysUser> userListTemp = Lists.newArrayList();
            List<SysUserRole> urListTemp = Lists.newArrayList();
            Map<String,String> insertIdMap = new HashMap<>();
            if (BlankUtil.isNotEmpty(accountList)){
                Map<String,SysUser> existUserMap = sysUserService.findExistUser(accountList);
                //遍历新增用户，校验账号重复
                for (SysUser u : userList){
                    if (BlankUtil.isEmpty(existUserMap.get(u.getDeleteFlag()+u.getAccount()+u.getOrgId()))){
                        userListTemp.add(u);
                    }else {
                        insertIdMap.put(accountMap.get(u.getAccount()),accountMap.get(u.getAccount()));
                    }
                }

                //遍历关联关系，去除上述被过滤的id
                for (SysUserRole ur : urList){
                    if (BlankUtil.isEmpty(insertIdMap.get(ur.getUserId()))){
                        urListTemp.add(ur);
                    }
                }
            }
            //批量插入用户
            sysUserService.batchInsert(userListTemp);
            //批量插入关联关系
            sysUserRoleService.batchInsert(urListTemp);
        }catch (Exception e){
            throw new ServiceException("创建默认用户异常："+e.getMessage());
        }
    }

    /**
     * 创建默认角色
     */
    private void createdDefaultRole() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            List<SysRole> roleList = Lists.newArrayList();
            //车厘子管理员
            SysRole clzAdmin = new SysRole("1", null, df.parse("2017-01-01"), df.parse("2017-01-01"), "1", "1", false, false, "车厘子管理员", "CHELIZI_ADMIN", false, null, "系统默认创建角色，请勿改动！");
            //驾校管理员
            SysRole jxAdmin = new SysRole("2", null, df.parse("2017-01-01"), df.parse("2017-01-01"), "1", "1", false, false, "驾校管理员", "JX_ADMIN", false, null, "系统默认创建角色，请勿改动！");
            //默认角色
            SysRole defaultUser = new SysRole("3", null, df.parse("2017-01-01"), df.parse("2017-01-01"), "1", "1", false, false, "默认用户角色", "DEFAULT", false, null, "系统默认创建角色，请勿改动！");
            //学员角色
            SysRole student = new SysRole("4", null, df.parse("2017-01-01"), df.parse("2017-01-01"), "1", "1", false, false, "学员", "STUDENT", false, null, "系统默认创建角色，请勿改动！");

            roleList.add(clzAdmin);
            roleList.add(jxAdmin);
            roleList.add(defaultUser);
            roleList.add(student);
            sysRoleService.batchInsert(roleList);

        }catch (Exception e){
            throw new ServiceException("创建默认角色【车厘子管理员】或【驾校管理员】异常："+e.getMessage());
        }
    }

}