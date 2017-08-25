package com.cui.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * 最原始的JDBC操作：
 * 加载驱动、获取连接、传输SQL语句、执行SQL获取结果、关闭连接。
 * Created by cuishixiang on 2017-08-25.
 */
public class JDBCOperate {
    private static final Logger logger = LoggerFactory.getLogger(JDBCOperate.class);

    private static final String MYSQL_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    public static void jdbcConn() {

        try {
            Class.forName(MYSQL_DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            logger.error("jdbc驱动类加载异常,驱动名={}", MYSQL_DRIVER_NAME, e);
        }

        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance_order?useSSL=false", "root", "root");
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM finance_order.ORD_USER where mobile=?;", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ) {
            preparedStatement.setString(1, "15600036028");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getLong("id"));
                System.out.println(resultSet.getString("uuid"));
                System.out.println(resultSet.getString("userName"));
                System.out.println(resultSet.getString("mobile"));
                System.out.println(resultSet.getString("open_id"));

                resultSet.updateString("online_account_no", "12345");
                resultSet.updateRow();
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("jdbc操作出问题了", e);
        }
    }

    public static void main(String[] args) {
        jdbcConn();
    }

}
