package soa.consumer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import soa.cluster.Cluster;
import soa.cluster.FailfastClusterInvoke;
import soa.cluster.FailoverClusterInvoke;
import soa.cluster.FailsafeClusterInvoke;
import soa.invoke.*;
import soa.loadbalance.LoadBalance;
import soa.loadbalance.RandomLoadBalance;
import soa.loadbalance.RoundRobinLoadBalance;
import soa.registry.BaseRegistryDelegate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangdong on 2018/6/15.
 *
 * 代理实例接口FactoryBean 获取Spring上下文 ApplicationContextAware
 */
public class Reference implements Serializable, FactoryBean,
        ApplicationContextAware, InitializingBean {
    // 序列化id
    private static final long serialVersionUID = 6496428948999332452L;

    private String id;
    private String intf;
    private String loadbalance;
    private String protocol;
    private String cluster;
    private String retries;

    private Invoke invoke;// 调用者

    private ApplicationContext application;

    private static Map<String, Invoke> invokes = new HashMap<String, Invoke>();
    private List<String> registryInfo = new ArrayList<String>();
    private static Map<String, LoadBalance> loadBalance = new HashMap<String, LoadBalance>();
    private static Map<String, Cluster> clusters = new HashMap<String, Cluster>();
    /**
     * 注册远程调用协议
     */
    static {
        invokes.put("http", new HttpInvoke());
        invokes.put("rmi", new RmiInvoke());
        invokes.put("netty", new NettyInvoke());

        loadBalance.put("random", new RandomLoadBalance());
        loadBalance.put("roundrobin", new RoundRobinLoadBalance());

        clusters.put("failover", new FailoverClusterInvoke());
        clusters.put("failfast", new FailfastClusterInvoke());
        clusters.put("failsafe", new FailsafeClusterInvoke());
    }

    public Reference() {
        System.out.println("66666666666666666666");
    }

    /** ApplicationContextAware的实现方法 */
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.application = applicationContext;
    }

    /**
     * 拿到一个实例，这个方法是Spring初始化时getBean方法里调用的
     * ApplicationContext.getBean("id")时就会调用getObject(),其返回值会交给Spring进行管理
     * 在getObject()方法里返回的是intf接口的代理对象
     */
    public Object getObject() throws Exception {
        System.out.println("返回intf的代理对象");
        if (protocol != null && protocol.length() > 0) {
            invoke = invokes.get(protocol);
        } else {
            // Protocol的实例在spring容器中
            Protocol protocol = application.getBean(Protocol.class);
            if (protocol != null) {
                invoke = invokes.get(protocol.getName());
            } else {
                invoke = invokes.get("http");
            }
        }
        // 返回代理对象
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class<?>[] { Class.forName(intf) },
                new InvokeInvocationHandler(invoke, this));
    }

    public Class getObjectType() {
        if (intf != null && intf.length() > 0) {
            try {
                return Class.forName(intf);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return null;
    }

    /** 返回的代理实例是否是单例 */
    public boolean isSingleton() {
        // TODO Auto-generated method stub
        return false;
    }

    // InitializingBean实现的方法
    public void afterPropertiesSet() throws Exception {
        // 消费者获得生产者所有注册信息
        registryInfo = BaseRegistryDelegate.getRegistry(id, application);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }

    public String getLoadbalance() {
        return loadbalance;
    }

    public void setLoadbalance(String loadbalance) {
        this.loadbalance = loadbalance;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public List<String> getRegistryInfo() {
        return registryInfo;
    }

    public void setRegistryInfo(List<String> registryInfo) {
        this.registryInfo = registryInfo;
    }

    public static Map<String, LoadBalance> getLoadBalance() {
        return loadBalance;
    }

    public static void setLoadBalance(Map<String, LoadBalance> loadBalance) {
        Reference.loadBalance = loadBalance;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getRetries() {
        return retries;
    }

    public void setRetries(String retries) {
        this.retries = retries;
    }

    public static Map<String, Cluster> getClusters() {
        return clusters;
    }

    public static void setClusters(Map<String, Cluster> clusters) {
        Reference.clusters = clusters;
    }

}