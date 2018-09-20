package com.sida.dcloud.auth.service.impl;

import com.sida.dcloud.auth.common.SysEnums;
import com.sida.dcloud.auth.dao.SysOrgMapper;
import com.sida.dcloud.auth.dao.SysResourceMapper;
import com.sida.dcloud.auth.dao.SysRoleMapper;
import com.sida.dcloud.auth.dao.SysUserMapper;
import com.sida.dcloud.auth.po.SysOrg;
import com.sida.dcloud.auth.po.SysResource;
import com.sida.dcloud.auth.po.SysRole;
import com.sida.dcloud.auth.po.SysUser;
import com.sida.dcloud.auth.service.SysRoleService;
import com.sida.dcloud.auth.service.SysUserService;
import com.sida.dcloud.auth.vo.*;
import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.common.util.AuthCodeConstants;
import com.sida.xiruo.common.util.ErrorCodeEnums;
import com.sida.xiruo.util.jedis.RedisKey;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.exception.ServiceException;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BaseEntityUtil;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.BuildTree;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.sida.dcloud.auth.vo.UserDetailsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {
    private static final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysResourceMapper sysResourceMapper;
    @Autowired
    private SysOrgMapper sysOrgMapper;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public IMybatisDao<SysUser> getBaseDao() {
        return sysUserMapper;
    }

    @Override
    public UserDetails selectUserByFaceId(String faceId) {
        SysUser user = sysUserMapper.selectUserByFaceId(faceId);
        if (user != null) {
            List<SysRole> list = sysRoleMapper.selectRolesByUserId(user.getId());

            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            SimpleGrantedAuthority auth = null;
            list.stream().forEach(u->grantedAuthorities.add(new SimpleGrantedAuthority(u.getName())));
            return new UserDetailsVo(user,grantedAuthorities);
        } else {
            throw new ServiceException("10002", "用户不存在！");
        }

    }

    @Override
    public UserDetails selectUserByPhone(String phone) {
        SysUser user = sysUserMapper.selectUserByPhone(phone);
        if (user != null) {
            List<SysRole> list = sysRoleMapper.selectRolesByUserId(user.getId());

            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            SimpleGrantedAuthority auth = null;
            list.stream().forEach(u->grantedAuthorities.add(new SimpleGrantedAuthority(u.getName())));
            return new UserDetailsVo(user,grantedAuthorities);
        } else {
            throw new ServiceException("10002", "用户不存在！");
        }
    }

    @Override
    public SysUser selectUserByName(String name) {
        return sysUserMapper.selectUserByName(name);
    }

    @Override
    public Page<SysUserVo> findPageList(SysUser param) {
        SysUser condition = new SysUser();
        condition.setAccount(param.getAccount());
        condition.setName(param.getName());
        condition.setStatus(param.getStatus());
        PageHelper.startPage(param.getP(),param.getS());
        BaseEntityUtil.putSort(condition,param);
        List<SysUserVo> list = sysUserMapper.selectVoList(condition);
        return (Page) list;

    }

    @Override
    @Transactional(readOnly = false)
    public int saveOrUpdateUser(SysUserVo param) {
        if (super.saveOrUpdate(param) > 0){
            //删除用户原有角色
            sysUserMapper.deleteByUserId(param.getId());
            return sysUserMapper.addUserRoleRela(param.getId(),param.getRoleId());
        }
        return -1;
    }

    @Override
    public UserInfo getUserInfo(String userId, Boolean resFlag) {
        if (BlankUtil.isEmpty(userId)){
            userId = LoginManager.getCurrentUserId();
        }
        //获取基本信息
        UserInfo info = sysUserMapper.getUserInfo(userId);

        if (BlankUtil.isEmpty(info)){
            throw new ServiceException(ErrorCodeEnums.SELECT,"无法根据userId【"+userId+"】获取到用户信息！");
        }

        List<RoleDTO> roleList = sysRoleService.findRoleListByUserId(userId);
        info.setRoleList(roleList);

        addOtherOrgInfoByOrgPath(info,info.getOrgPath());

        if (resFlag){
            //获取资源信息
            List<SysResourceVo> resources = sysResourceMapper.findUserResources(userId);
            List<SysResourceVo> resourcesTree = BuildTree.buildTree(resources);

            List<String> menus = Lists.newArrayList();
            List<String> hrefs = Lists.newArrayList();
            Map<String,List<String>> resMap = new HashMap<>();
            if (BlankUtil.isNotEmpty(resourcesTree)){
                for (SysResourceVo vo : resourcesTree){
                    menus.add(vo.getCode());
                }
            }
            if (BlankUtil.isNotEmpty(resources)){
                for (SysResource po : resources){
                    if (BlankUtil.isNotEmpty(po.getHref())){
                        hrefs.add(po.getHref());
                    }
                }
            }
            buildResMap(resMap,null,resourcesTree);

            info.setResources(resourcesTree);
            info.setMenus(menus);
            info.setResMap(resMap);
            info.setHrefs(hrefs);
        }

        return info;
    }

    /**
     * 构建父子结构Map
     * @param resMap
     * @param resList
     */
    private void buildResMap(Map<String, List<String>> resMap, SysResourceVo parent, List<SysResourceVo> resList) {
        List<String> childCodes = Lists.newArrayList();
        if (BlankUtil.isNotEmpty(resList)){
            for (SysResourceVo res : resList){
                childCodes.add(res.getCode());
                if (BlankUtil.isNotEmpty(res.getChildren())){
                    buildResMap(resMap,res,res.getChildren());
                }else {
                    resMap.put(res.getCode(),Lists.newArrayList());
                }
            }
        }
        if (BlankUtil.isNotEmpty(parent)){
            resMap.put(parent.getCode(),childCodes);
        }
    }

    private void addOtherOrgInfoByOrgPath(UserInfo info, String orgPath) {
        if (StringUtils.isNotBlank(orgPath)){
            String[] orgs = orgPath.split(",");
            List<String> orgStrs = Arrays.asList(orgs);
            List<SysOrg> list = sysOrgMapper.findByIds(orgStrs);
            String storePath = "";
            String areaPath = "";
            String certPath = "";
            if (BlankUtil.isNotEmpty(list)){
                for (SysOrg org : list){
                    if (SysEnums.OrgTypeEnums.CERT.getType().equals(org.getType())){
                        if (org.getIdPath().length() > certPath.length()){
                            info.setCertId(org.getId());
                        }
                    }else if (SysEnums.OrgTypeEnums.AREA.getType().equals(org.getType())){
                        if (org.getIdPath().length() > areaPath.length()){
                            info.setAreaId(org.getId());
                        }
                    }else if (SysEnums.OrgTypeEnums.STORE.getType().equals(org.getType())){
                        if (org.getIdPath().length() > storePath.length()){
                            info.setStoreId(org.getId());
                        }
                    }
                }
                String deptName = (list.size()<2?"":list.get(1).getName())
                                    + "-" + (list.get(0)==null?"":list.get(0).getName());
                info.setDeptName(deptName);
            }
        }
    }

    @Override
    public boolean isValid(String code, String mobile, String reqType) {
        if (BlankUtil.isNotEmpty(code) && BlankUtil.isNotEmpty(mobile) && BlankUtil.isNotEmpty(reqType)) {
            Object validCode = null;
            switch (reqType) {
                case AuthCodeConstants.REQTYPE_REMOTE_REGISTER_1:
                    //redis验证
                    validCode = redisUtil.getOneByMapKey(RedisKey.SHORT_MSG_AUTH_CODE, RedisKey.REMOTE_REGISTER + mobile);
                    if (BlankUtil.isNotEmpty(validCode) && code.equals(validCode.toString())) {
                        // 失效验证码
                        redisUtil.removeOneFromMap(RedisKey.SHORT_MSG_AUTH_CODE, RedisKey.REMOTE_REGISTER + mobile);
                        return true;
                    }
                    break;
                case AuthCodeConstants.REQTYPE_REMOTE_LOGIN_2:
                    //redis验证
                    validCode = redisUtil.getOneByMapKey(RedisKey.SHORT_MSG_AUTH_CODE, RedisKey.REMOTE_LOGIN + mobile);
                    if (BlankUtil.isNotEmpty(validCode) && code.equals(validCode.toString())) {
                        // 失效验证码
                        redisUtil.removeOneFromMap(RedisKey.SHORT_MSG_AUTH_CODE, RedisKey.REMOTE_LOGIN + mobile);
                        return true;
                    }
                    break;
                default:
                    break;
            }
        }
        return false;
    }
}