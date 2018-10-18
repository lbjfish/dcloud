/**
 * create by jianglingfeng
 * @date 2018-10
 */
package com.sida.dcloud.operation.dao;

import com.sida.dcloud.operation.dto.CommonUserOperation;
import com.sida.dcloud.operation.po.CommonUser;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface CommonUserMapper extends IMybatisDao<CommonUser> {
    Map<String, String> selectByPrimaryKeyToAuth(@Param("id") String id);
    int saveOrUpdateDto(@Param("dto") CommonUserOperation dto);
    int updateUserInfo(@Param("dto")CommonUserOperation dto);
}