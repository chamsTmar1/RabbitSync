package org.insat.gl3.UI.Dialogs;

import org.insat.gl3.Jdbc.CRUD.JdbcUpdate;
import org.insat.gl3.Send;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class UpdateRecordDialog extends JDialog {
    private JTextField productIdField;
    private JTextField dateField;
    private JTextField regionField;
    private JTextField productNameField;
    private JTextField quantityField;
    private JTextField costField;
    private JTextField taxField;
    private JTextField totalSalesField;
    private JButton updateButton;
    private String selectedBranch;

    public UpdateRecordDialog(JFrame parent, String selectedBranch, Object[] rowData) {
        super(parent, "Update Record", true);
        this.selectedBranch = selectedBranch;
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 10, 10));

        productIdField = new JTextField(rowData[0].toString());
        productIdField.setEditable(false);
        inputPanel.add(new JLabel("Product ID:"));
        inputPanel.add(productIdField);

        dateField = new JTextField(rowData[1].toString());
        inputPanel.add(new JLabel("Date:"));
        inputPanel.add(dateField);

        regionField = new JTextField(rowData[2].toString());
        inputPanel.add(new JLabel("Region:"));
        inputPanel.add(regionField);

        productNameField = new JTextField(rowData[3].toString());
        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(productNameField);

        quantityField = new JTextField(rowData[4].toString());
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);

        costField = new JTextField(rowData[5].toString());
        inputPanel.add(new JLabel("Cost:"));
        inputPanel.add(costField);

        taxField = new JTextField(rowData[6].toString());
        inputPanel.add(new JLabel("Tax:"));
        inputPanel.add(taxField);

        totalSalesField = new JTextField(rowData[7].toString());
        inputPanel.add(new JLabel("Total Sales:"));
        inputPanel.add(totalSalesField);

        add(inputPanel, BorderLayout.CENTER);

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int productId = Integer.parseInt(productIdField.getText());
                    int quantity = Integer.parseInt(quantityField.getText());
                    float cost = Float.parseFloat(costField.getText());
                    float tax = Float.parseFloat(taxField.getText());
                    float totalSales = Float.parseFloat(totalSalesField.getText());

                    JdbcUpdate.updateData(selectedBranch, productId, dateField.getText(), regionField.getText(),
                            productNameField.getText(), quantity, cost, tax, totalSales);

                    // Generate migration script and send it
                    String migrationScript = generateMigrationScript();
                    sendMigrationScript(migrationScript);

                    // Close the dialog
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(UpdateRecordDialog.this, "Invalid number format. Please enter valid numeric values.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(UpdateRecordDialog.this, "Failed to update record: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(updateButton, BorderLayout.SOUTH);
    }

    private String generateMigrationScript() {
        // Get the values of the fields
        int productId = Integer.parseInt(productIdField.getText());
        String date = dateField.getText();
        String region = regionField.getText();
        String productName = productNameField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        float cost = Float.parseFloat(costField.getText());
        float tax = Float.parseFloat(taxField.getText());
        float totalSales = Float.parseFloat(totalSalesField.getText());

        // Generate UPDATE statement for the updated record
        String updateStatement = String.format("UPDATE sales SET date='%s', region='%s', product_name='%s', " +
                        "quantity=%d, cost=%.2f, tax=%.2f, total_sales=%.2f WHERE product_id=%d;", date, region, productName,
                quantity, cost, tax, totalSales, productId);

        return updateStatement;
    }

    private void sendMigrationScript(String migrationScript) {
        try {
            Send.sendMigrationScript(migrationScript);
            System.out.println("Migration script sent successfully.");
        } catch (IOException | TimeoutException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(UpdateRecordDialog.this, "Failed to send migration script: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
