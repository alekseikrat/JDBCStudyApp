package ua.edu.znu.travel_agency;

import java.sql.*;

public class StatementResultSetDemo {
    public static void main(String[] args) {
        final String DB_CONNECTION = "jdbc:mysql://localhost:3306/travel_agency";
        final String DB_USER = "root";
        final String DB_PASSWORD = "0Rjhjkm.";
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION,
                DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            System.out.println("The " + connection.getCatalog()
                    + " database is connected");
            /*Add data*/
            String strSQL = "INSERT INTO tours VALUES (7,'Львів - Берлін')";
            int rows = statement.executeUpdate(strSQL);
            System.out.println("\n " + rows + " record(s) inserted");
            /*Alternative way*/
            strSQL = "INSERT INTO tours VALUES (8,'Одеса - Відень')";
            boolean res = statement.execute(strSQL);
            /*Read data*/
            System.out.println("Data read after insert, is ResultSet returned: "
                    + res);
            readAllFromTours(statement);
            /*Update data*/
            strSQL = "UPDATE tours SET name = 'Львів - Мадрид' WHERE tour_id =7";
            rows = statement.executeUpdate(strSQL);
            System.out.println("\n " + rows + " record(s) updated");
            System.out.println("Data read after update:");
            readAllFromTours(statement);
            /*Delete data*/
            strSQL = "DELETE FROM tours where `name`='Львів - Мадрид' OR tour_id=8";
            rows = statement.executeUpdate(strSQL);
            System.out.println("\n " + rows + " record(s) deleted");
            System.out.println("Data read after delete:");
            readAllFromTours(statement);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private static void readAllFromTours(final Statement statement)
            throws SQLException {
        String strSQL = "SELECT * FROM tours";
        /*ResultSet auto closed after it Statement close*/
        ResultSet resultSet = statement.executeQuery(strSQL);
        while (resultSet.next()) {
            int tourId = resultSet.getInt("tour_id");
            String tourName = resultSet.getString("name");
            System.out.println("bus_id: " + tourId + ", name: " + tourName);
        }
    }
}
