package com.sida.dcloud.auth.config;

import com.sida.dcloud.auth.authentication.CustomJwtAccessTokenConverter;
import com.sida.dcloud.auth.authentication.FaceIdTokenGranter;
import com.sida.dcloud.auth.authentication.MsgAuthCodeTokenGranter;
import com.sida.dcloud.auth.service.SysUserService;
import com.sida.dcloud.auth.service.impl.ClientDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.*;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import redis.clients.jedis.JedisCluster;

import java.security.KeyPair;
import java.util.*;

/**
 * Created by Xiruo on 2017/7/18.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {
    private static Logger logger = LoggerFactory.getLogger(AuthorizationServerConfigurer.class);
    @Value("${qiniu.defaultHeader}")
    private String defaultHeader;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;
    /*@Autowired
    private ClientDetailsServiceImpl ClientDetailsServiceImpl;*/
    @Autowired
    private ClientDetailsServiceImpl clientDetailsServiceImpl;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private JedisCluster jedisCluster;

    /*
    避免循环依赖，源类中已使用@Service注解这里不需要重复定义@Bean，直接使用即可
    @Bean
    public ClientDetailsServiceImpl clientDetailsServiceImpl(){
        return new ClientDetailsServiceImpl();
    }*/

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new CustomJwtAccessTokenConverter(sysUserService, defaultHeader);
//        KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("clzdev.jks"), "chelizi201723".toCharArray())
//                .getKeyPair("chelizidev");
        KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("sida.jks"), "sidasida".toCharArray())
                .getKeyPair("sida");
        converter.setKeyPair(keyPair);
        return converter;
    }

//    @Bean
//    public AuthorizationCodeServices redisAuthorizationCodeServices() {
//        return new RedisAuthorizationCodeServices(jedisCluster);
//    }

    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints){
        List<TokenGranter> granters = new ArrayList<TokenGranter>(Arrays.asList(endpoints.getTokenGranter()));
        granters.add(new FaceIdTokenGranter(
                authenticationManager,
                endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(),
                FaceIdTokenGranter.GRANT_TYPE));
        granters.add(new MsgAuthCodeTokenGranter(
                authenticationManager,
                endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(),
                MsgAuthCodeTokenGranter.GRANT_TYPE
        ));
        return new CompositeTokenGranter(granters);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*clients.inMemory()
                .withClient("acme")
                .secret("acmesecret")
                .authorizedGrantTypes("authorization_code", "refresh_token","password")
                .scopes("openid");*/
        logger.info("==============================="+clientDetailsServiceImpl);
        clients.withClientDetails(clientDetailsServiceImpl);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        /*endpoints.pathMapping("/oauth/confirm_access", "/authorize");
        endpoints.pathMapping("/oauth/login", "/login");*/
        endpoints.authenticationManager(authenticationManager).accessTokenConverter(jwtAccessTokenConverter());
//        endpoints.authorizationCodeServices(redisAuthorizationCodeServices());
        endpoints.tokenGranter(tokenGranter(endpoints));
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").allowFormAuthenticationForClients();
    }
}