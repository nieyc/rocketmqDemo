package com.github.nyc.Transaction;

import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;

import java.util.Date;
import java.util.Random;

/**
 * @author:nieyc
 * @company:panchan
 * @Date:created in 17:45 2018/7/16
 * @Description
 **/
public class TransactionExecuterImpl implements LocalTransactionExecuter {
    @Override
    public LocalTransactionState executeLocalTransactionBranch(Message message, Object o) {

        try{
            if(new Random().nextInt(3) == 2){
                int a = 1 / 0;
            }
            System.out.println(new Date()+"===> 本地事务执行成功，发送确认消息");
        }catch (Exception e){
            System.out.println(new Date()+"===> 本地事务执行失败！！！");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }


    public static void main(String[] args) {
        for (int i = 0; i <20 ; i++) {
          //  System.out.println(new Random().nextInt(3));
            int k=new Random().nextInt(3);
            System.out.println(k);
            try{
                if(k==2){
                    int a = 1 / 0;
                }
            }catch (Exception e){
                System.out.println("k==2，报错");
            }

        }

    }
}


