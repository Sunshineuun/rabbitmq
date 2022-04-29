package com.qiusm.rabbitmq;

import com.qiusm.rabbitmq.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author qiushengming
 */
@RequestMapping("rabbit")
@RestController
public class RabbitController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/seed")
    public void seed() {
        for (int i = 0; i < 100; i++) {
            rabbitTemplate.convertAndSend(RabbitConfig.testDirectExchange, RabbitConfig.testRoutingKey, "test-msg" + i);
        }
    }
}
