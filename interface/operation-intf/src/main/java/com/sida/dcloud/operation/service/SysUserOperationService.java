package com.sida.dcloud.operation.service;

import com.sida.dcloud.operation.dto.CommonUserOperation;
import com.sida.dcloud.operation.po.SysUserOperation;
import com.sida.xiruo.xframework.service.IBaseService;

public interface SysUserOperationService extends IBaseService<SysUserOperation> {
    String generateMobileVerifyCode(String mobile);
    CommonUserOperation verifyBindStatus(String loginFrom, String account);
    int bindMobile(CommonUserOperation dto);
}