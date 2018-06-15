package soa.loadbalance;

import com.alibaba.fastjson.JSONObject;

import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by zhangdong on 2018/6/15.
 *
 * 负载均衡随机算法
 */
public class RandomLoadBalance implements LoadBalance {

    public NodeInfo doSelect(List<String> registryInfo) {
        Random random = new Random();
        int index = random.nextInt(registryInfo.size());
        String registry = registryInfo.get(index);
        JSONObject registryJO = (JSONObject) JSONObject.parse(registry);
        Collection values = registryJO.values();
        JSONObject node = new JSONObject();
        for (Object value : values) {
            node = JSONObject.parseObject(value.toString());
        }
        JSONObject protocol = node.getJSONObject("protocol");
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setHost(protocol.get("host") != null ? protocol
                .getString("host") : "");
        nodeInfo.setPort(protocol.get("port") != null ? protocol
                .getString("port") : "");
        nodeInfo.setContextpath(protocol.get("contextpath") != null ? protocol
                .getString("contextpath") : "");
        return nodeInfo;
    }

}