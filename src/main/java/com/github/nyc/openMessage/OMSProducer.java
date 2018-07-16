package com.github.nyc.openMessage;

import io.openmessaging.*;

import java.nio.charset.Charset;

/**
 * @author:nieyc
 * @company:panchan
 * @Date:created in 12:21 2018/7/16
 * @Description
 **/
public class OMSProducer {

    public static void main(String[] args) {
        final MessagingAccessPoint messagingAccessPoint = MessagingAccessPointFactory
                .getMessagingAccessPoint("openmessaging:rocketmq://172.16.2.148:9876/namespace");
        final Producer producer = messagingAccessPoint.createProducer();
        messagingAccessPoint.startup();
        System.out.printf("MessagingAccessPoint startup OK%n");

        producer.startup();
        System.out.printf("Producer startup OK%n");


        Message message = producer.createBytesMessageToTopic("OMS_HELLO_TOPIC", "OMS_HELLO_BODY".getBytes(Charset.forName("UTF-8")));
        SendResult sendResult = producer.send(message);
        System.out.printf("Send sync message OK, msgId: %s%n", sendResult.messageId());


        producer.shutdown();
        messagingAccessPoint.shutdown();
    }


}
