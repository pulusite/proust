package soa.schema;

import org.springframework.context.ApplicationContext;

/**
 * Created by zhangdong on 2018/6/15.
 */
public class Service {

    private String intf;
    private String ref;
    private String protocol;

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public static ApplicationContext getApplication(){
        return null;
    }

}