package com.sida.dcloud.activity.util.pay;

import com.alibaba.fastjson.JSONObject;
import com.sida.dcloud.activity.common.ActivityConstants;
import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.po.ActivityOrder;
import com.sida.dcloud.activity.po.CustomerActivitySignupNote;
import com.sida.dcloud.activity.po.CustomerPaymentTrack;
import com.sida.dcloud.activity.service.ActivityOrderService;
import com.sida.dcloud.activity.service.CustomerActivitySignupNoteService;
import com.sida.dcloud.activity.service.CustomerPaymentTrackService;
import com.sida.xiruo.common.components.RandomUtils;
import com.sida.xiruo.common.util.Xiruo;
import com.sida.xiruo.xframework.common.Contants;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.util.*;
import org.activiti.engine.ActivitiException;
import org.apache.catalina.manager.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 小程序支付
 * created by
 */
@Service
public class PayUtilWithXcx {
    private static final Logger LOG = LoggerFactory.getLogger(PayUtilWithXcx.class);
    private static final String UNIFIELDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    private static final String ORDERQUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
    private static final String UNIFIELDORDER_SIGN_PARAMS = "appid=%s&attach=%s&body=%s&mch_id=%s&nonce_str=%s&notify_url=%s&openid=%s&out_trade_no=%s&spbill_create_ip=%s&total_fee=%s&trade_type=%s&key=%s";
    private static final String UNIFIELDORDER_PARAMS = "<xml><appid>%s</appid><attach>%s</attach><body>%s</body><mch_id>%s</mch_id><nonce_str>%s</nonce_str><notify_url>%s</notify_url><openid>%s</openid><out_trade_no>%s</out_trade_no><spbill_create_ip>%s</spbill_create_ip><total_fee>%s</total_fee><trade_type>%s</trade_type><sign>%s</sign></xml>";
    private static final String JSCODE_TO_SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
    private static final String TRADE_TYPE = "JSAPI";
    private static final String ORDERQUERY_SIGN_PARAMS = "appid=%s&mch_id=%s&nonce_str=%s&%s=%s&key=%s";
    private static final String ORDERQUERY_PARAMS = "<xml><appid>%s</appid><mch_id>%s</mch_id><nonce_str>%s</nonce_str><%s>%s</%s><sign>%s</sign></xml>";

    @Value("${common.env}")
    private String env;
    @Value("${weixin.app.id}")
    private String appid;
    @Value("${weixin.app.secret}")
    private String appSecret;
    @Value("${weixin.pay.mch_id}")
    private String mchId;
    @Value("${weixin.pay.key}")
    private String key;
    @Value("${weixin.pay.secret}")
    private String secret;
    @Value("${weixin.pay.xcxCallback}")
    private String xcxCallback;

    @Autowired
    private CustomerPaymentTrackService customerPaymentTrackService;
    @Autowired
    private ActivityOrderService activityOrderService;

    @PostConstruct
    private void init() {
        //配置文件加密，此处解密
        key = DESUtils.getDecryptString(key);
        secret = DESUtils.getDecryptString(secret);
        appSecret = DESUtils.getDecryptString(appSecret);
    }

    private void testParams(Map<String, String> map) {
//        key = "butong2018butong2018butong2018bt";
//        secret = "butong.com2018";
//        mchId = "1516520231";
//        map.put("appid", "wxec488e381b105038");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Object startPay(Map<String, String> map) {
        testParams(map);
//        this.appid = map.get("appid");
        CustomerPaymentTrack track = customerPaymentTrackService.findOneByNoteId(map.get("id"));
        if(track == null || BlankUtil.isEmpty(track.getRemark())) {
            Optional.ofNullable(map.get("jsCode")).orElseThrow(() -> new ActivityException("jsCode不能空"));
            loadActivityOrder(map);
            getOpenidAndSessionKey(map);
            return submitPay(map);
        } else {
            track.setCommand("UPDATE");
            track.setPayTime(new Date());
            return submitPay(track);
        }
    }

    private void loadActivityOrder(Map<String, String> map) {
        ActivityOrder order = activityOrderService.findOneByNoteId(map.get("id"));
        Optional.ofNullable(order).orElseThrow(() -> new ActivitiException("没有找到订单，对应报名表 id = " + map.get("id")));
        map.put("orderNo", order.getOrderNo());
        map.put("body", order.getOrderName());
        map.put("totalFee", order.getGoodsAmount() + "");
        map.put("noteId", order.getNoteId());
        map.put("activityId", order.getActivityId());
    }

    private String getOpenidAndSessionKey(Map<String, String> map) {
        String openid = "";
        CloseableHttpResponse response = null;
        try {
            HttpGet get = null;
            HttpEntity entity = null;
            try {
                HttpClient httpClient = HttpClients.createDefault();
                LOG.info("app secret = {}", appSecret);
                get = new HttpGet(String.format(JSCODE_TO_SESSION_URL, appid, appSecret, map.get("jsCode")));
                response = (CloseableHttpResponse) httpClient.execute(get);
                // 检验返回码
                int statusCode = response.getStatusLine().getStatusCode();
                if(statusCode != HttpStatus.SC_OK){
                    throw new ActivityException("请求出错: " + statusCode);
                } else {
                    //获取结果实体
                    entity = response.getEntity();
                    if (entity != null) {
                        //按指定编码转换结果实体为String类型
                        String body = EntityUtils.toString(entity, Constants.CHARSET);
                        LOG.info("body = {}", body);
                        JSONObject json = (JSONObject)JSONObject.parse(body);
                        if(json.containsKey("errcode") && json.getInteger("errcode") != 0) {
                            LOG.error("errcode = {}", json.getInteger("errcode"));
                            throw new ActivityException(json.getString("errmsg"));
                        }
                        map.put("openid", json.getString("openid"));
                        map.put("sessionKey", json.getString("session_key"));
                        Optional.ofNullable(json.getString("unionid")).ifPresent(unionid -> map.put("unionid", unionid));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new ActivityException(e);
            } finally {
                if(entity != null) {
                    EntityUtils.consume(entity);
                }
                if(get != null){
                    get.releaseConnection();
                }
                releaseResponse(response);
            }
        } catch(Exception e) {
            LOG.error("Get openid and session_key failed...", e);
            throw new ActivityException(e);
        }
        return openid;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected Object submitPay(Map<String, String> map) {
        String unifieldorderParam;

//        this.appid = map.get("appid");
        //随机32位字符串
        String rnd = System.currentTimeMillis() + "";
        String nonceStr = String.format("%s%s", rnd, RandomUtils.randomString(32 - rnd.length()));
        //商品描述
        String body = map.get("body");
        //商品订单号，32位
        String orderNo = map.get("orderNo");
//        if(orderNo.length() > 32) {
        orderNo = orderNo.replaceAll("^ACTIVITY_", "");
//        }
        //金额，分
        int totalFee = (int) Math.round(Double.parseDouble(map.get("totalFee")) * 100);
        //track id
        String trackId = UUIDGenerate.getNextId();
        String unifiedorderSignParam = String.format(UNIFIELDORDER_SIGN_PARAMS,
                appid, trackId,
                body, mchId, nonceStr, xcxCallback, map.get("openid"),
                orderNo, map.get("spbillCreateIp"), totalFee, TRADE_TYPE, key);
        LOG.info("unifiedorderSignParam = {}", unifiedorderSignParam);
        //签名
        String unifiedorderSign = MD5.GetMD5Code(unifiedorderSignParam).toUpperCase();
        unifieldorderParam = String.format(UNIFIELDORDER_PARAMS,
                appid, trackId,
                body, mchId, nonceStr, xcxCallback, map.get("openid"),
                orderNo, map.get("spbillCreateIp"), totalFee, TRADE_TYPE, unifiedorderSign);
//        LOG.info("unifieldorderParam = {}", unifieldorderParam);
        CustomerPaymentTrack track = new CustomerPaymentTrack();
        {
            track.setId(trackId);
            track.setUserId(LoginManager.getCurrentUserId());
            track.setActivityId(map.get("activityId"));
            track.setNoteId(map.get("noteId"));
            track.setPayAmount(Double.parseDouble(map.get("totalFee")));
            track.setFeeType("1");//1 人民币
            track.setPaymentMode("1");//1 微信
            track.setPayTime(new Date());

            track.setAppid(appid);
            track.setMchId(mchId);
            track.setNonceStr(nonceStr);
            track.setSign(unifiedorderSign);
            track.setBody(body);
            track.setOutTradeNo(orderNo);
            track.setSpbillCreateIp(map.get("spbillCreateIp"));
            track.setNotifyUrl(xcxCallback);
            track.setTradeType(TRADE_TYPE);
            track.setOpenid(map.get("openid"));
            track.setRemark(unifieldorderParam);
            track.setSucceed(false);

            track.setCommand("INSERT");
        }
        return submitPay(track);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected Object submitPay(CustomerPaymentTrack track) {
        String unifieldorderParam = track.getRemark();
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        HttpPost post = null;
        try {
            HttpClient httpClient = HttpClients.createDefault();
            post = new HttpPost(UNIFIELDORDER_URL);
            //添加参数
            post.setEntity(new StringEntity(unifieldorderParam, Constants.CHARSET));
            response = (CloseableHttpResponse) httpClient.execute(post);
            // 检验返回码
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != HttpStatus.SC_OK){
                throw new ActivityException("请求出错: " + statusCode);
            } else {
                //获取结果实体
                entity = response.getEntity();
                if (entity != null) {
                    //按指定编码转换结果实体为String类型
                    String content = EntityUtils.toString(entity, Constants.CHARSET);
                    LOG.info("content = {}", content);

                    Document doc = DocumentHelper.parseText(content);
                    Element root = doc.getRootElement();
                    if(!"SUCCESS".equals(root.elementTextTrim("return_code"))) {
                        track.setFailedReason(root.elementTextTrim("return_msg"));
                    }
                    if(!"SUCCESS".equals(root.elementTextTrim("result_code"))) {
                        track.setResultCode(root.elementTextTrim("result_code"));
                        track.setFailedReason(root.elementTextTrim("err_code_des"));
                        track.setErrCode(root.elementTextTrim("err_code"));
                        track.setErrCodeDes(root.elementTextTrim("err_code_des"));
                    }
                    if("INSERT".equals(track.getCommand())) {
                        customerPaymentTrackService.insert(track);
                    } else {
                        customerPaymentTrackService.updateByPrimaryKey(track);
                    }
                    if(BlankUtil.isNotEmpty(track.getFailedReason())) {
                        throw new ActivityException(track.getFailedReason());
                    }
                    return new HashMap<String, String>() {
                        {
                            put("prepayId", root.elementTextTrim("prepay_id"));
                            put("outTradeNo", track.getOutTradeNo());
                            put("sign", track.getSign());
                            put("signType", "MD5");
                            put("nonce_str", track.getNonceStr());
                            put("outTradeNo", track.getOutTradeNo());
                            put("timeStamp", Math.round(System.currentTimeMillis() / 1000) + "");
                        }
                    };
                }
            }
        } catch (IOException e) {
            LOG.error("Submit pay failed...", e);
            throw new ActivityException(e);
        } catch (DocumentException e) {
            LOG.error("Submit pay failed...", e);
            throw new ActivityException(e);
        } finally {
            if(entity != null) {
                try {EntityUtils.consume(entity);} catch(Exception e) {LOG.error("EntityUtils.consume: ", e);}
            }
            if(post != null){
                post.releaseConnection();
            }
            if(response != null) {
                try {
                    //释放链接
                    try {response.close();} catch(Exception e) {LOG.error("response.close: ", e);}
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void notifyPayResult(String notifyXml) {
        try {
            Document doc = DocumentHelper.parseText(notifyXml);
            Element root = doc.getRootElement();
            if(!"SUCCESS".equals(root.elementTextTrim("return_code"))) {
                throw new ActivityException(root.elementTextTrim("return_msg"));
            }
            //订单号
            String orderNo = root.elementTextTrim("out_trade_no");
            //自定义字段，track id
            String trackId = root.elementTextTrim("attach");
            LOG.info("回调通知支付成功，订单号 = {}，日志id = {}", orderNo, trackId);
            //更新日志的支付状态
            Date date = Xiruo.stringToDate(root.elementTextTrim("time_end"), "yyyyMMddHHmmss");
            Optional.ofNullable(customerPaymentTrackService.selectByPrimaryKey(trackId)).ifPresent(track -> {
                if(BlankUtil.isNotEmpty(track.getSuccessTime())) {
                    Optional.ofNullable(root.elementTextTrim("device_info")).ifPresent(track::setDeviceInfo);
                    Optional.ofNullable(root.elementTextTrim("result_code")).ifPresent(track::setResultCode);
                    Optional.ofNullable(root.elementTextTrim("is_subscribe")).ifPresent(track::setIsSubscribe);
                    Optional.ofNullable(root.elementTextTrim("bank_type")).ifPresent(track::setBankType);
                    Optional.ofNullable(root.elementTextTrim("out_trade_no")).ifPresent(track::setOutTradeNo);
                    Optional.ofNullable(root.elementTextTrim("total_fee")).map(Integer::parseInt).ifPresent(track::setTotalFee);
                    Optional.ofNullable(root.elementTextTrim("settlement_total_fee")).map(Integer::parseInt).ifPresent(track::setTotalFee);
                    Optional.ofNullable(root.elementTextTrim("cash_fee")).map(Integer::parseInt).ifPresent(track::setCashFee);
                    Optional.ofNullable(root.elementTextTrim("cash_fee_type")).ifPresent(track::setCashFeeType);
                    Optional.ofNullable(root.elementTextTrim("transaction_id")).ifPresent(track::setTransactionId);
                    Optional.ofNullable(root.elementTextTrim("attach")).ifPresent(track::setAttach);
                    Optional.ofNullable(root.elementTextTrim("time_end")).ifPresent(track::setTimeEnd);
                    Optional.ofNullable(root.elementTextTrim("appid")).ifPresent(track::setAppid);
                    Optional.ofNullable(root.elementTextTrim("mch_id")).ifPresent(track::setMchId);
                    Optional.ofNullable(root.elementTextTrim("nonce_str")).ifPresent(track::setNonceStr);
                    Optional.ofNullable(root.elementTextTrim("sign")).ifPresent(track::setSign);
                    track.setSucceed(true);
                    track.setSuccessTime(date);
                    track.setFailedReason("ok");
                    Optional.ofNullable(root.elementTextTrim("err_code_des")).ifPresent(track::setErrCodeDes);
                    Optional.ofNullable(root.elementTextTrim("err_code")).ifPresent(errCode -> {
                        track.setErrCode(errCode);
                        track.setSucceed(false);
                        track.setSuccessTime(null);
                        track.setFailedReason(track.getErrCodeDes());
                    });
                    customerPaymentTrackService.updateByPrimaryKey(track);
                    activityOrderService.updateActivityOrderStatusByNoteId(track.getNoteId(), ActivityConstants.ORDER_STATUS.NOT_JOIN.getCode(), root.elementTextTrim("time_end"));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActivityException(e);
        }
    }

    public Object orderQuery(String trackId) {
        CustomerPaymentTrack customerPaymentTrack = customerPaymentTrackService.findOneByTrackId(trackId);
        Optional.ofNullable(customerPaymentTrack).ifPresent(track -> {
            CloseableHttpResponse response = null;
            try {
                HttpPost post = null;
                HttpEntity entity = null;
                try {
                    HttpClient httpClient = HttpClients.createDefault();
                    String orderQueryParams;
                    String rnd = System.currentTimeMillis() + "";
                    String nonceStr = String.format("%s%s", rnd, RandomUtils.randomString(32 - rnd.length()));
                    String orderquerySignParam;
                    String orderquerySign;
                    if(BlankUtil.isNotEmpty(track.getTransactionId())) {
                        orderquerySignParam = String.format(ORDERQUERY_SIGN_PARAMS,
                                appid, mchId, nonceStr, "transaction_id", track.getTransactionId(), key);
                        //签名
                        orderquerySign = MD5.GetMD5Code(orderquerySignParam).toUpperCase();
                        orderQueryParams = String.format(ORDERQUERY_PARAMS,
                                track.getAppid(), track.getMchId(), nonceStr,
                                "transaction_id", track.getTransactionId(), "transaction_id", orderquerySign);
                    } else {
                        orderquerySignParam = String.format(ORDERQUERY_SIGN_PARAMS,
                                appid, mchId, nonceStr, "out_trade_no", track.getOutTradeNo(), key);
                        //签名
                        orderquerySign = MD5.GetMD5Code(orderquerySignParam).toUpperCase();
                        orderQueryParams = String.format(ORDERQUERY_PARAMS,
                                track.getAppid(), track.getMchId(), nonceStr,
                                "out_trade_no", track.getOutTradeNo(), "out_trade_no", orderquerySign);
                    }
//                    LOG.info("orderquerySignParam = {}", orderquerySignParam);
//                    LOG.info("orderquerySign = {}", orderquerySign);
//                    LOG.info("orderQueryParams = {}", orderQueryParams);
                    post = new HttpPost(ORDERQUERY_URL);
                    //添加参数
                    post.setEntity(new StringEntity(orderQueryParams, Constants.CHARSET));
                    response = (CloseableHttpResponse) httpClient.execute(post);
                    // 检验返回码
                    int statusCode = response.getStatusLine().getStatusCode();
                    if(statusCode != HttpStatus.SC_OK){
                        throw new ActivityException("请求出错: " + statusCode);
                    } else {
                        //获取结果实体
                        entity = response.getEntity();
                        if (entity != null) {
                            //按指定编码转换结果实体为String类型
                            String body = EntityUtils.toString(entity, Constants.CHARSET);
                            LOG.info("body = {}", body);
                            Document doc = DocumentHelper.parseText(body);
                            Element root = doc.getRootElement();
                            if(!"SUCCESS".equals(root.elementTextTrim("return_code"))) {
                                throw new ActivityException(root.elementTextTrim("return_msg"));
                            }

                            Date date = Xiruo.stringToDate(root.elementTextTrim("time_end"), "yyyyMMddHHmmss");
                            if("SUCCESS".equals(root.elementTextTrim("trade_state"))) {
                                track.setSucceed(true);
                                track.setSuccessTime(date);
                                track.setFailedReason("ok");
                            } else {
                                track.setSucceed(false);
                                track.setSuccessTime(null);
                                track.setFailedReason(root.elementTextTrim("trade_state_desc"));
                            }

                            Optional.ofNullable(root.elementTextTrim("device_info")).ifPresent(track::setDeviceInfo);
                            Optional.ofNullable(root.elementTextTrim("result_code")).ifPresent(track::setResultCode);
                            Optional.ofNullable(root.elementTextTrim("err_code")).ifPresent(track::setErrCode);
                            Optional.ofNullable(root.elementTextTrim("err_code_des")).ifPresent(track::setErrCodeDes);
                            Optional.ofNullable(root.elementTextTrim("is_subscribe")).ifPresent(track::setIsSubscribe);
                            Optional.ofNullable(root.elementTextTrim("bank_type")).ifPresent(track::setBankType);
                            Optional.ofNullable(root.elementTextTrim("out_trade_no")).ifPresent(track::setOutTradeNo);
                            Optional.ofNullable(root.elementTextTrim("total_fee")).map(Integer::parseInt).ifPresent(track::setTotalFee);
                            Optional.ofNullable(root.elementTextTrim("settlement_total_fee")).map(Integer::parseInt).ifPresent(track::setTotalFee);
                            Optional.ofNullable(root.elementTextTrim("cash_fee")).map(Integer::parseInt).ifPresent(track::setCashFee);
                            Optional.ofNullable(root.elementTextTrim("cash_fee_type")).ifPresent(track::setCashFeeType);
                            Optional.ofNullable(root.elementTextTrim("transaction_id")).ifPresent(track::setTransactionId);
                            Optional.ofNullable(root.elementTextTrim("attach")).ifPresent(track::setAttach);
                            Optional.ofNullable(root.elementTextTrim("time_end")).ifPresent(track::setTimeEnd);
                            Optional.ofNullable(root.elementTextTrim("appid")).ifPresent(track::setAppid);
                            Optional.ofNullable(root.elementTextTrim("mch_id")).ifPresent(track::setMchId);
                            Optional.ofNullable(root.elementTextTrim("nonce_str")).ifPresent(track::setNonceStr);
                            Optional.ofNullable(root.elementTextTrim("sign")).ifPresent(track::setSign);
                            Optional.ofNullable(root.elementTextTrim("trade_state")).ifPresent(track::setTradeState);
                            Optional.ofNullable(root.elementTextTrim("trade_state_desc")).ifPresent(track::setTradeStateDesc);

                            customerPaymentTrackService.updateByPrimaryKey(track);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ActivityException(e);
                } finally {
                    if(entity != null) {
                        EntityUtils.consume(entity);
                    }
                    if(post != null){
                        post.releaseConnection();
                    }
                    releaseResponse(response);
                }
            } catch(Exception e) {
                LOG.error("Get openid and session_key failed...", e);
                throw new ActivityException(e);
            }
        });

        return customerPaymentTrack;
    }

    private static void releaseResponse(CloseableHttpResponse response) throws IOException {
        if(response != null) {
            try {
                //释放链接
                response.close();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new ActivityException(e);
            }
        }
    }
}
