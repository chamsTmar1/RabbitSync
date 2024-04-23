package org.insat.gl3.Jdbc.CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcUpdate {
    // Method to update data in the database
    public static void updateData(Connection connection, int productId, String date, String region, String productName, int quantity, float cost, float tax, float totalSales) throws SQLException {
        String query = "UPDATE sales SET date=?, region=?, product_name=?, quantity=?, cost=?, tax=?, total_sales=? WHERE product_id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, date);
        statement.setString(2, region);
        statement.setString(3, productName);
        statement.setInt(4, quantity);
        statement.setFloat(5, cost);
        statement.setFloat(6, tax);
        statement.setFloat(7, totalSales);
        statement.setInt(8, productId);
        statement.executeUpdate();
    }
}

