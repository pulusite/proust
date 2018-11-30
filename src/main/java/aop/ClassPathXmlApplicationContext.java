package aop;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by zhangdong on 2018/6/13.
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {

    private String fileName;

    public ClassPathXmlApplicationContext(String fileName){
        this.fileName = fileName;
    }

    public Object getBean(String beanid) {
        System.out.println("传递过来的ID:"+beanid);
        //获取本类的当前目录
//        String currentPath = this.getClass().getResource("").getPath().toString();
        String fileXML = this.getClass().getResource("/" + fileName).getPath();
        SAXReader reader = new SAXReader();//DOM4J解释器
        Document doc = null;//xml文档本身
        Object obj = null;//目标表创建出来的实例
        try {
//            doc = reader.read(  new File(currentPath+fileName)  );
            doc = reader.read(  new File(fileXML)  );
            String xpath = "/beans/bean[@id='"+beanid+"']";
            Element beanNode = (Element) doc.selectSingleNode(xpath);
            String className = beanNode.attributeValue("class");
            if ("aop.ProxyFactoryBean".equals(className)){
                Element interceptorNamesNode =
                        (Element) beanNode.selectSingleNode("property[@name='handlerName']");
                String handlerName_value = interceptorNamesNode.attributeValue("ref");
                Element targetNode = (Element) beanNode.selectSingleNode("property[@name='target']");
                String targetName_value = targetNode.attributeValue("ref");
                return forProxyFactoryBean(targetName_value,handlerName_value);
            }
            obj = Class.forName(className).newInstance();
            //查找下一代
            Element propertyNode = (Element) beanNode.selectSingleNode("property");
            if (propertyNode!=null){
                //注入
                //System.out.println("发现property节点，准备注入");
                //需要注入的属性名
                String propertyName = propertyNode.attributeValue("name");
                //System.out.println("需要注入的属性："+propertyName);
                //注入方法
                String executeMethod = "set"+(propertyName.substring(0, 1)).toUpperCase()+propertyName.substring(1,propertyName.length());
                //System.out.println("需要执行注入方法："+executeMethod);
                //需要注入的对象实例
                String di_object_name = propertyNode.attributeValue("ref");
                //System.out.println("注入的对象是："+di_object_name);
                //定义我们的需要注入的对象实例[递归算法：1.层级是不知道多少层的  2.自己调用自己  3.最后1层会自己结束]
                Object di_object = getBean(di_object_name);
                //System.out.println("xxx:"+di_object);
                //Method method = obj.getClass().getMethod(executeMethod,di_object.getClass().getInterfaces());// new Method(executeMethod);
                Method[] methods = obj.getClass().getMethods();
                for (Method m : methods) {
                    if(executeMethod.equals(m.getName())  ) {
                        m.invoke(obj, di_object);
                        break;
                    }
                }
            }
            else{
                System.out.println("没有属性，结束即可");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("返回实例："+obj);
        return obj;
    }


    public Object forProxyFactoryBean(String targetName_value,String handlerName_value) throws Exception{
        System.out.println("目标对象"+targetName_value);
        Object target = getBean(targetName_value);
        System.out.println("代理对象"+handlerName_value);
        InvocationHandler handler = (InvocationHandler) getBean(handlerName_value);
        return new ProxyFactoryBean(target,handler).getProxyBean();
    }
}