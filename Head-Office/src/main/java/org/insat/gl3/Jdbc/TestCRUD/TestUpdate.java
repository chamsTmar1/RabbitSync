package org.insat.gl3.Jdbc.TestCRUD;

import org.insat.gl3.Jdbc.ConnectDB;
import org.insat.gl3.Jdbc.CRUD.JdbcUpdate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestUpdate {
    public static void main(String[] args) {
        try {
            // Establish a database connection
            Connection connection = ConnectDB.getConnection();

            int productId = 1002;
            String newDate = "2024-04-26";
            String newRegion = "South";
            String newProductName = "Product B";
            int newQuantity = 20;
            float newCost = 60.0f;
            float newTax = 6.0f;
            float newTotalSales = 720.0f;

            // Update data in the database
            JdbcUpdate.updateData(connection, productId, newDate, newRegion, newProductName, newQuantity, newCost, newTax, newTotalSales);

            // Verify that the data has been updated by retrieving it from the database
            verifyUpdate(connection, productId);

            // Close the database connection
            ConnectDB.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void verifyUpdate(Connection connection, int productId) throws SQLException {
        // Query to retrieve the updated data
        String query = "SELECT * FROM sales WHERE product_id = " + productId;

        // Execute the query
        try (ResultSet resultSet = connection.createStatement().executeQuery(query)) {
            if (resultSet.next()) {
                // Data found, print it
                System.out.println("Data updated successfully:");
                System.out.println("Product ID: " + resultSet.getInt("product_id"));
                System.out.println("Date: " + resultSet.getString("date"));
                System.out.println("Region: " + resultSet.getString("region"));
                System.out.println("Product Name: " + resultSet.getString("product_name"));
                System.out.println("Quantity: " + resultSet.getInt("quantity"));
                System.out.println("Cost: " + resultSet.getFloat("cost"));
                System.out.println("Tax: " + resultSet.getFloat("tax"));
                System.out.println("Total Sales: " + resultSet.getFloat("total_sales"));
            } else {
                // No data found
                System.out.println("No data found for Product ID: " + productId);
            }
        }
    }
}

