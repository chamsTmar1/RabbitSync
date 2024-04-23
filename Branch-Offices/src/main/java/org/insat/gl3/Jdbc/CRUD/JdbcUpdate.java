package org.insat.gl3.Jdbc.CRUD;

import org.insat.gl3.Jdbc.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcUpdate {
    public static void updateData(String branch, int productId, String date, String region, String productName,
                                  int quantity, float cost, float tax, float totalSales) throws SQLException {
        String query = "UPDATE sales SET date=?, region=?, product_name=?, quantity=?, cost=?, tax=?, total_sales=? " +
                "WHERE product_id=?";
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
            statement.setString(1, date);
            statement.setString(2, region);
            statement.setString(3, productName);
            statement.setInt(4, quantity);
            statement.setFloat(5, cost);
            statement.setFloat(6, tax);
            statement.setFloat(7, totalSales);
            statement.setInt(8, productId);

            // Execute the update statement
            statement.executeUpdate();
        } finally {
            // Close resources
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }
}

