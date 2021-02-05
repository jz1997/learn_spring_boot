package com.example.spring.boot.security.jwt.demo.controller;

import com.example.spring.boot.security.jwt.demo.model.entity.User;
import com.example.spring.boot.security.jwt.demo.service.AuthService;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * JwtAuthController:
 *
 * @author jzheng
 * @since 2021/1/1 1:19 上午
 */
@RestController
public class JwtAuthController {

    @Resource
    private AuthService authService;

    /**
     * 登录
     */
    @RequestMapping(value = "/authentication/login", method = RequestMethod.POST)
    public String createToken(String username, String password) throws AuthenticationException {
        return authService.login(username, password);
    }

    /**
     * 注册
     */
    @RequestMapping(value = "/authentication/register", method = RequestMethod.POST)
    public User register(@RequestBody User addedUser) throws AuthenticationException {
        return authService.register(addedUser);
    }

}
