package com.sida.dcloud.operation.common;

public interface OperationConstants {
    String DIC_LOGIN_FROM_WECHAT = "1";
    String DIC_LOGIN_FROM_ALIPAY = "2";
    String DIC_LOGIN_FROM_QQ = "3";
    String DIC_LOGIN_FROM_WEIBO = "4";
    String DIC_LOGIN_FROM_FACEBOOK = "5";
    String DIC_LOGIN_FROM_TWITTER = "6";

    String SMS_TEMPLATE_VERIFYCODE = "template_1";
    //以下字典枚举定义
    enum DIC_GROUP_CODE {
        LOGIN_FROM

    }

    String REDIS_KEY_OPERATION_TAG_MATCHER = "REDIS_KEY_OPERATION_TAG_MATCHER";
    String REDIS_KEY_OPERATION_TAG_INFO = "REDIS_KEY_OPERATION_TAG_INFO";
    String REDIS_KEY_OPERATION_COMPANY_INFO = "REDIS_KEY_OPERATION_COMPANY_INFO";
    String REDIS_KEY_OPERATION_COMPANY_REL_TAG = "REDIS_KEY_OPERATION_COMPANY_REL_TAG";
}