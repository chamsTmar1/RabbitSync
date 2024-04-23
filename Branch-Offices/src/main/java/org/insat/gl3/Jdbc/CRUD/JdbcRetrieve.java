package org.insat.gl3.Jdbc.CRUD;

import org.insat.gl3.Jdbc.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcRetrieve {
    public static ResultSet retrieveData(String branch) throws SQLException {
        String query = "SELECT * FROM sales";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish connection based on branch parameter
            if (branch.equalsIgnoreCase("sfax")) {
                connection = ConnectDB.getConnectionSfax();
            } else if (branch.equalsIgnoreCase("sousse")) {
                connection = ConnectDB.getConnectionSousse();
            } else {
                throw new IllegalArgumentException("Invalid branch name.");
            }

            // Create statement
            statement = connection.prepareStatement(query);

            // Execute query
            return statement.executeQuery();

        } catch (SQLException ex) {
            // If an exception occurs, close resources and rethrow the exception
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
            throw ex;
        }
    }
}


