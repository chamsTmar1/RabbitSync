package org.insat.gl3.Jdbc.TestCRUD;

import org.insat.gl3.Jdbc.CRUD.JdbcDelete;

import java.sql.SQLException;
import java.util.Scanner;

public class TestDelete {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user to enter branch name
        System.out.print("Enter branch (sfax or sousse): ");
        String branch = scanner.nextLine();

        // Prompt user to enter product ID of the record to delete
        System.out.print("Enter Product ID of the record to delete: ");
        int productId = scanner.nextInt();

        // Delete data from the specified branch database
        try {
            JdbcDelete.deleteData(branch, productId);
            System.out.println("Data deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to delete data: " + e.getMessage());
        }
    }
}

