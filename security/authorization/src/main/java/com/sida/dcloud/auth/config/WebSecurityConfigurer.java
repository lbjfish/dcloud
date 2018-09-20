package com.sida.dcloud.auth.config;

import com.sida.dcloud.auth.authentication.FaceIdAuthenticationProvider;
import com.sida.dcloud.auth.authentication.MsgAuthCodeAuthenticationProvider;
import com.sida.dcloud.auth.service.SysUserService;
import com.sida.dcloud.auth.service.impl.ClientDetailsServiceImpl;
import com.sida.dcloud.auth.service.impl.UserDetailsServiceImpl;
import com.sida.xiruo.common.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Created by Xiruo on 2017/7/18.
 */
@Order(ManagementServerProperties.ACCESS_OVERRIDE_ORDER)
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
   /* @Autowired
    private UserDetailsService userDetailsService;*/
   @Autowired
   private ClientDetailsServiceImpl clientDetailsServiceImpl;
   @Autowired
   private SysUserService sysUserService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable().formLogin().loginPage("/login").permitAll().and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/validate/code/image").permitAll()
                .anyRequest().authenticated();

        // 禁用缓存
        http.headers().cacheControl();
    }



//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//       /*auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER")
//                .and()
//                .withUser("admin").password("admin").roles("ADMIN");*/
//        auth.userDetailsService(/*new ClientDetailsUserDetailsService(clientDetailsServiceImpl)*/ userDetailsService()).passwordEncoder(new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence charSequence) {
//                return MD5Util.MD5PWD((String) charSequence);
//            }
//
//            @Override
//            public boolean matches(CharSequence charSequence, String pwd) {
//                return pwd.equals(MD5Util.MD5PWD((String) charSequence));
//            }
//        });
//        //auth.setSharedObject(ClientDetailsService.class,clientDetailsServiceImpl);
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return MD5Util.MD5PWD((String) charSequence);
            }

            @Override
            public boolean matches(CharSequence charSequence, String pwd) {
                return pwd.equals(MD5Util.MD5PWD((String) charSequence));
            }
        });
        // 使用自定义身份验证组件
        auth.authenticationProvider(new FaceIdAuthenticationProvider(sysUserService))
                .authenticationProvider(new MsgAuthCodeAuthenticationProvider(sysUserService));
    }

}