package com.weather.query;

/**
 * Created by ronald.bhuleskar on 9/27/15.
 */
public class Constants {
    public static final String PROJ_ID = "xenon-diorama-108008";

    public static final String DATASET_ID = "weather";

    public static final String TABLE_ID = "airdata";

    public static final String QUERY_LATEST = "SELECT\n" +
                "  avg(temperature) as temperature, avg(humidity) as humidity, avg(airquality) as airquality,\n" +
                "  avg(lightlevel) as lightlevel, avg(uvlevel) as uvlevel, avg(temperaturebmp) as temperaturebmp,\n" +
                "  avg(pressure) as pressure, avg(no2) as no2, avg(no2ohms) as no2ohms, avg(co) as co, \n" +
                "  avg(coohms) as coohms,\n" +
                "  LEFT(FORMAT_UTC_USEC(UTC_USEC_TO_HOUR(ts)),13) AS day\n" +
                "FROM\n" +
                "  ["+ DATASET_ID + "." + TABLE_ID +"]\n" +
                "WHERE\n" +
                "  ts > %d%n and ts < %d%n\n" +
                "  AND station = %d\n" +
                "GROUP BY day\n" +
                "ORDER BY\n" +
                "  day DESC\n" +
                "LIMIT 1;\n";


        //TODO change inputs
        public static final String QUERY_DAY = "SELECT\n" +
                "  avg(temperature) as temperature, avg(humidity) as humidity, avg(airquality) as airquality,\n" +
                "  avg(lightlevel) as lightlevel, avg(uvlevel) as uvlevel, avg(temperaturebmp) as temperaturebmp,\n" +
            "  avg(pressure) as pressure, avg(no2) as no2, avg(no2ohms) as no2ohms, avg(co) as co, \n" +
            "  avg(coohms) as coohms,\n" +
            "  LEFT(FORMAT_UTC_USEC(UTC_USEC_TO_HOUR(ts)),13) AS day\n" +
            "FROM\n" +
                "  ["+ DATASET_ID + "." + TABLE_ID +"]\n" +
            "WHERE\n" +
            "  ts > %d%n and ts < %d%n\n" +
            "  AND station = %d\n" +
            "GROUP BY day\n" +
            "ORDER BY\n" +
                "  day DESC;";

        public static final String QUERY_MONTH = "SELECT\n" +
                "  avg(temperature) as temperature, avg(humidity) as humidity, avg(airquality) as airquality,\n" +
                "  avg(lightlevel) as lightlevel, avg(uvlevel) as uvlevel, avg(temperaturebmp) as temperaturebmp,\n" +
                "  avg(pressure) as pressure, avg(no2) as no2, avg(no2ohms) as no2ohms, avg(co) as co, \n" +
                "  avg(coohms) as coohms,\n" +
                "  LEFT(FORMAT_UTC_USEC(UTC_USEC_TO_MONTH(ts)),7) AS day\n" +
                "FROM\n" +
                "  [weather.airdata]d\n" +
                "WHERE\n" +
                "  ts > 0 and ts < 99999999999999999\n" +
                "  AND station = 1\n" +
                "GROUP BY day\n" +
                "ORDER BY\n" +
                "  day DESC\n" +
                "LIMIT 12;\n";

}
