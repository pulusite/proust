package soa;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by zhangdong on 2018/6/15.
 */


/**
 * spring标签解析类 继承NamespaceHandlerSupport
 *
 * @author Administrator
 *
 */
public class SOANamespaceHandlers extends NamespaceHandlerSupport {

    /**
     * 注册标签
     */
    public void init() {
        this.registerBeanDefinitionParser("registry",
                new RegistryBeanDefinitionParse(Registry.class));
        this.registerBeanDefinitionParser("reference",
                new ReferenceBeanDefinitionParse(Reference.class));
        this.registerBeanDefinitionParser("protocol",
                new ProtocolBeanDefinitionParse(Protocol.class));
        this.registerBeanDefinitionParser("service",
                new ServiceBeanDefinitionParse(Service.class));
    }

}