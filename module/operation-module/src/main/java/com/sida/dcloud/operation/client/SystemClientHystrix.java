package com.sida.dcloud.operation.client;

import com.sida.dcloud.operation.common.OperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SystemClientHystrix implements SystemClient {
    private static final Logger LOG = LoggerFactory.getLogger(SystemClientHystrix.class);

    @Override
    public Object saveOrUpdateDto(Map<String, String> map) {
        LOG.warn("进入断路器-saveOrUpdateDto。。。");
        throw new OperationException("saveOrUpdateDto 保存失败.");
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
    public Object updateUserPassword(Map<String, String> map) {
        LOG.warn("进入断路器-updateUserPassword。。。");
        throw new OperationException("updateUserPassword 保存失败.");
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
