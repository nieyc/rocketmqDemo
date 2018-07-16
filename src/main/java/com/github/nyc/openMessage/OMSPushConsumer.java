package com.github.nyc.openMessage;

import io.openmessaging.*;
import io.openmessaging.exception.OMSResourceNotExistException;
import io.openmessaging.rocketmq.domain.NonStandardKeys;

/**
 * @author:nieyc
 * @company:panchan
 * @Date:created in 13:59 2018/7/16
 * @Description
 **/
public class OMSPushConsumer {
    public static void main(String[] args) throws OMSResourceNotExistException {
        final MessagingAccessPoint messagingAccessPoint = MessagingAccessPointFactory
                .getMessagingAccessPoint("openmessaging:rocketmq://172.16.2.148:9876/namespace");

        final PushConsumer consumer = messagingAccessPoint.
                createPushConsumer(OMS.newKeyValue().put(NonStandardKeys.CONSUMER_GROUP, "OMS_CONSUMER"));

        messagingAccessPoint.startup();
        System.out.printf("MessagingAccessPoint startup OK%n");
        consumer.startup();
        System.out.println("Consumer startup OK");

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                consumer.shutdown();
                messagingAccessPoint.shutdown();
            }
        }));

        consumer.attachQueue("OMS_HELLO_TOPIC", new MessageListener() {
            @Override
            public void onMessage(final Message message, final ReceivedMessageContext context) {
                System.out.printf("Received one message: %s%n", message.headers().getString(MessageHeader.MESSAGE_ID));
                context.ack();
            }
        });


    }
}
