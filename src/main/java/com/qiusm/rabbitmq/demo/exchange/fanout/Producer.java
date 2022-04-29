package com.qiusm.rabbitmq.demo.exchange.fanout;

import com.qsm.rabbitmq.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 生产者
 * 1. 交换机（exchange）与队列（queue）的绑定，可以通过图形化界面完成；也可以通过代码完成。个人感觉可以通过图形化界面完成，因为这样能减少代码量，但是增加了对代码逻辑理解困难度。
 *
 * @author qiushengming
 */
public class Producer {

    protected final static String EXCHANGE_NAME = "fanout-exchange";

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
            // 5: 准备消息内容
            String msg = "Hello lmz";
            // 6: 发送消息给队列-Queue MessageProperties
            // headers是对 headers模式的支持
            Map<String, Object> headers = new HashMap<>(4);
            headers.put("x", "1");
            headers.put("y", "1");
            // 配置一些信息
            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                    .deliveryMode(2) // 传送方式
                    .priority(1)
                    .contentEncoding("UTF-8") // 编码方式
                    .expiration("5000") // 过期时间
                    .headers(headers).build();
            /*
             * @params exchange 交换机，可以是空的，会有一个默认的交换机。交换机，有点mysql中的database
             *      - 交换机类型：direct/fanout/
             * @params routingKey 路由key，跟队列名的含义有点类似。
             */
            channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes(StandardCharsets.UTF_8));

            // 8: 关闭通道
        } catch (Exception e) {
            // 7: 关闭通道
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception ignored) {

                }
            }

            // 8: 关闭连接
            if (connection != null && connection.isOpen()) {
                try {
                    connection.close();
                } catch (Exception ignored) {

                }
            }
        }
    }

}
