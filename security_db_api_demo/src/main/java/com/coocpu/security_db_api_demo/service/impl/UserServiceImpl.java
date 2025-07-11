package com.coocpu.security_db_api_demo.service.impl;

import com.coocpu.security_db_api_demo.entity.Users;
import com.coocpu.security_db_api_demo.mapper.PermissionDao;
import com.coocpu.security_db_api_demo.mapper.RoleDao;
import com.coocpu.security_db_api_demo.mapper.UsersMapper;
import com.coocpu.security_db_api_demo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @auth Felix
 * @since 2025/3/29 17:39
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UsersMapper usersMapper;
    @Resource
    private RoleDao roleDao;

    @Resource
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersMapper.selectByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException("The user " + username + "not found!");
        }

        user.setRoles(roleDao.selectByUserid(user.getId()));
        user.setPermissions(permissionDao.selectByUserid(user.getId()));

        return user;
    }
}
