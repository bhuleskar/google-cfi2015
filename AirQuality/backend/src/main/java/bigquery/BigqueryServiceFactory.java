package bigquery;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.BigqueryScopes;
import com.weather.query.Constants;

import java.io.IOException;
import java.util.Collection;


/**
 * This class creates our Service to connect to Bigquery including auth.
 */
public final class BigqueryServiceFactory {

    private static Bigquery service = null;
    private static Object serviceLock = new Object();

    private BigqueryServiceFactory() {

    }

    /**
     * Threadsafe Factory that provides an authorized Bigquery service.
     */
    public static Bigquery getService() throws IOException {
        if (service == null) {
            synchronized (serviceLock) {
                if (service == null) {
                    service = createAuthorizedClient();
                }
            }
        }
        return service;
    }

    /**
     * Creates an authorized client to Google Bigquery.
     */
    private static Bigquery createAuthorizedClient() throws IOException {
        // Create the credential
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        GoogleCredential credential = GoogleCredential.getApplicationDefault(transport, jsonFactory);

        if (credential.createScopedRequired()) {
            Collection<String> bigqueryScopes = BigqueryScopes.all();
            credential = credential.createScoped(bigqueryScopes);
        }

        return new Bigquery.Builder(transport, jsonFactory, credential)
                .setApplicationName(Constants.PROJ_ID).build();
    }
}
