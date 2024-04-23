package org.insat.gl3.Jdbc.TestCRUD;

import org.insat.gl3.Jdbc.CRUD.JdbcRetrieve;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TestRetrieve {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter branch (sfax or sousse): ");
        String branch = scanner.nextLine();

        try {
            // Retrieve data from the database
            ResultSet resultSet = JdbcRetrieve.retrieveData(branch);

            // Print column names
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                System.out.print(resultSet.getMetaData().getColumnName(i) + "\t");
            }
            System.out.println(); // Newline

            // Print data
            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    System.out.print(resultSet.getString(i) + "\t");
                }
                System.out.println(); // Newline
            }

            // Close the ResultSet after processing all data
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
