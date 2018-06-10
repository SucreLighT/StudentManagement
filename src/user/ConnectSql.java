package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectSql {
    public static final String DBDRIVER = "com.mysql.jdbc.Driver";// 加载JDBC驱动
    public static final String DBURL = "jdbc:mysql://localhost:3306/student";
    public static final String DBUSER = "root";// 默认用户名
    public static final String DBPASS = "123456";// 密码

    public static Connection CONN() {
        Connection conn = null;//数据库连接
        try {
            Class.forName(DBDRIVER);//加载数据库驱动程序
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

//		连接数据库，在连接的时候要用用户名和密码，URL
        try {
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(conn);

        return conn;
    }

}

