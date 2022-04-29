package com.qiusm.rabbitmq.demo.exchange.fanout;

import com.qiusm.rabbitmq.demo.exchange.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消费者
 *
 * @author qiushengming
 */
public class Consumer extends Producer {

    public static Runnable runnable = () -> {
        //获取队列的名称
        final String queueName = Thread.currentThread().getName();
        Connection connection = null;
        Channel channel = null;
        try {
            // 1: 创建连接工程
            ConnectionFactory connectionFactory = RabbitMQUtils.createConnectionFactory();
            // 2: 创建连接-Connection
            connection = connectionFactory.newConnection("simple-consumer");
            // 3: 通过连接获取通道-Channel
            channel = connection.createChannel();
            // 3.1: 声明队列
            channel.queueDeclare(queueName, false, false, false, null);
            // 3.2: 绑定交换机
            channel.queueBind(queueName, EXCHANGE_NAME, "");
            // 3.3: 保障只分发一次
            channel.basicQos(1);
            // 4:
            channel.basicConsume(queueName, true,
                    (s, delivery) -> System.out.println("收到的消息是:" + queueName + new String(delivery.getBody(), "UTF-8")),
                    s -> System.out.println("接收失败."));
        } catch (Exception e) {
            e.printStackTrace();
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
    };

    public static void main(String[] args) {
        // 启动三个线程去执行
        new Thread(runnable, "queue-1").start();
        new Thread(runnable, "queue-2").start();
        new Thread(runnable, "queue-3").start();
    }
}
