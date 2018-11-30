package dp.proxy;

/**
 * Created by dongzhang on 5/13/17.
 */
public class DynamicProxyDemo {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        MyInvocationHandler invocationHandler = new MyInvocationHandler(userService);

        UserService proxy = (UserService) invocationHandler.getProxy();
        proxy.add();
    }
}
