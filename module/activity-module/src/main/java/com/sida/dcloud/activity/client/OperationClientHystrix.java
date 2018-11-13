package com.sida.dcloud.activity.client;

import com.sida.dcloud.activity.common.ActivityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class OperationClientHystrix implements OperationClient {
    private static final Logger LOG = LoggerFactory.getLogger(OperationClientHystrix.class);

    @Override
    public Object findCommonUserById(String userId) {
        LOG.warn("进入断路器-findCommonUserById。。。");
        throw new ActivityException("findCommonUserById 失败.");
    }

    @Override
    public Object updateFaceUrl(Map<String, String> map) {
        LOG.warn("进入断路器-updateFaceUrl。。。");
        throw new ActivityException("updateFaceUrl 失败.");
    }

    @Override
    public Object findOne(String id) {
        LOG.warn("进入断路器-findOne。。。");
        throw new ActivityException("findOne 失败.");
    }

    @Override
    public Object findMany(String ids) {
        LOG.warn("进入断路器-findMany。。。");
        throw new ActivityException("findMany 失败.");
    }
}
