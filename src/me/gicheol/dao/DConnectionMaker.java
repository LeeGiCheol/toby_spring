package me.gicheol.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        // D사의 독립적인 방법으로 Connection을 생성

        return null;
    }

}
