package ua.edu.znu.travel_agency;

import java.sql.*;

public class CallableStatementDemo {
    public static void main(String[] args) {
        final String DB_CONNECTION = "jdbc:mysql://localhost:3306/travel_agency";
        final String DB_USER = "root";
        final String DB_PASSWORD = "0Rjhjkm.";
        String SQL = "{call getEmployeeName (?, ?, ?)}";
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION,
                DB_USER, DB_PASSWORD);
             CallableStatement callableStatement = connection.prepareCall(SQL)) {
            int employeeId = 1;
            callableStatement.setInt(1, employeeId);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.execute();
            String employeeSurname = callableStatement.getString(2);
            String employeeName = callableStatement.getString(3);
            System.out.println("Employee with employeeId=" + employeeId + " is: "
                    + employeeSurname + " " + employeeName);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
