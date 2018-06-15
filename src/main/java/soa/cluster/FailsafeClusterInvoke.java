package soa.cluster;

import soa.invoke.Invocation;
import soa.invoke.Invoke;

/**
 * Created by zhangdong on 2018/6/15.
 *
 * @Description 调用节点失败，直接忽略
 * @ClassName FailsafeClusterInvoke
 */
public class FailsafeClusterInvoke implements Cluster {

    public String invoke(Invocation invocation) throws Exception {
        Invoke invoke = invocation.getInvoke();

        try {
            return invoke.invoke(invocation);
        } catch (Exception e) {
            e.printStackTrace();
            return "忽略";
        }
    }

}