package com.sida.dcloud.auth.service.impl;

import com.sida.dcloud.auth.dao.SysClientDetailMapper;
import com.sida.dcloud.auth.po.SysClientDetail;
import com.sida.dcloud.auth.vo.ClientDetailsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * Created by Xiruo on 2017/7/21.
 */
@Service("clientDetailsServiceImpl")
public class ClientDetailsServiceImpl /*extends SysClientDetailServiceImpl*/ implements ClientDetailsService {
    private static Logger logger = LoggerFactory.getLogger(ClientDetailsServiceImpl.class);
    @Autowired
    private SysClientDetailMapper clientDetailMapper;
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        SysClientDetail clientDetail = clientDetailMapper.selectClientDetailByClientId(clientId);


        if (clientDetail == null){
            throw new ClientRegistrationException(String.format("Client %s does not exist!",clientId));
        }
        return new ClientDetailsVo(clientDetail);
    }
}