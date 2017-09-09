package io.tanners.taggedwallpaper.network.vision;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ConnectionResponse {
    private final int TIMEOUT_CONNECTION_MS = 5000;
    private final int TIMEOUT_READ_MS = 10000;
    private final String REQUEST_TYPE = "GET";
    private URL url;
    private HttpURLConnection connection;

    public ConnectionResponse(String url_str) throws MalformedURLException {
        url = new URL(url_str);
    }

    public boolean openConnection() throws IOException {
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(REQUEST_TYPE);
            connection.setDoOutput(false);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setUseCaches(false);
            connection.setConnectTimeout(TIMEOUT_CONNECTION_MS);
            connection.setReadTimeout(TIMEOUT_READ_MS);
            return true;
        }
        catch (ProtocolException ex)
        {
            ex.fillInStackTrace();
            return false;
        }
    }

    public List<String> getResponse() throws IOException
    {
        ArrayList<String> response = new ArrayList<String>();

        int status = connection.getResponseCode();

        if (status == HttpURLConnection.HTTP_OK)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;

            while ((line = reader.readLine()) != null) {
                response.add(line);
            }

            reader.close();
        }
        else
        {
            throw new IOException("ERROR: " + status);
        }

        return response;
    }

    public void closeConnection()
    {
        connection.disconnect();
    }
}




























































