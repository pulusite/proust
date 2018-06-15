package trans;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * Created by zhangdong on 2018/6/15.
 */
public class Test {

    public static final String jdbcDriver="com.mysql.jdbc.Driver";
    public static final String jdbcURL="jdbc:mysql://127.0.0.1:3306/litart";
    public static final String jdbcUsername="root";
    public static final String jdbcPassword="root";

    public static void main(String[] args) {
        BasicDataSource basicDataSource=new BasicDataSource();
        basicDataSource.setDriverClassName(jdbcDriver);
        basicDataSource.setUsername(jdbcUsername);
        basicDataSource.setPassword(jdbcPassword);
        basicDataSource.setUrl(jdbcURL);

        final UserService userService=new UserService(basicDataSource);

        //模拟用户并发请求
        for (int i = 0; i < 10; i++) {
            //lambda
            new Thread(()->userService.action()).start();
        }

        try {
            Thread.sleep(10000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
