import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProducer_Topic {
    public static final String ACTIVEMQ_URL = "tcp://3.34.220.55:61616";
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException {
        //1 创建连接工厂，按照给定的urL地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2通过连接工场，获得连接接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3 创建会话session
        // 两个参数，第一个叫事务/第二个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4 创建目的地（具体是队列还是主题topic） 
        Topic topic = session.createTopic(TOPIC_NAME);
        // 5 创建消息的生产者 
        MessageProducer messageProducer = session.createProducer(topic);
        // 6 通过使imessaqeproducer生产3条消息发送到MQ的队列里面
        for (int i = 1; i <= 3; i++) {
            //7 创建消息，好比学生按照用哥的要求写好的面试题消息，
            TextMessage textMessage = session.createTextMessage("msg---" + i);
            // 理解为一个字符
            // 8 通过mes sageproducer发送给mq
            messageProducer.send(textMessage);
        }

        // 9 关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("Done Sending");
    }
}
