package com.sida.xiruo.xframework.util;

import net.sf.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * RestTemplate请求工具类
 * Created by huangbaidong
 * 2017/10/22.
 */
public class RestTemplateRequestUtil {


    /**
     * GET请求
     * @param url
     * @param params
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getRequest(String url, Map<String, Object> params, Class<T> clazz) {
        return request(url, params, clazz, HttpMethod.GET);
    }

    /**
     * POST请求
     * @param url
     * @param params
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T postRequest(String url, Map<String, Object> params, Class<T> clazz) {
        return request(url, params, clazz, HttpMethod.POST);
    }


    /**
     * restTemplate请求,
     * 默认设置了连接超时，请求超时时间
     * @param url 请求地址
     * @param params 请求参数
     * @param clazz  返回结果类型
     * @param <T>
     * @return
     */
    public static <T> T request(String url, Map<String, Object> params, Class<T> clazz, HttpMethod method) {
        return request(url, params, clazz, method,30000, 20000);
    }

    /**
     * restTemplate请求
     * @param url 请求地址
     * @param params 请求参数
     * @param clazz  返回结果类型
     * @param readTimeout     请求响应超时时间
     * @param connectTimeout  连接超时时间
     * @param <T>
     * @return
     */
    public static <T> T request(String url, Map<String, Object> params, Class<T> clazz,
                                HttpMethod method,int readTimeout, int connectTimeout) {

        RestTemplate restTemplate = new RestTemplate();

        //设置连接参数
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(readTimeout);
        requestFactory.setConnectTimeout(connectTimeout);
        restTemplate.setRequestFactory(requestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

        //拿到access_toeken
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();

        //请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "bearer "+details.getTokenValue());

        //发起请求
        ResponseEntity<T> responseEntity = null;
        if(HttpMethod.POST.equals(method)) {
            JSONObject jsonParams = JSONObject.fromObject(params);
            HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(jsonParams, headers);
            responseEntity = restTemplate.exchange(url, method, requestEntity, clazz);
        } else {
            HttpEntity requestEntity = new HttpEntity(null, headers);
            responseEntity = restTemplate.exchange(appendParams(url, params.keySet()), method, requestEntity, clazz, params);
        }
        return responseEntity.getBody();
    }


    /**
     * 将参数追加到URL后边
     * @param url
     * @param keys
     * @return
     * @author: huangbaidong
     * @Description:
     * @return: String
     */
    private static String appendParams(String url, Set<?> keys) {
        StringBuilder sb = new StringBuilder(url);
        if(url.contains("?")) {
            sb.append("&");
        } else {
            sb.append("?");
        }
        for (Object key : keys) {
            sb.append(key).append("=").append("{").append(key).append("}").append("&");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

}
