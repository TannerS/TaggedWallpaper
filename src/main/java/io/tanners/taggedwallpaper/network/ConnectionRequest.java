package io.tanners.taggedwallpaper.network;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
//import java.nio.charset.StandardCharsets;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ConnectionRequest {
    // type of connection
    public static enum TYPES {
        POST("POST"),
        GET("GET");

        private String type;

        TYPES(String type) {
            this.type = type;
        }

        public String type() {
            return type;
        }

    }

    private HttpURLConnection connection;
    private int mConnectionTimeOut;
    private int mReadTimeOut;
    private HashMap<String, String> mEntries;
    private String mBody;
    private String mUrl;
    private String mRequestype;
    private PrintWriter writer;
    private String mCharset;
    private final String LINE_BREAK = "\r\n";

    public ConnectionRequest(String mUrl) {
        mEntries = new HashMap<String, String>();
        mConnectionTimeOut = 5000;
        mReadTimeOut = 5000;
        mBody = null;
        mRequestype = TYPES.GET.toString();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            mCharset = (StandardCharsets.UTF_8.name().toLowerCase(Locale.getDefault()));
        else
            mCharset = "utf-8";

        this.mUrl = mUrl;
    }

    /**
     * set connecton type
     * @param type
     */
    public void setRequestType(TYPES type) {
        mRequestype = type.toString();
    }

    /**
     * charste for data
     * @param charset
     */
    public void setCharSet(String charset)
    {
        mCharset = charset;
    }

    /**
     * timeout
     * @param time
     */
    public void setConnectionTimeOut(int time)
    {
        mConnectionTimeOut = time;
    }

    /**
     * reading timeout
     * @param time
     */
    public void setReadTimeOut(int time)
    {
        mReadTimeOut = time;
    }

    /**
     * add passed in headers to http packet
     * @param entries
     */
    public void addRequestHeader(HashMap<String, String> entries)
    {
        if(entries != null) {

            for (Map.Entry<String, String> entry : entries.entrySet()) {
                String key = (entry.getKey()).trim();
                String value = (entry.getValue()).trim();
                this.mEntries.put(key, value);
            }
        }
    }

    /**
     * add passed in header to http packet
     * @param key
     * @param value
     */
    public void addRequestHeader(String key, String value)
    {
        if(mEntries != null) {
            this.mEntries.put(key.trim(), value.trim());
        }
    }

    /**
     * add body
     * @param body
     */
    public void addBasicBody(String body)
    {
        this.mBody = body;
    }

    /**
     * this will set the headers into the connection since the add header methods can be called multiple times
     */
    private void setHeaders()
    {
        if(mEntries != null) {

            for (Map.Entry<String, String> entry : mEntries.entrySet()) {
                String key = (entry.getKey()).trim();
                String value = (entry.getValue()).trim();
                connection.setRequestProperty(key, value);
            }
        }
    }

    /**
     * set body
     */
    private void setBody()
    {
        if(mBody == null || mBody.length() <= 0)
            // set body length
            connection.setFixedLengthStreamingMode(0);
        else {
            // set body length
            connection.setFixedLengthStreamingMode(mBody.length());

            try {
                // open stream to url
                writer = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), mCharset), true);
                // write to stream
                writer.append(LINE_BREAK);
                writer.append(mBody).append(LINE_BREAK);
                writer.flush();
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                // close stream
                writer.close();
            }
        }
    }

    /**
     * defauly settings
     */
    private void setDefaults()
    {
        if(mBody != null)
            // means we will be sending content
            connection.setDoOutput(true);
        else
            // means we will be NOT sending content
            connection.setDoOutput(false);
        // allow redirects
        connection.setInstanceFollowRedirects(false);
        // timeouts
        connection.setConnectTimeout(mConnectionTimeOut);
        connection.setReadTimeout(mReadTimeOut);
        // caches, not tested
        connection.setUseCaches(true);
        try {
            // method type
            connection.setRequestMethod(mRequestype);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        // set charset
        connection.setRequestProperty("charset", mCharset);
    }

    /**
     * connect to url
     * @return
     */
    public boolean connect() throws IOException {
        // open connect from url object
        connection = (HttpURLConnection) (new URL(mUrl)).openConnection();
        // set options
        setDefaults();
        setHeaders();
        setBody();

        return (connection.getResponseCode() == HttpURLConnection.HTTP_OK);
    }

    /**
     * close connection
     */
    public void closeConnection()
    {
        if(connection != null)
        {
            connection.disconnect();
        }
    }

    /**
     * get current connection
     * @return
     */
    public HttpURLConnection getConnection()
    {
        return this.connection;
    }
}