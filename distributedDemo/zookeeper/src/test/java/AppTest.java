import com.zero.demo.controller.Payment;
import com.zero.demo.controller.PaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AppTest {
	@Autowired
	private PaymentService service;

	@Test
	public void test() {
		Payment p = service.getById();
		System.out.println(p.getBrandId() + ", " + p.getProductId() + ", " + p.getPayPrice());
	}
}
