package trans;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by zhangdong on 2018/6/15.
 */
public class TransactionManager {
    private DataSource dataSource;

    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException{
        return SingleThreadConnectionHolder.getConnection(dataSource);
    }

    //开启事务
    public void start() throws SQLException{
        Connection connection=getConnection();
        connection.setAutoCommit(false);
    }

    //回滚事务
    public void rollback(){
        Connection connection=null;
        try{
            connection=getConnection();
            connection.rollback();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //关闭事务
    public void close() throws SQLException{
        Connection connection=getConnection();
        connection.setAutoCommit(false);
        connection.close();
    }
}
