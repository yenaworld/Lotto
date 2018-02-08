package com.yenaworld.rotto;

import java.io.IOException;
import java.util.Map;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;

import com.yenaworld.rotto.vo.NumberVo;

public class InfluxdbController {

    private static InfluxDB influxDB;

    // http://10.107.95.45:8086 admin admin testTuple
    public static void setUp(String ip, String id, String pw) throws InterruptedException, IOException {

        influxDB = InfluxDBFactory.connect(ip, id, pw);

        boolean influxDBstarted = false;
        do {
            Pong response;
            try {
                response = influxDB.ping();
                if (!response.getVersion().equalsIgnoreCase("unknown")) {
                    influxDBstarted = true;
                }
            } catch (Exception e) {
                // NOOP intentional
                e.printStackTrace();
            }
            Thread.sleep(100L);
        } while (!influxDBstarted);
        // influxDB.setLogLevel(LogLevel.FULL);

        System.out.println("# influxdb connect");
    }

    public void createDB(String dbName) {
        influxDB.createDatabase(dbName);
    }

    public void deleteDB(String dbName) {
        influxDB.deleteDatabase(dbName);
    }

    public static void sendInflux(String name, String partition, long value) {
        BatchPoints batchPoints = BatchPoints.database("testTuple").retentionPolicy("default").build();

        Point point1 = Point.measurement(name).tag("number", partition).addField("value", value).build();
        batchPoints.point(point1);

        influxDB.write(batchPoints);

    }

    public static void sendInfluxMap(String name, Map<String, Long> map) {
        BatchPoints batchPoints = BatchPoints.database("testTuple").tag("async", "true").retentionPolicy("default").build();

        for (Map.Entry<String, Long> elem : map.entrySet()) {

            Point point = Point.measurement(name).tag("year", elem.getKey()).tag("month", elem.getKey()).tag("date", elem.getKey()).tag("number", elem.getKey()).addField("value", elem.getValue()).build();
            batchPoints.point(point);

            // System.out.println(String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()));
        }

        influxDB.write(batchPoints);

    }
    
    public static void sendInfluxByNumber(String name, NumberVo vo) {
        BatchPoints batchPoints = BatchPoints.database("testTuple").tag("async", "true").retentionPolicy("default").build();
        
        String[] date = vo.getDate().split("-");
        
        Point point = Point.measurement(name).tag("year", date[0]).tag("month", date[1]).tag("date", date[2])
                .addField(String.valueOf(vo.getOne()), 1).addField(String.valueOf(vo.getTwo()), 1).addField(String.valueOf(vo.getThree()), 1)
                .addField(String.valueOf(vo.getFour()), 1).addField(String.valueOf(vo.getFive()), 1).addField(String.valueOf(vo.getSix()), 1)
                .addField(String.valueOf(vo.getBonus()), 1).build();
        batchPoints.point(point);

        influxDB.write(batchPoints);

    }
    
    public static void sendInflux(String name, NumberVo vo) {
        BatchPoints batchPoints = BatchPoints.database("testTuple").tag("async", "true").retentionPolicy("default").build();
        
        String[] date = vo.getDate().split("-");
        
        Point point = Point.measurement(name).tag("year", date[0]).tag("month", date[1]).tag("date", date[2])
                .addField("value1", vo.getOne()).addField("value2", vo.getTwo()).addField("value3", vo.getThree())
                .addField("value4", vo.getFour()).addField("value5", vo.getFive()).addField("value6", vo.getSix())
                .addField("value7", vo.getBonus()).build();
        batchPoints.point(point);

        influxDB.write(batchPoints);

    }
}