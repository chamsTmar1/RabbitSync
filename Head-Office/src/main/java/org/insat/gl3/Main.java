package org.insat.gl3;


import org.insat.gl3.UI.HeadOfficeUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create an instance of HeadOfficeUI
                HeadOfficeUI headOfficeUI = new HeadOfficeUI();

                // Populate the table with data from the database
                headOfficeUI.populateTable();

                // Display the UI
                headOfficeUI.setVisible(true);
            }
        });
    }
}



