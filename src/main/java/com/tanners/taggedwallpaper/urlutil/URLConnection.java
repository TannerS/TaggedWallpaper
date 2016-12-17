package com.tanners.taggedwallpaper.urlutil;

import org.apache.commons.io.IOUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class URLConnection
{
    private static int TIMEOUT_CONNECTION_MS = 10000;
    private static int TIMEOUT_READ_MS = 15000;
    private URL url;
    private boolean isGood;

    public URLConnection(String url_str)
    {
        HttpURLConnection connection = null;

        try
        {
            url = new URL(url_str);
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                connection.setConnectTimeout(TIMEOUT_CONNECTION_MS);
                connection.setReadTimeout(TIMEOUT_READ_MS);
                isGood = true;
            }
            else
            {
                throw new IOException();
            }
        }
        catch (MalformedURLException e)
        {
            isGood = false;
            e.printStackTrace();
        }
        catch (IOException e)
        {
            isGood = false;
            e.printStackTrace();
        }
        finally
        {
            if(connection != null)
            {

                connection.disconnect();
            }
        }
    }

    public ByteArrayOutputStream readData()
    {
        InputStream input_stream = null;
        ByteArrayOutputStream output = null;

        try
        {
            if (isGood())
            {
                output = new ByteArrayOutputStream();
                input_stream = url.openStream();
                byte[] buffer = IOUtils.toByteArray(input_stream);
                output.write(buffer, 0, buffer.length);
            }
            else
                throw new IOException();
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        finally
        {
            if(input_stream != null)
            {
                try
                {
                    input_stream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return output;
    }

    public HttpURLConnection getHttpURLConnection()
    {
        if(isGood())
        {
            HttpURLConnection http_con = null;

            try
            {
                http_con = (HttpURLConnection) url.openConnection();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return http_con;
        }
        else
            return null;
    }

    public boolean isGood()
    {
        return isGood;
    }
}
