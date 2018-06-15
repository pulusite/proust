package mes.mq.utility;


import mes.mq.message.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MessageAux {
    public static List<Message> readMessagesFrom(Socket socket) throws IOException{
        InputStream reader = socket.getInputStream();
        ArrayList<Message> messages = new ArrayList<>();

        int c;

        while ((c = reader.read()) != -1){
            try {

                //get message id
                byte[] idBytes = new byte[4];
                if(reader.read(idBytes) == -1){
                    return messages;
                }
                int id = Integer.valueOf(new String(idBytes));
                if(id < 0){
                    continue;
                }

                //get message length
                byte[] messageLengthBytes = new byte[4];
                if(reader.read(messageLengthBytes) == -1){
                    return messages;
                }
                int messageLength = Integer.valueOf(new String(messageLengthBytes));
                if(messageLength <= 0){
                    continue;
                }

                //get message content vai message length we got just now
                byte[] messageContentBytes = new byte[messageLength];
                if(reader.read(messageContentBytes) == -1){
                    return messages;
                }
                String messageContent = new String(messageContentBytes,"UTF-8");
                messages.add(new Message(id,messageLength,messageContent));
            }catch ( NumberFormatException e){
                continue;
            }
        }
        return messages;
    }

    public static Message readMessagesFrom(String fileName) throws IOException, ClassNotFoundException {
//        String curPath=System.getProperty("user.dir")+File.separator+"msgobj/";
        String curPath=MessageAux.class.getClassLoader().getResource("").toString()+File.separator+"msgobj"+File.separator;
        curPath=curPath.substring(6);
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(curPath+fileName)));
        Message m = (Message)inputStream.readObject();
        inputStream.close();
        return m;
    }
}
