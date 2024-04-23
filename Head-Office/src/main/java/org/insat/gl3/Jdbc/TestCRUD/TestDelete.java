package org.insat.gl3.Jdbc.TestCRUD;

import org.insat.gl3.Jdbc.ConnectDB;
import org.insat.gl3.Jdbc.CRUD.JdbcDelete;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDelete {
    public static void main(String[] args) {
        try {
            // Establish a database connection
            Connection connection = ConnectDB.getConnection();

            // Define the product ID to delete (replace this with the actual product ID)
            int productIdToDelete = 1001;

            // Delete data from the database
            JdbcDelete.deleteData(connection, productIdToDelete);

            // Verify that the data has been deleted by attempting to retrieve it from the database
            verifyDeletion(connection, productIdToDelete);

            // Close the database connection
            ConnectDB.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void verifyDeletion(Connection connection, int productId) throws SQLException {
        // Query to retrieve the deleted data
        String query = "SELECT * FROM sales WHERE product_id = " + productId;

        // Execute the query
        try (ResultSet resultSet = connection.createStatement().executeQuery(query)) {
            if (resultSet.next()) {
                // Data still exists (not deleted)
                System.out.println("Data was not deleted for Product ID: " + productId);
            } else {
                // Data not found (deleted successfully)
                System.out.println("Data deleted successfully for Product ID: " + productId);
            }
        }
    }
}
