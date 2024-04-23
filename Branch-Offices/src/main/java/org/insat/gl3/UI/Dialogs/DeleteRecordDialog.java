package org.insat.gl3.UI.Dialogs;

import org.insat.gl3.Jdbc.CRUD.JdbcDelete;
import org.insat.gl3.Send;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class DeleteRecordDialog extends JDialog {
    private JTextField productIdField;
    private JButton deleteButton;
    private String selectedBranch;

    public DeleteRecordDialog(JFrame parent, String selectedBranch, int productId) {
        super(parent, "Delete Record", true);
        this.selectedBranch = selectedBranch;
        setSize(300, 150);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        productIdField = new JTextField(String.valueOf(productId));
        productIdField.setEditable(false);
        inputPanel.add(new JLabel("Product ID:"));
        inputPanel.add(productIdField);

        add(inputPanel, BorderLayout.CENTER);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int productId = Integer.parseInt(productIdField.getText());

                    JdbcDelete.deleteData(selectedBranch, productId);

                    // Generate migration script and send it
                    String migrationScript = generateMigrationScript(productId);
                    sendMigrationScript(migrationScript);

                    // Close the dialog
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(DeleteRecordDialog.this, "Invalid product ID.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(DeleteRecordDialog.this, "Failed to delete record: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(deleteButton, BorderLayout.SOUTH);
    }

    private String generateMigrationScript(int productId) {
        // Generate DELETE statement for the deleted record
        String deleteStatement = String.format("DELETE FROM sales WHERE product_id=%d;", productId);

        return deleteStatement;
    }

    private void sendMigrationScript(String migrationScript) {
        try {
            Send.sendMigrationScript(migrationScript);
            System.out.println("Migration script sent successfully.");
        } catch (IOException | TimeoutException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(DeleteRecordDialog.this, "Failed to send migration script: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
