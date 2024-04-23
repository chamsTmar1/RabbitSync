package org.insat.gl3.UI.Dialogs;

import org.insat.gl3.Jdbc.ConnectDB;
import org.insat.gl3.Jdbc.CRUD.JdbcInsert;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class AddRecordDialog {
    public static void showDialog(Component parentComponent) {
        // Create text fields for input
        JTextField productIdField = new JTextField(10);
        JTextField dateField = new JTextField(10);
        JTextField regionField = new JTextField(10);
        JTextField productNameField = new JTextField(10);
        JTextField quantityField = new JTextField(10);
        JTextField costField = new JTextField(10);
        JTextField taxField = new JTextField(10);
        JTextField totalSalesField = new JTextField(10);

        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        inputPanel.add(new JLabel("Product ID:"));
        inputPanel.add(productIdField);
        inputPanel.add(new JLabel("Date:"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Region:"));
        inputPanel.add(regionField);
        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(productNameField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel("Cost:"));
        inputPanel.add(costField);
        inputPanel.add(new JLabel("Tax:"));
        inputPanel.add(taxField);
        inputPanel.add(new JLabel("Total Sales:"));
        inputPanel.add(totalSalesField);

        int result = JOptionPane.showConfirmDialog(parentComponent, inputPanel, "Add Record", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Get input values
            int productId = Integer.parseInt(productIdField.getText());
            String date = dateField.getText();
            String region = regionField.getText();
            String productName = productNameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            float cost = Float.parseFloat(costField.getText());
            float tax = Float.parseFloat(taxField.getText());
            float totalSales = Float.parseFloat(totalSalesField.getText());

            try {
                // Establish a database connection
                Connection connection = ConnectDB.getConnection();

                // Insert data into the database
                JdbcInsert.insertData(connection, productId, date, region, productName, quantity, cost, tax, totalSales);

                // Close the database connection
                ConnectDB.closeConnection(connection);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parentComponent, "Error adding record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
