package org.insat.gl3;

import org.insat.gl3.Jdbc.ConnectDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Synchronization {

    // Method to synchronize data with branches
    public static void syncWithDataFromBranches() throws Exception {
        // Get migration script from Receive class
        Receive receive = new Receive();
        String migrationScript = receive.getReceivedMessage();

        // Check if migration script is not empty or null
        if (migrationScript != null && !migrationScript.isEmpty()) {
            // Execute migration script on the Head Office database
            executeMigrationScript(migrationScript);
        } else {
            // Handle the case when migration script is empty or null
            System.out.println("Received migration script is empty or null.");
        }
    }


    // Method to execute migration script on the Head Office database
    public static void executeMigrationScript(String migrationScript) {
        Connection connection = null;
        Statement statement = null;

        try {
            // Establish connection to the Head Office database
            connection = ConnectDB.getConnection();

            // Execute the migration script
            statement = connection.createStatement();
            statement.execute(migrationScript);

            System.out.println("Migration script executed successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
