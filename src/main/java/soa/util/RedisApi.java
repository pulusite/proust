package soa.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

/**
 * Created by zhangdong on 2018/6/15.
 */
public class RedisApi {
    private Jedis jedis;
    private static JedisPool pool;

    /**
     * 建立连接池 真实环境，一般把配置参数缺抽取出来。
     *
     */
    public static void createJedisPool(String address) {

        // 建立连接池配置参数
        JedisPoolConfig config = new JedisPoolConfig();

        // 设置最大连接数
//        config.setMaxActive(100);

        // 设置最大阻塞时间，记住是毫秒数milliseconds
//        config.setMaxWait(1000);

        // 设置空间连接
        config.setMaxIdle(10);

        // 创建连接池
        pool = new JedisPool(config, "127.0.0.1", 6379);

    }

    /**
     * 在多线程环境同步初始化
     */
    private static synchronized void poolInit() {
        if (pool == null)
            createJedisPool(null);
    }

    /**
     * 获取一个jedis 对象
     *
     * @return
     */
    public static Jedis getJedis() {

        if (pool == null)
            poolInit();
        return pool.getResource();
    }

    /**
     * 归还一个连接
     *
     * @param jedis
     */
    public static void returnRes(Jedis jedis) {
        pool.returnResource(jedis);
    }

    public static boolean exists(String key){
        return true;
    }

    public static List<String> lrange(String key){
        return null;
    }

    public static void del(String key){

    }

    public static void lpush(String key,Object args){

    }
}
