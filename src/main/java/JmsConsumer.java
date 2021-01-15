import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer {
    public static final String ACTIVEMQ_URL = "tcp://3.34.220.55:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {
//1 创建连接工厂，按照给定的urL地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2通过连接工场，获得连接接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3 创建会话session
        // 两个参数，第一个叫事务/第二个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4 创建目的地（具体是队列还是主题topic）
        Queue queue = session.createQueue(QUEUE_NAME);
        // 5 创建消息的消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

//        while (true) {
//            TextMessage textMessage = (TextMessage) messageConsumer.receive();
//            if (textMessage != null){
//                System.out.println("Message is: " + textMessage.getText());
//            }else{
//                break;
//            }
//        }

        //通过监听的方式来消息消息 
        messageConsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if (null != message && message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("****消费者接收到消息：" + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        System.in.read();

    // 9 关闭资源
        messageConsumer.close();
        session.close();
        connection.close();
}
}
