/**
 * rabbitmq 一些关键概念阐述
 * rabbitmq 中生产者的数据都是投递给交换机的；再由交换机投放到队列中；消费者再消费队列中的数据。
 *
 * 交换机（exchange）的四种模式：广播（fanout），路由key（direct），主题（topic），header；
 * 三者性能比较：fanout > direct > topic. 比例大概为 11：10：6。（topic的匹配规则复杂，自然就效率就低。）
 * 简单：simple；一个交换机对应一个队列
 * 广播式：fanout；一个交换机对应多个队列，每个队列都会接收到。
 * 路由key：direct；一个交换机对应多个队列，每个队列根据routing key筛选是否接收交换机的发送过来的消息。
 * 主题：topic；一个交换机对应多个队列，每个队列根据模糊匹配 routing key筛选是否接收交换机发送过来的消息。
 *
 * 消息队列的使用过程大概如下：
 *
 * （1）客户端连接到消息队列服务器，打开一个channel。
 * （2）客户端声明一个exchange，并设置相关属性。
 * （3）客户端声明一个queue，并设置相关属性。
 * （4）客户端使用routing key，在exchange和queue之间建立好绑定关系。
 * （5）客户端投递消息到exchange。
 *
 * exchange接收到消息后，就根据消息的key和已经设置的binding，进行消息路由，将消息投递到一个或多个队列里。
 */
package com.qiusm.rabbitmq.demo.exchange;