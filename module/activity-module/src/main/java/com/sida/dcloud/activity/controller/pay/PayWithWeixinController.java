package com.sida.dcloud.activity.controller.pay;

import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.service.CustomerPaymentTrackService;
import com.sida.dcloud.activity.util.pay.PayUtilWithXcx;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("payWithWeixin")
@Api(description = "微信支付")
public class PayWithWeixinController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(PayWithWeixinController.class);

    @Autowired
    private PayUtilWithXcx payUtilWithXcx;

    /**
     * 小程序
     * @param
     * @return
     */
    @RequestMapping(value = "/xcxOrderQuery", method = RequestMethod.GET)
    @ApiOperation(value = "从腾讯查询小程序支付订单")
    public Object xcxOrderQuery(@RequestParam("trackId") @ApiParam("支付日志id")String trackId) {
        return toResult(payUtilWithXcx.orderQuery(trackId));
    }

    /**
     * 小程序
     * @param
     * @return
     */
    @RequestMapping(value = "/xcx", method = RequestMethod.POST)
    @ApiOperation(value = "小程序支付")
    public Object xcx(@RequestBody @ApiParam("json数据")Map<String, String> params) {
//        Optional.ofNullable(params.get("appid")).orElseThrow(() -> new ActivityException("appid不能空"));
        Optional.ofNullable(params.get("id")).orElseThrow(() -> new ActivityException("报名表id不能空"));
//        Optional.ofNullable(params.get("jsCode")).orElseThrow(() -> new ActivityException("jsCode不能空"));
        params.put("spbillCreateIp", getIpAddress(request));
        return toResult(payUtilWithXcx.startPay(params));
    }

    /**
     * 小程序
     * @param
     * @return
     */
    @RequestMapping(value = "/xcxCallback", method = RequestMethod.GET)
    @ApiOperation(value = "根据主键id获取部分信息")
    public Object xcxCallback(HttpServletRequest request) {
        String inputLine = "";
        String notityXml = "";
         try {
             while ((inputLine = request.getReader().readLine()) != null) {
                 notityXml += inputLine;
             }
             //关闭流
             request.getReader().close();
         } catch(Exception e) {
             throw new ActivityException(e);
         }
        payUtilWithXcx.notifyPayResult(notityXml);
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

    public static String getIpAddress(HttpServletRequest request) {
        // 避免反向代理不能获取真实地址, 取X-Forwarded-For中第一个非unknown的有效IP字符串
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        // 如果是多级代理，那么取第一个ip为客户ip
        if (ip != null) {
            int index = ip.indexOf(",");
            if(index >= 0) {
                ip = ip.substring(0, index);
            }
//            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();

        }
        return ip;
    }
}
