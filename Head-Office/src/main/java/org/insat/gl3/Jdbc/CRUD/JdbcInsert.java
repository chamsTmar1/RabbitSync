package org.insat.gl3.Jdbc.CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcInsert {
    // Method to insert data into the database
    public static void insertData(Connection connection, int productId, String date, String region, String productName, int quantity, float cost, float tax, float totalSales) throws SQLException {
        String query = "INSERT INTO sales (product_id, date, region, product_name, quantity, cost, tax, total_sales) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, productId);
        statement.setString(2, date);
        statement.setString(3, region);
        statement.setString(4, productName);
        statement.setInt(5, quantity);
        statement.setFloat(6, cost);
        statement.setFloat(7, tax);
        statement.setFloat(8, totalSales);
        statement.executeUpdate();
    }
}

