package   com.github.nyc.schedule;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @author:nieyc
 * @company:panchan
 * @Date:created in 14:55 2018/7/13
 * @Description messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
 * 对应级别 1,2,3.。。。。
 * 不能指定具体的时间，只能用级别来定义，如果想精确15分钟后执行，可以参考延原生的迟消息队列
 **/
public class ScheduledMessageProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer=new DefaultMQProducer("NYC_SIMPLE_MESSAGE");
        producer.setNamesrvAddr("172.16.2.148:9876");
        producer.setInstanceName("SyncProducer");
        producer.start();
        int totalMessagesToSend = 10;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("TestTopic", ("Hello scheduled message " + i).getBytes());
            message.setDelayTimeLevel(3);//10秒之后执行
            producer.send(message);
        }

        // Shutdown producer after use.
        producer.shutdown();
    }
}
