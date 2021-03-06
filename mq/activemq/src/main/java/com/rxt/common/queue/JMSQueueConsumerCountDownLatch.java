/**
 * FileName: JMSQueueConsumer
 * Author:   Ren Xiaotian
 * Date:     2018/10/22 16:15
 */

package com.rxt.common.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.CountDownLatch;

/**
 * JMS 从Queue中接收消息：需先启动生产者，再启动消费者
 */
public class JMSQueueConsumerCountDownLatch {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.117.101:61616");
        Connection connection = null;

        try {
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue("MyQueue");    //创建目的地
            MessageConsumer messageConsumer = session.createConsumer(destination);    //创建消息的消费者

//            TextMessage textMessage = (TextMessage) messageConsumer.receive();    //接收消息
//
//            System.out.println(textMessage.getText());

            CountDownLatch countDownLatch = new CountDownLatch(10);

            for (int i = 0; i < 10; i++) {
                System.out.println(((TextMessage)messageConsumer.receive()).getText());
                countDownLatch.countDown();
            }

            countDownLatch.await();

            session.commit();
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
