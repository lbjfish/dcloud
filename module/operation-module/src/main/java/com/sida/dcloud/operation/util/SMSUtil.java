package com.sida.dcloud.operation.util;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 短信平台类
 * created by
 */
@Service
public final class SMSUtil {

    private static final Logger logger = LoggerFactory.getLogger(SMSUtil.class);

    @Value("${yuntongxun.appId}")
    private String App_ID;
    @Value("${yuntongxun.server.ip}")
    private String SERVER_IP;
    @Value("${yuntongxun.server.port}")
    private String SERVER_PORT;
    @Value("${yuntongxun.account.sid}")
    private String ACCOUNT_SID;
    @Value("${yuntongxun.account.token}")
    private String ACCOUNT_TOKEN;

    @Value("${environment}")
    private String environment;



    /**
     * 通用接口
     * @param phoneNum
     * @param templateId
     * @param datas
     * @return
     */
    public void SMSSendMessage(String phoneNum, String templateId, String[] datas){
//        logger.debug("SMSSendMessage authcodeEnv: " + authcodeEnv + " |phoneNum:"+phoneNum + "|datas:"+Arrays.toString(datas));

        //TODO:wuzhenwei 提交代码记得控制这个推送条件，最好一直置false不发送验证码
//        if(environment.equals(SysConstants.ENV_PRO)){ //生产环境才发送验证码
            SMSSendMessageByYunTongXun(phoneNum, templateId, datas);
//        }
        System.out.println("==============手机短信验证码：手机号码【"+phoneNum+"】,短信验证码【"+datas[0]+"】");
    }


    /**
     * 联容云通讯
     *
     * @param phoneNum
     *            手机号码，多个的时候中间用逗号隔开 每批发送的手机号数量不得超过100个  http://www.yuntongxun.com/doc/rest/sms/3_2_2_2.html
     * @param templateId
     *            模版id，没有模版则填写1
     * @param datas
     *            需要替换的内容
     */
    public void SMSSendMessageByYunTongXun(String phoneNum, String templateId,String[] datas) {

        if (StringUtils.isEmpty(templateId)) {
            templateId = "1"; // 当模版为空的时候则默认填写1
        }

        HashMap<String, Object> result = null;
//        CCPRestSDK restAPI = new CCPRestSDK();
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        restAPI.init(SERVER_IP, SERVER_PORT); // 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
        restAPI.setAccount(ACCOUNT_SID, ACCOUNT_TOKEN); // 初始化主帐号和主帐号TOKEN
        restAPI.setAppId(App_ID); // 初始化应用ID
        result = restAPI.sendTemplateSMS(phoneNum, templateId, datas);
    }

    public void SMSSendMessageByHuaweiCloud(String phoneNum, String templateId,String[] datas) {
        if (StringUtils.isEmpty(templateId)) {
            templateId = "1"; // 当模版为空的时候则默认填写1
        }
    }
}
