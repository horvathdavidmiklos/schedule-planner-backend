package com.scheduleplanner.common.repository;

import com.mysql.cj.jdbc.ConnectionImpl;
import com.scheduleplanner.common.exception.baseexception.unhandled.GatewayTimeoutException;
import com.scheduleplanner.common.repository.mock.ConnectionFake;
import com.scheduleplanner.common.repository.mock.DataStructureFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BaseConnectionTest {
    private BaseConnection baseConnection;
    private boolean createConnectionIsCalled;
    private DataStructureFake dataStructureFake;
    @BeforeEach
    void setUp() {
        dataStructureFake = new DataStructureFake();
        createConnectionIsCalled = false;
        baseConnection = new BaseConnection(dataStructureFake) {
            @Override
            public Connection createConnection() {
                createConnectionIsCalled = true;
                return super.createConnection();
            }
        };
    }

    @Test
    void positive() {
        dataStructureFake.throwException=false;
        dataStructureFake.returnValue = new ConnectionFake();
        var result = baseConnection.createConnection();
        assertThat(createConnectionIsCalled).isTrue();
        assertThat(result).isNotNull();
    }

    @Test
    void excpetion() {
        dataStructureFake.throwException=true;
        assertThatThrownBy(()->baseConnection.createConnection())
                .isExactlyInstanceOf(GatewayTimeoutException.class);
    }
}