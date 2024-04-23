package org.insat.gl3;

import org.insat.gl3.UI.BranchOfficesUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BranchOfficesUI branchOfficesUI = new BranchOfficesUI();

                // Display the UI
                branchOfficesUI.setVisible(true);
            }
        });
    }
}

