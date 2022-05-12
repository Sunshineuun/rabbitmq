package com.qiusm.rabbitmq.demo.exchange.simple;

import com.qiusm.rabbitmq.demo.exchange.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * 生产者
 *
 * @author qiushengming
 */
public class Producer {

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            // 1: 创建连接工程
            ConnectionFactory connectionFactory = RabbitMQUtils.createConnectionFactory();
            // 2: 创建连接-Connection；RabbitMQ为什么基于channel去处理而不是链接？长链接，短链接，三次握手，三次挥手。TODO
            connection = connectionFactory.newConnection("simple-producer");
            // 3: 通过连接获取通道-Channel
            channel = connection.createChannel();
            // 4: 通过通道创建交换机，声明队列，绑定关系，路由Key，发送消息，接收消息
            /*
             * @params1 队列名称
             * @params2 是否持久化【durable=false】。所谓持久化是否指消息的存盘，如果false非持久化， true是持久化？非持久化回存盘嘛？
             * 持久化队列和非持久化队列的区别：1. 非持久化队列会被系统自动移除。2. 非持久化队列会存盘，但是会随着服务器的重启而丢失。
             * @params3 排他性，是否是独占队列。
             * @params4 是否自动删除。随着最后一个消费者消息消费完毕以后是否把队列自动删除。
             * @params5 携带附属参数
             */
            String queueName = "simple-queue1";
            channel.queueDeclare(queueName, false, false, false, null);
            // 5: 准备消息内容
            String msg = "Hello lmz";
            // 6: 发送消息给队列-Queue MessageProperties
            /*
             * @params exchange 交换机，可以是空的，会有一个默认的交换机。交换机，有点mysql中的database
             *      - 交换机类型：direct/fanout/
             */
            channel.basicPublish("", queueName, null, msg.getBytes(StandardCharsets.UTF_8));
            // 7: 关闭连接
            // 8: 关闭通道
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
