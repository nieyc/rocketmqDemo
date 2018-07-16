package   com.github.nyc.simple;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author:nieyc
 * @company:panchan
 * @Date:created in 17:07 2018/7/12
 * @Description：异步传输，异步传输通常用于响应时间敏感的业务场景。
 **/
public class AsyncProducer {

    public static void main(String[] args) throws Exception {
        final DefaultMQProducer producer=new DefaultMQProducer("NYC_SIMPLE_AMESSAGE");
        producer.setNamesrvAddr("172.16.2.148:9876");
        producer.setInstanceName("AsyncProducer");
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);
        for (int i = 0; i < 100; i++) {
            final int index = i;
            Message msg = new Message("TopicTest","TagB",
                    "OrderID188",
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));

            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index,
                            sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
            Thread.sleep(1000);
        }

        Thread.sleep(1000);
        producer.shutdown();

        //producer.shutdown();
       /* Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                producer.shutdown();
                System.out.println("AsyncProducer shut down");
            }
        }));*/


    }

}
