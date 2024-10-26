package com.schedule_planner.common.repository.mock;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class DataStructureFake implements DataSource {
    public boolean throwException;
    public Connection returnValue;

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public ConnectionBuilder createConnectionBuilder() throws SQLException {
        return DataSource.super.createConnectionBuilder();
    }

    @Override
    public Connection getConnection() throws SQLException {
        if(throwException) {
            throw new SQLException();
        }
        return returnValue;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        if(throwException) {
            throw new SQLException();
        }
        return returnValue;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
