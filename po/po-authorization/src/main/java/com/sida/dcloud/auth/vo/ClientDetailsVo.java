package com.sida.dcloud.auth.vo;

import com.sida.dcloud.auth.po.SysClientDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

/**
 * Created by Xiruo on 2017/7/21.
 */
public class ClientDetailsVo implements ClientDetails {

    private static final long serialVersionUID = 1L;
    private String clientId;
    private String clientSecret;
    private String scope;
    private String authorizedGrantTypes;
    private String webServerRedirectUri;
    private String authorities;
    private Integer accessTokenValidity;
    private Integer refreshTokenValidity;
    private String additionalInformation;
    private String autoapprove;

    public ClientDetailsVo(SysClientDetail client) {
        BeanUtils.copyProperties(client, this);
    }

    public ClientDetailsVo() {
    }

   /* public ClientDetailsVo(SysClientDetail client) {
        this.clientId = client.getClientId();
        this.clientSecret = client.getClientSecret();
        this.scope = client.getScope();
        this.authorizedGrantTypes = client.getAuthorizedGrantTypes();
        this.webServerRedirectUri = client.getWebServerRedirectUri();
        this.authorities = client.getAuthorities();
        this.accessTokenValidity = client.getAccessTokenValidity();
        this.refreshTokenValidity = client.getRefreshTokenValidity();
        this.additionalInformation = client.getAdditionalInformation();
        this.autoapprove = client.getAutoapprove();
    }*/

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        Set<String> set = new HashSet<>();
        return set;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        Set<String> set = new HashSet<>();
        if (this.scope != null){
            List list = Arrays.asList(this.scope.split(","));
            set.addAll(list);
        }
        return set;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        Set<String> set = new HashSet<>();
        if (this.authorizedGrantTypes != null){
            List list = Arrays.asList(this.authorizedGrantTypes.split(","));
            set.addAll(list);
        }
        return set;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<GrantedAuthority>();
        return collection;
    }


    @Override
    public Integer getAccessTokenValiditySeconds() {
        return this.accessTokenValidity;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return this.refreshTokenValidity;
    }

    @Override
    public boolean isAutoApprove(String s) {
        boolean b = false;
        if (s.equals("1")){
            b=true;
        }else{
            b=false;
        }
        return b;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        Map<String,Object> addinfo = new HashMap<>();
        if (this.additionalInformation != null){
            addinfo.put("Additional_information", this.additionalInformation);
        }
        return addinfo;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getWebServerRedirectUri() {
        return webServerRedirectUri;
    }

    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getAutoapprove() {
        return this.autoapprove;
    }

    public void setAutoapprove(String autoapprove) {
        this.autoapprove = autoapprove;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
