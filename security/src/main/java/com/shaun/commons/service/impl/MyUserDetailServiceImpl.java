package com.shaun.commons.service.impl;

import com.shaun.commons.dao.IDao;
import com.shaun.commons.entity.AppRole;
import com.shaun.commons.entity.MyUser;
import com.shaun.commons.util.AppException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author
 * @Description
 * @Date Created on 2017/9/8.
 */
public class MyUserDetailServiceImpl extends BaseDaoServiceImpl implements UserDetailsService {
    public MyUserDetailServiceImpl(){

    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Map resMap = (Map)this.getAppDao().queryForObject("login.queryByUserName",s);
        if(resMap.isEmpty()){
            throw new UsernameNotFoundException("没有找到用户信息");
        }else {
            MyUser user = new MyUser();
            user.setUserName(resMap.get("username")+"");
            user.setPassword(resMap.get("password")+"");
            AppRole appRole = new AppRole();
            appRole.setRoleName(AppRole.ROLE_USER);
            Set set = new HashSet();
            set.add(appRole);
            user.setRoles(set);
            return user;
        }
    }
}
