package rpc.abc;

/**
 * Created by zhangdong on 2018/6/15.
 */
public class HelloServiceImpl implements HelloService{
    public String hello(String name) {
        return "Hello " + name;
    }
}
