package com.sida.security.edge.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
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

    private static final String[] NOT_REQUIRED_LOGIN_URL_ARRAY = {
            "/sec/**"
            , "/editor-app/**"
            , "/diagram-viewer/**"
            , "/login"
            , "/**/*.json"
            ,"/**/*.js,/**/*.css"
            , "/apis/system/sysUser/remoteRegister"
            , "/apis/system/authcodes/getRemoteAuthCode",

            //设计云
            "/apis/system/init/loadDicTree",
            "/apis/system/init/loadGlobalVariable",
//            "/apis/system/init/updateSysRegionPinyin",
            "/apis/system/sysRegion/singlelevel",
            "/apis/system/sysRegion/threelevel",
            "/apis/system/sysRegion/tree",
            "/apis/system/sysUserCustomer/**",
            "/apis/system/compensate/notify",
            "/apis/activity/sysUserCustomer/**",
            "/apis/content/sysUserCustomer/**",
            "/apis/activity/activityInfo/**",
            "/apis/operation/sysUserOperation/**",
            "/apis/activity/activitySchedule/**",
            "/apis/activity/honoredGuest/**",
            "/apis/operation/commonUser/findCommonUserById*"
    };

    @Autowired
    private ResourceServerTokenServices resourceServerTokenServices;

    @Autowired
    MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    /**
     *
     */
//    @Bean
//    @Primary
//    @Autowired
//    @Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
//    public OAuth2RestOperations restTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext oauth2ClientContext) {
//        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resource, oauth2ClientContext);
//        AuthorizationCodeAccessTokenProvider provider = new AuthorizationCodeAccessTokenProvider();
//        provider.setStateMandatory(false);
//        AccessTokenProvider accessTokenProvider =
//                new AccessTokenProviderChain(Arrays.asList(provider, new ImplicitAccessTokenProvider(), new ResourceOwnerPasswordAccessTokenProvider(), new ClientCredentialsAccessTokenProvider()));
//        oAuth2RestTemplate.setAccessTokenProvider(accessTokenProvider);
//        return oAuth2RestTemplate;
//    }

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
                .antMatchers(NOT_REQUIRED_LOGIN_URL_ARRAY).permitAll().anyRequest().authenticated()
                /*.and()
                .csrf().requireCsrfProtectionMatcher(csrfRequestMatcher()).csrfTokenRepository(csrfTokenRepository())*/
                .and()
               /* .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)*/
//                .addFilterAfter(optionsHttpMethodFilter(), HeaderWriterFilter.class)
                .addFilterAfter(oAuth2AuthenticationProcessingFilter(), AbstractPreAuthenticatedProcessingFilter.class)
//                .addFilterBefore(oAuth2AuthenticationProcessingFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                /*.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)*/
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true).permitAll();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //ignore 写在这儿无法记录登录用户从而记录日志，通过permitAll通过则ok
//        web.ignoring().antMatchers(
//                "/apis/system/init/loadDicTree",
//                "/apis/system/init/loadGlobalVariable",
////                "/apis/system/init/updateSysRegionPinyin",
//                "/apis/system/sysRegion/singlelevel",
//                "/apis/system/sysRegion/threelevel",
//                "/apis/system/sysRegion/tree",
//                "/apis/system/sysUserCustomer/*",
//                "/apis/activity/sysUserCustomer/*",
//                "/apis/content/sysUserCustomer/*",
//                "/apis/activity/activityInfo/list",
//                "/apis/activity/activityInfo/findOne",
//                "/apis/operation/sysUserOperation/*"
//                );
    }

    private OAuth2AuthenticationProcessingFilter oAuth2AuthenticationProcessingFilter() {
        OAuth2AuthenticationProcessingFilter oAuth2AuthenticationProcessingFilter =
                new OAuth2AuthenticationProcessingFilter();
        oAuth2AuthenticationProcessingFilter.setAuthenticationManager(oauthAuthenticationManager());
        oAuth2AuthenticationProcessingFilter.setStateless(false);
//        CustomerOAuth2ExceptionRenderer exceptionRenderer = new CustomerOAuth2ExceptionRenderer();
        OAuth2AuthenticationEntryPoint o = new OAuth2AuthenticationEntryPoint();
        o.setExceptionTranslator(new SidaWebResponseExceptionTranslator());
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
//    @Autowired
//    private OAuth2ClientContext oauth2ClientContext;
//    @Bean
//    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails resource) {
//        OAuth2RestTemplate template = new OAuth2RestTemplate(resource, oauth2ClientContext);
//
//        AuthorizationCodeAccessTokenProvider authCodeProvider = new AuthorizationCodeAccessTokenProvider();
//        authCodeProvider.setStateMandatory(false);
//        AccessTokenProviderChain provider = new AccessTokenProviderChain(Arrays.asList(authCodeProvider));
//        template.setAccessTokenProvider(provider);
//        return template;
//    }
}
