package   com.github.nyc.simple;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author:nieyc
 * @company:panchan
 * @Date:created in 17:50 2018/7/12
 * @Description
 **/
public class Consumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroup");
        consumer.setNamesrvAddr("172.16.2.148:9876");
        consumer.setInstanceName("Consumber");
        consumer.subscribe("TopicTest","TagA||TagB||TagC");
        consumer.subscribe("TopicTest1", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + list.size());
                MessageExt msg = list.get(0);
                if (msg.getTopic().equals("TopicTest")) {
                    if(msg.getTags() != null &&msg.getTags().equals("TagA")){
                        System.out.println("TopicTest,TagA:"+new String(msg.getBody()));
                    }else if(msg.getTags()!=null&&"TagB".equals(msg.getTags())){
                        System.out.println("TopicTest,TagB:"+new String(msg.getBody()));
                    }
                } else if (msg.getTopic().equals("TopicTest1")) {
                    System.out.println("TopicTest1:"+new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.println("ConsumerStarted.");
    }

}
