package soa.invoke;

import com.alibaba.fastjson.JSONObject;
import soa.consumer.Reference;
import soa.loadbalance.LoadBalance;
import soa.loadbalance.NodeInfo;
import soa.netty.NettyUtil;

import java.util.List;

/**
 * Created by zhangdong on 2018/6/15.
 */
public class NettyInvoke implements Invoke {

    public String invoke(Invocation invocation) {
        Reference reference = invocation.getReference();
        List<String> registryInfo = reference.getRegistryInfo();
        // 负载均衡算法
        String loadbalance = reference.getLoadbalance();
        LoadBalance loadBalanceBean = reference.getLoadBalance().get(
                loadbalance);
        NodeInfo nodeInfo = loadBalanceBean.doSelect(registryInfo);

        // 调用远程的生产者是传输json字符串
        // 根据serviceId调用生产者spring容器中的service实例
        // 根据methodName和methodtype获取利用反射调用method方法
        JSONObject sendParam = new JSONObject();
        sendParam.put("methodName", invocation.getMethod().getName());
        sendParam.put("methodParams", invocation.getObjs());
        sendParam.put("serviceId", reference.getId());
        sendParam.put("paramTypes", invocation.getMethod().getParameterTypes());

        try {
            return NettyUtil.sendMsg(nodeInfo.getHost(), nodeInfo.getPort(),
                    sendParam.toJSONString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}