package bigquery;

import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.Bigquery.Jobs.GetQueryResults;
import com.google.api.services.bigquery.model.GetQueryResultsResponse;
import com.google.api.services.bigquery.model.QueryRequest;
import com.google.api.services.bigquery.model.QueryResponse;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Runs a synchronous query against Bigtable.
 */
public class SyncQuery {

    protected SyncQuery() {

    }

    /**
     * Perform the given query using the synchronous api.
     */
    public static Iterator<GetQueryResultsResponse> run(final String projectId,
                                                        final String queryString,
                                                        final long waitTime) throws IOException {
        Bigquery bigquery = BigqueryServiceFactory.getService();
        //Wait until query is done with 10 second timeout, at most 5 retries on error
        QueryResponse query = bigquery.jobs().query(
                projectId,
                new QueryRequest().setTimeoutMs(waitTime).setQuery(queryString))
                .execute();

        //Make a request to get the results of the query
        //(timeout is zero since job should be complete)

        GetQueryResults getRequest = bigquery.jobs().getQueryResults(
                query.getJobReference().getProjectId(),
                query.getJobReference().getJobId());


        return BigqueryUtils.getPages(getRequest);
    }
}
