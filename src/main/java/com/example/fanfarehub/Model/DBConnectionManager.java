package com.example.fanfarehub.Model;

import java.sql.*;

public class DBConnectionManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/webdb";

    private static final String USER = "webuser";

    private static final String PASSWORD = "webuser";

    private static DBConnectionManager instance;

    private DBConnectionManager() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Pb de driver", e);
        }
    }

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static DBConnectionManager getInstance() {
        if (instance == null) {
            synchronized (DBConnectionManager.class) {
                if (instance == null) {
                    instance = new DBConnectionManager();
                }
            }
        }
        
        return instance;
    }
}