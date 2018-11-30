package com.demo1.model;

import java.sql.*;

public class DBConnection {
    private static String url= "jdbc:mysql://localhost:3306/Gobang?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private static String user = "root";
    private static String password = "root1234";
    private static String driver = "com.mysql.jdbc.Driver";

    static {
        try{
            Class.forName(driver);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        try{

            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void free(Connection conn, Statement stmt, ResultSet rs){
        try{
            if(rs != null)
                rs.close();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try{
                if(stmt != null)
                    stmt.close();
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                try{
                    if(conn != null)
                        conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}

