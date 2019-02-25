package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

//    @Resource
//    private UserMapper userMapper;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);


    public void test(){
        logger.info("测试。。。。");
    }


}
