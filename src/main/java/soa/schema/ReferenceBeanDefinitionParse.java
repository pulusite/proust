package soa.schema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.springframework.beans.factory.xml.BeanDefinitionParser;

/**
 * Created by zhangdong on 2018/6/15.
 /**
 * bean初始化转换 Reference标签解析类
 */
public class ReferenceBeanDefinitionParse implements BeanDefinitionParser {

    // Reference
    private Class<?> beanClass;

    public ReferenceBeanDefinitionParse(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    // parse转换方法 解析soa.xsd的Reference标签 获取BeanDefinition
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        String id = element.getAttribute("id");
        String intf = element.getAttribute("interface");
        String loadbalance = element.getAttribute("loadbalance");
        String protocol = element.getAttribute("protocol");
        if (id == null || id.length() == 0) {
            throw new RuntimeException("Reference id 不能为空");
        }
        if (intf == null || intf.length() == 0) {
            throw new RuntimeException("Reference intf 不能为空");
        }
        if (loadbalance == null || loadbalance.length() == 0) {
            throw new RuntimeException("Reference loadbalance 不能为空");
        }
        if (protocol == null || protocol.length() == 0) {
            throw new RuntimeException("Reference protocol 不能为空");
        }
        beanDefinition.getPropertyValues().add("id", id);
        beanDefinition.getPropertyValues().add("intf", intf);
        beanDefinition.getPropertyValues().add("loadbalance", loadbalance);
        beanDefinition.getPropertyValues().add("protocol", protocol);
        // 注册
        parserContext.getRegistry().registerBeanDefinition("Reference" + id,
                beanDefinition);
        return beanDefinition;
    }

}