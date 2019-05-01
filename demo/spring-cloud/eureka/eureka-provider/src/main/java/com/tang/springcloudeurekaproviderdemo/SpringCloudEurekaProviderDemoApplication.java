package com.tang.springcloudeurekaproviderdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableDiscoveryClient
//可以使用@EnableEurekaClient代替@EnableDiscoveryClient，
// 但是@EnableEurekaClient只能使用Eureka的实现
// 而@EnableDiscoveryClient还支持consual
public class SpringCloudEurekaProviderDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudEurekaProviderDemoApplication.class, args);
	}

}
