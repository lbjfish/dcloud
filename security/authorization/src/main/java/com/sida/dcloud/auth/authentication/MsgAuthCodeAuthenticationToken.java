package com.sida.dcloud.auth.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MsgAuthCodeAuthenticationToken extends AbstractAuthenticationToken {
    //“手机号”或者“UserDetailVo实例”
    private final Object principal;
    // “输入的短信验证码”
    private Object credentials;
    private Object details;

    public MsgAuthCodeAuthenticationToken(Object principal, Object credentials){
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public MsgAuthCodeAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public void setDetails(Object details) {
        this.details = details;
    }
}
