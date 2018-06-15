package soa.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by zhangdong on 2018/6/15.
 *
 * 继承Remote
 *
 * @author Administrator
 *
 */
public interface SOARmi extends Remote {
    public String invoke(String param) throws RemoteException;
}