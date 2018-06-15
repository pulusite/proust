package soa.consumer;

import java.io.Serializable;

/**
 * Created by zhangdong on 2018/6/15.
 */
public class Registry implements Serializable {
    // 序列化id
    private static final long serialVersionUID = 1672531801363254807L;
    private String protocol;
    private String address;

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