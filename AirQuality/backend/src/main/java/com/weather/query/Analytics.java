package com.weather.query;

import com.google.api.services.bigquery.model.GetQueryResultsResponse;
import com.google.api.services.bigquery.model.TableRow;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.log.AppLogLine;
import com.weather.model.EnvironmentSeries;
import com.weather.model.PlaceInfo;
import com.weather.sensor.SensorHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import bigquery.SyncQuery;

/**
 * Created by ronald.bhuleskar on 9/27/15.
 */
public class Analytics {

    static final AppLogLine LOG = new AppLogLine();

    public static List<EnvironmentSeries> fetchCurrent(float lat, float lon) {
        List<PlaceInfo> placeInfos = SensorHelper.getPlaces(new GeoPt(lat,lon), 200000, 100);
        List<EnvironmentSeries> result = new ArrayList<>();

        for (PlaceInfo placeInfo : placeInfos) {
            LOG.setLogMessage("place : '" + placeInfo.getName() + "'"+ placeInfo.getLocation());
            result.addAll(fetchCurrent(Integer.parseInt(placeInfo.getName()), 0l, 99999999999999999l));
        }
        return result;
    }

    public static List<EnvironmentSeries> fetchDay(float lat, float lon) {
        List<PlaceInfo> placeInfos = SensorHelper.getPlaces(new GeoPt(lat,lon), 200000, 100);
        List<EnvironmentSeries> result = new ArrayList<>();

        for (PlaceInfo placeInfo : placeInfos) {
            LOG.setLogMessage("place : '" + placeInfo.getName() + "'"+ placeInfo.getLocation());
            result.addAll(fetchDay(Integer.parseInt(placeInfo.getName()), 0l, 99999999999999999l));
        }
        return result;
    }

    public static List<EnvironmentSeries> fetchMonth(float lat, float lon) {
        List<PlaceInfo> placeInfos = SensorHelper.getPlaces(new GeoPt(lat,lon), 200000, 100);
        List<EnvironmentSeries> result = new ArrayList<>();

        for (PlaceInfo placeInfo : placeInfos) {
            LOG.setLogMessage("place : '" + placeInfo.getName() + "'"+ placeInfo.getLocation());
            result.addAll(fetchMonth(Integer.parseInt(placeInfo.getName()), 0l, 99999999999999999l));
        }
        return result;
    }


    public static List<EnvironmentSeries> fetchCurrent(int station, long startTime, long endTime) {
        String query = String.format(Constants.QUERY_LATEST, startTime, endTime, station);
        List<EnvironmentSeries> result = new ArrayList<>();

        try {
            Iterator<GetQueryResultsResponse> pages = SyncQuery.run(Constants.PROJ_ID, query, 1000);
            while (pages.hasNext()) {
                result.addAll(rowToEnv(station, pages.next().getRows()));
                LOG.setLogMessage("found a station");
            }
            return result;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<EnvironmentSeries> fetchDay(int station, long startTime, long endTime) {
        String query = String.format(Constants.QUERY_DAY, startTime, endTime, station);
        List<EnvironmentSeries> result = new ArrayList<>();

        try {
            Iterator<GetQueryResultsResponse> pages = SyncQuery.run(Constants.PROJ_ID, query, 1000);
            while (pages.hasNext()) {
                result.addAll(rowToEnv(station, pages.next().getRows()));
                LOG.setLogMessage("found a station");
            }
            return result;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<EnvironmentSeries> fetchMonth(int station, long startTime, long endTime) {
        String query = String.format(Constants.QUERY_MONTH, startTime, endTime, station);
        List<EnvironmentSeries> result = new ArrayList<>();

        try {
            Iterator<GetQueryResultsResponse> pages = SyncQuery.run(Constants.PROJ_ID, query, 1000);
            while (pages.hasNext()) {
                result.addAll(rowToEnv(station, pages.next().getRows()));
                LOG.setLogMessage("found a station");
            }
            return result;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<EnvironmentSeries> rowToEnv(int station, List<TableRow> rows) {
        List<EnvironmentSeries> result = new ArrayList<>();

        for (TableRow row : rows) {
            LOG.setLogMessage("found a row");
            for (int i = 0; i< row.size(); i++) {
                //String temperature, String humidity, String airquality, String lightlevel,
                // String uvlevel, String temperaturebmp, String pressure, String no2, String no2ohms,
                // String co, String coohms, int id, String time, long latitude, long longitude
                String currentDate = (new SimpleDateFormat("dd/MM/yy HH:mm")).format(new Date());

                EnvironmentSeries env = new EnvironmentSeries(
                        ""+ row.getF().get(0).getV(),
                        ""+ row.getF().get(1).getV(),
                        ""+ row.getF().get(2).getV(),
                        ""+ row.getF().get(3).getV(),
                        ""+ row.getF().get(4).getV(),
                        ""+ row.getF().get(5).getV(),
                        ""+ row.getF().get(6).getV(),
                        ""+ row.getF().get(7).getV(),
                        ""+ row.getF().get(8).getV(),
                        ""+ row.getF().get(9).getV(),
                        ""+ row.getF().get(10).getV(),
                        station,
                        (row.getF().size() > 10 ? ""+row.getF().get(11).getV() : currentDate),
                        getLatitude(station),
                        getLongitude(station));

                result.add(env);
            }
        }
        return result;
    }

    private static long getLatitude(int station) {
        if (SensorHelper.latitudeMap == null ||
                SensorHelper.latitudeMap.get(station) == null) return 0;
        return SensorHelper.latitudeMap.get(station).longValue();
    }
    private static long getLongitude(int station) {
        if (SensorHelper.longitudeMap == null ||
                SensorHelper.longitudeMap.get(station) == null) return 0;
        return SensorHelper.longitudeMap.get(station).longValue();
    }


}
