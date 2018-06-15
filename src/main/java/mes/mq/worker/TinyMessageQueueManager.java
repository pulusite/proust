package mes.mq.worker;


import mes.mq.container.TinyMessageQueue;
import mes.mq.message.Message;
import mes.mq.utility.MessageAux;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class TinyMessageQueueManager {
    private TinyMessageQueue[] tmq;

    class ParserMessageFromSocketAndInqueue implements Runnable{
        private Socket task;

        public ParserMessageFromSocketAndInqueue(Socket task){
            this.task = task;
        }

        @Override
        public void run() {
            synchronized (this) {
                try {
                    List<Message> messages = MessageAux.readMessagesFrom(task);

                    for (Message message : messages){
                        System.out.println(message);
                        findMinLengthQueue().push(message);
                    }
                } catch (IOException e) {
                    MasterWorker.logger.warning("failure to push message into queue");
                }
            }
        }
    }

    class PersistenceTask implements Runnable{
        @Override
        public void run(){
            synchronized (this) {
                Message messageObject = findMaxLengthQueue().pop();
                if (messageObject != null) {
                    long epoch = System.currentTimeMillis() / 1000;
                    String curPath=MessageAux.class.getClassLoader().getResource("").toString()+"msgobj";
                    curPath=curPath.substring(6);
                    try(FileOutputStream fileOutputStream = new FileOutputStream(curPath+"/msg" + epoch);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);) {
                        objectOutputStream.writeObject(messageObject);
                        objectOutputStream.flush();
                        fileOutputStream.flush();
                        objectOutputStream.close();
                        fileOutputStream.close();
                        System.out.println("接收消息成功:"+messageObject);
                        MasterWorker.logger.info(Thread.currentThread().getName() + " : In persistence task with "+getTotalMessageCount());
                    } catch (IOException e) {
                        e.printStackTrace();
                        MasterWorker.logger.warning("failure to persist message object");
                    }
                }
            }
        }
    }

    synchronized public int getTotalMessageCount(){
        int result = 0;
        for (TinyMessageQueue tmq : tmq) {
            result += tmq.getLength();
        }
        return result;
    }

    public TinyMessageQueueManager(int maxQueueCount){
        tmq = new TinyMessageQueue[maxQueueCount];
        for(int i = 0;i < maxQueueCount; i++){
            tmq[i] = new TinyMessageQueue();
        }
    }

    public TinyMessageQueue findMinLengthQueue(){
        int minLen = tmq[0].getLength();

        TinyMessageQueue tmqRef = tmq[0];

        for(int i = 1; i < tmq.length; i++){
            int curlen = tmq[i].getLength();
            if(curlen < minLen ){
                minLen = curlen;
                tmqRef = tmq[i];
            }
        }
        return tmqRef;
    }

    public TinyMessageQueue findMaxLengthQueue(){
        int maxLen = tmq[0].getLength();

        TinyMessageQueue tmqRef = tmq[0];

        for(int i = 1; i < tmq.length; i++){
            int curlen = tmq[i].getLength();
            if(curlen > maxLen ){
                maxLen = curlen;
                tmqRef = tmq[i];
            }
        }
        return tmqRef;
    }
}
