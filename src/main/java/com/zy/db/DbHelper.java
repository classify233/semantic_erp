package com.zy.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DbHelper {
    /**
     * 常见错误
     * 1、在ResultSet迭代的时候，没有用<=导致没有获得当前数据行的列的最后的一列数据，
     * 这个错误会导致，在获取之以后给实体bean对象赋值的时候抛出空指针异常
     * 2、pstmt.setObject(i + 1, params.get(i));正确的
     * pstmt.setObject(i, params.get(i));错误的写法没有把数据参数的索引从1开始
     */

    // 连接数据库
    private static Connection connection;
    // 执行带预处理的sql语句
    private static PreparedStatement pstmt;

    // 接受数据的查询的返回结果集
    private static ResultSet rs;

    private static String sqlurlString =
            "jdbc:mysql://127.0.0.1:3306/erp?characterEncoding=UTF-8";
    private static String dbname = "root";
    private static String dbpwd = "123456";

    /**
     * 获得数据库的连接的方法
     *
     * @return
     */
    public static Connection getConnction() {

        // 获得驱动连接数据库
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = java.sql.DriverManager.getConnection(sqlurlString, dbname, dbpwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * 执行查询的通用方法
     *
     * @param sql
     * @param params
     * @return
     */
    public static ArrayList<HashMap<String, Object>> executeQuery(
            String sql, List<Object> params) {

        ArrayList<HashMap<String, Object>> resultArrayList = null;
        // 执行查询，必须先判断是否有正常的数据连接
        try {
            if (null == connection || connection.isClosed()) {
                getConnction();
            }
            pstmt = connection.prepareStatement(sql);
            // 参数绑定
            bindParams(params);
            rs = pstmt.executeQuery();
            if (null != rs) {
                resultArrayList = new ArrayList<>();
                while (rs.next()) {
                    // 获得数据库表的结构
                    ResultSetMetaData rsmdData = rs.getMetaData();
                    HashMap<String, Object> rowMap = new HashMap<>();
                    for (int j = 1; j <= rsmdData.getColumnCount(); j++) {
                        rowMap.put(rsmdData.getColumnName(j).toLowerCase(), rs.getObject(j));
                    }
                    resultArrayList.add(rowMap);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }

        return resultArrayList;
    }

    /**
     * 把参数绑定到PreparedStatement
     * @param params
     * @throws SQLException
     */
    private static void bindParams(List<Object> params) throws SQLException {
        if (null != params) {
            if (params.size() > 0) {
                for (int i = 0; i < params.size(); i++) {
                    //预处理的pstmt对象设置值
                    pstmt.setObject(i + 1, params.get(i));
                }
            }
        }
    }

    /**
     * 该方法执行数据库数据的添加，删除，和修改的操作
     *
     * @param sql    执行的T-SQL 语句，也就是结构化查询语句
     * @param params 执行sql语句有可能用到的参数的集合
     * @return int
     */
    public static int executeSave(String sql, List<Object> params) {
        int result = 0;

        try {
            //1、判断数据库的连接状态，如果没有连接则连接数据库，先获得数据库的连接
            if (null == connection || connection.isClosed()) {
                getConnction();
            }
            //2、执行预处理的sql语句对象，PreparedStatement，有可能该sql有相关的参数
            //执行该对象，需要一个有效的SQL语句
            pstmt = connection.prepareStatement(sql);
            //3、判断是否有参数，如果有则进行参数的绑定
            bindParams(params);
            //4、执行sql语句，获得返回的执行结果，会是int返回数据中受影响的行数
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //5、执行资源的回收
            closeAll();
        }

        return result;
    }

    /**
     * 执行插入语句，成功返回主键，失败返回NULL
     *
     * @param sql    执行的SQL INSERT语句，也就是结构化查询语句
     * @param params 执行sql语句有可能用到的参数的集合
     * @return
     */
    public static Object executeInsert(String sql, List<Object> params) {
        Object primaryKey = null;

        try {
            //1、判断数据库的连接状态，如果没有连接则连接数据库，先获得数据库的连接
            if (null == connection || connection.isClosed()) {
                getConnction();
            }
            //2、执行预处理的sql语句对象，PreparedStatement，有可能该sql有相关的参数
            //执行该对象，需要一个有效的SQL语句
            pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //3、判断是否有参数，如果有则进行参数的绑定
            bindParams(params);
            //4、执行sql语句，获得返回的执行结果，会是int返回数据中受影响的行数
            int result = pstmt.executeUpdate();
            if (result > 0) {  // 插入成功，更新primaryKey
                ResultSet rs = pstmt.getGeneratedKeys();   // 返回主键的结果集
                if (rs.next()) {
                    primaryKey = rs.getObject(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //5、执行资源的回收
            closeAll();
        }

        return primaryKey;
    }


    public static void closeAll() {
        try {
            if (null != rs) {
                rs.close();
                rs = null;
            }
            if (null != pstmt) {
                pstmt.close();
                pstmt = null;
            }
            if (null != connection) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
