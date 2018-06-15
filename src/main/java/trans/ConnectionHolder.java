package trans;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangdong on 2018/6/15.
 */
public class ConnectionHolder {
    //实现DataSource到数据库管道的路由
    private Map<DataSource,Connection> map=new HashMap<>();

    public Connection getConnectionByDataSource(DataSource dataSource) throws SQLException{
        Connection connection=map.get(dataSource);

        if (connection==null || connection.isClosed()){
            connection=dataSource.getConnection();
            map.put(dataSource,connection);
        }

        return connection;
    }
}
