package ioc; /**
 * Created by zhangdong on 2018/6/13.
 */
import java.io.File;
import java.lang.reflect.Method;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ClassPathXmlApplicationContext implements ApplicationContext {

    private String fileName;

    public ClassPathXmlApplicationContext(String fileName){
        this.fileName = fileName;
    }

    public Object getBean(String beanid) {
        //获取本类的当前目录
//        String currentPath = this.getClass().getResource("").getPath().toString();
        String fileXML=this.getClass().getResource("/"+fileName).getPath();

        SAXReader reader = new SAXReader();//DOM4J解释器
        Document doc = null;//xml文档本身
        Object obj = null;//目标表创建出来的实例
        try {
//            doc = reader.read(  new File(currentPath+fileName)  );
            doc = reader.read(  new File(fileXML)  );
            String xpath = "/beans/bean[@id='"+beanid+"']";
            Element beanNode = (Element) doc.selectSingleNode(xpath);
            String className = beanNode.attributeValue("class");
            obj = Class.forName(className).newInstance();

            Element propertyNode = (Element) beanNode.selectSingleNode("property");

            if(propertyNode!=null){
                System.out.println("当前bean有属性需要注入");

                String propertyName = propertyNode.attributeValue("name");
                System.out.println("当前bean需要注入的属性为"+propertyName);

                //拼接出注入方法
                String setMethod = "set"+(propertyName.substring(0, 1)).toUpperCase()+propertyName.substring(1,propertyName.length());
                System.out.println("自动调用注入方法"+setMethod);

                String set_object_name = propertyNode.attributeValue("ref");
                System.out.println("需要注入的对象名"+set_object_name);

                Object di_object = getBean(set_object_name);
                System.out.println("注入的对象实例"+di_object);

                Method []methods = obj.getClass().getMethods();

                for (Method m : methods) {
                    if(setMethod.equals(m.getName())  ) {
                        m.invoke(obj, di_object);
                        break;
                    }
                }

            }else{
                System.out.println("当前bean没有属性，无需注入直接结束");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return obj;
    }

}