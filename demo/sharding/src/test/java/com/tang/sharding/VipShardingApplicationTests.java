package com.tang.sharding;

import com.tang.sharding.model.User;
import com.tang.sharding.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
public class VipShardingApplicationTests {

    @Autowired
    UserService userService;

    @Test
    public void contextLoads() {
        User user = new User();
        user.setName("wangwu");
        userService.save(user);
    }

}

