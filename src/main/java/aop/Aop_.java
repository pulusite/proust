package aop;


import ioc.Master;

/**
 * Created by zhangdong on 2018/6/13.
 */
public class Aop_ {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("aop.xml");

        Master master = (Master)context.getBean("humanProxy");

        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");

        master.shopping();
        master.walkDog();

    }
}
