package org.insat.gl3.Jdbc.CRUD;

import org.insat.gl3.Jdbc.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcRetrieve {
    // Method to retrieve data from the database
    public static ResultSet retrieveData() throws SQLException {
        Connection connection = null;
        try {
            // Establish a database connection
            connection = ConnectDB.getConnection();

            String query = "SELECT * FROM sales";
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } finally {
            // Close the database connection
            /*if (connection != null) {
                ConnectDB.closeConnection(connection);
            }*/
        }
    }
}



