package com.sida.dcloud.operation.service;

import com.sida.dcloud.operation.dto.CommonUserOperation;
import com.sida.dcloud.operation.po.CommonUser;
import com.sida.xiruo.xframework.service.IBaseService;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface CommonUserService extends IBaseService<CommonUser> {
    Map<String, String> selectByPrimaryKeyToAuth(String id);
    int saveOrUpdateDto(CommonUserOperation dto);
    int updateUserInfo(CommonUserOperation dto);
}