package com.example.spring.boot.security.jwt.demo.service.impl;

import com.example.spring.boot.security.jwt.demo.repository.UserRepository;
import javax.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * UserService:
 *
 * @author jzheng
 * @since 2021/1/1 12:04 上午
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
