package   com.github.nyc.broadcast;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author:nieyc
 * @company:panchan
 * @Date:created in 14:17 2018/7/13
 * @Description:广播消息，对于每个订阅了的客户端，都可以接收到这个消息，
 * 如BroadcastConsumer，BroadcastConsumer1，BroadcastConsumer2 全部启动，则都可以收到
 **/
public class BroadcastProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer=new DefaultMQProducer("NYC_BROADCAST_MESSAGE");
        producer.setNamesrvAddr("172.16.2.148:9876");
        producer.setInstanceName("BroadCastProducer");
        producer.start();

        for (int i = 0; i < 10; i++){
            Message msg = new Message("TopicTest",
                    "TagA",
                    "OrderID188",
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }

}
