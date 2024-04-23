package org.insat.gl3.Jdbc.TestCRUD;

import org.insat.gl3.Jdbc.CRUD.JdbcUpdate;

import java.sql.SQLException;
import java.util.Scanner;

public class TestUpdate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user to enter branch name
        System.out.print("Enter branch (sfax or sousse): ");
        String branch = scanner.nextLine();

        // Prompt user to enter product ID of the record to update
        System.out.print("Enter Product ID of the record to update: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Prompt user to enter updated sales data
        System.out.println("Enter updated sales data:");
        System.out.print("Date: ");
        String date = scanner.nextLine();
        System.out.print("Region: ");
        String region = scanner.nextLine();
        System.out.print("Product Name: ");
        String productName = scanner.nextLine();
        System.out.print("Quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Cost: ");
        float cost = scanner.nextFloat();
        System.out.print("Tax: ");
        float tax = scanner.nextFloat();
        System.out.print("Total Sales: ");
        float totalSales = scanner.nextFloat();

        // Update data in the specified branch database
        try {
            JdbcUpdate.updateData(branch, productId, date, region, productName, quantity, cost, tax, totalSales);
            System.out.println("Data updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update data: " + e.getMessage());
        }
    }
}

