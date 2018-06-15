package soa.consumer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import soa.registry.BaseRegistryDelegate;

import java.io.Serializable;

/**
 * Created by zhangdong on 2018/6/15.
 *
 * InitializingBean SpringIOC注入后调用
 *
 */
public class Service implements Serializable, InitializingBean,
        ApplicationContextAware {
    // 序列化id
    private static final long serialVersionUID = -2814888066547175285L;
    private String intf;
    private String ref;
    private String protocol;

    public static ApplicationContext application;

    // InitializingBean 实现方法
    public void afterPropertiesSet() throws Exception {
        // 注册生产者的服务
        BaseRegistryDelegate.registry(ref, application);
    }

    // ApplicationContextAware 实现方法 用来获取Spring上下文
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        // TODO Auto-generated method stub
        this.application = applicationContext;
    }

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public static ApplicationContext getApplication() {
        return application;
    }

    public void setApplication(ApplicationContext application) {
        this.application = application;
    }

}