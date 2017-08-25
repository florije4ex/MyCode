package com.cui.jdbc;

import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 数据库连接池
 * Created by cuishixiang on 2017-08-25.
 */
public class JdbcPool implements DataSource {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JDBCOperate.class);

    /**
     * 使用LinkedList存放数据库连接
     * 为什么不使用ArrayList？
     * 因为涉及到对列表的不断增删（会不断的从list中获取和归还连接）
     * 如果使用ArrayList则性能较差，不如LinkedList快
     */
    private static LinkedList<Connection> connList = new LinkedList<>();

    /**
     * 因为只在第一次加载时即创建出所有的连接，所以写在stati代码块中
     */
    static {
        try {
            //获取数据库连接的配置文件
            InputStream inputStream = JdbcPool.class.getClassLoader().getResourceAsStream("db.properties");
            Properties ps = new Properties();
            ps.load(inputStream);

            String driver = ps.getProperty("driver");
            String url = ps.getProperty("url");
            String username = ps.getProperty("username");
            String password = ps.getProperty("password");

            // 1、加载Mysql驱动
            Class.forName(driver);

            //数据库的连接数可写成可配置的变量由客户端传进来，此处简单写成了初始化10个连接
            for (int i = 0; i < 10; i++) {
                Connection conn = DriverManager.getConnection(url, username, password);
                connList.add(conn);
            }
        } catch (Exception e) {
            logger.error("数据库连接池初始化异常", e);
        }
    }

    /**
     * 获取数据库连接
     *
     * @return 数据库连接对象
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        if (connList.size() > 0) {

            /**
             * removeFirst()方法是弹出list中的第一个元素，因为客户在获取连接时，该连接不能再放在list中
             * 但是这样就会有个问题：在客户使用完连接后，客户会直接调用connection.close()方法来关闭连接
             * 这样就没有把连接还给连接池，于是就有问题了。不能直接把连接给客户。
             * 解决思路：让客户在调用close()方法时不是关闭连接而是将连接返回给连接池
             * 解决方法：
             * 1、写一个子类，覆写close()方法
             * 2、写一个Connection的包装类，增强close()方法。（装饰设计模式Decorate）
             * 3、用动态代理，返回一个代理对象出去，拦截close()方法，对其进行增强
             * 分析：
             * 1、采用子类，由于connection对象中封装了一些信息（private），子类虽然可以覆写close()方法，
             * 但是客户拿到的该子类对象无法操作使用；（不能实现）
             * 2、装饰设计模式：实现见下面的MyConnection类，这样由于close()已被改写，客户在调用close()时，
             * 不再是关闭数据库连接而是将其归还到了连接池，但是问题来了，Connection的方法太多了，对于不想
             * 增强的方法必须得一个一个的调用被增强对象的相同方法，这样虽然可以实现，但是太累了；（不推荐）
             * 3、动态代理，只对想增强的方法进行增强即可，代码少，可行。（注意：动态代理的性能不是太好）
             * 下面采用动态代理实现
             */
//            return connList.removeFirst();

            final Connection conn = connList.removeFirst();

            return (Connection) Proxy.newProxyInstance(JdbcPool.class.getClassLoader(), conn.getClass().getInterfaces(), new InvocationHandler() {
                //调用的所有方法均会被invoke方法拦截
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    //如果调用方法的名称是close则处理,否则不处理
                    if (method.getName().equals("close")) {
                        return connList.add(conn);
                    } else {
                        return method.invoke(conn, args);
                    }
                }
            });
        } else {
            //若连接都被占用了，可抛出自定义的异常或者将连接池扩容
            throw new RuntimeException("对不起，数据库忙，请稍后重试！");
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}

/**
 * 装饰设计模式对Connection进行增强
 * 固定写法：
 * 1、写一个类实现与被增强对象相同的接口
 * 2、类中定义一个变量，指向被增强对象
 * 3、定义一个构造方法，接收被增强对象
 * 4、覆盖想增强的方法
 * 5、对于不想增强的方法，直接调用被增强对象的方法
 */
//class MyConnection implements Connection{
//
//    private Connection connection;
//    private List poolList;
//
//    public MyConnection(Connection connection,List poolList){
//        this.connection=connection;
//        this.poolList=poolList;
//    }
//
//    /**
//     * 只覆写想增强的方法
//     * @throws SQLException
//     */
//    public void close() throws SQLException {
//        poolList.add(connection);
//
//    }
//
//    /**
//     * 对于不想增强的方法，直接调用被增强对象的方法，其他方法太多了就省略不写了
//     */
//    public Statement createStatement() throws SQLException {
//        return this.connection.createStatement();
//    }
//
//    public PreparedStatement prepareStatement(String sql) throws SQLException {
//        return null;
//    }
//
//    public CallableStatement prepareCall(String sql) throws SQLException {
//        return null;
//    }
//
//    public String nativeSQL(String sql) throws SQLException {
//        return null;
//    }
//
//    public void setAutoCommit(boolean autoCommit) throws SQLException {
//
//    }
//
//    public boolean getAutoCommit() throws SQLException {
//        return false;
//    }
//
//    public void commit() throws SQLException {
//
//    }
//
//    public void rollback() throws SQLException {
//
//    }
//
//
//
//    public boolean isClosed() throws SQLException {
//        return false;
//    }
//
//    public DatabaseMetaData getMetaData() throws SQLException {
//        return null;
//    }
//
//    public void setReadOnly(boolean readOnly) throws SQLException {
//
//    }
//
//    public boolean isReadOnly() throws SQLException {
//        return false;
//    }
//
//    public void setCatalog(String catalog) throws SQLException {
//
//    }
//
//    public String getCatalog() throws SQLException {
//        return null;
//    }
//
//    public void setTransactionIsolation(int level) throws SQLException {
//
//    }
//
//    public int getTransactionIsolation() throws SQLException {
//        return 0;
//    }
//
//    public SQLWarning getWarnings() throws SQLException {
//        return null;
//    }
//
//    public void clearWarnings() throws SQLException {
//
//    }
//
//    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
//        return null;
//    }
//
//    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
//        return null;
//    }
//
//    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
//        return null;
//    }
//
//    public Map<String, Class<?>> getTypeMap() throws SQLException {
//        return null;
//    }
//
//    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
//
//    }
//
//    public void setHoldability(int holdability) throws SQLException {
//
//    }
//
//    public int getHoldability() throws SQLException {
//        return 0;
//    }
//
//    public Savepoint setSavepoint() throws SQLException {
//        return null;
//    }
//
//    public Savepoint setSavepoint(String name) throws SQLException {
//        return null;
//    }
//
//    public void rollback(Savepoint savepoint) throws SQLException {
//
//    }
//
//    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
//
//    }
//
//    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
//        return null;
//    }
//
//    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
//        return null;
//    }
//
//    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
//        return null;
//    }
//
//    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
//        return null;
//    }
//
//    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
//        return null;
//    }
//
//    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
//        return null;
//    }
//
//    public Clob createClob() throws SQLException {
//        return null;
//    }
//
//    public Blob createBlob() throws SQLException {
//        return null;
//    }
//
//    public NClob createNClob() throws SQLException {
//        return null;
//    }
//
//    public SQLXML createSQLXML() throws SQLException {
//        return null;
//    }
//
//    public boolean isValid(int timeout) throws SQLException {
//        return false;
//    }
//
//    public void setClientInfo(String name, String value) throws SQLClientInfoException {
//
//    }
//
//    public void setClientInfo(Properties properties) throws SQLClientInfoException {
//
//    }
//
//    public String getClientInfo(String name) throws SQLException {
//        return null;
//    }
//
//    public Properties getClientInfo() throws SQLException {
//        return null;
//    }
//
//    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
//        return null;
//    }
//
//    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
//        return null;
//    }
//
//    public void setSchema(String schema) throws SQLException {
//
//    }
//
//    public String getSchema() throws SQLException {
//        return null;
//    }
//
//    public void abort(Executor executor) throws SQLException {
//
//    }
//
//    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
//
//    }
//
//    public int getNetworkTimeout() throws SQLException {
//        return 0;
//    }
//
//    public <T> T unwrap(Class<T> iface) throws SQLException {
//        return null;
//    }
//
//    public boolean isWrapperFor(Class<?> iface) throws SQLException {
//        return false;
//    }
//}