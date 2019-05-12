package com.tang.sharding;

import com.tang.sharding.model.Province;
import com.tang.sharding.service.ProvinceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
public class BroadcastTableApplicationTests {
    @Autowired
    private ProvinceService provinceService;

    @Test
    public void test() {
        Province pro = new Province();
        pro.setId(222);
        pro.setName("上海");
        provinceService.save(pro);
    }
}
