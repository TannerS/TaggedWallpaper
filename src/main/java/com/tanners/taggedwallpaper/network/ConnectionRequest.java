package com.tanners.taggedwallpaper.network;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ConnectionRequest
{
    private final int TIMEOUT_CONNECTION_MS = 5000;
    private final int TIMEOUT_READ_MS = 10000;
    private final String REQUEST_TYPE = "POST";
    private URL url;
    private HttpURLConnection connection;
    private PrintWriter writer;
    private final String LINE_BREAK = "\r\n";
//    private final String charset = (StandardCharsets.UTF_8.name().toLowerCase(Locale.getDefault()));
    private final String charset = (StandardCharsets.UTF_8.name());

    public ConnectionRequest(String url_str) throws MalformedURLException {
        url = new URL(url_str);
    }

    public boolean openConnection() throws IOException {
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(REQUEST_TYPE);
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestProperty("content-type", "application/json; charset=UTF-8");
            // "application/x-www-form-urlencoded; charset=UTF-8"
            connection.setUseCaches(false);
            connection.setConnectTimeout(TIMEOUT_CONNECTION_MS);
            connection.setReadTimeout(TIMEOUT_READ_MS);
            System.out.println("CHAR SET: " + charset);
            return true;
        }
        catch (ProtocolException ex)
        {
            ex.fillInStackTrace();
            return false;
        }
    }

    public void addBody(String body) {

        try {
            connection.setRequestProperty("content-length", String.valueOf(body.getBytes(charset).length));

            writer = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), charset), true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        writer.append(LINE_BREAK);
        writer.append(body);
        writer.flush();
        writer.close();
    }

//    public List<String> getResponse() throws IOException
    public String getResponse() throws IOException
    {
        StringBuilder builder = new StringBuilder();

//        ArrayList<String> response = new ArrayList<String>();

        int status = connection.getResponseCode();

        if (status == HttpURLConnection.HTTP_OK)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;

            while ((line = reader.readLine()) != null) {
//                response.add(line);
                builder.append(line);
            }

            reader.close();
        }
        else
        {
            throw new IOException("ERROR: " + status);
        }

//        return response;
        return builder.toString();
    }

    public void closeConnection()
    {
        connection.disconnect();
    }
}




























































