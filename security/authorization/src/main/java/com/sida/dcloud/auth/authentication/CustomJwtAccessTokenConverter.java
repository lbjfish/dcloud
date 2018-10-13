package com.sida.dcloud.auth.authentication;

import com.sida.dcloud.auth.service.SysUserService;
import com.sida.dcloud.auth.vo.RoleDTO;
import com.sida.dcloud.auth.vo.UserDetailsVo;
import com.sida.dcloud.auth.vo.UserInfo;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

    @Autowired
    private SysUserService sysUserService;

    @Value("${qiniu.defaultHeader}")
    private String defaultHeader;

    @PostConstruct
    private void init() {
        KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("sida.jks"), "sidasida".toCharArray())
                .getKeyPair("sida");
        setKeyPair(keyPair);
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        UserDetailsVo userDetailsVo = (UserDetailsVo)authentication.getUserAuthentication().getPrincipal();
        final Map<String,Object> additionInfo = new HashMap<>();
        UserInfo info = sysUserService.getUserInfo(userDetailsVo.getId(),false);
        additionInfo.put("id",userDetailsVo.getId());
//        additionInfo.put("org_id",userDetailsVo.getOrgId());
        additionInfo.put("name",userDetailsVo.getName());
        List<String> roleList = Lists.newArrayList();
        if (BlankUtil.isNotEmpty(info.getRoleList())){
            for (RoleDTO dto : info.getRoleList()){
                roleList.add(dto.getRoleCode());
            }
        }else {
            roleList.add(info.getRoleCode());
        }
        additionInfo.put("role",roleList);
//        additionInfo.put("avatar",defaultHeader);//头像，功能暂无
        additionInfo.put("header",defaultHeader);//头像，功能暂无

        //其他信息
        additionInfo.put("userAccount",info.getUserAccount());
//        additionInfo.put("roleId",info.getRoleId());
//        additionInfo.put("roleCode",info.getRoleCode());
//        additionInfo.put("roleName",info.getRoleName());
        //拓展角色组
        additionInfo.put("roleList",info.getRoleList());

//        additionInfo.put("organizationId",info.getOrgId());
//        additionInfo.put("organizationPath",info.getOrgPath());
//        additionInfo.put("areaId",info.getAreaId());
//        additionInfo.put("storeId",info.getStoreId());
//        additionInfo.put("certId",info.getCertId());
//        additionInfo.put("deptName",info.getDeptName());

        //置入权限相关信息
//        additionInfo.put("permissionLevel",0);
//        additionInfo.put("permissionOrgId",null);
//        if (BlankUtil.isNotEmpty(info.getStoreId())){
//            additionInfo.put("permissionLevel",2);
//            additionInfo.put("permissionOrgId",info.getStoreId());
//        }else if (BlankUtil.isNotEmpty(info.getAreaId())){
//            additionInfo.put("permissionLevel",1);
//            additionInfo.put("permissionOrgId",info.getAreaId());
//        }
        additionInfo.put("data","");
        additionInfo.put("status",true);
        additionInfo.put("code",200);
        additionInfo.put("message","");
        additionInfo.put("total",0);

//                additionInfo.put("positionId",info.getPositionId());
//                additionInfo.put("positionName",info.getPositionName());
//                additionInfo.put("phone",info.getPhone());
//                additionInfo.put("idCardNum",info.getIdCardNum());
//                additionInfo.put("status",info.getStatus());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionInfo);
        OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);
        return  enhancedToken;
    }
}