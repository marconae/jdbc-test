package de.nae;

import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@UtilityClass
public class ConnectionUtility {

    public static Connection getExasolConnection(String host, String port, String user, String password) throws SQLException {
        final String driverClass = "com.exasol.jdbc.EXADriver";
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            final String msg = String.format("Driver %s not found", driverClass);
            throw new IllegalStateException(msg);
        }

        final String connectionString = String.format("jdbc:exa:%s:%s", host, port);
        return DriverManager.getConnection(connectionString, user, password);
    }

}
