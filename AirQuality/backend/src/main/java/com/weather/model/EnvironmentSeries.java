package com.weather.model;

/**
 * Created by ronald.bhuleskar on 9/27/15.
 */
public class EnvironmentSeries extends Environment {

    private String time;
    private long latitude;
    private long longitude;

    public EnvironmentSeries() {

    }
    public EnvironmentSeries(String temperature, String humidity, String airquality, String lightlevel, String uvlevel, String temperaturebmp, String pressure, String no2, String no2ohms, String co, String coohms, int id, String time, long latitude, long longitude) {
        super(temperature, humidity, airquality, lightlevel, uvlevel, temperaturebmp, pressure, no2, no2ohms, co, coohms, id);
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public EnvironmentSeries(String time, long latitude, long longitude) {
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
