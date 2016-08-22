package jdbc;

import java.sql.*;

/**
 * Created by 世祥 on 2016/8/11.
 */
public class JDBCDemo {

    private String url="jdbc:mysql://localhost:3306/jianshouji?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";

    public void jdbcConn() throws ClassNotFoundException, SQLException {
        //JDBC
        Connection connection =null;
        Statement statement =null;
        ResultSet resultSet =null;


        try {
            // 1、加载Mysql驱动
//            Class.forName("com.mysql.cj.jdbc.Driver");
            //2、获取与数据库的连接
//            connection = DriverManager.getConnection(url, "root", "root");
            //3、获取用于向数据库发送sql语句的statement
//            statement = connection.createStatement();
            //4、向数据库发送sql，并获取结果集
//            resultSet = statement.executeQuery("select * from tab_jianshouji_telphone");

            connection=JdbcUtils.getConnection();
            String sql="select * from tab_jianshouji_telphone";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            resultSet=preparedStatement.executeQuery();
            //5、取出结果集数据
            while (resultSet.next())
            {
                String name =  resultSet.getString("TELPHONE_NAME");
                System.out.println(name);
            }
        }finally {
            //6、关闭连接
            if (resultSet!=null){
                try {
                    resultSet.close();
                }catch (Exception e){
                    //处理异常
                }
            }
            if (statement!=null){
                try {
                    statement.close();
                }catch (Exception e){
                    //处理异常
                }
            }
            if (connection!=null){
                try {
                    connection.close();
                }catch (Exception e){
                    //处理异常
                }
            }
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("中文测试");
        JDBCDemo jdbcDemo=new JDBCDemo();
        jdbcDemo.jdbcConn();
    }
}
