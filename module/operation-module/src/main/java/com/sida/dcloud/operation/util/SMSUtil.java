package com.sida.dcloud.operation.util;

import com.sida.xiruo.util.constant.CommonConstants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 短信平台类
 * created by
 */
@Service
public final class SMSUtil {
    private static final Logger LOG = LoggerFactory.getLogger(SMSUtil.class);
    //无需修改,用于格式化鉴权头域,给"X-WSSE"参数赋值
    private static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
    //无需修改,用于格式化鉴权头域,给"Authorization"参数赋值
    private static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";

    public static final int TEMPLATE_ID_REG_LOGIN = 1;
    public static final int TEMPLATE_ID_MODIFY_PASSWORD = 2;
    public static final int TEMPLATE_ID_CHANGE_MOBILE = 3;

    @Value("${common.env}")
    private String env;

    @Value("${huawei.short_message.app.butong.app_key}")
    private String appKey;
    @Value("${huawei.short_message.app.butong.app_secret}")
    private String appSecret;
    @Value("${huawei.short_message.app.butong.app_in_url}")
    private String appInUrl;
    @Value("${huawei.short_message.app.butong.app_out_url}")
    private String appOutUrl;
    @Value("${huawei.short_message.sign}")
    private String sign;
    @Value("${huawei.short_message.template.reg_login}")
    private String regLoginTempId;
    @Value("${huawei.short_message.template.modify_password}")
    private String modifyPasswordTempId;
    @Value("${huawei.short_message.template.change_mobile}")
    private String changeMobileTempId;



    /**
     * 通用接口
     * @param phoneNum
     * @param templateId
     * @param datas
     * @return
     */
    public void SMSSendMessage(String phoneNum, int templateId, String[] datas) throws Exception {
//        logger.debug("SMSSendMessage authcodeEnv: " + authcodeEnv + " |phoneNum:"+phoneNum + "|datas:"+Arrays.toString(datas));

        if(CommonConstants.ENV_PROD.equals(env)) {//生产环境才发送验证码
            SMSSendMessageByHuaweiCloud(phoneNum, templateId, datas);
        }
        LOG.info("==============手机短信验证码：手机号码【"+phoneNum+"】,短信验证码【"+ StringUtils.join(datas, ",")+"】");
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
//    public void SMSSendMessageByYunTongXun(String phoneNum, String templateId,String[] datas) {
//
//        if (StringUtils.isEmpty(templateId)) {
//            templateId = "1"; // 当模版为空的时候则默认填写1
//        }
//
//        HashMap<String, Object> result = null;
////        CCPRestSDK restAPI = new CCPRestSDK();
//        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
//        restAPI.init(SERVER_IP, SERVER_PORT); // 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
//        restAPI.setAccount(ACCOUNT_SID, ACCOUNT_TOKEN); // 初始化主帐号和主帐号TOKEN
//        restAPI.setAppId(App_ID); // 初始化应用ID
//        result = restAPI.sendTemplateSMS(phoneNum, templateId, datas);
//    }

    /**
     * 手机号码，多个的时候中间用逗号隔开
     * @param phoneNum
     * @param tid
     * @param datas
     */
    public void SMSSendMessageByHuaweiCloud(String phoneNum, int tid, String[] datas) throws Exception {
        if (tid <= 0) {
            tid = 1; // 当模版为空的时候则默认填写1
        }
        String statusCallBack = "";
        StringBuilder builder = new StringBuilder("");
        if(datas.length > 0) {
            builder.append("[");
            Arrays.stream(datas).map(data -> String.format("\"%s\",", data)).forEach(builder::append);
            builder.deleteCharAt(builder.length() - 1).append("]");
        }
        String templateParas = String.format(builder.toString(), datas);
        String templateId = "";
        switch(tid) {
            case TEMPLATE_ID_MODIFY_PASSWORD:
                templateId = modifyPasswordTempId;
                break;
            case TEMPLATE_ID_CHANGE_MOBILE:
                templateId = changeMobileTempId;
                break;
            default:
            case TEMPLATE_ID_REG_LOGIN:
                templateId = regLoginTempId;
                break;
        }
//        LOG.info("templateId {}, params {}", templateId, builder.toString());
        //请求Body
        String body = buildRequestBody(sign, phoneNum, templateId, templateParas, statusCallBack);
        System.out.println("body is " + body);

        //请求Headers中的X-WSSE参数值
        String wsseHeader = buildWsseHeader(appKey, appSecret);
        System.out.println("wsse header is " + wsseHeader);

        //为防止因HTTPS证书认证失败造成API调用失败,需要先忽略证书信任问题
        CloseableHttpClient client = HttpClients.custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null,
                        (x509CertChain, authType) -> true).build())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();

        HttpResponse response = client.execute(RequestBuilder.create("POST")//请求方法POST
                .setUri(appInUrl)
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .addHeader(HttpHeaders.AUTHORIZATION, AUTH_HEADER_VALUE)
                .addHeader("X-WSSE", wsseHeader)
                .setEntity(new StringEntity(body)).build());

        LOG.info("response {}", response.toString());
        LOG.info("entity {}", EntityUtils.toString(response.getEntity()));
    }

    /**
     * 构造请求Body体
     * @param sender
     * @param receiver
     * @param templateId
     * @param templateParas
     * @param statusCallbackUrl
     * @return
     */
    static String buildRequestBody(String sender, String receiver, String templateId, String templateParas,
                                   String statusCallbackUrl) {

        List<NameValuePair> keyValues = new ArrayList<NameValuePair>();

        keyValues.add(new BasicNameValuePair("from", sender));
        keyValues.add(new BasicNameValuePair("to", receiver));
        keyValues.add(new BasicNameValuePair("templateId", templateId));
        keyValues.add(new BasicNameValuePair("templateParas", templateParas));
        keyValues.add(new BasicNameValuePair("statusCallback", statusCallbackUrl));

        //如果JDK版本是1.6,可使用:URLEncodedUtils.format(keyValues, Charset.forName("UTF-8"));
        return URLEncodedUtils.format(keyValues, StandardCharsets.UTF_8);
    }

    /**
     * 构造X-WSSE参数值
     * @param appKey
     * @param appSecret
     * @return
     */
    static String buildWsseHeader(String appKey, String appSecret) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String time = sdf.format(new Date());
        String nonce = UUID.randomUUID().toString().replace("-", "");

        byte[] passwordDigest = DigestUtils.sha256(nonce + time + appSecret);
        String hexDigest = Hex.encodeHexString(passwordDigest);
        String passwordDigestBase64Str = Base64.encodeBase64String(hexDigest.getBytes(Charset.forName("utf-8")));
        return String.format(WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
    }
}
