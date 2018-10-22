package com.sida.dcloud.operation.service;

import com.sida.dcloud.operation.dto.CommonUserOperation;
import com.sida.dcloud.operation.po.SysUserOperation;
import com.sida.xiruo.xframework.service.IBaseService;

public interface SysUserOperationService extends IBaseService<SysUserOperation> {
    String generateMobileVerifyCode(String mobileType, String mobile);
    CommonUserOperation verifyBindStatus(CommonUserOperation dto);
    CommonUserOperation findThirdPartAccount(CommonUserOperation dto);
    int bindMobile(CommonUserOperation dto);
    int bindThirdPartAccount(CommonUserOperation dto);
    int unbindThirdPartAccount(CommonUserOperation dto);
    int bindNewMobile(CommonUserOperation dto);
    int updateUserInfo(CommonUserOperation dto);
    int updateUserPassword(CommonUserOperation dto);

    int testDistributeTransaction(String id, String remark);
}