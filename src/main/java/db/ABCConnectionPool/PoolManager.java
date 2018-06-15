package db.ABCConnectionPool;

/**
 * Created by zhangdong on 2018/6/15.
 */
public class PoolManager {
    private static class createPool {

        private static MyPoolImpl poolImpl = new MyPoolImpl();

    }

    /**
     * 内部类单利模式产生使用对象
     * @return
     */
    public static MyPoolImpl getInstance() {
        return createPool.poolImpl;
    }
}
