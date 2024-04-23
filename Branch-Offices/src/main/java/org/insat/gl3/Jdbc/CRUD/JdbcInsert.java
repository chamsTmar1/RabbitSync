package org.insat.gl3.Jdbc.CRUD;

import org.insat.gl3.Jdbc.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcInsert {
    public static void insertData(String branch, int productId, String date, String region, String productName,
                                  int quantity, float cost, float tax, float totalSales) throws SQLException {
        String query = "INSERT INTO sales (product_id, date, region, product_name, quantity, cost, tax, total_sales) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
            statement.setString(2, date);
            statement.setString(3, region);
            statement.setString(4, productName);
            statement.setInt(5, quantity);
            statement.setFloat(6, cost);
            statement.setFloat(7, tax);
            statement.setFloat(8, totalSales);

            // Execute the insert statement
            statement.executeUpdate();
        } finally {
            // Close resources
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }
}

