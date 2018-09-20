package com.sida.dcloud.system.service;

public interface AuthcodeService {

    /**
     * 发送短信（同时绑定验证码-手机号码关系）通用接口
     * @param mobile
     * @param templateId
     * @param datas
     * @param mobileType
     * @param validity
     */
    void sendMessage(String mobile,String templateId,String[] datas,String mobileType,Long validity);

    /**
     * 定时失效redis中验证码
     */
    void cleanAuthcodeRedis();

    /**
     * 《远程教育平台》获取短信验证码
     * @param mobile 手机号码
     * @param reqType 业务类型：1-注册；2-登录
     */
    void getRemoteAuthCode(String mobile, String reqType);

    /**
     * 验证码验证接口（一次校验接口：校验成功后会失效该验证码
     * @param code 验证码
     * @param mobile  手机号
     * @param reqType   业务类型
     */
    boolean isValid(String code, String mobile, String reqType);
}