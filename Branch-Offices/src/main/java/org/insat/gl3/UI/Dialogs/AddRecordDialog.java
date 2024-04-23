package org.insat.gl3.UI.Dialogs;

import org.insat.gl3.Jdbc.CRUD.JdbcInsert;
import org.insat.gl3.Send;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class AddRecordDialog extends JDialog {
    private JTextField productIdField;
    private JTextField dateField;
    private JTextField regionField;
    private JTextField productNameField;
    private JTextField quantityField;
    private JTextField costField;
    private JTextField taxField;
    private JTextField totalSalesField;
    private JButton addButton;
    private String selectedBranch;

    public AddRecordDialog(JFrame parent, String selectedBranch) {
        super(parent, "Add Record", true);
        this.selectedBranch = selectedBranch;
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 10, 10));

        productIdField = new JTextField();
        inputPanel.add(new JLabel("Product ID:"));
        inputPanel.add(productIdField);

        dateField = new JTextField();
        inputPanel.add(new JLabel("Date:"));
        inputPanel.add(dateField);

        regionField = new JTextField();
        inputPanel.add(new JLabel("Region:"));
        inputPanel.add(regionField);

        productNameField = new JTextField();
        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(productNameField);

        quantityField = new JTextField();
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);

        costField = new JTextField();
        inputPanel.add(new JLabel("Cost:"));
        inputPanel.add(costField);

        taxField = new JTextField();
        inputPanel.add(new JLabel("Tax:"));
        inputPanel.add(taxField);

        totalSalesField = new JTextField();
        inputPanel.add(new JLabel("Total Sales:"));
        inputPanel.add(totalSalesField);

        add(inputPanel, BorderLayout.CENTER);

        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int productId = Integer.parseInt(productIdField.getText());
                    int quantity = Integer.parseInt(quantityField.getText());
                    float cost = Float.parseFloat(costField.getText());
                    float tax = Float.parseFloat(taxField.getText());
                    float totalSales = Float.parseFloat(totalSalesField.getText());

                    JdbcInsert.insertData(selectedBranch, productId, dateField.getText(), regionField.getText(),
                            productNameField.getText(), quantity, cost, tax, totalSales);

                    // If the database operation succeeds, generate the migration script and send it
                    String migrationScript = generateMigrationScript();
                    Send.sendMigrationScript(migrationScript);

                    // Close the dialog
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddRecordDialog.this, "Invalid number format. Please enter valid numeric values.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(AddRecordDialog.this, "Failed to insert record: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (TimeoutException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });
        add(addButton, BorderLayout.SOUTH);
    }
    private String generateMigrationScript() throws IOException {
        // Get the values of the fields
        int productId = Integer.parseInt(productIdField.getText());
        String date = dateField.getText();
        String region = regionField.getText();
        String productName = productNameField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        float cost = Float.parseFloat(costField.getText());
        float tax = Float.parseFloat(taxField.getText());
        float totalSales = Float.parseFloat(totalSalesField.getText());

        // Generate INSERT statement for the added record
        String insertStatement = String.format("INSERT INTO sales (product_id, date, region, product_name, quantity, cost, tax, total_sales) " +
                "VALUES (%d, '%s', '%s', '%s', %d, %.2f, %.2f, %.2f);", productId, date, region, productName, quantity, cost, tax, totalSales);

        System.out.println(insertStatement);
        return insertStatement;
    }
}

