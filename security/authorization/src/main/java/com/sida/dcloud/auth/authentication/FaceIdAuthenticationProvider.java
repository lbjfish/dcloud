package com.sida.dcloud.auth.authentication;

import com.sida.dcloud.auth.service.SysUserService;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class FaceIdAuthenticationProvider implements AuthenticationProvider {

    private SysUserService sysUserService;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private AccountStatusUserDetailsChecker accountStatusUserDetailsChecker = new AccountStatusUserDetailsChecker();

    public FaceIdAuthenticationProvider(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("FaceIdAuthenticationProvider authenticate");
        String faceId = authentication.getName();

        // 认证逻辑 - faceId有对应用户即可。
        UserDetails userDetails = sysUserService.selectUserByFaceId(faceId);
        if (null != userDetails) {
            //检查账户的状态
            accountStatusUserDetailsChecker.check(userDetails);

            FaceIdAuthenticationToken auth = new FaceIdAuthenticationToken(userDetails, authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));
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
        return authentication.equals(FaceIdAuthenticationToken.class);
    }
}