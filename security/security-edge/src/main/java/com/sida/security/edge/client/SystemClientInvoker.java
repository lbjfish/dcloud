package com.sida.security.edge.client;

import com.sida.dcloud.system.po.SysAccessLogDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huangbaidong
 * 2017/11/2.
 */
@Component
public class SystemClientInvoker {

    @Autowired
    private SystemClient systemClient;

    public void insertAccessLogDetail(SysAccessLogDetail detail) {
        systemClient.insertAccessLogDetail(detail);
    }
}
