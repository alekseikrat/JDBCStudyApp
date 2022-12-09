package ua.edu.znu.travel_agency;

import java.sql.*;

public class PreparedStatementDemo {
    public static void main(String[] args) {
        final String DB_CONNECTION = "jdbc:mysql://localhost:3306/travel_agency";
        final String DB_USER = "root";
        final String DB_PASSWORD = "0Rjhjkm.";
        String insertClientTourSQL = "INSERT into clienttour (`clienttour_id`, `client_id`, `tour_id`) VALUES (?, ?, ?)";
        String updateTourIdSQL = "UPDATE clienttour SET tour_id = ? WHERE clienttour_id = ?";
        String selectClientTourSQL = "SELECT * FROM clienttour WHERE clienttour_id = ?";
        String deleteClientTourSQL = "DELETE FROM clienttour where clienttour_id=?";
        String selectAllClientTourSQL = "SELECT * FROM clienttour";
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION,
                DB_USER, DB_PASSWORD);
             PreparedStatement insertClientTourStmt =
                     connection.prepareStatement(insertClientTourSQL);
             PreparedStatement updateTourIdStmt =
                     connection.prepareStatement(updateTourIdSQL);
             PreparedStatement selectClientTourStmt =
                     connection.prepareStatement(selectClientTourSQL);
             PreparedStatement deleteClientTourStmt =
                     connection.prepareStatement(deleteClientTourSQL);
             PreparedStatement selectAllClientTourStmt =
                     connection.prepareStatement(selectAllClientTourSQL)) {
            System.out.println("The " + connection.getCatalog()
                    + " database is connected");
            /*Prepare data to insert*/
            insertClientTourStmt.setInt(1, 2);
            insertClientTourStmt.setInt(2, 6);
            insertClientTourStmt.setInt(3, 3);
            /*Insert data*/
            int rows = insertClientTourStmt.executeUpdate();
            System.out.println("\n " + rows + " record(s) inserted");
            readAllFromClientTour(selectAllClientTourStmt);
            /*Prepare data to update*/
            updateTourIdStmt.setInt(1, 6);
            updateTourIdStmt.setInt(2, 5);
            /*Update data*/
            rows = updateTourIdStmt.executeUpdate();
            System.out.println("\n " + rows + " record(s) updated");
            System.out.println("Data read after insert and update:");
            /*Prepare data to read*/
            selectClientTourStmt.setInt(1, 4);
            /*Read data*/
            ResultSet rs = selectClientTourStmt.executeQuery();
            rs.next();
/*You can select data not by name, but by column numbers,
the first column has number 1*/
            System.out.println("clienttour_id: " + rs.getInt(1) + ", client_id: "
                    + rs.getInt(2) + ", tour_id: "
                    + rs.getInt(3));
            /*Prepare data to delete*/
            deleteClientTourStmt.setInt(1, 4);
            /*Delete data*/
            rows = deleteClientTourStmt.executeUpdate();
            System.out.println("\n " + rows + " record(s) deleted");
            System.out.println("Data read after insert and delete:");
            readAllFromClientTour(selectAllClientTourStmt);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private static void readAllFromClientTour(final PreparedStatement statement)
            throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int clienttourId = resultSet.getInt("clienttour_id");
            int clientId = resultSet.getInt("client_id");
            int tourId = resultSet.getInt("tour_id");
            System.out.println("clienttour_id: " + clienttourId + ", client_id: " + clientId + ", tour_id: " + tourId);
        }
    }
}
