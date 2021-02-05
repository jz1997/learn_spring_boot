package com.example.spring.security.jwt.demo;

import com.alibaba.fastjson.JSON;
import com.example.spring.boot.security.jwt.demo.RunApp;
import com.example.spring.boot.security.jwt.demo.model.entity.User;
import com.example.spring.boot.security.jwt.demo.repository.UserRepository;
import com.google.common.collect.Lists;
import io.jsonwebtoken.lang.Assert;
import javax.annotation.Resource;
import net.bytebuddy.build.ToStringPlugin.Enhance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = RunApp.class)
@RunWith(SpringRunner.class)
@Transactional
@Rollback
public class UserRepositoryTests {

    @Resource
    private UserRepository userRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    public void saveUser() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        User returnUser = userRepository.save(user);
        Assert.notNull(returnUser);
    }

    @Test
    public void testFindAllByPage() {
        Page<User> userPage = userRepository
            .findAll(PageRequest.of(0, 10));
        System.out.println(userPage.getContent().size());
        System.out.println(JSON.toJSONString(userPage, true));
    }

    @Test
    public void testFindByUsername() {
        User findUser = userRepository.findByUsername("admin");
        Assert.notNull(findUser);
        System.out.println(findUser.getUsername());
    }

    @Test
    public void testPasswordEncoder() {
        String admin = this.passwordEncoder.encode("admin");
        System.out.println(admin);
    }
}
