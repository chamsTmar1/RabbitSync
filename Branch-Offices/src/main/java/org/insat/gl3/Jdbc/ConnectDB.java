package org.insat.gl3.Jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String DATABASE_SFAX = "branchofficesfax";
    private static final String DATABASE_SOUSSE = "branchofficesousse";

    public static Connection getConnectionSfax() throws SQLException {
        return DriverManager.getConnection(URL + DATABASE_SFAX, USER, PASSWORD);
    }

    public static Connection getConnectionSousse() throws SQLException {
        return DriverManager.getConnection(URL + DATABASE_SOUSSE, USER, PASSWORD);
    }
}

