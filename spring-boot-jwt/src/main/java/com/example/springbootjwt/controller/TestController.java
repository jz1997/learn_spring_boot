package com.example.springbootjwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController:
 * 测试接口
 * @author jzheng
 * @since 2020/12/29 10:45 下午
 */
@RestController
public class TestController {

    @GetMapping("/test/login")
    public String login() {
        return "login ok !";
    }

}
