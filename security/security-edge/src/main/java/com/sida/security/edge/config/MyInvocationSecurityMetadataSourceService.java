package com.sida.security.edge.config;


import com.alibaba.fastjson.JSON;
import com.sida.dcloud.auth.vo.SysResourceVo;
import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.util.BlankUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Lv.Chen on 2017/9/19.
 */
@Service("securityMetadataSource")
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    private Logger log = LoggerFactory.getLogger(MyInvocationSecurityMetadataSourceService.class);
    private static Map<String,Collection<ConfigAttribute>> resourceMap = null;
    @Autowired
    private RedisUtil redisUtil;

    public void updateResourceMap() {
        resourceMap = null;
    }

    private void loadResourceDefine(){
        resourceMap = new ConcurrentHashMap<>();
        Collection<ConfigAttribute> configAttributes = null;
        configAttributes = new ArrayList<ConfigAttribute>();
        ConfigAttribute configAttribute = null;

        String jsonStr = (String)redisUtil.get(RedisKey.RESOURCE_ALL);
        List<SysResourceVo> list = JSON.parseArray(jsonStr,SysResourceVo.class);


        for (SysResourceVo vo :list){
            List<String> listRole = vo.getRoleNameList();
            String url = vo.getValue();
            if (null != listRole && listRole.size()>0 && ""!=url && null !=url){
                for(String role : listRole){
                    configAttribute = new SecurityConfig(role);
                    configAttributes.add(configAttribute);
                    resourceMap.put(url,configAttributes);
                }
            }
        }
    }
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if(resourceMap == null)
            loadResourceDefine();
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();

        AntPathRequestMatcher matcher;
        String resUrl = null;
        for (Iterator<String> iter = resourceMap.keySet().iterator(); iter.hasNext();){
            resUrl = iter.next();
            if(BlankUtil.isNotEmpty(resUrl)){
                matcher = new AntPathRequestMatcher(resUrl);
                if (matcher.matches(request)){
                    return resourceMap.get(resUrl);
                }
            }
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
