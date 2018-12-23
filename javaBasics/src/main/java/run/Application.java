package run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//指定扫描的包，springBooboot默认的扫描包为：com.example.demo.xxx
//默认所扫描Application.class所在的包及其子包，与SpringApplication.run()中的参数有关
//@ComponentScan("controller")
@ComponentScan(basePackages = {"controller", "spring.aop"})//"spring.aopAnnotation.aop",
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

