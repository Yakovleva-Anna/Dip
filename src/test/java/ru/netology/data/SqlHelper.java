package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlHelper {
    private static final String url = System.getProperty("datasource.url");
    private static final String user = System.getProperty("datasource.user");
    private static final String password = System.getProperty("datasource.password");
    private static QueryRunner runner = new QueryRunner();

    private SqlHelper() {
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        var conn = getConn();
        var status = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        return runner.query(conn, status, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getCreditStatus() {
        var conn = getConn();
        var status = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        return runner.query(conn, status, new ScalarHandler<>());
    }

    @SneakyThrows
    public static void cleanBase() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM order_entity");
        runner.execute(connection, "DELETE FROM payment_entity");
    }
}
