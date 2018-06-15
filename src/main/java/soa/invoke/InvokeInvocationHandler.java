package soa.invoke;

import org.springframework.cglib.proxy.InvocationHandler;
import soa.cluster.Cluster;
import soa.consumer.Reference;

import java.lang.reflect.Method;

/**
 * Created by zhangdong on 2018/6/15.
 *
 * jdk动作代理 InvokeInvocationHandler 是一个advice(通知、增强)，它进行了rpc(http、rmi、netty)的远程调用
 *
 * @author Administrator
 *
 */
public class InvokeInvocationHandler implements InvocationHandler {

    private Invoke invoke;

    private Reference reference;

    public InvokeInvocationHandler(Invoke invoke, Reference reference) {
        this.invoke = invoke;
        this.reference = reference;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        // 这里最终要调用多个远程的生产者 生产者在启动的时候要在注册中心（redis）注册信息
        System.out.println("已经获取到了代理实例 InvokeInvocationHandler invoke");

        Invocation invocation = new Invocation();
        invocation.setMethod(method);
        invocation.setObjs(args);
        invocation.setReference(reference);
        invocation.setInvoke(invoke);
        // String result = invoke.invoke(invocation);
        Cluster cluster = reference.getClusters().get(reference.getCluster());
        String result = cluster.invoke(invocation);
        return result;
    }

}