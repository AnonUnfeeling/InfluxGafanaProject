package com.jdroidcoder.controllers;

import com.jdroidcoder.dto.BatchPointsDto;
import com.jdroidcoder.dto.PointDto;
import com.jdroidcoder.services.InfluxDBService;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by jdroidcoder on 20.01.17.
 */
@RestController
public class Controller {
    @Autowired
    private InfluxDBService influxDBService;
    private BatchPoints batchPoints;

    @GetMapping("/createDB/{dbName}")
    public ResponseEntity createDB(@PathVariable String dbName) {
        if (dbName.isEmpty() || dbName.equals(null) || dbName.equals("")) {
            return ResponseEntity.ok("Database name is not valid");
        } else {
            influxDBService.createDataBase(dbName);
            return ResponseEntity.ok("Database was created");
        }
    }

    @GetMapping("/createBatchPoints")
    public ResponseEntity<String> createBatchPoints(@Valid BatchPointsDto batchPointsDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        batchPoints = influxDBService.createBatchPoints(batchPointsDto);
        return ResponseEntity.ok("Batch Point was created");
    }

    @GetMapping("/createPoint")
    public ResponseEntity<String> createPoint(@Valid PointDto pointDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        batchPoints.point(influxDBService.createPoint(pointDto));
        return ResponseEntity.ok("Point was created");
    }

    @GetMapping("/write")
    public ResponseEntity<String> write() {
        influxDBService.writeToDataBase(batchPoints);
        return ResponseEntity.ok("Batch Points was saved");
    }

    @GetMapping("/query/{dbName}")
    public ResponseEntity<List<List<QueryResult.Result>>> query(@PathVariable String dbName) {
        return ResponseEntity.ok(Arrays.asList(influxDBService.sendQuery(new Query("SELECT * FROM cpu", dbName)).getResults()));
    }

    @GetMapping("/test")
    public ResponseEntity<List<List<QueryResult.Result>>> test() {
        String dbName = "test1";
        influxDBService.createDataBase(dbName);

        BatchPoints batchPoints = BatchPoints
                .database(dbName)
                .tag("async", "true")
                .retentionPolicy("autogen")
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();
        Point point1 = Point.measurement("cpu")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("idle", 111111L)
                .addField("user", 9L)
                .addField("system", 1L)
                .build();
        Point point2 = Point.measurement("disk")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("used", 80L)
                .addField("free", 1L)
                .build();
        batchPoints.point(point1);
        batchPoints.point(point2);
        influxDBService.writeToDataBase(batchPoints);
        Query query = new Query("SELECT * FROM cpu", dbName);
        QueryResult queryResult = influxDBService.sendQuery(query);
        return ResponseEntity.ok(Arrays.asList(queryResult.getResults()));
    }
}
