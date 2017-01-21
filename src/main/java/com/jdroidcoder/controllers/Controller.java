package com.jdroidcoder.controllers;

import com.jdroidcoder.dto.BatchPointsDto;
import com.jdroidcoder.dto.PointDto;
import com.jdroidcoder.services.InfluxDBService;
import org.influxdb.dto.BatchPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
}
