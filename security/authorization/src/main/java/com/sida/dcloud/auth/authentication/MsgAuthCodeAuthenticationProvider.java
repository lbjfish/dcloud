package com.sida.dcloud.auth.authentication;

import com.sida.dcloud.auth.service.SysUserService;
import com.sida.xiruo.common.util.AuthCodeConstants;
import com.sida.xiruo.xframework.exception.ServiceException;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MsgAuthCodeAuthenticationProvider implements AuthenticationProvider {

    private SysUserService sysUserService;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private AccountStatusUserDetailsChecker accountStatusUserDetailsChecker = new AccountStatusUserDetailsChecker();

    public MsgAuthCodeAuthenticationProvider(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("MsgAuthCodeAuthenticationProvider authenticate");
        //手机号
        String principal = authentication.getName();
        //用户输入的验证码
        String msgAuthCode = authentication.getCredentials().toString();

        // 认证逻辑 - 通过service验证是否有效。
        boolean valid = sysUserService.isValid(msgAuthCode, principal, AuthCodeConstants.REQTYPE_REMOTE_LOGIN_2);

        if (!valid) {
            throw new ServiceException("短信验证码不正确!");
        }

        UserDetails userDetails = sysUserService.selectUserByPhone(principal);
        if (null != userDetails) {
            //检查账户的状态
            accountStatusUserDetailsChecker.check(userDetails);

            MsgAuthCodeAuthenticationToken auth = new MsgAuthCodeAuthenticationToken(userDetails, msgAuthCode, authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));
            auth.setDetails(userDetails);
            auth.setAuthenticated(true);
            return auth;
        } else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }

    // 是否可以提供输入类型的认证服务
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(MsgAuthCodeAuthenticationToken.class);
    }
}