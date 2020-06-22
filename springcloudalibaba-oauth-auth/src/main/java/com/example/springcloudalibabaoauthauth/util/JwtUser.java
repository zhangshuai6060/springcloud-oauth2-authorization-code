package com.example.springcloudalibabaoauthauth.util;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
public class JwtUser extends User {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     *账号
     */
    private  boolean accountNonExpired;
    /**
     *账号是否被锁定
     */
    private  boolean accountNonLocked;
    /**
     * 帐号是否过期
     */
    private  boolean credentialsNonExpired;
    /**
     * 帐号是否被禁用
     */
    private  boolean enabled;

    /**
     * 构造方法
     * @param username
     * @param password
     * @param authorities
     * @param accountNonExpired
     * @param accountNonLocked
     * @param credentialsNonExpired
     * @param enabled
     */
    public JwtUser(String username, String password, Collection<? extends GrantedAuthority> authorities,  boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        super(username, password, authorities);
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }


    /**
     * 让boolean的都变为true
     * @param username 用户名
     * @param password 密码
     * @param authorities  权限
     */
    public JwtUser(String username, String password,
                Collection<? extends GrantedAuthority> authorities) {
        this(username, password, authorities, true, true, true,true );
    }

}

