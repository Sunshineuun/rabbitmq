package com.qiusm.rabbitmq.demo.exchange.simple;

import com.qsm.rabbitmq.RabbitMQUtils;
import com.rabbitmq.client.*;

/**
 * 消费者
 *
 * @author qiushengming
 */
public class Consumer extends Producer {

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            // 1: 创建连接工程
            ConnectionFactory connectionFactory = RabbitMQUtils.createConnectionFactory();
            // 2: 创建连接-Connection
            connection = connectionFactory.newConnection("simple-consumer");
            // 3: 通过连接获取通道-Channel
            channel = connection.createChannel();
            // 4:
            channel.basicConsume("simple-queue1", true,
                    (s, delivery) -> System.out.println("收到的消息是:" + new String(delivery.getBody(), "UTF-8")),
                    s -> System.out.println("接收失败."));
        } catch (Exception e) {
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception ignored) {

                }
            }

            if (connection != null && connection.isOpen()) {
                try {
                    connection.close();
                } catch (Exception ignored) {

                }
            }
        }
    }
}
