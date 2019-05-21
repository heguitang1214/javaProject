package com.tang.utils;

import com.tang.service.ITicketService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2018年10月26日 下午4:36:38
 * 
 */
@Component
@RabbitListener(queues=MQProperties.QUEUE_NAME)
public class MQBusiness {

	@Autowired
	private ITicketService ticketService;
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@RabbitHandler
	public void process(String userId){
		String result = ticketService.createTicket(userId);
		amqpTemplate.convertAndSend(MQProperties.EXCHANGE_NAME, MQProperties.ROUTE_KEY_RESP, result);
	}
}
