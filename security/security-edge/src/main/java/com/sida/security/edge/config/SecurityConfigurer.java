package com.sida.security.edge.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;



/**
 * Created by Xiruo
 */
@Configuration
@EnableOAuth2Sso
@EnableResourceServer
@Order(value = 0)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    private static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";
    private static final String CSRF_HEADER_NAME = "X-XSRF-TOKEN";

    @Autowired
    private ResourceServerTokenServices resourceServerTokenServices;

    @Autowired
    MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    // 权限拦截器
    /*@Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;*/

    /*@Bean
    @Primary
    public OAuth2ClientContextFilter dynamicOauth2ClientContextFilter() {
        return new DynamicOauth2ClientContextFilter();
    }*/

//    @Bean
//    public OptionsHttpMethodFilter optionsHttpMethodFilter() {
//        return new OptionsHttpMethodFilter();
//    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/sec/**"
                        , "/editor-app/**"
                        , "/diagram-viewer/**"
                        , "/login"
                        , "/**/*.json"
                        ,"/**/*.js,/**/*.css"
                        , "/apis/system/sysUser/remoteRegister"
                        , "/apis/system/authcodes/getRemoteAuthCode"
                ).permitAll().anyRequest().authenticated()
                /*.and()
                .csrf().requireCsrfProtectionMatcher(csrfRequestMatcher()).csrfTokenRepository(csrfTokenRepository())*/
                .and()
               /* .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)*/
//                .addFilterAfter(optionsHttpMethodFilter(), HeaderWriterFilter.class)
                .addFilterAfter(oAuth2AuthenticationProcessingFilter(), AbstractPreAuthenticatedProcessingFilter.class)
                .addFilterAt(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                /*.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)*/
                .logout().permitAll()
                .logoutSuccessUrl("/");

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //ignore
        web.ignoring().antMatchers("/apis/system/init/loadDicTree");
    }

    private OAuth2AuthenticationProcessingFilter oAuth2AuthenticationProcessingFilter() {
        OAuth2AuthenticationProcessingFilter oAuth2AuthenticationProcessingFilter =
                new OAuth2AuthenticationProcessingFilter();
        oAuth2AuthenticationProcessingFilter.setAuthenticationManager(oauthAuthenticationManager());
        oAuth2AuthenticationProcessingFilter.setStateless(false);
//        CustomerOAuth2ExceptionRenderer exceptionRenderer = new CustomerOAuth2ExceptionRenderer();
        OAuth2AuthenticationEntryPoint o = new OAuth2AuthenticationEntryPoint();
        o.setExceptionTranslator(new ClzWebResponseExceptionTranslator());
//        o.setExceptionRenderer(exceptionRenderer);
        oAuth2AuthenticationProcessingFilter.setAuthenticationEntryPoint(o);

        return oAuth2AuthenticationProcessingFilter;
    }


    private AuthenticationManager oauthAuthenticationManager() {
        OAuth2AuthenticationManager oAuth2AuthenticationManager = new OAuth2AuthenticationManager();
        oAuth2AuthenticationManager.setResourceId("securityedge");
        oAuth2AuthenticationManager.setTokenServices(resourceServerTokenServices);
        oAuth2AuthenticationManager.setClientDetailsService(null);

        return oAuth2AuthenticationManager;
    }

    private RequestMatcher csrfRequestMatcher() {
        return new RequestMatcher() {
            // Always allow the HTTP GET method
            private final Pattern allowedMethods = Pattern.compile("^(GET|HEAD|OPTIONS|TRACE)$");

            // Disable CSFR protection on the following urls:
            private final AntPathRequestMatcher[] requestMatchers = { new AntPathRequestMatcher("/sec/**") };

            @Override
            public boolean matches(HttpServletRequest request) {
                if (allowedMethods.matcher(request.getMethod()).matches()) {
                    return false;
                }

                for (AntPathRequestMatcher matcher : requestMatchers) {
                    if (matcher.matches(request)) {
                        return false;
                    }
                }
                return true;
            }
        };
    }

    /*private static Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
                if (csrf != null) {
                    Cookie cookie = new Cookie(CSRF_COOKIE_NAME, csrf.getToken());
                    cookie.setPath("/");
                    cookie.setSecure(true);
                    response.addCookie(cookie);
                }
                filterChain.doFilter(request, response);
            }
        };
    }*/

    private static CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName(CSRF_HEADER_NAME);
        return repository;
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/v2/api-docs",//swagger api json
//                "/swagger-resources/configuration/ui",//用来获取支持的动作
//                "/swagger-resources",//用来获取api-docs的URI
//                "/swagger-resources/configuration/security",//安全选项
//                "/swagger-ui.html");
//    }

    /*@Bean
    public MyFilterSecurityInterceptor myFilterSecurityInterceptor(){
        MyFilterSecurityInterceptor myFilterSecurityInterceptor = new MyFilterSecurityInterceptor();
        myFilterSecurityInterceptor.setSecurityMetadataSource(securityMetadataSource());
        return myFilterSecurityInterceptor;
    }
    @Bean
    @Qualifier("securityMetadataSource")
    public FilterInvocationSecurityMetadataSource securityMetadataSource(){
        return new MyInvocationSecurityMetadataSourceService();
    }*/

}
