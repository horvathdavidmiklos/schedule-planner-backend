package com.scheduleplanner.dataaccesslayer.operations.authorization;

import com.scheduleplanner.dataaccesslayer.config.DatabaseProperties;
import com.scheduleplanner.dataaccesslayer.exception.UnknownSqlException;
import com.scheduleplanner.dataaccesslayer.operations.BaseConnection;
import com.sheduleplanner.common.entity.Account;
import com.sheduleplanner.common.entity.NewAccount;
import com.sheduleplanner.common.exception.baseexception.handled.ValueNotUniqueException;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AccountHandler extends BaseConnection {
    private final static String SAVE_ACCOUNT_SQL =
            "INSERT INTO account (email, username, password_hash, created_at) VALUES (?, ?, ?, ?)";
    private final static String IS_UNIQUE_EMAIL_SQL =
            "SELECT COUNT(*) FROM account WHERE email = ?";
    private final static String IS_UNIQUE_USERNAME_SQL =
            "SELECT COUNT(*) FROM account WHERE username = ?";
    private final static String FIND_ACCOUNT_BY_EMAIL_SQL = "SELECT email,username,password_hash FROM account WHERE email =?";
    private final static String FIND_ACCOUNT_BY_USERNAME_SQL = "SELECT username,password_hash FROM account WHERE username =?";


    public AccountHandler(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }

    public void save(NewAccount account) {
        try (Connection connection = createConnection();
             PreparedStatement stmt = connection.prepareStatement(SAVE_ACCOUNT_SQL)) {
            stmt.setString(1, account.email());
            stmt.setString(2, account.username());
            stmt.setString(3, account.passwordHash());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(account.createdAt()));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating account failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new UnknownSqlException(e);
        }
    }

    public boolean isUniqueUsername(String username) {
        return isUnique(IS_UNIQUE_USERNAME_SQL, username);
    }


    public boolean isUniqueEmail(String email) {
        return isUnique(IS_UNIQUE_EMAIL_SQL, email);
    }

    public boolean isUnique(String sql,String field){
        try (Connection connection = createConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, field);
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

    public Account findByEmail(String email) {
        return getAccount(email, FIND_ACCOUNT_BY_EMAIL_SQL);
    }

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
                } return null;
            }
        } catch (SQLException e) {
            throw new UnknownSqlException(e);
        }
    }
}
