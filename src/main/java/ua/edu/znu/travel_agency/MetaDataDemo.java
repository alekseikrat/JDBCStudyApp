package ua.edu.znu.travel_agency;

import java.sql.*;

public class MetaDataDemo {
    public static void main(String[] args) {
        final String DB_CONNECTION = "jdbc:mysql://localhost:3306/travel_agency";
        final String DB_USER = "root";
        final String DB_PASSWORD = "0Rjhjkm.";
        String strSQL = "SELECT * FROM employees";
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION,
                DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            System.out.println("The " + connection.getCatalog()
                    + " database is connected");
            /*Get database metadata*/
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            /*DBMS info*/
            int majorVersion = databaseMetaData.getDatabaseMajorVersion();
            int minorVersion = databaseMetaData.getDatabaseMinorVersion();
            String productName = databaseMetaData.getDatabaseProductName();
            String productVersion =
                    databaseMetaData.getDatabaseProductVersion();
            System.out.println("We use DBMS: " + productName + "-"
                    + productVersion + "-" + majorVersion + "." + minorVersion);
            /*Driver info*/
            int driverMajorVersion = databaseMetaData.getDriverMajorVersion();
            int driverMinorVersion = databaseMetaData.getDriverMinorVersion();
            String driverName = databaseMetaData.getDriverName();
            System.out.println("We use driver: " + driverName + " версии "
                    + driverMajorVersion + "." + driverMinorVersion);
            /*Get database table list*/
            String catalog = null;
            String schemaPattern = null;
            String tableNamePattern = null;
            String[] types = {"TABLE"};
            ResultSet result = databaseMetaData.getTables(catalog,
                    schemaPattern, tableNamePattern, types);
            System.out.println("Database tables:");
            while (result.next()) {
                System.out.println(result.getString(3));
            }
            /*Get a list of column names and types for a specific table*/
            tableNamePattern = "employees";
            String columnNamePattern = null;
            result = databaseMetaData.getColumns(catalog, schemaPattern,
                    tableNamePattern, columnNamePattern);
            System.out.println("list of column names and types of the table "
                    + tableNamePattern + ":");
            while (result.next()) {
                System.out.println(result.getString(4) + ":"
                        + result.getInt(5));
            }
            /*Getting a list of primary key columns*/
            String schema = null;
            String tableName = "employees";
            result = databaseMetaData.getPrimaryKeys(catalog, schema,
                    tableName);
            System.out.println("list of primary key columns of the table "
                    + tableName + ":");
            while (result.next()) {
                System.out.println(result.getString(4));
            }
            ResultSet rs = statement.executeQuery(strSQL);
            /*Get the metadata of the ResultSet*/
            ResultSetMetaData rmd = rs.getMetaData();
            System.out.println("Table data " + rmd.getTableName(1));
            /*Column numbers start from 1*/
            for (int i = 1; i <= rmd.getColumnCount(); i++) {
                System.out.print(rmd.getColumnName(i) + "\t\t\t");
            }
            System.out.println();
            for (int i = 1; i <= rmd.getColumnCount(); i++) {
                System.out.print(rmd.getColumnClassName(i) + "\t");
            }
            System.out.println();
            for (int i = 1; i <= rmd.getColumnCount(); i++) {
                System.out.print(rmd.getColumnTypeName(i) + "\t\t\t");
            }
            System.out.println();
            while (rs.next()) {
                int employeeId = rs.getInt("employee_id");
                String employeeName = rs.getString("name");
                String employeeSurname = rs.getString("surname");
                int age = rs.getInt("age");
                int tourId = rs.getInt("tour_id");
                System.out.println(employeeId + "\t\t\t" + employeeName + "\t\t\t"
                        + employeeSurname + "\t\t\t" + age + "\t\t\t" + tourId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
