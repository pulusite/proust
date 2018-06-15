package soa.cluster;

import soa.invoke.Invocation;
import soa.invoke.Invoke;

/**
 * Created by zhangdong on 2018/6/15.
 *
 * @Description 这个如果调用节点异常，直接失败
 * @ClassName FailfastClusterInvoke
 */

public class FailfastClusterInvoke implements Cluster {

    public String invoke(Invocation invocation) throws Exception {
        Invoke invoke = invocation.getInvoke();

        try {
            return invoke.invoke(invocation);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}