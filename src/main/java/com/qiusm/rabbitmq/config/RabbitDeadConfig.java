package com.qiusm.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 死信队列配置
 *
 * @author qiushengming
 */
// @Configuration
public class RabbitDeadConfig {

    /**
     * 1: 声明注册fanout模式的交换机
     */
    @Bean("directDeadExchange")
    public DirectExchange directExchange() {
        return new DirectExchange("direct_dead_order_exchange", true, false);
    }

    @Bean("directDeadQueue")
    public Queue directQueue() {
        return new Queue("direct_dead_queue_test1", true);
    }

    @Bean("directDeadBinding")
    public Binding directBinding(
            @Autowired @Qualifier("directDeadExchange") DirectExchange exchange,
            @Autowired @Qualifier("directDeadQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("");
    }
}