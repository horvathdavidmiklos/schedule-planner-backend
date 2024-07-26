package integrationtest;

import org.hibernate.id.uuid.CustomVersionOneStrategy;

import java.sql.Connection;
import java.sql.SQLException;

public class H2DbHelper {

    private static final String CREATE_ACCOUNT_TABLE_SQL = """
            CREATE TABLE account (
                email VARCHAR(255) UNIQUE NOT NULL,
                username VARCHAR(255) UNIQUE NOT NULL,
                password_hash VARCHAR(255) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            """;
    private static final String DROP_ACCOUNT_TABLE_SQL = "DROP TABLE IF EXISTS account;";

    public static void createAll(Connection connection) {
        try {
            connection.prepareStatement(CREATE_ACCOUNT_TABLE_SQL).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dropAll(Connection connection)  {
        try {
            connection.prepareStatement(DROP_ACCOUNT_TABLE_SQL).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ;
    }




}
