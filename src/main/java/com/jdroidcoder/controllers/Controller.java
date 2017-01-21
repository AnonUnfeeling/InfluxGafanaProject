package com.jdroidcoder.controllers;

import com.jdroidcoder.dto.BatchPointsDto;
import com.jdroidcoder.dto.PointDto;
import com.jdroidcoder.services.InfluxDBService;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

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
}
