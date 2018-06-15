package soa.registry;

import org.springframework.context.ApplicationContext;
import soa.schema.Registry;

import java.util.List;

/**
 * Created by zhangdong on 2018/6/15.
 *
 * 委托者类 注册内容委托类
 */
public class BaseRegistryDelegate {

    public static void registry(String ref, ApplicationContext application) {
        // 获取注册信息
        Registry registry = application.getBean(Registry.class);
        String protocol = registry.getProtocol();
        BaseRegistry baseRegistry = Registry.registryMap.get(protocol);
        baseRegistry.registry(ref, application);
    }

    public static List<String> getRegistry(String id,
                                           ApplicationContext application) {
        Registry registry = application.getBean(Registry.class);
        String protocol = registry.getProtocol();
        BaseRegistry registryBean = Registry.registryMap.get(protocol);
        return registryBean.getRegistry(id, application);
    }
}