package mes.mq.worker;


import mes.mq.utility.FolderAux;
import mes.mq.utility.MessageAux;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MasterWorker {
    public static Logger logger;

    private ServerSocket serverSocket;

    private DispatchMiddleware dispatcher;

    private ScheduledExecutorService mainThreadPool;

    private int port;

    public MasterWorker(int port) {
        try {
            this.port = port;
            serverSocket = new ServerSocket(port);
            dispatcher = new DispatchMiddleware();
            mainThreadPool = Executors.newScheduledThreadPool(10);
            serverSocket.setSoTimeout(3000);
        }catch (Exception e){
            System.exit(0);
        }
    }

    public void configureWorker(){
        String curPath=MessageAux.class.getClassLoader().getResource("").toString()+"msgobj";
        curPath=curPath.substring(6);
        FolderAux.createFolder(curPath);
        logger = Logger.getLogger("TMQ::LOGGER");
        logger.setLevel(Level.ALL);
    }

    public void handleRequest(){
        mainThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                MasterWorker.logger.info("In socket waiting");
                Socket socketTask = null;
                try {
                    socketTask = serverSocket.accept();
                    dispatcher.dispatch(socketTask);
                    System.out.println("分发任务成功！");
                } catch (SocketTimeoutException e){
                    MasterWorker.logger.info("Accept timeout");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 3, 10, TimeUnit.MILLISECONDS);

        mainThreadPool.scheduleAtFixedRate(dispatcher.tmqManager.new PersistenceTask(), 2, 1, TimeUnit.SECONDS);
    }

    public int getPort(){
        return port;
    }
}
