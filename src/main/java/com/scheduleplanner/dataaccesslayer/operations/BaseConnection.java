package com.scheduleplanner.dataaccesslayer.operations;


import com.scheduleplanner.dataaccesslayer.exception.DatabaseNotAvailableException;
import com.scheduleplanner.dataaccesslayer.config.DatabaseProperties;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;

@Service
public class BaseConnection {
    protected final DatabaseProperties properties;

    public BaseConnection(DatabaseProperties properties) {
        this.properties = properties;
    }


    public Connection createConnection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return  DriverManager.getConnection(
                    "jdbc:mysql://"+properties.url+":"+properties.port+"/"+properties.schema,properties.username,properties.password);
        }catch(Exception e){
            throw new DatabaseNotAvailableException(e) ;
        }
    }
}
