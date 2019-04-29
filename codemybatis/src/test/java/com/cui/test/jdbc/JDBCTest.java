package com.cui.test.jdbc;

import org.junit.Test;

import java.sql.*;

/**
 * JDBC测试
 * <p>
 * 传统 JDBC 编程：
 * (1 )注册数据库驱动类 ，明确指定数据库 URL 地址、数据库用户名、密码等连接信息 。
 * (2)通过 DriverManager 打开数据库连接 。
 * (3)通过数据库连接创建 Statement对象 。
 * (4)通过 Statement对象执行 SQL语句，得到 ResultSet对象。
 * (5)通过 ResultSet 读取数据，并将数据转换成 JavaBean 对象 。
 * (6)关闭 ResultSet、 Statement 对象以及数据库连接，释放相关资源。
 * 步骤 1~步骤 4 以及步骤 6 在每次操作中都会出现，
 * 在实践中，为了提高代码的可维护性，将上述重复性代码封装到一个类似 DBUtils 的工具类中，后来延伸至ORM框架
 *
 * @author cuiswing
 * @date 2016-08-11
 */
public class JDBCTest {

    private String url = "jdbc:mysql://localhost:3306/user?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";

    /**
     * jdbc原始操作数据
     *
     * @throws SQLException
     */
    public void jdbcOperation() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 1、加载Mysql驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2、获取与数据库的连接
            connection = DriverManager.getConnection(url, "root", "root");
            // 3、获取用于向数据库发送sql语句的statement
            statement = connection.createStatement();
            // 4、向数据库发送sql，并获取结果集
            String sql = "select * from user";
            resultSet = statement.executeQuery(sql);

            //5、取出结果集数据
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                System.out.println(name);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            //6、关闭连接
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception e) {
                    //处理异常
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    //处理异常
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    //处理异常
                }
            }
        }
    }

    public void jdbcPoolOperation() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "select * from user";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String name = resultSet.getString("address");
            System.out.println(name);
        }

        // 关闭资源
        connection.close();
        resultSet.close();
    }

    @Test
    public void testJDBC() throws SQLException {
        JDBCTest jdbcTest = new JDBCTest();
        jdbcTest.jdbcOperation();
    }

    @Test
    public void testDBPool() throws SQLException {
        jdbcPoolOperation();
    }
}
