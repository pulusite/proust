package soa.schema;

import org.w3c.dom.Element;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;

/**
 * Created by zhangdong on 2018/6/15.
 * bean初始化转换 Registry标签解析类
 */
public class RegistryBeanDefinitionParse implements BeanDefinitionParser {

    // Registry
    private Class<?> beanClass;

    public RegistryBeanDefinitionParse(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    // parse转换方法 解析soa.xsd的registry标签 获取BeanDefinition
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        // spring会把beanClass实例化 BeanDefinitionNames??
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        String protocol = element.getAttribute("protocol");
        String address = element.getAttribute("address");
        if (protocol == null || address.length() == 0) {
            throw new RuntimeException("Registry protocol 不能为空");
        }
        if (address == null || address.length() == 0) {
            throw new RuntimeException("Registry address 不能为空");
        }
        beanDefinition.getPropertyValues().add("protocol", protocol);
        beanDefinition.getPropertyValues().add("address", address);
        // 注册
        parserContext.getRegistry().registerBeanDefinition(
                "Registry" + address, beanDefinition);
        return beanDefinition;
    }

}