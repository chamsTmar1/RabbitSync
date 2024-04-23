package org.insat.gl3.Jdbc.TestCRUD;

import org.insat.gl3.Jdbc.ConnectDB;
import org.insat.gl3.Jdbc.CRUD.JdbcInsert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestInsert {
    public static void main(String[] args) {
        try {
            // Establish a database connection
            Connection connection = ConnectDB.getConnection();

            // Define the values to insert (replace these with actual values)
            int productId = 1003;
            String date = "2024-04-25";
            String region = "North";
            String productName = "Product A";
            int quantity = 10;
            float cost = 50.0f;
            float tax = 5.0f;
            float totalSales = 550.0f;

            // Insert data into the database
            JdbcInsert.insertData(connection, productId, date, region, productName, quantity, cost, tax, totalSales);

            // Verify that the data has been inserted by retrieving it from the database
            verifyInsert(connection, productId);

            // Close the database connection
            ConnectDB.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void verifyInsert(Connection connection, int productId) throws SQLException {
        // Query to retrieve the inserted data
        String query = "SELECT * FROM sales WHERE product_id = " + productId;

        // Execute the query
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                // Data found, print it
                System.out.println("Data inserted successfully:");
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
