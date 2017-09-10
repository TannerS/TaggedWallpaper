package io.tanners.taggedwallpaper.network;

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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ConnectionRequest {
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
    private boolean mIsGood;
    private String mBody;
    private String mUrl;
    private String mRequestype;
    private PrintWriter writer;
    private String mCharset;
    private final String LINE_BREAK = "\r\n";


    public ConnectionRequest(String mUrl) {
        mEntries = new HashMap<String, String>();
        mConnectionTimeOut = 10000;
        mReadTimeOut = 5000;
        mBody = null;
        mIsGood = false;
        mRequestype = TYPES.GET.toString();
        mCharset = (StandardCharsets.UTF_8.name().toLowerCase(Locale.getDefault()));
        this.mUrl = mUrl;
    }

    public void setRequestType(TYPES type) {
        mRequestype = type.toString();
    }

    public void setCharSet(String charset)
    {
        mCharset = charset;
    }

    public void setConnectionTimeOut(int time)
    {
        mConnectionTimeOut = time;
    }

    public void setReadTimeOut(int time)
    {
        mReadTimeOut = time;
    }

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

    public void addRequestHeader(String key, String value)
    {
        if(mEntries != null) {
            this.mEntries.put(key.trim(), value.trim());
        }
    }

    public void addBasicBody(String body)
    {
        this.mBody = body;
    }

    private void setHeaders()
    {
        if(mEntries != null) {
            for (Map.Entry<String, String> entry : mEntries.entrySet()) {
                String key = (entry.getKey()).trim();
                String value = (entry.getValue()).trim();
                connection.setRequestProperty(key, value);
            }
        }

        //connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        //connection.setRequestProperty("Content-Type", "text/html");
        //connection.setRequestProperty("charset", charset);
    }

    private void setBody()
    {
        if(mBody == null || mBody.length() <= 0)
            connection.setFixedLengthStreamingMode(0);
        else {
            connection.setFixedLengthStreamingMode(mBody.length());

            try {

                writer = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), mCharset), true);

                // TODO may need newline, may not need it
                writer.append(LINE_BREAK);
                writer.append(mBody).append(LINE_BREAK);
                writer.flush();
                writer.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                writer.close();
            }
        }
    }

    private void setDefaults()
    {
        if(mBody != null)
            connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setConnectTimeout(mConnectionTimeOut);
        connection.setReadTimeout(mReadTimeOut);
        connection.setUseCaches(true);
        try {
            connection.setRequestMethod(mRequestype);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    public boolean connect()
    {
        try
        {
            connection = (HttpURLConnection) (new URL(mUrl)).openConnection();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                mIsGood = true;
                setDefaults();
                setHeaders();
                setBody();
            }
            else
            {
                mIsGood = false;
                throw new IOException();
            }
        }
        catch (IOException e)
        {
            mIsGood = false;
            e.printStackTrace();
        }

        return mIsGood;
    }

    public void closeConnection()
    {
        if(connection != null)
        {
            connection.disconnect();
        }
    }

    public HttpURLConnection getConnection()
    {
        if(mIsGood)
            return this.connection;
        else
            return null;
    }

    public boolean isGood()
    {
        return mIsGood;
    }

//    public String getResponse() throws IOException
//    {
//        StringBuilder mBuilder = new StringBuilder();
//
//        int status = connection.getResponseCode();
//
//        if (status == HttpURLConnection.HTTP_OK)
//        {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String line = null;
//
//            while ((line = reader.readLine()) != null) {
//                mBuilder.append(line);
//            }
//
//            reader.close();
//
//            connection.disconnect();
//        } else {
//            throw new IOException("ERROR: " + status);
//        }
//
//        return mBuilder.toString();
//    }
}