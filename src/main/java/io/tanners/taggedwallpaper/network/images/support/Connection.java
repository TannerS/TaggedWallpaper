package io.tanners.taggedwallpaper.network.images.support;

import org.apache.commons.io.IOUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Connection
{
    private HttpURLConnection connection;
    private final int TIMEOUT_CONNECTION_MS = 10000;
    private final int TIMEOUT_READ_MS = 15000;
//    private URL url;
    private boolean isGood;

    public Connection(String urlStr)
    {
        try
        {
            // check for ok connection
//            url = new URL(urlStr);
//            URL url = new URL(urlStr);
            connection = (HttpURLConnection) (new URL(urlStr)).openConnection();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                connection.setConnectTimeout(TIMEOUT_CONNECTION_MS);
                connection.setReadTimeout(TIMEOUT_READ_MS);
                isGood = true;
            }
            else
            {
                isGood = false;
                throw new IOException();
            }
        }
        catch (IOException e)
        {
            isGood = false;
            e.printStackTrace();
        }
//        finally
//        {
////            if(connection != null)
////            {
////                connection.disconnect();
////            }
//        }
    }

//    public ByteArrayOutputStream readData()
//    {
//        InputStream input_stream = null;
//        ByteArrayOutputStream output = null;
//
//        try
//        {
//            if (isGood)
//            {
//                output = new ByteArrayOutputStream();
////                input_stream = url.openStream();
//                input_stream = connection.getInputStream();
//                byte[] buffer = IOUtils.toByteArray(input_stream);
//                output.write(buffer, 0, buffer.length);
//            }
//            else
//                throw new IOException();
//        }
//        catch(IOException e)
//        {
//            e.printStackTrace();
//        }
//
//        finally
//        {
//            if(input_stream != null)
//            {
//                try
//                {
//                    input_stream.close();
//                }
//                catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return output;
//    }

    public HttpURLConnection getConnection()
    {
        if(isGood)
        {
            return this.connection;
        }
        else
            return null;
    }

    public boolean isGood()
    {
        return isGood;
    }


}