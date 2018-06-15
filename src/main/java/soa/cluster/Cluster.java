package soa.cluster;

import soa.invoke.Invocation;

/**
 * Created by zhangdong on 2018/6/15.
 *
 * 集群容错
 */
public interface Cluster {
    public String invoke(Invocation invocation) throws Exception;
}