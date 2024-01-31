package com.blit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            return conn;
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // update once connecting to live databasse
//            String url = "demos-database.cdomywmkyglo.us-east-1.rds.amazonaws.com:3306";
            final String url = "jdbc:mysql://localhost:3308/bootcamp";
            final String username = "admin";
            final String password = "password";

            conn = DriverManager.getConnection(url, username, password);
        }

        return conn;
    }
}
