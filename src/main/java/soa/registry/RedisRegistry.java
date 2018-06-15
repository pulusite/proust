package soa.registry;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.ApplicationContext;
import soa.schema.Protocol;
import soa.util.RedisApi;
import soa.schema.Registry;
import soa.schema.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhangdong on 2018/6/15.
 *
 * redis注册中心处理类
 *
 */
public class RedisRegistry implements BaseRegistry {

    public boolean registry(String ref, ApplicationContext application) {
        try {
            Protocol protocol = application.getBean(Protocol.class);
            Map<String, Service> services = application
                    .getBeansOfType(Service.class);
            Registry registry = application.getBean(Registry.class);
            RedisApi.createJedisPool(registry.getAddress());
            for (Map.Entry<String, Service> entry : services.entrySet()) {
                if (entry.getValue().getRef().equals(ref)) {
                    JSONObject jo = new JSONObject();
                    jo.put("protocol", JSONObject.toJSONString(protocol));
                    jo.put("services",
                            JSONObject.toJSONString(entry.getValue()));

                    JSONObject ipport = new JSONObject();
                    ipport.put(protocol.getHost() + ":" + protocol.getPort(),
                            jo);
                    lpush(ipport, ref);
                }
            }
            return true;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    /**
     * [ { "127.0.0.1:27017":{
     * "protocol":"{"host":"127.0.0.1","name":"http","port":"27017"}",
     * "services"
     * :"{"intf":"com.youi.la.test.service.UserService4","protocol":"
     * http","ref":"userServiceImpl4"}" },{ "127.0.0.1:27017":{
     * "protocol":"{"host":"127.0.0.1","name":"http","port":"27017"}",
     * "services"
     * :"{"intf":"com.youi.la.test.service.UserService1","protocol":"
     * http","ref":"userServiceImpl1"}" } } ]
     */
    // 数据加入到redis
    private void lpush(JSONObject ipport, String key) {
        if (RedisApi.exists(key)) {
            Set<String> keys = ipport.keySet();
            String ipportStr = "";
            for (String ks : keys) {
                ipportStr = ks;
            }
            // 去重
            boolean isold = false;
            List<String> registryInfo = RedisApi.lrange(key);
            List<String> newRegistry = new ArrayList<String>();
            for (String node : registryInfo) {
                JSONObject jo = JSONObject.parseObject(node);
                if (jo.containsKey(ipportStr)) {
                    newRegistry.add(ipport.toJSONString());
                    isold = true;
                } else {
                    newRegistry.add(node);
                }
            }
            if (isold) {
                if (newRegistry.size() > 0) {
                    RedisApi.del(key);
                    String[] newReStr = new String[newRegistry.size()];
                    for (int i = 0; i < newReStr.length; i++) {
                        newReStr[i] = newRegistry.get(i);
                    }
                    RedisApi.lpush(key, newReStr);
                }
            } else {
                RedisApi.lpush(key, ipport.toJSONString());
            }
        } else {
            RedisApi.lpush(key, ipport.toJSONString());
        }
    }

    public List<String> getRegistry(String id, ApplicationContext application) {
        try {
            Registry registry = application.getBean(Registry.class);
            RedisApi.createJedisPool(registry.getAddress());
            if (RedisApi.exists(id)) {
                // 获取list
                return RedisApi.lrange(id);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

}
