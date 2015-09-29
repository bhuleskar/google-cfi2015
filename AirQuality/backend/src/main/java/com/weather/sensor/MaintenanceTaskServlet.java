package com.weather.sensor;

import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.GetRequest;
import com.google.appengine.api.search.GetResponse;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.StatusCode;
import com.weather.model.Place;
import com.weather.model.PlaceInfo;
import com.weather.model.Sensor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.weather.persistence.OfyService.ofy;

/**
 * HttpServlet for handling maintenance tasks.
 * Builds Sensor Index
 * Created by ronald.bhuleskar on 9/26/15.
 */
public class MaintenanceTaskServlet extends HttpServlet {

    @Override
    public final void doGet(final HttpServletRequest req,
                            final HttpServletResponse resp) throws IOException {

        System.out.println("test big query -- ");

        resp.setContentType("text/plain");
        if (!buildSearchIndexForSensors()) {
            resp.getWriter().println(
                    "MaintenanceTasks failed. Try again by refreshing.");
            return;
        }
        resp.getWriter().println("MaintenanceTasks completed");
    }

    /**
     * Creates the indexes to search for places.
     * @return a boolean indicating the success or failure of the method.
     */
    @SuppressWarnings({"cast", "unchecked"})
    private boolean buildSearchIndexForSensors() {
        Index index = SensorHelper.getIndex();

        removeAllDocumentsFromIndex();

        List<Place> places = ofy().load().type(Place.class).list();

        try {
            for (Place sensor : places) {
                Document placeAsDocument = SensorHelper.buildDocument(
                        sensor.getName(), sensor.getAddress(),
                        sensor.getLocation());
                try {
                    index.put(placeAsDocument);
                } catch (PutException e) {
                    if (StatusCode.TRANSIENT_ERROR
                            .equals(e.getOperationResult().getCode())) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }

//        List<PlaceInfo> pl = SensorHelper.getPlaces(new GeoPt(37.5073225f,-122.1210617f), 100,5);

        return true;
    }

    /**
     * Cleans the index of places from all entries.
     */
    private void removeAllDocumentsFromIndex() {
        Index index = SensorHelper.getIndex();
        // As the request will only return up to 1000 documents,
        // we need to loop until there are no more documents in the index.
        // We batch delete 1000 documents per iteration.
        final int numberOfDocuments = 1000;
        while (true) {
            GetRequest request = GetRequest.newBuilder()
                    .setReturningIdsOnly(true)
                    .build();

            ArrayList<String> documentIds = new ArrayList<>(numberOfDocuments);
            GetResponse<Document> response = index.getRange(request);
            for (Document document : response.getResults()) {
                documentIds.add(document.getId());
            }

            if (documentIds.size() == 0) {
                break;
            }

            index.delete(documentIds);
        }
    }
}
