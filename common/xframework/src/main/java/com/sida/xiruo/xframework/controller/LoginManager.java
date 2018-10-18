package com.sida.xiruo.xframework.controller;

import com.sida.dcloud.auth.vo.RoleDTO;
import com.sida.dcloud.auth.vo.SysUserVo;
import com.sida.xiruo.xframework.util.BeanCovertUtil;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.google.common.collect.Lists;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * LoginManager
 * @author jianglingfeng
 * @date 2007-11-9
 * @version
 */
public final class LoginManager {

    /**
     * 未登录返回空的sysUser对象
     * @return
     */
    public static SysUserVo getUser(){
        SysUserVo user = new SysUserVo();
        try{
            user.setId("0");
            user.setOrgId("0");
            if(SecurityContextHolder.getContext().getAuthentication() == null) {
                return user;
            }
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
//            Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
//                    .map(auth -> auth.getDetails()).ifPresent(details -> {
                String token = details.getTokenValue();
                JsonParser jsonParser = JsonParserFactory.create();
                if (token != null) {
                    Map<String, Object> map = jsonParser.parseMap(JwtHelper.decode(token).getClaims());
                    user.setId((String) map.get("user_id"));
                    user.setOrgId((String) map.get("org_id"));
                    user.setName(map.get("name") + "");

                    user.setUserAccount(map.get("userAccount") == null ? "" : map.get("userAccount").toString());
                    user.setRoleId(map.get("roleId") == null ? "" : map.get("roleId").toString());
                    user.setRoleCode(map.get("roleCode") == null ? "" : map.get("roleCode").toString());
                    user.setRoleName(map.get("roleName") == null ? "" : map.get("roleName").toString());
                    user.setOrganizationId(map.get("organizationId") == null ? "" : map.get("organizationId").toString());
                    user.setOrganizationPath(map.get("organizationPath") == null ? "" : map.get("organizationPath").toString());
                    user.setAreaId(map.get("areaId") == null ? "" : map.get("areaId").toString());
                    user.setStoreId(map.get("storeId") == null ? "" : map.get("storeId").toString());
                    user.setCertId(map.get("certId") == null ? "" : map.get("certId").toString());

                    user.setPermissionLevel(map.get("permissionLevel") == null ? 0 : Integer.valueOf(map.get("permissionLevel").toString()));
                    user.setPermissionOrgId(map.get("permissionOrgId") == null ? "" : map.get("permissionOrgId").toString());

                    List<LinkedHashMap> roleListMap = map.get("roleList") == null ? null : (List) map.get("roleList");
                    List<RoleDTO> roleList = Lists.newArrayList();
                    if (BlankUtil.isNotEmpty(roleListMap)) {
                        for (LinkedHashMap hashMap : roleListMap) {
                            RoleDTO dto = (RoleDTO) BeanCovertUtil.convertMap(RoleDTO.class, hashMap);
                            roleList.add(dto);
                        }
                    }
                    user.setRoleList(roleList);
                }
//            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public static String getCurrentUserId(){
        return getUser().getId();
    }

    public static String getCurrentOrgId(){
        return getUser().getOrgId();
    }

    public static String getCurrentUserName(){
        return getUser().getName();
    }

    public static String getCurrentOrganizationPath(){
        return getUser().getOrganizationPath();
    }
}
