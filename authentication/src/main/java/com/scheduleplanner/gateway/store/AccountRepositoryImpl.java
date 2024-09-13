package com.scheduleplanner.gateway.store;

import com.scheduleplanner.common.repository.BaseConnection;
import com.scheduleplanner.common.exception.baseexception.handled.ValueNotUniqueException;
import com.scheduleplanner.common.exception.baseexception.unhandled.UnknownSqlException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AccountRepositoryImpl extends BaseConnection  implements AccountRepository {
    private final static String SAVE_ACCOUNT_SQL =
            "INSERT INTO unverified (email, username, password_hash, created_at, token) VALUES (?, ?, ?, ?, ?)";
    private final static String IS_UNIQUE_EMAIL_SQL =
            "SELECT COUNT(*) FROM (SELECT email FROM unverified WHERE email = ? UNION SELECT email FROM account WHERE email = ?)";
    private final static String IS_UNIQUE_USERNAME_SQL =
            "SELECT COUNT(*) FROM (SELECT username FROM unverified WHERE username = ? UNION SELECT username FROM account WHERE email = ?)";
    private final static String FIND_ACCOUNT_BY_EMAIL_SQL =
            "SELECT email,username,password_hash FROM unverified WHERE email =?";
    private final static String FIND_ACCOUNT_BY_USERNAME_SQL =
            "SELECT username,password_hash FROM unverified WHERE username =?";
    private final static String SAVE_VERIFIED_ACCOUNT_SQL = "INSERT INTO account (email, username, password_hash, created_at) " +
            "SELECT email, username, password_hash, created_at FROM unverified WHERE username = ?";
    private  final static String DELETE_UNVERIFIED_ACCOUNT_SQL = "DELETE FROM unverified WHERE username = ?;";



    public AccountRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void saveVerifiedAccount(String username) {

    }

    @Override
    public void saveUnverified(NewAccount account) {
        try (Connection connection = createConnection();
             PreparedStatement stmt = connection.prepareStatement(SAVE_ACCOUNT_SQL)) {
            stmt.setString(1, account.email());
            stmt.setString(2, account.username());
            stmt.setString(3, account.passwordHash());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(account.createdAt()));
            stmt.setString(5, account.token());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new UnknownSqlException(e);
        }
    }

    @Override
    public boolean isUniqueUsername(String username) {
        return isUnique(IS_UNIQUE_USERNAME_SQL, username);
    }


    @Override
    public boolean isUniqueEmail(String email) {
        return isUnique(IS_UNIQUE_EMAIL_SQL, email);
    }

    @Override
    public Account findByEmail(String email) {
        return getAccount(email, FIND_ACCOUNT_BY_EMAIL_SQL);
    }

    @Override
    public Account findByUsername(String username) {
        return getAccount(username, FIND_ACCOUNT_BY_USERNAME_SQL);
    }

    private Account getAccount(String username, String findAccountByUsernameSql) {
        try (Connection connection = createConnection();
             PreparedStatement stmt = connection.prepareStatement(findAccountByUsernameSql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Account()
                            .username(rs.getString("username"))
                            .passwordHash(rs.getString("password_hash"));
                }
                return null;
            }
        } catch (SQLException e) {
            throw new UnknownSqlException(e);
        }
    }

    private boolean isUnique(String sql, String field) {
        try (Connection connection = createConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, field);
            stmt.setString(2, field);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return 0 == rs.getInt(1);
                } else {
                    throw new ValueNotUniqueException(field);
                }
            }
        } catch (SQLException e) {
            throw new UnknownSqlException(e);
        }
    }
}
