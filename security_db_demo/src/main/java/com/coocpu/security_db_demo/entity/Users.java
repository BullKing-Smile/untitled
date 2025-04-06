package com.coocpu.security_db_demo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * users
 */
@Data
public class Users implements UserDetails, Serializable {

    private Long id;

    private String username;

    // json 格式不返回
    @JsonIgnore
    private String password;

    @JsonIgnore
    private List<Role> roles;

    @JsonIgnore
    private List<Permission> permissions;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    private Date lastLogin;

    private Integer enabled;

    private static final long serialVersionUID = 1L;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return AuthorityUtils.NO_AUTHORITIES;
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // 添加角色
        if (null != roles) {
            for (Role role : roles) {
                // 添加角色 固定前缀ROLE_
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            }
        }
        // 添加资源权限
        if (null != permissions) {
            for (Permission permission : permissions) {
                authorities.add(new SimpleGrantedAuthority( permission.getCode()));
            }
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled == 1;
    }

    @Override
    public boolean isEnabled() {
        return enabled == 1;
    }
}