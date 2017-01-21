package com.jdroidcoder;

import com.jdroidcoder.components.InfluxComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestTAskApplication {

	@Autowired
	InfluxComponent influxComponent;

	public static void main(String[] args) {
		SpringApplication.run(TestTAskApplication.class, args);
	}
}
