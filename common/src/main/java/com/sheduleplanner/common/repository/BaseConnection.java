package com.sheduleplanner.common.repository;


import com.sheduleplanner.common.exception.baseexception.unhandled.DatabaseNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class BaseConnection {

    private final DataSource dataSource;

    @Autowired
    public BaseConnection(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection createConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DatabaseNotAvailableException(e);
        }
    }
}
