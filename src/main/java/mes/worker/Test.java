package mes.worker;



import mes.mq.message.Message;

import java.io.*;

/**
 * Created by zhangdong on 2018/6/15.
 */
public class Test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Message message=new Message(1,4,"good");
//        String curPath=System.getProperty("user.dir")+File.separator+"resource"+File.separator;
        String curPath=Test.class.getClassLoader().getResource("").toString()+File.separator+"msgobj"+File.separator;
        curPath=curPath.substring(6);
        File f= new File(curPath+"msg1498374247");

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
        out.writeObject(message);

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
        message=(Message)in.readObject();
        System.out.println("Message: " +message);
    }
}
