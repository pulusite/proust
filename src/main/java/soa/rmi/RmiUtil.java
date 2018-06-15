package soa.rmi;

import soa.loadbalance.NodeInfo;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by zhangdong on 2018/6/15.
 *
 * Rmi工具 RMI是底层是socket 不能跨平台 java底层的协议
 *
 */
public class RmiUtil {

    /**
     * @Description 启动rmi服务
     * @param @param host
     * @param @param port
     * @param @param id 参数
     * @return void 返回类型
     * @throws
     */
    public void startRmiServer(String host, String port, String id) {
        try {
            SOARmi soaRmi = new SOARmiImpl();
            LocateRegistry.createRegistry(Integer.valueOf(port));
            // rmi://127.0.0.1:1135/fudisfuodsuf id保证bind的唯一
            Naming.bind("rmi://" + host + ":" + port + "/" + id, soaRmi);
            System.out.println("rmi server start !!!");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public SOARmi startRmiClient(NodeInfo nodeinfo, String id) {
        String host = nodeinfo.getHost();
        String port = nodeinfo.getPort();
        try {
            return (SOARmi) Naming.lookup("rmi://" + host + ":" + port + "/"
                    + id);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}