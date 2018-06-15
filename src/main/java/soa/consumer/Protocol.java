package soa.consumer;

/**
 * Created by zhangdong on 2018/6/15.
 */
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import soa.netty.NettyUtil;
import soa.rmi.RmiUtil;

import java.io.Serializable;

public class Protocol implements Serializable, InitializingBean,
        ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    // 序列化id
    private static final long serialVersionUID = -323182438989154754L;

    private String name;
    private String port;
    private String host;
    private String contextpath;

    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        if (name.equalsIgnoreCase("rmi")) {
            RmiUtil rmiUtil = new RmiUtil();
            rmiUtil.startRmiServer(host, port, "larmi");
        }
    }

    // spring启动后的事件
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!ContextRefreshedEvent.class.getName().equals(
                event.getClass().getName())) {
            return;
        }

        if (!"netty".equalsIgnoreCase(name)) {
            return;
        }
        new Thread(new Runnable() {
            public void run() {
                try {
                    NettyUtil.startServer(port);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getContextpath() {
        return contextpath;
    }

    public void setContextpath(String contextpath) {
        this.contextpath = contextpath;
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        // TODO Auto-generated method stub

    }

}