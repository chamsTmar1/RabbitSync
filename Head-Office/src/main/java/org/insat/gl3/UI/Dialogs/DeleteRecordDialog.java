package org.insat.gl3.UI.Dialogs;

import org.insat.gl3.Jdbc.ConnectDB;
import org.insat.gl3.Jdbc.CRUD.JdbcDelete;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class DeleteRecordDialog {
    public static void showDialog(Component parentComponent, int productId) {
        int option = JOptionPane.showConfirmDialog(parentComponent,
                "Are you sure you want to delete this record?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try {
                // Establish a database connection
                Connection connection = ConnectDB.getConnection();

                // Delete the record from the database
                JdbcDelete.deleteData(connection, productId);

                // Close the database connection
                ConnectDB.closeConnection(connection);

                JOptionPane.showMessageDialog(parentComponent, "Record deleted successfully.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parentComponent, "Error deleting record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
