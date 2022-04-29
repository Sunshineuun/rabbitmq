package com.qiusm.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.Resource;

/**
 * @author qiushengming
 */
@Slf4j
public class RabbitmqTemplateTest extends RabbitmqApplicationTests {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    void template() {
        log.info("{}", rabbitTemplate);
    }

    @Test
    void send() {
        String exchange = "testDirectExchange";
        String routingkey = "testRoutingkey";
        rabbitTemplate.convertAndSend(exchange, routingkey, "testMessage");
    }
}
