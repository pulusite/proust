package ioc;

/**
 * Created by zhangdong on 2018/6/13.
 */
public class IocTest {
    public static void main(String[] args) {
//        String currentPath = IocTest.class.getResource("/applicationContext.xml").getPath().toString();
//        System.out.println(currentPath);

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Master master = (Master)context.getBean("hostess");

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("***********************************");
        master.walkDog();
    }
}
