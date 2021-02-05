package com.example.spring.boot.security.jwt.demo.service;

import com.example.spring.boot.security.jwt.demo.model.entity.User;

/**
 * AuthService: 认证服务接口
 *
 * @author jzheng
 * @since 2021/1/1 12:57 上午
 */
public interface AuthService {

    /**
     * 注册
     * @param userToAdd {@link User}
     * @return 用户信息 {@link User}
     */
    User register(User userToAdd);

    /**
     * 登录
     * @param username username
     * @param password password
     * @return 生成的 token
     */
    String login(String username, String password);
}