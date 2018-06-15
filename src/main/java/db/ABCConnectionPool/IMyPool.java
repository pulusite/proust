package db.ABCConnectionPool;

/**
 * Created by zhangdong on 2018/6/15.
 */
public interface IMyPool {
    PooledConnection getConnection();

    void createConnection(int count);
}
