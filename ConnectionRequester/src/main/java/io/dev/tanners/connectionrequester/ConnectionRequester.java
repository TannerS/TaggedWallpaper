package io.dev.tanners.connectionrequester;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ConnectionRequester {
    // type of connection
    public static enum RequestType {
        POST("POST"),
        GET("GET");

        private String type;

        RequestType(String type) {
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

    public ConnectionRequester(String mUrl, String encoding) {
        mEntries = new HashMap<String, String>();
        mConnectionTimeOut = 5000;
        mReadTimeOut = 10000;
        mBody = null;
        mRequestype = RequestType.GET.toString();
        mCharset = encoding;
        this.mUrl = mUrl;
    }

    /**
     * set connecton type
     * @param type
     */
    public ConnectionRequester setRequestType(RequestType type) {
        mRequestype = type.toString();

        return this;
    }

    /**
     * charste for data
     * @param charset
     */
    public ConnectionRequester setCharSet(String charset)
    {
        mCharset = charset;

        return this;
    }

    /**
     * timeout
     * @param time
     */
    public ConnectionRequester setConnectionTimeOut(int time)
    {
        mConnectionTimeOut = time;

        return this;
    }

    /**
     * reading timeout
     * @param time
     */
    public ConnectionRequester setReadTimeOut(int time)
    {
        mReadTimeOut = time;

        return this;
    }

    /**
     * add passed in headers to http packet
     *
     * @param entries
     */
    public ConnectionRequester addRequestHeader(HashMap<String, String> entries)
    {
        if(entries != null) {

            for (Map.Entry<String, String> entry : entries.entrySet()) {
                String key = (entry.getKey()).trim();
                String value = (entry.getValue()).trim();
                this.mEntries.put(key, value);
            }
        }

        return this;
    }

    /**
     * add passed in header to http packet
     *
     * @param key
     * @param value
     */
    public ConnectionRequester addRequestHeader(String key, String value)
    {
        if(mEntries != null) {
            this.mEntries.put(key.trim(), value.trim());
        }

        return this;
    }

    /**
     * add body
     *
     * @param body
     */
    public ConnectionRequester addBasicBody(String body)
    {
        this.mBody = body;

        return this;
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
        if(mBody == null || mBody.length() == 0)
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
     * default settings
     */
    private void setConfig()
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

    public void fileDownloader(File mFile) throws FileNotFoundException, IOException
    {
        // set stream to write to passed in file
        OutputStream os = null;
        // open stream
        os = new FileOutputStream(mFile);
        // write buffer size at 1024 bytes at a time
        byte[] bytes = new byte[1024];
        int length;
        // write to file until end
        while ((length = getStream().read(bytes)) != -1) {
            os.write(bytes, 0, length);
        }
        // close streams
        getStream().close();
        os.close();
    }

    public void build() throws IOException {
        // set options
        setConfig();
        setHeaders();
        setBody();
    }

    /**
     * connect to url
     *
     * @return
     */
    public int connect() throws IOException {
        // open connect from url object
        connection = (HttpURLConnection) (new URL(mUrl)).openConnection();
        // build settings
        build();
        // return response code
        return (connection.getResponseCode());
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

    public boolean isConnectionOk() {
        return connection != null;
    }

    public InputStream getStream() throws IOException {
        if(connection != null) {
            return connection.getInputStream();
        }
        return null;
    }
}