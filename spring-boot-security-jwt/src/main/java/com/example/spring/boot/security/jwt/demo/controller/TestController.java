package com.example.spring.boot.security.jwt.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController:
 *
 * @author jzheng
 * @since 2021/1/1 2:05 上午
 */
@RestController
public class TestController {

    /**
     * 测试接口 url 做权限 /normal/test
     *
     * @return return the tip string
     */
    @PreAuthorize("hasAuthority('/normal/test')")
    @RequestMapping(value = "/normal/test", method = RequestMethod.GET)
    public String test1() {
        return "ROLE_NORMAL /normal/test接口调用成功！";
    }

    /**
     * 测试接口 url 做权限 /admin/test
     *
     * @return return this tip string
     */
    @PreAuthorize("hasAuthority('/admin/test')")
    @RequestMapping(value = "/admin/test", method = RequestMethod.GET)
    public String test2() {
        return "ROLE_ADMIN /admin/test接口调用成功！";
    }
}
