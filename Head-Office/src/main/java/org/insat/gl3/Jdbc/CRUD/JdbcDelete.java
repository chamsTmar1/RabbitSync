package org.insat.gl3.Jdbc.CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcDelete {
    // Method to delete data from the database
    public static void deleteData(Connection connection, int productId) throws SQLException {
        String query = "DELETE FROM sales WHERE product_id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, productId);
        statement.executeUpdate();
    }
}

