package ua.edu.znu.travel_agency;

import java.sql.*;

public class TransactionDemo {
    public static void main(String[] args) {
        final String DB_CONNECTION = "jdbc:mysql://localhost:3306/travel_agency";
        final String DB_USER = "root";
        final String DB_PASSWORD = "0Rjhjkm.";
        String insertTourSQL = "INSERT into tours (tour_id, name) VALUES (?, ?)";
        String updateEmployeeTourSQL = "UPDATE employees SET tour_id = ? WHERE employee_id =?";
        String selectEmployeeSQL = "SELECT employee_id, e.surname, e.name, t.name FROM employees e INNER JOIN tours t ON e.tour_id=" +
                " t.tour_id WHERE employee_id =?";
        /*Set rollback point*/
        Savepoint tourInsertedSavePoint = null;
/*Create the connection object and the PreparedStatement statement
that is not included in the transaction with an external
try-with-resources block*/
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION,
                DB_USER, DB_PASSWORD);
             PreparedStatement selectEmployeeStmt =
                     connection.prepareStatement(selectEmployeeSQL)) {
            System.out.println("The " + connection.getCatalog()
                    + " database is connected");
/*Create the PreparedStatement statements that is included
in the transaction with an internal try-with-resources block*/
            try (PreparedStatement insertTourStmt =
                         connection.prepareStatement(insertTourSQL);
                 PreparedStatement updateEmployeeTourStmt =
                         connection.prepareStatement(updateEmployeeTourSQL)) {
                /*Transaction begin*/
                connection.setAutoCommit(false);
                /*Prepare data to insert - new route add*/
                insertTourStmt.setInt(1, 7);
                insertTourStmt.setInt(2,
                        8);
/*If the first parameter equals 4, the SQLException
will be thrown and the transaction rollbacks*/
                //insertTourStmt.setString(3, "Львів - Мадрид");
                /*Insert data*/
                int rows = insertTourStmt.executeUpdate();
                System.out.println("\n " + rows + " record(s) inserted");
                tourInsertedSavePoint = connection.setSavepoint("Tour "
                        + "inserted");
                /*Prepare data to update - set bus to the new route*/
                updateEmployeeTourStmt.setInt(1, 5);
/*If the first parameter equals 3, the SQLException
will be thrown and the transaction rollbacks
to tourInsertedSavePoint*/
                updateEmployeeTourStmt.setInt(2, 3);
                rows = updateEmployeeTourStmt.executeUpdate();
                System.out.println("\n " + rows + " record(s) updated");
                /*Transaction commit*/
                connection.commit();
                System.out.println("The transaction from the operations "
                        + "of adding a tour and assigning a employee to it "
                        + "was successfully completed");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
/*If an SQLException was thrown while adding new tour,
rollback the entire transaction*/
                if (tourInsertedSavePoint == null) {
                    connection.rollback();
                    System.out.println("Transaction rollback");
                } else {
/*If an SQLException was thrown when assigning a new employee
to the tour, rollback the transaction
to routeInsertedSavePoint*/
                    connection.rollback(tourInsertedSavePoint);
                    System.out.println("Transaction rollback "
                            + "to tourInsertedSavePoint");
                    /*Transaction commit*/
                    connection.commit();
                    System.out.println("The transaction from the operations "
                            + "of adding a tour was successfully completed");
                }
            } //end of the internal try-with-resources
/*Return the connection to the default mode: every operation
is auto-committed*/
            connection.setAutoCommit(true);
            System.out.println("\nData read for employee with employee_id=1:");
            selectEmployeeStmt.setInt(1, 1);
            ResultSet rs = selectEmployeeStmt.executeQuery();
            rs.next();
/*You can select data not by name, but by column numbers,
the first column has number 1*/
            System.out.println("employee_id: " + rs.getInt(1) + ", employee_surname: "
                    + rs.getString(2) + ", employee_name: "
                    + rs.getString(3) + ", tour_name: "
                    + rs.getString(4));
        } catch (SQLException ex) {
            ex.printStackTrace();
        } //end of the external try-with-resources
    }
}
