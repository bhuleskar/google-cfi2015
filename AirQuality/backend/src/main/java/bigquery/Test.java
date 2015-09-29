package bigquery;

import com.google.api.services.bigquery.Bigquery;
import com.weather.model.Environment;
import com.weather.model.EnvironmentSeries;
import com.weather.query.Analytics;

import org.codehaus.jettison.json.JSONObject;

import java.util.List;

import junk.ListDatasetsProjects;
import junk.StreamingSample;

/**
 * Created by ronald.bhuleskar on 9/26/15.
 */
public class Test {
    public static void main(String[] args) throws Exception{

        Bigquery bigquery = BigqueryServiceFactory.getService();
        String projId = "xenon-diorama-108008";

        ListDatasetsProjects.listDatasets(bigquery, projId);

//        String query = String.format(Constants.QUERY_DAY, 0l, 99999999999999999l, 1);
//        Iterator<GetQueryResultsResponse> pages = SyncQuery.run(projId, query, 1000);
//        while (pages.hasNext()) {
//            BigqueryUtils.printRows(pages.next().getRows(), System.out);
//        }

//        String json = "[{\"ts\": 1443302845221, \"station\": 3, \"temperature\": 44, \"humidity\": 37.9, \"co\":0.07}]";
//        String json = "[{\"co\": 1}]";



        Environment d = new Environment();
        d.setCo("1.1");
        d.setHumidity("2.2");
        d.setId(1);
        d.setTemperature("23.5");

        StreamingSample.insertNewRow(projId, "weather", "airdata", d);

        List<EnvironmentSeries> environmentSeries = Analytics.fetchCurrent(1, 0l, 99999999999999999l);
        JSONObject json = new JSONObject();
        json.put("AirQuality", environmentSeries);
        System.out.println(json);

    }
}
