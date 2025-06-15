package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=PRJ301;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "hans";
    private static final String PASS = "123456azsx@H";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
