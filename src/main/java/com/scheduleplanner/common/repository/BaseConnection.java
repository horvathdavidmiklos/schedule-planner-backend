package com.scheduleplanner.common.repository;


import com.scheduleplanner.common.exception.baseexception.unhandled.GatewayTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public abstract class BaseConnection {

    private final DataSource dataSource;

    @Autowired
    public BaseConnection(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection createConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new GatewayTimeoutException(e);
        }
    }
}
