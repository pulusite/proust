package soa.invoke;

import soa.consumer.Reference;

import java.lang.reflect.Method;

/**
 * Created by zhangdong on 2018/6/15.
 * 封装InvokeInvocationHandler的invoke方法里的三个参数Object , Method , Object[]
 *
 * @author Administrator
 *
 */
public class Invocation {

    private Method method;

    private Object[] objs;

    private Invoke invoke;

    private Reference reference;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getObjs() {
        return objs;
    }

    public void setObjs(Object[] objs) {
        this.objs = objs;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }

    public Invoke getInvoke() {
        return invoke;
    }

    public void setInvoke(Invoke invoke) {
        this.invoke = invoke;
    }

}