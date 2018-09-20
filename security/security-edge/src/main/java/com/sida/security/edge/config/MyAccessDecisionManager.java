package com.sida.security.edge.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Lv.Chen on 2017/9/19.
 */
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {

    private Logger log = LoggerFactory.getLogger(MyAccessDecisionManager.class);

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null){
            return ;
        }
        Iterator<ConfigAttribute> ite = configAttributes.iterator();
        log.info("==================decide方法执行了===========");
        while (ite.hasNext()){
            ConfigAttribute ca = ite.next();
            String neelRole = ((SecurityConfig) ca).getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities()){
                log.info("=====needRole===="+neelRole+"   +====用户权限===="+ga.getAuthority());
                if (neelRole.trim().equals(ga.getAuthority())){
                    return;
                }
            }
        }
        log.warn("没有权限访问！");
        throw new AccessDeniedException("没有权限访问!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
