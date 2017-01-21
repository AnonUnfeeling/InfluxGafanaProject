package com.jdroidcoder.components;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by jdroidcoder on 21.01.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GraphiteComponentTest {
    @Autowired
    private GraphiteComponent graphiteComponent;

    @Before
    public void openConnect() throws IOException {
        graphiteComponent.getGraphite().connect();
    }

    @After
    public void closeConnect() throws IOException {
        graphiteComponent.getGraphite().close();
    }

    @Test
    public void isConnectTest(){
        assertTrue(graphiteComponent.getGraphite().isConnected());
    }

    @Test
    public void disconnectTest() throws IOException {
        graphiteComponent.getGraphite().close();
        assertFalse(graphiteComponent.getGraphite().isConnected());
    }
}