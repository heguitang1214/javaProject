package com.zero.utils;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2018年10月26日 下午4:36:38
 * 
 */
@Component
@RabbitListener(queues=MQProperties.QUEUE_NAME_RESP)
public class MQBusiness {

	@RabbitHandler
	public void process(String message) throws Exception {
		System.out.println(message);
	}

}
