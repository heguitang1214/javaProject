package rabbitMQ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("rabbitMQ")
public class DelayMessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(DelayMessageApplication.class, args);
	}
}
