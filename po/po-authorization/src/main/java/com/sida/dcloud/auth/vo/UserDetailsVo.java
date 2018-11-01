package com.sida.dcloud.auth.vo;

import com.sida.dcloud.auth.po.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by Xiruo on 2017/7/21.
 */
public class UserDetailsVo extends SysUser implements UserDetails {

    private Collection<? extends GrantedAuthority> authorities ;

    public UserDetailsVo(){
    }
    public UserDetailsVo(SysUser user,Collection<GrantedAuthority> authorities){
        BeanUtils.copyProperties(user, this);
     /*  super(user);*/
        this.authorities = authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities){
        this.authorities = authorities;
    }
    @Override
    public String getUsername() {
//        return getName();
        return getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true/*getValidDate().getTime() >= System.currentTimeMillis()*/;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !getLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !getDisable();
    }


}