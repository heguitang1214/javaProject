package com.tang.product.service;

import com.tang.entity.Order;
import org.springframework.amqp.core.Message;

import com.rabbitmq.client.Channel;


/**
 * @author hegeitang
 * @createTime 2018年7月24日 上午11:48:27
 * 产品服务接口
 */
public interface IProductService {

    int updateProduct(Order order) throws Exception;

    void updateProduct4MQ(Order order, Channel channel, Message message) throws Exception;
}
