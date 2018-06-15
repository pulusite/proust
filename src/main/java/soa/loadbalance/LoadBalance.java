package soa.loadbalance;

import java.util.List;

/**
 * Created by zhangdong on 2018/6/15.
 *
 * 负载均衡算法
 */
public interface LoadBalance {

    NodeInfo doSelect(List<String> registryInfo);
}