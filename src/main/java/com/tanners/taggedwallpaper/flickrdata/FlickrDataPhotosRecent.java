package com.tanners.taggedwallpaper.flickrdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanners.taggedwallpaper.data.photodata.PhotoContainer;
import com.tanners.taggedwallpaper.flickrdata.urldata.FlickrURLBuilder;
import com.tanners.taggedwallpaper.util.URLConnection;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.net.MalformedURLException;

public class FlickrDataPhotosRecent
{
    FlickrURLBuilder url;

    public FlickrDataPhotosRecent()
    {
        url = new FlickrURLBuilder();
    }

    public PhotoContainer populateFlickrPhotos()
    {
        PhotoContainer flickr = null;
        URLConnection connection = null;

        try
        {
            connection = new URLConnection(url.getRecentPhotos(700, 1));

            if(connection.isGood())
            {
                String responseStr = IOUtils.toString(connection.getHttpURLConnection().getInputStream());
                ObjectMapper objectMapper = new ObjectMapper();
                flickr = objectMapper.readValue(responseStr, PhotoContainer.class);
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return flickr;
    }
}



