import com.tang.demo.controller.Payment;
import com.tang.demo.controller.PaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试能不能从zk配置中心获取数据
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AppTest {
    @Autowired
    private PaymentService service;

    @Test
    public void test() {
        Payment p = service.getById();
        System.out.println(p.getBrandId() + ", " + p.getProductId() + ", " + p.getPayPrice());
    }
}
