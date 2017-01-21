package com.jdroidcoder.services.impl;

import com.jdroidcoder.components.InfluxComponent;
import com.jdroidcoder.dto.BatchPointsDto;
import com.jdroidcoder.dto.PointDto;
import com.jdroidcoder.services.InfluxDBService;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by jdroidcoder on 21.01.17.
 */
@Service
public class InfluxDBServiceImpl implements InfluxDBService {
    @Autowired
    private InfluxComponent influxComponent;

    @Override
    public void createDataBase(String dataBaseName) {
        influxComponent.getInfluxDB().createDatabase(dataBaseName);
    }

    @Override
    public List<String> findAllDataBases() {
        return influxComponent.getInfluxDB().describeDatabases();
    }

    @Override
    public BatchPoints createBatchPoints(BatchPointsDto batchPointsDto) {
        return BatchPoints
                .database(batchPointsDto.getDbName())
                .tag(batchPointsDto.getTag(), batchPointsDto.getTagValue())
                .retentionPolicy(batchPointsDto.getPolicy())
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();
    }

    @Override
    public Point createPoint(PointDto pointDto) {
        return Point.measurement(pointDto.getMeasurement())
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .fields(pointDto.getFields())
                .build();
    }

    @Override
    public void writeToDataBase(BatchPoints batchPoints) {
        influxComponent.getInfluxDB().write(batchPoints);
    }

    @Override
    public QueryResult sendQuery(Query query) {
        return influxComponent.getInfluxDB().query(query);
    }

    @Override
    public void deleteDataBase(String dbName) {
        influxComponent.getInfluxDB().deleteDatabase(dbName);
    }
}
