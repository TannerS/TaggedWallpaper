//package com.tanners.taggedwallpaper.util;
//
//import com.tanners.taggedwallpaper.exceptions.BadHttpResponseCode;
//
//import org.apache.commons.io.IOUtils;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class URLConnection
//{
//    private static int TIMEOUT_CONNECTION_MS = 5000;
//    private static int TIMEOUT_READ_MS = 10000;
//    private boolean isGood;
//
//    public URLConnection(String url)
//    {
//        HttpURLConnection connection = null;
//
//        try
//        {
//            URL url1 = new URL(url);
//
//            connection = (HttpURLConnection) url1.openConnection();
//
//            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
//            {
//                connection.setConnectTimeout(TIMEOUT_CONNECTION_MS);
//                connection.setReadTimeout(TIMEOUT_READ_MS);
//
////                isValidWebsite = true;
//                isGood = true;
//            }
//            else
//            {
////                isValidWebsite = false;
//                isGood = false;
//                throw new BadHttpResponseCode("Response Code: " + connection.getResponseCode());
//            }
//        }
//        catch (BadHttpResponseCode | IOException badHttpResponseCode) {
//            isGood = false;
//            badHttpResponseCode.printStackTrace();
//        }
//        finally
//        {
//            if(connection != null)
//            {
//
//                connection.disconnect();
//            }
//        }
//    }
//
////    public ByteArrayOutputStream readData()
////    {
////        InputStream input_stream = null;
////        ByteArrayOutputStream output = null;
////
////        try
////        {
////            if (isGood && isValidWebsite)
////            {
////                output = new ByteArrayOutputStream();
////                input_stream = url.openStream();
////                byte[] buffer = IOUtils.toByteArray(input_stream);
////                output.write(buffer, 0, buffer.length);
////            }
////            else
////                throw new IOException();
////        }
////        catch(IOException e)
////        {
////            e.printStackTrace();
////        }
////
////        finally
////        {
////            if(input_stream != null)
////            {
////                try
////                {
////                    input_stream.close();
////                }
////                catch (IOException e)
////                {
////                    e.printStackTrace();
////                }
////            }
////        }
////        return output;
////    }
////
////    public HttpURLConnection getHttpURLConnection()
////    {
////        if(isGood)
////        {
////            HttpURLConnection http_con = null;
////
////            try
////            {
////                http_con = (HttpURLConnection) url.openConnection();
////            }
////            catch (IOException e)
////            {
////                e.printStackTrace();
////            }
////            return http_con;
////        }
////        else
////            return null;
////    }
//}
