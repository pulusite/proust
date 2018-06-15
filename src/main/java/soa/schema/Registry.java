package soa.schema;

import soa.registry.BaseRegistry;

import java.util.Map;

/**
 * Created by zhangdong on 2018/6/15.
 */
public class Registry {

    private String protocol;
    private String address;

    public static Map<String,BaseRegistry> registryMap;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}