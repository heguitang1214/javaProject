package ai.yunxi.sharding.service.impl;

import ai.yunxi.sharding.mapper.UserMapper;
import ai.yunxi.sharding.model.User;
import ai.yunxi.sharding.service.UserService;
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
