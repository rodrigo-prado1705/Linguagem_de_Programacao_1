package com.rodrigo.prado.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    //private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String CONNECTION_PROPERTIES = "?verifyServerCertificate=false&useSSL=true";
    private static final String DATABASE_NAME = "place";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/".concat(DATABASE_NAME)
            .concat(CONNECTION_PROPERTIES);
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";

    private Connection connection;

    public Connection open() {
        try {
            this.connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);

            return this.connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
