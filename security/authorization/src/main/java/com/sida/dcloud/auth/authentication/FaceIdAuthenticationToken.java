package com.sida.dcloud.auth.authentication;

import com.sida.dcloud.auth.vo.UserDetailsVo;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class FaceIdAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private Object credentials;
    private Object faceId;
    private Object details;

    public FaceIdAuthenticationToken(Object principal){
        super(null);
        this.principal = principal;
        if (principal instanceof UserDetailsVo) {
            UserDetailsVo userDetails = (UserDetailsVo)principal;
            this.faceId = userDetails.getFaceId();
            this.credentials = userDetails.getFaceId();
        } else {
            this.faceId = principal;
            this.credentials = principal;
        }
        setAuthenticated(false);
    }

    public FaceIdAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        if (principal instanceof UserDetailsVo) {
            UserDetailsVo userDetails = (UserDetailsVo)principal;
            this.faceId = userDetails.getFaceId();
            this.credentials = userDetails.getFaceId();
        } else {
            this.faceId = principal;
            this.credentials = principal;
        }
        super.setAuthenticated(true); // must use super, as we override
    }

    public Object getFaceId() {
        return this.faceId;
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public void setDetails(Object details) {
        this.details = details;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}