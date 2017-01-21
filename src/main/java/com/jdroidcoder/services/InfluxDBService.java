package com.jdroidcoder.services;

import com.jdroidcoder.dto.BatchPointsDto;
import com.jdroidcoder.dto.PointDto;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.List;

/**
 * Created by jdroidcoder on 21.01.17.
 */
public interface InfluxDBService {
    void createDataBase(String dataBaseName);

    List<String> findAllDataBases();

    BatchPoints createBatchPoints(BatchPointsDto batchPointsDto);

    Point createPoint(PointDto pointDto);

    void writeToDataBase(BatchPoints batchPoints);

    QueryResult sendQuery(Query query);

    void deleteDataBase(String dbName);
}
