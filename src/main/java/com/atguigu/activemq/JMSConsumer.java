package com.atguigu.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import static org.hamcrest.CoreMatchers.nullValue;

import javax.jms.*;

/**
 * @auther zzyy
 * @create 2019-09-10 14:51
 */
public class JMSConsumer
{
    public static final String MQ_URL = "tcp://192.168.10.17:61616";
    public static final String MyQUEUE = "queue0508";

    public static void main(String[] args) throws JMSException
    {
    	ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);
    	Connection connection = activeMQConnectionFactory.createConnection();
    	connection.start();
    	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	Queue queue = session.createQueue(MyQUEUE);
    	MessageConsumer messageConsumer = session.createConsumer(queue);
    	TextMessage textMessage = null;
    	while(true) {
    		textMessage = (TextMessage) messageConsumer.receive();
    		if(textMessage != null) {
    			System.out.println("****收到的消息："+textMessage.getText());
    		}else {
				break;
			}
    	}
    	messageConsumer.close();
    	session.close();
    	connection.close();

     /*   ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        Queue queue = session.createQueue(MyQUEUE);
        MessageConsumer messageConsumer = session.createConsumer(queue);


       同步阻塞方式receive() ，订阅者或接收者调用MessageConsumer的receive()方法来接收消息，
        receive()将一直阻塞
        receive(long timeout) 按照给定的时间阻塞，到时间自动退出
        TextMessage textMessage = null;
        while(true)
        {
            textMessage = (TextMessage) messageConsumer.receive();

            if(null != textMessage)
            {
                System.out.println("****收到的消息："+textMessage.getText());
                textMessage.acknowledge();;
                //session.commit();;
            }else{
                break;
            }
        }
        messageConsumer.close();

        session.close();
        connection.close();*/



       /*
        异步非阻塞方式(监听器onMessage())
        订阅者或接收者通过MessageConsumer的setMessageListener(MessageListener listener)注册一个消息监听器，
        当消息到达之后，系统自动调用监听器MessageListener的onMessage(Message message)方法。
        messageConsumer.setMessageListener((message) -> {
            if(null != message && message instanceof TextMessage)
            {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("****收到的消息："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });*/



    }
}
