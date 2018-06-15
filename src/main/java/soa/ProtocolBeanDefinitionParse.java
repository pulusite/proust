package soa;

import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;

/**
 * Created by zhangdong on 2018/6/15.
 *
 * bean初始化转换 Protocol标签解析类
 */
public class ProtocolBeanDefinitionParse implements BeanDefinitionParser {

    // Protocol
    private Class<?> beanClass;

    public ProtocolBeanDefinitionParse(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    // parse转换方法 解析soa.xsd的Protocol标签 获取BeanDefinition
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        String name = element.getAttribute("name");
        String port = element.getAttribute("port");
        String host = element.getAttribute("host");
        if (name == null || name.length() == 0) {
            throw new RuntimeException("Protocol name 不能为空");
        }
        if (port == null || port.length() == 0) {
            throw new RuntimeException("Protocol port 不能为空");
        }
        if (host == null || host.length() == 0) {
            throw new RuntimeException("Protocol host 不能为空");
        }
        beanDefinition.getPropertyValues().add("name", name);
        beanDefinition.getPropertyValues().add("port", port);
        beanDefinition.getPropertyValues().add("host", host);
        // 注册
        parserContext.getRegistry().registerBeanDefinition(
                "Protocol" + host + port, beanDefinition);
        return beanDefinition;
    }

}