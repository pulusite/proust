package dp.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by dongzhang on 5/13/17.
 */
public class MyInvocationHandler implements InvocationHandler {
    public Object target;

    public MyInvocationHandler(Object target){
        super();
        this.target = target;
    }

    public Object getProxy(){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(),
                this);
    }

    public Object invoke(Object proxy, Method methnd, Object[] args) throws Throwable {
        System.out.println("-----before-----");
        Object result = methnd.invoke(target,args);
        System.out.println("-----after----");
        return result;
    }

}
