package   com.github.nyc.simple;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author:nieyc
 * @company:panchan
 * @Date:created in 17:39 2018/7/12
 * @Description 单向传输,单向传输用于需要中等可靠性的情况，例如日志收集.
 **/
public class OneWayProducer {
    public static void main(String[] args) throws Exception {
        final DefaultMQProducer producer=new DefaultMQProducer("NYC_SIMPLE_ONEMESSAGE");
        producer.setNamesrvAddr("172.16.2.148:9876");
        producer.setInstanceName("OneProducer");
        producer.start();
        for (int i = 0; i < 100; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("TopicTest1" /* Topic */,
                    "TagC" /* Tag */,
                    ("Hello RocketMQ " +
                            i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            Thread.sleep(1000);
            producer.sendOneway(msg);

        }
        producer.shutdown();
    }
}
