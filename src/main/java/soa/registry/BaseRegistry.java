package soa.registry;

import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Created by zhangdong on 2018/6/15.
 *
 * 生产者信息注册 RedisApi是Rdis工具类
 */
public interface BaseRegistry {
    public boolean registry(String param, ApplicationContext application);
    public List<String> getRegistry(String id, ApplicationContext application);
}