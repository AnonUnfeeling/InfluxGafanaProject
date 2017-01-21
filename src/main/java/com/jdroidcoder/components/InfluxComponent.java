package com.jdroidcoder.components;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by jdroidcoder on 21.01.17.
 */
@Component
public class InfluxComponent {

    private InfluxDB influxDB = null;


    @Autowired
    public InfluxComponent(@Value("${influx.host}") String host,
                           @Value("${influx.login}") String login,
                           @Value("${influx.password}") String password) {
        if (influxDB == null) {
            influxDB = InfluxDBFactory.connect(host, login, password);
        }
    }

    public InfluxDB getInfluxDB() {
        return influxDB;
    }
}
