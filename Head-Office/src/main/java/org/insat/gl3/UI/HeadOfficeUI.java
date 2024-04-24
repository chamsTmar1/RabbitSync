package org.insat.gl3.UI;


import org.insat.gl3.Jdbc.CRUD.JdbcRetrieve;
import org.insat.gl3.Synchronization;
import org.insat.gl3.UI.Dialogs.AddRecordDialog;
import org.insat.gl3.UI.Dialogs.DeleteRecordDialog;
import org.insat.gl3.UI.Dialogs.UpdateRecordDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HeadOfficeUI extends JFrame {
    private JTable dataTable;
    private DefaultTableModel tableModel;

    public HeadOfficeUI() {
        // Set title
        setTitle("Head Office Tunis DB");

        // Create table model
        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);

        // Add columns to the table
        String[] columnNames = {"Product ID", "Date", "Region", "Product Name", "Quantity", "Cost", "Tax", "Total Sales"};
        for (String columnName : columnNames) {
            tableModel.addColumn(columnName);
        }

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(dataTable);

        // Add CRUD buttons
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        // Add action listeners for CRUD buttons
        addButton.addActionListener(e -> {
            AddRecordDialog.showDialog(HeadOfficeUI.this);
            populateTable();
        });


        updateButton.addActionListener(e -> {
            // Get the selected row index
            int selectedRowIndex = dataTable.getSelectedRow();

            // Check if a row is selected
            if (selectedRowIndex == -1) {
                JOptionPane.showMessageDialog(HeadOfficeUI.this, "Please select a row to update.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get the data of the selected row
            Object[] rowData = new Object[tableModel.getColumnCount()];
            for (int i = 0; i < rowData.length; i++) {
                rowData[i] = tableModel.getValueAt(selectedRowIndex, i);
            }

            // Open the update dialog
            UpdateRecordDialog.showDialog(HeadOfficeUI.this, rowData);

            // Refresh the table to reflect the changes
            populateTable();
        });


        deleteButton.addActionListener(e -> {
            // Get the selected row index
            int selectedRowIndex = dataTable.getSelectedRow();

            // Check if a row is selected
            if (selectedRowIndex == -1) {
                JOptionPane.showMessageDialog(HeadOfficeUI.this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get the product ID of the selected row
            int productId = (int) tableModel.getValueAt(selectedRowIndex, 0);

            // Open the delete confirmation dialog
            DeleteRecordDialog.showDialog(HeadOfficeUI.this, productId);

            // Refresh the table to reflect any changes
            populateTable();
        });


        // Add Sync with Branches button
        JButton syncButton = new JButton("Sync with Branches");

        // Add action listener for Sync with Branches button
        syncButton.addActionListener(e -> {
            try {
                Synchronization.syncWithDataFromBranches();
                // Refresh the table to reflect any changes
                populateTable();

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(syncButton);

        // Set layout and add components to the frame
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    // Method to populate the table with data from the database
    public void populateTable() {
        try {
            // Clear existing data from the table
            tableModel.setRowCount(0);

            // Retrieve data from the database
            ResultSet resultSet = JdbcRetrieve.retrieveData();

            // Populate table with data
            while (resultSet.next()) {
                Object[] rowData = {
                        resultSet.getInt("product_id"),
                        resultSet.getString("date"),
                        resultSet.getString("region"),
                        resultSet.getString("product_name"),
                        resultSet.getInt("quantity"),
                        resultSet.getFloat("cost"),
                        resultSet.getFloat("tax"),
                        resultSet.getFloat("total_sales")
                };
                tableModel.addRow(rowData);
            }

            // Close the ResultSet
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
