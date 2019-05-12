package com.tang.sharding;

import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 雪花算法：
 *      测试雪花算法生成主键ID
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
public class KeyGeneratorApplicationTests {

    @Test
    public void contextLoads() {
        DefaultKeyGenerator keyGenerator = new DefaultKeyGenerator();
        for (int i = 0; i < 20; i++) {
            System.out.println(keyGenerator.generateKey());
        }
    }

}

