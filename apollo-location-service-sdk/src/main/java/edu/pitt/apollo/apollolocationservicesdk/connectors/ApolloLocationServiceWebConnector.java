package edu.pitt.apollo.apollolocationservicesdk.connectors;

import edu.pitt.apollo.apollolocationservicesdk.exception.ApolloLocationCacheException;
import edu.pitt.apollo.apollolocationservicesdk.exception.ApolloLocationServicesUnreachableException;
import edu.pitt.apollo.apollolocationservicesdk.interfaces.ApolloLocationServiceConnectorInterface;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jettison.json.JSONException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Created by mas400 on 7/7/16.
 */
public class ApolloLocationServiceWebConnector implements ApolloLocationServiceConnectorInterface {

    @Override
    public ApolloLocationServiceConnectorInterface getNextConnector() {
        return null;
    }

    @Override
    public String getLocationByID(String locationCode) throws JSONException, IOException, ApolloLocationServicesUnreachableException, ApolloLocationCacheException {
        String locationJSON = callURL("https://betaweb.rods.pitt.edu/ls/api/locations/"
                + locationCode + "?format=geojson");
        return locationJSON;
    }

    @Override
    public String getLocationByName(String locationName, String encompassingRegion) throws JSONException, IOException, ApolloLocationServicesUnreachableException, ApolloLocationCacheException {
        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        String locationJSON = "";
        try {
            HttpPost request = new HttpPost("https://betaweb.rods.pitt.edu/ls/api/locations/find-by-term");
            StringEntity params = new StringEntity("{\"queryTerm\":\"" + locationName + "\"}");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream inStream = entity.getContent();
                locationJSON = convertStreamToString(inStream);
                inStream.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return locationJSON;
    }

    @Override
    public String getLocationByCoordinate(String latitude, String longitude) {
        return null;
    }

    @Override
    public String getUniqueLocationNames(String locationName) {
        return null;
    }

    @Override
    public String getLocationsByType(String locationTypeID) {
        return null;
    }

    public static String callURL(String myURL) throws ApolloLocationServicesUnreachableException {
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            URL url = new URL(myURL);
            urlConn = url.openConnection();

            if (urlConn != null) {
                urlConn.setReadTimeout(60 * 1000);
            }
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (FileNotFoundException ex) {
            System.out.println(myURL);
            throw new ApolloLocationServicesUnreachableException(myURL);
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:" + myURL,
                    e);
        }

        return sb.toString();
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
