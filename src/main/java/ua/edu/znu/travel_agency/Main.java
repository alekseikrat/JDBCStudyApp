package ua.edu.znu.travel_agency;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        final String DB_CONNECTION = "jdbc:mysql://localhost:3306/travel_agency";
        final String DB_USER = "root";
        final String DB_PASSWORD = "0Rjhjkm.";
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_CONNECTION);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        config.addDataSourceProperty("poolName", "Hikari Connections Pool");
        config.addDataSourceProperty("maximumPoolSize", "15");
        config.addDataSourceProperty("minimumIdle", "2");
        HikariDataSource dataSource = new HikariDataSource(config);
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("The " + connection.getCatalog()
                    + " database is connected");
            System.out.println("Connection Pool: " + dataSource.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}