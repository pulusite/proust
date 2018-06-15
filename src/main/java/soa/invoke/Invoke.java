package soa.invoke;

/**
 * Created by zhangdong on 2018/6/15.
 *
 * 采用策略模式进行rpc调用 返回json Stringj进行通信
 *
 * @author Administrator
 *
 */
public interface Invoke {

    public String invoke(Invocation invocation);
}