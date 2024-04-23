package org.insat.gl3.UI;

import org.insat.gl3.Jdbc.CRUD.JdbcRetrieve;
import org.insat.gl3.UI.Dialogs.AddRecordDialog;
import org.insat.gl3.UI.Dialogs.DeleteRecordDialog;
import org.insat.gl3.UI.Dialogs.UpdateRecordDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BranchOfficesUI extends JFrame {
    private JComboBox<String> branchComboBox;
    private JTable table;
    private DefaultTableModel tableModel;

    public BranchOfficesUI() {
        initializeUI();
        populateTable("sfax"); // Default to sfax branch
    }

    private void initializeUI() {
        setTitle("Branch Offices DBs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        JPanel crudPanel = new JPanel();
        crudPanel.setLayout(new FlowLayout());

        JLabel titleLabel = new JLabel("Select a Database");
        crudPanel.add(titleLabel);

        branchComboBox = new JComboBox<>(new String[]{"sfax", "sousse"});
        branchComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedBranch = (String) branchComboBox.getSelectedItem();
                populateTable(selectedBranch);
            }
        });
        crudPanel.add(branchComboBox);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedBranch = (String) branchComboBox.getSelectedItem();
                AddRecordDialog addRecordDialog = new AddRecordDialog(BranchOfficesUI.this, selectedBranch);
                addRecordDialog.setVisible(true);
                populateTable(selectedBranch);
            }
        });
        crudPanel.add(addButton);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Object[] rowData = new Object[tableModel.getColumnCount()];
                    for (int i = 0; i < rowData.length; i++) {
                        rowData[i] = tableModel.getValueAt(selectedRow, i);
                    }
                    String selectedBranch = (String) branchComboBox.getSelectedItem();
                    UpdateRecordDialog updateRecordDialog = new UpdateRecordDialog(BranchOfficesUI.this, selectedBranch, rowData);
                    updateRecordDialog.setVisible(true);
                    populateTable(selectedBranch);
                } else {
                    JOptionPane.showMessageDialog(BranchOfficesUI.this, "Please select a row to update.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        crudPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int productId = (int) tableModel.getValueAt(selectedRow, 0);
                    String selectedBranch = (String) branchComboBox.getSelectedItem();
                    DeleteRecordDialog deleteRecordDialog = new DeleteRecordDialog(BranchOfficesUI.this, selectedBranch, productId);
                    deleteRecordDialog.setVisible(true);
                    populateTable(selectedBranch);
                } else {
                    JOptionPane.showMessageDialog(BranchOfficesUI.this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        crudPanel.add(deleteButton);

        bottomPanel.add(crudPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void populateTable(String branch) {
        try {
            ResultSet resultSet = JdbcRetrieve.retrieveData(branch);

            tableModel = new DefaultTableModel();
            tableModel.setColumnIdentifiers(new String[]{"product_id", "date", "region", "product_name",
                    "quantity", "cost", "tax", "total_sales"});

            while (resultSet.next()) {
                Object[] row = new Object[]{
                        resultSet.getInt("product_id"),
                        resultSet.getString("date"),
                        resultSet.getString("region"),
                        resultSet.getString("product_name"),
                        resultSet.getInt("quantity"),
                        resultSet.getFloat("cost"),
                        resultSet.getFloat("tax"),
                        resultSet.getFloat("total_sales")
                };
                tableModel.addRow(row);
            }

            table.setModel(tableModel);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to retrieve data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
