package com.sida.dcloud.system.service;

import com.sida.dcloud.auth.po.SysEmployee;
import com.sida.dcloud.auth.po.SysUser;
import com.sida.dcloud.auth.vo.*;
import com.sida.xiruo.xframework.service.IBaseService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SysUserService extends IBaseService<SysUser> {
    SysUser selectUserByName(String name);

    /**
     * 条件查询用户列表
     * @param param
     * @return
     */
    Page<SysUserVo> findPageList(SysUser param);

    /**
     * 新增或编辑用户，并建立 用户-角色 关联关系
     * @param param
     */
    int saveOrUpdateUser(SysUserVo param);

    /**
     * 根据条件获取用户列表
     * @param param
     * @return
     */
    List<SysUserVo> findUsersByCondition(UserConditionDTO param);

    /**
     * 为新增员工默认创建用户
     * @param sysEmployee
     * @return
     */
    int insertUserWithEmployee(SysEmployee sysEmployee);

    /**
     * 登录获取用户信息
     * @return
     */
    UserInfo getUserInfo();


    /**
     * 获取登录用户信息：不包含前端资源信息
     * @param userId
     * @param resFlag
     * @return
     */
    UserInfo getUserInfo(String userId, Boolean resFlag);

    /**
     * 根据员工和角色创建用户
     * @param sysEmployee
     * @param roleCode
     * @return
     */
    int insertUserWithEmployeeAndRoleCode(SysEmployee sysEmployee, String roleCode);

    /**
     * 查找驾校名称在nameList中的所有员工
     * @param orgId 驾校ID
     * @param nameList 用户名列表
     * @return
     */
    List<SysUser> findUsersByNames(String orgId, List<String> nameList);

    /**
     * 从营销模块创建订单入口进入，触发获取|创建用户，并返回用户id
     * @param userDTO
     * @return
     */
    OrderUserDTO createUserByOrder(OrderUserDTO userDTO);

    /**
     * 同步数据时为符合条件的员工创建用户
     * @param employeeList
     */
    void insertUserWithEmployees(List<SysEmployee> employeeList);

    /**
     * 根据ids获取<id，SysUser>集合
     * @param ids
     * @return
     */
    Map<String, SysUser> getUserMapByIds(List<String> ids);

    /**
     * 获取名称
     * @param condition
     * @return
     */
    List<SysUser> selectNamesByCondition(SysUser condition);

    /**
     * 获取用户
     * @param idType
     * @param idNum
     * @return
     */
    SysUser selectByIdTypeAndNumber(String idType, String idNum);

    /**
     * 获取历史学员，限制最多30条
     * @param query
     * @param orgId
     * @return
     */
    List<Map<String,Object>> findHistoryStudent(String query, String orgId);

    /**
     * 批量插入
     * @param userList
     */
    void batchInsert(List<SysUser> userList);

    /**
     * 全量插入员工对应的用户数据
     */
    void insertWithEmployee();

    /**
     * 获取对象map集合
     * @param poIdList
     * @return
     */
    Map<String,SysUser> findMapByIds(List<String> poIdList);

    /**
     * 增量插入数据
     * @param list
     */
    void insertWithEmployees(List<SysEmployee> list);

    /**
     * 增量更新数据
     * @param list
     */
    void updateWithEmployees(List<SysEmployee> list);

    /**
     * 校验用户是否符合新增条件
     * @param account
     * @param idType
     * @param idNum
     * @return
     */
    boolean checkAccountAndIdNumber(String account, String idType, String idNum);

    /**
     * 物理删除所有
     */
    void removeAll();

    /**
     * 修改密码
     * @param userId
     * @param oldPassword
     * @param newPassword
     */
    void changePassword(String userId, String oldPassword, String newPassword);

    /**
     *
     * @param userId
     * @return
     */
    AppUserInfoDTO getUserInfoForApp(String userId);

    /**
     * 根据账号列表获取已存在用户
     * @param accountList
     * @return
     */
    Map<String,SysUser> findExistUser(List<String> accountList);

    /**
     * 获取所有页面资源
     * @return
     */
    Map<String, List<SysResourceVo>> findAllResMap();

    /**
     * 根据账号获取用户
     * @param account
     * @param orgId
     * @return
     */
    SysUser findByAccount(String account, String orgId);

    /**
     * 根据账号获取用户
     * @param account
     * @return
     */
    SysUser findByAccount(String account);

    /**
     * 第三方注册接口-带短信验证码
     * @param param
     */
    String remoteRegister(SysUserDTO param);

    /**
     * 注册用户审核
     * @param ids
     * @param status
     */
    void userApproval(String ids, String status);

    /**
     * 获取远程教育系统用户
     * @param account
     * @return
     */
    SysUser findRemoteUserOne(String account);

    /**
     * 获取远程教育系统用户
     * @param accounts
     * @return
     */
    Map<String,SysUser> findRemoteUsers(List<String> accounts);

    /**
     * 远程教育用户列表
     * @param param
     * @return
     */
    Page<SysUser> remoteUserList(SysUserDTO param);

    /**
     * 新增用户（可批量）
     * @param dtos
     */
    Map<String, String> addRemoteUser(List<SysUserDTO> dtos);

    /**
     * 远程教育查询用户总数
     */
    int selectUserCount();

    public void createUserByOrderList(List<SysUser> listUser);
}