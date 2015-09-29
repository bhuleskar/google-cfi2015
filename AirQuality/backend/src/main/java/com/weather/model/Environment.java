package com.weather.model;

/**
 * Created by ronald.bhuleskar on 9/27/15.
 */

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "environment")
public class Environment {

    private String temperature;
    private String humidity;
    private String airquality;
    private String lightlevel;
    private String uvlevel;
    private String temperaturebmp;
    private String pressure;
    private String no2;

    public Environment(String temperature, String humidity, String airquality, String lightlevel, String uvlevel, String temperaturebmp, String pressure, String no2, String no2ohms, String co, String coohms, int id) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.airquality = airquality;
        this.lightlevel = lightlevel;
        this.uvlevel = uvlevel;
        this.temperaturebmp = temperaturebmp;
        this.pressure = pressure;
        this.no2 = no2;
        this.no2ohms = no2ohms;
        this.co = co;
        this.coohms = coohms;
        this.id = id;
    }

    private String no2ohms;
    private String co;
    private String coohms;
    private int id;


    public String getUvlevel() {
        return uvlevel;
    }

    @XmlElement
    public void setUvlevel(String uvlevel) {
        this.uvlevel = uvlevel;
    }

    public String getTemperaturebmp() {
        return temperaturebmp;
    }

    @XmlElement
    public void setTemperaturebmp(String temperaturebmp) {
        this.temperaturebmp = temperaturebmp;
    }

    public String getPressure() {
        return pressure;
    }

    @XmlElement
    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getNo2() {
        return no2;
    }

    @XmlElement
    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getNo2ohms() {
        return no2ohms;
    }

    @XmlElement
    public void setNo2ohms(String no2ohms) {
        this.no2ohms = no2ohms;
    }

    public String getCoohms() {
        return coohms;
    }

    @XmlElement
    public void setCoohms(String coohms) {
        this.coohms = coohms;
    }

    public String getAirquality() {
        return airquality;
    }

    @XmlElement
    public void setAirquality(String airquality) {
        this.airquality = airquality;
    }

    public String getHumidity() {
        return humidity;
    }

    @XmlElement
    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTemperature() {
        return temperature;
    }

    @XmlElement
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getLightlevel() {
        return lightlevel;
    }

    @XmlElement
    public void setLightlevel(String lightlevel) {
        this.lightlevel = lightlevel;
    }

    public String getCo() {
        return co;
    }

    @XmlElement
    public void setCo(String co) {
        this.co = co;
    }

    public int getId() {
        return id;
    }

    @XmlAttribute
    public void setId(int id) {
        this.id = id;
    }

    // Must have no-argument constructor
    public Environment() {

    }

    @Override
    public String toString() {
        return new StringBuffer(" Temp : ").append(this.temperature)
                .append(" Air : ").append(this.airquality).append("Light:").append(this.lightlevel)
                .append(" CO : ").append(this.co).append(" ID : ")
                .append(this.id).toString();
    }

}