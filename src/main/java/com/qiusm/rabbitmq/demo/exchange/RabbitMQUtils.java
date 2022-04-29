package com.qiusm.rabbitmq.demo.exchange;

import com.rabbitmq.client.ConnectionFactory;

/**
 * 工具类
 *
 * @author qiushengming
 */
public class RabbitMQUtils {
    public static ConnectionFactory createConnectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("qiusm");
        connectionFactory.setPassword("liumengzhen");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }
}
