package com.sida.dcloud.system.dao;

import com.sida.dcloud.auth.po.SysEmployee;
import com.sida.dcloud.auth.po.SysUser;
import com.sida.dcloud.auth.vo.SysUserDTO;
import com.sida.dcloud.auth.vo.SysUserVo;
import com.sida.dcloud.auth.vo.UserInfo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface SysUserMapper extends IMybatisDao<SysUser> {
    SysUser selectUserByName(String username);
    List<SysUser> selectAll();

    /**
     * 获取用户拓展信息列表（包含角色信息：角色id、角色name）
     * @param condition
     * @return
     */
    List<SysUserVo> selectVoList(SysUser condition);

    /**
     * 建立用户-角色关联关系
     * @param userId
     * @param roleId
     * @return
     */
    int addUserRoleRela(@Param("userId") String userId, @Param("roleId") String roleId);

    /**
     * 删除员工关联的角色id
     * @param userId
     */
    int deleteByUserId(@Param("userId") String userId);

    /**
     * 根据条件获取用户列表
     * @param roleCode
     * @param organizationId
     * @return
     */
    List<SysUserVo> findUsersByCondition(@Param("roleCode") String roleCode, @Param("organizationId") String organizationId);

    /**
     * 根据条件获取用户列表
     * @param roleCode
     * @param schoolId
     * @return
     */
    List<SysUserVo> findUsersByRoleCodeAndSchoolId(@Param("roleCode") String roleCode, @Param("schoolId") String schoolId);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    UserInfo getUserInfo(@Param("userId") String userId);

    /**
     * 查找驾校名称在nameList中的所有员工
     * @param orgId
     * @param nameList
     * @return
     */
    List<SysUser> findUsersByNames(@Param("orgId")String orgId, @Param("nameList")List<String> nameList);

    /**
     * 检查用户表，若存在则返回对象
     * @param idType
     * @param idNum
     * @return
     */
    List<SysUser> findUserByIdTypeAndNum(@Param(value = "idType") String idType, @Param(value = "idNum") String idNum);

    /**
     * 若类型为0-身份证，检查员工表，若存在则组装user，并返回对象
     * @param idType
     * @param idNum
     * @return
     */
    List<SysUser> findEmpByIdTypeAndNum(@Param(value = "idType") String idType, @Param(value = "idNum") String idNum);

    /**
     * 校验账号唯一
     * @param mobile
     * @return
     */
    Integer checkUserAccount(@Param(value = "mobile") String mobile);

    /**
     * 根据ids获取用户集合
     * @param ids
     * @return
     */
    List<SysUser> getUserMapByIds(@Param(value = "ids") List<String> ids);

    /**
     * 更新默认创建用户
     * @param user
     */
    void updateDefaultCreatedUser(SysUser user);

    /**
     * 新增默认创建用户
     * @param userList
     */
    void batchInsert(@Param(value = "userList") List<SysUser> userList);

    /**
     * 校验账号
     * @param accountList
     * @return
     */
    List<SysUser> checkAccount(@Param(value = "accountList") List<String> accountList);

    /**
     * 检验身份证号
     * @param idNumList
     * @return
     */
    List<SysUser> checkIdNum(@Param(value = "idNumList")List<String> idNumList);

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
    List<SysUser> selectByIdTypeAndNumber(@Param(value = "idType") String idType,@Param(value = "idNum") String idNum);

    /**
     * 获取历史学员，限制最多30条
     * @param query
     * @param orgId
     * @return
     */
    List<SysUser> findHistoryStudent(@Param(value = "query") String query, @Param(value = "orgId") String orgId, @Param(value = "roleCode")String roleCode);

    /**
     * 全量插入员工对应的用户数据
     */
    void insertWithEmployee();

    /**
     * 根据ids获取对象集合
     * @param ids
     * @return
     */
    List<SysUser> findByIds(@Param(value = "ids") List<String> ids);

    /**
     * 由员工增量插入用户
     * @param list
     */
    void insertWithEmployees(List<SysEmployee> list);

    /**
     * 批量根据员工信息更新 用户信息
     * @param list
     */
    void updateWithEmployees(List<SysEmployee> list);

    /**
     * 根据自定义条件获取用户列表
     * @param po
     * @return
     */
    List<SysUser> findUsersByCustomerCondition(SysUser po);

    /**
     * 物理删除所有
     */
    void removeAll();

    /**
     * 根据accountList获取对象集合
     * @param accountList
     * @return
     */
    List<SysUser> findAccountUserMap(@Param(value = "accountList") List<String> accountList);

    /**
     * 获取本年度学员数量
     * @param roleCode
     * @param orgId
     * @param beginTime
     * @param endTime
     * @return
     */
    long findNumberByRoleCodeAndTime(@Param(value = "roleCode") String roleCode,
                                     @Param(value = "orgId") String orgId,
                                     @Param(value = "beginTime") Date beginTime,
                                     @Param(value = "endTime") Date endTime);

    List<SysUser> findRemoteUserByAccounts(@Param(value = "accounts") List<String> accounts);

    /**
     * 获取远程教育平台列表
     * @param param
     * @return
     */
    List<SysUser> findRemoteUserList(SysUserDTO param);

    /**
     * 批量根据身份证号码获取用户
     * @param idNums
     * @return
     */
    List<SysUser> findUserByIdNums(@Param(value = "idNums") List<String> idNums);

    /**
     * 远程教育查询用户总数
     */
    int selectUserCount();

    int insertDto(@Param("map") Map<String, String> map);
    int updateMobile(@Param("map") Map<String, String> map);
    int updateUserInfo(@Param("map") Map<String, String> map);
    int updateUserPassword(@Param("map") Map<String, String> map);
}