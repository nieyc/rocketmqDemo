package com.github.nyc.Transaction;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author:nieyc
 * @company:panchan
 * @Date:created in 16:50 2018/7/16
 * @Description
 **/
public class TransactionProducer  {
    public static void main(String[] args)  {
            TransactionCheckListener transactionListener = new TransactionListenerImpl();
            TransactionMQProducer producer = new TransactionMQProducer("NYC_TRANSACTION_TEST");
            producer.setNamesrvAddr("172.16.2.148:9876");
            producer.setTransactionCheckListener(transactionListener);
            TransactionExecuterImpl tran=null;
            try {
                producer.start();
                tran=new TransactionExecuterImpl();
                Message message1=new Message("TransactionTopic","TagA","Key1","Hello,NIEYC".getBytes());
                Message message2=new Message("TransactionTopic","TagA","Key2","Hello,LIYIWEN".getBytes());
                TransactionSendResult sendResult= producer.sendMessageInTransaction(message1,tran,null);
                System.out.println(new Date() + "message1 :"+sendResult);

                sendResult= producer.sendMessageInTransaction(message2,tran,null);
                System.out.println(new Date() + "message2 :"+sendResult);
            }catch (MQClientException e){
                e.printStackTrace();
            }

            producer.shutdown();
        }

}
