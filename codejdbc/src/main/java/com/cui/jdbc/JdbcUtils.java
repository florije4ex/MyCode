package com.cui.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC工具类，用于获取连接和释放连接
 * Created by 世祥 on 2016/8/14.
 */
public class JdbcUtils {

    private static JdbcPool jdbcPool = new JdbcPool();

    /**
     * 获取数据库连接
     *
     * @return 数据库连接
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return jdbcPool.getConnection();
    }

    /**
     * 释放数据库连接
     *
     * @param conn 数据库连接
     * @param st   Statement
     * @param rs   ResultSet
     */
    public static void release(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                //处理异常
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (Exception e) {
                //处理异常
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                //处理异常
            }
        }
    }
}
