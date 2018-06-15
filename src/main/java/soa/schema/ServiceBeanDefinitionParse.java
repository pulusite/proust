package soa.schema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by zhangdong on 2018/6/15.
 *
 * bean初始化转换 Service标签解析类
 */
public class ServiceBeanDefinitionParse implements BeanDefinitionParser {

    // Service
    private Class<?> beanClass;

    public ServiceBeanDefinitionParse(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    // parse转换方法 解析soa.xsd的Service标签 获取BeanDefinition
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        String intf = element.getAttribute("interface");
        String ref = element.getAttribute("ref");
        String protocol = element.getAttribute("protocol");
        if (intf == null || intf.length() == 0) {
            throw new RuntimeException("Service intf 不能为空");
        }
        if (ref == null || ref.length() == 0) {
            throw new RuntimeException("Service ref 不能为空");
        }
        /**
         * if (protocol == null || protocol.length() == 0) { throw new
         * RuntimeException("Service protocol 不能为空"); }
         */
        beanDefinition.getPropertyValues().add("intf", intf);
        beanDefinition.getPropertyValues().add("ref", ref);
        beanDefinition.getPropertyValues().add("protocol", protocol);
        // 注册
        parserContext.getRegistry().registerBeanDefinition(
                "Service" + ref + intf, beanDefinition);
        return beanDefinition;
    }

}