package rabbitMQ.mqDelayDemo;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * 初始化队列、交换器及绑定
 */
@Component
@Configuration
public class MQConfig {

	//任务交换器
	@Bean(name="orderExchange")
	public TopicExchange exchange(){
		return new TopicExchange(MQProperties.EXCHANGE_NAME);
	}

	//任务队列
	@Bean(name="orderQueue")
	public Queue queueMessage(){
		Map<String, Object> args = new HashMap<>();
		args.put("x-message-ttl", 10000); //设置消息在MQ中的过期时间 10s
		args.put("x-dead-letter-exchange", MQProperties.DEAD_EXCHANGE_NAME); //绑定死信交换器
		return new Queue(MQProperties.QUEUE_NAME, true, false, false, args);
	}

	//死信交换器
	@Bean(name="orderExchange4Dead")
	public FanoutExchange exchange4Dead(){
		return new FanoutExchange(MQProperties.DEAD_EXCHANGE_NAME);
	}

	//死信队列
	@Bean(name="orderDeadQueue")
	public Queue deadQueueMessage(){
		return new Queue(MQProperties.DEAD_QUEUE_NAME);
	}

	//任务交换器与任务队列绑定
	@Bean
	public Binding bindingExchangeMessage(@Qualifier("orderQueue") Queue queueMessage, @Qualifier("orderExchange") TopicExchange exchange){
		return BindingBuilder.bind(queueMessage).to(exchange).with(MQProperties.ROUTE_KEY);
	}

	//死信交换器与死信队列绑定
	@Bean
	public Binding bindingExchangeMessage4Dead(@Qualifier("orderDeadQueue") Queue queueMessage, @Qualifier("orderExchange4Dead") FanoutExchange exchange){
		return BindingBuilder.bind(queueMessage).to(exchange);
	}
}
