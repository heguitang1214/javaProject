package com.tang.sharding.service.impl;

import com.tang.sharding.mapper.UserMapper;
import com.tang.sharding.model.User;
import com.tang.sharding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void save(User user) {
        userMapper.save(user);
    }
}
