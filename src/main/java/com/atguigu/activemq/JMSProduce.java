package com.atguigu.activemq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @auther zzyy
 * @create 2019-09-10 14:31
 */
public class JMSProduce
{
    public static final String MQ_URL = "tcp://192.168.10.17:61616";
    public static final String MyQUEUE = "queue0508";

    public static void main(String[] args) throws JMSException
    {
        //1 通过ConnectionFactory工厂
    	ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);
        //2 获得connection对象并启动
    	Connection connection = activeMQConnectionFactory.createConnection();
    	connection.start();
        //3 通过connection对象获得session对象
    	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 第一个参数叫mq的事务/第二个参数叫消息的签收，此时忽略用默认
        //4 通过session获得目的地
    	Queue queue = session.createQueue(MyQUEUE);
        //5 通过session获得消息的生产者
    	MessageProducer messageProducer = session.createProducer(queue);
        //messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        //6 开始生产3条消息发送到Activemq上
    	for (int i = 1; i <= 3; i++) {
			TextMessage message = session.createTextMessage("msg---" + i);
			messageProducer.send(message);
		}
            //7 用messageProducer发送消息到mq
        //8 释放资源
        //session.commit();
    	messageProducer.close();
    	session.close();
    	connection.close();
        System.out.println("*****run is ok");



    }
}
