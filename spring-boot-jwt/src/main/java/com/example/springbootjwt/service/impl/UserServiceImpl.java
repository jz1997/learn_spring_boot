package com.example.springbootjwt.service.impl;

import com.example.springbootjwt.entity.User;
import com.example.springbootjwt.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean login(User user) {
        return "admin".equals(user.getUsername()) && "123456".equals(user.getPassword());
    }
}
