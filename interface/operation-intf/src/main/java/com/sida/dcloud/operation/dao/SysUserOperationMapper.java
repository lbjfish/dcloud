/**
 * create by jianglingfeng
 * @date 2018-10
 */
package com.sida.dcloud.operation.dao;

import com.sida.dcloud.operation.dto.CommonUserOperation;
import com.sida.dcloud.operation.po.SysUserOperation;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

public interface SysUserOperationMapper extends IMybatisDao<SysUserOperation> {
    CommonUserOperation verifyBindStatus(@Param("dto")CommonUserOperation dto);
    int saveOrUpdateDto(@Param("dto")CommonUserOperation dto);
    int updateMobile(@Param("dto")CommonUserOperation dto);
    int updateUserInfo(@Param("dto")CommonUserOperation dto);
    int updateUserPassword(@Param("dto")CommonUserOperation dto);
    int checkMultiCountByUnique(@Param("dto")CommonUserOperation dto);
}