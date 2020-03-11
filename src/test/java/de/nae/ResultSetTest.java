package de.nae;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ResultSetTest {

    private static final String HOST = "localhost";
    private static final String PORT = "8899";
    private static final String USER = "sys";
    private static final String PWD = "exasol";
    private static final String QUERY = "SELECT 'a' AS c1 FROM dual;";

    @Test
    void singleValueShouldBeObtained() throws SQLException {
        try (
                final Connection connection = ConnectionUtility.getExasolConnection(HOST, PORT, USER, PWD);
                final Statement statement = connection.createStatement();
                final ResultSet resultSet = statement.executeQuery(QUERY)
        ) {
            checkResultSet(resultSet);
        }
    }

    @Test
    void singleValueShouldBeObtained_2() throws SQLException {
        try (
                final Connection connection = ConnectionUtility.getExasolConnection(HOST, PORT, USER, PWD);
                final Statement statement = connection.createStatement();
        ) {
            assertThat(statement.execute(QUERY)).isTrue();

            try (final ResultSet resultSet = statement.getResultSet()) {
                checkResultSet(resultSet);
            }
        }
    }

    private void checkResultSet(ResultSet resultSet) throws SQLException {
        assertThat(resultSet.getMetaData().getColumnCount()).isEqualTo(1);
        assertThat(resultSet.getMetaData().getColumnName(1)).isEqualTo("C1");
        assertThat(resultSet.first()).isTrue();
        assertThat(resultSet.getString(1)).isEqualTo("a");
    }
}