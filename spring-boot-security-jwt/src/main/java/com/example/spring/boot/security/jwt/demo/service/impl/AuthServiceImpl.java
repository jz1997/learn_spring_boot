package com.example.spring.boot.security.jwt.demo.service.impl;

import com.example.spring.boot.security.jwt.demo.model.entity.User;
import com.example.spring.boot.security.jwt.demo.repository.UserRepository;
import com.example.spring.boot.security.jwt.demo.service.AuthService;
import com.example.spring.boot.security.jwt.demo.util.JwtTokenUtil;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthServiceImpl:
 *
 * @author jzheng
 * @since 2021/1/1 1:00 上午
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(User userToAdd) {
        // 用户已存在
        if (Objects.nonNull(userRepository.findByUsername(userToAdd.getUsername()))) {
            return null;
        }
        userToAdd.setPassword(passwordEncoder.encode(userToAdd.getPassword()));
        return userRepository.save(userToAdd);
    }

    @Override
    public String login(String username, String password) {
        // 进行验证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
            = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager
            .authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 生成 token
        UserDetails userDetails = userRepository.findByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }
}
