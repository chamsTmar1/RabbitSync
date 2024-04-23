package org.insat.gl3.UI.Dialogs;

import org.insat.gl3.Jdbc.ConnectDB;
import org.insat.gl3.Jdbc.CRUD.JdbcUpdate;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class UpdateRecordDialog {
    public static void showDialog(Component parentComponent, Object[] rowData) {
        // Extract data from the selected row
        int productId = (int) rowData[0];
        String date = (String) rowData[1];
        String region = (String) rowData[2];
        String productName = (String) rowData[3];
        int quantity = (int) rowData[4];
        float cost = (float) rowData[5];
        float tax = (float) rowData[6];
        float totalSales = (float) rowData[7];

        // Create text fields for input
        JTextField dateField = new JTextField(date, 10);
        JTextField regionField = new JTextField(region, 10);
        JTextField productNameField = new JTextField(productName, 10);
        JTextField quantityField = new JTextField(Integer.toString(quantity), 10);
        JTextField costField = new JTextField(Float.toString(cost), 10);
        JTextField taxField = new JTextField(Float.toString(tax), 10);
        JTextField totalSalesField = new JTextField(Float.toString(totalSales), 10);

        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
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

        int result = JOptionPane.showConfirmDialog(parentComponent, inputPanel, "Update Record", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Get updated input values
            String updatedDate = dateField.getText();
            String updatedRegion = regionField.getText();
            String updatedProductName = productNameField.getText();
            int updatedQuantity = Integer.parseInt(quantityField.getText());
            float updatedCost = Float.parseFloat(costField.getText());
            float updatedTax = Float.parseFloat(taxField.getText());
            float updatedTotalSales = Float.parseFloat(totalSalesField.getText());

            try {
                // Establish a database connection
                Connection connection = ConnectDB.getConnection();

                // Update data in the database
                JdbcUpdate.updateData(connection, productId, updatedDate, updatedRegion, updatedProductName, updatedQuantity, updatedCost, updatedTax, updatedTotalSales);

                // Close the database connection
                ConnectDB.closeConnection(connection);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parentComponent, "Error updating record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
