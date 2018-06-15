package trans;

import javax.sql.DataSource;

/**
 * Created by zhangdong on 2018/6/15.
 */
public class UserService {
    private UserAccountDao userAccountDao;
    private UserOrderDao userOrderDao;
    private TransactionManager transactionManager;

    public UserService(DataSource dataSource) {
        userAccountDao = new UserAccountDao(dataSource);
        userOrderDao = new UserOrderDao(dataSource);
        transactionManager = new TransactionManager(dataSource);
    }

    public void action(){
        try{
            transactionManager.start();
            userAccountDao.buy();
            userOrderDao.order();
            transactionManager.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
