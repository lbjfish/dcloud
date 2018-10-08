package com.sida.dcloud.auth.service.impl;

import com.sida.dcloud.auth.vo.UserDetailsVo;
import com.sida.dcloud.auth.dao.SysRoleMapper;
import com.sida.dcloud.auth.dao.SysUserMapper;
import com.sida.dcloud.auth.po.SysRole;
import com.sida.dcloud.auth.po.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl /*extends SysUserServiceImpl*/ implements UserDetailsService {
   // private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser user = sysUserMapper.selectUserByName(username);
        if (user != null){
            List<SysRole> list = sysRoleMapper.selectRolesByUserId(user.getId());

            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            list.forEach(role->grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));
            return new UserDetailsVo(user,grantedAuthorities);
        }else {
            throw new UsernameNotFoundException(String.format("User %s does not exist",username));
        }

    }


   /* @Override
    public IMybatisDao<SysUser> getBaseDao() {
        return sysUserMapper;
    }*/

   /* @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       logger.info("============"+sysUserMapper.selectAll().get(0).getName());
        Optional<SysUser> optional = Optional.ofNullable(sysUserMapper.selectUserByName(username));
        logger.info("=============="+sysUserMapper.selectUserByName(username));
        try {
            optional.orElseThrow(ServiceException::new);
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            optional.map(user -> sysRoleMapper.selectRolesByUserId(user.getId()))
                    .ifPresent(roleList -> roleList.stream().forEach(
                            role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getCode()))
                    ));
            return new UserDetailsVo(optional.get(), grantedAuthorities);
        } catch(ServiceException e) {
            throw new UsernameNotFoundException(String.format("User %s does not exist", username));
        }
    }*/
}