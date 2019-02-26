package ai.yunxi.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("ai.yunxi.sharding.mapper")
public class ShardingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShardingApplication.class, args);
	}

}

