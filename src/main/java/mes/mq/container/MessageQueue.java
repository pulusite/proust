package mes.mq.container;


import mes.mq.message.Message;

public interface MessageQueue {
    void push(Message message);
    Message pop();
    int getLength();
}
