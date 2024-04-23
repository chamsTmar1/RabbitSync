package org.insat.gl3.Jdbc.TestCRUD;

import org.insat.gl3.Jdbc.CRUD.JdbcInsert;

import java.sql.SQLException;
import java.util.Scanner;

public class TestInsert {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user to enter branch name
        System.out.print("Enter branch (sfax or sousse): ");
        String branch = scanner.nextLine();

        // Prompt user to enter sales data
        System.out.println("Enter sales data:");
        System.out.print("Product ID: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
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

        // Insert data into the specified branch database
        try {
            JdbcInsert.insertData(branch, productId, date, region, productName, quantity, cost, tax, totalSales);
            System.out.println("Data inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to insert data: " + e.getMessage());
        }
    }
}

