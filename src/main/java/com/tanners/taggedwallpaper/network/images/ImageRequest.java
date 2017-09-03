package com.tanners.taggedwallpaper.network.images;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanners.taggedwallpaper.mappings.photodata.PhotoContainer;
import com.tanners.taggedwallpaper.network.images.support.Connection;
import com.tanners.taggedwallpaper.util.EndpointRestBuilder;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;

public class ImageRequest
{
    private EndpointRestBuilder endPoint;
    private int per_page;
    private int page;
//    private FireBaseUtil fb_util;
//    public static final int GROUP_SEARCH = 0;
//    public static final int OPEN_SEARCH = 1;

//    public ImageRequest(Context context, int per_pages, int page)
    public ImageRequest(int per_pages, int page)
    {
        endPoint = new EndpointRestBuilder();
        this.page = page;
        this.per_page = per_pages;
//        fb_util = new FireBaseUtil(context);
    }

//    public ImageRequest()
//    {
//        endPoint = new EndpointRestBuilder();
//        this.page = 1;
//        this.per_page = 25;
//    }

//    public List<PhotoItem> searchForImages(String tag)
    public PhotoContainer searchForImages(String tag)
    {
        PhotoContainer container = null;
        HttpURLConnection connection = null;

        try
        {
            connection = (new Connection(endPoint.getAllPhotos(tag, per_page, page))).getConnection();

            if(connection != null)
            {
                String response = IOUtils.toString(connection.getInputStream());
                ObjectMapper objectMapper = new ObjectMapper();
                container = objectMapper.readValue(response, PhotoContainer.class);
            }

            return container;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}



