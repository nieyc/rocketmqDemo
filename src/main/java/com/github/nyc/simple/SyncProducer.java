import  com.github.nyc.simple.*;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author:nieyc
 * @company:panchan
 * @Date:created in 16:49 2018/7/12
 * @Description
 **/
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer=new DefaultMQProducer("NYC_SIMPLE_MESSAGE");
        producer.setNamesrvAddr("172.16.2.148:9876");
        producer.setInstanceName("SyncProducer");
        producer.start();

        for (int i = 0; i <100; i++) {
            Message msg = new Message("TopicTest" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " +
                            i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            SendResult sendResult= producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }

        producer.shutdown();


    }
}
