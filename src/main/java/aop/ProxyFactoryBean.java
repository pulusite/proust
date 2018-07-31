package aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by zhangdong on 2018/6/13.
 */
public class ProxyFactoryBean {

    private Object target;

    private InvocationHandler handler;

    public ProxyFactoryBean(Object target,InvocationHandler handler){
        this.target = target;
        this.handler = handler;
    }

//    public ProxyFactoryBean() {
//        super();
//    }

    //返回本类的一个实例
    public Object getProxyBean() throws IllegalArgumentException{
        Object obj = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                handler);
        return obj;
    }
}