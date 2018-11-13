package com.sida.dcloud.system.common;

import com.sida.xiruo.util.constant.CommonConstants;

public class SysConstants {

    /**
     * 令牌名称
     */
    public static final String TOKEN="token";

    /**
     * 系统环境标识
     */
    public static final String ENV_DEV = CommonConstants.ENV_DEV;
    public static final String ENV_LOCAL = CommonConstants.ENV_LOCAL;
    public static final String ENV_PROD = CommonConstants.ENV_PROD;
    public static final String ENV_TEST = CommonConstants.ENV_TEST;
    public static final String ENV_UAT = CommonConstants.ENV_UAT;

    /**
     * 请求类型：用户注册
     */
    public static final int REQ_TYPE_REGISTER = 1;

    /**
     * 请求类型：找回密码
     */
    public static final int REQ_TYPE_FIND_PASSWORD = 2;

    /**
     * 用户类型：学员
     */
    public static final int USER_TYPE_STUDENT = 2;


}
