package ioc;

/**
 * Created by zhangdong on 2018/6/13.
 */
public class Ioc_ {
    public static void main(String[] args) {
//        String currentPath = IocTest.class.getResource("/applicationContext.xml").getPath().toString();
//        System.out.println(currentPath);

        ApplicationContext context = new ClassPathXmlApplicationContext("ioc.xml");
        Master master = (Master)context.getBean("hostess");

        System.out.println("***********************************");
        master.walkDog();
    }
}
