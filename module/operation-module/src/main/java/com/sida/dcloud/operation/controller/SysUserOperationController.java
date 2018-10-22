package com.sida.dcloud.operation.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.sida.dcloud.operation.common.OperationConstants;
import com.sida.dcloud.operation.common.OperationException;
import com.sida.dcloud.operation.dto.CommonUserOperation;
import com.sida.dcloud.operation.po.SysUserOperation;
import com.sida.dcloud.operation.service.SysUserOperationService;
import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.xiruo.common.components.encrypt.Base64;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.MobileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import scala.annotation.meta.param;
import ytx.org.apache.http.params.CoreConnectionPNames;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("sysUserOperation")
@Api(description = "系统用户信息")
public class SysUserOperationController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(SysUserOperationController.class);

    @Value("${service.security.auth.port}")
    private String authPort;
    @Autowired
    private SysUserOperationService sysUserOperationService;

    @RequestMapping(value = "/generateMobileVerifyCode", method = RequestMethod.GET)
    @ApiOperation(value = "获取手机验证码")
    public Object generateMobileVerifyCode(@RequestParam("mobileType") @ApiParam("终端类型")String mobileType,
            @RequestParam("mobile") @ApiParam("手机号码")String mobile) {
        return toResult(sysUserOperationService.generateMobileVerifyCode(mobileType, mobile));
    }

    @RequestMapping(value = "/verifyBindStatus", method = RequestMethod.POST)
    @ApiOperation(value = "判断第三方账号是否绑定过手机号")
    public Object verifyBindStatus(@RequestBody @ApiParam("json数据") CommonUserOperation dto) {
        CommonUserOperation commonUser = sysUserOperationService.verifyBindStatus(dto);
        //头盖骨第三方账号找到手机号码则使用手机号码登录
        if(commonUser != null) {
            String mobileType = "thirdpart";
            String mobile = commonUser.getMobile();
            return toResult(autoLogin(mobile, mobileType));
        }
        return toResult(0);
    }

    @RequestMapping(value = "/findThirdPartAccount", method = RequestMethod.POST)
    @ApiOperation(value = "通过userId或mobile查询第三方绑定账号")
    public Object findThirdPartAccount(@RequestBody @ApiParam("userId or mobile") CommonUserOperation dto) {
        return toResult(sysUserOperationService.findThirdPartAccount(dto));
    }

    /********************************************************************************/

    @RequestMapping(value = "/bindMobile", method = RequestMethod.POST)
    @ApiOperation(value = "登录绑定&注册用户")
    public Object bindMobile(@RequestBody @ApiParam("用户信息JSON") CommonUserOperation param) {
        checkForm(param, EventConstants.EVENT_INSERT);
        int result = sysUserOperationService.bindMobile(param);
        if(result == -1) {
            return toResult("请求过程中发生错误，请联系管理员");
        }
        return toResult(autoLogin(param.getMobile(), param.getMobileType()));
    }

    @RequestMapping(value = "/bindThirdPartAccount", method = RequestMethod.POST)
    @ApiOperation(value = "绑定第三方账号")
    public Object bindThirdPartAccount(@RequestBody @ApiParam("用户信息JSON") CommonUserOperation param) {
//        LOG.info("login {} = argument {}", LoginManager.getCurrentMobile(), param.getMobile());
        Optional.ofNullable(param.getMobile()).orElseThrow(() -> new OperationException("手机号不能空"));
        if(!MobileUtil.isMobile(param.getMobile())) {
            throw new OperationException("手机号码格式不正确");
        }
        if(BlankUtil.isEmpty(param.getWechat())
            && BlankUtil.isEmpty(param.getQq())
                && BlankUtil.isEmpty(param.getAlipayNo())
                && BlankUtil.isEmpty(param.getSinaWeibo())
                && BlankUtil.isEmpty(param.getFacebookNo())
                && BlankUtil.isEmpty(param.getTwitterNo())) {
            throw new OperationException("绑定账号不能空");
        }
        if(!param.getMobile().equals(LoginManager.getCurrentMobile())) {
            throw new OperationException("绑定手机号不匹配");
        }
        return toResult(sysUserOperationService.bindThirdPartAccount(param));
    }

    @RequestMapping(value = "/unbindThirdPartAccount", method = RequestMethod.POST)
    @ApiOperation(value = "取消绑定第三方账号")
    public Object unbindThirdPartAccount(@RequestBody @ApiParam("用户信息JSON") CommonUserOperation param) {
        Optional.ofNullable(param.getMobile()).orElseThrow(() -> new OperationException("手机号不能空"));
        Optional.ofNullable(param.getLoginFrom()).orElseThrow(() -> new OperationException("解绑账号不能空"));
        if(!MobileUtil.isMobile(param.getMobile())) {
            throw new OperationException("手机号码格式不正确");
        }
        return toResult(sysUserOperationService.unbindThirdPartAccount(param));
    }

    @RequestMapping(value = "/bindNewMobile", method = RequestMethod.POST)
    @ApiOperation(value = "更改手机")
    public Object bindNewMobile(@RequestBody @ApiParam("用户信息JSON") CommonUserOperation param) {
        Optional.ofNullable(param.getId()).orElseThrow(() ->new OperationException("id不能空"));
        Optional.ofNullable(param.getMobile()).orElseThrow(() ->new OperationException("手机号不能空"));
        Optional.ofNullable(param.getVerifyCode()).orElseThrow(() ->new OperationException("验证码不能空"));
        Optional.ofNullable(param.getMobileType()).orElseThrow(() ->new OperationException("终端类型不能空"));
        if(!MobileUtil.isMobile(param.getMobile())) {
            throw new OperationException("手机号码格式不正确");
        }
        return toResult(sysUserOperationService.bindNewMobile(param));
    }

    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户资料")
    public Object updateUserInfo(@RequestBody @ApiParam("用户信息JSON") CommonUserOperation param) {
        checkForm(param, EventConstants.EVENT_UPDATE);
        if(!param.getId().equals(LoginManager.getCurrentUserId())) {
            throw new OperationException("绑定手机号不匹配");
        }
        return toResult(sysUserOperationService.updateUserInfo(param));
    }

    @RequestMapping(value = "/updateUserPassword", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户密码")
    public Object updateUserPassword(@RequestBody @ApiParam("用户信息JSON") CommonUserOperation param) {
        Optional.ofNullable(param.getMobile()).orElseThrow(() ->new OperationException("手机号不能空"));
        Optional.ofNullable(param.getVerifyCode()).orElseThrow(() ->new OperationException("验证码不能空"));
        Optional.ofNullable(param.getPassword()).orElseThrow(() ->new OperationException("密码不能空"));
        if(!MobileUtil.isMobile(param.getMobile())) {
            throw new OperationException("手机号码格式不正确");
        }
        if(!param.getMobile().equals(LoginManager.getCurrentMobile())) {
            throw new OperationException("绑定手机号不匹配");
        }
        return toResult(sysUserOperationService.updateUserPassword(param));
    }

    private void checkForm(CommonUserOperation param, int event) {
        checkIdEmpty(param, event);

//        Optional.ofNullable(param.getName()).orElseThrow(() ->new OperationException("名称不能空"));
        if(EventConstants.EVENT_INSERT == event) {
//            Optional.ofNullable(param.getPassword()).orElseThrow(() ->new OperationException("密码不能空"));
            Optional.ofNullable(param.getMobile()).orElseThrow(() ->new OperationException("手机号不能空"));
            Optional.ofNullable(param.getVerifyCode()).orElseThrow(() ->new OperationException("验证码不能空"));
            Optional.ofNullable(param.getMobileType()).orElseThrow(() ->new OperationException("终端类型不能空"));
            if(!MobileUtil.isMobile(param.getMobile())) {
                throw new OperationException("手机号码格式不正确");
            }
            param.setSort(0);
            param.setName(param.getMobile());
            param.setPassword(param.getMobile());
        }
        fillDefaultFields(param, event);
    }

    /**************************************************************/
    private Object autoLogin(String mobile, String mobileType) {
        CloseableHttpResponse response = null;
        try {
            String loginUrl = String.format("http://%s:%s/sec/oauth/token", request.getRemoteHost(), authPort);
            {
                String authCode = sysUserOperationService.generateMobileVerifyCode(mobileType, mobile);
                HttpPost post = null;
                try {
                    HttpClient httpClient = HttpClients.createDefault();
                    post = new HttpPost(loginUrl);
                    // 构造消息头
                    post.setHeader("Content-Type", "application/x-www-form-urlencoded");
//                        post.setHeader("Authorization", "Basic " + Base64.encode("acme:acmesecret"));
                    post.setHeader("Authorization", "Basic YWNtZTphY21lc2VjcmV0");
                    post.setHeader("Accept", "application/json");
                    // 构造消息体
                    List<NameValuePair> params = new ArrayList<>();
                    //建立一个NameValuePair数组，用于存储欲传送的参数
                    params.add(new BasicNameValuePair("grant_type","msgAuthCode"));
                    params.add(new BasicNameValuePair("mobileType",mobileType));
                    params.add(new BasicNameValuePair("mobile",mobile));
                    params.add(new BasicNameValuePair("msgAuthCode",authCode));
                    //添加参数
                    post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    response = (CloseableHttpResponse) httpClient.execute(post);
                    // 检验返回码
                    int statusCode = response.getStatusLine().getStatusCode();
                    if(statusCode != HttpStatus.SC_OK){
                        LOG.error("请求出错: " + statusCode);
                    } else {
                        //获取结果实体
                        HttpEntity entity = response.getEntity();
                        String body = "";
                        if (entity != null) {
                            //按指定编码转换结果实体为String类型
                            body = EntityUtils.toString(entity, "UTF-8");
//                                ((JSONObject)JSONObject.parse(body)).;
                        }
                        EntityUtils.consume(entity);
                        return JSONObject.parse(body);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if(post != null){
                        post.releaseConnection();
                    }
                    if(response != null) {
                        try {
                            //释放链接
                            response.close();
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return toResult(0);
        } catch(Exception e) {
            LOG.error("Redirect to login failed...", e);
            return -1;
        }
    }

    /*****************************************/
    @RequestMapping(value = "/testDistributeTransaction", method = RequestMethod.GET)
    @ApiOperation(value = "测试分布式事务")
    public Object testDistributeTransaction(@RequestParam(value = "id",defaultValue = "100") @ApiParam("id")String id,
                                           @RequestParam(name="remark",defaultValue = "remark") @ApiParam("描述")String remark) {
        return toResult(sysUserOperationService.testDistributeTransaction(id, remark));
    }
}