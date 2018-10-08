package com.sida.dcloud.auth.vo;

import com.sida.dcloud.auth.po.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
 * Created by Xiruo on 2017/7/21.
 */
public class UserDetailsVo /*extends SysUser*/ implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;
    private Collection<? extends GrantedAuthority> authorities;
    private String username;
    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enabled;
    private String password;
    private String id;
    private String orgId;
    private String name;
    private String faceId;

    private UserDetailsVo(){
    }
    public UserDetailsVo(SysUser user, Collection<GrantedAuthority> authorities){
//        BeanUtils.copyProperties(user, this);
     /*  super(user);*/
        this.authorities = authorities;
        this.id = user.getId();
        this.orgId = user.getOrgId();
        this.name = user.getName();
        this.faceId = user.getFaceId();
        this.username = user.getAccount();
        Optional.ofNullable(user.getValidDate()).ifPresent(datetime -> this.accountNonExpired = datetime.getTime() >= System.currentTimeMillis());
        this.accountNonLocked = !user.getLocked();
        this.credentialsNonExpired = true;
        this.enabled = !user.getDisable();
        this.password = user.getPassword();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
//        return getName();
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getName() {
        return name;
    }

    public String getFaceId() {
        return faceId;
    }
}