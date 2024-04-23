package org.insat.gl3.Jdbc.CRUD;

import org.insat.gl3.Jdbc.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcDelete {
    public static void deleteData(String branch, int productId) throws SQLException {
        String query = "DELETE FROM sales WHERE product_id=?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Establish connection based on branch parameter
            if (branch.equalsIgnoreCase("sfax")) {
                connection = ConnectDB.getConnectionSfax();
            } else if (branch.equalsIgnoreCase("sousse")) {
                connection = ConnectDB.getConnectionSousse();
            } else {
                throw new IllegalArgumentException("Invalid branch name.");
            }

            // Create prepared statement
            statement = connection.prepareStatement(query);
            statement.setInt(1, productId);

            // Execute the delete statement
            statement.executeUpdate();
        } finally {
            // Close resources
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }
}
