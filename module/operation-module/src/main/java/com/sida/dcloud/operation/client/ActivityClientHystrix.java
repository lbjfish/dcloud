package com.sida.dcloud.operation.client;

import com.sida.dcloud.operation.common.OperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ActivityClientHystrix implements ActivityClient {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityClientHystrix.class);

    @Override
    public Object insertDto(Map<String, String> map) {
        LOG.warn("进入断路器-insertDto。。。");
        throw new OperationException("insertDto 保存失败.");
    }

    @Override
    public Object updateMobile(Map<String, String> map) {
        LOG.warn("进入断路器-updateMobile。。。");
        throw new OperationException("updateMobile 保存失败.");
    }

    @Override
    public Object updateUserInfo(Map<String, String> map) {
        LOG.warn("进入断路器-updateUserInfo。。。");
        throw new OperationException("updateUserInfo 保存失败.");
    }

    @Override
    public Object unbindThirdPartAccount(String loginFrom, String mobile) {
        LOG.warn("进入断路器-unbindThirdPartAccount。。。");
        throw new OperationException("unbindThirdPartAccount 保存失败.");
    }

    @Override
    public Object bindThirdPartAccount(Map<String, String> map) {
        LOG.warn("进入断路器-bindThirdPartAccount。。。");
        throw new OperationException("bindThirdPartAccount 保存失败.");
    }

    @Override
    public Object testDistributeTransaction(String id, String remark) {
        LOG.warn("进入断路器-testDistributeTransaction。。。");
        throw new OperationException("testDistributeTransaction 保存失败.");
    }
}
