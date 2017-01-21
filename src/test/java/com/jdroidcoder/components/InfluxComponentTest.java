package com.jdroidcoder.components;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jdroidcoder on 21.01.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InfluxComponentTest {
    private final String DATABASE_NAME = "TestDataBase";
    @Autowired
    private InfluxComponent influxComponent;

    @Before
    public void createDataBase() {
        influxComponent.getInfluxDB().createDatabase(DATABASE_NAME);
    }

    @After
    public void removeDataBase() {
        influxComponent.getInfluxDB().deleteDatabase(DATABASE_NAME);
    }

    @Test
    public void checkDataBaseTest() {
        List<String> dataBases = influxComponent.getInfluxDB().describeDatabases();
        assertTrue(dataBases.stream().filter(dbName -> dbName.equals(DATABASE_NAME)).findFirst().isPresent());
    }
}