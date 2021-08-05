package com.techelevator.reservations.addressbook.model.dao;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.SQLException;

public class JdbcContactDAOIntegrationTest {


    public static SingleConnectionDataSource dataSource;

    @BeforeClass

    public static void setupDataSource(){
        dataSource = new SingleConnectionDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/addressbook");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        dataSource.setAutoCommit(false);
    }

    @AfterClass
    public static void destoyConnection(){
        dataSource.destroy();
    }

    @After

    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }

    @Before

    public void setup(){

    }

}
