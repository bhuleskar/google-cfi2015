package com.weather.ws;

/**
 * Created by Sridhar on 9/26/15.
 */


import com.google.gson.Gson;
import com.weather.model.Environment;
import com.weather.model.EnvironmentSeries;
import com.weather.query.Analytics;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/airquality")
public class Server {


    @GET
    @Produces("text/plain")
    public String getClichedMessage() {
        return "Hello World";
    }

    @POST
    @Path("/env")
    @Consumes(MediaType.APPLICATION_XML)
    public Response consumeXML( Environment environment) {
        String output = environment.toString();
        return Response.status(200).entity(output).build();
    }

    @POST
    @Path("/fetchcurrent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchCurrent(JSONObject environment) throws JSONException{

        JSONArray name = (JSONArray) environment.get("results");
        JSONObject obj = (JSONObject) name.get(0);

        JSONObject geo = (JSONObject) obj.get("geometry");

        JSONObject location = (JSONObject) geo.get("location");
        String lat = location.getString("lat");
        String lng = location.getString("lng");

        List<EnvironmentSeries> result = Analytics.fetchCurrent(Float.valueOf(lat), Float.valueOf(lng));

        Gson gson = new Gson();
        if (result == null || result.isEmpty()) {
            result = Analytics.fetchCurrent(1, 0, 99999999999999999l);
        }
        return Response.status(200).entity(gson.toJson(result)).build();
    }


    @POST
    @Path("/fetchday")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchDay (JSONObject environment) throws JSONException{

        JSONArray name = (JSONArray) environment.get("results");
        JSONObject obj = (JSONObject) name.get(0);
        JSONObject geo = (JSONObject) obj.get("geometry");
        JSONObject location = (JSONObject) geo.get("location");

        String lat = location.getString("lat");
        String lng = location.getString("lng");

        List<EnvironmentSeries> result = Analytics.fetchDay(Float.valueOf(lat), Float.valueOf(lng));

        Gson gson = new Gson();
        if (result == null || result.isEmpty()) {
            result = Analytics.fetchDay(1, 0, 999999999999l);
        }
        return Response.status(200).entity(gson.toJson(result)).build();
    }


    @POST
    @Path("/fetchmonth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchMonth( JSONObject environment) throws JSONException{

        JSONArray name = (JSONArray) environment.get("results");
        JSONObject obj = (JSONObject) name.get(0);
        JSONObject geo = (JSONObject) obj.get("geometry");
        JSONObject location = (JSONObject) geo.get("location");

        String lat = location.getString("lat");
        String lng = location.getString("lng");

        List<EnvironmentSeries> result = Analytics.fetchMonth(Float.valueOf(lat), Float.valueOf(lng));

        Gson gson = new Gson();
        if (result == null || result.isEmpty()) {
            List<EnvironmentSeries> res = Analytics.fetchMonth(1, 0, 999999999999l);
        }
        return Response.status(200).entity(gson.toJson(result)).build();
    }
}