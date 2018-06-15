package dis.distributedlock;

import org.apache.zookeeper.KeeperException;

/**
 * Created by zhangdong on 2018/6/15.
 */
public interface ZooKeeperOperation {
    public boolean execute() throws KeeperException, InterruptedException;
}
