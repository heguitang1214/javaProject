package rabbitMQ.mqDelayDemo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rabbitMQ.utils.CalendarUtils;

import java.io.IOException;

/**
 * 监听rabbitmq中的死信队列
 */
@Component
public class MQBusiness {

    @RabbitListener(queues = MQProperties.DEAD_QUEUE_NAME)
    public void process(String message) {
        System.out.println(CalendarUtils.getCurrentTimeByStr(0) + " 消费了一个超时订单，订单ID：" + message);
    }
}
