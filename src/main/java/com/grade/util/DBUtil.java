package com.grade.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;

/**
 * 数据库连接工具类（简易连接池）
 * 统一管理数据库连接，提供获取连接和关闭资源的方法
 */
public class DBUtil {

    private static final LinkedList<Connection> pool = new LinkedList<>();
    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    private static int maxActive = 20;
    private static int currentActive = 0;

    static {
        try {
            // 从类路径加载 db.properties
            InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            if (is == null) {
                throw new RuntimeException("找不到 db.properties 配置文件，请确保其位于类路径根目录");
            }
            Properties props = new Properties();
            props.load(is);
            is.close();

            driver = props.getProperty("db.driver");
            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
            maxActive = Integer.parseInt(props.getProperty("db.maxActive", "20"));
            int initialSize = Integer.parseInt(props.getProperty("db.initialSize", "5"));

            Class.forName(driver);

            // 初始化连接池
            for (int i = 0; i < initialSize; i++) {
                pool.add(createConnection());
            }
            currentActive = initialSize;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("数据库配置加载失败", e);
        }
    }

    private static Connection createConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("创建数据库连接失败", e);
        }
    }

    /**
     * 从连接池获取连接
     */
    public static synchronized Connection getConnection() {
        if (!pool.isEmpty()) {
            return pool.removeFirst();
        }
        if (currentActive < maxActive) {
            currentActive++;
            return createConnection();
        }
        throw new RuntimeException("连接池已满，请稍后再试");
    }

    /**
     * 归还连接到连接池
     */
    public static synchronized void releaseConnection(Connection conn) {
        if (conn != null) {
            pool.addLast(conn);
        }
    }

    /**
     * 关闭资源（Connection 归还到连接池，不真正关闭）
     */
    public static void close(ResultSet rs, PreparedStatement ps, Connection conn) {
        if (rs != null) {
            try { rs.close(); } catch (SQLException ignored) {}
        }
        if (ps != null) {
            try { ps.close(); } catch (SQLException ignored) {}
        }
        releaseConnection(conn);
    }

    public static void close(PreparedStatement ps, Connection conn) {
        close(null, ps, conn);
    }
}
