package com.tang.springcloudribbondemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
//import okhttp3.OkHttpClient;

@SpringBootApplication
public class SpringCloudRibbonDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudRibbonDemoApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

//	@Bean
//	@LoadBalanced
//	public RestTemplate restTemplate(){
//		return new RestTemplate(new OkHttp3ClientHttpRequestFactory(okHttpClient()));
//	}

//	/**
//     * http client
//     * @return okHttp
//     */
//    @Bean
//    public OkHttpClient okHttpClient() {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.connectTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(10,TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true);
//        return builder.build();
//    }
}
