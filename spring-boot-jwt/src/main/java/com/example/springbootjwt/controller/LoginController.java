package com.example.springbootjwt.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springbootjwt.entity.User;
import com.example.springbootjwt.service.UserService;
import com.example.springbootjwt.util.JwtUtils;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoginController:
 *
 * @author jzheng
 * @since 2020/12/30 5:47 下午
 */
@RestController
public class LoginController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        boolean login = userService.login(user);
        Map<String, Object> result = new HashMap<>(3);
        if (login) {
            Map<String, String> claims = new HashMap<>(2);
            claims.put("username", user.getUsername());
            String token = JwtUtils.sign(claims);

            result.put("code", 0);
            result.put("msg", "登陆成功");
            result.put("token", token);
        } else {
            result.put("code", -1);
            result.put("msg", "登陆失败");
            result.put("data", null);
        }
        return result;
    }

    @GetMapping("/request/data")
    public Map<String, Object> requestData() {
        Map<String, Object>  result = new HashMap<>(2);
        result.put("code", 0);
        result.put("msg", "hello world");
        return result;
    }
}
